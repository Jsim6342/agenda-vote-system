package com.agenda.vote.agenda.facade;

import com.agenda.vote.agenda.domain.Agenda;
import com.agenda.vote.agenda.domain.Vote;
import com.agenda.vote.agenda.domain.agenda.interfaces.AgendaService;
import com.agenda.vote.agenda.domain.vote.interfaces.VoteService;
import com.agenda.vote.agenda.exception.*;
import com.agenda.vote.agenda.interfaces.request.*;
import com.agenda.vote.agenda.interfaces.response.AgendaResponse;
import com.agenda.vote.agenda.interfaces.response.AgendaSearchResponse;
import com.agenda.vote.agenda.interfaces.response.VoteResponse;
import com.agenda.vote.common.entity.BaseStatus;
import com.agenda.vote.common.entity.VoteType;
import com.agenda.vote.user.domain.User;
import com.agenda.vote.user.domain.user.interfaces.UserService;
import com.agenda.vote.user.exception.NoExistUserException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.agenda.vote.common.response.ErrorCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AgendaFacade {

    private final AgendaService agendaService;
    private final VoteService voteService;
    private final UserService userService;

    @Transactional
    public void createAgenda(Long userId, AgendaCreateRequest agendaCreateRequest) {
        User user = userService.getUser(userId);
        Agenda agenda = agendaService.saveAgenda(agendaCreateRequest, user);
        if (agendaCreateRequest.getVote() != null) {
            LocalDateTime now = LocalDateTime.now();
            if (agendaCreateRequest.getVote().getStartDate().compareTo(now) <= 0) {
                throw new NotAllowDateException(VOTE_NOT_ALLOW_DATE.getErrorMsg());
            }
            voteService.saveVote(agenda, agendaCreateRequest);
        }
    }

    @Transactional(readOnly = true)
    public List<AgendaResponse> getAgendaResponseList(Long userId) {
        User user = userService.getUser(userId);
        List<Agenda> agendaList = agendaService.getAgendaList();

        List<AgendaResponse> agendaResponseList = new ArrayList<>();
        for(Agenda agenda : agendaList) {
            VoteResponse voteResponse = voteService.getVoteResponse(user, agenda);
            AgendaResponse agendaResponse = new AgendaResponse(agenda.getTitle(), agenda.getContent(), voteResponse);
            agendaResponseList.add(agendaResponse);
        }

        return agendaResponseList;
    }

    @Transactional(readOnly = true)
    public AgendaResponse getAgendaResponse(Long userId, Long agendaId) {
        User user = userService.getUser(userId);
        Agenda agenda = agendaService.getAgenda(agendaId);
        VoteResponse voteResponse = voteService.getVoteResponse(user, agenda);
        return new AgendaResponse(agenda.getTitle(), agenda.getContent(), voteResponse);
    }

    @Transactional
    public void updateAgenda(Long userId, Long agendaId, AgendaUpdateRequest agendaUpdateRequest) {
        Agenda agenda = agendaService.getAgenda(agendaId);
        Vote agendaVote = voteService.getVoteByAgenda(agenda);
        if (userId != agenda.getUser().getId()) {
            throw new NotMatchPosterException(AGENDA_NOT_MATCH_POSTER.getErrorMsg());
        }
        if (agendaVote != null && agendaUpdateRequest.getVote() != null) {
            LocalDateTime now = LocalDateTime.now();
            if (agendaVote.getStartDate().compareTo(now) <= 0) {
                throw new AlreadyStartVoteException(VOTE_ALREADY_START.getErrorMsg());
            }
            agendaService.updateAgenda(agendaId, agendaUpdateRequest);
            voteService.updateVote(agenda, agendaUpdateRequest.getVote());
        }else {
            agendaService.updateAgenda(agendaId, agendaUpdateRequest);
        }
    }

    @Transactional
    public void deleteAgenda(Long userId, Long agendaId) {
        Agenda agenda = agendaService.getAgenda(agendaId);
        if (userId != agenda.getUser().getId()) {
            throw new NotMatchPosterException(AGENDA_NOT_MATCH_POSTER.getErrorMsg());
        }
        if (voteService.getVoteByAgenda(agenda) == null) {
            agendaService.deleteAgenda(agendaId);
        }else {
            agendaService.deleteAgenda(agendaId);
            voteService.deleteVote(agenda);
        }

    }

    @Transactional
    public void createVote(Long userId, Long agendaId, VoteCreateRequest voteCreateRequest) {
        Agenda agenda = agendaService.getAgenda(agendaId);
        if (userId != agenda.getUser().getId()) {
            throw new NotMatchPosterException(AGENDA_NOT_MATCH_POSTER.getErrorMsg());
        }

        Vote existVote = voteService.getExistVote(agenda);
        if (existVote == null) {
            voteService.createVote(agenda, voteCreateRequest);
        }else {
            if (existVote.getStatus() == BaseStatus.INACTIVE) {
                voteService.updateExistVote(agenda, voteCreateRequest);
            }else {
                throw new AlreadyExistVoteException(VOTE_ALREADY_EXIST.getErrorMsg());
            }
        }

    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void voting(Long userId, Long agendaId, VotingRequest votingRequest) {
        User user = userService.getUser(userId);
        Agenda agenda = agendaService.getAgenda(agendaId);
        Vote vote = voteService.getVoteByAgenda(agenda);

        Long totalVotingCountByUser = voteService.getTotalVotingCountByUser(vote, user);
        Long userRightCount = user.getVotingRightCount() - totalVotingCountByUser;
        Long sumRightCount = votingRequest.getAgreeCount() + votingRequest.getDisagreeCount();

        // 투표 일정 예외처리
        LocalDateTime now = LocalDateTime.now();
        if (!(vote.getStartDate().compareTo(now) <= 0 && vote.getEndDate().compareTo(now) >= 0)) {
            throw new NotMatchDateException(VOTE_NOT_MATCH_DATE.getErrorMsg());
        }

        // 투표 타입 예외처리
        if (vote.getType() == VoteType.LIMIT
                & voteService.getTotalVotingCount(vote) + sumRightCount > 10) {
            throw new TypeInsufficientConditionException(VOTE_TYPE_INSUFFICIENT_CONDITION.getErrorMsg());
        }

        // 의결권 보유 갯수 예외처리
        if (sumRightCount > userRightCount) {
            throw new InsufficientRightCountException(VOTE_INSUFFICIENT_RIGHT_COUNT.getErrorMsg());
        }

        vote.voting(votingRequest);
        voteService.createVoting(user, vote, votingRequest);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public List<AgendaResponse> searchAgendaResponses(Long userId, VoteSearchFilter voteSearchFilter) {
        User loinUser = userService.getUser(userId);

        User searchUser = null;
        if (voteSearchFilter.getUserId() != null) {
            try {
                searchUser = userService.getUser(voteSearchFilter.getUserId());
            }catch (NoExistUserException e) {

            }
        }

        List<AgendaSearchResponse> agendaSearchResponses = agendaService.searchAgendaResponses(searchUser, voteSearchFilter);
        List<AgendaResponse> agendaResponseList = new ArrayList<>();
        for (AgendaSearchResponse agendaSearchResponse : agendaSearchResponses) {
            Agenda agenda = agendaService.getAgenda(agendaSearchResponse.getAgendaId());
            VoteResponse voteResponse = voteService.getVoteResponse(loinUser, agenda);

            AgendaResponse agendaResponse = new AgendaResponse(agendaSearchResponse.getTitle(),
                    agendaSearchResponse.getContent(), voteResponse);

            agendaResponseList.add(agendaResponse);
        }

        return agendaResponseList;
    }

}

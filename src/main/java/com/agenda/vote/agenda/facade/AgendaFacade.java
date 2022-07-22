package com.agenda.vote.agenda.facade;

import com.agenda.vote.agenda.domain.Agenda;
import com.agenda.vote.agenda.domain.agenda.interfaces.AgendaService;
import com.agenda.vote.agenda.domain.vote.interfaces.VoteService;
import com.agenda.vote.agenda.exception.NotMatchPosterException;
import com.agenda.vote.agenda.interfaces.request.AgendaCreateRequest;
import com.agenda.vote.agenda.interfaces.request.AgendaUpdateRequest;
import com.agenda.vote.agenda.interfaces.response.AgendaResponse;
import com.agenda.vote.agenda.interfaces.response.VoteResponse;
import com.agenda.vote.user.domain.User;
import com.agenda.vote.user.domain.user.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static com.agenda.vote.common.response.ErrorCode.AGENDA_NOT_MATCH_POSTER;

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
            voteService.saveVote(agendaCreateRequest, agenda);
        }
    }

    @Transactional
    public List<AgendaResponse> getAgendaResponseList() {
        List<Agenda> agendaList = agendaService.getAgendaList();

        List<AgendaResponse> agendaResponseList = new ArrayList<>();
        for(Agenda agenda : agendaList) {
            VoteResponse voteResponse = voteService.getVoteResponse(agenda);
            AgendaResponse agendaResponse = new AgendaResponse(agenda.getTitle(), agenda.getContent(), voteResponse);
            agendaResponseList.add(agendaResponse);
        }

        return agendaResponseList;
    }

    @Transactional
    public AgendaResponse getAgendaResponse(Long agendaId) {
        Agenda agenda = agendaService.getAgenda(agendaId);
        VoteResponse voteResponse = voteService.getVoteResponse(agenda);
        return new AgendaResponse(agenda.getTitle(), agenda.getContent(), voteResponse);
    }

    @Transactional
    public void updateAgenda(Long userId, Long agendaId, AgendaUpdateRequest agendaUpdateRequest) {
        Agenda agenda = agendaService.getAgenda(agendaId);
        if (userId != agenda.getUser().getId()) {
            throw new NotMatchPosterException(AGENDA_NOT_MATCH_POSTER.getErrorMsg());
        }
        if (voteService.getVoteResponse(agenda) == null) {
            agendaService.updateAgenda(agendaId, agendaUpdateRequest);
        }else {
            agendaService.updateAgenda(agendaId, agendaUpdateRequest);
            voteService.updateVote(agenda, agendaUpdateRequest.getVote());
        }
    }

    @Transactional
    public void deleteAgenda(Long userId, Long agendaId) {
        Agenda agenda = agendaService.getAgenda(agendaId);
        if (userId != agenda.getUser().getId()) {
            throw new NotMatchPosterException(AGENDA_NOT_MATCH_POSTER.getErrorMsg());
        }
        if (voteService.getVoteResponse(agenda) == null) {
            agendaService.deleteAgenda(agendaId);
        }else {
            agendaService.deleteAgenda(agendaId);
            voteService.deleteVote(agenda);
        }

    }
}

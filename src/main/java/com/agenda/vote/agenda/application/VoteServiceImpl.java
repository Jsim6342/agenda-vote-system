package com.agenda.vote.agenda.application;

import com.agenda.vote.agenda.domain.Agenda;
import com.agenda.vote.agenda.domain.Vote;
import com.agenda.vote.agenda.domain.Voting;
import com.agenda.vote.agenda.domain.vote.interfaces.VoteReader;
import com.agenda.vote.agenda.domain.vote.interfaces.VoteService;
import com.agenda.vote.agenda.domain.vote.interfaces.VoteStore;
import com.agenda.vote.agenda.exception.BadVotingRequestException;
import com.agenda.vote.agenda.interfaces.request.*;
import com.agenda.vote.agenda.interfaces.response.AgendaResponse;
import com.agenda.vote.agenda.interfaces.response.VoteResponse;
import com.agenda.vote.common.entity.Opinion;
import com.agenda.vote.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.agenda.vote.common.entity.BaseStatus.ACTIVE;
import static com.agenda.vote.common.response.ErrorCode.VOTE_BAD_REQUEST;

@Slf4j
@Service
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService {

    private final VoteStore voteStore;
    private final VoteReader voteReader;

    @Override
    public void saveVote(Agenda agenda, AgendaCreateRequest agendaCreateRequest) {
        voteStore.saveVote(agendaCreateRequest.getVote().toEntity(agenda));
    }

    @Override
    public VoteResponse getVoteResponse(User user, Agenda agenda) {
        Vote vote = voteReader.getVoteResponse(agenda);

        List<Voting> votingList = voteReader.getVotingList(vote);
        Long totalUsedRightCount = 0L;
        for (Voting voting : votingList) {
            if (voting.getUser() == user) totalUsedRightCount += voting.getVotingCount();
        }
        Long extraRightCount = user.getVotingRightCount() - totalUsedRightCount;

        if (vote != null) {
            return new VoteResponse(vote.getType(),
                    vote.getAgreeCount(),
                    vote.getDisagreeCount(),
                    vote.getStartDate(),
                    vote.getEndDate(),
                    vote.getCreated(),
                    extraRightCount);
        }
        return null;
    }

    @Override
    public void updateVote(Agenda agenda, VoteUpdateRequest voteUpdateRequest) {
        voteStore.updateVote(agenda, voteUpdateRequest);
    }

    @Override
    public void deleteVote(Agenda agenda) {
        voteStore.deleteVote(agenda);
    }

    @Override
    public Vote getExistVote(Agenda agenda) {
        return voteReader.getExistVote(agenda);
    }

    @Override
    public void updateExistVote(Agenda agenda, VoteCreateRequest voteCreateRequest) {
        voteStore.updateExistVote(agenda, voteCreateRequest);
    }

    @Override
    public void createVote(Agenda agenda, VoteCreateRequest voteCreateRequest) {
        voteStore.saveVote(voteCreateRequest.toEntity(agenda));
    }

    @Override
    public Vote getVote(Long voteId) {
        return voteStore.getVote(voteId);
    }

    @Override
    public Long getTotalVotingCount(Vote vote) {
        Long totalCount = 0L;
        List<Voting> votingList = voteReader.getVotingList(vote);
        for (Voting voting : votingList) {
            totalCount += voting.getVotingCount();
        }
        return totalCount;
    }

    @Override
    public Long getTotalVotingCountByUser(Vote vote, User user) {
        Long totalCount = 0L;
        List<Voting> votingList = voteReader.getVotingList(vote);
        for (Voting voting : votingList) {
            if (voting.getUser() == user) totalCount += voting.getVotingCount();
        }
        return totalCount;
    }

    @Override
    public void createVoting(User user, Vote vote, VotingRequest votingRequest) {
        Opinion opinion;
        if (votingRequest.getAgreeCount() == 0 && votingRequest.getDisagreeCount() > 0) {
            opinion = Opinion.DISAGREE;
        }else if (votingRequest.getAgreeCount() > 0 && votingRequest.getDisagreeCount() == 0) {
            opinion = Opinion.AGREE;
        }else {
            throw new BadVotingRequestException(VOTE_BAD_REQUEST.getErrorMsg());
        }

        Long votingCount = votingRequest.getAgreeCount() + votingRequest.getDisagreeCount();

        Voting voting = Voting.builder()
                .user(user)
                .vote(vote)
                .opinion(opinion)
                .votingCount(votingCount)
                .status(ACTIVE)
                .build();

        voteStore.saveVoting(user, vote, voting);
    }

    @Override
    public Vote getVoteByAgenda(Agenda agenda) {
        return voteReader.getVoteByAgenda(agenda);
    }

}

package com.agenda.vote.agenda.domain.vote.interfaces;

import com.agenda.vote.agenda.domain.Agenda;
import com.agenda.vote.agenda.domain.Vote;
import com.agenda.vote.agenda.interfaces.request.AgendaCreateRequest;
import com.agenda.vote.agenda.interfaces.request.VoteCreateRequest;
import com.agenda.vote.agenda.interfaces.request.VoteUpdateRequest;
import com.agenda.vote.agenda.interfaces.request.VotingRequest;
import com.agenda.vote.agenda.interfaces.response.VoteResponse;
import com.agenda.vote.user.domain.User;

public interface VoteService {
    void saveVote(Agenda agenda, AgendaCreateRequest agendaCreateRequest);

    VoteResponse getVoteResponse(User user, Agenda agenda);

    void updateVote(Agenda agenda, VoteUpdateRequest voteUpdateRequest);

    void deleteVote(Agenda agenda);

    Vote getExistVote(Agenda agenda);

    void updateExistVote(Agenda agenda, VoteCreateRequest voteCreateRequest);

    void createVote(Agenda agenda, VoteCreateRequest voteCreateRequest);

    Vote getVote(Long voteId);

    Long getTotalVotingCount(Vote vote);

    Long getTotalVotingCountByUser(Vote vote, User user);

    void createVoting(User user, Vote vote, VotingRequest votingRequest);


    Vote getVoteByAgenda(Agenda agenda);
}

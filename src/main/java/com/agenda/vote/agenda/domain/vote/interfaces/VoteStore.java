package com.agenda.vote.agenda.domain.vote.interfaces;

import com.agenda.vote.agenda.domain.Agenda;
import com.agenda.vote.agenda.domain.Vote;
import com.agenda.vote.agenda.domain.Voting;
import com.agenda.vote.agenda.interfaces.request.VoteCreateRequest;
import com.agenda.vote.agenda.interfaces.request.VoteUpdateRequest;
import com.agenda.vote.common.entity.Opinion;
import com.agenda.vote.user.domain.User;

public interface VoteStore {
    Vote saveVote(Vote vote);
    void updateVote(Agenda agenda, VoteUpdateRequest voteUpdateRequest);
    void deleteVote(Agenda agenda);

    void updateExistVote(Agenda agenda, VoteCreateRequest voteCreateRequest);

    Vote getVote(Long voteId);

    void saveVoting(User user, Vote vote, Voting voting);
}

package com.agenda.vote.agenda.domain.vote.interfaces;

import com.agenda.vote.agenda.domain.Agenda;
import com.agenda.vote.agenda.domain.Vote;
import com.agenda.vote.agenda.interfaces.request.VoteUpdateRequest;

public interface VoteStore {
    Vote saveVote(Vote vote);
    void updateVote(Agenda agenda, VoteUpdateRequest voteUpdateRequest);
    void deleteVote(Agenda agenda);
}

package com.agenda.vote.agenda.domain.vote.interfaces;

import com.agenda.vote.agenda.domain.Agenda;
import com.agenda.vote.agenda.domain.Vote;
import com.agenda.vote.agenda.interfaces.response.VoteResponse;

public interface VoteReader {
    Vote getVoteResponse(Agenda agenda);
}

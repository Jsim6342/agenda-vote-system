package com.agenda.vote.agenda.domain.vote.interfaces;

import com.agenda.vote.agenda.domain.Agenda;
import com.agenda.vote.agenda.domain.Vote;
import com.agenda.vote.agenda.domain.Voting;
import com.agenda.vote.agenda.interfaces.request.SearchFilter;
import com.agenda.vote.agenda.interfaces.response.AgendaResponse;

import java.util.List;

public interface VoteReader {
    Vote getVoteResponse(Agenda agenda);

    Vote getExistVote(Agenda agenda);

    List<Voting> getVotingList(Vote vote);

    Vote getVoteByAgenda(Agenda agenda);
}

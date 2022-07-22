package com.agenda.vote.agenda.domain.vote.interfaces;

import com.agenda.vote.agenda.domain.Agenda;
import com.agenda.vote.agenda.interfaces.request.AgendaCreateRequest;
import com.agenda.vote.agenda.interfaces.request.VoteUpdateRequest;
import com.agenda.vote.agenda.interfaces.response.VoteResponse;

public interface VoteService {
    void saveVote(AgendaCreateRequest agendaCreateRequest, Agenda agenda);

    VoteResponse getVoteResponse(Agenda agneda);

    void updateVote(Agenda agenda, VoteUpdateRequest voteUpdateRequest);

    void deleteVote(Agenda agenda);
}

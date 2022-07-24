package com.agenda.vote.agenda.domain.agenda.interfaces;

import com.agenda.vote.agenda.domain.Agenda;
import com.agenda.vote.agenda.interfaces.request.VoteSearchFilter;
import com.agenda.vote.agenda.interfaces.response.AgendaSearchResponse;
import com.agenda.vote.user.domain.User;

import java.util.List;

public interface AgendaReader {
    List<Agenda> selectAgendaList();
    Agenda selectAgenda(Long agendaId);
    List<AgendaSearchResponse> searchAgendaResponses(User user, VoteSearchFilter voteSearchFilter);
}

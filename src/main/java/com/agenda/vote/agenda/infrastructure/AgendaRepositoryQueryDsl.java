package com.agenda.vote.agenda.infrastructure;

import com.agenda.vote.agenda.domain.Agenda;
import com.agenda.vote.agenda.interfaces.request.SearchFilter;
import com.agenda.vote.agenda.interfaces.response.AgendaResponse;
import com.agenda.vote.agenda.interfaces.response.AgendaSearchResponse;
import com.agenda.vote.user.domain.User;

import java.util.List;

public interface AgendaRepositoryQueryDsl {
    List<AgendaSearchResponse> searchAgendaResponses(User user, SearchFilter searchFilter);
}

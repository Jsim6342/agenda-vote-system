package com.agenda.vote.agenda.domain.agenda.interfaces;

import com.agenda.vote.agenda.domain.Agenda;
import com.agenda.vote.agenda.interfaces.request.AgendaCreateRequest;
import com.agenda.vote.agenda.interfaces.request.AgendaUpdateRequest;
import com.agenda.vote.agenda.interfaces.request.VoteSearchFilter;
import com.agenda.vote.agenda.interfaces.response.AgendaSearchResponse;
import com.agenda.vote.user.domain.User;

import java.util.List;

public interface AgendaService {
    Agenda saveAgenda(AgendaCreateRequest agendaCreateRequest, User user);

    List<Agenda> getAgendaList();

    Agenda getAgenda(Long agendaId);

    void updateAgenda(Long agendaId, AgendaUpdateRequest agendaUpdateRequest);

    void deleteAgenda(Long agendaId);
    List<AgendaSearchResponse> searchAgendaResponses(User user, VoteSearchFilter voteSearchFilter);
}

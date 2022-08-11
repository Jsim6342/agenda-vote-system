package com.agenda.vote.agenda.domain.agenda.interfaces;

import com.agenda.vote.agenda.domain.Agenda;
import com.agenda.vote.agenda.interfaces.request.AgendaUpdateRequest;
import com.agenda.vote.user.domain.User;

public interface AgendaStore {

    Agenda saveAgenda(Agenda agenda);
    Agenda updateAgenda(Long agendaId, AgendaUpdateRequest agendaUpdateRequest);
    void deleteAgenda(Long agendaId);


}

package com.agenda.vote.agenda.domain.agenda.interfaces;

import com.agenda.vote.agenda.domain.Agenda;

import java.util.List;

public interface AgendaReader {
    List<Agenda> selectAgendaList();

    Agenda selectAgenda(Long agendaId);
}

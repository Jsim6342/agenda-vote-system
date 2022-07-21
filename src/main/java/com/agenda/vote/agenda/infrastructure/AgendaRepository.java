package com.agenda.vote.agenda.infrastructure;

import com.agenda.vote.agenda.domain.Agenda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgendaRepository extends JpaRepository<Agenda, Long> {
}

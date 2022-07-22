package com.agenda.vote.agenda.infrastructure;

import com.agenda.vote.agenda.domain.Agenda;
import com.agenda.vote.common.entity.BaseStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AgendaRepository extends JpaRepository<Agenda, Long> {
    Optional<Agenda> findByIdAndStatus(Long agendaId, BaseStatus status);
    List<Agenda> findAllByStatus(BaseStatus status);
}

package com.agenda.vote.agenda.infrastructure;

import com.agenda.vote.agenda.domain.Agenda;
import com.agenda.vote.agenda.domain.Vote;
import com.agenda.vote.common.entity.BaseStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    Vote findByAgendaAndStatus(Agenda agenda, BaseStatus status);

    Vote findByAgenda(Agenda agenda);

    Optional<Vote> findByIdAndStatus(Long voteId, BaseStatus status);

}

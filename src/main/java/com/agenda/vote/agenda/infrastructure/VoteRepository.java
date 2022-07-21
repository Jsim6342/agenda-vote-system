package com.agenda.vote.agenda.infrastructure;

import com.agenda.vote.agenda.domain.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, Long> {
}

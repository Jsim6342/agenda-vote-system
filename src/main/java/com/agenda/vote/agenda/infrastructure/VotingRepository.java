package com.agenda.vote.agenda.infrastructure;

import com.agenda.vote.agenda.domain.Vote;
import com.agenda.vote.agenda.domain.Voting;
import com.agenda.vote.common.entity.BaseStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VotingRepository extends JpaRepository<Voting, Long> {
    List<Voting> findByVoteAndStatus(Vote vote, BaseStatus status);
}

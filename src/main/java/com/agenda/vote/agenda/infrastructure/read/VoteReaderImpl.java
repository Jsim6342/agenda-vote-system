package com.agenda.vote.agenda.infrastructure.read;

import com.agenda.vote.agenda.domain.Agenda;
import com.agenda.vote.agenda.domain.Vote;
import com.agenda.vote.agenda.domain.Voting;
import com.agenda.vote.agenda.domain.vote.interfaces.VoteReader;
import com.agenda.vote.agenda.infrastructure.VoteRepository;
import com.agenda.vote.agenda.infrastructure.VotingRepository;
import com.agenda.vote.agenda.interfaces.request.SearchFilter;
import com.agenda.vote.agenda.interfaces.response.AgendaResponse;
import com.agenda.vote.common.entity.BaseStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.agenda.vote.common.entity.BaseStatus.ACTIVE;

@Slf4j
@Component
@RequiredArgsConstructor
public class VoteReaderImpl implements VoteReader {

    private final VoteRepository voteRepository;
    private final VotingRepository votingRepository;

    @Override
    public Vote getVoteResponse(Agenda agenda) {
        return voteRepository.findByAgendaAndStatus(agenda, ACTIVE);
    }

    @Override
    public Vote getExistVote(Agenda agenda) {
        return voteRepository.findByAgenda(agenda);
    }

    @Override
    public List<Voting> getVotingList(Vote vote) {
        return votingRepository.findByVoteAndStatus(vote, ACTIVE);
    }

    @Override
    public Vote getVoteByAgenda(Agenda agenda) {
        return voteRepository.findByAgendaAndStatus(agenda, ACTIVE);
    }
}

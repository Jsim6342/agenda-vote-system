package com.agenda.vote.agenda.infrastructure.read;

import com.agenda.vote.agenda.domain.Agenda;
import com.agenda.vote.agenda.domain.Vote;
import com.agenda.vote.agenda.domain.vote.interfaces.VoteReader;
import com.agenda.vote.agenda.infrastructure.VoteRepository;
import com.agenda.vote.common.entity.BaseStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class VoteReaderImpl implements VoteReader {

    private final VoteRepository voteRepository;

    @Override
    public Vote getVoteResponse(Agenda agenda) {
        return voteRepository.findByAgendaAndStatus(agenda, BaseStatus.ACTIVE);
    }
}

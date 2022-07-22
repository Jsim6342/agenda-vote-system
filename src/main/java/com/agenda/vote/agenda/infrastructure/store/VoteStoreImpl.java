package com.agenda.vote.agenda.infrastructure.store;

import com.agenda.vote.agenda.domain.Agenda;
import com.agenda.vote.agenda.domain.Vote;
import com.agenda.vote.agenda.domain.vote.interfaces.VoteStore;
import com.agenda.vote.agenda.exception.NoExistVoteException;
import com.agenda.vote.agenda.infrastructure.VoteRepository;
import com.agenda.vote.agenda.interfaces.request.VoteUpdateRequest;
import com.agenda.vote.common.entity.BaseStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.agenda.vote.common.entity.BaseStatus.ACTIVE;
import static com.agenda.vote.common.response.ErrorCode.VOTE_NOT_FOUND;

@Slf4j
@Component
@RequiredArgsConstructor
public class VoteStoreImpl implements VoteStore {

    private final VoteRepository voteRepository;

    @Override
    public Vote saveVote(Vote vote) {
        return voteRepository.save(vote);
    }

    @Override
    public void updateVote(Agenda agenda, VoteUpdateRequest voteUpdateRequest) {
        Vote vote = voteRepository.findByAgendaAndStatus(agenda, ACTIVE);
        if (vote == null) {
            throw new NoExistVoteException(VOTE_NOT_FOUND.getErrorMsg());
        }
        vote.update(voteUpdateRequest);
    }

    @Override
    public void deleteVote(Agenda agenda) {
        Vote vote = voteRepository.findByAgendaAndStatus(agenda, ACTIVE);
        if (vote == null) {
            throw new NoExistVoteException(VOTE_NOT_FOUND.getErrorMsg());
        }
        vote.updateOffStatus();
    }
}

package com.agenda.vote.agenda.infrastructure.store;

import com.agenda.vote.agenda.domain.Agenda;
import com.agenda.vote.agenda.domain.Vote;
import com.agenda.vote.agenda.domain.Voting;
import com.agenda.vote.agenda.domain.vote.interfaces.VoteStore;
import com.agenda.vote.agenda.exception.NoExistVoteException;
import com.agenda.vote.agenda.infrastructure.VoteRepository;
import com.agenda.vote.agenda.infrastructure.VotingRepository;
import com.agenda.vote.agenda.interfaces.request.VoteCreateRequest;
import com.agenda.vote.agenda.interfaces.request.VoteUpdateRequest;
import com.agenda.vote.common.entity.BaseStatus;
import com.agenda.vote.common.entity.Opinion;
import com.agenda.vote.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.agenda.vote.common.entity.BaseStatus.ACTIVE;
import static com.agenda.vote.common.entity.BaseStatus.INACTIVE;
import static com.agenda.vote.common.response.ErrorCode.VOTE_NOT_FOUND;

@Slf4j
@Component
@RequiredArgsConstructor
public class VoteStoreImpl implements VoteStore {

    private final VoteRepository voteRepository;
    private final VotingRepository votingRepository;

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

    @Override
    public void updateExistVote(Agenda agenda, VoteCreateRequest voteCreateRequest) {
        Vote vote = voteRepository.findByAgenda(agenda);
        if (vote == null) {
            throw new NoExistVoteException(VOTE_NOT_FOUND.getErrorMsg());
        }
        vote.updateExistVote(voteCreateRequest);
    }

    @Override
    public Vote getVote(Long voteId) {
        return voteRepository.findByIdAndStatus(voteId, ACTIVE)
                .orElseThrow(() -> new NoExistVoteException(VOTE_NOT_FOUND.getErrorMsg()));
    }

    @Override
    public void saveVoting(User user, Vote vote, Voting voting) {
        votingRepository.save(voting);
    }
}

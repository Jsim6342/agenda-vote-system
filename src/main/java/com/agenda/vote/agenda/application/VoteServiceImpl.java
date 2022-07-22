package com.agenda.vote.agenda.application;

import com.agenda.vote.agenda.domain.Agenda;
import com.agenda.vote.agenda.domain.Vote;
import com.agenda.vote.agenda.domain.vote.interfaces.VoteReader;
import com.agenda.vote.agenda.domain.vote.interfaces.VoteService;
import com.agenda.vote.agenda.domain.vote.interfaces.VoteStore;
import com.agenda.vote.agenda.interfaces.request.AgendaCreateRequest;
import com.agenda.vote.agenda.interfaces.request.VoteUpdateRequest;
import com.agenda.vote.agenda.interfaces.response.VoteResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService {

    private final VoteStore voteStore;
    private final VoteReader voteReader;

    @Override
    public void saveVote(AgendaCreateRequest agendaCreateRequest, Agenda agenda) {
        voteStore.saveVote(agendaCreateRequest.getVote().toEntity(agenda));
    }

    @Override
    public VoteResponse getVoteResponse(Agenda agenda) {
        Vote vote = voteReader.getVoteResponse(agenda);
        if (vote != null) {
            return new VoteResponse(vote.getType(),
                    vote.getAgreeCount(),
                    vote.getDisagreeCount(),
                    vote.getStartDate(),
                    vote.getEndDate(),
                    vote.getCreated());
        }
        return null;
    }

    @Override
    public void updateVote(Agenda agenda, VoteUpdateRequest voteUpdateRequest) {
        voteStore.updateVote(agenda, voteUpdateRequest);
    }

    @Override
    public void deleteVote(Agenda agenda) {
        voteStore.deleteVote(agenda);
    }
}

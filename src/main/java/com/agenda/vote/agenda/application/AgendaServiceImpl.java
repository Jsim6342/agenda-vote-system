package com.agenda.vote.agenda.application;

import com.agenda.vote.agenda.domain.Agenda;
import com.agenda.vote.agenda.domain.agenda.interfaces.AgendaReader;
import com.agenda.vote.agenda.domain.agenda.interfaces.AgendaService;
import com.agenda.vote.agenda.domain.agenda.interfaces.AgendaStore;
import com.agenda.vote.agenda.exception.VoteDateException;
import com.agenda.vote.agenda.interfaces.request.AgendaCreateRequest;
import com.agenda.vote.agenda.interfaces.request.AgendaUpdateRequest;
import com.agenda.vote.agenda.interfaces.request.VoteSearchFilter;
import com.agenda.vote.agenda.interfaces.response.AgendaSearchResponse;
import com.agenda.vote.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.agenda.vote.common.response.ErrorCode.VOTE_INVALID_DATE;

@Slf4j
@Service
@RequiredArgsConstructor
public class AgendaServiceImpl implements AgendaService {

    private final AgendaStore agendaStore;
    private final AgendaReader agendaReader;

    @Override
    public Agenda saveAgenda(AgendaCreateRequest agendaCreateRequest, User user) {
        LocalDateTime startDate = agendaCreateRequest.getVote().getStartDate();
        LocalDateTime endDate = agendaCreateRequest.getVote().getEndDate();
        if (startDate.compareTo(endDate) >= 0) {
            throw new VoteDateException(VOTE_INVALID_DATE.getErrorMsg());
        }
        return agendaStore.saveAgenda(agendaCreateRequest.toEntity(user));
    }

    @Override
    public List<Agenda> getAgendaList() {
        return agendaReader.selectAgendaList();
    }

    @Override
    public Agenda getAgenda(Long agendaId) {
        return agendaReader.selectAgenda(agendaId);
    }

    @Override
    public void updateAgenda(Long agendaId, AgendaUpdateRequest agendaUpdateRequest) {
        LocalDateTime startDate = agendaUpdateRequest.getVote().getStartDate();
        LocalDateTime endDate = agendaUpdateRequest.getVote().getEndDate();
        if (startDate.compareTo(endDate) >= 0) {
            throw new VoteDateException(VOTE_INVALID_DATE.getErrorMsg());
        }
        agendaStore.updateAgenda(agendaId, agendaUpdateRequest);
    }
    @Override
    public void deleteAgenda(Long agendaId) {
        agendaStore.deleteAgenda(agendaId);
    }

    @Override
    public List<AgendaSearchResponse> searchAgendaResponses(User user, VoteSearchFilter voteSearchFilter) {
        return agendaReader.searchAgendaResponses(user, voteSearchFilter);
    }


}

package com.agenda.vote.agenda.infrastructure.read;

import com.agenda.vote.agenda.domain.Agenda;
import com.agenda.vote.agenda.domain.agenda.interfaces.AgendaReader;
import com.agenda.vote.agenda.exception.NoExistAgendaException;
import com.agenda.vote.agenda.infrastructure.AgendaRepository;
import com.agenda.vote.agenda.interfaces.request.VoteSearchFilter;
import com.agenda.vote.agenda.interfaces.response.AgendaSearchResponse;
import com.agenda.vote.common.entity.BaseStatus;
import com.agenda.vote.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.agenda.vote.common.response.ErrorCode.AGENDA_NOT_FOUND;

@Slf4j
@Component
@RequiredArgsConstructor
public class AgendaReaderImpl implements AgendaReader {

    private final AgendaRepository agendaRepository;

    @Override
    public List<Agenda> selectAgendaList() {
        return agendaRepository.findAllByStatus(BaseStatus.ACTIVE);
    }

    @Override
    public Agenda selectAgenda(Long agendaId) {
        return agendaRepository.findByIdAndStatus(agendaId, BaseStatus.ACTIVE)
                .orElseThrow(() -> new NoExistAgendaException(AGENDA_NOT_FOUND.getErrorMsg()));
    }
    @Override
    public List<AgendaSearchResponse> searchAgendaResponses(User user, VoteSearchFilter voteSearchFilter) {
        return agendaRepository.searchAgendaResponses(user, voteSearchFilter);
    }

}

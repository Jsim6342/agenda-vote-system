package com.agenda.vote.agenda.infrastructure.store;

import com.agenda.vote.agenda.domain.Agenda;
import com.agenda.vote.agenda.domain.agenda.interfaces.AgendaStore;
import com.agenda.vote.agenda.exception.NoExistAgendaException;
import com.agenda.vote.agenda.infrastructure.AgendaRepository;
import com.agenda.vote.agenda.interfaces.request.AgendaUpdateRequest;
import com.agenda.vote.common.entity.BaseStatus;
import com.agenda.vote.common.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AgendaStoreImpl implements AgendaStore {

    private final AgendaRepository agendaRepository;

    @Override
    public Agenda saveAgenda(Agenda agenda) {
        return agendaRepository.save(agenda);
    }

    @Override
    public Agenda updateAgenda(Long agendaId, AgendaUpdateRequest agendaUpdateRequest) {
        Agenda agenda = agendaRepository.findByIdAndStatus(agendaId, BaseStatus.ACTIVE)
                .orElseThrow(() -> new NoExistAgendaException(ErrorCode.AGENDA_NOT_FOUND.getErrorMsg()));
        agenda.update(agendaUpdateRequest);
        return agenda;
    }

    @Override
    public void deleteAgenda(Long agendaId) {
        Agenda agenda = agendaRepository.findByIdAndStatus(agendaId, BaseStatus.ACTIVE)
                .orElseThrow(() -> new NoExistAgendaException(ErrorCode.AGENDA_NOT_FOUND.getErrorMsg()));
        agenda.updateOffStatus();
    }
}

package com.agenda.vote.agenda.interfaces.request;


import com.agenda.vote.agenda.domain.Agenda;
import com.agenda.vote.agenda.domain.Vote;
import com.agenda.vote.common.annotation.ValidEnum;
import com.agenda.vote.common.entity.BaseStatus;
import com.agenda.vote.common.entity.VoteType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Future;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VoteCreateRequest {

    @ValidEnum(enumClass = VoteType.class)
    private VoteType type;
    @Future
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startDate;
    @Future
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endDate;

    public Vote toEntity(Agenda agenda) {
        return Vote.builder()
                .agenda(agenda)
                .type(type)
                .agreeCount(0L)
                .disagreeCount(0L)
                .startDate(startDate)
                .endDate(endDate)
                .status(BaseStatus.ACTIVE)
                .build();
    }
}

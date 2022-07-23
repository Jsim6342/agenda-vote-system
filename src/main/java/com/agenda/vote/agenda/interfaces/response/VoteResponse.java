package com.agenda.vote.agenda.interfaces.response;

import com.agenda.vote.common.entity.VoteType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VoteResponse {
    private VoteType type;
    private Long agreeCount;
    private Long disagreeCount;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime created;
    private Long extraRightCount;
}

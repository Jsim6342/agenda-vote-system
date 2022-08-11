package com.agenda.vote.agenda.interfaces.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AgendaSearchResponse {
    private Long agendaId;
    private String title;
    private String content;
    private LocalDateTime created;
    private LocalDateTime updated;
}

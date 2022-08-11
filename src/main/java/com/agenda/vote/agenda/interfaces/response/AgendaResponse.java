package com.agenda.vote.agenda.interfaces.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AgendaResponse {
    private String title;
    private String content;
    private VoteResponse vote;
}

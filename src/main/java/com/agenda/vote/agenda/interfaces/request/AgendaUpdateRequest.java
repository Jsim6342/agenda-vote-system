package com.agenda.vote.agenda.interfaces.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AgendaUpdateRequest {

    @NotBlank
    private String title;
    @NotBlank
    private String content;
    private VoteUpdateRequest vote;

}

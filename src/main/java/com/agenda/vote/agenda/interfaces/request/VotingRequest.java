package com.agenda.vote.agenda.interfaces.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VotingRequest {
    @NotBlank
    private Long agreeCount;
    @NotBlank
    private Long disagreeCount;
}

package com.agenda.vote.agenda.interfaces.request;

import com.agenda.vote.agenda.domain.Agenda;
import com.agenda.vote.agenda.domain.Vote;
import com.agenda.vote.common.entity.BaseStatus;
import com.agenda.vote.user.domain.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AgendaCreateRequest {

    @NotBlank
    private String title;
    @NotBlank
    private String content;
    private VoteCreateRequest vote;

    public Agenda toEntity(User user) {
        return Agenda.builder()
                .user(user)
                .title(title)
                .content(content)
                .status(BaseStatus.ACTIVE)
                .build();
    }


}

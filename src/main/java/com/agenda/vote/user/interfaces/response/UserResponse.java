package com.agenda.vote.user.interfaces.response;

import com.agenda.vote.user.domain.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserResponse {
    private String email;
    private Integer votingRightCount;
    private String role;

    public UserResponse(User user) {
        this.email = user.getEmail();
        this.votingRightCount = user.getVotingRightCount();
        this.role = user.getRole().getDisplayName();
    }
}

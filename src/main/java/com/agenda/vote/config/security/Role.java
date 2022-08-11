package com.agenda.vote.config.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
    USER("USER", "일반 사용자 권한"),
    ADMIN("ADMIN", "관리자 권한");

    private final String name;
    private final String displayName;

}

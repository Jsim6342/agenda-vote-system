package com.agenda.vote.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    COMMON_NO_REGISTRATION_EXCEPTION("등록되지 않은 에러입니다."),
    COMMON_SYSTEM_ERROR("일시적인 오류가 발생했습니다. 잠시 후 다시 시도해주세요."), // 장애 상황
    COMMON_INVALID_PARAMETER("요청한 값이 올바르지 않습니다."),
    COMMON_ENTITY_NOT_FOUND("존재하지 않는 엔티티입니다."),
    COMMON_ILLEGAL_STATUS("잘못된 상태 값입니다."),
    COMMON_JSON_BAD_GRAMMAR("잘못된 JSON 문법입니다."),

    // USER
    USER_NOT_FOUND("일치하는 회원 정보가 없습니다."),
    USER_PASSWORD_NOT_MATCH("비밀번호가 일치하지 않습니다."),

    // DB
    MULTIPLE_BAG_FETCH("MULTIPLE_BAG_FETCH DB 에러 입니다.");

    private final String errorMsg;

    public String getErrorMsg(Object... arg) {
        return String.format(errorMsg, arg);
    }
}

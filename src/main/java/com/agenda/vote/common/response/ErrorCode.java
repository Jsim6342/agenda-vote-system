package com.agenda.vote.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    COMMON_NO_REGISTRATION_EXCEPTION("등록되지 않은 에러입니다."),

    CLIENT_INVALID_PARAMETER("요청한 값이 올바르지 않습니다."),
    CLIENT_JSON_BAD_GRAMMAR("잘못된 JSON 문법입니다."),

    DB_MULTIPLE_BAG_FETCH("MULTIPLE_BAG_FETCH DB 에러 입니다."),

    AUTH_NOT_MATCH_AUTHORIZATION("권한이 일치하지 않습니다."),
    AUTH_NO_LOGIN_INFO("로그인 정보가 없습니다."),

    USER_NOT_FOUND("일치하는 회원 정보가 없습니다."),
    USER_NOT_MATCH_PASSWORD("비밀번호가 일치하지 않습니다."),
    USER_ALREADY_EMAIL_USED("이미 사용중인 이메일입니다."),

    AGENDA_NOT_FOUND("일치하는 안건 정보가 없습니다."),
    AGENDA_NOT_MATCH_POSTER("작성자와 일치하지 않는 유저입니다."),

    VOTE_NOT_FOUND("일치하는 투표 정보가 없습니다."),
    VOTE_INVALID_DATE("투표 일정 값이 올바르지 않습니다.");



    private final String errorMsg;

    public String getErrorMsg(Object... arg) {
        return String.format(errorMsg, arg);
    }
}

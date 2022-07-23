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
    VOTE_INVALID_DATE("투표 일정 값이 올바르지 않습니다."),
    VOTE_INSUFFICIENT_RIGHT_COUNT("의결권이 부족합니다."),
    VOTE_NOT_MATCH_AGENDA("안건과 일치하는 투표가 아닙니다."),
    VOTE_NOT_MATCH_DATE("투표 일정에 알맞지 않습니다."),
    VOTE_TYPE_INSUFFICIENT_CONDITION("투표 조건에 알맞지 않습니다. 의결권 선착순 제한 투표는 10개로 제한됩니다."),
    VOTE_BAD_REQUEST("잘못된 투표 요청입니다. 요청당 찬성 또는 반대 하나에만 투표해주세요"),
    VOTE_ALREADY_EXIST("이미 생성된 투표가 있습니다."),
    VOTE_ALREADY_START("이미 시작한 투표입니다."),
    VOTE_NOT_ALLOW_DATE("허용되지 않은 투표 일정입니다.");



    private final String errorMsg;

    public String getErrorMsg(Object... arg) {
        return String.format(errorMsg, arg);
    }
}

package com.agenda.vote.common.response;

import com.agenda.vote.user.exception.NoExistUserException;
import com.agenda.vote.user.exception.NoMatchPasswordException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

    /**
     * Common Exception
     */
    // 검증 에러
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResponse methodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("cause = {}, errorMsg = {}", NestedExceptionUtils.getMostSpecificCause(e), NestedExceptionUtils.getMostSpecificCause(e).getMessage());
        return CommonResponse.fail(ErrorCode.COMMON_INVALID_PARAMETER);
    }

    // 잘못된 JSON 문법 요청 에러
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public CommonResponse httpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("cause = {}, errorMsg = {}", NestedExceptionUtils.getMostSpecificCause(e), NestedExceptionUtils.getMostSpecificCause(e).getMessage());
        return CommonResponse.fail(ErrorCode.COMMON_JSON_BAD_GRAMMAR);
    }


    /**
     * Server, DB Exception
     */
    @ExceptionHandler(Exception.class)
    public CommonResponse exception(Exception e) {
        log.error("cause = {}, errorMsg = {}", NestedExceptionUtils.getMostSpecificCause(e), NestedExceptionUtils.getMostSpecificCause(e).getMessage());
        return CommonResponse.fail(ErrorCode.COMMON_NO_REGISTRATION_EXCEPTION);
    }

    // Multiple Bag Fetch 에러
    @ExceptionHandler(InvalidDataAccessApiUsageException.class)
    public CommonResponse invalidDataAccessApiUsageException(InvalidDataAccessApiUsageException e) {
        log.error("cause = {}, errorMsg = {}", NestedExceptionUtils.getMostSpecificCause(e), NestedExceptionUtils.getMostSpecificCause(e).getMessage());
        return CommonResponse.fail(ErrorCode.MULTIPLE_BAG_FETCH);
    }


    /**
     * User Exception
     */
    @ExceptionHandler(NoExistUserException.class)
    public CommonResponse noExistUserException(NoExistUserException e) {
        log.error("cause = {}, errorMsg = {}", NestedExceptionUtils.getMostSpecificCause(e), NestedExceptionUtils.getMostSpecificCause(e).getMessage());
        return CommonResponse.fail(ErrorCode.USER_NOT_FOUND);
    }

    @ExceptionHandler(NoMatchPasswordException.class)
    public CommonResponse noExistUserException(NoMatchPasswordException e) {
        log.error("cause = {}, errorMsg = {}", NestedExceptionUtils.getMostSpecificCause(e), NestedExceptionUtils.getMostSpecificCause(e).getMessage());
        return CommonResponse.fail(ErrorCode.USER_PASSWORD_NOT_MATCH);
    }

}

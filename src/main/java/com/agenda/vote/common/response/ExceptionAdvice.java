package com.agenda.vote.common.response;

import com.agenda.vote.agenda.exception.*;
import com.agenda.vote.common.exception.CertifiedException;
import com.agenda.vote.common.exception.NoAuthorizedException;
import com.agenda.vote.user.exception.AlreadyEmailUsedException;
import com.agenda.vote.user.exception.NoExistUserException;
import com.agenda.vote.user.exception.NotMatchPasswordException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

    /**
     * Client Exception
     */
    // 파라미터 에러
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResponse methodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("cause = {}, errorMsg = {}", NestedExceptionUtils.getMostSpecificCause(e), NestedExceptionUtils.getMostSpecificCause(e).getMessage());
        return CommonResponse.fail(ErrorCode.CLIENT_INVALID_PARAMETER);
    }

    // 잘못된 JSON 문법 요청 에러
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public CommonResponse httpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("cause = {}, errorMsg = {}", NestedExceptionUtils.getMostSpecificCause(e), NestedExceptionUtils.getMostSpecificCause(e).getMessage());
        return CommonResponse.fail(ErrorCode.CLIENT_JSON_BAD_GRAMMAR);
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
        return CommonResponse.fail(ErrorCode.DB_MULTIPLE_BAG_FETCH);
    }

    /**
     * Auth Exception
     */
    @ExceptionHandler(NoAuthorizedException.class)
    public CommonResponse noAuthorizedException(NoAuthorizedException e) {
        log.error("cause = {}, errorMsg = {}", NestedExceptionUtils.getMostSpecificCause(e), NestedExceptionUtils.getMostSpecificCause(e).getMessage());
        return CommonResponse.fail(ErrorCode.AUTH_NOT_MATCH_AUTHORIZATION);
    }

    @ExceptionHandler(CertifiedException.class)
    public CommonResponse certifiedException(CertifiedException e) {
        log.error("cause = {}, errorMsg = {}", NestedExceptionUtils.getMostSpecificCause(e), NestedExceptionUtils.getMostSpecificCause(e).getMessage());
        return CommonResponse.fail(ErrorCode.AUTH_NO_LOGIN_INFO);
    }



    /**
     * User Exception
     */
    @ExceptionHandler(NoExistUserException.class)
    public CommonResponse noExistUserException(NoExistUserException e) {
        log.error("cause = {}, errorMsg = {}", NestedExceptionUtils.getMostSpecificCause(e), NestedExceptionUtils.getMostSpecificCause(e).getMessage());
        return CommonResponse.fail(ErrorCode.USER_NOT_FOUND);
    }

    @ExceptionHandler(NotMatchPasswordException.class)
    public CommonResponse noExistUserException(NotMatchPasswordException e) {
        log.error("cause = {}, errorMsg = {}", NestedExceptionUtils.getMostSpecificCause(e), NestedExceptionUtils.getMostSpecificCause(e).getMessage());
        return CommonResponse.fail(ErrorCode.USER_NOT_MATCH_PASSWORD);
    }

    @ExceptionHandler(AlreadyEmailUsedException.class)
    public CommonResponse alreadyEmailUsedException(AlreadyEmailUsedException e) {
        log.error("cause = {}, errorMsg = {}", NestedExceptionUtils.getMostSpecificCause(e), NestedExceptionUtils.getMostSpecificCause(e).getMessage());
        return CommonResponse.fail(ErrorCode.USER_ALREADY_EMAIL_USED);
    }


    /**
     * Agenda Exception
     */
    @ExceptionHandler(NoExistAgendaException.class)
    public CommonResponse noExistAgendaException(NoExistAgendaException e) {
        log.error("cause = {}, errorMsg = {}", NestedExceptionUtils.getMostSpecificCause(e), NestedExceptionUtils.getMostSpecificCause(e).getMessage());
        return CommonResponse.fail(ErrorCode.AGENDA_NOT_FOUND);
    }

    @ExceptionHandler(NotMatchPosterException.class)
    public CommonResponse notMatchPosterException(NotMatchPosterException e) {
        log.error("cause = {}, errorMsg = {}", NestedExceptionUtils.getMostSpecificCause(e), NestedExceptionUtils.getMostSpecificCause(e).getMessage());
        return CommonResponse.fail(ErrorCode.AGENDA_NOT_MATCH_POSTER);
    }

    /**
     * Vote Exception
     */
    @ExceptionHandler(NoExistVoteException.class)
    public CommonResponse noExistVoteException(NoExistVoteException e) {
        log.error("cause = {}, errorMsg = {}", NestedExceptionUtils.getMostSpecificCause(e), NestedExceptionUtils.getMostSpecificCause(e).getMessage());
        return CommonResponse.fail(ErrorCode.VOTE_NOT_FOUND);
    }

    @ExceptionHandler(VoteDateException.class)
    public CommonResponse voteDateException(VoteDateException e) {
        log.error("cause = {}, errorMsg = {}", NestedExceptionUtils.getMostSpecificCause(e), NestedExceptionUtils.getMostSpecificCause(e).getMessage());
        return CommonResponse.fail(ErrorCode.VOTE_INVALID_DATE);
    }

    @ExceptionHandler(InsufficientRightCountException.class)
    public CommonResponse insufficientRightCountException(InsufficientRightCountException e) {
        log.error("cause = {}, errorMsg = {}", NestedExceptionUtils.getMostSpecificCause(e), NestedExceptionUtils.getMostSpecificCause(e).getMessage());
        return CommonResponse.fail(ErrorCode.VOTE_INSUFFICIENT_RIGHT_COUNT);
    }

    @ExceptionHandler(NotMatchPostException.class)
    public CommonResponse notMatchPostException(NotMatchPostException e) {
        log.error("cause = {}, errorMsg = {}", NestedExceptionUtils.getMostSpecificCause(e), NestedExceptionUtils.getMostSpecificCause(e).getMessage());
        return CommonResponse.fail(ErrorCode.VOTE_NOT_MATCH_AGENDA);
    }

    @ExceptionHandler(BadVotingRequestException.class)
    public CommonResponse badVotingRequestException(BadVotingRequestException e) {
        log.error("cause = {}, errorMsg = {}", NestedExceptionUtils.getMostSpecificCause(e), NestedExceptionUtils.getMostSpecificCause(e).getMessage());
        return CommonResponse.fail(ErrorCode.VOTE_BAD_REQUEST);
    }

    @ExceptionHandler(AlreadyExistVoteException.class)
    public CommonResponse alreadyExistVoteException(AlreadyExistVoteException e) {
        log.error("cause = {}, errorMsg = {}", NestedExceptionUtils.getMostSpecificCause(e), NestedExceptionUtils.getMostSpecificCause(e).getMessage());
        return CommonResponse.fail(ErrorCode.VOTE_ALREADY_EXIST);
    }

    @ExceptionHandler(NotMatchDateException.class)
    public CommonResponse notMatchDateException(NotMatchDateException e) {
        log.error("cause = {}, errorMsg = {}", NestedExceptionUtils.getMostSpecificCause(e), NestedExceptionUtils.getMostSpecificCause(e).getMessage());
        return CommonResponse.fail(ErrorCode.VOTE_INVALID_DATE);
    }

    @ExceptionHandler(AlreadyStartVoteException.class)
    public CommonResponse alreadyStartVoteException(AlreadyStartVoteException e) {
        log.error("cause = {}, errorMsg = {}", NestedExceptionUtils.getMostSpecificCause(e), NestedExceptionUtils.getMostSpecificCause(e).getMessage());
        return CommonResponse.fail(ErrorCode.VOTE_ALREADY_START);
    }

    @ExceptionHandler(TypeInsufficientConditionException.class)
    public CommonResponse typeInsufficientConditionException(TypeInsufficientConditionException e) {
        log.error("cause = {}, errorMsg = {}", NestedExceptionUtils.getMostSpecificCause(e), NestedExceptionUtils.getMostSpecificCause(e).getMessage());
        return CommonResponse.fail(ErrorCode.VOTE_TYPE_INSUFFICIENT_CONDITION);
    }

    @ExceptionHandler(NotAllowDateException.class)
    public CommonResponse notAllowDateException(NotAllowDateException e) {
        log.error("cause = {}, errorMsg = {}", NestedExceptionUtils.getMostSpecificCause(e), NestedExceptionUtils.getMostSpecificCause(e).getMessage());
        return CommonResponse.fail(ErrorCode.VOTE_NOT_ALLOW_DATE);
    }






}

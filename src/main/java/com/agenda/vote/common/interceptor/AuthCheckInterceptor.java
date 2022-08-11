package com.agenda.vote.common.interceptor;


import com.agenda.vote.common.annotation.Admin;
import com.agenda.vote.common.annotation.Guest;
import com.agenda.vote.common.exception.CertifiedException;
import com.agenda.vote.common.exception.NoAuthorizedException;
import com.agenda.vote.config.security.JwtService;
import com.agenda.vote.user.infrastructure.read.UserReaderImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

import static com.agenda.vote.common.response.ErrorCode.AUTH_NOT_MATCH_AUTHORIZATION;
import static com.agenda.vote.common.response.ErrorCode.AUTH_NO_LOGIN_INFO;


@Component
@RequiredArgsConstructor
public class AuthCheckInterceptor implements HandlerInterceptor {

    private final JwtService jwtService;
    private final UserReaderImpl userReader;

    /**
     * Admin: 관리자 페이지
     * guest: 비인가 페이지
     * 어노테이션이 붙지 않은 페이지는 모두 일반 회원 인가 페이지
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        // Preflight Request 체크
        if (isPreflightRequest(request)) return true;

        // 비로그인 인가 처리. 비회원은 userId를 0으로 배정
        if(request.getHeader("X-ACCESS-TOKEN")==null) {
            if(checkAnnotation(handler, Guest.class)) {
                request.setAttribute("userId", 0L);
                return true;
            }
            throw new CertifiedException(AUTH_NO_LOGIN_INFO.getErrorMsg());
        }

        // 로그인 인가 처리
        request.setAttribute("userId", jwtService.getUserId());

        // 관리자 페이지
        if(checkAnnotation(handler, Admin.class)) {
            if(userReader.checkAdmin(jwtService.getUserId())) return true;
            throw new NoAuthorizedException(AUTH_NOT_MATCH_AUTHORIZATION.getErrorMsg());
        }

        return true;
    }

    private boolean checkAnnotation(Object handler, Class clazz) {

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        if(handlerMethod.getMethodAnnotation(clazz) != null || handlerMethod.getMethodAnnotation(clazz) != null) {
            return true;
        }
        return false;
    }

    private boolean isPreflightRequest(HttpServletRequest request) {
        return isOptions(request) && hasHeaders(request) && hasMethod(request) && hasOrigin(request);
    }

    private boolean isOptions(HttpServletRequest request) {
        return request.getMethod().equalsIgnoreCase(HttpMethod.OPTIONS.toString());
    }

    private boolean hasHeaders(HttpServletRequest request) {
        return Objects.nonNull(request.getHeader("Access-Control-Request-Headers"));
    }

    private boolean hasMethod(HttpServletRequest request) {
        return Objects.nonNull(request.getHeader("Access-Control-Request-Method"));
    }

    private boolean hasOrigin(HttpServletRequest request) {
        return Objects.nonNull(request.getHeader("Origin"));
    }


}

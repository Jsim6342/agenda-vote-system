package com.agenda.vote.user.interfaces;

import com.agenda.vote.common.annotation.Guest;
import com.agenda.vote.common.response.CommonResponse;
import com.agenda.vote.user.facade.UserFacade;
import com.agenda.vote.user.interfaces.request.LoginRequest;
import com.agenda.vote.user.interfaces.request.RegisterRequest;
import com.agenda.vote.user.interfaces.request.UserUpdateRequest;
import com.agenda.vote.user.interfaces.response.RegisterResponse;
import com.agenda.vote.user.interfaces.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserApiController {

    private final UserFacade userFacade;

    /**
     * 유저 등록 API
     * [POST] /api/v1/users
     */
    @Guest
    @PostMapping
    public CommonResponse<RegisterResponse> registerUser(@Validated @RequestBody RegisterRequest registerRequest) {
        return CommonResponse.success(userFacade.registerUser(registerRequest));
    }

    /**
     * 로그인 API
     * [POST] /api/v1/users/login
     */
    @Guest
    @PostMapping("/login")
    public CommonResponse<String> login(@RequestBody LoginRequest loginRequest) {
        return CommonResponse.success(userFacade.login(loginRequest));
    }

    /**
     * 유저 조회 API
     * [GET] /api/v1/users
     */
    @GetMapping
    public CommonResponse<UserResponse> getUserResponse(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return CommonResponse.success(userFacade.getUserResponse(userId));
    }

    /**
     * 유저 수정 API
     * [PATCH] /api/v1/users
     */
    @PatchMapping
    public CommonResponse<HttpStatus> updateUser(@Validated @RequestBody UserUpdateRequest userUpdateRequest,
                                                 HttpServletRequest request) {
        Long userId = (Long)request.getAttribute("userId");
        userFacade.updateUser(userId, userUpdateRequest);
        return CommonResponse.success(HttpStatus.OK);
    }

    /**
     * 유저 삭제 API
     * [DELETE] /api/v1/users
     */
    @DeleteMapping
    public CommonResponse<HttpStatus> deleteUser(HttpServletRequest request) {
        Long userId = (Long)request.getAttribute("userId");
        userFacade.deleteUser(userId);
        return CommonResponse.success(HttpStatus.OK);
    }



}

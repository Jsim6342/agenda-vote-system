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

    @Guest
    @PostMapping
    public CommonResponse<RegisterResponse> registerUser(@Validated @RequestBody RegisterRequest registerRequest) {
        return CommonResponse.success(userFacade.registerUser(registerRequest));
    }

    @Guest
    @PostMapping("/login")
    public CommonResponse<String> login(@RequestBody LoginRequest loginRequest) {
        return CommonResponse.success(userFacade.login(loginRequest));
    }

    @GetMapping
    public CommonResponse<UserResponse> getUserResponse(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return CommonResponse.success(userFacade.getUserResponse(userId));
    }

    @PatchMapping
    public CommonResponse<HttpStatus> updateUser(@Validated @RequestBody UserUpdateRequest userUpdateRequest,
                                                 HttpServletRequest request) {
        Long userId = (Long)request.getAttribute("userId");
        userFacade.updateUser(userId, userUpdateRequest);
        return CommonResponse.success(HttpStatus.OK);
    }

    @DeleteMapping
    public CommonResponse<HttpStatus> deleteUser(HttpServletRequest request) {
        Long userId = (Long)request.getAttribute("userId");
        userFacade.deleteUser(userId);
        return CommonResponse.success(HttpStatus.OK);
    }



}

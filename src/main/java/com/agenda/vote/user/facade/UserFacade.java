package com.agenda.vote.user.facade;

import com.agenda.vote.config.security.JwtService;
import com.agenda.vote.user.domain.User;
import com.agenda.vote.user.domain.user.interfaces.UserService;
import com.agenda.vote.user.interfaces.request.LoginRequest;
import com.agenda.vote.user.interfaces.request.RegisterRequest;
import com.agenda.vote.user.interfaces.request.UserUpdateRequest;
import com.agenda.vote.user.interfaces.response.RegisterResponse;
import com.agenda.vote.user.interfaces.response.UserInfoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserFacade {

    private final UserService userService;
    private final JwtService jwtService;


    @Transactional
    public RegisterResponse registerUser(RegisterRequest registerRequest) {
        User user = userService.registerUser(registerRequest);
        String jwt = jwtService.createJwt(user.getId());
        return new RegisterResponse(user.getId(), jwt);
    }

    @Transactional
    public String login(LoginRequest loginRequest) {
        User user = userService.login(loginRequest);
        return jwtService.createJwt(user.getId());
    }

    @Transactional
    public UserInfoResponse getUser(Long userId) {
        return userService.getUser(userId);
    }

    @Transactional
    public void updateUser(Long userId, UserUpdateRequest userUpdateRequest) {
        userService.updateUser(userId, userUpdateRequest);
    }

    @Transactional
    public void deleteUser(Long userId) {
        userService.deleteUser(userId);
    }
}

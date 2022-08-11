package com.agenda.vote.user.facade;

import com.agenda.vote.common.response.ErrorCode;
import com.agenda.vote.config.security.JwtService;
import com.agenda.vote.user.domain.User;
import com.agenda.vote.user.domain.user.interfaces.UserService;
import com.agenda.vote.user.exception.AlreadyEmailUsedException;
import com.agenda.vote.user.exception.NoExistUserException;
import com.agenda.vote.user.interfaces.request.LoginRequest;
import com.agenda.vote.user.interfaces.request.RegisterRequest;
import com.agenda.vote.user.interfaces.request.UserUpdateRequest;
import com.agenda.vote.user.interfaces.response.RegisterResponse;
import com.agenda.vote.user.interfaces.response.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static com.agenda.vote.common.response.ErrorCode.USER_ALREADY_EMAIL_USED;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserFacade {

    private final UserService userService;
    private final JwtService jwtService;


    @Transactional
    public RegisterResponse registerUser(RegisterRequest registerRequest) {
        User findUser;

        try{
            findUser = userService.getUser(registerRequest.getEmail());
        }catch (NoExistUserException e) {
            findUser = null;
        }

        if (findUser != null) {
            throw new AlreadyEmailUsedException(USER_ALREADY_EMAIL_USED.getErrorMsg());
        }

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
    public UserResponse getUserResponse(Long userId) {
        return userService.getUserResponse(userId);
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

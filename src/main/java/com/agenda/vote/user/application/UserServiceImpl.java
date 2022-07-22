package com.agenda.vote.user.application;


import com.agenda.vote.config.security.certification.SHA256;
import com.agenda.vote.user.domain.User;
import com.agenda.vote.user.domain.user.interfaces.UserReader;
import com.agenda.vote.user.domain.user.interfaces.UserService;
import com.agenda.vote.user.domain.user.interfaces.UserStore;
import com.agenda.vote.user.exception.NotMatchPasswordException;
import com.agenda.vote.user.interfaces.request.LoginRequest;
import com.agenda.vote.user.interfaces.request.RegisterRequest;
import com.agenda.vote.user.interfaces.request.UserUpdateRequest;
import com.agenda.vote.user.interfaces.response.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.agenda.vote.common.response.ErrorCode.USER_NOT_MATCH_PASSWORD;
import static com.agenda.vote.config.security.certification.Secret.USER_INFO_PASSWORD_KEY;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserStore userStore;
    private final UserReader userReader;

    @Override
    public User registerUser(RegisterRequest registerRequest) {
        String encryptPassword = SHA256.encrypt(registerRequest.getPassword() + USER_INFO_PASSWORD_KEY);
        registerRequest.setEncryptPassword(encryptPassword);

        return userStore.saveUser(registerRequest.toEntity());
    }

    @Override
    public User login(LoginRequest loginRequest) {
        User user = userReader.selectUserByEmail(loginRequest.getEmail());

        String loginPassword = SHA256.encrypt(loginRequest.getPassword() + USER_INFO_PASSWORD_KEY);
        if (!user.getPassword().equals(loginPassword)) {
            throw new NotMatchPasswordException(USER_NOT_MATCH_PASSWORD.getErrorMsg());
        }

        return user;
    }

    @Override
    public User getUser(Long userId) {
        return userReader.selectUser(userId);
    }

    @Override
    public User getUser(String email) {
        return userReader.selectUserByEmail(email);
    }

    @Override
    public UserResponse getUserResponse(Long userId) {
        User user = userReader.selectUser(userId);
        return new UserResponse(user);
    }

    @Override
    public void updateUser(Long userId, UserUpdateRequest userUpdateRequest) {

        String encryptPassword = SHA256.encrypt(userUpdateRequest.getPassword() + USER_INFO_PASSWORD_KEY);
        userUpdateRequest.setEncryptPassword(encryptPassword);

        userStore.updateUser(userId, userUpdateRequest.toEntity());
    }

    @Override
    public void deleteUser(Long userId) {
        userStore.deleteUser(userId);
    }
}

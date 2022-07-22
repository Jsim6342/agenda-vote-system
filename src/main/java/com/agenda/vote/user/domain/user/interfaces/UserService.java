package com.agenda.vote.user.domain.user.interfaces;

import com.agenda.vote.user.domain.User;
import com.agenda.vote.user.interfaces.request.LoginRequest;
import com.agenda.vote.user.interfaces.request.RegisterRequest;
import com.agenda.vote.user.interfaces.request.UserUpdateRequest;
import com.agenda.vote.user.interfaces.response.RegisterResponse;
import com.agenda.vote.user.interfaces.response.UserInfoResponse;

public interface UserService {
    User registerUser(RegisterRequest registerRequest);
    User login(LoginRequest loginRequest);
    UserInfoResponse getUser(Long userId);
    void updateUser(Long userId, UserUpdateRequest userUpdateRequest);

    void deleteUser(Long userId);
}

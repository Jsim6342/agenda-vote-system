package com.agenda.vote.user.domain.user.interfaces;

import com.agenda.vote.user.domain.User;
import com.agenda.vote.user.interfaces.request.LoginRequest;
import com.agenda.vote.user.interfaces.request.RegisterRequest;
import com.agenda.vote.user.interfaces.request.UserUpdateRequest;
import com.agenda.vote.user.interfaces.response.UserResponse;

public interface UserService {
    User registerUser(RegisterRequest registerRequest);
    User login(LoginRequest loginRequest);
    User getUser(Long userId);
    User getUser(String email);
    UserResponse getUserResponse(Long userId);
    void updateUser(Long userId, UserUpdateRequest userUpdateRequest);

    void deleteUser(Long userId);
}

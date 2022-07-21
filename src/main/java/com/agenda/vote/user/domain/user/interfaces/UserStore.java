package com.agenda.vote.user.domain.user.interfaces;

import com.agenda.vote.user.domain.User;

import java.util.Optional;

public interface UserStore {

    User saveUser(User user);
    User updateUser(Long userId, User updateUser);
    User deleteUser(Long userId);

}

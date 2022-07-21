package com.agenda.vote.user.domain.user.interfaces;

import com.agenda.vote.user.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserReader {

    List<User> selectUserList();
    User selectUser(Long userId);

}

package com.agenda.vote.user.infrastructure.store;

import com.agenda.vote.common.response.ErrorCode;
import com.agenda.vote.user.domain.User;
import com.agenda.vote.user.domain.user.interfaces.UserStore;
import com.agenda.vote.user.exception.NoExistUserException;
import com.agenda.vote.user.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.agenda.vote.common.entity.BaseStatus.ACTIVE;
import static com.agenda.vote.common.response.ErrorCode.USER_NOT_FOUND;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserStoreImpl implements UserStore {
    private final UserRepository userRepository;

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long userId, User updateUser) {
        User existUser = userRepository.findByIdAndStatus(userId, ACTIVE)
                .orElseThrow(() -> new NoExistUserException(USER_NOT_FOUND.getErrorMsg()));
        existUser.update(updateUser);
        return existUser;
    }

    @Override
    public User deleteUser(Long userId) {
        User user = userRepository.findByIdAndStatus(userId, ACTIVE)
                .orElseThrow(() -> new NoExistUserException(USER_NOT_FOUND.getErrorMsg()));
        user.updateOffStatus();
        return user;
    }
}

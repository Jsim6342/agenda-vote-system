package com.agenda.vote.user.infrastructure.store;

import com.agenda.vote.user.domain.User;
import com.agenda.vote.user.domain.user.interfaces.UserStore;
import com.agenda.vote.user.exception.NoExistUserException;
import com.agenda.vote.user.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.agenda.vote.common.BaseStatus.ACTIVE;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserStoreImpl implements UserStore {
    private final UserRepository userRepository;

    @Override
    public User saveUser(User user) {
        // TODO 로직 추가
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long userId, User updateUser) {
        // TODO 로직 추가
        User existUser = userRepository.findByIdAndStatus(userId, ACTIVE)
                .orElseThrow(() -> new NoExistUserException("일치하는 회원 정보가 없습니다."));
        existUser.update(updateUser);
        return existUser;
    }

    @Override
    public User deleteUser(Long userId) {
        // TODO 로직 추가
        User user = userRepository.findByIdAndStatus(userId, ACTIVE)
                .orElseThrow(() -> new NoExistUserException("일치하는 회원 정보가 없습니다."));
        user.updateOffStatus();
        return user;
    }
}

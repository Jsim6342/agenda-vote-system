package com.agenda.vote.user.infrastructure.read;


import com.agenda.vote.config.security.Role;
import com.agenda.vote.user.domain.User;
import com.agenda.vote.user.domain.user.interfaces.UserReader;
import com.agenda.vote.user.exception.NoExistUserException;
import com.agenda.vote.user.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static com.agenda.vote.common.entity.BaseStatus.ACTIVE;
import static com.agenda.vote.common.response.ErrorCode.USER_NOT_FOUND;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserReaderImpl implements UserReader {
    private final UserRepository userRepository;

    @Override
    public List<User> selectUserList() {
        return userRepository.findAllByStatus(ACTIVE)
                .orElse(null);
    }

    @Override
    public User selectUser(Long userId) {
        return userRepository.findByIdAndStatus(userId, ACTIVE)
                .orElseThrow(() -> new NoExistUserException(USER_NOT_FOUND.getErrorMsg()));
    }

    @Override
    public User selectUserByEmail(String email) {
        return userRepository.findByEmailAndStatus(email, ACTIVE)
                .orElseThrow(() -> new NoExistUserException(USER_NOT_FOUND.getErrorMsg()));
    }

    @Override
    public boolean checkAdmin(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoExistUserException(USER_NOT_FOUND.getErrorMsg()));

        return user.getRole().getName().equals(Role.ADMIN.getName());
    }
}

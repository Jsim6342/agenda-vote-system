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

@Slf4j
@Component
@RequiredArgsConstructor
public class UserReaderImpl implements UserReader {
    private final UserRepository userRepository;

    @Override
    public List<User> selectUserList() {
        // TODO 로직 추가
        return userRepository.findAllByStatus(ACTIVE)
                .orElse(null);
    }

    @Override
    public User selectUser(Long userId) {
        // TODO 로직 추가
        return userRepository.findByIdAndStatus(userId, ACTIVE)
                .orElseThrow(() -> new NoExistUserException("일치하는 회원 정보가 없습니다."));

    }

    @Override
    public boolean checkAdmin(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoExistUserException("로그인 정보와 일치하는 회원 정보가 없습니다."));

        return user.getRole().getName().equals(Role.ADMIN.getName());
    }
}

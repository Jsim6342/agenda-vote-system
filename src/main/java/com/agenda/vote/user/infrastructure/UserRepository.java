package com.agenda.vote.user.infrastructure;

import com.agenda.vote.common.entity.BaseStatus;
import com.agenda.vote.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<List<User>> findAllByStatus(BaseStatus status);
    Optional<User> findByIdAndStatus(Long id, BaseStatus status);

}

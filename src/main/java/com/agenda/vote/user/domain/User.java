package com.agenda.vote.user.domain;

import com.agenda.vote.common.entity.AbstractEntity;
import com.agenda.vote.common.entity.BaseStatus;
import com.agenda.vote.config.security.Role;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private Long id;

    private String email;

    private String password;

    private Integer votingRightCount;

    private boolean isOAuth;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private BaseStatus status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return isOAuth == user.isOAuth && Objects.equals(id, user.id) && Objects.equals(email, user.email) && Objects.equals(password, user.password) && Objects.equals(votingRightCount, user.votingRightCount) && status == user.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password, votingRightCount, isOAuth, status);
    }

    @Builder
    public User(Long id, String email, String password, Integer votingRightCount, boolean isOAuth, Role role, BaseStatus status) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.votingRightCount = votingRightCount;
        this.isOAuth = isOAuth;
        this.role = role;
        this.status = status;
    }


    public void update(User user) {
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.votingRightCount = user.getVotingRightCount();
    }

    public void updateOnStatus() {
        this.status = BaseStatus.ACTIVE;
    }

    public void updateOffStatus() {
        this.status = BaseStatus.INACTIVE;
    }
}

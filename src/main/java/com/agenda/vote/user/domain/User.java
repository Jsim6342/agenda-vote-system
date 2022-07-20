package com.agenda.vote.user.domain;

import com.agenda.vote.common.AbstractEntity;
import com.agenda.vote.common.BaseStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
    private BaseStatus status;


}

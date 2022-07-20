package com.agenda.vote.agenda.domain;

import com.agenda.vote.common.AbstractEntity;
import com.agenda.vote.common.BaseStatus;
import com.agenda.vote.common.Opinion;
import com.agenda.vote.user.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Voting extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "votingId")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "voteId")
    private Vote vote;

    @Enumerated(EnumType.STRING)
    private Opinion opinion;

    private Integer votingCount;

    @Enumerated(EnumType.STRING)
    private BaseStatus status;


}

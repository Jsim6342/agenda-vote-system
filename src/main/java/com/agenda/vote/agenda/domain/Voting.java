package com.agenda.vote.agenda.domain;

import com.agenda.vote.common.entity.AbstractEntity;
import com.agenda.vote.common.entity.BaseStatus;
import com.agenda.vote.common.entity.Opinion;
import com.agenda.vote.user.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Voting)) return false;
        Voting voting = (Voting) o;
        return Objects.equals(id, voting.id) && Objects.equals(user, voting.user) && Objects.equals(vote, voting.vote) && opinion == voting.opinion && Objects.equals(votingCount, voting.votingCount) && status == voting.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, vote, opinion, votingCount, status);
    }

    public void updateOnStatus() {
        this.status = BaseStatus.ACTIVE;
    }

    public void updateOffStatus() {
        this.status = BaseStatus.INACTIVE;
    }
}

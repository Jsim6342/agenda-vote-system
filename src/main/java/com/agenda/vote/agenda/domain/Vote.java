package com.agenda.vote.agenda.domain;

import com.agenda.vote.common.AbstractEntity;
import com.agenda.vote.common.BaseStatus;
import com.agenda.vote.common.VoteType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Vote extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "voteId")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agendaId")
    private Agenda agenda;

    @Enumerated(EnumType.STRING)
    private VoteType type;

    private Long agreeCount;

    private Long disagreeCount;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    private BaseStatus status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vote)) return false;
        Vote vote = (Vote) o;
        return Objects.equals(id, vote.id) && Objects.equals(agenda, vote.agenda) && type == vote.type && Objects.equals(agreeCount, vote.agreeCount) && Objects.equals(disagreeCount, vote.disagreeCount) && Objects.equals(startDate, vote.startDate) && Objects.equals(endDate, vote.endDate) && status == vote.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, agenda, type, agreeCount, disagreeCount, startDate, endDate, status);
    }

    public void updateOnStatus() {
        this.status = BaseStatus.ACTIVE;
    }

    public void updateOffStatus() {
        this.status = BaseStatus.INACTIVE;
    }
}

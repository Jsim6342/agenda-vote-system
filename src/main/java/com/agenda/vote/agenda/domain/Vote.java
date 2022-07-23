package com.agenda.vote.agenda.domain;

import com.agenda.vote.agenda.interfaces.request.VoteCreateRequest;
import com.agenda.vote.agenda.interfaces.request.VoteUpdateRequest;
import com.agenda.vote.agenda.interfaces.request.VotingRequest;
import com.agenda.vote.common.entity.AbstractEntity;
import com.agenda.vote.common.entity.BaseStatus;
import com.agenda.vote.common.entity.VoteType;
import lombok.AccessLevel;
import lombok.Builder;
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

    @Builder
    public Vote(Long id, Agenda agenda, VoteType type, Long agreeCount, Long disagreeCount, LocalDateTime startDate, LocalDateTime endDate, BaseStatus status) {
        this.id = id;
        this.agenda = agenda;
        this.type = type;
        this.agreeCount = agreeCount;
        this.disagreeCount = disagreeCount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    public void update(VoteUpdateRequest voteUpdateRequest) {
        startDate = voteUpdateRequest.getStartDate();
        endDate = voteUpdateRequest.getEndDate();
    }

    public void updateExistVote(VoteCreateRequest voteCreateRequest) {
        type = voteCreateRequest.getType();
        startDate = voteCreateRequest.getStartDate();
        endDate = voteCreateRequest.getEndDate();
        status = BaseStatus.ACTIVE;
    }

    public void voting(VotingRequest votingRequest) {
        agreeCount += votingRequest.getAgreeCount();
        disagreeCount += votingRequest.getDisagreeCount();
    }

    public void updateOnStatus() {
        this.status = BaseStatus.ACTIVE;
    }

    public void updateOffStatus() {
        this.status = BaseStatus.INACTIVE;
    }



}

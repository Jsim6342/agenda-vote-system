package com.agenda.vote.agenda.domain;

import com.agenda.vote.common.AbstractEntity;
import com.agenda.vote.common.BaseStatus;
import com.agenda.vote.user.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Agenda extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "agendaId")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    private String title;

    private String content;

    @Enumerated(EnumType.STRING)
    private BaseStatus status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Agenda)) return false;
        Agenda agenda = (Agenda) o;
        return id.equals(agenda.id) && user.equals(agenda.user) && title.equals(agenda.title) && content.equals(agenda.content) && status == agenda.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, title, content, status);
    }

    public void updateOnStatus() {
        this.status = BaseStatus.ACTIVE;
    }

    public void updateOffStatus() {
        this.status = BaseStatus.INACTIVE;
    }
}

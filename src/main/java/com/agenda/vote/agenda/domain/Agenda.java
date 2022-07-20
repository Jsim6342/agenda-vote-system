package com.agenda.vote.agenda.domain;

import com.agenda.vote.common.AbstractEntity;
import com.agenda.vote.common.BaseStatus;
import com.agenda.vote.user.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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


}

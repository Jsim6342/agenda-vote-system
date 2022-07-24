package com.agenda.vote.agenda.infrastructure;

import com.agenda.vote.agenda.interfaces.request.VoteSearchFilter;
import com.agenda.vote.agenda.interfaces.response.AgendaSearchResponse;
import com.agenda.vote.user.domain.User;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static com.agenda.vote.agenda.domain.QAgenda.*;
import static com.agenda.vote.agenda.domain.QVote.*;
import static com.agenda.vote.common.entity.BaseStatus.ACTIVE;

public class AgendaRepositoryImpl implements AgendaRepositoryQueryDsl {

    private final JPAQueryFactory queryFactory;

    public AgendaRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<AgendaSearchResponse> searchAgendaResponses(User nowUser, VoteSearchFilter voteSearchFilter) {

    return queryFactory
            .select(Projections.fields(AgendaSearchResponse.class,
                    agenda.id.as("agendaId"),
                    agenda.title,
                    agenda.content,
                    agenda.created,
                    agenda.updated))
            .from(vote)
            .leftJoin(vote.agenda, agenda)
            .where(
                    userEq(nowUser),
                    startDateGoe(voteSearchFilter.getStartDate()),
                    endDateLoe(voteSearchFilter.getEndDate()),
                    agenda.status.eq(ACTIVE))
            .fetch();
    }

    public BooleanExpression userEq(User nowUser) {
        return nowUser == null ? null : agenda.user.eq(nowUser);
    }

    public BooleanExpression startDateGoe(LocalDateTime startDate) {
        return startDate == null ? null : vote.startDate.after(startDate);
    }

    public BooleanExpression endDateLoe(LocalDateTime endDate) {
        return endDate == null ? null : vote.endDate.before(endDate);
    }



}

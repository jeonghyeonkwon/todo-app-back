package com.givejeong.todo.repository.querydsl;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

import static com.givejeong.todo.domain.QProgrammingRole.*;

public class ProgrammingRoleRepositoryImpl implements ProgrammingRoleRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;

    public ProgrammingRoleRepositoryImpl(EntityManager em){
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }

    @Override
    public long studyTotalCount() {
        return jpaQueryFactory.selectFrom(programmingRole).where(programmingRole.study.isNotNull()).fetchCount();
    }

    @Override
    public List<Tuple> studyTop5Rank() {
        return jpaQueryFactory
                .select(programmingRole.programmingEnum, programmingRole.count())
                .from(programmingRole)
                .where(programmingRole.study.isNotNull())
                .groupBy(programmingRole.programmingEnum).orderBy(programmingRole.programmingEnum.count().desc()).limit(5).fetch();
    }

    @Override
    public long qnaTotalCount() {
        return jpaQueryFactory.selectFrom(programmingRole).where(programmingRole.qna.isNotNull()).fetchCount();
    }

    @Override
    public List<Tuple> qnaTop5Rank() {
        return jpaQueryFactory
                .select(programmingRole.programmingEnum, programmingRole.count())
                .from(programmingRole)
                .where(programmingRole.qna.isNotNull())
                .groupBy(programmingRole.programmingEnum).orderBy(programmingRole.programmingEnum.count().desc()).limit(5).fetch();
    }

    @Override
    public long totalCount() {
        return jpaQueryFactory.selectFrom(programmingRole).fetchCount();
    }

    @Override
    public List<Tuple> Top5Rank() {
        return jpaQueryFactory.select(programmingRole.programmingEnum,programmingRole.count())
                .from(programmingRole).groupBy(programmingRole.programmingEnum).orderBy(programmingRole.programmingEnum.count().desc()).limit(5).fetch();
    }
}

package com.givejeong.todo.repository.querydsl;

import com.givejeong.todo.domain.*;
import com.givejeong.todo.dto.board.study.StudyDto;
import com.givejeong.todo.dto.board.study.StudyListDto;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

import static com.givejeong.todo.domain.QAccount.account;
import static com.givejeong.todo.domain.QStudy.*;

public class StudyRepositoryImpl implements StudyRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;

    public StudyRepositoryImpl(EntityManager em){
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }

    public Optional<StudyDto> findByIdx(Long id){
        return Optional.ofNullable(
                jpaQueryFactory
                        .select(
                                Projections
                                        .constructor(StudyDto.class
                                                ,study.id
                                                ,study.title
                                                ,account.accountId
                                                ,study.content
                                                ,study.applicant
                                                ,study.studyStatusEnum
                                                ,study.hit
                                                ,study.createdDate))
                        .from(study)
                .join(study.account,account)
                .where(study.id.eq(id)).fetchOne()
        );
    }

    @Override
    public Page<StudyListDto> studyListDtos(String section, LocalEnum localEnum, Pageable pageable) {
        List<StudyListDto> content = jpaQueryFactory.select(Projections.constructor(StudyListDto.class, study.id, study.title, account.accountId, study.studyStatusEnum, study.applicant, study.hit, study.createdDate))
                .from(study)
                .join(study.account, account)
                .where(sectionEq(section),
                        localEq(localEnum))

                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(study.createdDate.desc()).fetch();
        JPAQuery<Study> countQuery = jpaQueryFactory.selectFrom(study).where(sectionEq(section),
                localEq(localEnum));
        return PageableExecutionUtils.getPage(content,pageable,countQuery::fetchCount);
    }
    //section : web,programming,db,mobile
    private BooleanExpression sectionEq(String section){
        return study.fieldEnum.eq(FieldEnum.find(section));
    }
    // ALL,DAGUE...
    private BooleanExpression localEq(LocalEnum localEnum){
        return localEnum.name().equals("ALL") ? null: study.localEnum.eq(localEnum);
    }
}

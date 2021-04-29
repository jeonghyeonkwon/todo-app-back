package com.givejeong.todo.repository.querydsl;

import com.givejeong.todo.domain.FieldEnum;
import com.givejeong.todo.domain.QAccount;
import com.givejeong.todo.domain.QQna;
import com.givejeong.todo.domain.Qna;
import com.givejeong.todo.dto.board.qna.QnaDto;
import com.givejeong.todo.dto.board.qna.QnaListDto;
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
import static com.givejeong.todo.domain.QQna.*;

public class QnaRepositoryImpl implements QnaRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;

    public QnaRepositoryImpl(EntityManager em){
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }


    @Override
    public Optional<QnaDto> findByIdx(Long id) {
        return Optional.ofNullable(
                jpaQueryFactory.select(Projections.constructor(QnaDto.class,qna.id,qna.title,account.accountId,qna.content,qna.hit,qna.createdDate))
                        .from(qna).join(qna.account,account)
                        .where(qna.id.eq(id))
                        .fetchOne()
        );
    }

    @Override
    public Page<QnaListDto> qnaListDtos(String section, Pageable pageable) {
        List<QnaListDto> content = jpaQueryFactory
                .select(Projections.constructor(QnaListDto.class, qna.id, qna.title, account.accountId, qna.hit, qna.createdDate))
                .from(qna).join(qna.account, account)
                .where(sectionEq(section))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(qna.createdDate.desc()).fetch();

        JPAQuery<Qna> countQuery = jpaQueryFactory.selectFrom(qna).where(sectionEq(section));
        return PageableExecutionUtils.getPage(content,pageable,countQuery::fetchCount);
    }
    private BooleanExpression sectionEq(String section){
        return qna.fieldEnum.eq(FieldEnum.find(section));
    }
}

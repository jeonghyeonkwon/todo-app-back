package com.givejeong.todo.repository.querydsl;

import com.givejeong.todo.domain.Comment;
import com.givejeong.todo.domain.QAccount;
import com.givejeong.todo.domain.QComment;
import com.givejeong.todo.dto.board.CommentDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import javax.persistence.EntityManager;


import java.util.List;

import static com.givejeong.todo.domain.QAccount.account;
import static com.givejeong.todo.domain.QComment.*;

public class CommentRepositoryImpl implements CommentRepositoryCustom{
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public CommentRepositoryImpl(EntityManager em){
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }



    @Override
    public Page<CommentDto> studyCommentList(Pageable pageable, Long boardId) {
        List<CommentDto> content = queryFactory.select(Projections.constructor(CommentDto.class, account.accountId, comment.content, comment.createdDate))
                .from(comment).join(comment.account, account)
                .where(comment.study.id.eq(boardId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(comment.createdDate.desc()).fetch();
        JPAQuery<Comment> countQuery = queryFactory.selectFrom(comment).where(comment.study.id.eq(boardId));
        return PageableExecutionUtils.getPage(content,pageable,countQuery::fetchCount);
    }

    @Override
    public Page<CommentDto> qnaCommentList(Pageable pageable, Long boardId) {
        List<CommentDto> content = queryFactory.select(Projections.constructor(CommentDto.class, account.accountId, comment.content, comment.createdDate))
                .from(comment).join(comment.account, account)
                .where(comment.qna.id.eq(boardId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(comment.createdDate.desc()).fetch();
        JPAQuery<Comment> countQuery = queryFactory.selectFrom(comment).where(comment.qna.id.eq(boardId));
        return PageableExecutionUtils.getPage(content,pageable,countQuery::fetchCount);
    }
}

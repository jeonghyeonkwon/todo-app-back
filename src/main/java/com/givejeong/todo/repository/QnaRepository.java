package com.givejeong.todo.repository;

import com.givejeong.todo.domain.FieldEnum;
import com.givejeong.todo.domain.Qna;

import com.givejeong.todo.repository.querydsl.QnaRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QnaRepository extends JpaRepository<Qna,Long>, QnaRepositoryCustom {

    //조회수 증가
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Qna qna SET qna.hit = qna.hit + 1 WHERE qna.id = :boardId")
    void hitUp(@Param("boardId") Long boardId);

}

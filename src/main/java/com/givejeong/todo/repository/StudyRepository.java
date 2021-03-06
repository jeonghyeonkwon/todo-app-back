package com.givejeong.todo.repository;

import com.givejeong.todo.domain.FieldEnum;
import com.givejeong.todo.domain.LocalEnum;
import com.givejeong.todo.domain.Study;
import com.givejeong.todo.repository.querydsl.StudyRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudyRepository extends JpaRepository<Study,Long>, StudyRepositoryCustom {

    //조회수 증가
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Study std SET std.hit = std.hit + 1 WHERE std.id = :boardId")
    void hitUp(@Param("boardId") Long boardId);


}

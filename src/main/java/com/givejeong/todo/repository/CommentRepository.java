package com.givejeong.todo.repository;

import com.givejeong.todo.domain.Comment;
import com.givejeong.todo.repository.querydsl.CommentRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long>, CommentRepositoryCustom {
}

package com.givejeong.todo.repository.querydsl;

import com.givejeong.todo.dto.board.CommentDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CommentRepositoryCustom {

    Page<CommentDto> studyCommentList(Pageable pageable, Long boardId);
    Page<CommentDto> qnaCommentList(Pageable pageable, Long boardId);
}

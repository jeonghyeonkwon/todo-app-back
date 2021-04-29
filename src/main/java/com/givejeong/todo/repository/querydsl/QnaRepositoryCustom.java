package com.givejeong.todo.repository.querydsl;

import com.givejeong.todo.domain.LocalEnum;
import com.givejeong.todo.dto.board.qna.QnaDto;
import com.givejeong.todo.dto.board.qna.QnaListDto;
import com.givejeong.todo.dto.board.study.StudyListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface QnaRepositoryCustom {

    Optional<QnaDto> findByIdx(Long id);
    Page<QnaListDto> qnaListDtos(String section, Pageable pageable);
}

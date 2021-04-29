package com.givejeong.todo.repository.querydsl;

import com.givejeong.todo.domain.LocalEnum;
import com.givejeong.todo.dto.board.study.StudyDto;
import com.givejeong.todo.dto.board.study.StudyListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface StudyRepositoryCustom {
    Page<StudyListDto> studyListDtos(String section, LocalEnum localEnum, Pageable pageable);
    Optional<StudyDto> findByIdx(Long id);
}

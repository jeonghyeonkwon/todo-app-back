package com.givejeong.todo.repository.querydsl;

import com.givejeong.todo.domain.TodoEnum;
import com.givejeong.todo.dto.todo.TodoDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TodoRepositoryCustom {
    Page<TodoDto> findTodoList(Long id, Long status, Pageable pageable);
}

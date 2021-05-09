package com.givejeong.todo.repository;

import com.givejeong.todo.domain.Todo;
import com.givejeong.todo.repository.querydsl.TodoRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo,Long> , TodoRepositoryCustom {
}

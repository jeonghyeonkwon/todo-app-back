package com.givejeong.todo.controller;

import com.givejeong.todo.dto.todo.TodoDto;
import com.givejeong.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @GetMapping("/api/todo")
    public ResponseEntity todo_리스트(@RequestParam("status") Long status,Pageable pageable){


        return todoService.todoList(status,pageable);
    }
    @PostMapping("/api/todo")
    public ResponseEntity 할일_추가(@RequestBody TodoDto todoDto){

        return todoService.createTodo(todoDto);
    }

    @PatchMapping("/api/todo/{id}")
    public ResponseEntity todo_성공처리(@PathVariable Long id){
        return todoService.success(id);
    }

}

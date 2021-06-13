package com.givejeong.todo.controller;

import com.givejeong.todo.dto.todo.TodoDto;
import com.givejeong.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class TodoController {

    private final TodoService todoService;

    @GetMapping("/{userId}/todo")
    public ResponseEntity todo_리스트(@PathVariable Long userId,@RequestParam Long status,Pageable pageable){
        log.info("리스트");
        return todoService.todoList(userId,status,pageable);
    }


    @PostMapping("/{userId}/todo")
    public ResponseEntity 할일_추가(@PathVariable Long userId,@RequestBody TodoDto todoDto){

        return todoService.createTodo(userId,todoDto);
    }

    @PatchMapping("/{userId}/todo/{cardId}")
    public ResponseEntity todo_성공처리(@PathVariable Long userId,@PathVariable Long cardId){
        return todoService.success(userId,cardId);
    }

}

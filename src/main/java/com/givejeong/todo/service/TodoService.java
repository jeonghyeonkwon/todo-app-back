package com.givejeong.todo.service;

import com.givejeong.todo.domain.Account;
import com.givejeong.todo.domain.Todo;
import com.givejeong.todo.dto.todo.TodoDto;
import com.givejeong.todo.repository.AccountRepository;
import com.givejeong.todo.repository.TodoRepository;
import com.givejeong.todo.security.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor

public class TodoService {
    private final AccountRepository accountRepository;
    private final TodoRepository todoRepository;
    @Transactional
    public ResponseEntity createTodo(Long userId,TodoDto todoDto){
        String accountId = SecurityUtil.getCurrentUsername().get();
        Account account = accountRepository.findByAccountId(accountId).get();
        if(!account.getId().equals(userId)) return new ResponseEntity(HttpStatus.BAD_REQUEST);
        Todo todo = new Todo(account,todoDto);
        Todo save = todoRepository.save(todo);
        return new ResponseEntity(save.getId(), HttpStatus.CREATED);
    }
    @Transactional
    public ResponseEntity todoList(Long userId, Long status, Pageable pageable) {
        String accountId = SecurityUtil.getCurrentUsername().get();
        Account account = accountRepository.findByAccountId(accountId).get();
        if(!userId.equals(account.getId())) return new ResponseEntity(HttpStatus.BAD_REQUEST);
        for(Todo todo : account.getTodoList()){
            todo.refreshStatus();
            todoRepository.save(todo);
        }
        Page<TodoDto> todoList = todoRepository.findTodoList(account.getId(), status, pageable);

        return new ResponseEntity(todoList,HttpStatus.OK);
    }
    @Transactional
    public ResponseEntity success(Long userId,Long cardId) {
        String accountId = SecurityUtil.getCurrentUsername().get();

        Account account = accountRepository.findByAccountId(accountId).get();
        if(!account.getId().equals(userId)) return new ResponseEntity(HttpStatus.BAD_REQUEST);

        Todo todo = todoRepository.findById(cardId).get();
        todo.success();
        Todo save = todoRepository.save(todo);
        return new ResponseEntity(save,HttpStatus.OK);
    }
}

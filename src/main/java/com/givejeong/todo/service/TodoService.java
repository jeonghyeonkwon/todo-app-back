package com.givejeong.todo.service;

import com.givejeong.todo.domain.Account;
import com.givejeong.todo.domain.Todo;
import com.givejeong.todo.dto.todo.TodoDto;
import com.givejeong.todo.repository.AccountRepository;
import com.givejeong.todo.repository.TodoRepository;
import com.givejeong.todo.security.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity createTodo(TodoDto todoDto){
        String accountId = SecurityUtil.getCurrentUsername().get();
        Account account = accountRepository.findByAccountId(accountId);
        Todo todo = new Todo(account,todoDto);
        Todo save = todoRepository.save(todo);
        return new ResponseEntity(save.getId(), HttpStatus.CREATED);
    }
    @Transactional
    public ResponseEntity todoList(Long status, Pageable pageable) {
        String id = SecurityUtil.getCurrentUsername().get();
        Account account = accountRepository.findByAccountId(id);
        for(Todo todo : account.getTodoList()){
            todo.refreshStatus();
            todoRepository.save(todo);
        }
        Page<TodoDto> todoList = todoRepository.findTodoList(account.getId(), status, pageable);

        return new ResponseEntity(todoList,HttpStatus.OK);
    }
    @Transactional
    public ResponseEntity success(Long id) {
        Todo todo = todoRepository.findById(id).get();
        todo.success();
        Todo save = todoRepository.save(todo);
        return new ResponseEntity(save,HttpStatus.OK);
    }
}

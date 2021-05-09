package com.givejeong.todo.dto.todo;

import com.givejeong.todo.domain.Todo;
import com.givejeong.todo.domain.TodoEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodoDto {
    private Long id;
    private String title;
    private String content;
    private String createTodo;
    private String goal;
    private String status;
    public TodoDto(Todo todo){
        this.id = todo.getId();
        this.title = todo.getTitle();
        this.content = todo.getContent();
        this.createTodo = todo.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        try{
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            this.goal = simpleDateFormat.format(todo.getGoal());
        }catch (Exception e){
            e.printStackTrace();
        }
        this.status = todo.getTodoEnum().name().toLowerCase(Locale.ROOT);
    }
    public TodoDto(Long id, String title, String content, LocalDateTime createTodo, Date goal, TodoEnum todoEnum){
        this.id = id;
        this.title = title;
        this.content = content;
        this.createTodo = createTodo.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        try{
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            this.goal = simpleDateFormat.format(goal);
        }catch (Exception e){
            e.printStackTrace();
        }
        this.status = todoEnum.name().toLowerCase(Locale.ROOT);
    }

}

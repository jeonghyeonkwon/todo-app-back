package com.givejeong.todo.domain;

import com.givejeong.todo.dto.todo.TodoDto;
import lombok.Getter;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Getter
@Table(name="TODO_TABLE")
public class Todo extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="todo_pkid")
    private Long id;

    private String title;
    private Date goal;
    @Lob
    private String content;

    @Enumerated(EnumType.STRING)
    private TodoEnum todoEnum;
    /*----------------------- 생성자-------------------------*/
    protected Todo(){}
    public Todo(Account account, TodoDto todoDto){
        createTodo(account);
        this.title = todoDto.getTitle();
        this.content = todoDto.getContent();
        try {
            SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
            this.goal = sdformat.parse(todoDto.getGoal());
        }catch (Exception e){
            e.printStackTrace();
        }
        refreshStatus();
    }


    /*----------------------- 다대일-------------------------*/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="account_id")
    private Account account;

    /*----------------------- 연관관계 -------------------------*/
    //Todo생성
    private void createTodo(Account account) {
        this.account = account;
        account.getTodoList().add(this);
    }
    /*----------------------- 로직 -------------------------*/
    public void refreshStatus(){
        if(this.todoEnum !=TodoEnum.ACHIEVE){
            try {
                SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
                Date current = new Date();
                String currentString = sdformat.format(current);
                String innerDate = sdformat.format(this.goal);
                if(currentString.compareTo(innerDate)>0){
                    this.todoEnum = TodoEnum.FAILURE;
                }else if(currentString.compareTo(innerDate)<0){
                    this.todoEnum = TodoEnum.EXPECTED;
                }else{
                    this.todoEnum = TodoEnum.TODAY;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void success() {
        if(this.todoEnum==TodoEnum.TODAY||this.todoEnum==TodoEnum.EXPECTED){
            this.todoEnum = TodoEnum.ACHIEVE;
        }
    }
}


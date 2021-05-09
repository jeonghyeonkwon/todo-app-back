package com.givejeong.todo.repository.querydsl;

import com.givejeong.todo.domain.QAccount;
import com.givejeong.todo.domain.QTodo;
import com.givejeong.todo.domain.Todo;
import com.givejeong.todo.domain.TodoEnum;
import com.givejeong.todo.dto.todo.TodoDto;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import javax.persistence.EntityManager;
import java.util.List;

import static com.givejeong.todo.domain.QAccount.account;
import static com.givejeong.todo.domain.QTodo.todo;

public class TodoRepositoryImpl implements TodoRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;

    public TodoRepositoryImpl(EntityManager em){
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }


    @Override
    public Page<TodoDto> findTodoList(Long id, Long status, Pageable pageable) {
        List<TodoDto> content = jpaQueryFactory.select(Projections.constructor(TodoDto.class,
                todo.id, todo.title, todo.content, todo.createdDate, todo.goal, todo.todoEnum
        ))
                .from(todo).join(todo.account, account)
                .where(accountEq(id), todoEnumEq(status))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(todo.goal.desc()).fetch();
        JPAQuery<Todo> countQuery = jpaQueryFactory.selectFrom(todo).where(accountEq(id), todoEnumEq(status));
        return PageableExecutionUtils.getPage(content,pageable,countQuery::fetchCount);
    }

    private BooleanExpression accountEq(Long id){
        return account.id.eq(id);
    }
    private BooleanExpression todoEnumEq(Long status){
        if(status==0) return todo.todoEnum.eq(TodoEnum.TODAY).or(todo.todoEnum.eq(TodoEnum.EXPECTED));
        else if(status==1) return todo.todoEnum.eq(TodoEnum.FAILURE);
        else return todo.todoEnum.eq(TodoEnum.ACHIEVE);
    }
}

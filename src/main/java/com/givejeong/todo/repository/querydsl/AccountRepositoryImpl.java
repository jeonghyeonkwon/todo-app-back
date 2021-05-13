package com.givejeong.todo.repository.querydsl;

import com.givejeong.todo.domain.Account;
import com.givejeong.todo.domain.QAccount;
import com.givejeong.todo.dto.auth.SearchDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.Optional;

import static com.givejeong.todo.domain.QAccount.*;

public class AccountRepositoryImpl implements AccountRepositoryCustom{
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public AccountRepositoryImpl(EntityManager em){
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public Optional<Account> searchId(SearchDto searchDto) {
        String accountId = queryFactory.select(account.accountId).from(account).where(account.accountName.eq(searchDto.getAccountName()), account.tel.eq(searchDto.getTel())).fetchFirst();
        return Optional.empty();
    }

    @Override
    public Optional<Account> searchPw(SearchDto searchDto) {
        return Optional.empty();
    }
//
//    private BooleanExpression accountNameEq(String accountName){
//
//    }
//    private BooleanExpression accountIdEq(String accountId){
//
//    }
//    private BooleanExpression telEq(String tel){
//
//    }
}

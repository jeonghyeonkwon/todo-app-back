package com.givejeong.todo.repository.querydsl;

import com.givejeong.todo.domain.Account;
import com.givejeong.todo.domain.QAccount;
import com.givejeong.todo.dto.auth.SearchDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;
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
    public List<String> searchId(String accountName,String tel) {
        List<String> fetch = queryFactory
                .select(account.accountId)
                .from(account)
                .where(
                        account.accountName.eq(accountName),
                        account.tel.eq(tel)
                )
                .fetch();
        return fetch;
    }

    @Override
    public Optional<Long> searchPw(String accountName,String accountId,String tel) {
        Long id = queryFactory
                .select(account.id)
                .from(account)
                .where(
                        account.accountName.eq(accountName),
                        account.accountId.eq(accountId),
                        account.tel.eq(tel)
                )
                .fetchFirst();
        return Optional.ofNullable(id);
    }

}

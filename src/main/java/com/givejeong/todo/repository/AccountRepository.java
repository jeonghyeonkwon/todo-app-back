package com.givejeong.todo.repository;

import com.givejeong.todo.domain.Account;
import com.givejeong.todo.repository.querydsl.AccountRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account,Long>, AccountRepositoryCustom {
    Account findByAccountId(String accountId);
}

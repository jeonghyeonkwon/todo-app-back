package com.givejeong.todo.repository;

import com.givejeong.todo.domain.Account;
import com.givejeong.todo.repository.querydsl.AccountRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account,Long>, AccountRepositoryCustom {
    Optional<Account> findByAccountId(String accountId);
}

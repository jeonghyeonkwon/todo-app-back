package com.givejeong.todo.repository;

import com.givejeong.todo.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account,Long> {
    Account findByAccountId(String accountId);
}

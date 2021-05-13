package com.givejeong.todo.repository.querydsl;

import com.givejeong.todo.domain.Account;
import com.givejeong.todo.dto.auth.SearchDto;

import java.util.Optional;

public interface AccountRepositoryCustom {
    Optional<Account> searchId(SearchDto searchDto);
    Optional<Account> searchPw(SearchDto searchDto);
}

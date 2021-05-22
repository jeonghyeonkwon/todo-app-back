package com.givejeong.todo.repository.querydsl;

import com.givejeong.todo.domain.Account;
import com.givejeong.todo.dto.auth.SearchDto;

import java.util.List;
import java.util.Optional;

public interface AccountRepositoryCustom {
    List<String> searchId(String accountName,String tel);
    Optional<Long> searchPw(String accountName,String accoutId, String tel);
}

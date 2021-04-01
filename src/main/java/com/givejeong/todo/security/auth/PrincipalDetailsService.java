package com.givejeong.todo.security.auth;

import com.givejeong.todo.domain.Account;
import com.givejeong.todo.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//http://localhost:8080/login 이 기본인데 formLogin.disabled 해서 jwtAuthenticationFilter에서 처리
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {
    private final AccountRepository accountRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByAccountId(username);
        System.out.println("service : " + account.getAccountId() + "pw : " + account.getAccountPw() +"account : "  +account.getLocalEnum().getKor());
        System.out.println("PrincipalDetailsService");
        return new PrincipalDetails(account);
    }
}

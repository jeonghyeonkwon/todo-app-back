package com.givejeong.todo.account;

import com.givejeong.todo.domain.*;
import com.givejeong.todo.dto.auth.AccountDto;
import com.givejeong.todo.dto.board.RoleTypeDto;
import com.givejeong.todo.dto.board.qna.QnaDto;
import com.givejeong.todo.dto.board.study.StudyDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.givejeong.todo.domain.QAccount.account;
import static org.assertj.core.api.Assertions.*;
@SpringBootTest
@Transactional
public class AccountTest {

    @Autowired
    EntityManager em;
    @Autowired
    PasswordEncoder passwordEncoder;

    JPAQueryFactory queryFactory;
    @BeforeEach
    public void before(){
        queryFactory = new JPAQueryFactory(em);
        AccountDto accountDto = AccountDto.builder()
                .accountId("givejeong555")
                .password("1234")
                .tel("010-4321-4321")
                .name("박정현")
                .location("서울").build();
        Account givejeong = new Account(accountDto);
        givejeong.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        em.persist(givejeong);

        AccountDto accountDto2 = AccountDto.builder()
                .accountId("givejeong6666")
                .password("1234")
                .tel("010-4321-4321")
                .name("박정현")
                .location("서울").build();
        Account givejeong2 = new Account(accountDto2);
        givejeong2.setPassword(passwordEncoder.encode(accountDto2.getPassword()));
        em.persist(givejeong2);

        AccountDto accountDto3 = AccountDto.builder()
                .accountId("givejeong6666")
                .password("123456")
                .tel("010-0000-0000")
                .name("박정현")
                .location("서울").build();
        Account givejeong3 = new Account(accountDto3);
        givejeong3.setPassword(passwordEncoder.encode(accountDto3.getPassword()));
        em.persist(givejeong3);

    }
    @Test
    public void 계정_갯수(){
        List<Account> accountList = queryFactory.selectFrom(account).fetch();
        for(Account account : accountList){
            System.out.println(account.getAccountName());
        }
        long count = queryFactory.selectFrom(account).fetchCount();
        assertThat(count).isEqualTo(6);
    }

    @Test
    public void 아이디_찾기_이름과_전화번호_같을때_리스트(){
        List<String> idList = queryFactory
                .select(account.accountId)
                .from(account)
                .where(
                        account.accountName.eq("박정현"),
                        account.tel.eq("010-4321-4321")
                ).fetch();
        idList.forEach(System.out::println);
    }
    @Test
    public void 아이디_찾기_일치하지_않을_때(){
        List<String> idList = queryFactory
                .select(account.accountId)
                .from(account)
                .where(
                        account.accountName.eq("정현"),
                        account.tel.eq("010-4321-4321")
                ).fetch();
        idList.forEach(System.out::println);
    }

    @Test
    public void 아이디_반_특수문자로_가리기(){
        List<String> idList = queryFactory
                .select(account.accountId)
                .from(account)
                .where(
                        account.accountName.eq("박정현"),
                        account.tel.eq("010-4321-4321")
                ).fetch();
        List<String> findId = idList.stream().map(s -> {
            StringBuffer buffer = new StringBuffer(s.length());
            buffer.append(s.substring(0, s.length() / 2));
            for (int i = 0; i < s.length() - s.length() / 2; i++) buffer.append("*");
            System.out.println("s.length/2 " + s.length() / 2 + " s.length " + s.length() + " buffer.length " + buffer.length());
            return buffer.toString();
        }).collect(Collectors.toList());
        findId.forEach(System.out::println);
    }
    @Test
    public void 비번_찾기_아이디_이름_전화번호_같을때(){
        String accountPw = queryFactory
                .select(account.accountPw)
                .from(account)
                .where(
                        account.accountName.eq("박정현"),
                        account.accountId.eq("givejeong6666"),
                        account.tel.eq("010-0000-0000")
                )
                .fetchFirst();
        System.out.println(accountPw);

    }
    @Test
    public void 비번_암호화_해제(){
        String accountPw = queryFactory
                .select(account.accountPw)
                .from(account)
                .where(
                        account.accountName.eq("박정현"),
                        account.accountId.eq("givejeong6666"),
                        account.tel.eq("010-0000-0000")
                )
                .fetchFirst();

        System.out.println(passwordEncoder.encode(accountPw));

    }
}

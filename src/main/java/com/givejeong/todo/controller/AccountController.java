package com.givejeong.todo.controller;

import com.givejeong.todo.domain.Account;
import com.givejeong.todo.domain.LocalEnum;
import com.givejeong.todo.dto.*;
import com.givejeong.todo.dto.auth.*;
import com.givejeong.todo.security.jwt.JwtFilter;
import com.givejeong.todo.security.jwt.TokenProvider;
import com.givejeong.todo.security.util.SecurityUtil;
import com.givejeong.todo.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;


    @GetMapping("/register")
    public ResponseEntity 회원가입_폼(){
        //지역 리스트 반환
        Map data = new HashMap<>();
        data.put("local",LocalEnum.createAccountLocalList());
        return ResponseEntity.ok().body(data);
    }

    @GetMapping("/validate")
    public ResponseEntity 아이디_중복_체크(@RequestParam("accountId") String accountId){
        Map data = accountService.validateId(accountId);
        return ResponseEntity.ok().body(data);
    }
    @PostMapping("/register")
    public ResponseEntity 회원가입_완료(@RequestBody AccountDto dto){
        System.out.println(dto);

        ResponseEntity<Object> response = accountService.createUser(dto);
        return response;
    }
    @PostMapping("/authenticate")
    public ResponseEntity<TokenDto> 로그인(@RequestBody LoginDto loginDto){
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginDto.getUsername(),loginDto.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.createToken(authentication);

        Account user = accountService.currentUser();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER,"Bearer " + jwt);
        return new ResponseEntity<>(new TokenDto(jwt), httpHeaders, HttpStatus.OK);
    }

    @GetMapping("/api/auth/check")
    public ResponseEntity<CheckDto> 로그인_체크(){
        Account account = accountService.currentUser();
        System.out.println("체크 함");
        CheckDto dto = new CheckDto(account);
        return ResponseEntity.ok().body(dto);
    }
    @GetMapping("/api/myinfo")
    public ResponseEntity 회원정보_수정_폼(){
        Map data = new HashMap<>();
        Account account = accountService.currentUser();
        data.put("account",new AccountDto(account));
        data.put("local",LocalEnum.createAccountLocalList());
        return ResponseEntity.ok().body(data);
    }
    @PatchMapping("/api/myinfo/{id}")
    public ResponseEntity 회원정보_수정(@RequestParam("update") String update,@RequestBody UpdateDto dto,@PathVariable("id") Long id){
        System.out.println("update : " + update);
        System.out.println(dto);

        return accountService.updateUser(id,update,dto);
    }

    //아직 미완성 삭제시 연관된 엔티티 관련 문제
    @DeleteMapping("/api/myinfo/{id}")
    public ResponseEntity 회원_삭제하기(@PathVariable("id")Long id ,@RequestBody UpdateDto dto){
        System.out.println("회원삭제");
        System.out.println(id);
        System.out.println(dto);
        return accountService.deleteUser(id, dto);

    }

    @GetMapping("/api/search-id")
    public ResponseEntity 아이디_찾기(@RequestBody SearchDto searchDto){
        return accountService.searchId(searchDto);

    }

}

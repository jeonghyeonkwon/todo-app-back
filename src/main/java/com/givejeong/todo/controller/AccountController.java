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
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;



    @GetMapping("/validate")
    public ResponseEntity 아이디_중복_체크(@RequestParam("accountId") String accountId){

        return accountService.validateId(accountId);

    }
    @PostMapping("/register")
    public ResponseEntity 회원가입_완료(@RequestBody AccountDto dto){
        System.out.println(dto);

        ResponseEntity<Object> response = accountService.createUser(dto);
        return response;
    }
    @PostMapping("/authenticate")
    public ResponseEntity<TokenDto> 로그인(@RequestBody LoginDto loginDto){
        log.info("username"+loginDto.getUsername()+"password"+loginDto.getPassword());
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginDto.getUsername(),loginDto.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(authentication);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER,"Bearer " + jwt);
        return new ResponseEntity<>(new TokenDto(jwt), httpHeaders, HttpStatus.OK);
    }

    @GetMapping("/auth/check")
    public ResponseEntity<CheckDto> 로그인_체크(){
        Account account = accountService.currentUser();
        CheckDto dto = new CheckDto(account);
        return ResponseEntity.ok().body(dto);
    }
    @GetMapping("/myinfo")
    public ResponseEntity 회원정보_수정_폼(){
        Map data = new HashMap<>();
        Account account = accountService.currentUser();
        data.put("account",new AccountDto(account));
        data.put("local",LocalEnum.createAccountLocalList());
        return ResponseEntity.ok().body(data);
    }
    @PatchMapping("/myinfo/{id}")
    public ResponseEntity 회원정보_수정(@RequestParam("update") String update,@RequestBody UpdateDto dto,@PathVariable("id") Long id){
        return accountService.updateUser(id,update,dto);
    }

    //아직 미완성 삭제시 연관된 엔티티 관련 문제
    @DeleteMapping("/myinfo/{id}")
    public ResponseEntity 회원_삭제하기(@PathVariable("id")Long id ,@RequestBody UpdateDto dto){

        return accountService.deleteUser(id, dto);

    }

    @GetMapping("/search-id")
    public ResponseEntity 아이디_찾기(@RequestParam String accountName, @RequestParam String tel){
        return accountService.searchId(accountName,tel);

    }
    @GetMapping("/search-pw")
    public ResponseEntity 비밀번호_찾아_id_넘기기(@RequestParam String accountName,@RequestParam String accountId,@RequestParam String tel){
        return accountService.searchPw(accountId,accountName,tel);
    }
    @PatchMapping("/search-pw/{id}")
    public ResponseEntity 비밀번호_변경(@PathVariable Long id,@RequestBody SearchDto searchDto){
        if(!searchDto.getNewPw().equals(searchDto.getReNewPw())) return new ResponseEntity(HttpStatus.BAD_REQUEST);

        if(id == searchDto.getId()) return accountService.updatePw(searchDto);
        else return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

}

package com.givejeong.todo.service;

import com.givejeong.todo.domain.Account;
import com.givejeong.todo.dto.auth.AccountDto;
import com.givejeong.todo.dto.ErrorDto;
import com.givejeong.todo.dto.auth.UpdateDto;
import com.givejeong.todo.repository.AccountRepository;
import com.givejeong.todo.security.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    private final PasswordEncoder passwordEncoder;
    public Map validateId(String id) {
        Map msg = new HashMap();
        Account account = accountRepository.findByAccountId(id);
        if(account==null){
            msg.put("message","ID_OK");
        }else{
            msg.put("message","ID_NO");
        }
        return msg;
    }
    @Transactional
    public ResponseEntity<Object> createUser(AccountDto dto) {
        Account account = accountRepository.findByAccountId(dto.getAccountId());

        if(account==null){
            Account newUser = new Account(dto);
            newUser.setPassword(passwordEncoder.encode(dto.getPassword()));
            Account save = accountRepository.save(newUser);
            Map map = new HashMap();
            map.put("userId",save.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(map);
        }else{
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorDto("회원 가입 중 동일한 아이디를 가입한 유저가 있습니다. 다시 시도해 주세요."));
        }
    }

    public Account currentUser() {
        String accountId = SecurityUtil.getCurrentUsername().get();
        return accountRepository.findByAccountId(accountId);
    }
    @Transactional
    public ResponseEntity updateUser(Long id, String update, UpdateDto dto) {
        Account account = accountRepository.findById(id).get();
        String currentId = SecurityUtil.getCurrentUsername().get();
        if(!account.getAccountId().equals(currentId)){
            return new ResponseEntity(new ErrorDto("정보 변경에 오류가 발생했습니다. 다시 시도해 주세요"),HttpStatus.BAD_REQUEST);
        }
        switch (update){
            case"password":
                if(!passwordEncoder.matches(dto.getCurrentPw(),account.getAccountPw())){
                    return new ResponseEntity(new ErrorDto("현재 비밀번호가 일치하지 않습니다"),HttpStatus.BAD_REQUEST);
                }
                account.updatePassword(passwordEncoder.encode(dto.getNewPw()));
                break;
            case"name":
            case"local":
                account.update(update,dto);
                break;
        }
        Account updateUser = accountRepository.save(account);
        return new ResponseEntity(updateUser.getId(),HttpStatus.NO_CONTENT);
    }
    @Transactional
    public ResponseEntity deleteUser(Long id, UpdateDto dto) {
        Account account = accountRepository.findById(id).get();
        String userId = SecurityUtil.getCurrentUsername().get();
        if(!account.getAccountId().equals(userId)){
            return new ResponseEntity(new ErrorDto("정보 변경에 오류가 발생했습니다. 다시 시도해 주세요"),HttpStatus.BAD_REQUEST);
        }else if(!passwordEncoder.matches(dto.getWithDrawalPw(),account.getAccountPw())){
            return new ResponseEntity(new ErrorDto("비밀번호가 맞지 않습니다"),HttpStatus.BAD_REQUEST);
        }
        accountRepository.delete(account);
        return new ResponseEntity(HttpStatus.NO_CONTENT);

    }
}

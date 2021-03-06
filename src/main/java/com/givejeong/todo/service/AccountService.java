package com.givejeong.todo.service;

import com.givejeong.todo.domain.Account;
import com.givejeong.todo.dto.auth.AccountDto;
import com.givejeong.todo.dto.ErrorDto;
import com.givejeong.todo.dto.auth.SearchDto;
import com.givejeong.todo.dto.auth.UpdateDto;
import com.givejeong.todo.repository.AccountRepository;
import com.givejeong.todo.security.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class AccountService {
    private final AccountRepository accountRepository;

    private final PasswordEncoder passwordEncoder;
    public ResponseEntity validateId(String id) {
        Optional<Account> byAccountId = accountRepository.findByAccountId(id);
        if(byAccountId.isPresent()){
            return new ResponseEntity<>(new ErrorDto("ID_NO"),HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>("ID_OK",HttpStatus.OK);
    }
    @Transactional
    public ResponseEntity<Object> createUser(AccountDto dto) {
        Optional<Account> byAccountId = accountRepository.findByAccountId(dto.getAccountId());

        if(!byAccountId.isPresent()){
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
        return accountRepository.findByAccountId(accountId).get();
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
            case"tel":
                account.update(update,dto);
                break;
        }
        Account updateUser = accountRepository.save(account);

        return new ResponseEntity(updateUser.getId(),HttpStatus.OK);
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

    public ResponseEntity searchId(String accountName, String tel) {
        List<String> strings = accountRepository.searchId(accountName,tel);
        if(strings.size()==0) return new ResponseEntity("조건에 맞는 아이디가 없습니다.",HttpStatus.NOT_FOUND);
        List<String> findId = strings.stream().map(s -> {
            StringBuffer buffer = new StringBuffer(s.length());
            buffer.append(s.substring(0, s.length() / 2));
            for (int i = 0; i < s.length() - s.length() / 2; i++) buffer.append("*");
            return buffer.toString();
        }).collect(Collectors.toList());
        return new ResponseEntity(findId,HttpStatus.OK);
    }
    public ResponseEntity searchPw(String accountId,String accountName,String tel){
        Optional<Long> optional = accountRepository.searchPw(accountName, accountId, tel);
        if(!optional.isPresent()){
            return new ResponseEntity("회원 정보가 일치하지 않습니다",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(optional.get(),HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity updatePw(SearchDto searchDto) {
        Optional<Account> byId = accountRepository.findById(searchDto.getId());
        if(!byId.isPresent()){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        Account account = byId.get();
        account.updatePassword(passwordEncoder.encode(searchDto.getNewPw()));
        return new ResponseEntity(account.getId(),HttpStatus.OK);
    }
}

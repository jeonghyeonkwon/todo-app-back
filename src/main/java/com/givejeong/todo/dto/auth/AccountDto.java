package com.givejeong.todo.dto.auth;

import com.givejeong.todo.domain.Account;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDto {
    private Long id;
    private String accountId;
    private String password;
    private String name;
    private String location;
    private String tel;
    public AccountDto(Account dto){
        this.id = dto.getId();
        this.accountId = dto.getAccountId();
        this.name = dto.getAccountName();
        this.location = dto.getLocalEnum().getKor();
        this.tel = dto.getTel();
    }
}
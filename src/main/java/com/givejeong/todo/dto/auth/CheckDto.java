package com.givejeong.todo.dto.auth;

import com.givejeong.todo.domain.Account;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
@Data
public class CheckDto {
    private Long id;
    private String accountId;
    private String accountName;
    public CheckDto(Account account){
        this.id = account.getId();
        this.accountId = account.getAccountId();
        this.accountName = account.getAccountName();
    }
}

package com.givejeong.todo.dto.auth;

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

}
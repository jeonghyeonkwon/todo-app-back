package com.givejeong.todo.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateDto {
    private String currentPw;
    private String newPw;
    private String reNewPw;
    private String tel;
    private String name;
    private String local;
    private String withDrawalPw;
}

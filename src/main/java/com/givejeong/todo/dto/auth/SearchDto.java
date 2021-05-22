package com.givejeong.todo.dto.auth;

import lombok.Data;

@Data
public class SearchDto {
  private Long id;
  private String newPw;
  private String reNewPw;
}

package com.givejeong.todo.dto.board.study;

import lombok.Data;

@Data
public class LocalDto {
    private String kor;
    private String eng;
    public LocalDto(String kor,String eng){
        this.kor = kor;
        this.eng = eng;
    }
}

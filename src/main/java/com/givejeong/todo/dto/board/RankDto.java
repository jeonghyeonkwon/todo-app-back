package com.givejeong.todo.dto.board;

import lombok.Data;

@Data
public class RankDto {
    private String language;
    private Double percent;

    public RankDto(String language,Double percent){
        this.language = language;
        this.percent = Math.round(percent*100)/100.0;
    }
}

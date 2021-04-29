package com.givejeong.todo.dto.board;

import lombok.Data;

import java.util.List;

@Data
public class RankFormDto {
    private List<RankDto> qnaRank;
    private List<RankDto> studyRank;
    private List<RankDto> totalRank;
    public RankFormDto(List<RankDto> qnaRank,List<RankDto> studyRank,List<RankDto> totalRank){
        this.qnaRank =qnaRank;
        this.studyRank = studyRank;
        this.totalRank = totalRank;
    }
}

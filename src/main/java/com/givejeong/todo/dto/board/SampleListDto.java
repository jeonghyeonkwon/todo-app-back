package com.givejeong.todo.dto.board;

import com.givejeong.todo.domain.Qna;
import com.givejeong.todo.domain.Study;
import com.givejeong.todo.dto.board.RoleTypeDto;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class SampleListDto {
    private Long id;
    private String title;

    private List<RoleTypeDto> roleTypeDtoList;

    public SampleListDto(){}
    public SampleListDto(Long id,String title){
        this.id = id;
        this.title = title;
    }
}

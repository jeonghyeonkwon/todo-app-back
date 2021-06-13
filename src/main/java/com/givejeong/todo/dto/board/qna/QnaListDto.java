package com.givejeong.todo.dto.board.qna;

import com.givejeong.todo.domain.Qna;
import com.givejeong.todo.dto.board.RoleTypeDto;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class QnaListDto {
    private Long id;
    private String title;
    private String writer;
    private List<RoleTypeDto> roleTypeDtoList;
    private Long hit;
    private String createQna;

    public QnaListDto(Long id, String title, String writer, Long hit, LocalDateTime createQna){
        this.id = id;
        this.title = title;
        this.writer = writer;
        this.hit = hit;
        this.createQna = createQna.format(DateTimeFormatter.ofPattern("yy-MM-dd HH:mm"));
    }
}

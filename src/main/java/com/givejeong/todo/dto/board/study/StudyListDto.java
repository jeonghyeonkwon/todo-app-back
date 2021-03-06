package com.givejeong.todo.dto.board.study;

import com.givejeong.todo.domain.Study;
import com.givejeong.todo.domain.StudyStatusEnum;
import com.givejeong.todo.dto.board.RoleTypeDto;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Data
public class StudyListDto {
    private Long id;
    private String title;
    private String writer;
    private List<RoleTypeDto> roleTypeDtoList ;
    private String status;
    private Long applicant;
    private Long hit;
    private String createStudy;

    public StudyListDto(Long id, String title, String writer, StudyStatusEnum status, Long applicant, Long hit, LocalDateTime createStudy){
        this.id = id;
        this.title = title;
        this.writer = writer;

        this.status = status.name().toLowerCase(Locale.ROOT);
        this.applicant =applicant;
        this.hit = hit;
        this.createStudy = createStudy.format(DateTimeFormatter.ofPattern("yy-MM-dd HH:mm"));
    }
}

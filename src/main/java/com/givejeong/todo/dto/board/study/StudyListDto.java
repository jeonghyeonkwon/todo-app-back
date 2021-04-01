package com.givejeong.todo.dto.board.study;

import com.givejeong.todo.domain.Study;
import com.givejeong.todo.dto.board.RoleTypeDto;
import lombok.Data;

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

    public StudyListDto(Study study){
        this.id = study.getId();
        this.title = study.getTitle();
        this.writer = study.getAccount().getAccountId();
        this.roleTypeDtoList = study.getProgrammingRoleList().stream().map(RoleTypeDto::new).collect(Collectors.toList());
        this.status = study.getStudyStatusEnum().name().toLowerCase(Locale.ROOT);
        this.applicant = study.getApplicant();
    }
}

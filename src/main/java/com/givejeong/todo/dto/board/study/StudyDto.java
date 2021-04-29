package com.givejeong.todo.dto.board.study;

import com.givejeong.todo.domain.Account;
import com.givejeong.todo.domain.Study;
import com.givejeong.todo.domain.StudyStatusEnum;
import com.givejeong.todo.dto.board.CommentDto;
import com.givejeong.todo.dto.board.RoleTypeDto;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class StudyDto {
    private Long id;
    private String title;
    private String contents;
    private Long applicant;
    private String writer;
    private List<RoleTypeDto> programmingType;
    private String status;
    private List<CommentDto> commentList;
    private Long hit;
    private String createBoard;

    public StudyDto(Study board, Account account, List<RoleTypeDto> programmingRoleList,List<CommentDto> commentList) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.contents = board.getContent();
        this.applicant = board.getApplicant();
        this.writer = account.getAccountId();
        this.programmingType = programmingRoleList;
        this.status = board.getStudyStatusEnum().name().toLowerCase(Locale.ROOT);
        this.commentList = commentList;
        this.createBoard = board.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.hit = board.getHit();
    }
    public StudyDto(Long id, String title, String writer, String contents, Long applicant, StudyStatusEnum status, Long hit, LocalDateTime creatStudy){
        this.id = id;
        this.title = title;
        this.writer = writer;
        this.contents = contents;
        this.applicant = applicant;
        this.status = status.name().toLowerCase(Locale.ROOT);
        this.hit = hit;
        this.createBoard = creatStudy.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}

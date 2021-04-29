package com.givejeong.todo.dto.board.qna;

import com.givejeong.todo.domain.Account;
import com.givejeong.todo.domain.Qna;
import com.givejeong.todo.dto.board.CommentDto;
import com.givejeong.todo.dto.board.RoleTypeDto;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Data
public class QnaDto {
    private Long id;
    private String title;
    private String contents;
    private String writer;
    private List<RoleTypeDto> programmingType;
    private List<CommentDto> commentList;
    private Long hit;
    private String createBoard;

    public QnaDto(Qna qna, Account account, List<RoleTypeDto> programmingRoleList, List<CommentDto> commentList){
        this.id = qna.getId();
        this.title = qna.getTitle();
        this.contents = qna.getContent();
        this.writer = account.getAccountId();
        this.programmingType = programmingRoleList;
        this.commentList = commentList;
        this.createBoard = qna.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.hit = qna.getHit();
    }

    public QnaDto(Long id, String title, String writer, String contents, Long hit, LocalDateTime createQna){
        this.id = id;
        this.title = title;
        this.writer = writer;
        this.contents = contents;
        this.hit = hit;
        this.createBoard = createQna.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}

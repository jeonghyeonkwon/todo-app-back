package com.givejeong.todo.dto.board;

import com.givejeong.todo.domain.Comment;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDto {
    private String writer;
    private String comment;
    private String createComment;

    public CommentDto(Comment comment){
        this.writer = comment.getAccount().getAccountId();
        this.comment = comment.getContent();
        this.createComment = comment.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}

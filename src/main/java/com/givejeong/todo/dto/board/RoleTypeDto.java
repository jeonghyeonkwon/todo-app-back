package com.givejeong.todo.dto.board;

import com.givejeong.todo.domain.ProgrammingEnum;
import com.givejeong.todo.domain.ProgrammingRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.management.relation.Role;
import java.util.Locale;

@Data

@AllArgsConstructor
@Builder
public class RoleTypeDto {
    private Long boardId;
    private String title;
    private String kor;
    public RoleTypeDto(){}
    public RoleTypeDto(ProgrammingRole pr){
        this.title = pr.getProgrammingEnum().name().toLowerCase(Locale.ROOT);
        this.kor = pr.getProgrammingEnum().getKor();
    }
    public RoleTypeDto(Long boardId, ProgrammingEnum pr){
        this.boardId = boardId;
        this.title = pr.name().toLowerCase(Locale.ROOT);
        this.kor = pr.getKor();
    }
}

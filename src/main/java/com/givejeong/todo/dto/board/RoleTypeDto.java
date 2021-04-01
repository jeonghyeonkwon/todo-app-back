package com.givejeong.todo.dto.board;

import com.givejeong.todo.domain.ProgrammingRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Locale;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleTypeDto {
    private String title;
    private String kor;
    public RoleTypeDto(ProgrammingRole pr){
        this.title = pr.getProgrammingEnum().name().toLowerCase(Locale.ROOT);
        this.kor = pr.getProgrammingEnum().getKor();
    }
}

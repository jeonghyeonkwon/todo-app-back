package com.givejeong.todo.repository.querydsl;

import com.querydsl.core.Tuple;

import java.util.List;

public interface ProgrammingRoleRepositoryCustom {
    long studyTotalCount();

    List<Tuple> studyTop5Rank();


    long qnaTotalCount();

    List<Tuple> qnaTop5Rank();

    long totalCount();

    List<Tuple> Top5Rank();

}

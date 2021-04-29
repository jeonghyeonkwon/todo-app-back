package com.givejeong.todo.repository.querydsl;

import com.givejeong.todo.domain.QProgrammingRole;
import com.givejeong.todo.domain.QQna;
import com.givejeong.todo.domain.QStudy;
import com.givejeong.todo.dto.board.RoleTypeDto;
import com.givejeong.todo.dto.board.SampleListDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.givejeong.todo.domain.QProgrammingRole.*;
import static com.givejeong.todo.domain.QQna.*;
import static com.givejeong.todo.domain.QStudy.*;

@Repository

public class BoardRepository {
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public BoardRepository(EntityManager em){
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }
    //-------------------------------홈화면 최근 게시물 5개 제목 기술 타입만 반환-----------------------------
    public List<SampleListDto> recentStudyList(){
        List<SampleListDto> sampleListDtos = studySample();
        List<Long> studyId = sampleListDtos.stream().map(o->o.getId()).collect(Collectors.toList());

//        List<RoleTypeDto> roleTypeDtoList =queryFactory
//                .select(Projections.constructor(RoleTypeDto.class,programmingRole.study.id,programmingRole.programmingEnum ))
//                .from(programmingRole)
//                .join(programmingRole.study,study)
//                .where(programmingRole.study.id.in(studyId))
//                .fetch();

        List<RoleTypeDto> roleTypeDtoList = roleTypeDtoListInType("study",studyId);

        Map<Long,List<RoleTypeDto>> map = roleTypeDtoList.stream()
                .collect(Collectors.groupingBy(dto->dto.getBoardId()));
        sampleListDtos.forEach(o->o.setRoleTypeDtoList(map.get(o.getId())));
        return sampleListDtos;
    }
    public List<SampleListDto> recentQnaList(){
        List<SampleListDto> sampleListDtos = qnaSample();
        List<Long> qnaId = sampleListDtos.stream().map(o->o.getId()).collect(Collectors.toList());

//        List<RoleTypeDto> roleTypeDtoList =
//                queryFactory
//                        .select(Projections.constructor(RoleTypeDto.class,programmingRole.qna.id,programmingRole.programmingEnum ))
//                        .from(programmingRole)
//                        .join(programmingRole.qna,qna)
//                        .where(programmingRole.qna.id.in(qnaId))
//                        .fetch();
        List<RoleTypeDto> roleTypeDtoList = roleTypeDtoListInType("qna",qnaId);

        Map<Long,List<RoleTypeDto>> map = roleTypeDtoList.stream()
                .collect(Collectors.groupingBy(dto->dto.getBoardId()));
        sampleListDtos.forEach(o->o.setRoleTypeDtoList(map.get(o.getId())));
        return sampleListDtos;
    }
    //-------------------------------성능 최적화 Board(1) :RoleTypeDto(N) IN절 -----------------------------
    public List<RoleTypeDto> roleTypeDtoListInType(String type,List<Long> boardId){
        if(type.equals("study"))
            return queryFactory
                    .select(Projections.constructor(RoleTypeDto.class,programmingRole.study.id,programmingRole.programmingEnum ))
                    .from(programmingRole)
                    .join(programmingRole.study,study)
                    .where(programmingRole.study.id.in(boardId))
                    .fetch();
        else return
                queryFactory
                        .select(Projections.constructor(RoleTypeDto.class,programmingRole.qna.id,programmingRole.programmingEnum ))
                        .from(programmingRole)
                        .join(programmingRole.qna,qna)
                        .where(programmingRole.qna.id.in(boardId))
                        .fetch();
    }
    public List<RoleTypeDto> roleTypeDtoList(String type,Long boardId){
        if(type.equals("study"))
            return queryFactory
                    .select(Projections.constructor(RoleTypeDto.class,programmingRole.study.id,programmingRole.programmingEnum ))
                    .from(programmingRole)
                    .join(programmingRole.study,study)
                    .where(programmingRole.study.id.eq(boardId))
                    .fetch();
        else return
                queryFactory
                        .select(Projections.constructor(RoleTypeDto.class,programmingRole.qna.id,programmingRole.programmingEnum ))
                        .from(programmingRole)
                        .join(programmingRole.qna,qna)
                        .where(programmingRole.qna.id.eq(boardId))
                        .fetch();
    }
    //-------------------------------최근 게시물 5개--------------------------------------------------------

    public List<SampleListDto> studySample(){
        return queryFactory.select(Projections.constructor(SampleListDto.class, study.id,study.title)).from(study).orderBy(study.createdDate.desc()).limit(5).fetch();
    }
    public List<SampleListDto> qnaSample(){
        return queryFactory.select(Projections.constructor(SampleListDto.class, qna.id,qna.title)).from(qna).orderBy(qna.createdDate.desc()).limit(5).fetch();
    }
}

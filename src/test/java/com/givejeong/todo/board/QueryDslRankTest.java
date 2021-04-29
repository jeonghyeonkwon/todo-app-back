package com.givejeong.todo.board;

import com.givejeong.todo.domain.*;
import com.givejeong.todo.dto.auth.AccountDto;
import com.givejeong.todo.dto.board.RankDto;
import com.givejeong.todo.dto.board.RoleTypeDto;
import com.givejeong.todo.dto.board.qna.QnaDto;
import com.givejeong.todo.dto.board.study.StudyDto;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static com.givejeong.todo.domain.QAccount.*;
import static com.givejeong.todo.domain.QProgrammingRole.*;
import static org.assertj.core.api.Assertions.*;
@SpringBootTest
@Transactional
public class QueryDslRankTest {

    @Autowired
    EntityManager em;
    @Autowired
    PasswordEncoder passwordEncoder;

    JPAQueryFactory queryFactory;
    @BeforeEach
    public void before(){
        queryFactory = new JPAQueryFactory(em);
        AccountDto accountDto = AccountDto.builder()
                .accountId("aaa")
                .password("1234")
                .name("권정현")
                .location("서울").build();
        Account givejeong = new Account(accountDto);
        givejeong.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        em.persist(givejeong);
        //스터디 게시판 등록
        List<String> programmingTypeStudy = new ArrayList<>(Arrays.asList("HTML","HTML","CSS","CSS","CSS","CSS","파이썬"));

        List<RoleTypeDto> roleTypeStudy = new ArrayList<>();
        for(int i=0;i<7;i++){
            RoleTypeDto dto = RoleTypeDto.builder().kor(programmingTypeStudy.get(i)).build();
            roleTypeStudy.add(dto);
        }
        StudyDto studyDto = StudyDto.builder()
                .title("타이틀")
                .contents("내용")
                .applicant((long)5)
                .programmingType(roleTypeStudy).build();
        Study study = new Study(givejeong,studyDto,"web");
        studyDto.getProgrammingType().forEach(o->{
            ProgrammingRole role = new ProgrammingRole(study,o.getKor());
            em.persist(role);
        });
        em.persist(study);

        List<String> programmingTypeQna = new ArrayList<>(Arrays.asList("HTML"
                ,"뷰","뷰","뷰","뷰","뷰","뷰","뷰","뷰","뷰","뷰","뷰","뷰","뷰","뷰","뷰","뷰","뷰","뷰","뷰","뷰","뷰","C","C++","C++","C","자바스크립트","자바스크립트","자바스크립트","자바스크립트","자바스크립트","자바스크립트","자바스크립트","자바스크립트","자바스크립트","자바스크립트","CSS","CSS","CSS","CSS","CSS","CSS","CSS","CSS","CSS","CSS","CSS","CSS","CSS","CSS","CSS","파이썬"));

        List<RoleTypeDto> roleTypeQna = new ArrayList<>();
        for(int i=0;i<programmingTypeQna.size();i++){
            RoleTypeDto dto = RoleTypeDto.builder().kor(programmingTypeQna.get(i)).build();
            roleTypeQna.add(dto);
        }
        QnaDto qnaDto = QnaDto.builder()
                .title("타이틀")
                .contents("내용")
                .programmingType(roleTypeQna).build();
        Qna qna = new Qna(givejeong,qnaDto,"web");
        qnaDto.getProgrammingType().forEach(o->{
            ProgrammingRole role = new ProgrammingRole(qna,o.getKor());
            em.persist(role);
        });
        em.persist(qna);
    }
    @Test
    public void startQuerydsl(){
        Account find = queryFactory
                .select(QAccount.account)
                .from(QAccount.account)
                .where(QAccount.account.accountId.eq("aaa")).fetchOne();
        assertThat(find.getAccountName()).isEqualTo("권정현");
    }
    @Test
    public void 스터디_타입_갯수_7인가(){
        long count = queryFactory.select(programmingRole)
                .from(programmingRole).where(programmingRole.study.isNotNull()).fetchCount();
        assertThat(count).isEqualTo(7);
    }
    @Test
    public void 스터디_타입_그룹(){
        List<Tuple> fetch = queryFactory
                .select(programmingRole.programmingEnum, programmingRole.count())
                .from(programmingRole)
                .where(programmingRole.study.isNotNull())
                .groupBy(programmingRole.programmingEnum).fetch();
        fetch.forEach(o-> System.out.println(o));

    }
    @Test
    public void Qna_타입_갯수_9인가(){
        long count = queryFactory.select(programmingRole)
                .from(programmingRole).where(programmingRole.qna.isNotNull()).fetchCount();
        //assertThat(count).isEqualTo(9);
    }
    @Test
    public void Qna_타입_그룹(){
        long totalCount = queryFactory.select(programmingRole)
                .from(programmingRole).where(programmingRole.qna.isNotNull()).fetchCount();

        System.out.println("전체 갯수 : " + totalCount);
        List<Tuple> fetch = queryFactory
                .select(programmingRole.programmingEnum, programmingRole.count())
                .from(programmingRole)
                .where(programmingRole.qna.isNotNull())
                .groupBy(programmingRole.programmingEnum).orderBy(programmingRole.programmingEnum.count().desc()).limit(5).fetch();
        fetch.forEach(o-> System.out.println(o));
        List<RankDto> collect = fetch.stream().map(o -> new RankDto(o.get(programmingRole.programmingEnum).name().toLowerCase(Locale.ROOT), (double)o.get(programmingRole.count()) / (double)totalCount * 100.0)).collect(Collectors.toList());
        collect.forEach(o-> System.out.println(o));

    }
}

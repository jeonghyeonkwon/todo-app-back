package com.givejeong.todo;

import com.givejeong.todo.domain.Account;
import com.givejeong.todo.domain.Comment;
import com.givejeong.todo.domain.ProgrammingRole;
import com.givejeong.todo.domain.Study;
import com.givejeong.todo.dto.auth.AccountDto;
import com.givejeong.todo.dto.board.CommentDto;
import com.givejeong.todo.dto.board.study.StudyDto;
import com.givejeong.todo.dto.board.RoleTypeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class InitDb {
    private final InitService initService;

    @PostConstruct
    public void init(){
        initService.dbInitUserGivejeong();
        initService.dbInitUserGivejeong1();
    }
    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService{
        private final EntityManager em;
        private final PasswordEncoder passwordEncoder;
        public void dbInitUserGivejeong(){
            //유저 생성
            AccountDto accountDto = AccountDto.builder()
                    .accountId("givejeong")
                    .password("1234")
                    .name("권정현")
                    .location("대구").build();
            Account givejeong = new Account(accountDto);
            givejeong.setPassword(passwordEncoder.encode(accountDto.getPassword()));
            em.persist(givejeong);

            //스터디 모집 랜덤으로 생성
            List<String> programmingType =
                    new ArrayList<>(Arrays.asList(
                            "C",
                            "C++",
                            "자바",
                            "자바스크립트",
                            "HTML",
                            "CSS",
                            "스위프트",
                            "파이썬",
                            "코틀린",
                            "Ruby",
                            "오브젝트-C",
                            "스프링",
                            "node-js",
                            "node-js",
                            "php",
                            "ruby on rails",
                            "리액트네이티브",
                            "리액트",
                            "뷰",
                            "앵귤러",
                            "스벨트",
                            "JDBC",
                            "myBatis",
                            "JPA",
                            "관계형 DB",
                            "NoSQL",
                            "MySQL",
                            "오라클 DB"));
            Random rd = new Random();
            for(int i=0;i<13;i++){
                List<RoleTypeDto> roleType = new ArrayList<>();
                for(int j=0;j<2;j++){
                    RoleTypeDto dto = RoleTypeDto.builder().kor(programmingType.get(rd.nextInt(programmingType.size()))).build();

                    roleType.add(dto);
                }
                StudyDto studyDto = StudyDto.builder()
                        .title("게시글 입니다 여기 제목이 출력되는 곳이에요 " + i)
                        .contents("게시글 내용 입니다 여기 제목이 출력되는 곳이에요"+i+"번째" )
                        .applicant((long)rd.nextInt(45)+0)
                        .programmingType(roleType)
                        .build();


                Study study = new Study(givejeong, studyDto,"web");
                studyDto.getProgrammingType().forEach(o->{
                    ProgrammingRole role = new ProgrammingRole(study,o.getKor());
                    em.persist(role);
                });
                em.persist(study);

            }

        }
        public void dbInitUserGivejeong1(){
            AccountDto accountDto = AccountDto.builder()
                    .accountId("givejeong1")
                    .password("1234")
                    .name("김정현")
                    .location("대구").build();
            Account givejeong = new Account(accountDto);
            givejeong.setPassword(passwordEncoder.encode(accountDto.getPassword()));
            em.persist(givejeong);

            //스터디 모집 랜덤으로 생성
            List<String> programmingType =
                    new ArrayList<>(Arrays.asList(
                            "C",
                            "C++",
                            "자바",
                            "자바스크립트",
                            "HTML",
                            "CSS",
                            "스위프트",
                            "파이썬",
                            "코틀린",
                            "Ruby",
                            "오브젝트-C",
                            "스프링",
                            "node-js",
                            "node-js",
                            "php",
                            "ruby on rails",
                            "리액트네이티브",
                            "리액트",
                            "뷰",
                            "앵귤러",
                            "스벨트",
                            "JDBC",
                            "myBatis",
                            "JPA",
                            "관계형 DB",
                            "NoSQL",
                            "MySQL",
                            "오라클 DB"));
            Random rd = new Random();
            for(int i=0;i<10;i++){
                List<RoleTypeDto> roleType = new ArrayList<>();
                for(int j=0;j<2;j++){
                    RoleTypeDto dto = RoleTypeDto.builder().kor(programmingType.get(rd.nextInt(programmingType.size()))).build();

                    roleType.add(dto);
                }
                StudyDto studyDto = StudyDto.builder()
                        .title("게시글 입니다 여기 제목이 출력되는 곳이에요 " + i)
                        .contents("게시글 내용 입니다 여기 제목이 출력되는 곳이에요"+i+"번째" )
                        .applicant((long)rd.nextInt(45)+0)
                        .programmingType(roleType)
                        .build();


                Study study = new Study(givejeong, studyDto,"language");
                studyDto.getProgrammingType().forEach(o->{
                    ProgrammingRole role = new ProgrammingRole(study,o.getKor());
                    em.persist(role);
                });
                em.persist(study);
                for(int k = 0;k<=i;k++){
                    CommentDto dto = CommentDto.builder().comment("댓글을 달고있다"+k).build();
                    Comment comment = new Comment(givejeong,study,dto);
                    em.persist(comment);
                }

            }
        }
    }
}

package com.givejeong.todo;

import com.givejeong.todo.domain.*;
import com.givejeong.todo.dto.auth.AccountDto;
import com.givejeong.todo.dto.board.CommentDto;
import com.givejeong.todo.dto.board.qna.QnaDto;
import com.givejeong.todo.dto.board.study.StudyDto;
import com.givejeong.todo.dto.board.RoleTypeDto;
import com.givejeong.todo.dto.todo.TodoDto;
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
        initService.dbInitUserGivejeong2();
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
                    .tel("02-0000-0000")
                    .location("서울").build();
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
            for(int i=0;i<23;i++){
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
            for(int i=1;i<=50;i++){
                TodoDto dto;
                if(i<10){
                    dto = TodoDto.builder().title("할일 추가 하였습니다" + i+"길게 쓰기 길게 쓰기 길게 쓰기 길게 쓰기")
                            .content("할일의 내용입니다 이렇게 길게 쓰면 어떻게 출력될까요??? 할일의 내용입니다 이렇게 길게 쓰면 어떻게 출력될까요???할일의 내용입니다 이렇게 길게 쓰면 어떻게 출력될까요??" +
                                    "?할일의 내용입니다 이렇게 길게 쓰면 어떻게 출력될까요???할일의 내용입니다 이렇게 길게 쓰면 어떻게 출력될까요???할일의 내용입니다 이렇게 길게 쓰면 어떻게 출력될까요???할일의 내용입니다 이렇게 길게 쓰면 어떻게 출력될까요???할일의 내용입니다 이렇게 길게 쓰면 어떻게 출력될까요???"+i)
                            .goal("200"+i+"-05-09T09:39:38.974Z")
                            .build();
                }else{
                    dto = TodoDto.builder().title("할일 추가 하였습니다" + i)
                            .content("할일의 내용입니다 이렇게 길게 쓰면 어떻게 출력될까요??? 할일의 내용입니다 이렇게 길게 쓰면 어떻게 출력될까요???할일의 내용입니다 이렇게 길게 쓰면 어떻게 출력될까요??" +
                                    "?할일의 내용입니다 이렇게 길게 쓰면 어떻게 출력될까요???할일의 내용입니다 이렇게 길게 쓰면 어떻게 출력될까요???할일의 내용입니다 이렇게 길게 쓰면 어떻게 출력될까요???할일의 내용입니다 이렇게 길게 쓰면 어떻게 출력될까요???할일의 내용입니다 이렇게 길게 쓰면 어떻게 출력될까요???"+i)
                            .goal("20"+i+"-05-09T09:39:38.974Z")
                            .build();
                }
                Todo todo = new Todo(givejeong,dto);
                em.persist(todo);
            }

        }
        public void dbInitUserGivejeong1(){
            AccountDto accountDto = AccountDto.builder()
                    .accountId("givejeong1")
                    .password("1234")
                    .name("김정현")
                    .tel("053-0000-0000")
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
            for(int i=0;i<44;i++){
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
                for(int k = 0;k<=45;k++){
                    CommentDto dto = CommentDto.builder().comment("댓글을 달고있다 스터디 페이징"+k).build();
                    Comment comment = new Comment(givejeong,study,dto);
                    em.persist(comment);
                }

            }
        }

        public void dbInitUserGivejeong2(){
            AccountDto accountDto = AccountDto.builder()
                    .accountId("givejeong2")
                    .password("1234")
                    .name("이정현")
                    .tel("054-0000-0000")
                    .location("부산").build();
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
            for(int i=0;i<35;i++){
                List<RoleTypeDto> roleType = new ArrayList<>();
                for(int j=0;j<2;j++){
                    RoleTypeDto dto = RoleTypeDto.builder().kor(programmingType.get(rd.nextInt(programmingType.size()))).build();

                    roleType.add(dto);
                }

                QnaDto qnaDto = QnaDto.builder()
                        .title("QNA 게시글 입니다. "+i+"번째 타이틀 입니다.")
                        .contents("QNA 게시글 입니다. "+i+"번째 내용 입니다.")
                        .programmingType(roleType)
                        .build();

                Qna qna = new Qna(givejeong, qnaDto,"db");
                qnaDto.getProgrammingType().forEach(o->{
                    ProgrammingRole role = new ProgrammingRole(qna,o.getKor());
                    em.persist(role);
                });
                em.persist(qna);
                for(int k = 0;k<=33;k++){
                    CommentDto dto = CommentDto.builder().comment("댓글이 달렸습니다. 페이징"+k).build();
                    Comment comment = new Comment(givejeong,qna,dto);
                    em.persist(comment);
                }

            }
        }
    }
}

package com.givejeong.todo.domain;

import com.givejeong.todo.dto.board.study.StudyDto;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name="STUDY_TABLE")
public class Study extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="study_pkid")
    private Long id;

    //제목
    private String title;

    //내용
    @Lob
    private String content;

    @Enumerated(EnumType.STRING)
    private LocalEnum localEnum;

    //모집 인원
    private Long applicant;

    //조회수
    private Long hit;

    //프로그래밍 언어, 모바일, 웹, DB
    @Enumerated(EnumType.STRING)
    private FieldEnum fieldEnum;

    @Enumerated(EnumType.STRING)
    private StudyStatusEnum studyStatusEnum;
    /*----------------------- 생성자-------------------------*/
    protected Study(){}

    public Study(Account account , StudyDto dto, String section){
        createBoard(account);
        this.localEnum = account.getLocalEnum();
        this.title = dto.getTitle();
        this.content = dto.getContents();
        this.applicant = dto.getApplicant();
        this.fieldEnum = FieldEnum.find(section);
        this.studyStatusEnum = StudyStatusEnum.ING;
        this.hit = (long) 0;
    }

    /*----------------------- 일대다-------------------------*/

    @OneToMany(mappedBy = "study")
    private List<ProgrammingRole> programmingRoleList = new ArrayList<>();

    @OneToMany(mappedBy = "study")
    private List<Comment> commentList = new ArrayList<>();

    /*----------------------- 다대일-------------------------*/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="account_pkid")
    private Account account;


    /*----------------------- 연관관계 -------------------------*/
    // 게시글 생성
    public void createBoard(Account account){
        this.account = account;
        account.getStudyList().add(this);
    }
    /*----------------------- 메소드 -------------------------*/
    public void closingCompleted() {
        this.studyStatusEnum = StudyStatusEnum.FINISH;
    }
}

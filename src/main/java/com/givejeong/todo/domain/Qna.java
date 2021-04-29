package com.givejeong.todo.domain;

import com.givejeong.todo.dto.board.qna.QnaDto;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name="QNA_TABLE")
public class Qna extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Qna_pkid")
    private Long id;

    private String title;

    @Lob
    private String content;

    private Long hit;

    @Enumerated(EnumType.STRING)
    private FieldEnum fieldEnum;

    /*-----------------------------------생성자-----------------------------------------*/
    protected Qna(){}
    public Qna(Account account, QnaDto qnaDto, String section){
        createQna(account);
        this.title = qnaDto.getTitle();
        this.content = qnaDto.getContents();
        this.fieldEnum = FieldEnum.find(section);
        this.hit = (long) 0;
    }

    /*----------------------- 연관관계 -------------------------*/
    // 게시글 생성
    public void createQna(Account account){
        this.account = account;
        account.getQnaList().add(this);
    }
    /*----------------------- 메소드 -------------------------*/


    /*-----------------------------------일대다-----------------------------------------*/
    @OneToMany(mappedBy = "qna")
    private List<ProgrammingRole> programmingRoleList = new ArrayList<>();

    @OneToMany(mappedBy = "qna")
    private List<Comment> commentList = new ArrayList<>();

    /*-----------------------------------다대일-----------------------------------------*/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="account_pkid")
    private Account account;

}

package com.givejeong.todo.domain;

import com.givejeong.todo.dto.board.CommentDto;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Table(name="COMMENT_TABLE")
public class Comment extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="comment_pkid")
    private Long id;

    @Lob
    private String content;
    /*--------------------------------------생성자---------------------------------------*/
    protected Comment(){}
    public Comment(Account account, Study study, CommentDto dto){
        this.content = dto.getComment();
        StudyMapping(account,study);
    }

    public void StudyMapping(Account account,Study study){
        this.account = account;
        this.study = study;
        account.getCommentList().add(this);
        study.getCommentList().add(this);
    }


    /*--------------------------------------연관관계---------------------------------------*/

    /*--------------------------------------다대일---------------------------------------*/

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="study_pkid")
    private Study study;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="qna_pkid")
    private Qna qna;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="account_pkid")
    private Account account;
}

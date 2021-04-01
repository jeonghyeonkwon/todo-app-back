package com.givejeong.todo.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Table(name="PROGRAMMING_TABLE")
public class ProgrammingRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="programming_pkid")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="study_pkid")
    private Study study;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="qna_pkid")
    private Qna qna;

    @Enumerated(EnumType.STRING)
    private ProgrammingEnum programmingEnum;

    /*----------------------생성자---------------------------------*/
    protected ProgrammingRole(){}

    public ProgrammingRole(Study study,String str){
        this.programmingEnum = ProgrammingEnum.find(str);
        this.study = study;
        study.getProgrammingRoleList().add(this);
    }
    public ProgrammingRole(Qna qna, String str){
        this.programmingEnum = ProgrammingEnum.find(str);
        this.qna = qna;
        qna.getProgrammingRoleList().add(this);
    }
}

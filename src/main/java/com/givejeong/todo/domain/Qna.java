package com.givejeong.todo.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name="QNA_TABLE")
public class Qna {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Qna_pkid")
    private Long id;

    private String title;

    @Lob
    private String content;

    /*-----------------------------------생성자-----------------------------------------*/
    protected Qna(){}
    /*-----------------------------------일대다-----------------------------------------*/
    @OneToMany(mappedBy = "qna")
    private List<ProgrammingRole> programmingRoleList = new ArrayList<>();
    /*-----------------------------------다대일-----------------------------------------*/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="account_pkid")
    private Account account;

}

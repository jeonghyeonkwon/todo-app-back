package com.givejeong.todo.domain;

import com.givejeong.todo.dto.auth.AccountDto;
import com.givejeong.todo.dto.auth.UpdateDto;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Getter
@Table(name="ACCOUNT_TABLE")
public class Account extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="account_pkid")
    private Long id;

    private String accountId;

    private String accountPw;

    private String accountName;

    @OneToMany(mappedBy = "account")
    private List<Todo> todoList =new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private LocalEnum localEnum;
    //권한 설정
    private String role;
    /*----------------------- 생성-------------------------*/
    protected Account(){}

    public Account(AccountDto accountDto){
        this.accountId = accountDto.getAccountId();

        this.accountName = accountDto.getName();
        this.localEnum = LocalEnum.find(accountDto.getLocation());
        this.role = "ROLE_USER";

    }
    public List<String> getRoleList(){
        if(this.role.length()>0){
            return Arrays.asList(this.role.split(","));
        }
        return new ArrayList<>();
    }
    public void setPassword(String accountPw) {
        this.accountPw = accountPw;
    }

    /*----------------------- 일대다-------------------------*/
    @OneToMany(mappedBy = "account")
    private List<Study> studyList = new ArrayList<>();

    @OneToMany(mappedBy = "account")
    private List<Qna> qnaList = new ArrayList<>();

    @OneToMany(mappedBy = "account")
    private List<Comment> commentList = new ArrayList<>();

    public void update(String update, UpdateDto dto) {
        switch (update){
            case "name":
                this.accountName = dto.getName();
            case"local":
                this.localEnum = LocalEnum.find(dto.getLocal());
        }

    }

    public void updatePassword(String encode) {
        this.accountPw = encode;

    }
}

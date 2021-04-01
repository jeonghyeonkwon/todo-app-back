package com.givejeong.todo.domain;

public enum StudyStatusEnum {
    ING("진행중"),
    FINISH("마감");
    private String kor;

    StudyStatusEnum(String kor){
        this.kor = kor;
    }
}

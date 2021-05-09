package com.givejeong.todo.domain;

public enum TodoEnum {
    EXPECTED("진행중"),
    TODAY("오늘 마감"),
    ACHIEVE("달성"),
    FAILURE("실패");
    private String kor;
    TodoEnum(String kor){
        this.kor = kor;
    }

    public String getKor() {
        return kor;
    }

}

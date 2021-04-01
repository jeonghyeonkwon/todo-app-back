package com.givejeong.todo.domain;

import java.util.stream.Stream;

public enum FieldEnum {
    //IT 분야
    LANGUAGE("언어","language"),
    MOBILE("모바일","mobile"),
    WEB("웹 개발","web"),
    DB("데이터 베이스","db");
    private String kor;
    private String en;

    public String getKor() {
        return kor;
    }
    public String getEn(){return en;}
    FieldEnum(String kor,String en){
        this.kor = kor;
        this.en = en;
    }

    public static FieldEnum find(String str){
        return Stream.of(values()).filter(type->type.en.equals(str)).findAny().orElse(null);
    }
}
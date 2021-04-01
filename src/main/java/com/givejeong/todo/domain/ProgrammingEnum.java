package com.givejeong.todo.domain;

import java.util.stream.Stream;

public enum ProgrammingEnum {
    C("C"),
    CP("C++"),
    JAVA("자바"),
    JAVASCRIPT("자바스크립트"),
    HTML("HTML"),
    CSS("CSS"),
    SWIFT("스위프트"),
    PYTHON("파이썬"),
    KOTILIN("코틀린"),
    RUBY("Ruby"),
    OBJECTC("오브젝트-C"),
    SPRING("스프링"),
    NODEJS("node-js"),
    DJANGO("node-js"),
    PHP("php"),
    RUBYON("ruby on rails"),
    REACTN("리액트네이티브"),
    REACT("리액트"),
    VUE("뷰"),
    ANGULER("앵귤러"),
    SVELET("스벨트"),
    JDBC("JDBC"),
    MYBATIS("myBatis"),
    JPA("JPA"),
    RDB("관계형 DB"),
    NOSQL("NoSQL"),
    MYSQL("MySQL"),
    ORACLE("오라클 DB");

    private String kor;
    public String getKor() {
        return kor;
    }
    ProgrammingEnum(String kor){
        this.kor = kor;
    }

    public static ProgrammingEnum find(String kor){
    return Stream.of(values()).filter(type->type.kor.equals(kor)).findAny().orElse(null);
    }
}

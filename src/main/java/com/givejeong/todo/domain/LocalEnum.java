package com.givejeong.todo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public enum LocalEnum {
    ALL("전체"),
    SEOUL("서울"),
    GYEONGGI("경기"),
    BUSAN("부산"),
    DEAGU("대구"),
    INCHEON("인천"),
    GWANGJU("광주"),
    DAEJEON("대전"),
    ULSAN("울산"),
    CHUNGCHEONBUK("충북"),
    CHUNGCHEONNAM("충남"),
    JEOLLANAM("전남"),
    JEOLLABUK("전북"),
    GYEONGSANGBUK("경북"),
    GYEONGSANGNAM("경남"),
    JEJU("제주"),
    GANGWON("강원");
    private String kor;
    LocalEnum(String kor){
        this.kor = kor;
    }

    public static LocalEnum find(String local){
        return Stream.of(values())
                .filter(localEnum -> localEnum.kor.equals(local))
                .findAny()
                .orElse(ALL);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class LocalDto<T>{
        private T local;

    }
    //회원가입시 지역리스트
    public static List<String> createAccountLocalList(){
        return Stream.of(LocalEnum.values())
                .map(local->local.kor)
                .filter(s -> s!=LocalEnum.ALL.kor)
                .collect(Collectors.toList());

    }


}

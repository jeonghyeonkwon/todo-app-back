package com.givejeong.todo.controller;

import com.givejeong.todo.domain.LocalEnum;
import com.givejeong.todo.dto.board.CommentDto;
import com.givejeong.todo.dto.board.study.StudyDto;
import com.givejeong.todo.service.StudyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class StudyController {
    private final StudyService studyService;

    @GetMapping("/locallist")
    public ResponseEntity 스터디_지역(){
        Map data = new HashMap<>();
        data.put("local", LocalEnum.localList());
        return ResponseEntity.ok().body(data);
    }
    @GetMapping("/api/study")
    public ResponseEntity 스터디_게시판(@RequestParam("section") String section,@RequestParam(value="local",defaultValue = "ALL") LocalEnum localEnum, Pageable pageable){
        System.out.println("지역 " +localEnum.name());
        System.out.println("컨트롤러 section : "+ section);

        System.out.println("offset : " + pageable.getOffset());
        System.out.println("size : " + pageable.getPageSize());
        return studyService.findStudy(section,localEnum,pageable);
    }

    //이거 ?section으로 바꿔야함
    @PostMapping("/api/study")
    public ResponseEntity<Long> 게시글_작성(@RequestParam("section")String section, @RequestBody StudyDto dto){
        return studyService.createStudy(section,dto);

    }

    @GetMapping("/api/study/{id}")
    public ResponseEntity 게시글_자세히(@PathVariable("id") Long id){
        System.out.println("여기 접근 "+id);
        return studyService.findStudyDetail(id);
    }
    @GetMapping("/api/study/{id}/comment")
    public ResponseEntity Study_댓글_페이징(@PathVariable("id") Long boardId ,Pageable pageable){
        return studyService.commentList(boardId,pageable);
    }
    @PatchMapping("/api/study/{id}")
    public ResponseEntity 마감_하기(@PathVariable("id") Long id){
        System.out.println("마감하기 ");
        return studyService.closing(id);
    }
    @PostMapping("/api/study/{id}")
    public ResponseEntity 댓글_달기(@PathVariable("id") Long id, @RequestBody CommentDto dto){


        return studyService.createComment(id,dto);
    }

}

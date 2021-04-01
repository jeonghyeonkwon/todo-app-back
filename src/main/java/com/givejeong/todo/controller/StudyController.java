package com.givejeong.todo.controller;

import com.givejeong.todo.dto.board.CommentDto;
import com.givejeong.todo.dto.board.study.StudyDto;
import com.givejeong.todo.service.StudyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class StudyController {
    private final StudyService studyService;

    @GetMapping("/api/study")
    public ResponseEntity 스터디_게시판(@RequestParam("section") String section, @PageableDefault(sort="id" ,direction = Sort.Direction.DESC)Pageable pageable){
        System.out.println("컨트롤러 section : "+ section);
        return studyService.findStudy(section,pageable);
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

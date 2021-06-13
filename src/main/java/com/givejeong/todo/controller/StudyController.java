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
@RequestMapping("/api")
public class StudyController {
    private final StudyService studyService;


    @GetMapping("/study")
    public ResponseEntity 스터디_게시판(@RequestParam("section") String section,@RequestParam(value="local",defaultValue = "ALL") LocalEnum localEnum, Pageable pageable){

        return studyService.findStudy(section,localEnum,pageable);
    }


    @PostMapping("/study/{skill}/{userId}")
    public ResponseEntity<Long> 게시글_작성(@PathVariable String skill, @PathVariable Long userId,@RequestBody StudyDto dto){
        return studyService.createStudy(skill,userId,dto);

    }

    @GetMapping("/study/{id}")
    public ResponseEntity 게시글_자세히(@PathVariable("id") Long id){

        return studyService.findStudyDetail(id);
    }
    @GetMapping("/study/{id}/comment")
    public ResponseEntity Study_댓글_페이징(@PathVariable("id") Long boardId ,Pageable pageable){
        return studyService.commentList(boardId,pageable);
    }
    @PatchMapping("{userId}/study/{boardId}/closing")
    public ResponseEntity 마감_하기(@PathVariable Long userId,@PathVariable Long boardId){

        return studyService.closing(userId,boardId);
    }
    @PostMapping("{userId}/study/{boardId}")
    public ResponseEntity 댓글_달기(@PathVariable Long userId,@PathVariable Long boardId, @RequestBody CommentDto dto){
        return studyService.createComment(userId,boardId,dto);
    }

}

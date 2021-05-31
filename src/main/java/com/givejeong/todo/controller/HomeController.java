package com.givejeong.todo.controller;


import com.givejeong.todo.service.QnaService;
import com.givejeong.todo.service.RankService;
import com.givejeong.todo.service.StudyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class HomeController {
    private final StudyService studyService;
    private final QnaService qnaService;
    private final RankService rankService;

    @GetMapping("/home")
    public ResponseEntity 스터디_QNA_게시글보기(){
        Map map = new HashMap<>();
        map.put("rank",rankService.top5Rank());
        map.put("study",studyService.recentList());
        map.put("qna",qnaService.recentList());
        return new ResponseEntity(map, HttpStatus.OK);
    }
}

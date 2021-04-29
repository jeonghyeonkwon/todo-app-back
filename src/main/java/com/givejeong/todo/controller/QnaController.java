package com.givejeong.todo.controller;

import com.givejeong.todo.dto.board.CommentDto;
import com.givejeong.todo.dto.board.qna.QnaDto;
import com.givejeong.todo.service.QnaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class QnaController {
    private final QnaService qnaService;

    @GetMapping("/api/qna")
    public ResponseEntity QNA_게시판(@RequestParam("section") String section, Pageable pageable){
        return qnaService.findQna(section,pageable);
    }
    @PostMapping("/api/qna")
    public ResponseEntity<Long> QNA_게시글_작성(@RequestParam("section") String section, @RequestBody QnaDto dto){
        return qnaService.createQna(section,dto);
    }

    @GetMapping("/api/qna/{id}")
    public ResponseEntity QNA_게시글_자세히(@PathVariable("id") Long id){
        return qnaService.findQnaDetail(id);
    }
    @GetMapping("/api/qna/{id}/comment")
    public ResponseEntity QNA_댓글_페이징(@PathVariable("id") Long id,Pageable pageable){
        return qnaService.commentList(id,pageable);
    }
    @PostMapping("/api/qna/{id}")
    public ResponseEntity 댓글_달기(@PathVariable("id") Long id, @RequestBody CommentDto dto){
        return qnaService.createComment(id,dto);
    }
}
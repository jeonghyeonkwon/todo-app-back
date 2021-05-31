package com.givejeong.todo.service;

import com.givejeong.todo.domain.*;
import com.givejeong.todo.dto.ErrorDto;
import com.givejeong.todo.dto.board.CommentDto;
import com.givejeong.todo.dto.board.ListFormDto;
import com.givejeong.todo.dto.board.RoleTypeDto;
import com.givejeong.todo.dto.board.qna.QnaDto;
import com.givejeong.todo.dto.board.qna.QnaListDto;
import com.givejeong.todo.dto.board.SampleListDto;
import com.givejeong.todo.repository.AccountRepository;
import com.givejeong.todo.repository.CommentRepository;
import com.givejeong.todo.repository.ProgrammingRoleRepository;
import com.givejeong.todo.repository.QnaRepository;
import com.givejeong.todo.repository.querydsl.BoardRepository;
import com.givejeong.todo.security.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QnaService {
    private final AccountRepository accountRepository;
    private final QnaRepository qnaRepository;
    private final ProgrammingRoleRepository programmingRoleRepository;
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public ResponseEntity<Long> createQna(String section, QnaDto dto) {
        String accountId = SecurityUtil.getCurrentUsername().get();
        Account account = accountRepository.findByAccountId(accountId).get();
        Qna qna = new Qna(account,dto,section);
        dto.getProgrammingType().forEach(o->{
            ProgrammingRole role = new ProgrammingRole(qna,o.getKor());
            programmingRoleRepository.save(role);
        });
        Qna save = qnaRepository.save(qna);
        return new ResponseEntity<>(save.getId(), HttpStatus.CREATED);
    }

    public ResponseEntity findQna(String section, Pageable pageable) {
        Page<QnaListDto> qnaListDtos = qnaRepository.qnaListDtos(section, pageable);
        List<Long> qnaIdList = qnaListDtos.stream().map(o -> o.getId()).collect(Collectors.toList());
        List<RoleTypeDto> roleTypeDtoList = boardRepository.roleTypeDtoListInType("qna", qnaIdList);
        Map<Long,List<RoleTypeDto>> map = roleTypeDtoList.stream().collect(Collectors.groupingBy(dto->dto.getBoardId()));
        qnaListDtos.forEach(o->o.setRoleTypeDtoList(map.get(o.getId())));
        return new ResponseEntity(qnaListDtos,HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity findQnaDetail(Long id) {
        qnaRepository.hitUp(id);

        Optional<QnaDto> optionalQnaDto = qnaRepository.findByIdx(id);
        if(!optionalQnaDto.isPresent()){
            return new ResponseEntity<>(new ErrorDto("해당 게시글을 조회할 수 없습니다."),HttpStatus.NOT_FOUND);
        }
        QnaDto qnaDto = optionalQnaDto.get();
        List<RoleTypeDto> roleTypeDtoList = boardRepository.roleTypeDtoList("qna", qnaDto.getId());
        qnaDto.setProgrammingType(roleTypeDtoList);
        //List<CommentDto> commentList = board.getCommentList().stream().map(d->new CommentDto(d)).collect(Collectors.toList());
        return new ResponseEntity<>(qnaDto,HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity createComment(Long id, CommentDto dto) {
        String accountId = SecurityUtil.getCurrentUsername().get();
        Account account = accountRepository.findByAccountId(accountId).get();
        Qna qna = qnaRepository.findById(id).get();
        Comment comment = new Comment(account,qna,dto);
        Comment save = commentRepository.save(comment);
        return new ResponseEntity<>(save.getId(),HttpStatus.CREATED);
    }

    public List<SampleListDto> recentList() {
        return boardRepository.recentQnaList();

    }
    public ResponseEntity commentList(Long boardId, Pageable pageable) {
        Page<CommentDto> commentDtos = commentRepository.qnaCommentList(pageable, boardId);
        return new ResponseEntity<>(commentDtos,HttpStatus.OK);
    }
}

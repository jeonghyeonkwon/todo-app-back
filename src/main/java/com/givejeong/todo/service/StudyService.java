package com.givejeong.todo.service;

import com.givejeong.todo.domain.*;
import com.givejeong.todo.dto.*;
import com.givejeong.todo.dto.board.CommentDto;
import com.givejeong.todo.dto.board.ListFormDto;
import com.givejeong.todo.dto.board.RoleTypeDto;
import com.givejeong.todo.dto.board.SampleListDto;
import com.givejeong.todo.dto.board.study.StudyDto;
import com.givejeong.todo.dto.board.study.StudyListDto;
import com.givejeong.todo.repository.AccountRepository;
import com.givejeong.todo.repository.CommentRepository;
import com.givejeong.todo.repository.ProgrammingRoleRepository;
import com.givejeong.todo.repository.StudyRepository;
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
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StudyService {
    private final StudyRepository studyRepository;
    private final AccountRepository accountRepository;
    private final ProgrammingRoleRepository programmingRoleRepository;
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public ResponseEntity<Long> createStudy(String section, Long userId,StudyDto dto){
        String accountId = SecurityUtil.getCurrentUsername().get();
        Account user = accountRepository.findByAccountId(accountId).get();
        if(!user.getId().equals(userId)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Study study = new Study(user,dto,section);
        dto.getProgrammingType().forEach(o->{
            ProgrammingRole role = new ProgrammingRole(study,o.getKor());
            programmingRoleRepository.save(role);
        });
        Study save = studyRepository.save(study);

        return new ResponseEntity(save.getId(), HttpStatus.CREATED);
    }

    @Transactional
    public ResponseEntity findStudyDetail(Long id) {
        studyRepository.hitUp(id);
        Optional<StudyDto> optionalStudyDto = studyRepository.findByIdx(id);
        if(!optionalStudyDto.isPresent()){
             return new ResponseEntity<>(new ErrorDto("해당 게시글을 조회할 수 없습니다."),HttpStatus.NOT_FOUND);
        }
        StudyDto studyDto = optionalStudyDto.get();
        List<RoleTypeDto> roleTypeDtoList = boardRepository.roleTypeDtoList("study", studyDto.getId());
        studyDto.setProgrammingType(roleTypeDtoList);
        return new ResponseEntity<>(studyDto,HttpStatus.OK);
    }


    @Transactional
    public ResponseEntity closing(Long userId,Long boardId) {
        Study study = studyRepository.findById(boardId).get();
        Account account = study.getAccount();
        if(!account.getId().equals(userId)) return new ResponseEntity(HttpStatus.BAD_REQUEST);
        
        study.closingCompleted();
        studyRepository.save(study);
        return new ResponseEntity<>(study.getId(),HttpStatus.OK);
    }

    //게시판 리스트
    public ResponseEntity findStudy(String section,LocalEnum localEnum,Pageable pageable) {
        Page<StudyListDto> studyListDtos = studyRepository.studyListDtos(section, localEnum, pageable);
        List<Long> studyIdList = studyListDtos.stream().map(o -> o.getId()).collect(Collectors.toList());
        List<RoleTypeDto> roleTypeDtoList = boardRepository.roleTypeDtoListInType("study", studyIdList);
        Map<Long,List<RoleTypeDto>> map = roleTypeDtoList.stream().collect(Collectors.groupingBy(dto->dto.getBoardId()));
        studyListDtos.forEach(o->o.setRoleTypeDtoList(map.get(o.getId())));
        return new ResponseEntity(studyListDtos,HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity createComment(Long userId,Long id, CommentDto dto) {

        String accountId = SecurityUtil.getCurrentUsername().get();
        Account account = accountRepository.findByAccountId(accountId).get();
        if(!userId.equals(account.getId())) return new ResponseEntity(HttpStatus.BAD_REQUEST);
        Study study = studyRepository.findById(id).get();
        Comment comment = new Comment(account,study,dto);

        Comment save = commentRepository.save(comment);

        return new ResponseEntity(save.getId(),HttpStatus.CREATED);
    }

    public List<SampleListDto> recentList() {

        return boardRepository.recentStudyList();
    }

    public ResponseEntity commentList(Long boardId, Pageable pageable) {
        Page<CommentDto> commentDtos = commentRepository.studyCommentList(pageable, boardId);
        return new ResponseEntity<>(commentDtos,HttpStatus.OK);
    }
}

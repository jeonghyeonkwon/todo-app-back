package com.givejeong.todo.service;

import com.givejeong.todo.domain.*;
import com.givejeong.todo.dto.*;
import com.givejeong.todo.dto.board.CommentDto;
import com.givejeong.todo.dto.board.ListFormDto;
import com.givejeong.todo.dto.board.RoleTypeDto;
import com.givejeong.todo.dto.board.study.StudyDto;
import com.givejeong.todo.dto.board.study.StudyListDto;
import com.givejeong.todo.repository.AccountRepository;
import com.givejeong.todo.repository.CommentRepository;
import com.givejeong.todo.repository.ProgrammingRoleRepository;
import com.givejeong.todo.repository.StudyRepository;
import com.givejeong.todo.security.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    @Transactional
    public ResponseEntity<Long> createStudy(String section, StudyDto dto){
        String accountId = SecurityUtil.getCurrentUsername().get();
        Account user = accountRepository.findByAccountId(accountId);
        Study study = new Study(user,dto,section);
        dto.getProgrammingType().forEach(o->{
            ProgrammingRole role = new ProgrammingRole(study,o.getKor());
            programmingRoleRepository.save(role);
        });
        Study save = studyRepository.save(study);

        return new ResponseEntity(save.getId(), HttpStatus.CREATED);
    }

    public ResponseEntity findStudyDetail(Long id) {
        Optional<Study> study = studyRepository.findById(id);
        if(!study.isPresent()){
             return new ResponseEntity<>(new ErrorDto("해당 게시글을 조회할 수 없습니다."),HttpStatus.NOT_FOUND);
        }
        Study board = study.get();
        List<RoleTypeDto> collect = board.getProgrammingRoleList().stream().map(data -> new RoleTypeDto(data)).collect(Collectors.toList());
        Account account = board.getAccount();
        List<CommentDto> commentList = board.getCommentList().stream().map(d -> new CommentDto(d)).collect(Collectors.toList());
        return new ResponseEntity<>(new StudyDto(board,account,collect,commentList),HttpStatus.OK);
    }


    @Transactional
    public ResponseEntity closing(Long id) {
        Study study = studyRepository.findById(id).get();
        study.closingCompleted();
        studyRepository.save(study);
        return new ResponseEntity<>(study.getId(),HttpStatus.OK);
    }
    public ResponseEntity findStudy(String section,Pageable pageable) {
        FieldEnum fieldEnum = FieldEnum.find(section);
        System.out.println("서비스 section: " + section);
        System.out.println(fieldEnum);
        Page<Study> study = studyRepository.findSectionStudy(pageable,fieldEnum);

        System.out.println("getTotalPage : " + study.getTotalPages()+" getTotalElement : "+study.getTotalElements());
        System.out.println("-------------------------------슬라이스 부모---------------------------------------");
        System.out.println("getNumber : " + study.getNumber()+" isFirst : " + study.isFirst() + " isLast : " + study.isLast());
        System.out.println("hasNext : " + study.hasNext() + " hasPrevious : " + study.hasPrevious() );
        List<StudyListDto> list = study.stream().map(o -> new StudyListDto(o)).collect(Collectors.toList());

        ListFormDto dto = new ListFormDto(study,list);
        return new ResponseEntity(dto,HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity createComment(Long id, CommentDto dto) {

        String userId = SecurityUtil.getCurrentUsername().get();
        Account account = accountRepository.findByAccountId(userId);

        Study study = studyRepository.findById(id).get();
        Comment comment = new Comment(account,study,dto);

        Comment save = commentRepository.save(comment);

        return new ResponseEntity(save.getId(),HttpStatus.CREATED);
    }
}

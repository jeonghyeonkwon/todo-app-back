# 스터디 TODO Back-end[미완성] (21-04-30기준)
[프론트엔드 (React)](https://github.com/jeonghyeonkwon/todo-app-front)

## 주요 기술
Spring-boot, H2, Spring Data JPA, Querydsl, Spring Security, JWT([참고 코드](https://github.com/SilverNine/spring-boot-jwt-tutorial))

## Controller(현재 상황)
Method|Path|이름|jwt 토큰 여부|내용(보완 사항)|
---|---|---|---|---|
GET|/home|홈화면|X|스터디,QNA,스터디+QNA 많이 검색한 기술 순위 및 최근 게시물 5개|
GET|/api/auth/check|로그인 체크|X|리덕스를 이용하여 새로고침을 실행해도 jwt 토큰을 이용하여 로그인 상태가 유지되도록 함|
POST|/authenticate|로그인|토큰 발행|
GET|/register|회원가입 폼|X|회원 가입 폼에 지역리스트를 보내줌|
GET|/validate|아이디 검증|X|회원 가입 중 중복된 이름이 있는지 검증|
POST|/register|회원 가입 완료|X|회원 가입 데이터를 받아 db에 저장|
GET|api/myinfo|회원 정보 수정 폼|O|로그인된 회원 정보(아이디,이름,지역)만 출력|
PATCH|/api/myinfo/{id}|회원 정보 수정|O|회원 정보 수정|
GET|/locallist|지역 리스트 반환|X|지역(서울,대구,대전,...등)|
GET|ex) /api/study?section=web&page=24|게시물 리스트|X|모바일,웹,DB,프로그래밍 언어에 따라 페이징처리로 리스트 응답|
POST|/api/study|스터디 게시글 작성|O|
GET|/api/study/{id}|스터디 게시글 자세히|O|
PATCH|/api/study/{id}|스터디 모집 마감|O|현재 게시글을 모집 마감으로 변경 보안을 위해 게시글의 작성자와 마감한 작성자의 비교 로직 작성 필요
POST|/api/study/{id}|댓글 작성|O|
GET|/api/study/{id}/comment|댓글 리스트 반환|댓글 페이징 처리|
GET|ex) /api/qna?section=web&page=24|게시물 리스트|X|모바일,웹,DB,프로그래밍 언어에 따라 페이징처리로 리스트 응답|
POST|/api/qna|Q&A 게시글 작성|O|
GET|/api/qna/{id}|Q&A 게시글 자세히|O|
POST|/api/qna/{id}|댓글 작성|O|
GET|/api/todo|todo 리스트 출력|O|토큰을 이용한 로그인 유저의 todo리스트 반환|
POST|/api/todo|TODO 추가|O|
PATCH|api/todo/{id}|TODO 성공 처리|O|현재 진행중 또는 오늘 마감인 TODO 성공으로 변경




# 스터디 TODO Back-end[진행중] (21-06-14기준)
[프론트엔드 (React)](https://github.com/jeonghyeonkwon/todo-app-front)

## 주요 기술
Spring-boot, H2, Spring Data JPA, Querydsl, Spring Security, JWT([참고 코드](https://github.com/SilverNine/spring-boot-jwt-tutorial))

## Controller(현재 상황)
Method|Path(/api)|이름|jwt 토큰 여부|내용(보완 사항)|
---|---|---|---|---|
GET|/home|홈화면|X|스터디,QNA,스터디+QNA 많이 검색한 기술 순위 및 최근 게시물 5개|
GET|/locallist|지역 리스트|X|회원가입,스터디 게시판 이용의 지역 리스트 반환|
GET|/auth/check|로그인 체크|X|리덕스를 이용하여 새로고침을 실행해도 jwt 토큰을 이용하여 로그인 상태 관리|
POST|/authenticate|로그인|jwt 토큰 발행||
GET|/validate|아이디 검증|X|회원 가입 중 중복된 이름이 있는지 검증|
POST|/register|회원 가입 완료|X|회원 가입 데이터를 받아 db에 저장|
GET|/myinfo|회원 정보 수정 폼|O|로그인된 회원 정보(비밀번호 제외) 출력|
PATCH|/myinfo/{id}|회원 정보 수정|O|회원 정보 수정|
GET|/search-id|아이디 찾기|X|회원가입한 회원의 이름, 전화번호로 검색
GET|/search-pw|비밀번호 찾기|X|회원가임한 아이디, 이름 전화번호로 검색해 있으면 pk 넘겨줌|
PATCH|/search-pw/{id}|비밀번호 변경|X|새로운 비밀번호로 수정|
GET|/{userId}/todo?status={status}&page={page}|todo 리스트|O|status는 진행중, 실패, 성공과 page를 받아 리스트 출력
POST|/{userId}/todo|todo 추가|O|dto를 받아 db 저장
PATCH|/{userId}/todo/{cardId}|todo 성공처리|O|진행중인 todo 성공 처리
GET|/qna?section=${skill}&page=${page}|qna 스터디 리스트|X|skill은 언어,웹,모바일,db의 값과 page를 받아 게시글 출력
GET|/study?section=${skill}&page=${page}&local=${local}|study 스터디 리스트|X|skill은 언어,웹,모바일,db의 값과 page,지역을 요청 받아 게시글 출력
POST|/${boardType}/${skill}/${userId}|게시글 작성|O|boardType은 study,qna skill은 언어,웹,모바일,db과 게시글 내용을 받아 db에 저장
GET|/${boardType}/${boardId}|게시글 자세히|O|
GET|/${boardType}/${boardId}/comment?page=${page}|게시글 댓글 리스트|O|
POST|/${userId}/${boardType}/${boardId}|게시글 댓글 쓰기|O|
PATCH|/${userId}/study/${boardId}/closing|스터디 게시글 마감|O|글 작성자이고 모집 중인 스터디 게스글 마감처리

### ERD
![데이터 베이스](https://user-images.githubusercontent.com/38342219/119259867-35e82300-bc0b-11eb-954b-afd40755c425.PNG)


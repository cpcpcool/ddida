<img src="https://github.com/cpcpcool/ddida/assets/143718896/ac22e037-bd62-413e-a0be-bf23d7954282" width="450" >
<br><br>

### 배포 주소 : http://3.34.49.62:9999

# 뛰다(多)
### 스프링부트와 JPA를 활용한 RestAPI(OpenAPI 포함) 서버 개발 및 배포
### 주제 : 시설물 관리 및 대여 시스템
#### 프로젝트 기간 : 2024.01.15 - 2024.02.05

<br><br>

## 프로젝트 소개
#### OpenAPI로 받아 온 체육 시설물 정보로,
#### 사용자는 원하는 시설을 예약하고 관련된 문의나 후기를 남기고 
#### 관리자는 시설물 정보와 사용자의 문의/후기를 관리할 수 있도록 서비스를 제공하는
#### 체육시설 예약 사이트입니다.

<br><br>

## 상세 기능
### [이용자]
#### - 로그인/회원가입 : 기존 아이디와 비밀번호 입력 후 로그인 (자동로그인 체크 시 기능 사용) / 회원가입(새로운 사용자 계정 생성)
#### - 메인 화면 : 시설 검색(시설분류 카테고리 필수 선택 후 검색)/추천 시설
#### - 시설 검색/예약 : 시설 검색(시설분류 카테고리 필수 선택 후 검색 -> 시설 상세 -> 예약 -> 예약 내역으로 이동
#### - 뛰맵 : 시설 지도 뷰 (지도 맵으로 시설 위치 정보 확인)
#### - QnA : 문의 목록 (검색 및 등록) -> 문의 상세 (관리자가 남긴 답변 확인, 해당 로그인 회원 작성글 수정/삭제)
#### - 마이페이지 : 예약 내역 목록(시설명 또는 예약일로 검색) -> 예약 상세 페이지(이용 대기 상태 : 예약 취소 가능 / 이용 완료 상태 : 후기 작성 가능)

### [관리자]
#### - 로그인/회원가입 : 기존 아이디와 코드 입력 후 관리자 로그인 / 회원가입(새로운 관리자 계정 생성)
#### - QnA : 문의 목록(문의글 제목 또는 회원 아이디로 검색) -> 문의 상세(답변 기능) 답변 등록 시 해당 유저의 문의글 페이지에 표시
#### - 회원 관리 : 회원 목록(아이디/이름/권한상태/가입일로 검색) -> 회원 정보 상세(해당 회원의 정보와 문의글 내역 확인)
#### - 시설물 정보 관리 : 시설물 목록(코드/종류/시설명/주소/관리주체로 검색) -> 시설 정보 상세(해당 시설의 상세 정보 확인)
<br><br>

## **개발환경**<br>
|   |    |
----|----|
**OS**|Windows 11|
**IDE**|Spring Tool Suite 4 4.21.0.RELEASE|
**Front-end**|HTML<br>CSS<br>JavaScript<br>Bootstrap 5.3|
**Back-end**|Java 17<br>Spring Boot 3.2.2<br>Spring Security 6<br>Spring data JPA 3.2.2<br>lombok 1.18.30|
**Database**|MySQL 8.0.23|

<br><br>

## **팀원별 업무 분담**<br>
|    |**박재용(팀장)**|**김민혜(팀원)**|**노윤건(팀원)**|
|----|---|---|---|
|**Front**|**페이지**<br>- 로그인<br>- [이용자/관리자] 회원가입 <br>- [이용자] 메인 페이지 <br>- [이용자] 뛰맵(시설지도)<br>- KAKAO Maps API 연동<br>**컴포넌트**<br>- [이용자/관리자] header<br>- [이용자/관리자] footer|**페이지**<br>- [이용자] 문의 목록/상세/등록<br>- [이용자] 예약 목록/상세<br>-[이용자] 내 정보 조회<br>-[이용자] 비밀번호 변경 <br>**컴포넌트**<br>- 페이지네이션|**페이지**<br>-[관리자] 문의 목록/상세 <br>- [관리자] 회원 목록/상세<br>- [관리자] 시설 목록/상세<br>**컴포넌트**<br>- 검색 그룹|
|**Back**|- 로그인/로그아웃<br>- 회원가입 <br>- [이용자] 메인 시설검색/추천시설 <br>- [이용자] 뛰맵 시설 검색<br>- [이용자] 내 정보 조회<br>- [이용자] 비밀번호 변경<br>- 공용누리 OpenAPI|- [이용자] 문의 목록/상세/등록 <br>- [이용자] 문의 수정/삭제<br>- [이용자] 예약 목록/상세 <br>- [이용자] 이용 후기 등록 <br>- 페이지네이션<br>- 검색<br>|- [관리자] 문의 목록/상세 <br>- [관리자] 문의 답변 등록 <br>- [관리자] 회원 목록/상세<br>- [관리자] 시설 목록/상세<br>- 공용누리OpenAPI<br>|


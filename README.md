<img src="https://github.com/cpcpcool/ddida/assets/143718896/ac22e037-bd62-413e-a0be-bf23d7954282" width="450" >
<br><br>

### 배포 주소 : http://3.34.49.62:9999
#### 기본 관리자 아이디 / 비밀번호 : admin / 11
#### 기본 이용자 아이디 / 비밀번호 : user1 / 11

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
#### - 로그인/회원가입 : 기존 아이디와 비밀번호 입력 후 로그인 / 회원가입
#### - 메인 화면 : 시설 검색(시설분류 카테고리 필수 선택 후 검색)/추천 시설
#### - 시설 검색/예약 : 시설 목록/검색 -> 시설 상세 -> 예약 -> 예약 내역으로 이동
#### - 뛰맵 : 시설 지도 뷰 (지도 맵으로 시설 위치 정보 확인)
#### - QnA : 문의 목록/등록 -> 문의 상세 (답변 확인)
#### - 마이페이지 : 예약 내역 목록 -> 예약 상세 페이지(이용 대기 상태 : 예약 취소 가능 / 이용 완료 상태 : 후기 작성 가능)

### [관리자]
#### - 로그인/회원가입 : 기존 아이디와 코드 입력 후 관리자 로그인 / 회원가입(새로운 관리자 계정 생성)
#### - QnA : 문의 목록 -> 문의 상세(답변 등록) -> 답변 등록 시 해당 유저의 문의글 페이지에 표시
#### - 회원 관리 : 회원 목록-> 회원 정보 상세(해당 회원의 정보와 문의글 내역 확인)
#### - 시설물 정보 관리 : 시설물 목록 -> 시설 정보 상세
<br><br>

## **개발환경**<br>
|   |    |
----|----|
**OS**|Windows 11|
**IDE**|Spring Tool Suite 4 4.21.0.RELEASE|
**Front-end**|HTML<br>CSS<br>JavaScript<br>Bootstrap 5.3|
**Back-end**|Java 17<br>Spring Boot 3.2.2<br>Spring Security 6<br>Spring data JPA 3.2.2<br>lombok 1.18.30|
**Database**|MySQL 8.0.32|

<br><br>

## **팀원별 업무 분담**<br>
|    |**박재용(팀장)**|**김민혜(팀원)**|**김부경(팀원)**|**노윤건(팀원)**|
|----|---|---|---|---|
|**Front**|**페이지**<br>- 로그인<br>- [이용자/관리자] 회원가입 <br>- [이용자] 메인 페이지 <br>- [이용자] 뛰맵(시설지도)<br>- KAKAO Maps API 연동<br>**컴포넌트**<br>- [이용자/관리자] header<br>- [이용자/관리자] footer|**페이지**<br>- [이용자] 문의 목록/상세/등록 <br>- [이용자] 예약 목록/상세<br>- [이용자] 내 정보 조회<br>- [이용자] 비밀번호 변경 <br>**컴포넌트**<br>- 페이지네이션|**페이지**<br>- [이용자] 시설 목록/상세 <br>- [이용자] 시설 예약 폼<br>- [이용자] 시설 예약 완료 |**페이지**<br>- [관리자] 문의 목록/상세 <br>- [관리자] 회원 목록/상세<br>- [관리자] 시설 목록/상세<br>- KAKAO Maps API 연동<br>**컴포넌트**<br>- 검색 그룹|
|**Back**|- 로그인/로그아웃<br>- 회원가입 <br>- [이용자] 메인 시설검색/추천시설 <br>- [이용자] 뛰맵 시설 검색<br>- [이용자] 내 정보 조회<br>- [이용자] 비밀번호 변경<br>- 공유누리 OpenAPI|- [이용자] 문의 목록/상세/등록 <br>- [이용자] 문의 수정/삭제<br>- [이용자] 예약 목록/상세 <br>- [이용자] 이용 후기 등록 <br>- 페이지네이션<br>- 검색<br>|- [이용자] 시설 목록/검색 <br>- [이용자] 시설 상세 정보 <br>- [이용자] 시설 예약 <br>- 공유누리OpenAPI <br>|- [관리자] 문의 목록/상세 <br>- [관리자] 문의 답변 등록 <br>- [관리자] 회원 목록/상세<br>- [관리자] 시설 목록/상세<br>- 페이지네이션<br>- 검색<br>- 공유누리OpenAPI<br>|
<br>

## **프로젝트 아키텍처 구성도**<br>
![image](https://github.com/cpcpcool/ddida/assets/143718896/c649076a-8583-4e72-880d-103894e360c5)

## **ERD**<br>
![ERD_뛰다](https://github.com/cpcpcool/ddida/assets/143718896/a36daba9-7b07-45c4-b0e1-705d7054c4bf)


<br>

## **주요 기능**
### [이용자]
### **회원가입**
![join](https://github.com/cpcpcool/ddida/assets/71428936/d92daed6-3108-4e60-b612-58805021ed8a)<br>
### **로그인** 
![login](https://github.com/cpcpcool/ddida/assets/71428936/1423944f-4670-40f8-9c65-5b098c362c7a)<br>
### **메인페이지**
![main](https://github.com/cpcpcool/ddida/assets/71428936/71eee399-3cba-4b0e-a76b-4ce8b82cf4eb)<br>
### **시설 검색/예약**
![sports](https://github.com/cpcpcool/ddida/assets/71428936/ee530771-603b-4ddb-a9f5-c72d03ed7d2e)<br>
### **시설 상세**
![sportdetail](https://github.com/cpcpcool/ddida/assets/71428936/4940a575-85ab-4813-b542-dca2f787c250)<br>
### **시설 예약**
![sportreserve](https://github.com/cpcpcool/ddida/assets/71428936/26fa28ca-5389-4f1d-a7a1-4f65d9dcbec6)<br>
### **시설 예약 완료**
![complete](https://github.com/cpcpcool/ddida/assets/71428936/be8562bf-242f-48c5-95ee-065e34db231b)<br>
### **뛰맵**
![ddimap](https://github.com/cpcpcool/ddida/assets/71428936/bf52d3f4-a03d-44be-a95c-288ed8154208)<br>
### **문의 목록**
![qna](https://github.com/cpcpcool/ddida/assets/71428936/a30b50e2-7d54-496e-b419-8044ad524cc9)<br>
### **문의 상세**
![qnadetail](https://github.com/cpcpcool/ddida/assets/71428936/bd7887aa-d1d7-42d3-bc43-7a1605b35012)<br>
### **문의 등록**
![qnaadd](https://github.com/cpcpcool/ddida/assets/71428936/97fffebc-f0e2-4ab8-908b-97270d8f16ca)<br>
### **예약 내역**
![reservelist](https://github.com/cpcpcool/ddida/assets/71428936/ba17f6e3-5393-47bd-b49f-e6e8f85670f6)<br>
### **예약 상세/이용 후기 등록**
![reservedetail](https://github.com/cpcpcool/ddida/assets/71428936/0bac074c-882d-41d8-9243-65f3214b1920)<br>
### **내 정보 조회**
![userinfo](https://github.com/cpcpcool/ddida/assets/71428936/72179f28-766a-45f1-8ce2-81311559bf3b)<br>
### **비밀번호 변경**
![passwordedit](https://github.com/cpcpcool/ddida/assets/71428936/7b8dbf52-ccc0-42d0-a4f2-2c497b181766)<br>

### [관리자]
### **회원가입**
![adminjoin](https://github.com/cpcpcool/ddida/assets/71428936/ab98b9de-5e90-4522-a516-1a0a2ad26232)<br>
### **문의 목록**
![adminqna](https://github.com/cpcpcool/ddida/assets/71428936/23dc58ab-e281-47c1-88cf-630f4f4ec4b8)<br>
### **문의 상세/답변등록**
![adminqnadetail](https://github.com/cpcpcool/ddida/assets/71428936/73ab9a0a-1980-4a8d-ab86-44a62f6628cd)<br>
### **회원 목록**
![adminusers](https://github.com/cpcpcool/ddida/assets/71428936/ba9bca96-38e7-4132-9104-0aba304005f6)<br>
### **회원 상세**
![adminuserdetail](https://github.com/cpcpcool/ddida/assets/71428936/cc013aff-77d6-4eb2-bbe1-bedcee924e04)<br>
### **시설 목록**
![adminspace](https://github.com/cpcpcool/ddida/assets/71428936/bae89300-3ae6-4bed-807b-6f422454a040)<br>
### **시설 상세**
![adminspacedetail](https://github.com/cpcpcool/ddida/assets/71428936/f5bf44d6-6367-470b-bfe8-06523f11bf18)<br>



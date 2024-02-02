## 회원
create table if not exists member(
	user_no bigint auto_increment not null comment '회원 고유번호',
    username varchar(50) unique not null COMMENT '사용자 아이디',
    password varchar(100) not null COMMENT '비밀번호',
    name varchar(30) COMMENT '사용자 이름',
	role varchar(10) COMMENT '권한',
    phone varchar(13) COMMENT '회원 휴대전화번호',
    email varchar(30) COMMENT '회원 이메일',
    sign_date varchar(100) COMMENT '회원 가입일자',
    primary key(user_no)
); 

## 예약
CREATE TABLE reserve (
	`reserveId`	bigint	NOT NULL	COMMENT '시설 예약번호 auto_increment',
	`userNo`	bigint	NOT NULL	COMMENT '회원 고유번호 auto_increment',
	`spaceName`	varchar(60)	NOT NULL	COMMENT '체육시설의 이름',
	`useStartDate`	date	NOT NULL	COMMENT '예정 이용시작 일자',
	`useStartTime`	time	NOT NULL	COMMENT '예정 이용시작 시간',
	`reserveDate`	date	NOT NULL	COMMENT '예정 이용종료 일자',
	`reserveTime`	time	NOT NULL	COMMENT '예정 이용종료 시간',
	`reserveFee`	int	NOT NULL	COMMENT '예약내역에 해당하는 금액',
	`reserveType`	varchar(10)	NOT NULL	COMMENT '예약 시 결제할 방식'
);

## 예약시간
CREATE TABLE `reservetime` (
	`Key`	VARCHAR(255)	NOT NULL,
	`Field`	VARCHAR(255)	NULL
);

## 질문
create table qna(
	qna_no bigint auto_increment not null comment '문의 고유번호',
    user_no bigint not null comment '회원 고유번호',
    title varchar(90) not null comment '이용자가 작성한 문의글 제목',
    description text not null comment '이용자가 작성한 문의글 내용',
    qna_date datetime not null comment '이용자가 문의글을 등록한 시간',
    qna_view int not null comment '문의글의 조회수',
    qna_secret tinyint(1) not null comment '비밀글 여부',
    foreign key(user_no) references member(user_no),
	primary key(qna_no)
);

## 권한
CREATE TABLE `authority` (
	`userNo`	bigint	NOT NULL	COMMENT '회원 고유번호 auto_increment',
	`userAuth`	char(1)	NOT NULL	COMMENT '일정기간동안 변경될 권한',
	`banPeriod`	int	NOT NULL	COMMENT '권한 변경이 유지되는 기간',
	`reasonChanged`	varchar(255)	NOT NULL	COMMENT '권한이 변경된 사유',
	`banStartDate`	datetime	NOT NULL	COMMENT '권한 변경이 시작되는 시간'
);

## 회원활동내역
CREATE TABLE `actHistory` (
	`userNo`	bigint	NOT NULL	COMMENT '회원 고유번호 auto_increment',
	`countQna`	int	NULL	COMMENT '회원이 작성한 문의글 수',
	`countReview`	int	NULL	COMMENT '회원이 작성한 후기 수',
	`countReserve`	int	NULL	COMMENT '회원이 예약한 총 횟수'
);

## 후기
CREATE TABLE `Review` (
	`reserveId`	bigint	NOT NULL	COMMENT '시설 예약번호 auto_increment',
	`userNo`	bigint	NOT NULL	COMMENT '회원 고유번호 auto_increment',
	`userId`	char(30)	NOT NULL	COMMENT '회원 아이디',
	`reviewContent`	varchar(255)	NOT NULL	COMMENT '회원이 작성한 후기 내용',
	`reviewDate`	datetime	NOT NULL	COMMENT '회원이 후기를 작성한 시간'
);

## 문의답변
CREATE TABLE `qnaAns` (
	`qnaNo`	bigint	NOT NULL	COMMENT '문의 고유번호 auto_increment',
	`ansContent`	text	NOT NULL	COMMENT '관리자가 작성한 답변 내용',
	`ansDate`	datetime	NOT NULL	COMMENT '관리자가 답변을 작성한 시간'
);

## 파일
CREATE TABLE `file` (
	`fileNo`	bigint	NOT NULL	COMMENT '첨부파일의 고유번호',
	`qnaNo`	bigint	NOT NULL	COMMENT '문의 고유번호 auto_increment',
	`fileName`	varchar(255)	NOT NULL	COMMENT '첨부파일의 이름',
	`originFileName`	varchar(255)	NOT NULL	COMMENT '첨부파일의 원본파일 이름',
	`fileStoragePath`	varchar(30)	NOT NULL	COMMENT '파일이 저장되는 경로',
	`fileSize`	varchar(255)	NOT NULL	COMMENT '첨부파일 용량'
);

show tables;
show databases;


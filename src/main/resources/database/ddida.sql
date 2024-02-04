## ddida
## DB 생성
drop database if exists ddida;
create database if not exists ddida
charset='utf8mb4';

use ddida;

## user
drop table if exists member;
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
create table if not exists reserve (
    reserve_id bigint auto_increment not null comment '시설 예약번호',
	user_no bigint not null comment '회원 고유번호',
    rsrc_no varchar(60) comment '체육시설의 번호',
    rsrc_nm varchar(60) comment '체육시설의 이름',
    use_date date comment '예정 이용시작 일자',
    reserve_date date comment '예약 신청 일자',
    reserve_time time comment '예약 `신청 시간',
    reserve_fee varchar(30) comment '시설 이용 금액',
    reserve_type varchar(30) comment '예약 결제 방식',
	phone varchar(13) COMMENT '예약자 휴대전화번호',
    email varchar(30) COMMENT '예약자 이메일',
    name varchar(30) COMMENT '예약자 이름',
    checkout int COMMENT '이용 완료 여부',
    review varchar(255) comment '이용 후기',
    checkout int comment '이용 상태',
    primary key(reserve_id),
    foreign key(user_no) references member(user_no)
);

## 예약 시간
create table if not exists reserve_time (
    reserve_time_no bigint auto_increment not null comment '예약시간번호',
    reserve_id bigint not null comment '시설 예약번호',
    use_time varchar(30) not null comment '예정 이용시작 시간',
    primary key(reserve_time_no),	
    foreign key(reserve_id) references reserve(reserve_id)
);

## qna
create table if not exists qna(
	qna_no bigint auto_increment not null comment '문의 고유번호',
    username varchar(50) not null COMMENT '사용자 아이디',
    name varchar(30) COMMENT '사용자 이름',
    title varchar(90) not null comment '이용자가 작성한 문의글 제목',
    description text not null comment '이용자가 작성한 문의글 내용',
    qna_date datetime not null comment '이용자가 문의글을 등록한 시간',
    qna_view int not null comment '문의글의 조회수',
    qna_secret tinyint(1) not null comment '비밀글 여부',
    foreign key(username) references member(username),
	primary key(qna_no)
);
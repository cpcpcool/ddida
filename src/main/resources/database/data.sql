insert into member (username, password, name, role, phone, email, sign_date)
values 
('user1', '{bcrypt}$2a$10$xkQgdtS35jgYPIyRxKvt6OHWIi2owMHVpIPFVaOQM3DCX0ZWjNRcK', '박재용', 'USER', '010-1234-5678', 'cpcpcoole@gmail.com', '2022-02-10'),
('user2', '{bcrypt}$2a$10$xkQgdtS35jgYPIyRxKvt6OHWIi2owMHVpIPFVaOQM3DCX0ZWjNRcK', '김민혜', 'USER', '010-5678-1234', 'kmmnnnh@gmail.com', '2022-02-12'),
('user3', '{bcrypt}$2a$10$xkQgdtS35jgYPIyRxKvt6OHWIi2owMHVpIPFVaOQM3DCX0ZWjNRcK', '노윤건', 'USER', '010-1234-0987', 'nyg-6789@example.com', '2022-02-12'),
('admin', '{bcrypt}$2a$10$xkQgdtS35jgYPIyRxKvt6OHWIi2owMHVpIPFVaOQM3DCX0ZWjNRcK', '-', 'ADMIN', '-', '-', '2022-02-15');

insert into reserve (user_no, rsrc_no, rsrc_nm, use_date, reserve_date, reserve_time, reserve_fee, reserve_type, phone, email, name, review)
values
(1, 'R001', 'EH22N4422987', '2022-02-20', '2022-02-18', '18:00:00', '20000', '카드 결제', '010-1234-5678', 'user1@example.com', 'User 1', '좋았어요.'),
(2, 'R002', '체육시설2', '2022-02-25', '2022-02-22', '14:30:00', '15000', '현장결제', '010-5678-1234', 'user2@example.com', 'User 2', '만족합니다.'),
(1, 'R003', '체육시설3', '2022-03-01', '2022-02-28', '18:00:00', '25000', '카드 결제', '010-1234-5678', 'user1@example.com', 'User 1', '추천합니다.');

insert into reserve_time (reserve_id, use_time)
values
(1, '18:00:00'),
(2, '14:30:00'),
(3, '18:00:00');

insert into qna (username, title, description, qna_date, qna_view, qna_secret)
values
('user1', '체육시설 이용 문의', '체육시설 이용 관련 문의 내용입니다.', '2022-03-05 10:30:00', 15, 0),
('user2', '결제 문의', '결제 관련 문의 내용입니다.', '2022-03-08 14:45:00', 20, 1),
('user1', '이용 후기 공유', '체육시설 이용 후기입니다.', '2022-03-12 19:00:00', 25, 0);

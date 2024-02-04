insert into member (username, password, name, role, phone, email, sign_date)
values 
('user1', '{bcrypt}$2a$10$xkQgdtS35jgYPIyRxKvt6OHWIi2owMHVpIPFVaOQM3DCX0ZWjNRcK', '박재용', 'USER', '010-1234-5678', 'cpcpcoole@gmail.com', '2022-02-10'),
('user2', '{bcrypt}$2a$10$xkQgdtS35jgYPIyRxKvt6OHWIi2owMHVpIPFVaOQM3DCX0ZWjNRcK', '김민혜', 'USER', '010-5678-1234', 'kmmnnnh@gmail.com', '2023-02-12'),
('user3', '{bcrypt}$2a$10$xkQgdtS35jgYPIyRxKvt6OHWIi2owMHVpIPFVaOQM3DCX0ZWjNRcK', '노윤건', 'USER', '010-1234-0987', 'nyg-6789@gmail.com', '2020-02-12'),
('admin', '{bcrypt}$2a$10$xkQgdtS35jgYPIyRxKvt6OHWIi2owMHVpIPFVaOQM3DCX0ZWjNRcK', '-', 'ADMIN', '-', '-', '2022-02-15');

insert into reserve (user_no, rsrc_no, rsrc_nm, use_date, reserve_date, reserve_time, reserve_fee, reserve_type, phone, email, name, review)
values
(1, 'EH22N4422987', '테니스장(양평누리체육공원)', '2024-02-02', '2024-02-01', '12:40:16', '개별 문의', '업체 문의', '010-1234-5678', 'cpcpcoole@gmail.com', '박재용', '근처에 편의점이 가까워서 좋았어요.'),
(2, 'CF02P0645947', '대체육관', '2024-02-03', '2024-02-02', '14:30:02', '개별 문의', '업체 문의', '010-1234-5678', 'kmmnnnh@gmail.com', '김민혜', '실내가 쾌적해요!');

insert into reserve (user_no, rsrc_no, rsrc_nm, use_date, reserve_date, reserve_time, reserve_fee, reserve_type, phone, email, name)
values
(1, 'EH22N4422987', '테니스장(양평누리체육공원)', '2024-02-03', '2024-02-01', '12:40:16', '개별 문의', '업체 문의', '010-1234-5678', 'cpcpcoole@gmail.com', '박재용'),
(1, 'EH22N4422987', '테니스장(양평누리체육공원)', '2024-02-07', '2024-02-02', '12:40:16', '개별 문의', '업체 문의', '010-1234-5678', 'cpcpcoole@gmail.com', '박재용'),
(3, 'CF02P0645947', '대체육관', '2024-02-11', '2024-02-02', '12:40:16', '개별 문의', '업체 문의', '010-1234-5678', 'nyg-6789@gmail.com', '노윤건');

insert into reserve_time (reserve_id, use_time)
values
(1, '17:00 ~ 18:00'),
(2, '12:00 ~ 13:00'),
(3, '15:00 ~ 16:00'),
(3, '16:00 ~ 17:00'),
(4, '13:00 ~ 14:00'),
(4, '14:00 ~ 15:00'),
(5, '13:00 ~ 14:00'),
(5, '14:00 ~ 15:00');

insert into qna (username, title, description, qna_date, qna_view, qna_secret)
values
('user1', '체육시설 이용 문의', '체육시설 이용 관련 문의 내용입니다.', '2022-03-05 10:30:00', 15, 0),
('user2', '결제 문의', '결제 관련 문의 내용입니다.', '2022-03-08 14:45:00', 20, 1),
('user1', '이용 후기 공유', '체육시설 이용 후기입니다.', '2022-03-12 19:00:00', 25, 0);

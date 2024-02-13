insert into member (username, password, name, role, phone, email, sign_date)
values 
('user1', '{bcrypt}$2a$10$xkQgdtS35jgYPIyRxKvt6OHWIi2owMHVpIPFVaOQM3DCX0ZWjNRcK', '박재용', 'USER', '010-1234-5678', 'cpcpcoole@gmail.com', '2022-02-10'),
('user2', '{bcrypt}$2a$10$xkQgdtS35jgYPIyRxKvt6OHWIi2owMHVpIPFVaOQM3DCX0ZWjNRcK', '김민혜', 'USER', '010-5678-1234', 'kmmnnnh@gmail.com', '2023-02-12'),
('user3', '{bcrypt}$2a$10$xkQgdtS35jgYPIyRxKvt6OHWIi2owMHVpIPFVaOQM3DCX0ZWjNRcK', '노윤건', 'USER', '010-1234-0987', 'nyg-6789@gmail.com', '2020-02-12'),
('admin', '{bcrypt}$2a$10$xkQgdtS35jgYPIyRxKvt6OHWIi2owMHVpIPFVaOQM3DCX0ZWjNRcK', '-', 'ADMIN', '-', '-', '2022-02-15');

insert into reserve (user_no, rsrc_no, rsrc_nm, use_date, reserve_date, reserve_time, reserve_fee, reserve_type, phone, email, name, review, checkout)
values
(1, 'EH22N4422987', '테니스장(양평누리체육공원)', '2024-02-02', '2024-02-01', '12:40:16', '개별 문의', '업체 문의', '010-1234-5678', 'cpcpcoole@gmail.com', '박재용', '근처에 편의점이 가까워서 좋았어요.', 0),
(2, 'CF02P0645947', '대체육관', '2024-02-03', '2024-02-02', '14:30:02', '개별 문의', '업체 문의', '010-1234-5678', 'kmmnnnh@gmail.com', '김민혜', '실내가 쾌적해요!', 0),
(2, 'CF02P0645947', '대체육관', '2024-02-05', '2024-02-02', '14:30:02', '개별 문의', '업체 문의', '010-1234-5678', 'kmmnnnh@gmail.com', '김민혜', '실내가 쾌적해요!', 0),
(2, 'EH22N4422987', '테니스장(양평누리체육공원)', '2024-02-03', '2024-02-01', '12:40:16', '개별 문의', '업체 문의', '010-1234-5678', 'kmmnnnh@gmail.com', '김민혜',  '근처에 편의점이 가까워서 좋았어요.', 0);

insert into reserve (user_no, rsrc_no, rsrc_nm, use_date, reserve_date, reserve_time, reserve_fee, reserve_type, phone, email, name, checkout)
values
(1, 'EH22N4422987', '테니스장(양평누리체육공원)', '2024-02-03', '2024-02-01', '12:40:16', '개별 문의', '업체 문의', '010-1234-5678', 'cpcpcoole@gmail.com', '박재용', 0),
(1, 'EH22N4422987', '테니스장(양평누리체육공원)', '2024-02-07', '2024-02-02', '12:40:16', '개별 문의', '업체 문의', '010-1234-5678', 'cpcpcoole@gmail.com', '박재용', 0),
(3, 'CF02P0645947', '대체육관', '2024-02-11', '2024-02-02', '12:40:16', '개별 문의', '업체 문의', '010-1234-5678', 'nyg-6789@gmail.com', '노윤건', 0),
(3, 'EH22N4422987', '테니스장(양평누리체육공원)', '2024-02-10', '2024-02-02', '12:40:16', '개별 문의', '업체 문의', '010-1234-5678', 'nyg-6789@gmail.com', '노윤건', 0);

insert into reserve_time (reserve_id, use_time)
values
(1, '17:00 ~ 18:00'),
(2, '12:00 ~ 13:00'),
(3, '15:00 ~ 16:00'),
(3, '16:00 ~ 17:00'),
(4, '13:00 ~ 14:00'),
(4, '14:00 ~ 15:00'),
(5, '13:00 ~ 14:00'),
(5, '14:00 ~ 15:00'),
(6, '14:00 ~ 15:00'),
(6, '14:00 ~ 15:00'),
(7, '10:00 ~ 11:00'),
(7, '11:00 ~ 12:00'),
(7, '12:00 ~ 13:00'),
(7, '13:00 ~ 14:00'),
(7, '14:00 ~ 15:00'),
(7, '15:00 ~ 16:00'),
(7, '16:00 ~ 17:00'),
(7, '19:00 ~ 20:00'),
(7, '20:00 ~ 21:00');
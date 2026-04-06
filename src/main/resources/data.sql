-- workers
insert into workers (full_name, account, password) values
(N'americano', 'abc123', 'lmao'),
(N'robusta', 'kikiki', 'lol');

-- momo_users
insert into momo_users (full_name, phone) values
('Nguyễn Huy Bình', '0987654321');

-- users
-- admin@gmail.com     : bcrypt of "lmao"  (fixed from plain text!)
-- posSystem@gmail.com : bcrypt of "lmao"
-- a@gmail.com         : bcrypt of "password"
insert into users (full_name, email, password, phone, default_address, avatar, role) values
('admin', 'admin@gmail.com', '$2a$12$pFOPW6MiM8N9ctlwN0SoPehWv.NLB1/A5QMnJegidMUU2Ucj.umzu', '0123456789', 'Hà Nội', 'nah', 'ADMIN'),
('POS system', 'posSystem@gmail.com', '$2a$12$pFOPW6MiM8N9ctlwN0SoPehWv.NLB1/A5QMnJegidMUU2Ucj.umzu', null, 'POS', 'no', 'ADMIN'),
('Nguyễn Văn A', 'a@gmail.com', '$2a$12$8fKVVkzn7BVFBA2cKIH.D.9QZUf9vWK2KdmUcuIAsWPlY07bbNToW', '0988888888', 'Hà Nội', 'avatar_a.png', 'USER');

-- customers
insert into customers (full_name, loyalty_point, date_of_birth, phone, user_id) values
('admin', 200000000, '2024-11-21', '0123457689', 1),
('POS system', 0, null, null, 2),
('Nguyễn Văn A', 150.0, '2026-03-20', '0988888888', 3);

-- carts
insert into carts (user_id) values
(2); --(POS system cart)

-- instructions
insert into instructions (name, instructions) values
(N'Pha Máy', 'Chiết xuất Espresso tiêu chuẩn 30ml'),
(N'Pha Phin', 'Ủ 2 phút với 20ml nước, sau đó rót thêm 40ml'),
(N'Ủ Lạnh', 'Ủ bột cà phê trong nước lạnh 16 tiếng');

-- coffee_beans
insert into coffee_beans (name, price, quantity) values
(N'Arabica Highland', 0, 500),
(N'Robusta Special', 0, 500);

-- heavy_creams
insert into heavy_creams (name, price, quantity) values
(N'Kem Béo Rich', 0, 50);

-- ice_creams
insert into ice_creams (name, price, quantity) values
(N'Cream', 10, 220),
(N'SUPAAAAAAAA', 30, 50),
(N'Creammmmm', 11, 50);

-- milks
insert into milks (name, price, quantity) values
(N'Sữa Đặc Larosee', 0, 100),
(N'Sữa Tươi Vinamilk', 0, 100);

-- sizes
insert into sizes (name, price) values
('S', 0),
('M', 5000),
('L', 10000);

-- drinks
insert into drinks (name, category, base_price, image_url, coffee_bean_id, instruction_id, milk_id, active) values
(N'Matcha Latte', N'Trà', 45000, '/IMG/MistakesWereMade.jpg', null, null, 2, 1),
(N'Trà Đào', N'Trà', 25000, '/IMG/MistakesWereMade.jpg', null, null, null, 1),
(N'Cà Phê Sữa Đá', N'Cà phê', 35000, '/IMG/MistakesWereMade.jpg', 2, 2, 1, 1),
(N'Cà Phê Muối', N'Cà phê', 45000, '/IMG/MistakesWereMade.jpg', 1, 1, 1, 1),
(N'Bạc Xỉu', N'Cà phê', 55000, '/IMG/MistakesWereMade.jpg', 2, 1, 1, 1);

-- toppings
insert into toppings (name, price) values
(N'Trân châu đen', 5000),
(N'Thạch cà phê', 5000),
(N'Kem cheese', 8000),
(N'Pudding trứng', 8000),
(N'Kem béo', 6000),
(N'Whipping cream', 8000),
(N'Lmao special', 10000);

-- promo_codes
insert into promo_codes (code, category, name, value, quantity, start_date, end_date, status) values
(N'VOUCHER10', N'PHẦN TRĂM', N'Giảm 10%', 10.00, 1, '2025-12-25', '2026-01-25', 1),
(N'VIP30', N'PHẦN TRĂM', N'VIP giảm 30%', 30.00, 19, '2025-12-22', '2026-01-22', 1),
(N'LMAO200', N'PHẦN TRĂM', N'Giảm 200k', 200000, 216, '2026-01-11', '2026-02-11', 1);

-- orders
insert into orders (order_number, customer_id, original_price, final_price, status, order_date) values
(719523346, 1, 55000, 55000, N'Chờ xác nhận', '2026-03-20 13:36:00'),
(719523347, 1, 50000, 45000, N'Đã giao', '2026-03-20 13:38:00'),
(750602738, 1, 218000, 218000, N'Đang xử lý', current_timestamp),
(781602731, 1, 220000, 220000, N'Đã huỷ', current_timestamp),
(1001, 1, 450000, 450000, N'Đã giao', '2026-03-28 10:00:00'),
(1002, 1, 320000, 320000, N'Đã giao', '2026-03-29 14:30:00'),
(1003, 1, 580000, 580000, N'Đã giao', '2026-03-30 09:15:00'),
(1004, 1, 210000, 210000, N'Đã giao', '2026-03-31 11:45:00'),
(1005, 1, 750000, 750000, N'Đã giao', '2026-04-01 16:20:00'),
(1006, 1, 420000, 420000, N'Đã giao', '2026-04-02 13:10:00'),
(1007, 1, 600000, 600000, N'Đã giao', '2026-04-03 10:00:00'),
(1007, 1, 99999999, 99999999, N'Đã giao', '2026-04-03 11:00:00');

-- invoices
insert into invoices (invoice_number, order_id, customer_id, payment_method, receipt_type, original_price, discount_amount, final_price) values
(1, 2, 1, N'Tiền mặt', N'Tại Quầy', 50000, 5000, 45000),
(2, 1, 1, N'Chuyển khoản', N'Online', 70000, 5000, 65000);
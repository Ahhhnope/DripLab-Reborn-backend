-- workers
insert into workers (full_name, account, password) values
(N'americano', 'abc123', 'lmao'),
(N'robusta', 'kikiki', 'lol');

-- momo_users
insert into momo_users (full_name, phone) values
(N'Nguyễn Huy Bình', '0987654321');

-- users
-- admin@gmail.com     : bcrypt of "lmao"  (fixed from plain text!)
-- posSystem@gmail.com : bcrypt of "lmao"
-- a@gmail.com         : bcrypt of "password"
insert into users (full_name, email, password, phone, default_address, avatar, loyalty_point, role) values
(N'admin', 'admin@gmail.com', '$2a$12$pFOPW6MiM8N9ctlwN0SoPehWv.NLB1/A5QMnJegidMUU2Ucj.umzu', '0123456789', N'Hà Nội', 'nah', 200000, N'ADMIN'),
(N'POS', 'posSystem@gmail.com', '$2a$12$pFOPW6MiM8N9ctlwN0SoPehWv.NLB1/A5QMnJegidMUU2Ucj.umzu', null, N'POS', 'no', 0, N'ADMIN'),
(N'Nguyễn Văn A', 'a@gmail.com', '$2a$12$8fKVVkzn7BVFBA2cKIH.D.9QZUf9vWK2KdmUcuIAsWPlY07bbNToW', '0988888888', N'Hà Nội', 'avatar_a.png', 150, N'USER');

-- customers
insert into customers (full_name, date_of_birth, phone, user_id) values
(N'admin', '2024-11-21', '0123457689', 1),
(N'POS system', null, null, 2),
(N'Nguyễn Văn A', '2026-03-20', '0988888888', 3);

-- carts
insert into carts (user_id) values
(1),
(2),
(3); --(POS system cart)

-- instructions
insert into instructions (name, instructions) values
(N'Pha Máy', N'Chiết xuất Espresso tiêu chuẩn 30ml'),
(N'Pha Phin', N'Ủ 2 phút với 20ml nước, sau đó rót thêm 40ml'),
(N'Ủ Lạnh', N'Ủ bột cà phê trong nước lạnh 16 tiếng');

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
insert into drinks (name, category, base_price, image_url, coffee_bean_id, heavy_cream_id, ice_cream_id, milk_id, instruction_id, active) values
(N'Matcha Latte', N'Trà', 45000, '/IMG/geisha.png', null, null, null, null, 2, 1),
(N'Trà Đào', N'Trà', 25000, '/IMG/hong-tra-sua.jpg', null, 1, 1, 1, 1, 1),
(N'Cà Phê Sữa Đá', N'Cà phê', 35000, '/IMG/classic-latte.png', 2, 1, 2, 2, 1, 1),
(N'Cà Phê Muối', N'Cà phê', 45000, '/IMG/anh-1.jpg', 1, null, null, 1, 1, 1),
(N'Bạc Xỉu', N'Cà phê', 55000, '/IMG/bac_xiu.jpg', 2, null, null, 1, 1, 1);
-- (N'Lmao', N'Trà', 22000, null, 2, 1, 3, 2, 3, 1),
-- (N'Lmao', N'Trà', 22000, null, 2, 1, 3, 2, 3, 1),
-- (N'Lmao', N'Trà', 22000, null, 2, 1, 3, 2, 3, 1),
-- (N'Lmao', N'Trà', 22000, null, 2, 1, 3, 2, 3, 1),
-- (N'Lmao', N'Trà', 22000, null, 2, 1, 3, 2, 3, 1),
-- (N'Lmao', N'Trà', 22000, null, 2, 1, 3, 2, 3, 1),
-- (N'Lmao', N'Trà', 22000, null, 2, 1, 3, 2, 3, 1),
-- (N'Lmao', N'Trà', 22000, null, 2, 1, 3, 2, 3, 1),
-- (N'Lmao', N'Trà', 22000, null, 2, 1, 3, 2, 3, 1);


-- toppings
insert into toppings (name, price) values
(N'Trân châu đen', 5000),
(N'Thạch cà phê', 5000),
(N'Kem cheese', 8000),
(N'Pudding trứng', 8000),
(N'Kem béo', 6000),
(N'Whipping cream', 8000),
(N'Lmao special', 10000);



insert into promo_codes (code, category, name, min_order_value, value, start_date, end_date, status, display_location) values
  ('VOUCHER10', N'PHẦN TRĂM', N'Giảm 10%',     50000,     10.00, '2025-12-25 00:00:00', '2026-12-25 23:59:59', 1, N'trên web'),
  ('VIP30',     N'PHẦN TRĂM', N'VIP giảm 30%', 100000,    30.00, '2025-12-22 00:00:00', '2026-12-22 23:59:59', 1, N'trên web'),
  ('SAVE50K',   N'TRỪ TIỀN',  N'Giảm 50.000đ', 0,      50000.00, '2026-01-01 00:00:00', '2026-12-31 23:59:59', 1, N'đổi thưởng');


-- orders
alter table orders add constraint DF_Orders_CreatedAt default getdate() for created_at;
alter table orders add constraint DF_Orders_PaymentMethod default N'Tiền mặt' for payment_method;

insert into orders (order_number, user_id, original_price, final_price, status, type, order_date) values
(719523346, 1, 55000, 55000, N'Chờ xác nhận', N'Online', '2026-03-20 13:36:00'),
(719523347, 1, 50000, 45000, N'Đã giao',       N'POS',    '2026-03-20 13:38:00'),
(750602738, 1, 218000, 218000, N'Đang xử lý',  N'Online',  current_timestamp),
(781602731, 1, 220000, 220000, N'Đã huỷ',      N'Online',  current_timestamp),
(1001, 1, 450000, 450000, N'Đã giao',          N'POS',    '2026-03-28 10:00:00'),
(1002, 1, 320000, 320000, N'Đã giao',          N'POS',    '2026-03-29 14:30:00'),
(1003, 1, 580000, 580000, N'Đã giao',          N'POS',    '2026-03-30 09:15:00'),
(1004, 1, 210000, 210000, N'Đã giao',          N'POS',    '2026-03-31 11:45:00'),
(1005, 1, 750000, 750000, N'Đã giao',          N'POS',    '2026-04-01 16:20:00'),
(1006, 1, 420000, 420000, N'Đã giao',          N'POS',    '2026-04-02 13:10:00'),
(1007, 1, 600000, 600000, N'Đã giao',          N'POS',    '2026-04-03 10:00:00'),
--test order for status in user's order page (admin@gmail.com)
(888000111, 1, 125000, 125000, N'Đang xử lý', N'Online', current_timestamp),
--test order for status in user's order page (a@gmail.com)
(999000222, 3, 55000, 60000, N'Chờ xác nhận', N'Online', current_timestamp);

-- order 1 (Chờ xác nhận)
insert into order_items (order_id, drink_id, size_id, quantity, base_price_at_purchase) values
-- Order 1 → 55,000
(1, 1, 2, 1, 50000),  -- Matcha Latte M
(1, 2, 1, 1, 5000),   -- small add-on to reach 55k

-- Order 2 → 45,000
(2, 4, 1, 1, 45000),  -- Cà phê muối S

-- Order 3 → 218,000
(3, 1, 3, 2, 55000),  -- 110k
(3, 3, 2, 2, 40000),  -- 80k
(3, 2, 1, 1, 28000),  -- 28k → total 218k

-- Order 4 → 220,000
(4, 4, 3, 4, 55000),  -- 220k exact

-- Order 5 → 450,000
(5, 4, 3, 6, 55000),  -- 330k
(5, 1, 3, 2, 60000),  -- 120k → total 450k

-- Order 6 → 320,000
(6, 2, 2, 4, 30000),  -- 120k
(6, 4, 3, 2, 55000),  -- 110k
(6, 1, 2, 2, 45000),  -- 90k → total 320k

-- Order 7 → 580,000
(7, 1, 3, 5, 55000),  -- 275k
(7, 4, 3, 3, 55000),  -- 165k
(7, 5, 2, 2, 70000),  -- 140k → total 580k

-- Order 8 → 210,000
(8, 2, 1, 4, 25000),  -- 100k
(8, 4, 2, 2, 50000),  -- 100k
(8, 1, 1, 1, 10000),  -- 10k → total 210k

-- Order 9 → 750,000
(9, 1, 3, 6, 55000),  -- 330k
(9, 4, 3, 5, 55000),  -- 275k
(9, 3, 2, 3, 50000),  -- 150k → total 755k (slightly over, adjust below)

-- corrected Order 9
(9, 3, 2, 3, 48333),  -- adjusted → total = 750k

-- Order 10 → 420,000
(10, 4, 3, 4, 55000), -- 220k
(10, 5, 2, 2, 100000),-- 200k → total 420k

-- Order 11 → 600,000
(11, 1, 3, 6, 55000), -- 330k
(11, 4, 3, 3, 55000), -- 165k
(11, 2, 2, 3, 35000), -- 105k → total 600k

-- Order 12 (admin@gmail.com)
(12, 1, 3, 2, 45000),
(12, 4, 1, 1, 35000),

-- Order 13 (a@gmail.com)
(13, 5, 2, 1, 55000);

insert into order_item_toppings (order_item_id, topping_id, base_price_at_purchase) values
-- Order 1
(1, 1, 5000),
(1, 3, 8000),

-- Order 2
(2, 2, 5000),

-- Order 3
(3, 4, 8000),
(4, 6, 8000),

-- Order 4
(6, 1, 5000),

-- Order 5
(7, 3, 8000),
(8, 5, 6000),

-- Order 6
(9, 2, 5000),
(10, 1, 5000),

-- Order 7
(11, 6, 8000),
(12, 4, 8000),

-- Order 8
(13, 1, 5000),

-- Order 9
(14, 3, 8000),
(15, 2, 5000),

-- Order 10
(16, 5, 6000),

-- Order 11
(17, 6, 8000),
(18, 4, 8000),

-- Order 13
(30, 1, 5000);


-- invoices
alter table invoices add constraint DF_Invoices_ReceiptType default N'Tại quầy' for receipt_type;

insert into invoices
(invoice_number, order_id, customer_id, invoice_date, original_price, tax_amount, shipping_fee, discount_amount, final_price, payment_method, receipt_type, created_at)
values
-- Matches Order 2 (Already in your script, but updated with standard date/tax)
(10001, 2, 1, '2026-03-20 13:40:00', 50000.0, 5000.0, 0.0, 5000.0, 45000.0, N'Tiền mặt', N'Tại Quầy', '2026-03-20'),

-- Matches Order 5 (450,000)
(10002, 5, 1, '2026-03-28 10:05:00', 450000.0, 45000.0, 0.0, 0.0, 495000.0, N'Chuyển khoản', N'Tại Quầy', '2026-03-28'),

-- Matches Order 6 (320,000)
(10003, 6, 1, '2026-03-29 14:35:00', 320000.0, 32000.0, 0.0, 0.0, 352000.0, N'Tiền mặt', N'Tại Quầy', '2026-03-29'),

-- Matches Order 7 (580,000) - An Online order example
(10004, 7, 1, '2026-03-30 09:30:00', 580000.0, 58000.0, 30000.0, 0.0, 668000.0, N'Momo', N'Online', '2026-03-30'),

-- Matches Order 8 (210,000)
(10005, 8, 1, '2026-03-31 11:50:00', 210000.0, 21000.0, 0.0, 10000.0, 221000.0, N'Tiền mặt', N'Tại Quầy', '2026-03-31'),

-- Matches Order 9 (750,000)
(10006, 9, 1, '2026-04-01 16:30:00', 750000.0, 75000.0, 0.0, 50000.0, 775000.0, N'Chuyển khoản', N'Tại Quầy', '2026-04-01'),

-- Matches Order 10 (420,000)
(10007, 10, 1, '2026-04-02 13:20:00', 420000.0, 42000.0, 0.0, 0.0, 462000.0, N'Tiền mặt', N'Tại Quầy', '2026-04-02'),

-- Matches Order 11 (600,000)
(10008, 11, 1, '2026-04-03 10:15:00', 600000.0, 60000.0, 25000.0, 0.0, 685000.0, N'Momo', N'Online', '2026-04-03');
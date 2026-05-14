-- workers
IF NOT EXISTS (SELECT 1 FROM workers)
insert into workers (full_name, account, password) values
(N'americano', 'abc123', 'lmao'),
(N'robusta', 'kikiki', 'lol');

INSERT INTO workers (full_name, account, password)
SELECT v.* FROM (VALUES
                     (N'Nguyễn Văn An',   'nguyenvanan',  'pass1234'),
                     (N'Trần Thị Bảo',    'trантhibao',   'pass1234'),
                     (N'Lê Minh Châu',    'leminhchau',   'pass1234'),
                     (N'Phạm Quốc Dũng',  'phamquocdung', 'pass1234'),
                     (N'Hoàng Thị Em',    'hoangthiem',   'pass1234'),
                     (N'Đỗ Văn Phúc',     'dovanhphuc',   'pass1234')
                ) AS v(full_name, account, password)
WHERE NOT EXISTS (
    SELECT 1 FROM workers w WHERE w.account = v.account
);

-- momo_users
IF NOT EXISTS (SELECT 1 FROM momo_users)
insert into momo_users (full_name, phone) values
(N'Nguyễn Huy Bình', '0987654321');

-- tiers
IF NOT EXISTS (SELECT 1 FROM tiers)
insert into tiers (name, min_point) values
('NOOB', 0),
('PRO', 100),
('HACKER', 500),
('GOD', 1000);

if not exists (select 1 from tables)
insert into tables (table_number, status) values
(1, N'Còn trống'),
(2, N'Còn trống'),
(3, N'Còn trống'),
(4, N'Còn trống'),
(5, N'Còn trống'),
(6, N'Còn trống'),
(7, N'Còn trống'),
(8, N'Còn trống'),
(9, N'Còn trống'),
(10, N'Còn trống'),
(11, N'Còn trống'),
(12, N'Còn trống'),
(13, N'Còn trống'),
(14, N'Còn trống'),
(15, N'Còn trống'),
(16, N'Còn trống');

-- users
-- admin@gmail.com     : bcrypt of "lmao"  (fixed from plain text!)
-- posSystem@gmail.com : bcrypt of "lmao"
-- a@gmail.com         : bcrypt of "password"
IF NOT EXISTS (SELECT 1 FROM users)
insert into users (full_name, email, password, phone, default_address, avatar, loyalty_point, used_point, tier_id, role) values
(N'admin', 'admin@gmail.com', '$2a$12$pFOPW6MiM8N9ctlwN0SoPehWv.NLB1/A5QMnJegidMUU2Ucj.umzu', '0123456789', N'Hà Nội', '/IMG/lel.png', 200000, 0, 3, N'ADMIN'),
(N'POS', 'posSystem@gmail.com', '$2a$12$pFOPW6MiM8N9ctlwN0SoPehWv.NLB1/A5QMnJegidMUU2Ucj.umzu', null, N'POS', '/IMG/lel.png', 9000000, 0, 3, N'EMPLOYEE'),
(N'Mã Quang Duy', 'marcodma0101@gmail.com', '$2a$12$8fKVVkzn7BVFBA2cKIH.D.9QZUf9vWK2KdmUcuIAsWPlY07bbNToW', '0988888888', N'Hà Nội', '/IMG/lel.png', 99, 0, 1, N'USER');


-- customers
IF NOT EXISTS (SELECT 1 FROM customers)
insert into customers (full_name, date_of_birth, phone, user_id) values
(N'admin', '2024-11-21', '0123457689', 1),
(N'POS system', null, null, 2),
(N'Nguyễn Văn A', '2026-03-20', '0988888888', 3);

-- carts
IF NOT EXISTS (SELECT 1 FROM carts)
insert into carts (user_id) values
(1),
(2),
(3); --(POS system cart)

-- instructions
IF NOT EXISTS (SELECT 1 FROM instructions)
insert into instructions (name, instructions) values
(N'Pha Máy', N'Chiết xuất Espresso tiêu chuẩn 30ml'),
(N'Pha Phin', N'Ủ 2 phút với 20ml nước, sau đó rót thêm 40ml'),
(N'Ủ Lạnh', N'Ủ bột cà phê trong nước lạnh 16 tiếng');

-- coffee_beans
IF NOT EXISTS (SELECT 1 FROM coffee_beans)
insert into coffee_beans (name, price, quantity) values
(N'Arabica Highland', 0, 500),
(N'Robusta Special', 0, 500);

-- heavy_creams
IF NOT EXISTS (SELECT 1 FROM heavy_creams)
insert into heavy_creams (name, price, quantity) values
(N'Kem Béo Rich', 0, 50);

-- ice_creams
IF NOT EXISTS (SELECT 1 FROM ice_creams)
insert into ice_creams (name, price, quantity) values
(N'Cream', 10, 220),
(N'SUPAAAAAAAA', 30, 50),
(N'Creammmmm', 11, 50);

-- milks
IF NOT EXISTS (SELECT 1 FROM milks)
insert into milks (name, price, quantity) values
(N'Sữa Đặc Larosee', 0, 100),
(N'Sữa Tươi Vinamilk', 0, 100);

-- sizes
IF NOT EXISTS (SELECT 1 FROM sizes)
insert into sizes (name, price) values
('S', 0),
('M', 5000),
('L', 10000);

-- drinks
IF NOT EXISTS (SELECT 1 FROM drinks)
insert into drinks (name, category, base_price, image_url, coffee_bean_id, heavy_cream_id, ice_cream_id, milk_id, instruction_id, active) values
-- Cà phê (A1)
(N'Bạc Xỉu',        N'Cà phê', 55000, '/IMG/A1_BXKX.jpg',        2, null, null, 1, 1, 1),
(N'Cappuccino',      N'Cà phê', 45000, '/IMG/A1_Capu.jpg',         2, 1,    null, 1, 1, 1),
(N'Cà Phê Sữa Đá',  N'Cà phê', 35000, '/IMG/A1_ClassicLatte.jpg', 2, 1,    2,    2, 1, 1),
(N'Cà Phê Phin',    N'Cà phê', 30000, '/IMG/A1_Fein.jpg',         1, null, null, null, 1, 1),
(N'Latte',           N'Cà phê', 45000, '/IMG/A1_late.jpg',          2, null, null, 1, 1, 1),
(N'Cà Phê Phin Nâu',N'Cà phê', 35000, '/IMG/A1_PhinNau.png',      1, null, null, 1, 1, 1),
-- Trà (A2)
(N'Trà Geisha',      N'Trà',    45000, '/IMG/A2_Geisha.jpg',       null, null, null, null, 2, 1),
(N'Matcha Latte',    N'Trà',    45000, '/IMG/A2_Matcha.jpg',       null, null, null, null, 2, 1),
(N'Matcha Nóng',     N'Trà',    40000, '/IMG/A2_MatchaNong.jpg',   null, null, null, null, 2, 1),
(N'Mocha',           N'Trà',    45000, '/IMG/A2_Mocha.png',        null, null, null, null, 2, 1),
(N'Trà Đào',         N'Trà',    25000, '/IMG/A2_TraDao.jpg',       null, 1,    1,    1,    1, 1),
(N'Trà Gừng',        N'Trà',    30000, '/IMG/A2_TraGung.jpg',      null, null, null, null, 2, 1),
(N'Trà Sen Vàng',    N'Trà',    35000, '/IMG/A2_TraSenVang.jpg',   null, null, null, null, 2, 1),
(N'Trà Vải',         N'Trà',    30000, '/IMG/A2_TraVai.jpg',       null, null, null, null, 2, 1),
(N'Custom coffee', N'Cà phê',    30000, '/IMG/lel.png',       null, null, null, null, null, 0);
-- (N'Lmao', N'Trà', 22000, null, 2, 1, 3, 2, 3, 1),
   -- hoặc dùng db online
-- (N'Lmao', N'Trà', 22000, null, 2, 1, 3, 2, 3, 1),
-- (N'Lmao', N'Trà', 22000, null, 2, 1, 3, 2, 3, 1),
-- (N'Lmao', N'Trà', 22000, null, 2, 1, 3, 2, 3, 1),
-- (N'Lmao', N'Trà', 22000, null, 2, 1, 3, 2, 3, 1),
-- (N'Lmao', N'Trà', 22000, null, 2, 1, 3, 2, 3, 1),
-- (N'Lmao', N'Trà', 22000, null, 2, 1, 3, 2, 3, 1),
-- (N'Lmao', N'Trà', 22000, null, 2, 1, 3, 2, 3, 1),
-- (N'Lmao', N'Trà', 22000, null, 2, 1, 3, 2, 3, 1);


-- toppings
IF NOT EXISTS (SELECT 1 FROM toppings)
insert into toppings (name, price) values
(N'Trân châu đen', 5000),
(N'Thạch cà phê', 5000),
(N'Kem cheese', 8000),
(N'Pudding trứng', 8000),
(N'Kem béo', 6000),
(N'Whipping cream', 8000),
(N'Lmao special', 10000);



IF NOT EXISTS (SELECT 1 FROM promo_codes)
insert into promo_codes (code, category, name, min_order_value, value, start_date, end_date, status, display_location, quantity) values
  ('VOUCHER10', N'PHẦN TRĂM', N'Giảm 10%',     50000,     10.00, '2025-12-25 00:00:00', '2026-12-25 23:59:59', 1, N'trên web', 999),
  ('VIP30',     N'PHẦN TRĂM', N'VIP giảm 30%', 100000,    30.00, '2025-12-22 00:00:00', '2026-12-22 23:59:59', 1, N'trên web', 67),
  ('SAVE50K',   N'TRỪ TIỀN',  N'Giảm 50.000đ', 0,      50000.00, '2026-01-01 00:00:00', '2026-12-31 23:59:59', 1, N'đổi thưởng', 69);


INSERT INTO promo_codes (code, category, name, min_order_value, value, start_date, end_date, status, display_location, quantity)
SELECT v.* FROM (VALUES
                     ('WELCOME15',  N'PHẦN TRĂM', N'Chào mừng giảm 15%',       0,         15.00, '2026-06-01', '2026-12-31 23:59:59', 1, N'trên web',    500),
                     ('FLASH20',    N'PHẦN TRĂM', N'Flash sale 20%',        150000,        20.00, '2026-07-07', '2026-07-07 23:59:59', 1, N'trên web',    200),
                     ('SUMMER25',   N'PHẦN TRĂM', N'Hè giảm 25%',           200000,        25.00, '2026-06-01', '2026-08-31 23:59:59', 1, N'trên web',    300),
                     ('FREESHIP',   N'TRỪ TIỀN',  N'Miễn phí vận chuyển',   100000,     30000.00, '2026-06-01', '2026-09-30 23:59:59', 1, N'trên web',    999),
                     ('BIRTHDAY50', N'TRỪ TIỀN',  N'Sinh nhật giảm 50.000đ', 80000,     50000.00, '2026-01-01', '2026-12-31 23:59:59', 1, N'đổi thưởng', 100),
                     ('MEGA100K',   N'TRỪ TIỀN',  N'Giảm 100.000đ đơn lớn', 500000,   100000.00, '2026-06-15', '2026-12-31 23:59:59', 1, N'trên web',     50),
                     ('APP10',      N'PHẦN TRĂM', N'App giảm 10%',           50000,         10.00, '2026-06-01', '2026-12-31 23:59:59', 1, N'trên app',   999),
                     ('REFER200K',  N'TRỪ TIỀN',  N'Giới thiệu bạn 200.000đ',300000,   200000.00, '2026-06-01', '2026-12-31 23:59:59', 1, N'đổi thưởng', 200),
                     ('ENDYEAR40',  N'PHẦN TRĂM', N'Cuối năm giảm 40%',      250000,        40.00, '2026-12-20', '2026-12-31 23:59:59', 1, N'trên web',   150),
                     ('NEWMEM20K',  N'TRỪ TIỀN',  N'Thành viên mới 20.000đ',      0,     20000.00, '2026-06-01', '2026-12-31 23:59:59', 1, N'trên web',   999)
                ) AS v(code, category, name, min_order_value, value, start_date, end_date, status, display_location, quantity)
WHERE NOT EXISTS (
    SELECT 1 FROM promo_codes p WHERE p.code = v.code
);

-- orders
IF NOT EXISTS (SELECT 1 FROM sys.default_constraints WHERE name = 'DF_Orders_CreatedAt')
alter table orders add constraint DF_Orders_CreatedAt default getdate() for created_at;
IF NOT EXISTS (SELECT 1 FROM sys.default_constraints WHERE name = 'DF_Orders_PaymentMethod')
alter table orders add constraint DF_Orders_PaymentMethod default N'Tiền mặt' for payment_method;

IF NOT EXISTS (SELECT 1 FROM orders WHERE order_number = 719523346)
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
IF NOT EXISTS (SELECT 1 FROM order_items WHERE order_id = 1)
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

IF NOT EXISTS (SELECT 1 FROM order_item_toppings WHERE order_item_id = 1)
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
IF NOT EXISTS (SELECT 1 FROM sys.default_constraints WHERE name = 'DF_Invoices_ReceiptType')
alter table invoices add constraint DF_Invoices_ReceiptType default N'Tại quầy' for receipt_type;

IF NOT EXISTS (SELECT 1 FROM invoices WHERE invoice_number = 10001)
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

-- 1. TẠO BẢNG THẺ MOMO
IF OBJECT_ID('test_atm_cards', 'U') IS NULL
BEGIN
CREATE TABLE test_atm_cards (
    id INT IDENTITY(1,1) PRIMARY KEY,
    card_number NVARCHAR(19)  NOT NULL UNIQUE,
    holder_name NVARCHAR(100) NOT NULL,
    expiry_date NVARCHAR(5)   NOT NULL,
    phone       NVARCHAR(10)  NULL,
    bank_name   NVARCHAR(60)  NOT NULL,
    card_status NVARCHAR(15)  NOT NULL DEFAULT 'ACTIVE',
    created_at  DATETIME      DEFAULT GETDATE()
)
END;
-- Thẻ 1
IF NOT EXISTS (SELECT 1 FROM test_atm_cards WHERE card_number = '9704000000000018')
INSERT INTO test_atm_cards VALUES
('9704000000000018', 'NGUYEN VAN A', '03/07', '0987654321', N'Vietcombank', 'ACTIVE', DEFAULT);

-- Thẻ 2
IF NOT EXISTS (SELECT 1 FROM test_atm_cards WHERE card_number = '9704000000000026')
INSERT INTO test_atm_cards VALUES
('9704000000000026', 'NGUYEN VAN A', '03/07', NULL, N'MBBank', 'BLOCKED', DEFAULT);

-- Thẻ 3
IF NOT EXISTS (SELECT 1 FROM test_atm_cards WHERE card_number = '9704000000000034')
INSERT INTO test_atm_cards VALUES
('9704000000000034', 'NGUYEN VAN A', '03/07', '0901234567', N'Agribank', 'INSUFFICIENT', DEFAULT);

-- Thẻ 4
IF NOT EXISTS (SELECT 1 FROM test_atm_cards WHERE card_number = '9704000000000042')
INSERT INTO test_atm_cards VALUES
('9704000000000042', 'NGUYEN VAN A', '03/07', NULL, N'Techcombank', 'LIMIT', DEFAULT);

-- Thẻ 5
IF NOT EXISTS (SELECT 1 FROM test_atm_cards WHERE card_number = '9704111111111111')
INSERT INTO test_atm_cards VALUES
('9704111111111111', 'TRAN THI B', '06/08', '0912345678', N'VPBank', 'ACTIVE', DEFAULT);

-- STORES TABLE
IF OBJECT_ID('stores', 'U') IS NULL
BEGIN
CREATE TABLE stores (
    id            INT IDENTITY(1,1) PRIMARY KEY,
    code          NVARCHAR(100)  NOT NULL,
    address       NVARCHAR(300)  NOT NULL,
    image_url     NVARCHAR(300)  NOT NULL,
    lat           FLOAT          NOT NULL,
    lng           FLOAT          NOT NULL,
    open_time     NVARCHAR(5)    NOT NULL DEFAULT '08:00',
    close_time    NVARCHAR(5)    NOT NULL DEFAULT '22:00',
    maps_url      NVARCHAR(500)  NULL,
    amenities     NVARCHAR(500)  NULL,
    created_at    DATETIME       DEFAULT GETDATE()
)
END;

-- STORE_REVIEWS TABLE
IF OBJECT_ID('store_reviews', 'U') IS NULL
BEGIN
CREATE TABLE store_reviews (
    id            INT IDENTITY(1,1) PRIMARY KEY,
    store_id      INT            NOT NULL REFERENCES stores(id),
    user_id       INT            NULL REFERENCES users(id),
    initials      NVARCHAR(5)    NOT NULL,
    reviewer_name NVARCHAR(100)  NOT NULL,
    stars         TINYINT        NOT NULL CHECK (stars BETWEEN 1 AND 5),
    review_text   NVARCHAR(1000) NOT NULL,
    review_date   DATETIME       DEFAULT GETDATE(),
    created_at    DATETIME       DEFAULT GETDATE()
)
END;

-- SEED STORES
IF NOT EXISTS (SELECT 1 FROM stores WHERE code = N'Drip Lab-Vincom Bà Triệu')
INSERT INTO stores (code, address, image_url, lat, lng, open_time, close_time, maps_url, amenities) VALUES
(N'Drip Lab-Vincom Bà Triệu', N'191 Bà Triệu, Lê Đại Hành, Hai Bà Trưng, Hà Nội', '/IMG/HinhAnh1Coffee.png', 21.0134, 105.8497, '08:00', '22:00', 'https://maps.app.goo.gl/example1', N'["Wi-Fi Miễn Phí","Chỗ Ngồi Ngoài Trời","Thú Cưng","Lớp Latte Art"]');

IF NOT EXISTS (SELECT 1 FROM stores WHERE code = N'Drip Lab-Thái Hà')
INSERT INTO stores (code, address, image_url, lat, lng, open_time, close_time, maps_url, amenities) VALUES
(N'Drip Lab-Thái Hà', N'Tòa nhà Viet Tower, 1 Thái Hà, Trung Liệt, Đống Đa, Hà Nội', '/IMG/HinhAnh2Coffee.png', 21.0197, 105.8363, '08:00', '22:00', 'https://maps.app.goo.gl/example2', N'["Wi-Fi Miễn Phí","Đặt Chỗ Trước","Chỗ Đậu Xe"]');

IF NOT EXISTS (SELECT 1 FROM stores WHERE code = N'Drip Lab-Indochina Plaza')
INSERT INTO stores (code, address, image_url, lat, lng, open_time, close_time, maps_url, amenities) VALUES
(N'Drip Lab-Indochina Plaza', N'241 Xuân Thủy, Dịch Vọng Hậu, Cầu Giấy, Hà Nội', '/IMG/HinhAnh3Coffee.png   ', 21.0380, 105.7846, '08:00', '22:00', 'https://maps.app.goo.gl/example3', N'["Wi-Fi Miễn Phí","Tầng Lầu","Nhạc Sống Cuối Tuần"]');

IF NOT EXISTS (SELECT 1 FROM stores WHERE code = N'Drip Lab-Aeon Mall Hà Đông')
INSERT INTO stores (code, address, image_url, lat, lng, open_time, close_time, maps_url, amenities) VALUES
(N'Drip Lab-Aeon Mall Hà Đông', N'Khu Dân cư Hoàng Văn Thụ, Dương Nội, Hà Đông, Hà Nội', '/IMG/HinhAnh4Coffee.png', 20.9812, 105.7469, '08:00', '22:00', 'https://maps.app.goo.gl/example4', N'["Wi-Fi Miễn Phí","Trong Trung Tâm Thương Mại","Chỗ Đậu Xe Rộng"]');

IF NOT EXISTS (SELECT 1 FROM stores WHERE code = N'Drip Lab-Aeon Mall Long Biên')
INSERT INTO stores (code, address, image_url, lat, lng, open_time, close_time, maps_url, amenities) VALUES
(N'Drip Lab-Aeon Mall Long Biên', N'27 Cổ Linh, Long Biên, Hà Nội', '/IMG/HinhAnh5Coffee.png', 21.0486, 105.9001, '08:00', '22:00', 'https://maps.app.goo.gl/example5', N'["Wi-Fi Miễn Phí","Trong Trung Tâm Thương Mại","Khu Vui Chơi Trẻ Em"]');

-- SEED REVIEWS Store 1
IF NOT EXISTS (SELECT 1 FROM store_reviews WHERE store_id = 1 AND reviewer_name = N'Nguyễn Thanh')
INSERT INTO store_reviews (store_id, initials, reviewer_name, stars, review_text, review_date) VALUES
(1, N'NT', N'Nguyễn Thanh', 5, N'Không khí ở đây tuyệt vời. Tôi đặc biệt thích ly pour-over từ cà phê Ethiopia single-origin. Nhân viên rất chuyên nghiệp và am hiểu về cà phê.', DATEADD(DAY, -2, GETDATE())),
(1, N'LH', N'Lê Hoàng', 4, N'Latte art đẹp lắm! Hơi đông vào cuối tuần nhưng chờ xứng đáng. Tôi gợi ý dòng blend đặc trưng nếu bạn thích vị bùi bùi.', DATEADD(DAY, -7, GETDATE())),
(1, N'PD', N'Phạm Dũng', 5, N'Cold brew ngon nhất khu vực, không chỗ nào bì được. Thiết kế industrial chic rất thu hút.', DATEADD(DAY, -14, GETDATE()));

-- SEED REVIEWS Store 2
IF NOT EXISTS (SELECT 1 FROM store_reviews WHERE store_id = 2 AND reviewer_name = N'Trần Minh')
INSERT INTO store_reviews (store_id, initials, reviewer_name, stars, review_text, review_date) VALUES
(2, N'TM', N'Trần Minh', 5, N'Cơ sở Thái Hà rất yên tĩnh, thích hợp làm việc. Espresso đậm vị, không bị đắng. Sẽ quay lại nhiều lần.', DATEADD(DAY, -3, GETDATE())),
(2, N'HN', N'Hà Ngân', 4, N'Không gian thoáng, nhạc nền nhẹ nhàng. Matcha latte ở đây khá chuẩn vị Nhật. Nhân viên thân thiện.', DATEADD(DAY, -10, GETDATE())),
(2, N'BT', N'Bảo Trân', 5, N'Góc chụp ảnh siêu đẹp ở tầng 2. Flat white rất ngon, cân bằng giữa sữa và espresso hoàn hảo.', DATEADD(DAY, -18, GETDATE()));

-- SEED REVIEWS Store 3
IF NOT EXISTS (SELECT 1 FROM store_reviews WHERE store_id = 3 AND reviewer_name = N'Vũ Hùng')
INSERT INTO store_reviews (store_id, initials, reviewer_name, stars, review_text, review_date) VALUES
(3, N'VH', N'Vũ Hùng', 4, N'Gần ĐH Quốc gia nên hay ghé. Giá hợp lý, cà phê chất lượng tốt. Cuối tuần hơi đông sinh viên.', DATEADD(DAY, -1, GETDATE())),
(3, N'LT', N'Lan Trinh', 5, N'Rang xay tại chỗ nên hương thơm rất đặc trưng. Cold brew ngâm 16 tiếng vị mượt không cần thêm đường.', DATEADD(DAY, -5, GETDATE())),
(3, N'KN', N'Khánh Nam', 4, N'Decor hiện đại, ánh sáng tự nhiên tốt. Ổ cắm điện đầy đủ cho dân làm việc remote.', DATEADD(DAY, -12, GETDATE()));

-- SEED REVIEWS Store 4
IF NOT EXISTS (SELECT 1 FROM store_reviews WHERE store_id = 4 AND reviewer_name = N'Mai Anh')
INSERT INTO store_reviews (store_id, initials, reviewer_name, stars, review_text, review_date) VALUES
(4, N'MA', N'Mai Anh', 5, N'Thuận tiện vì nằm trong Aeon Mall, ghé mua sắm xong vào nghỉ chân là chuẩn. Trà đào ở đây ngon hơn nhiều nơi.', DATEADD(DAY, -4, GETDATE())),
(4, N'QT', N'Quốc Toản', 4, N'Phục vụ nhanh dù đông khách mall. Bạc xỉu vị vừa phải, không quá ngọt. Sẽ quay lại.', DATEADD(DAY, -9, GETDATE())),
(4, N'DL', N'Diệu Linh', 5, N'Mang con đến đây rất ổn vì rộng rãi và thoải mái. Nhân viên vui vẻ, hỗ trợ nhiệt tình.', DATEADD(DAY, -20, GETDATE()));

-- SEED REVIEWS Store 5
IF NOT EXISTS (SELECT 1 FROM store_reviews WHERE store_id = 5 AND reviewer_name = N'Đức Anh')
INSERT INTO store_reviews (store_id, initials, reviewer_name, stars, review_text, review_date) VALUES
(5, N'ĐA', N'Đức Anh', 5, N'Cơ sở Long Biên mới mở nhưng chất lượng không thua các chi nhánh khác. Arabica Highland có vị hoa quả nhẹ rất thú vị.', DATEADD(DAY, -2, GETDATE())),
(5, N'PT', N'Phương Thảo', 4, N'Không gian rộng, phù hợp cả gia đình. Cà phê muối ở đây thêm chút kem béo rất sáng tạo.', DATEADD(DAY, -6, GETDATE())),
(5, N'HV', N'Hoàng Việt', 5, N'View nhìn ra khu vui chơi trẻ em rất dễ chịu. Drip coffee chuẩn vị, phục vụ nhanh.', DATEADD(DAY, -15, GETDATE()));
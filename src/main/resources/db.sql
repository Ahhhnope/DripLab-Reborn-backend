create database CafeDB
go
use CafeDB
go

--USE master;
--ALTER DATABASE CafeDB SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
--DROP DATABASE CafeDB;

------------------------------------------
create table users (
	id int identity(1,1) primary key,
	full_name nvarchar(50),
	email varchar(50),
	password nvarchar(max),
	phone varchar(10),
	avatar nvarchar(max)
);

create table customers (
	id int identity(1,1) primary key,
	full_name nvarchar(50),
	loyalty_point float,
	default_address nvarchar(50),
	date_of_birth date,
	phone varchar(10),
	created_at date default getdate(),
	user_id int foreign key references users (id)
);

create table workers (
    id int identity(1,1) primary key,
    full_name nvarchar(50) not null,
    account varchar(50) not null unique,
    password nvarchar(50) not null,
    created_at date default getdate()
);

create table promo_codes (
	id int identity(1,1) primary key,
	name nvarchar(50),
	code nvarchar(max),
	description nvarchar(100)
);


-------------------------------------------
create table toppings (
	id int identity(1,1) primary key,
	name nvarchar(50) not null,
	price float not null default 0,
	quantity int,
	created_at date default getdate()
);

create table coffee_beans (
	id int primary key identity(1,1),
	name nvarchar(255) not null,
	price float not null default 0,
	quantity int,
	created_at date default getdate()
);

create table milks (
	id int primary key identity(1,1),
	name nvarchar(255) not null,
	price float not null default 0,
	quantity int,
	created_at date default getdate()
);

create table heavy_creams (
	id int primary key identity(1,1),
	name nvarchar(255) not null,
	price float not null default 0,
	quantity int,
	created_at date default getdate()
);

create table ice_creams (
	id int primary key identity(1,1),
	name nvarchar(255) not null,
	price float not null default 0,
	quantity int,
	created_at date default getdate()
);

create table instructions (
	id int primary key identity(1,1),
	name nvarchar(255) not null,
	instructions nvarchar(max),
	created_at date default getdate()
);

create table drinks (
	id int identity(1,1) primary key,
	name nvarchar(50),
	category nvarchar(50) default N'Cà phê',
	
	coffee_bean_id int foreign key references coffee_beans (id),
	milk_id int foreign key references milks (id),
	heavy_cream_id int foreign key references heavy_creams (id),
	ice_cream_id int foreign key references ice_creams (id),
	instruction_id int foreign key references instructions (id),
	
	description nvarchar(50),
	base_price float,
	image_url nvarchar(max),
	active bit default 1
);

-------------------------------------------
create table carts (
	id int identity(1,1) primary key,
	user_id int unique foreign key references users (id)
);

create table cart_items (
	id int identity(1,1) primary key,
	cart_id int foreign key references carts (id),
	product_id int foreign key references drinks (id),
	quantity int
);

create table cart_item_toppings (
	id int identity(1,1) primary key,
	cart_item_id int foreign key references cart_items (id),
	topping_id int foreign key references toppings (id)
);

-------------------------------------------
create table orders (
	id int identity(1,1) primary key,
	order_number int,
	order_date date default getdate(),
	customer_name nvarchar(50),
	customer_phone varchar(10),
	customer_address nvarchar(50),
	original_price float,
	discount_amount float,
	shipping_fee float,
	tax_amount float,
	final_price float,
	note nvarchar(100),
	status nvarchar(20) default N'Chưa giải quyết',
	created_at date default getdate(),
	updated_at date default getdate(),
	customer_id int foreign key references customers (id)
);

create table order_items (
	id int identity(1,1) primary key,
	order_id int foreign key references orders (id),
	drink_id int foreign key references drinks (id),
	quantity int,
	base_price_at_purchase float --base price of items (not like after tax and discounts or something ;-;)
);

create table order_item_toppings (
	id int identity(1,1) primary key,
	order_item_id int foreign key references order_items (id),
	topping_id int foreign key references toppings (id),
	base_price_at_purchase float --same as above table but toppings now
);


create table invoices (
	id int identity(1,1) primary key,
	invoice_number int,
	invoice_date date,
	customer_name nvarchar(50),
	customer_phone varchar(10),
	customer_address nvarchar(50),
	original_price float,
	discount_amount float,
	shipping_fee float,
	tax_amount float,
	final_price float,
	payment_method nvarchar(50),
	payment_status nvarchar(50),
	created_at date default getdate(),
	order_id int foreign key references orders (id),
	customer_id int foreign key references customers (id)
);
-------------------------------------------
insert into workers (full_name, account, password) values
(N'americano', 'abc123', 'lmao'),
(N'robusta', 'kikiki', 'lol')



insert into users (full_name, email, password, phone, avatar) values
('admin', 'admin@gmail.com', '12345a', '0123456789', N'nah'),
('POS system', 'posSystem@gmail.com', '$2a$12$pFOPW6MiM8N9ctlwN0SoPehWv.NLB1/A5QMnJegidMUU2Ucj.umzu', null, N'no')

insert into customers (full_name, loyalty_point, default_address, date_of_birth, phone, user_id) values
('admin', 200000000, '*default address*', '2024-11-21', '0123457689', 1),
('POS system', 0, '*restaurant address*', null, null, 2)

insert into carts (user_id) values ('2')
-- 2 là hệ thống POS


insert into instructions (name, instructions) values
(N'Pha Máy', N'Chiết xuất Espresso tiêu chuẩn 30ml'),
(N'Pha Phin', N'Ủ 2 phút với 20ml nước, sau đó rót thêm 40ml'),
(N'Ủ Lạnh', N'Ủ bột cà phê trong nước lạnh 16 tiếng')

insert into coffee_beans (name, price, quantity) values
(N'Arabica Highland', 0, 500),
(N'Robusta Special', 0, 500)

insert into heavy_creams (name, price, quantity) values 
(N'Kem Béo Rich', 0, 50);

insert into milks (name, price, quantity, created_at) values 
(N'Sữa Đặc Larosee', 0, 100, GETDATE()),
(N'Sữa Tươi Vinamilk', 0, 100, GETDATE());

insert into drinks (name, category, base_price, active, image_url, coffee_bean_id, instruction_id, milk_id) values
(N'Matcha Latte', N'Trà', 45000, 1, N'/IMG/matcha.png', null, null, 2),
(N'Trà Đào', N'Trà', 25000, 1, N'/IMG/tra-dao.png', null, null, null),
(N'Cà Phê Sữa Đá', N'Cà phê', 35000, 1, N'/IMG/cf-sua.png', 2, 2, 1),
(N'Cà Phê Muối', N'Cà phê', 45000, 1, N'/IMG/cf-muoi.png', 1, 1, 1),
(N'Bạc Xỉu', N'Cà phê', 55000, 1, N'/IMG/bac-xiu.png', 2, 1, 1)

insert into toppings (name, price) values
(N'Trân châu đen', 5000),
(N'Thạch cà phê', 5000),
(N'Kem cheese', 8000),
(N'Pudding trứng', 8000),
(N'Kem béo', 6000),
(N'Whipping cream', 8000)


insert into orders (order_number, customer_name, customer_phone, customer_address, original_price, discount_amount, shipping_fee, tax_amount, final_price, note, customer_id) values
('1', 'customer name', '0123456789', 'customer address', 260000, 0, 20000, 26000, (260000-20000-26000), 'this is a test lmao', 1)

select * FROM cart_items 
WHERE cart_id = (SELECT id FROM carts WHERE user_id = 2);


select * from carts
select * from drinks
select * from milks
select * from users
select * from orders
select ci1_0.id,ci1_0.cart_id,ci1_0.product_id,ci1_0.quantity from cart_items ci1_0 where ci1_0.cart_id=1

select u1_0.id,u1_0.avatar,u1_0.email,u1_0.full_name,u1_0.password,u1_0.phone from users u1_0 where u1_0.email='posSystem@gmail.com'

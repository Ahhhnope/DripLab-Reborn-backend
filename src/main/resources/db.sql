create database CafeDB
go
use CafeDB
go

--use master
--go
--drop database CafeDB


------------------------------------------
create table users (
	id int identity(1,1) primary key,
	full_name nvarchar(50),
	email varchar(50),
	password nvarchar(50),
	phone varchar(10)
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

create table promo_codes (
	id int identity(1,1) primary key,
	name nvarchar(50),
	code nvarchar(max),
	description nvarchar(100)
);



-------------------------------------------
create table drinks (
	id int identity(1,1) primary key,
	name nvarchar(50),
	description nvarchar(50),
	base_price float,
	quantity int,
	image_url nvarchar(max)
);

create table toppings (
	id int identity(1,1) primary key,
	name nvarchar(50),
	price float
);

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
insert into users (full_name, email, password, phone) values
('admin', 'admin@gmail.com', '12345', '0123456789'),
('POS system', 'posSystem@gmail.com', 'lmaoPOS', null)

insert into customers (full_name, loyalty_point, default_address, date_of_birth, phone, user_id) values
('admin', 200000000, '*default address*', '2024-11-21', '0123457689', 1),
('POS system', 0, '*restaurant address*', null, null, 2)

insert into carts (user_id) values ('2')

insert into drinks (name, description, base_price, quantity, image_url) values
('Matcha Tea', null, 45000, 100, N'/IMG/tên_ảnh.png'),
('Tea 1', null, 25000, 100, N'/IMG/tên_ảnh.png'),
('Tea 2', null, 35000, 100, N'/IMG/tên_ảnh.png'),
('Tea 3', null, 45000, 100, N'/IMG/tên_ảnh.png'),
('Tea 4', null, 55000, 100, N'/IMG/tên_ảnh.png')

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

select * from orders

select * from carts
select * from drinks
select * from orders
select ci1_0.id,ci1_0.cart_id,ci1_0.product_id,ci1_0.quantity from cart_items ci1_0 where ci1_0.cart_id=1

create database orderdb;

create table orderdb.t_order
(
    orderid varchar(20) primary key,
		userid varchar(20),
		productid varchar(20),
		purchasenum integer,
		statu varchar(20),
		createtime varchar(20)
)

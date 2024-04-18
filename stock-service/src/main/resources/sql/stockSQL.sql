
#创建数据库
CREATE DATABASE stockdb;
#选择使用数据库
USE stockdb;
#创建表t_stock并插入相关数据
DROP TABLE IF EXISTS t_stock;
create table t_stock
(
    id    int(20) auto_increment comment '记录id' primary key,
    p_id  varchar(200)  null comment '商品代码',
    count int(20) default '0' null comment '库存量',
    constraint commodity_code unique (p_id)
)charset = utf8;
insert into t_stock values ('1', 'p001', '1000');
insert into t_stock values ('2', 'p002', '2000');
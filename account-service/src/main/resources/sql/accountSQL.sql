/**
 * @author YRJ
 * @date 2023/4/9 23:56
 */
#创建数据库
#CREATE DATABASE accountdb;
#选择使用数据库
USE accountdb;
#创建表t_account并插入相关数据
DROP TABLE IF EXISTS t_account;
create table t_account
(
    id      int(20) auto_increment comment '记录id' primary key,
    a_id    varchar(200) null comment '账户号码',
    balance float default '0' null comment '账户余额'
)charset = utf8;
insert into t_account values ('1', 'a001', '1000');
insert into t_account values ('2', 'a002', '2000.5')
create database productdb;

USE productdb;

DROP TABLE IF EXISTS product;
CREATE TABLE productdb.product (
                                   id int UNSIGNED NOT NULL AUTO_INCREMENT,
                                   productId varchar(255) NOT NULL DEFAULT '',
                                   productTag varchar(255) DEFAULT NULL,
                                   productName varchar(255) DEFAULT NULL,
                                   productPrice double DEFAULT NULL,
                                   stock int DEFAULT NULL,
                                   businessName varchar(255) DEFAULT NULL,
                                   PRIMARY KEY (id)
)
    ENGINE = INNODB,
    AUTO_INCREMENT = 4,
    AVG_ROW_LENGTH = 5461,
    CHARACTER SET utf8mb4,
    COLLATE utf8mb4_0900_ai_ci;

insert into product values ('1', '14660411222', '水果','沃柑10斤新鲜水果当季一级桔子砂糖蜜桔时令砂糖桔子','17.6','375','贞圣旗舰店');
insert into product values ('2', '21251104162', '手办','原神甘雨手办循循守月','742','100','miLoY旗舰级');
insert into product values ('3', '27988341263', '运动鞋','耐克官方AIR FORCE 1MID ''07空军一号男子运动鞋板鞋CW2289','799','337','nike官方旗舰店');
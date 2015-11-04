CREATE DATABASE IF NOT EXISTS `user`;
USE `user`;

CREATE TABLE IF NOT EXISTS `User` (
  `userId` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `partitionId` int(11) unsigned,
  `userName` varchar(50) NOT NULL,
  `password` varchar(50) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `roles` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`userId`),
  UNIQUE KEY userName (`userName`),
  KEY User_createTime (`createTime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `Wallet` (
  `userId` bigint(20) NOT NULL,
  `balance` decimal(19,2) DEFAULT NULL,
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS `ApiConfig` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
   `name` varchar(50),
   `key1` varchar(50),
   `key2` varchar(50),
   `key3` varchar(50),
   `apiAddress` varchar(512),
   `merchant` varchar(50),
  	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `TopUpOrder` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    `topUpOrderId` varchar(50),
    `topUpTransactionId` varchar(50),
    `payMethod` varchar(50),
    `paymentOrderId` varchar(50),
    `paymentTransactionId` varchar(50),
   `countryCode` int(11),
   `networkOperator` varchar(50),
   `mobileNumber` varchar(50),
   `topUpAmount` decimal(19,2),
   `paymentAmount` decimal(19,2),
   `currency` varchar(50),
   `exchangeRate` decimal(19,2),
   `status` int(11),
   `createTime` datetime DEFAULT NULL,
  	PRIMARY KEY (`id`),
  	KEY `TopUpOrder_createTime` (`createTime`),
  	KEY `TopUpOrder_status` (`status`),
  	KEY `TopUpOrder_mobileNumber` (`mobileNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#测试资源
Truncate TABLE `User`;
insert into `User` (`userId`,`partitionId`, `userName`, `password`, `createTime`, `roles`) values('1','1','test001','vLFfghR5tNV3K9DKhmwArV+SbjWAcgZZzIDTnJ0JgCo=','2015-04-14 21:06:59','user');

#测试资源
Truncate TABLE `Wallet`;
INSERT INTO `Wallet` (`userId`,`balance`) VALUES (100001,'0.00');

Truncate TABLE `ApiConfig`;
INSERT INTO `ApiConfig` (`id`,`name`,`key1`,`key2`,`key3`,`apiAddress`,`merchant`) VALUES (100001,'name','key1','key2','key3','apiAddress','merchant');
INSERT INTO `ApiConfig` (`id`,`name`,`key1`,`key2`,`key3`,`apiAddress`,`merchant`) VALUES (1,'thaitopup','sk_3642aacafee36b2fb8148ad8a45f5f9a','key2','key3','https://thailandtopup.com/api/v1/','hu.lian.cn@gmail.com');
INSERT INTO `ApiConfig` (`id`,`name`,`key1`,`key2`,`key3`,`apiAddress`,`merchant`) VALUES (2,'lian.hu.chinese@gmail.com','uz7ysv5iw3cu0xdfip62ricxtr7uhkyv','key2','key3','https://mapi.alipay.com/gateway.do?_input_charset=utf-8','2088312171023422');

Truncate TABLE `TopUpOrder`;
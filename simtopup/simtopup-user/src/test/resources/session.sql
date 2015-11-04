CREATE TABLE IF NOT EXISTS `Session` (
  `token` varchar(50) NOT NULL,
  `tokenKey` varchar(50) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `roles` varchar(50) DEFAULT NULL,
  `userId` int(10) unsigned DEFAULT NULL,
  `userName` varchar(50) DEFAULT NULL,
  `lastAccessTime` datetime DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  PRIMARY KEY (`token`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



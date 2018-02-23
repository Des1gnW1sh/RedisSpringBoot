/*
MySQL Backup
Source Server Version: 5.1.73
Source Database: test
Date: 2017/3/8 13:58:29
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
--  Table structure for `photo`
-- ----------------------------
DROP TABLE IF EXISTS `photo`;
CREATE TABLE `photo` (
  `photo_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `photo_url` varchar(256) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`photo_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` varchar(50) NOT NULL DEFAULT '',
  `username` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `users`
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) DEFAULT NULL,
  `pwd` varchar(128) DEFAULT NULL,
  `salt` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records 
-- ----------------------------
INSERT INTO `photo` VALUES ('1','/imgs/xxx1.jpg','1'), ('2','/imgs/xxxw2.jpg','1'), ('3','/sdf/ssdfs.png','2');
INSERT INTO `user` VALUES ('1','肖昌伟'), ('2','317409898@qq.com'), ('3','SpringBoot&Cloud学习整理');
INSERT INTO `users` VALUES ('1','肖昌伟','317409898@qq.com','高级JAVA工程师'), ('2','sdf2','sdf2','sf2');

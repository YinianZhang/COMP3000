/*
 Navicat Premium Data Transfer

 Source Server         : COMP3000
 Source Server Type    : MySQL
 Source Server Version : 80033
 Source Host           : localhost:3306
 Source Schema         : manager

 Target Server Type    : MySQL
 Target Server Version : 80033
 File Encoding         : 65001

 Date: 21/04/2024 22:38:39
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES (1, 'admin', '131022114yi', 'YINIANZHANG', 'http://localhost:9090/files/1712570133217-A.jpg', 'ADMIN', '13102022658', 'yinian.zhang@students.plymouth.ac.uk');

-- ----------------------------
-- Table structure for adopt
-- ----------------------------
DROP TABLE IF EXISTS `adopt`;
CREATE TABLE `adopt`  (
  `aid` int(0) NOT NULL AUTO_INCREMENT,
  `user_id` int(0) NULL DEFAULT NULL,
  `pet_id` int(0) NULL DEFAULT NULL,
  `atime` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `astatus` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`aid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of adopt
-- ----------------------------
INSERT INTO `adopt` VALUES (10, 20, 10, '2024-04-21', 'Adopted');

-- ----------------------------
-- Table structure for foster
-- ----------------------------
DROP TABLE IF EXISTS `foster`;
CREATE TABLE `foster`  (
  `fid` int(0) NOT NULL AUTO_INCREMENT,
  `user_id` int(0) NULL DEFAULT NULL,
  `pet_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `photo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `ftime` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `days` int(0) NULL DEFAULT NULL,
  `service` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `room_id` int(0) NULL DEFAULT NULL,
  `spend` decimal(10, 2) NULL DEFAULT NULL,
  PRIMARY KEY (`fid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 91 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of foster
-- ----------------------------
INSERT INTO `foster` VALUES (91, 20, 'Viki', 'http://localhost:9090/files/1713732724749-fostercat1.jpg', '2024-04-21', 2, 'IntermediateFoster', 'WaitingFoster', 1, 100.00);

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods`  (
  `gid` int(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `gimg` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `price` double(10, 2) NULL DEFAULT NULL,
  `numb` int(0) NULL DEFAULT 0,
  PRIMARY KEY (`gid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods` VALUES (11, 'Dogfood-Korea', 'http://localhost:9090/files/1713731428414-dogfood1.jpg', 20.00, 30);
INSERT INTO `goods` VALUES (12, 'Dogfood-America', 'http://localhost:9090/files/1713731540029-dogfood2.jpg', 30.00, 20);
INSERT INTO `goods` VALUES (13, 'Catfood-Thailand', 'http://localhost:9090/files/1713731790871-catfood1.jpg', 15.00, 30);
INSERT INTO `goods` VALUES (14, 'Catfood-America', 'http://localhost:9090/files/1713731861287-catfood2.jpg', 25.00, 46);

-- ----------------------------
-- Table structure for notice
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice`  (
  `nid` int(0) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`nid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of notice
-- ----------------------------
INSERT INTO `notice` VALUES (22, 'Begin', 'System begin running', '2024-04-21', 'admin');
INSERT INTO `notice` VALUES (23, 'Welcome', 'Welcome to use', '2024-04-21', 'admin');

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `goods_id` int(0) NULL DEFAULT NULL,
  `order_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `user_id` int(0) NULL DEFAULT NULL,
  `time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `num` int(0) NULL DEFAULT NULL,
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `price` double(10, 2) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES (17, 14, '20240421215417541550', 20, '2024-04-21 21:54:17', 'YinianZhang', '13102027689', 'Plymouth PL1 1LD', 4, 'WaitingDelivery', 100.00);

-- ----------------------------
-- Table structure for pet
-- ----------------------------
DROP TABLE IF EXISTS `pet`;
CREATE TABLE `pet`  (
  `pid` int(0) NOT NULL AUTO_INCREMENT,
  `img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `sex` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `age` int(0) NULL DEFAULT NULL,
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `intro` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`pid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pet
-- ----------------------------
INSERT INTO `pet` VALUES (7, 'http://localhost:9090/files/1713731004362-cat1.jpg', 'Kiki', 'female', 1, 'CAT', 'WaitingAdoption', 'A cute kitten found on the side of road');
INSERT INTO `pet` VALUES (8, 'http://localhost:9090/files/1713731079421-cat2.jpg', 'Paka', 'male', 2, 'CAT', 'WaitingAdoption', 'A sad kitten found on the side of library');
INSERT INTO `pet` VALUES (9, 'http://localhost:9090/files/1713731192316-dog1.jpg', 'Pipi', 'male', 4, 'DOG', 'WaitingAdoption', 'A little dog that is been wandering');
INSERT INTO `pet` VALUES (10, 'http://localhost:9090/files/1713731308516-dog2.jpg', 'Lerry', 'male', 1, 'DOG', 'Adopted', 'A funny Puppy');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `uid` int(0) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `wallet` double(10, 2) NULL DEFAULT 0.00,
  PRIMARY KEY (`uid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (20, 'Meki', '123qweuioa', 'Jerry', 'http://localhost:9090/files/1713714661286-user1.jpg', 'USER', '13102024653', '1029115832@qq.com', 0.00);

SET FOREIGN_KEY_CHECKS = 1;

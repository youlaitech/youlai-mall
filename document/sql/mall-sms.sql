/*
 Navicat Premium Data Transfer

 Source Server         : 101.37.69.49
 Source Server Type    : MySQL
 Source Server Version : 80022
 Source Host           : 101.37.69.49:3306
 Source Schema         : mall-sms

 Target Server Type    : MySQL
 Target Server Version : 80022
 File Encoding         : 65001

 Date: 12/01/2021 00:00:27
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sms_advert
-- ----------------------------
DROP TABLE IF EXISTS `sms_advert`;
CREATE TABLE `sms_advert`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '广告名称',
  `pic` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '图片地址',
  `start_time` datetime(0) NULL DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '结束时间',
  `status` tinyint(1) NOT NULL COMMENT '状态：1-开启  0-关闭',
  `sort` int(0) NULL DEFAULT NULL COMMENT '排序',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '链接地址',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `gmt_create` datetime(0) NULL DEFAULT NULL,
  `gmt_modified` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '广告表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sms_advert
-- ----------------------------
INSERT INTO `sms_advert` VALUES (1, '小程序首页轮播图1', 'http://101.37.69.49:9000/default/d52ffbad86244b3b8042c472004eec70.jpg', '2020-10-25 09:12:14', '2023-10-06 09:12:31', 1, 1, NULL, NULL, '2020-10-25 00:13:51', '2021-01-01 16:14:10');
INSERT INTO `sms_advert` VALUES (2, '小程序首页轮播图2', 'http://101.37.69.49:9000/default/aa2fc49ed91e406da5a1d507258cafbc.jpg', '2020-10-25 09:25:07', '2023-10-06 00:00:00', 1, 2, NULL, NULL, '2020-10-25 09:25:23', '2021-01-01 16:14:22');
INSERT INTO `sms_advert` VALUES (3, '小程序首页轮播图3', 'http://101.37.69.49:9000/default/d3bf2a650a374526a4733d0490571339.jpg', '2020-10-25 09:25:37', '2020-10-31 00:00:00', 1, 3, NULL, NULL, '2020-10-25 09:25:56', '2021-01-01 16:14:30');
INSERT INTO `sms_advert` VALUES (4, '小程序首页轮播图4', 'http://101.37.69.49:9000/default/c2e8f39d49c1478b8e400cefb09c9075.jpg', '2020-10-25 00:00:00', '2020-10-31 00:00:00', 1, 4, NULL, NULL, '2020-10-25 09:26:47', '2021-01-01 16:14:37');
INSERT INTO `sms_advert` VALUES (5, '小程序首页轮播图5', 'http://101.37.69.49:9000/default/095c4f8efa224c2383aa73af427a2587.jpg', '2020-10-25 00:00:00', '2020-10-31 00:00:00', 1, 5, NULL, NULL, '2020-10-25 10:51:02', '2021-01-01 16:14:44');
INSERT INTO `sms_advert` VALUES (6, '小程序首页轮播图6', 'http://101.37.69.49:9000/default/3e80396f78814846a53b4e963ee3adb5.jpg', '2021-01-22 00:00:00', '2021-01-15 00:00:00', 1, 6, NULL, NULL, NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;

/*
 Navicat Premium Data Transfer

 Source Server         : a.youlai.store
 Source Server Type    : MySQL
 Source Server Version : 80023
 Source Host           : a.youlai.store:3306
 Source Schema         : mall-sms

 Target Server Type    : MySQL
 Target Server Version : 80023
 File Encoding         : 65001

 Date: 01/03/2021 00:08:33
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
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '广告表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sms_advert
-- ----------------------------
INSERT INTO `sms_advert` VALUES (2, '小程序首页轮播图2', 'http://101.37.69.49:9000/default/aa2fc49ed91e406da5a1d507258cafbc.jpg', '2020-10-25 09:25:07', '2023-10-06 00:00:00', 1, 2, NULL, NULL, '2020-10-25 09:25:23', '2021-02-10 10:37:29');
INSERT INTO `sms_advert` VALUES (3, '小程序首页轮播图3', 'http://101.37.69.49:9000/default/d3bf2a650a374526a4733d0490571339.jpg', '2020-10-25 09:25:37', '2020-10-31 00:00:00', 1, 3, NULL, NULL, '2020-10-25 09:25:56', '2021-01-01 16:14:30');
INSERT INTO `sms_advert` VALUES (4, '小程序首页轮播图4', 'http://101.37.69.49:9000/default/c2e8f39d49c1478b8e400cefb09c9075.jpg', '2020-10-25 00:00:00', '2020-10-31 00:00:00', 1, 4, NULL, NULL, '2020-10-25 09:26:47', '2021-01-01 16:14:37');
INSERT INTO `sms_advert` VALUES (5, '小程序首页轮播图5', 'http://101.37.69.49:9000/default/095c4f8efa224c2383aa73af427a2587.jpg', '2020-10-25 00:00:00', '2020-10-31 00:00:00', 1, 5, NULL, NULL, '2020-10-25 10:51:02', '2021-01-01 16:14:44');

DROP TABLE IF EXISTS `sms_seckill_session`;
CREATE TABLE `sms_seckill_session` (
   `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
   `name` varchar(200) DEFAULT '' NOT NULL COMMENT '场次名称',
   `start_time` datetime DEFAULT NULL COMMENT '每日开始时间',
   `end_time` datetime DEFAULT NULL COMMENT '每日结束时间',
   `status` tinyint(1) DEFAULT NULL COMMENT '启用状态 1-开启  0-关闭',
   `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
   `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='秒杀活动场次';

DROP TABLE IF EXISTS `sms_seckill_sku_relation`;
CREATE TABLE `sms_seckill_sku_relation` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
    `session_id` bigint(20) DEFAULT NULL COMMENT '活动场次id',
    `sku_id` bigint(20) DEFAULT NULL COMMENT '商品id',
    `seckill_price` bigint DEFAULT '0' NOT NULL COMMENT '秒杀价格',
    `seckill_count` int DEFAULT '0' NOT NULL COMMENT '秒杀总量',
    `seckill_limit` int DEFAULT '0' NOT NULL COMMENT '每人限购数量',
    `seckill_sort` int(11) DEFAULT NULL COMMENT '排序',
    `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
    `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='秒杀活动商品关联';

SET FOREIGN_KEY_CHECKS = 1;

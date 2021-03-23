/*
 Navicat Premium Data Transfer

 Source Server         : www.youlai.store
 Source Server Type    : MySQL
 Source Server Version : 80023
 Source Host           : www.youlai.store:3306
 Source Schema         : mall_sms

 Target Server Type    : MySQL
 Target Server Version : 80023
 File Encoding         : 65001

 Date: 22/03/2021 09:27:41
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
  `start_time` datetime(0) DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime(0) DEFAULT NULL COMMENT '结束时间',
  `status` tinyint(1) NOT NULL COMMENT '状态：1-开启  0-关闭',
  `sort` int(0) DEFAULT NULL COMMENT '排序',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '链接地址',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  `gmt_create` datetime(0) DEFAULT NULL,
  `gmt_modified` datetime(0) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '广告表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sms_advert
-- ----------------------------
INSERT INTO `sms_advert` VALUES (2, '小程序首页轮播图2', 'http://101.37.69.49:9000/default/aa2fc49ed91e406da5a1d507258cafbc.jpg', '2020-10-25 09:25:07', '2023-10-06 00:00:00', 1, 2, NULL, NULL, '2020-10-25 09:25:23', '2021-02-10 10:37:29');
INSERT INTO `sms_advert` VALUES (3, '小程序首页轮播图3', 'http://101.37.69.49:9000/default/d3bf2a650a374526a4733d0490571339.jpg', '2020-10-25 09:25:37', '2020-10-31 00:00:00', 1, 3, NULL, NULL, '2020-10-25 09:25:56', '2021-01-01 16:14:30');
INSERT INTO `sms_advert` VALUES (4, '小程序首页轮播图4', 'http://101.37.69.49:9000/default/c2e8f39d49c1478b8e400cefb09c9075.jpg', '2020-10-25 00:00:00', '2020-10-31 00:00:00', 1, 4, NULL, NULL, '2020-10-25 09:26:47', '2021-01-01 16:14:37');
INSERT INTO `sms_advert` VALUES (5, '小程序首页轮播图5', 'http://101.37.69.49:9000/default/095c4f8efa224c2383aa73af427a2587.jpg', '2020-10-25 00:00:00', '2020-10-31 00:00:00', 1, 5, NULL, NULL, '2020-10-25 10:51:02', '2021-01-01 16:14:44');

-- ----------------------------
-- Table structure for sms_coupon
-- ----------------------------
DROP TABLE IF EXISTS `sms_coupon`;
CREATE TABLE `sms_coupon`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `title` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '优惠券标题（有图片则显示图片）：无门槛50元优惠券 | 单品最高减2000元',
  `img` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '图片',
  `type` int(0) NOT NULL DEFAULT 1 COMMENT '1满减券 2叠加满减券 3无门槛券（需要限制大小）',
  `publish` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '发布状态, PUBLISH发布，DRAFT草稿，OFFLINE下线',
  `condition_price` bigint(0) NOT NULL DEFAULT 0 COMMENT '满多少才可以使用（为0则不限制金额）',
  `price` bigint(0) NOT NULL COMMENT '抵扣价格',
  `publish_count` int(0) NOT NULL DEFAULT 1 COMMENT '优惠券总量',
  `limit_count` int(0) NOT NULL DEFAULT 1 COMMENT '限领张数(默认为1，为0则不限制)',
  `take_count` int(0) NOT NULL DEFAULT 0 COMMENT '已领取的优惠券数量',
  `used_count` int(0) NOT NULL DEFAULT 0 COMMENT '已使用的优惠券数量',
  `start_time` datetime(0) NOT NULL COMMENT '发放开始时间',
  `end_time` datetime(0) NOT NULL COMMENT '发放结束时间',
  `valid_days` int(0) NOT NULL DEFAULT 1 COMMENT '自领取之日起有效天数',
  `status` int(0) NOT NULL DEFAULT 1 COMMENT '逻辑删除使用',
  `gmt_create` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1372839943053258753 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '优惠券表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sms_coupon
-- ----------------------------
INSERT INTO `sms_coupon` VALUES (1, '新人无门槛优惠券,立减20元', 'http://file.gorun996.com/img/2021/03/17/211545.png', 3, ' PUBLISH', 0, 1500, 1000, 1, 0, 0, '2021-03-17 21:10:35', '2025-03-17 21:10:38', 7, 1, '2021-03-17 21:10:54', '2021-03-17 21:10:56');
INSERT INTO `sms_coupon` VALUES (2, '满减优惠券', NULL, 1, 'PUBLISH', 50, 2000, 100, 1, 0, 0, '2021-03-17 21:19:22', '2022-03-17 21:19:24', 7, 1, '2021-03-17 21:19:44', '2021-03-17 21:19:46');

-- ----------------------------
-- Table structure for sms_coupon_record
-- ----------------------------
DROP TABLE IF EXISTS `sms_coupon_record`;
CREATE TABLE `sms_coupon_record`  (
  `id` bigint unsigned NOT NULL,
  `coupon_id` bigint(0) DEFAULT NULL COMMENT '优惠券id',
  `use_state` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '使用状态  可用 NEW,已使用USED,过期 EXPIRED;',
  `user_id` bigint(0) DEFAULT NULL COMMENT '用户id',
  `user_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '用户昵称（冗余字段）',
  `coupon_type` int(0) NOT NULL DEFAULT 1 COMMENT '1满减券 2叠加满减券 3无门槛券',
  `coupon_title` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '优惠券标题',
  `condition_price` bigint(0) NOT NULL DEFAULT 0 COMMENT '满多少才可以使用（为0则不限制金额）',
  `price` bigint(0) NOT NULL COMMENT '抵扣价格',
  `start_time` datetime(0) DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime(0) DEFAULT NULL COMMENT '结束时间',
  `order_id` bigint(0) DEFAULT NULL COMMENT '订单id',
  `status` int(0) NOT NULL DEFAULT 1 COMMENT '逻辑删除使用',
  `gmt_create` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1372841652324487169 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '优惠券领劵使用记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sms_coupon_record
-- ----------------------------
INSERT INTO `sms_coupon_record` VALUES (1, 1, 'NEWS', 1, 'zhgsan', 1, '大促销', 50, 20, '2021-03-19 11:12:42', '2021-04-19 11:12:50', NULL, 1, NULL, NULL);

-- ----------------------------
-- Table structure for sms_seckill_session
-- ----------------------------
DROP TABLE IF EXISTS `sms_seckill_session`;
CREATE TABLE `sms_seckill_session`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '场次名称',
  `start_time` datetime(0) DEFAULT NULL COMMENT '每日开始时间',
  `end_time` datetime(0) DEFAULT NULL COMMENT '每日结束时间',
  `status` tinyint(1) DEFAULT NULL COMMENT '启用状态 1-开启  0-关闭',
  `gmt_create` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '秒杀活动场次' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sms_seckill_session
-- ----------------------------
INSERT INTO `sms_seckill_session` VALUES (1, '测试秒杀场次1', '2021-03-07 20:20:50', '2021-03-09 20:20:53', 1, '2021-03-07 20:21:04', '2021-03-07 20:21:07');
INSERT INTO `sms_seckill_session` VALUES (2, '测试秒杀场次2', '2021-03-07 20:20:50', '2021-03-09 20:20:53', 1, '2021-03-07 20:21:04', '2021-03-07 20:21:07');

-- ----------------------------
-- Table structure for sms_seckill_sku_relation
-- ----------------------------
DROP TABLE IF EXISTS `sms_seckill_sku_relation`;
CREATE TABLE `sms_seckill_sku_relation`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `session_id` bigint(0) DEFAULT NULL COMMENT '活动场次id',
  `sku_id` bigint(0) DEFAULT NULL COMMENT '商品id',
  `seckill_price` bigint(0) NOT NULL DEFAULT 0 COMMENT '秒杀价格',
  `seckill_count` int(0) NOT NULL DEFAULT 0 COMMENT '秒杀总量',
  `seckill_limit` int(0) NOT NULL DEFAULT 0 COMMENT '每人限购数量',
  `seckill_sort` int(0) DEFAULT NULL COMMENT '排序',
  `gmt_create` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '秒杀活动商品关联' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sms_seckill_sku_relation
-- ----------------------------
INSERT INTO `sms_seckill_sku_relation` VALUES (1, 1, 186, 10, 100, 1, 1, '2021-03-07 20:22:01', '2021-03-07 20:22:03');
INSERT INTO `sms_seckill_sku_relation` VALUES (2, 2, 186, 10, 100, 1, 1, '2021-03-07 20:22:01', '2021-03-07 20:22:03');
INSERT INTO `sms_seckill_sku_relation` VALUES (3, 2, 187, 10, 100, 1, 1, '2021-03-07 20:22:01', '2021-03-07 20:22:03');

SET FOREIGN_KEY_CHECKS = 1;

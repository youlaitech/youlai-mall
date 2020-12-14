/*
 Navicat Premium Data Transfer

 Source Server         : 101.37.69.49
 Source Server Type    : MySQL
 Source Server Version : 80022
 Source Host           : 101.37.69.49:3306
 Source Schema         : youlai-mall

 Target Server Type    : MySQL
 Target Server Version : 80022
 File Encoding         : 65001

 Date: 14/12/2020 17:20:36
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for oms_order
-- ----------------------------
DROP TABLE IF EXISTS `oms_order`;
CREATE TABLE `oms_order`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `order_sn` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '订单编号',
  `member_id` bigint(0) NOT NULL COMMENT '会员id',
  `status` smallint(0) NOT NULL DEFAULT 0 COMMENT '订单状态：11-未付款 12-用户取消 13-系统取消 21-已付款 22-申请退款 23-已退款 31-已发货 41-用户收货 42-系统收货',
  `source` tinyint(0) NOT NULL COMMENT '订单来源：1-微信小程序 2-APP 3-PC',
  `consignee` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '收货人',
  `mobile` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '收货人联系方式',
  `postcode` char(6) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '邮编',
  `address` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '收货地址',
  `message` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '买家留言',
  `coupon_id` bigint(0) DEFAULT NULL COMMENT '优惠券id',
  `order_price` bigint(0) DEFAULT NULL COMMENT '订单费用',
  `sku_price` bigint(0) DEFAULT NULL COMMENT '商品费用',
  `freight_price` bigint(0) DEFAULT NULL COMMENT '运费',
  `coupon_price` bigint(0) DEFAULT NULL COMMENT '优惠券抵扣费用',
  `integration_price` bigint(0) DEFAULT NULL COMMENT '积分抵扣费用',
  `pay_price` decimal(10, 0) DEFAULT NULL COMMENT '支付费用',
  `pay_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '付款编号',
  `pay_channel` tinyint(0) DEFAULT NULL COMMENT '支付方式',
  `gmt_pay` datetime(0) DEFAULT NULL COMMENT '支付时间',
  `logistics_channel` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '物流渠道',
  `logistics_no` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '物流单号',
  `gmt_delivery` datetime(0) DEFAULT NULL COMMENT '发货时间',
  `refund_amount` decimal(10, 0) DEFAULT NULL COMMENT '退款金额',
  `refund_type` tinyint(0) DEFAULT NULL COMMENT '退款方式',
  `refund_note` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '退款备注',
  `gmt_refund` datetime(0) DEFAULT NULL COMMENT '退款时间',
  `gmt_confirm` datetime(0) DEFAULT NULL COMMENT '确认收货时间',
  `gmt_create` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) DEFAULT NULL COMMENT '修改时间',
  `deleted` tinyint(0) DEFAULT NULL COMMENT '是否删除： 0-正常 1-已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '订单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oms_order
-- ----------------------------
INSERT INTO `oms_order` VALUES (2, '1331886187098542080', 1315699931359424513, 11, 1, 'str', 'str', 'str', 'str', NULL, 0, 10000, 0, 0, 0, 0, 10000, 'str', 1, '2020-11-26 16:28:04', 'SF', '202011260000001', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for oms_order_item
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_item`;
CREATE TABLE `oms_order_item`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `order_id` bigint(0) NOT NULL COMMENT '订单id',
  `spu_id` bigint(0) NOT NULL COMMENT '商品id',
  `spu_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品名称',
  `sku_id` bigint(0) NOT NULL COMMENT '货品id',
  `sku_bar_code` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '货品条码',
  `sku_specifications` json NOT NULL COMMENT '货品规格',
  `sku_price` bigint(0) NOT NULL COMMENT '货品价格',
  `sku_quantity` int(0) NOT NULL COMMENT '货品数量',
  `sku_pic` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '货品图片',
  `gmt_create` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '订单明细表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oms_order_item
-- ----------------------------
INSERT INTO `oms_order_item` VALUES (1, 2, 0, 'str', 0, 'str', '{}', 0, 0, 'str', NULL, NULL);

-- ----------------------------
-- Table structure for pms_brand
-- ----------------------------
DROP TABLE IF EXISTS `pms_brand`;
CREATE TABLE `pms_brand`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '品牌名称',
  `first_letter` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '检索首字母',
  `logo` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '品牌logo图片地址',
  `sort` int(0) DEFAULT NULL COMMENT '排序',
  `status` tinyint(0) DEFAULT NULL COMMENT '状态: 1-正常 0-禁用',
  `gmt_create` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品品牌' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pms_brand
-- ----------------------------
INSERT INTO `pms_brand` VALUES (1, '小米', 'X', 'http://101.37.69.49:9000/default/1e5d01a4b332445fa8bc554d2d63e163.png', 1, 1, NULL, NULL);
INSERT INTO `pms_brand` VALUES (2, '华为', 'H', 'http://101.37.69.49:9000/default/adfc8aec667744a989f5d84bac8bc3a7.jpg', 2, 1, NULL, NULL);

-- ----------------------------
-- Table structure for pms_category
-- ----------------------------
DROP TABLE IF EXISTS `pms_category`;
CREATE TABLE `pms_category`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '分类名称',
  `parent_id` bigint(0) NOT NULL,
  `level` int(0) DEFAULT NULL COMMENT '层级',
  `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '图标地址',
  `sort` int(0) DEFAULT NULL COMMENT '排序',
  `status` tinyint(1) DEFAULT NULL COMMENT '显示状态: 0-隐藏 1-显示',
  `gmt_create` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品分类表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pms_category
-- ----------------------------
INSERT INTO `pms_category` VALUES (1, '男装', 0, 0, '', NULL, 1, NULL, NULL);
INSERT INTO `pms_category` VALUES (2, '衣服', 1, 1, NULL, NULL, 1, NULL, NULL);
INSERT INTO `pms_category` VALUES (3, '裤子', 1, 1, NULL, NULL, 1, NULL, NULL);
INSERT INTO `pms_category` VALUES (4, 'T恤2', 2, 2, 'https://i.loli.net/2020/05/08/dVvpaQ8NHkWAC2c.jpg', 1, 1, NULL, NULL);
INSERT INTO `pms_category` VALUES (5, '夹克', 2, 2, NULL, NULL, 1, NULL, NULL);
INSERT INTO `pms_category` VALUES (6, '西裤', 3, 2, NULL, NULL, 1, NULL, NULL);
INSERT INTO `pms_category` VALUES (7, '牛仔裤', 3, 2, NULL, NULL, 1, NULL, NULL);
INSERT INTO `pms_category` VALUES (8, '手机', 0, 0, NULL, NULL, 1, NULL, NULL);

-- ----------------------------
-- Table structure for pms_sku
-- ----------------------------
DROP TABLE IF EXISTS `pms_sku`;
CREATE TABLE `pms_sku`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `spu_id` bigint(0) NOT NULL COMMENT '商品id',
  `specification` json NOT NULL COMMENT '商品规格',
  `bar_code` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '商品码',
  `pic` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '商品SKU图片',
  `origin_price` bigint(0) NOT NULL COMMENT '原价',
  `price` bigint(0) NOT NULL COMMENT '现价',
  `stock` int(0) NOT NULL DEFAULT 0 COMMENT '库存',
  `gmt_create` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `pms_sku_bar_code_uindex`(`bar_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 42 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '货品表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pms_spu
-- ----------------------------
DROP TABLE IF EXISTS `pms_spu`;
CREATE TABLE `pms_spu`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品名称',
  `category_id` bigint(0) NOT NULL COMMENT '商品类型id',
  `brand_id` bigint(0) DEFAULT NULL COMMENT '商品品牌id',
  `origin_price` bigint(0) NOT NULL COMMENT '原价',
  `price` bigint(0) NOT NULL COMMENT '现价',
  `sales` int(0) DEFAULT 0 COMMENT '销量',
  `pic` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '商品主图',
  `album` json COMMENT '商品相册',
  `unit` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '单位',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '商品简介',
  `detail` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '商品详情',
  `status` tinyint(0) DEFAULT NULL COMMENT '商品状态：0-下架 1-上架',
  `gmt_create` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pms_spu
-- ----------------------------
INSERT INTO `pms_spu` VALUES (21, 'Redmi Note9', 8, 1, 139900, 129900, 0, 'http://101.37.69.49:9000/default/122b67003c774114bb01d5916d31b805.jpg', '[\"http://101.37.69.49:9000/default/daaab2f97acc47f5abf49267ae1f1404.jpg\", \"http://101.37.69.49:9000/default/19b9eb26245e42fa925e04bf39978323.jpg\"]', '台', '性价比之王', '<p>三生三世</p>', 1, NULL, NULL);

-- ----------------------------
-- Table structure for pms_spu_attribute
-- ----------------------------
DROP TABLE IF EXISTS `pms_spu_attribute`;
CREATE TABLE `pms_spu_attribute`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `spu_id` bigint(0) NOT NULL COMMENT '商品id',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '属性名称',
  `value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '属性值',
  `gmt_create` datetime(0) DEFAULT NULL,
  `gmt_modified` datetime(0) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品属性表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pms_spu_attribute
-- ----------------------------
INSERT INTO `pms_spu_attribute` VALUES (13, 21, '上市时间', '2020-11-24', NULL, NULL);

-- ----------------------------
-- Table structure for pms_spu_specification
-- ----------------------------
DROP TABLE IF EXISTS `pms_spu_specification`;
CREATE TABLE `pms_spu_specification`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `spu_id` bigint(0) NOT NULL COMMENT '商品id',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '规格名称',
  `value` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '规格值',
  `gmt_create` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 47 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品规格表' ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '广告表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sms_advert
-- ----------------------------
INSERT INTO `sms_advert` VALUES (1, '小程序首页轮播图1', 'http://101.37.69.49:9000/default/19248a50b674402bb6825683e7ffd070.jpg', '2020-10-25 09:12:14', '2020-10-31 09:12:31', 1, 1, NULL, NULL, '2020-10-25 00:13:51', '2020-10-25 10:51:22');
INSERT INTO `sms_advert` VALUES (2, '小程序首页轮播图2', 'http://101.37.69.49:9000/default/2c8f4023f98c4fa0a843907b9af6b8a4.jpg', '2020-10-25 09:25:07', '2020-10-31 00:00:00', 1, 2, NULL, NULL, '2020-10-25 09:25:23', '2020-10-25 10:51:19');
INSERT INTO `sms_advert` VALUES (3, '小程序首页轮播图3', 'http://101.37.69.49:9000/default/7d21e401bea247428dc5b64bf67b9351.jpg', '2020-10-25 09:25:37', '2020-10-31 00:00:00', 1, 3, NULL, NULL, '2020-10-25 09:25:56', '2020-10-25 10:51:14');
INSERT INTO `sms_advert` VALUES (4, '小程序首页轮播图4', 'http://101.37.69.49:9000/default/545b9ba066d246f7a26bea54622b966c.jpg', '2020-10-25 00:00:00', '2020-10-31 00:00:00', 1, 4, NULL, NULL, '2020-10-25 09:26:47', '2020-10-25 10:51:10');
INSERT INTO `sms_advert` VALUES (5, '小程序首页轮播图5', 'http://101.37.69.49:9000/default/a804f39f69a84b08abb3dc7fbda51efe.jpg', '2020-10-25 00:00:00', '2020-10-31 00:00:00', 1, 5, NULL, NULL, '2020-10-25 10:51:02', '2020-10-25 10:51:02');

-- ----------------------------
-- Table structure for sms_coupon
-- ----------------------------
DROP TABLE IF EXISTS `sms_coupon`;
CREATE TABLE `sms_coupon`  (
  `id` bigint(0) NOT NULL,
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '优惠券名称',
  `type` tinyint(0) DEFAULT NULL COMMENT '优惠券类型',
  `discount` decimal(10, 2) DEFAULT NULL COMMENT '优惠金额',
  `code` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '优惠券兑换码',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '优惠券描述',
  `days` int(0) DEFAULT NULL COMMENT '领取时间开始的有效天数',
  `start_time` datetime(0) DEFAULT NULL COMMENT '优惠券有效开始时间',
  `end_time` datetime(0) DEFAULT NULL COMMENT '优惠券有效结束时间',
  `gmt_create` datetime(0) DEFAULT NULL,
  `gmt_modified` datetime(0) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ums_member
-- ----------------------------
DROP TABLE IF EXISTS `ums_member`;
CREATE TABLE `ums_member`  (
  `id` bigint(0) NOT NULL,
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `gender` tinyint(1) DEFAULT NULL,
  `nickname` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `mobile` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `openid` char(28) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `session_key` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `status` tinyint(1) DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT NULL,
  `gmt_create` datetime(0) DEFAULT NULL,
  `gmt_modified` datetime(0) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ums_member
-- ----------------------------
INSERT INTO `ums_member` VALUES (1315699931359424513, 'oUBUG5hAB_8EMrSaqd2HjJQBFg74', '$2a$10$izTOlQmmWfYwY56Dc5mdY../m4a3345wSPKN2X5ldh9GgwWKY.sea', 1, '郝先瑞', '17621590365', '2020-11-27', 'https://thirdwx.qlogo.cn/mmopen/vi_32/J31cY2qVWviaOqhjPlr18VY5ic1SUvDESG1mQkicQfFugWibYe7VJIhYJBZYDBib0T4TJ8KClTmP3gOF1eOk91ctibRQ/132', 'oUBUG5hAB_8EMrSaqd2HjJQBFg74', NULL, 1, 0, NULL, NULL);
INSERT INTO `ums_member` VALUES (1338143290000621569, 'oEMah4qx8utBwve1_5U2bq4z9Ucw', '$2a$10$1fwuJq88DI8DvYuSkIbiTeTFIAeVQ3ZwgOz6igWQG0uOurqGbuOn2', 1, 'Flamesky', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eorwiaJcRPxKMNHgov0HGBRA8JODQrhw67x61FGEFwic2E2UlhXSKmQ455jqT5RIPsZjmpkdia0pyZdA/132', 'oEMah4qx8utBwve1_5U2bq4z9Ucw', NULL, 1, 0, NULL, NULL);

-- ----------------------------
-- Table structure for ums_member_address
-- ----------------------------
DROP TABLE IF EXISTS `ums_member_address`;
CREATE TABLE `ums_member_address`  (
  `id` bigint(0) NOT NULL,
  `member_id` bigint(0) NOT NULL COMMENT '会员id',
  `consignee` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '收货人姓名',
  `mobile` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '联系方式',
  `province` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '省',
  `city` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '市',
  `area` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '区',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '详细地址',
  `postcode` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '邮编',
  `default` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否默认：1-是  0-否 ',
  `gmt_create` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '会员收货地址表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ums_member_coupon
-- ----------------------------
DROP TABLE IF EXISTS `ums_member_coupon`;
CREATE TABLE `ums_member_coupon`  (
  `id` bigint(0) NOT NULL,
  `member_id` bigint(0) NOT NULL COMMENT '会员id',
  `coupon_id` bigint(0) NOT NULL COMMENT '优惠券id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;

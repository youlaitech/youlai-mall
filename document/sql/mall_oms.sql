/*
 Navicat Premium Data Transfer

 Source Server         : www.youlai.store
 Source Server Type    : MySQL
 Source Server Version : 80023
 Source Host           : www.youlai.store:3306
 Source Schema         : mall_oms

 Target Server Type    : MySQL
 Target Server Version : 80023
 File Encoding         : 65001

 Date: 22/03/2021 09:21:59
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for oms_order
-- ----------------------------
DROP TABLE IF EXISTS `oms_order`;
CREATE TABLE `oms_order`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_sn` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '订单号',
  `total_amount` bigint(0) NOT NULL DEFAULT 0 COMMENT '订单总额（分）',
  `total_quantity` int(0) NOT NULL DEFAULT 0 COMMENT '商品总数',
  `source_type` tinyint(0) NOT NULL DEFAULT 1 COMMENT '订单来源[0->PC订单；1->APP订单]',
  `status` int(0) NOT NULL DEFAULT 101 COMMENT '订单状态：\r\n101->待付款；\r\n102->用户取消；\r\n103->系统取消；\r\n201->已付款；\r\n202->申请退款；\r\n203->已退款；\r\n301->待发货；\r\n401->已发货；\r\n501->用户收货；\r\n502->系统收货；\r\n901->已完成；',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '订单备注',
  `member_id` bigint(0) NOT NULL DEFAULT 0 COMMENT '会员id',
  `coupon_id` bigint(0) NOT NULL DEFAULT 0 COMMENT '使用的优惠券',
  `coupon_amount` bigint(0) NOT NULL DEFAULT 0 COMMENT '优惠券抵扣金额（分）',
  `freight_amount` bigint(0) NOT NULL DEFAULT 0 COMMENT '运费金额（分）',
  `pay_amount` bigint(0) NOT NULL DEFAULT 0 COMMENT '应付总额（分）',
  `pay_time` datetime(0) DEFAULT NULL COMMENT '支付时间',
  `pay_type` tinyint(0) DEFAULT NULL COMMENT '支付方式【1->支付宝；2->微信；3->银联； 4->货到付款；】',
  `delivery_time` datetime(0) DEFAULT NULL COMMENT '发货时间',
  `receive_time` datetime(0) DEFAULT NULL COMMENT '确认收货时间',
  `comment_time` datetime(0) DEFAULT NULL COMMENT '评价时间',
  `deleted` tinyint(1) DEFAULT 0 COMMENT '逻辑删除【0->正常；1->已删除】',
  `gmt_create` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `index_order_sn`(`order_sn`) USING BTREE COMMENT '订单号唯一索引'
) ENGINE = InnoDB AUTO_INCREMENT = 1351548262424821854 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单详情表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oms_order
-- ----------------------------
INSERT INTO `oms_order` VALUES (1351548262424821854, '20210321300000001', 159900, 1, 1, 201, '', 1, 0, 0, 0, 159900, '2021-03-21 18:41:39', 3, NULL, NULL, NULL, 0, '2021-03-21 18:41:32', '2021-03-21 18:41:39');
INSERT INTO `oms_order` VALUES (1351548262424821855, '20210321300000010', 639600, 4, 1, 103, '', 4, 0, 0, 0, 639600, NULL, NULL, NULL, NULL, NULL, 0, '2021-03-21 19:19:00', '2021-03-21 19:20:01');
INSERT INTO `oms_order` VALUES (1351548262424821856, '20210321300000022', 159900, 1, 1, 103, '', 1, 0, 0, 0, 159900, NULL, NULL, NULL, NULL, NULL, 0, '2021-03-21 19:30:32', '2021-03-21 19:31:33');
INSERT INTO `oms_order` VALUES (1351548262424821857, '20210321300000030', 639600, 4, 1, 103, '', 4, 0, 0, 0, 639600, NULL, NULL, NULL, NULL, NULL, 0, '2021-03-21 19:43:28', '2021-03-21 19:44:29');
INSERT INTO `oms_order` VALUES (1351548262424821858, '20210321300000031', 639600, 4, 1, 103, '', 4, 0, 0, 0, 639600, NULL, NULL, NULL, NULL, NULL, 0, '2021-03-21 19:45:33', '2021-03-21 19:46:33');
INSERT INTO `oms_order` VALUES (1351548262424821859, '20210321300000032', 639600, 4, 1, 103, '', 4, 0, 0, 0, 639600, NULL, NULL, NULL, NULL, NULL, 0, '2021-03-21 19:45:55', '2021-03-21 19:46:55');
INSERT INTO `oms_order` VALUES (1351548262424821860, '20210321300000033', 159900, 1, 1, 103, '', 1, 0, 0, 0, 159900, NULL, NULL, NULL, NULL, NULL, 0, '2021-03-21 19:55:24', '2021-03-21 19:56:25');
INSERT INTO `oms_order` VALUES (1351548262424821861, '20210321300000034', 319800, 2, 1, 103, '', 1, 0, 0, 0, 319800, NULL, NULL, NULL, NULL, NULL, 0, '2021-03-21 21:10:13', '2021-03-21 21:11:14');
INSERT INTO `oms_order` VALUES (1351548262424821862, '20210321300000035', 479700, 3, 1, 201, '', 1, 0, 0, 0, 479700, '2021-03-21 21:15:37', 3, NULL, NULL, NULL, 0, '2021-03-21 21:15:07', '2021-03-21 21:15:37');
INSERT INTO `oms_order` VALUES (1351548262424821863, '20210321300000036', 159900, 1, 1, 201, '', 1, 0, 0, 0, 159900, '2021-03-21 21:17:21', 3, NULL, NULL, NULL, 0, '2021-03-21 21:17:14', '2021-03-21 21:17:21');
INSERT INTO `oms_order` VALUES (1351548262424821864, '20210321300000037', 159900, 1, 1, 103, '', 1, 0, 0, 0, 159900, NULL, NULL, NULL, NULL, NULL, 0, '2021-03-21 21:17:38', '2021-03-21 21:18:38');
INSERT INTO `oms_order` VALUES (1351548262424821865, '20210322300000003', 319800, 2, 1, 201, '', 1, 0, 0, 0, 319800, '2021-03-22 00:40:37', 3, NULL, NULL, NULL, 0, '2021-03-22 00:40:32', '2021-03-22 00:40:37');
INSERT INTO `oms_order` VALUES (1351548262424821866, '20210322300000004', 159900, 1, 1, 103, '', 1, 0, 0, 0, 159900, NULL, NULL, NULL, NULL, NULL, 0, '2021-03-22 00:42:26', '2021-03-22 00:43:25');
INSERT INTO `oms_order` VALUES (1351548262424821867, '20210322300000005', 319800, 2, 1, 103, '', 1, 0, 0, 0, 319800, NULL, NULL, NULL, NULL, NULL, 0, '2021-03-22 00:45:12', '2021-03-22 00:46:13');
INSERT INTO `oms_order` VALUES (1351548262424821868, '20210322300000006', 499700, 3, 1, 201, '', 1, 0, 0, 0, 499700, '2021-03-22 00:47:15', 3, NULL, NULL, NULL, 0, '2021-03-22 00:47:12', '2021-03-22 00:47:15');
INSERT INTO `oms_order` VALUES (1351548262424821869, '20210322300000007', 159900, 1, 1, 201, '', 1, 0, 0, 0, 159900, '2021-03-22 00:47:54', 3, NULL, NULL, NULL, 0, '2021-03-22 00:47:51', '2021-03-22 00:47:54');
INSERT INTO `oms_order` VALUES (1351548262424821870, '20210322300000010', 159900, 1, 1, 201, '', 4, 0, 0, 0, 159900, '2021-03-22 07:13:01', 3, NULL, NULL, NULL, 0, '2021-03-22 07:12:58', '2021-03-22 07:13:01');
INSERT INTO `oms_order` VALUES (1351548262424821871, '20210322300000011', 479700, 3, 1, 201, '', 1, 0, 0, 0, 479700, '2021-03-22 07:17:45', 3, NULL, NULL, NULL, 0, '2021-03-22 07:17:40', '2021-03-22 07:17:45');
INSERT INTO `oms_order` VALUES (1351548262424821872, '20210322300000012', 159900, 1, 1, 201, '', 1, 0, 0, 0, 159900, '2021-03-22 08:19:23', 3, NULL, NULL, NULL, 0, '2021-03-22 08:19:19', '2021-03-22 08:19:23');

-- ----------------------------
-- Table structure for oms_order_delivery
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_delivery`;
CREATE TABLE `oms_order_delivery`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_id` bigint(0) NOT NULL COMMENT '订单id',
  `delivery_company` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '物流公司(配送方式)',
  `delivery_sn` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '物流单号',
  `receiver_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '收货人姓名',
  `receiver_phone` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '收货人电话',
  `receiver_post_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '收货人邮编',
  `receiver_province` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '省份/直辖市',
  `receiver_city` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '城市',
  `receiver_region` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '区',
  `receiver_detail_address` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '详细地址',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '备注',
  `delivery_status` tinyint(0) DEFAULT 0 COMMENT '物流状态【0->运输中；1->已收货】',
  `delivery_time` datetime(0) DEFAULT NULL COMMENT '发货时间',
  `receive_time` datetime(0) DEFAULT NULL COMMENT '确认收货时间',
  `deleted` tinyint(1) DEFAULT 0 COMMENT '逻辑删除【0->正常；1->已删除】',
  `gmt_create` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 44 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单物流记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oms_order_item
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_item`;
CREATE TABLE `oms_order_item`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_id` bigint(0) NOT NULL COMMENT 'order_id',
  `sku_id` bigint(0) NOT NULL DEFAULT 0 COMMENT '商品sku id',
  `sku_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '商品sku编号',
  `sku_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '商品sku名字',
  `sku_pic` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '商品sku图片',
  `sku_price` bigint(0) NOT NULL DEFAULT 0 COMMENT '商品sku价格(分)',
  `sku_quantity` int(0) NOT NULL DEFAULT 0 COMMENT '商品购买的数量',
  `sku_total_price` bigint(0) NOT NULL DEFAULT 0 COMMENT '商品sku总价格(分)',
  `spu_id` bigint(0) NOT NULL DEFAULT 0 COMMENT 'spu_id',
  `spu_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT 'spu_name',
  `spu_pic` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT 'spu_pic',
  `brand_id` bigint(0) NOT NULL DEFAULT 0 COMMENT '品牌id',
  `brand_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '品牌名称',
  `category_id` bigint(0) NOT NULL DEFAULT 0 COMMENT '商品分类id',
  `category_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '商品分类名称',
  `deleted` tinyint(1) DEFAULT 0 COMMENT '逻辑删除【0->正常；1->已删除】',
  `gmt_create` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_order_id`(`order_id`) USING BTREE COMMENT '订单id索引'
) ENGINE = InnoDB AUTO_INCREMENT = 99 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单商品信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oms_order_item
-- ----------------------------
INSERT INTO `oms_order_item` VALUES (95, 1351548262424821850, 190, '', '测试手机2 黑 4+64 ', 'http://a.youlai.store:9000/default/37729cd58065419f84b15f44b0e4f27f.jpg', 159900, 1, 0, 0, '', '', 0, '', 0, '', 0, '2021-03-21 13:40:52', '2021-03-21 13:40:52');
INSERT INTO `oms_order_item` VALUES (96, 1351548262424821851, 190, '', '测试手机2 黑 4+64 ', 'http://a.youlai.store:9000/default/37729cd58065419f84b15f44b0e4f27f.jpg', 159900, 1, 0, 0, '', '', 0, '', 0, '', 0, '2021-03-21 13:53:44', '2021-03-21 13:53:44');
INSERT INTO `oms_order_item` VALUES (97, 1351548262424821852, 190, '', '测试手机2 黑 4+64 ', 'http://a.youlai.store:9000/default/37729cd58065419f84b15f44b0e4f27f.jpg', 159900, 1, 0, 0, '', '', 0, '', 0, '', 0, '2021-03-21 14:03:43', '2021-03-21 14:03:43');
INSERT INTO `oms_order_item` VALUES (98, 1351548262424821853, 190, '', '测试手机2 黑 4+64 ', 'http://a.youlai.store:9000/default/37729cd58065419f84b15f44b0e4f27f.jpg', 159900, 1, 0, 0, '', '', 0, '', 0, '', 0, '2021-03-21 14:04:16', '2021-03-21 14:04:16');
INSERT INTO `oms_order_item` VALUES (99, 1351548262424821854, 190, '1614505936507', '测试手机2 黑 4+64 ', 'http://a.youlai.store:9000/default/37729cd58065419f84b15f44b0e4f27f.jpg', 159900, 1, 0, 0, '', '', 0, '', 0, '', 0, '2021-03-21 18:41:34', '2021-03-21 18:41:34');
INSERT INTO `oms_order_item` VALUES (100, 1351548262424821855, 190, '1614505936507', '测试手机2 黑 4+64 ', 'http://a.youlai.store:9000/default/37729cd58065419f84b15f44b0e4f27f.jpg', 159900, 4, 0, 0, '', '', 0, '', 0, '', 0, '2021-03-21 19:19:01', '2021-03-21 19:19:01');
INSERT INTO `oms_order_item` VALUES (101, 1351548262424821856, 190, '1614505936507', '测试手机2 黑 4+64 ', 'http://a.youlai.store:9000/default/37729cd58065419f84b15f44b0e4f27f.jpg', 159900, 1, 0, 0, '', '', 0, '', 0, '', 0, '2021-03-21 19:30:33', '2021-03-21 19:30:33');
INSERT INTO `oms_order_item` VALUES (102, 1351548262424821857, 190, '1614505936507', '测试手机2 黑 4+64 ', 'http://a.youlai.store:9000/default/37729cd58065419f84b15f44b0e4f27f.jpg', 159900, 4, 0, 0, '', '', 0, '', 0, '', 0, '2021-03-21 19:43:29', '2021-03-21 19:43:29');
INSERT INTO `oms_order_item` VALUES (103, 1351548262424821858, 190, '1614505936507', '测试手机2 黑 4+64 ', 'http://a.youlai.store:9000/default/37729cd58065419f84b15f44b0e4f27f.jpg', 159900, 4, 0, 0, '', '', 0, '', 0, '', 0, '2021-03-21 19:45:34', '2021-03-21 19:45:34');
INSERT INTO `oms_order_item` VALUES (104, 1351548262424821859, 190, '1614505936507', '测试手机2 黑 4+64 ', 'http://a.youlai.store:9000/default/37729cd58065419f84b15f44b0e4f27f.jpg', 159900, 4, 0, 0, '', '', 0, '', 0, '', 0, '2021-03-21 19:45:55', '2021-03-21 19:45:55');
INSERT INTO `oms_order_item` VALUES (105, 1351548262424821860, 190, '1614505936507', '测试手机2 黑 4+64 ', 'http://a.youlai.store:9000/default/37729cd58065419f84b15f44b0e4f27f.jpg', 159900, 1, 0, 0, '', '', 0, '', 0, '', 0, '2021-03-21 19:55:25', '2021-03-21 19:55:25');
INSERT INTO `oms_order_item` VALUES (106, 1351548262424821861, 190, '1614505936507', '测试手机2 黑 4+64 ', 'http://a.youlai.store:9000/default/37729cd58065419f84b15f44b0e4f27f.jpg', 159900, 2, 0, 0, '', '', 0, '', 0, '', 0, '2021-03-21 21:10:14', '2021-03-21 21:10:14');
INSERT INTO `oms_order_item` VALUES (107, 1351548262424821862, 190, '1614505936507', '测试手机2 黑 4+64 ', 'http://a.youlai.store:9000/default/37729cd58065419f84b15f44b0e4f27f.jpg', 159900, 3, 0, 0, '', '', 0, '', 0, '', 0, '2021-03-21 21:15:08', '2021-03-21 21:15:08');
INSERT INTO `oms_order_item` VALUES (108, 1351548262424821863, 190, '1614505936507', '测试手机2 黑 4+64 ', 'http://a.youlai.store:9000/default/37729cd58065419f84b15f44b0e4f27f.jpg', 159900, 1, 0, 0, '', '', 0, '', 0, '', 0, '2021-03-21 21:17:14', '2021-03-21 21:17:14');
INSERT INTO `oms_order_item` VALUES (109, 1351548262424821864, 190, '1614505936507', '测试手机2 黑 4+64 ', 'http://a.youlai.store:9000/default/37729cd58065419f84b15f44b0e4f27f.jpg', 159900, 1, 0, 0, '', '', 0, '', 0, '', 0, '2021-03-21 21:17:38', '2021-03-21 21:17:38');
INSERT INTO `oms_order_item` VALUES (110, 1351548262424821865, 190, '1614505936507', '测试手机2 黑 4+64 ', 'http://a.youlai.store:9000/default/37729cd58065419f84b15f44b0e4f27f.jpg', 159900, 2, 0, 0, '', '', 0, '', 0, '', 0, '2021-03-22 00:40:33', '2021-03-22 00:40:33');
INSERT INTO `oms_order_item` VALUES (111, 1351548262424821866, 190, '1614505936507', '测试手机2 黑 4+64 ', 'http://a.youlai.store:9000/default/37729cd58065419f84b15f44b0e4f27f.jpg', 159900, 1, 0, 0, '', '', 0, '', 0, '', 0, '2021-03-22 00:42:26', '2021-03-22 00:42:26');
INSERT INTO `oms_order_item` VALUES (112, 1351548262424821867, 190, '1614505936507', '测试手机2 黑 4+64 ', 'http://a.youlai.store:9000/default/37729cd58065419f84b15f44b0e4f27f.jpg', 159900, 2, 0, 0, '', '', 0, '', 0, '', 0, '2021-03-22 00:45:12', '2021-03-22 00:45:12');
INSERT INTO `oms_order_item` VALUES (113, 1351548262424821868, 190, '1614505936507', '测试手机2 黑 4+64 ', 'http://a.youlai.store:9000/default/37729cd58065419f84b15f44b0e4f27f.jpg', 159900, 2, 0, 0, '', '', 0, '', 0, '', 0, '2021-03-22 00:47:13', '2021-03-22 00:47:13');
INSERT INTO `oms_order_item` VALUES (114, 1351548262424821868, 191, '1614505936509', '测试手机2 黑 6+128 ', 'http://a.youlai.store:9000/default/37729cd58065419f84b15f44b0e4f27f.jpg', 179900, 1, 0, 0, '', '', 0, '', 0, '', 0, '2021-03-22 00:47:13', '2021-03-22 00:47:13');
INSERT INTO `oms_order_item` VALUES (115, 1351548262424821869, 190, '1614505936507', '测试手机2 黑 4+64 ', 'http://a.youlai.store:9000/default/37729cd58065419f84b15f44b0e4f27f.jpg', 159900, 1, 0, 0, '', '', 0, '', 0, '', 0, '2021-03-22 00:47:52', '2021-03-22 00:47:52');
INSERT INTO `oms_order_item` VALUES (116, 1351548262424821870, 190, '1614505936507', '测试手机2 黑 4+64 ', 'http://a.youlai.store:9000/default/37729cd58065419f84b15f44b0e4f27f.jpg', 159900, 1, 0, 0, '', '', 0, '', 0, '', 0, '2021-03-22 07:12:59', '2021-03-22 07:12:59');
INSERT INTO `oms_order_item` VALUES (117, 1351548262424821871, 190, '1614505936507', '测试手机2 黑 4+64 ', 'http://a.youlai.store:9000/default/37729cd58065419f84b15f44b0e4f27f.jpg', 159900, 3, 0, 0, '', '', 0, '', 0, '', 0, '2021-03-22 07:17:41', '2021-03-22 07:17:41');
INSERT INTO `oms_order_item` VALUES (118, 1351548262424821872, 190, '1614505936507', '测试手机2 黑 4+64 ', 'http://a.youlai.store:9000/default/37729cd58065419f84b15f44b0e4f27f.jpg', 159900, 1, 0, 0, '', '', 0, '', 0, '', 0, '2021-03-22 08:19:20', '2021-03-22 08:19:20');

-- ----------------------------
-- Table structure for oms_order_log
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_log`;
CREATE TABLE `oms_order_log`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_id` bigint(0) NOT NULL COMMENT '订单id',
  `user` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '操作人[用户；系统；后台管理员]',
  `detail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '操作详情',
  `order_status` int(0) DEFAULT NULL COMMENT '操作时订单状态',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '备注',
  `deleted` tinyint(1) DEFAULT 0 COMMENT '逻辑删除【0->正常；1->已删除】',
  `gmt_create` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 52 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单操作历史记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oms_order_pay
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_pay`;
CREATE TABLE `oms_order_pay`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_id` bigint(0) NOT NULL COMMENT '订单id',
  `pay_sn` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '支付流水号',
  `pay_amount` bigint(0) NOT NULL DEFAULT 0 COMMENT '应付总额(分)',
  `pay_time` datetime(0) DEFAULT NULL COMMENT '支付时间',
  `pay_type` tinyint(0) DEFAULT NULL COMMENT '支付方式【1->支付宝；2->微信；3->银联； 4->货到付款；】',
  `pay_status` tinyint(0) DEFAULT NULL COMMENT '支付状态',
  `confirm_time` datetime(0) DEFAULT NULL COMMENT '确认时间',
  `callback_content` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '回调内容',
  `callback_time` datetime(0) DEFAULT NULL COMMENT '回调时间',
  `pay_subject` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '交易内容',
  `deleted` tinyint(1) DEFAULT 0 COMMENT '逻辑删除【0->正常；1->已删除】',
  `gmt_create` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '支付信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oms_order_setting
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_setting`;
CREATE TABLE `oms_order_setting`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `flash_order_overtime` int(0) DEFAULT NULL COMMENT '秒杀订单超时关闭时间(分)',
  `normal_order_overtime` int(0) DEFAULT NULL COMMENT '正常订单超时时间(分)',
  `confirm_overtime` int(0) DEFAULT NULL COMMENT '发货后自动确认收货时间（天）',
  `finish_overtime` int(0) DEFAULT NULL COMMENT '自动完成交易时间，不能申请退货（天）',
  `comment_overtime` int(0) DEFAULT NULL COMMENT '订单完成后自动好评时间（天）',
  `member_level` tinyint(0) DEFAULT NULL COMMENT '会员等级【0-不限会员等级，全部通用；其他-对应的其他会员等级】',
  `deleted` tinyint(1) DEFAULT 0 COMMENT '逻辑删除【0->正常；1->已删除】',
  `gmt_create` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单配置信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for undo_log
-- ----------------------------
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log`  (
  `branch_id` bigint(0) NOT NULL COMMENT 'branch transaction id',
  `xid` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'global transaction id',
  `context` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'undo_log context,such as serialization',
  `rollback_info` longblob NOT NULL COMMENT 'rollback info',
  `log_status` int(0) NOT NULL COMMENT '0:normal status,1:defense status',
  `log_created` datetime(6) NOT NULL COMMENT 'create datetime',
  `log_modified` datetime(6) NOT NULL COMMENT 'modify datetime',
  UNIQUE INDEX `ux_undo_log`(`xid`, `branch_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'AT transaction mode undo table' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;

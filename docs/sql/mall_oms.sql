/*
 Navicat Premium Data Transfer

 Source Server         : www.youlai.tech
 Source Server Type    : MySQL
 Source Server Version : 80023
 Source Host           : www.youlai.tech:3306
 Source Schema         : mall_oms

 Target Server Type    : MySQL
 Target Server Version : 80023
 File Encoding         : 65001

 Date: 20/06/2021 13:15:25
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
  `pay_time` datetime(0) NULL DEFAULT NULL COMMENT '支付时间',
  `pay_type` tinyint(0) NULL DEFAULT NULL COMMENT '支付方式【1->支付宝；2->微信；3->银联； 4->货到付款；】',
  `delivery_time` datetime(0) NULL DEFAULT NULL COMMENT '发货时间',
  `receive_time` datetime(0) NULL DEFAULT NULL COMMENT '确认收货时间',
  `comment_time` datetime(0) NULL DEFAULT NULL COMMENT '评价时间',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除【0->正常；1->已删除】',
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `index_order_sn`(`order_sn`) USING BTREE COMMENT '订单号唯一索引'
) ENGINE = InnoDB AUTO_INCREMENT = 1351548262424821912 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单详情表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oms_order
-- ----------------------------
INSERT INTO `oms_order` VALUES (1351548262424821882, '20210322300000028', 159900, 1, 1, 201, '', 21, 0, 0, 0, 159900, '2021-03-22 23:34:49', 3, NULL, NULL, NULL, 0, '2021-03-22 23:34:43', '2021-03-22 23:34:50');
INSERT INTO `oms_order` VALUES (1351548262424821883, '20210323300000001', 159900, 1, 1, 201, '', 21, 0, 0, 0, 159900, '2021-03-23 01:03:55', 3, NULL, NULL, NULL, 0, '2021-03-23 01:03:43', '2021-03-23 01:03:55');
INSERT INTO `oms_order` VALUES (1351548262424821884, '20210323300000002', 319800, 2, 1, 201, '', 21, 0, 0, 0, 319800, '2021-03-23 01:10:24', 3, NULL, NULL, NULL, 0, '2021-03-23 01:10:15', '2021-03-23 01:10:24');
INSERT INTO `oms_order` VALUES (1351548262424821885, '20210323300000003', 159900, 1, 1, 103, '', 21, 0, 0, 0, 159900, NULL, NULL, NULL, NULL, NULL, 0, '2021-03-23 02:21:24', '2021-03-23 02:22:32');
INSERT INTO `oms_order` VALUES (1351548262424821886, '20210323300000004', 319800, 2, 1, 201, '', 21, 0, 0, 0, 319800, '2021-03-23 06:37:29', 3, NULL, NULL, NULL, 0, '2021-03-23 06:37:26', '2021-03-23 06:37:29');
INSERT INTO `oms_order` VALUES (1351548262424821887, '20210323300000005', 159900, 1, 1, 201, '', 21, 0, 0, 0, 159900, '2021-03-23 07:02:30', 3, NULL, NULL, NULL, 0, '2021-03-23 07:02:27', '2021-03-23 07:02:30');
INSERT INTO `oms_order` VALUES (1351548262424821888, '20210323300000006', 319800, 2, 1, 201, '', 21, 0, 0, 0, 319800, '2021-03-23 07:31:46', 3, NULL, NULL, NULL, 0, '2021-03-23 07:31:42', '2021-03-23 07:31:46');
INSERT INTO `oms_order` VALUES (1351548262424821889, '20210323300000009', 159900, 1, 1, 201, '', 22, 0, 0, 0, 159900, '2021-03-23 18:55:46', 3, NULL, NULL, NULL, 0, '2021-03-23 18:55:41', '2021-03-23 18:55:46');
INSERT INTO `oms_order` VALUES (1351548262424821890, '20210325300000001', 319800, 2, 1, 201, '', 21, 0, 0, 0, 319800, '2021-03-25 19:03:03', 3, NULL, NULL, NULL, 0, '2021-03-25 19:02:51', '2021-03-25 19:03:03');
INSERT INTO `oms_order` VALUES (1351548262424821891, '20210329300000001', 159900, 1, 1, 201, '', 25, 0, 0, 0, 159900, '2021-03-29 15:57:48', 3, NULL, NULL, NULL, 0, '2021-03-29 15:57:45', '2021-03-29 15:57:48');
INSERT INTO `oms_order` VALUES (1351548262424821892, '20210329300000002', 299900, 1, 1, 201, '', 25, 0, 0, 0, 299900, '2021-03-29 16:02:48', 3, NULL, NULL, NULL, 0, '2021-03-29 16:02:45', '2021-03-29 16:02:48');
INSERT INTO `oms_order` VALUES (1351548262424821893, '20210331300000001', 159900, 1, 1, 201, '', 25, 0, 0, 0, 159900, '2021-03-31 14:22:38', 3, NULL, NULL, NULL, 0, '2021-03-31 14:22:32', '2021-03-31 14:22:38');
INSERT INTO `oms_order` VALUES (1351548262424821894, '20210414300000001', 159900, 1, 1, 201, '', 27, 0, 0, 0, 159900, '2021-04-14 22:59:48', 3, NULL, NULL, NULL, 0, '2021-04-14 22:59:42', '2021-04-14 22:59:48');
INSERT INTO `oms_order` VALUES (1351548262424821895, '20210414300000003', 199900, 1, 1, 201, '', 27, 0, 0, 0, 199900, '2021-04-14 23:03:24', 3, NULL, NULL, NULL, 0, '2021-04-14 23:03:20', '2021-04-14 23:03:24');
INSERT INTO `oms_order` VALUES (1351548262424821896, '20210414300000005', 159900, 1, 1, 103, '', 31, 0, 0, 0, 159900, NULL, NULL, NULL, NULL, NULL, 0, '2021-04-14 23:33:47', '2021-04-14 23:34:48');
INSERT INTO `oms_order` VALUES (1351548262424821897, '20210414300000007', 319800, 2, 1, 103, '', 31, 0, 0, 0, 319800, NULL, NULL, NULL, NULL, NULL, 0, '2021-04-14 23:38:15', '2021-04-14 23:39:16');
INSERT INTO `oms_order` VALUES (1351548262424821898, '20210414300000008', 319800, 2, 1, 103, '', 31, 0, 0, 0, 319800, NULL, NULL, NULL, NULL, NULL, 0, '2021-04-14 23:38:31', '2021-04-14 23:39:31');
INSERT INTO `oms_order` VALUES (1351548262424821899, '20210414300000010', 319800, 2, 1, 103, '', 31, 0, 0, 0, 319800, NULL, NULL, NULL, NULL, NULL, 0, '2021-04-14 23:42:46', '2021-04-14 23:43:47');
INSERT INTO `oms_order` VALUES (1351548262424821900, '20210414300000012', 479700, 3, 1, 201, '', 31, 0, 0, 0, 479700, '2021-04-14 23:48:07', 3, NULL, NULL, NULL, 0, '2021-04-14 23:48:04', '2021-04-14 23:48:07');
INSERT INTO `oms_order` VALUES (1351548262424821901, '20210416300000001', 159900, 1, 1, 201, '', 31, 0, 0, 0, 159900, '2021-04-16 16:18:30', 3, NULL, NULL, NULL, 0, '2021-04-16 16:18:25', '2021-04-16 16:18:30');
INSERT INTO `oms_order` VALUES (1351548262424821904, '20210611300000001', 200, 1, 1, 103, '', 39, 0, 0, 0, 200, NULL, NULL, NULL, NULL, NULL, 0, '2021-06-11 00:20:24', '2021-06-11 00:21:35');
INSERT INTO `oms_order` VALUES (1351548262424821905, '20210611300000002', 200, 1, 1, 103, '', 39, 0, 0, 0, 200, NULL, NULL, NULL, NULL, NULL, 0, '2021-06-11 00:24:06', '2021-06-11 00:25:51');
INSERT INTO `oms_order` VALUES (1351548262424821906, '20210611300000003', 200, 1, 1, 201, '', 39, 0, 0, 0, 200, '2021-06-11 00:26:19', 3, NULL, NULL, NULL, 0, '2021-06-11 00:26:15', '2021-06-11 00:26:19');
INSERT INTO `oms_order` VALUES (1351548262424821907, '20210611300000004', 200, 1, 1, 201, '', 39, 0, 0, 0, 200, '2021-06-11 00:42:46', 3, NULL, NULL, NULL, 0, '2021-06-11 00:42:40', '2021-06-11 00:42:46');
INSERT INTO `oms_order` VALUES (1351548262424821908, '20210611300000005', 999900, 1, 1, 201, '', 39, 0, 0, 0, 999900, '2021-06-11 07:58:10', 3, NULL, NULL, NULL, 0, '2021-06-11 07:58:01', '2021-06-11 07:58:10');
INSERT INTO `oms_order` VALUES (1351548262424821909, '20210611300000007', 1999800, 2, 1, 201, '', 39, 0, 0, 0, 1999800, '2021-06-11 08:35:15', 3, NULL, NULL, NULL, 0, '2021-06-11 08:35:10', '2021-06-11 08:35:15');
INSERT INTO `oms_order` VALUES (1351548262424821910, '20210612300000001', 999900, 1, 1, 103, '', 39, 0, 0, 0, 999900, NULL, NULL, NULL, NULL, NULL, 0, '2021-06-12 14:16:12', '2021-06-12 14:17:13');
INSERT INTO `oms_order` VALUES (1351548262424821911, '20210617300000002', 1999800, 2, 1, 201, '', 39, 0, 0, 0, 1999800, '2021-06-17 08:48:52', 3, NULL, NULL, NULL, 0, '2021-06-17 08:48:43', '2021-06-17 08:48:52');

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
  `delivery_status` tinyint(0) NULL DEFAULT 0 COMMENT '物流状态【0->运输中；1->已收货】',
  `delivery_time` datetime(0) NULL DEFAULT NULL COMMENT '发货时间',
  `receive_time` datetime(0) NULL DEFAULT NULL COMMENT '确认收货时间',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除【0->正常；1->已删除】',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 42 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单物流记录表' ROW_FORMAT = Dynamic;

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
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除【0->正常；1->已删除】',
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_order_id`(`order_id`) USING BTREE COMMENT '订单id索引'
) ENGINE = InnoDB AUTO_INCREMENT = 159 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单商品信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oms_order_item
-- ----------------------------
INSERT INTO `oms_order_item` VALUES (128, 1351548262424821882, 190, '1614505936507', '测试手机2 黑 4+64 ', 'http://a.youlai.store:9000/default/37729cd58065419f84b15f44b0e4f27f.jpg', 159900, 1, 159900, 0, '', '', 0, '', 0, '', 0, '2021-03-22 23:34:46', '2021-03-22 23:34:46');
INSERT INTO `oms_order_item` VALUES (129, 1351548262424821883, 190, '1614505936507', '测试手机2 黑 4+64 ', 'http://a.youlai.store:9000/default/37729cd58065419f84b15f44b0e4f27f.jpg', 159900, 1, 159900, 0, '', '', 0, '', 0, '', 0, '2021-03-23 01:03:47', '2021-03-23 01:03:47');
INSERT INTO `oms_order_item` VALUES (130, 1351548262424821884, 190, '1614505936507', '测试手机2 黑 4+64 ', 'http://a.youlai.store:9000/default/37729cd58065419f84b15f44b0e4f27f.jpg', 159900, 2, 319800, 0, '', '', 0, '', 0, '', 0, '2021-03-23 01:10:17', '2021-03-23 01:10:17');
INSERT INTO `oms_order_item` VALUES (131, 1351548262424821885, 190, '1614505936507', '测试手机2 黑 4+64 ', 'http://a.youlai.store:9000/default/37729cd58065419f84b15f44b0e4f27f.jpg', 159900, 1, 159900, 0, '', '', 0, '', 0, '', 0, '2021-03-23 02:21:27', '2021-03-23 02:21:27');
INSERT INTO `oms_order_item` VALUES (132, 1351548262424821886, 190, '1614505936507', '测试手机2 黑 4+64 ', 'http://a.youlai.store:9000/default/37729cd58065419f84b15f44b0e4f27f.jpg', 159900, 2, 319800, 0, '', '', 0, '', 0, '', 0, '2021-03-23 06:37:27', '2021-03-23 06:37:27');
INSERT INTO `oms_order_item` VALUES (133, 1351548262424821887, 190, '1614505936507', '测试手机2 黑 4+64 ', 'http://a.youlai.store:9000/default/37729cd58065419f84b15f44b0e4f27f.jpg', 159900, 1, 159900, 0, '', '', 0, '', 0, '', 0, '2021-03-23 07:02:27', '2021-03-23 07:02:27');
INSERT INTO `oms_order_item` VALUES (134, 1351548262424821888, 190, '1614505936507', '测试手机2 黑 4+64 ', 'http://a.youlai.store:9000/default/37729cd58065419f84b15f44b0e4f27f.jpg', 159900, 2, 319800, 0, '', '', 0, '', 0, '', 0, '2021-03-23 07:31:43', '2021-03-23 07:31:43');
INSERT INTO `oms_order_item` VALUES (135, 1351548262424821889, 190, '1614505936507', '测试手机2 黑 4+64 ', 'http://a.youlai.store:9000/default/37729cd58065419f84b15f44b0e4f27f.jpg', 159900, 1, 159900, 0, '', '', 0, '', 0, '', 0, '2021-03-23 18:55:41', '2021-03-23 18:55:41');
INSERT INTO `oms_order_item` VALUES (136, 1351548262424821890, 190, '1614505936507', '测试手机2 黑 4+64 ', 'http://a.youlai.store:9000/default/37729cd58065419f84b15f44b0e4f27f.jpg', 159900, 2, 319800, 0, '', '', 0, '', 0, '', 0, '2021-03-25 19:02:51', '2021-03-25 19:02:51');
INSERT INTO `oms_order_item` VALUES (137, 1351548262424821891, 190, '1614505936507', '测试手机2 黑 4+64 ', 'http://a.youlai.store:9000/default/37729cd58065419f84b15f44b0e4f27f.jpg', 159900, 1, 159900, 0, '', '', 0, '', 0, '', 0, '2021-03-29 15:57:45', '2021-03-29 15:57:45');
INSERT INTO `oms_order_item` VALUES (138, 1351548262424821892, 186, '1614505517559', '测试手机1 黑 6+128 ', 'http://a.youlai.store:9000/default/9715dde8c35c429b8c56cbe800ebd205.jpg', 299900, 1, 299900, 0, '', '', 0, '', 0, '', 0, '2021-03-29 16:02:46', '2021-03-29 16:02:46');
INSERT INTO `oms_order_item` VALUES (139, 1351548262424821893, 190, '1614505936507', '测试手机2 黑 4+64 ', 'http://a.youlai.store:9000/default/37729cd58065419f84b15f44b0e4f27f.jpg', 159900, 1, 159900, 0, '', '', 0, '', 0, '', 0, '2021-03-31 14:22:33', '2021-03-31 14:22:33');
INSERT INTO `oms_order_item` VALUES (140, 1351548262424821894, 190, '1614505936507', '测试手机2 黑 4+64 ', 'http://a.youlai.store:9000/default/37729cd58065419f84b15f44b0e4f27f.jpg', 159900, 1, 159900, 0, '', '', 0, '', 0, '', 0, '2021-04-14 22:59:43', '2021-04-14 22:59:43');
INSERT INTO `oms_order_item` VALUES (141, 1351548262424821895, 192, '1614505936511', '测试手机2 黑 8+256 ', 'http://a.youlai.tech:9000/default/3e369c91377a4c9f9eb33f66a6dd6906.jpg', 199900, 1, 199900, 0, '', '', 0, '', 0, '', 0, '2021-04-14 23:03:21', '2021-04-14 23:03:21');
INSERT INTO `oms_order_item` VALUES (142, 1351548262424821896, 190, '1614505936507', '测试手机2 黑 4+64 ', 'http://a.youlai.tech:9000/default/c9f6f4f2e4de46f78d9c7a87d25948e1.jpg', 159900, 1, 159900, 0, '', '', 0, '', 0, '', 0, '2021-04-14 23:33:48', '2021-04-14 23:33:48');
INSERT INTO `oms_order_item` VALUES (143, 1351548262424821897, 190, '1614505936507', '测试手机2 黑 4+64 ', 'http://a.youlai.tech:9000/default/c9f6f4f2e4de46f78d9c7a87d25948e1.jpg', 159900, 2, 319800, 0, '', '', 0, '', 0, '', 0, '2021-04-14 23:38:15', '2021-04-14 23:38:15');
INSERT INTO `oms_order_item` VALUES (144, 1351548262424821898, 190, '1614505936507', '测试手机2 黑 4+64 ', 'http://a.youlai.tech:9000/default/c9f6f4f2e4de46f78d9c7a87d25948e1.jpg', 159900, 2, 319800, 0, '', '', 0, '', 0, '', 0, '2021-04-14 23:38:31', '2021-04-14 23:38:31');
INSERT INTO `oms_order_item` VALUES (145, 1351548262424821899, 190, '1614505936507', '测试手机2 黑 4+64 ', 'http://a.youlai.tech:9000/default/c9f6f4f2e4de46f78d9c7a87d25948e1.jpg', 159900, 2, 319800, 0, '', '', 0, '', 0, '', 0, '2021-04-14 23:42:47', '2021-04-14 23:42:47');
INSERT INTO `oms_order_item` VALUES (146, 1351548262424821900, 190, '1614505936507', '测试手机2 黑 4+64 ', 'http://a.youlai.tech:9000/default/c9f6f4f2e4de46f78d9c7a87d25948e1.jpg', 159900, 3, 479700, 0, '', '', 0, '', 0, '', 0, '2021-04-14 23:48:04', '2021-04-14 23:48:04');
INSERT INTO `oms_order_item` VALUES (147, 1351548262424821901, 190, '1614505936507', '测试手机2 黑 4+64 ', 'http://a.youlai.tech:9000/default/c9f6f4f2e4de46f78d9c7a87d25948e1.jpg', 159900, 1, 159900, 0, '', '', 0, '', 0, '', 0, '2021-04-16 16:18:27', '2021-04-16 16:18:27');
INSERT INTO `oms_order_item` VALUES (150, 1351548262424821904, 232, '1622008618852', '名称 红色 l ', '', 200, 1, 200, 0, '', '', 0, '', 0, '', 0, '2021-06-11 00:20:33', '2021-06-11 00:20:33');
INSERT INTO `oms_order_item` VALUES (151, 1351548262424821905, 232, '1622008618852', '名称 红色 l ', '', 200, 1, 200, 0, '', '', 0, '', 0, '', 0, '2021-06-11 00:24:07', '2021-06-11 00:24:07');
INSERT INTO `oms_order_item` VALUES (152, 1351548262424821906, 232, '1622008618852', '名称 红色 l ', '', 200, 1, 200, 0, '', '', 0, '', 0, '', 0, '2021-06-11 00:26:16', '2021-06-11 00:26:16');
INSERT INTO `oms_order_item` VALUES (153, 1351548262424821907, 232, '1622008618852', '名称 红色 l ', '', 200, 1, 200, 0, '', '', 0, '', 0, '', 0, '2021-06-11 00:42:43', '2021-06-11 00:42:43');
INSERT INTO `oms_order_item` VALUES (154, 1351548262424821908, 236, '1623369301480', '测试合约机 黑 6+64g ', 'http://a.youlai.tech:9000/default/c6e941103d8841acaf2f3f7356e11686.jpg', 999900, 1, 999900, 0, '', '', 0, '', 0, '', 0, '2021-06-11 07:58:04', '2021-06-11 07:58:04');
INSERT INTO `oms_order_item` VALUES (155, 1351548262424821909, 236, '1623369301480', '测试合约机 黑 6+64g ', 'http://a.youlai.tech:9000/default/c6e941103d8841acaf2f3f7356e11686.jpg', 999900, 1, 999900, 0, '', '', 0, '', 0, '', 0, '2021-06-11 08:35:11', '2021-06-11 08:35:11');
INSERT INTO `oms_order_item` VALUES (156, 1351548262424821909, 244, '1623369301480', '测试合约机 蓝 12+256g ', 'http://a.youlai.tech:9000/default/c6e941103d8841acaf2f3f7356e11686.jpg', 999900, 1, 999900, 0, '', '', 0, '', 0, '', 0, '2021-06-11 08:35:11', '2021-06-11 08:35:11');
INSERT INTO `oms_order_item` VALUES (157, 1351548262424821910, 236, '1623369301480', '测试合约机 黑 6+64g ', 'http://a.youlai.tech:9000/default/c6e941103d8841acaf2f3f7356e11686.jpg', 999900, 1, 999900, 0, '', '', 0, '', 0, '', 0, '2021-06-12 14:16:13', '2021-06-12 14:16:13');
INSERT INTO `oms_order_item` VALUES (158, 1351548262424821911, 236, '1623369301480', '测试合约机 黑 6+64g ', 'http://a.youlai.tech:9000/default/c6e941103d8841acaf2f3f7356e11686.jpg', 999900, 2, 1999800, 0, '', '', 0, '', 0, '', 0, '2021-06-17 08:48:45', '2021-06-17 08:48:45');

-- ----------------------------
-- Table structure for oms_order_log
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_log`;
CREATE TABLE `oms_order_log`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_id` bigint(0) NOT NULL COMMENT '订单id',
  `user` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '操作人[用户；系统；后台管理员]',
  `detail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '操作详情',
  `order_status` int(0) NULL DEFAULT NULL COMMENT '操作时订单状态',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '备注',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除【0->正常；1->已删除】',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 51 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单操作历史记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oms_order_pay
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_pay`;
CREATE TABLE `oms_order_pay`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_id` bigint(0) NOT NULL COMMENT '订单id',
  `pay_sn` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '支付流水号',
  `pay_amount` bigint(0) NOT NULL DEFAULT 0 COMMENT '应付总额(分)',
  `pay_time` datetime(0) NULL DEFAULT NULL COMMENT '支付时间',
  `pay_type` tinyint(0) NULL DEFAULT NULL COMMENT '支付方式【1->支付宝；2->微信；3->银联； 4->货到付款；】',
  `pay_status` tinyint(0) NULL DEFAULT NULL COMMENT '支付状态',
  `confirm_time` datetime(0) NULL DEFAULT NULL COMMENT '确认时间',
  `callback_content` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '回调内容',
  `callback_time` datetime(0) NULL DEFAULT NULL COMMENT '回调时间',
  `pay_subject` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '交易内容',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除【0->正常；1->已删除】',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '支付信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oms_order_setting
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_setting`;
CREATE TABLE `oms_order_setting`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `flash_order_overtime` int(0) NULL DEFAULT NULL COMMENT '秒杀订单超时关闭时间(分)',
  `normal_order_overtime` int(0) NULL DEFAULT NULL COMMENT '正常订单超时时间(分)',
  `confirm_overtime` int(0) NULL DEFAULT NULL COMMENT '发货后自动确认收货时间（天）',
  `finish_overtime` int(0) NULL DEFAULT NULL COMMENT '自动完成交易时间，不能申请退货（天）',
  `comment_overtime` int(0) NULL DEFAULT NULL COMMENT '订单完成后自动好评时间（天）',
  `member_level` tinyint(0) NULL DEFAULT NULL COMMENT '会员等级【0-不限会员等级，全部通用；其他-对应的其他会员等级】',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除【0->正常；1->已删除】',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
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

/*
 Navicat Premium Data Transfer

 Source Server         : root.youlai.tech
 Source Server Type    : MySQL
 Source Server Version : 80028
 Source Host           : www.youlai.tech:3306
 Source Schema         : mall_oms

 Target Server Type    : MySQL
 Target Server Version : 80028
 File Encoding         : 65001

 Date: 24/02/2022 23:53:16
*/

use mall_oms;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for oms_order
-- ----------------------------
DROP TABLE IF EXISTS `oms_order`;
CREATE TABLE `oms_order`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_sn` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '订单号',
  `total_amount` bigint NOT NULL DEFAULT 0 COMMENT '订单总额（分）',
  `total_quantity` int NOT NULL DEFAULT 0 COMMENT '商品总数',
  `source_type` tinyint NOT NULL DEFAULT 1 COMMENT '订单来源[0->PC订单；1->APP订单]',
  `status` int NOT NULL DEFAULT 101 COMMENT '订单状态：\r\n101->待付款；\r\n102->用户取消；\r\n103->系统取消；\r\n201->已付款；\r\n202->申请退款；\r\n203->已退款；\r\n301->待发货；\r\n401->已发货；\r\n501->用户收货；\r\n502->系统收货；\r\n901->已完成；',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '订单备注',
  `member_id` bigint NOT NULL DEFAULT 0 COMMENT '会员id',
  `coupon_id` bigint NOT NULL DEFAULT 0 COMMENT '使用的优惠券',
  `coupon_amount` bigint NOT NULL DEFAULT 0 COMMENT '优惠券抵扣金额（分）',
  `freight_amount` bigint NOT NULL DEFAULT 0 COMMENT '运费金额（分）',
  `pay_amount` bigint NOT NULL DEFAULT 0 COMMENT '应付总额（分）',
  `pay_time` datetime NULL DEFAULT NULL COMMENT '支付时间',
  `pay_type` tinyint NULL DEFAULT NULL COMMENT '支付方式【1->微信jsapi；2->支付宝；3->余额； 4->微信app；】',
  `out_trade_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '微信支付等第三方支付平台的商户订单号',
  `transaction_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '微信支付订单号',
  `out_refund_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商户退款单号',
  `refund_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '微信退款单号',
  `delivery_time` datetime NULL DEFAULT NULL COMMENT '发货时间',
  `receive_time` datetime NULL DEFAULT NULL COMMENT '确认收货时间',
  `comment_time` datetime NULL DEFAULT NULL COMMENT '评价时间',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除【0->正常；1->已删除】',
  `gmt_create` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `index_order_sn`(`order_sn`) USING BTREE COMMENT '订单号唯一索引',
  UNIQUE INDEX `index_otn`(`out_trade_no`) USING BTREE COMMENT '商户订单号唯一索引',
  UNIQUE INDEX `index_ti`(`transaction_id`) USING BTREE COMMENT '商户支付单号唯一索引',
  UNIQUE INDEX `index_orn`(`out_refund_no`) USING BTREE COMMENT '商户退款单号唯一索引',
  UNIQUE INDEX `index_ri`(`refund_id`) USING BTREE COMMENT '退款单号唯一索引'
) ENGINE = InnoDB AUTO_INCREMENT = 1351548262424821956 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单详情表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of oms_order
-- ----------------------------
INSERT INTO `oms_order` VALUES (1351548262424821940, '20210815300000009', 100, 1, 1, 201, '', 39, 0, 0, 0, 100, '2021-08-15 12:38:02', 3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, '2021-08-15 12:38:02', '2021-08-15 12:38:02');
INSERT INTO `oms_order` VALUES (1351548262424821941, '20210815300000010', 300, 1, 1, 201, '', 39, 0, 0, 0, 300, '2021-08-15 21:34:00', 3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, '2021-08-15 21:33:56', '2021-08-15 21:34:00');
INSERT INTO `oms_order` VALUES (1351548262424821942, '20210815300000011', 100, 1, 1, 201, '', 4, 0, 0, 0, 100, '2021-08-15 21:34:16', 3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, '2021-08-15 21:34:10', '2021-08-15 21:34:16');
INSERT INTO `oms_order` VALUES (1351548262424821943, '20210815300000012', 100, 1, 1, 201, '', 39, 0, 0, 0, 100, '2021-08-15 21:44:02', 3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, '2021-08-15 21:44:01', '2021-08-15 21:44:02');
INSERT INTO `oms_order` VALUES (1351548262424821944, '20210815300000013', 100, 1, 1, 201, '', 39, 0, 0, 0, 100, '2021-08-15 21:45:27', 3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, '2021-08-15 21:45:21', '2021-08-15 21:45:27');
INSERT INTO `oms_order` VALUES (1351548262424821945, '20210815300000014', 100, 1, 1, 201, '', 39, 0, 0, 0, 100, '2021-08-15 22:00:38', 3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, '2021-08-15 22:00:36', '2021-08-15 22:00:38');
INSERT INTO `oms_order` VALUES (1351548262424821948, '20210816300000002', 100, 1, 1, 201, '', 39, 0, 0, 0, 100, '2021-08-16 23:18:17', 3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, '2021-08-16 23:18:14', '2021-08-16 23:18:17');
INSERT INTO `oms_order` VALUES (1351548262424821949, '20210828300000009', 100, 1, 1, 201, '', 39, 0, 0, 0, 100, '2021-08-28 00:43:59', 3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, '2021-08-28 00:43:55', NULL);
INSERT INTO `oms_order` VALUES (1351548262424821950, '20220213300000040', 100, 1, 1, 201, '', 39, 0, 0, 0, 100, '2022-02-13 21:55:52', 3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, '2022-02-13 21:52:46', '2022-02-13 21:52:46');
INSERT INTO `oms_order` VALUES (1351548262424821951, '20220224300000001', 100, 1, 1, 201, '', 39, 0, 0, 0, 100, '2022-02-24 00:54:10', 3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, '2022-02-24 00:54:06', '2022-02-24 00:54:06');
INSERT INTO `oms_order` VALUES (1351548262424821952, '20220224300000002', 100, 1, 1, 201, '', 39, 0, 0, 0, 100, '2022-02-24 00:54:56', 3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, '2022-02-24 00:54:55', '2022-02-24 00:54:55');
INSERT INTO `oms_order` VALUES (1351548262424821953, '20220224300000003', 100, 1, 1, 201, '', 39, 0, 0, 0, 100, '2022-02-24 01:16:40', 3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, '2022-02-24 01:16:37', '2022-02-24 01:16:37');
INSERT INTO `oms_order` VALUES (1351548262424821954, '20220224300000004', 100, 1, 1, 201, '', 39, 0, 0, 0, 100, '2022-02-24 01:17:21', 3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, '2022-02-24 01:17:20', '2022-02-24 01:17:20');
INSERT INTO `oms_order` VALUES (1351548262424821955, '20220224300000005', 100, 1, 1, 201, '', 39, 0, 0, 0, 100, '2022-02-24 09:34:55', 3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, '2022-02-24 09:34:53', '2022-02-24 09:34:53');
INSERT INTO `oms_order` VALUES (1351548262424821956, '20220224300000008', 100, 1, 1, 103, '', 39, 0, 0, 0, 100, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, '2022-02-24 23:23:22', '2022-02-24 23:23:22');

-- ----------------------------
-- Table structure for oms_order_delivery
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_delivery`;
CREATE TABLE `oms_order_delivery`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_id` bigint NOT NULL COMMENT '订单id',
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
  `delivery_status` tinyint NULL DEFAULT 0 COMMENT '物流状态【0->运输中；1->已收货】',
  `delivery_time` datetime NULL DEFAULT NULL COMMENT '发货时间',
  `receive_time` datetime NULL DEFAULT NULL COMMENT '确认收货时间',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除【0->正常；1->已删除】',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单物流记录表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of oms_order_delivery
-- ----------------------------

-- ----------------------------
-- Table structure for oms_order_item
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_item`;
CREATE TABLE `oms_order_item`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_id` bigint NOT NULL COMMENT '订单ID',
  `spu_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品名称',
  `sku_id` bigint NOT NULL DEFAULT 0 COMMENT '商品ID',
  `sku_sn` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '商品编码',
  `sku_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '规格名称',
  `pic_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '商品图片',
  `price` bigint NOT NULL DEFAULT 0 COMMENT '商品单价(单位：分)',
  `count` int NOT NULL DEFAULT 0 COMMENT '商品数量',
  `total_amount` bigint NOT NULL DEFAULT 0 COMMENT '商品总价(单位：分)',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除标识(1:已删除；0:正常)',
  `gmt_create` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_order_id`(`order_id`) USING BTREE COMMENT '订单id索引'
) ENGINE = InnoDB AUTO_INCREMENT = 210 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单商品信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of oms_order_item
-- ----------------------------
INSERT INTO `oms_order_item` VALUES (128, 1351548262424821882, NULL, 190, '1614505936507', '测试手机2 黑 4+64 ', 'http://a.youlai.store:9000/default/37729cd58065419f84b15f44b0e4f27f.jpg', 159900, 1, 159900, 0, '2021-03-22 23:34:46', '2021-03-22 23:34:46');
INSERT INTO `oms_order_item` VALUES (129, 1351548262424821883, NULL, 190, '1614505936507', '测试手机2 黑 4+64 ', 'http://a.youlai.store:9000/default/37729cd58065419f84b15f44b0e4f27f.jpg', 159900, 1, 159900, 0, '2021-03-23 01:03:47', '2021-03-23 01:03:47');
INSERT INTO `oms_order_item` VALUES (130, 1351548262424821884, NULL, 190, '1614505936507', '测试手机2 黑 4+64 ', 'http://a.youlai.store:9000/default/37729cd58065419f84b15f44b0e4f27f.jpg', 159900, 2, 319800, 0, '2021-03-23 01:10:17', '2021-03-23 01:10:17');
INSERT INTO `oms_order_item` VALUES (131, 1351548262424821885, NULL, 190, '1614505936507', '测试手机2 黑 4+64 ', 'http://a.youlai.store:9000/default/37729cd58065419f84b15f44b0e4f27f.jpg', 159900, 1, 159900, 0, '2021-03-23 02:21:27', '2021-03-23 02:21:27');
INSERT INTO `oms_order_item` VALUES (132, 1351548262424821886, NULL, 190, '1614505936507', '测试手机2 黑 4+64 ', 'http://a.youlai.store:9000/default/37729cd58065419f84b15f44b0e4f27f.jpg', 159900, 2, 319800, 0, '2021-03-23 06:37:27', '2021-03-23 06:37:27');
INSERT INTO `oms_order_item` VALUES (133, 1351548262424821887, NULL, 190, '1614505936507', '测试手机2 黑 4+64 ', 'http://a.youlai.store:9000/default/37729cd58065419f84b15f44b0e4f27f.jpg', 159900, 1, 159900, 0, '2021-03-23 07:02:27', '2021-03-23 07:02:27');
INSERT INTO `oms_order_item` VALUES (134, 1351548262424821888, NULL, 190, '1614505936507', '测试手机2 黑 4+64 ', 'http://a.youlai.store:9000/default/37729cd58065419f84b15f44b0e4f27f.jpg', 159900, 2, 319800, 0, '2021-03-23 07:31:43', '2021-03-23 07:31:43');
INSERT INTO `oms_order_item` VALUES (135, 1351548262424821889, NULL, 190, '1614505936507', '测试手机2 黑 4+64 ', 'http://a.youlai.store:9000/default/37729cd58065419f84b15f44b0e4f27f.jpg', 159900, 1, 159900, 0, '2021-03-23 18:55:41', '2021-03-23 18:55:41');
INSERT INTO `oms_order_item` VALUES (136, 1351548262424821890, NULL, 190, '1614505936507', '测试手机2 黑 4+64 ', 'http://a.youlai.store:9000/default/37729cd58065419f84b15f44b0e4f27f.jpg', 159900, 2, 319800, 0, '2021-03-25 19:02:51', '2021-03-25 19:02:51');
INSERT INTO `oms_order_item` VALUES (137, 1351548262424821891, NULL, 190, '1614505936507', '测试手机2 黑 4+64 ', 'http://a.youlai.store:9000/default/37729cd58065419f84b15f44b0e4f27f.jpg', 159900, 1, 159900, 0, '2021-03-29 15:57:45', '2021-03-29 15:57:45');
INSERT INTO `oms_order_item` VALUES (138, 1351548262424821892, NULL, 186, '1614505517559', '测试手机1 黑 6+128 ', 'http://a.youlai.store:9000/default/9715dde8c35c429b8c56cbe800ebd205.jpg', 299900, 1, 299900, 0, '2021-03-29 16:02:46', '2021-03-29 16:02:46');
INSERT INTO `oms_order_item` VALUES (139, 1351548262424821893, NULL, 190, '1614505936507', '测试手机2 黑 4+64 ', 'http://a.youlai.store:9000/default/37729cd58065419f84b15f44b0e4f27f.jpg', 159900, 1, 159900, 0, '2021-03-31 14:22:33', '2021-03-31 14:22:33');
INSERT INTO `oms_order_item` VALUES (140, 1351548262424821894, NULL, 190, '1614505936507', '测试手机2 黑 4+64 ', 'http://a.youlai.store:9000/default/37729cd58065419f84b15f44b0e4f27f.jpg', 159900, 1, 159900, 0, '2021-04-14 22:59:43', '2021-04-14 22:59:43');
INSERT INTO `oms_order_item` VALUES (141, 1351548262424821895, NULL, 192, '1614505936511', '测试手机2 黑 8+256 ', 'http://a.youlai.tech:9000/default/3e369c91377a4c9f9eb33f66a6dd6906.jpg', 199900, 1, 199900, 0, '2021-04-14 23:03:21', '2021-04-14 23:03:21');
INSERT INTO `oms_order_item` VALUES (142, 1351548262424821896, NULL, 190, '1614505936507', '测试手机2 黑 4+64 ', 'http://a.youlai.tech:9000/default/c9f6f4f2e4de46f78d9c7a87d25948e1.jpg', 159900, 1, 159900, 0, '2021-04-14 23:33:48', '2021-04-14 23:33:48');
INSERT INTO `oms_order_item` VALUES (143, 1351548262424821897, NULL, 190, '1614505936507', '测试手机2 黑 4+64 ', 'http://a.youlai.tech:9000/default/c9f6f4f2e4de46f78d9c7a87d25948e1.jpg', 159900, 2, 319800, 0, '2021-04-14 23:38:15', '2021-04-14 23:38:15');
INSERT INTO `oms_order_item` VALUES (144, 1351548262424821898, NULL, 190, '1614505936507', '测试手机2 黑 4+64 ', 'http://a.youlai.tech:9000/default/c9f6f4f2e4de46f78d9c7a87d25948e1.jpg', 159900, 2, 319800, 0, '2021-04-14 23:38:31', '2021-04-14 23:38:31');
INSERT INTO `oms_order_item` VALUES (145, 1351548262424821899, NULL, 190, '1614505936507', '测试手机2 黑 4+64 ', 'http://a.youlai.tech:9000/default/c9f6f4f2e4de46f78d9c7a87d25948e1.jpg', 159900, 2, 319800, 0, '2021-04-14 23:42:47', '2021-04-14 23:42:47');
INSERT INTO `oms_order_item` VALUES (146, 1351548262424821900, NULL, 190, '1614505936507', '测试手机2 黑 4+64 ', 'http://a.youlai.tech:9000/default/c9f6f4f2e4de46f78d9c7a87d25948e1.jpg', 159900, 3, 479700, 0, '2021-04-14 23:48:04', '2021-04-14 23:48:04');
INSERT INTO `oms_order_item` VALUES (147, 1351548262424821901, NULL, 190, '1614505936507', '测试手机2 黑 4+64 ', 'http://a.youlai.tech:9000/default/c9f6f4f2e4de46f78d9c7a87d25948e1.jpg', 159900, 1, 159900, 0, '2021-04-16 16:18:27', '2021-04-16 16:18:27');
INSERT INTO `oms_order_item` VALUES (150, 1351548262424821904, NULL, 232, '1622008618852', '名称 红色 l ', '', 200, 1, 200, 0, '2021-06-11 00:20:33', '2021-06-11 00:20:33');
INSERT INTO `oms_order_item` VALUES (151, 1351548262424821905, NULL, 232, '1622008618852', '名称 红色 l ', '', 200, 1, 200, 0, '2021-06-11 00:24:07', '2021-06-11 00:24:07');
INSERT INTO `oms_order_item` VALUES (152, 1351548262424821906, NULL, 232, '1622008618852', '名称 红色 l ', '', 200, 1, 200, 0, '2021-06-11 00:26:16', '2021-06-11 00:26:16');
INSERT INTO `oms_order_item` VALUES (153, 1351548262424821907, NULL, 232, '1622008618852', '名称 红色 l ', '', 200, 1, 200, 0, '2021-06-11 00:42:43', '2021-06-11 00:42:43');
INSERT INTO `oms_order_item` VALUES (154, 1351548262424821908, NULL, 236, '1623369301480', '测试合约机 黑 6+64g ', 'http://a.youlai.tech:9000/default/c6e941103d8841acaf2f3f7356e11686.jpg', 999900, 1, 999900, 0, '2021-06-11 07:58:04', '2021-06-11 07:58:04');
INSERT INTO `oms_order_item` VALUES (155, 1351548262424821909, NULL, 236, '1623369301480', '测试合约机 黑 6+64g ', 'http://a.youlai.tech:9000/default/c6e941103d8841acaf2f3f7356e11686.jpg', 999900, 1, 999900, 0, '2021-06-11 08:35:11', '2021-06-11 08:35:11');
INSERT INTO `oms_order_item` VALUES (156, 1351548262424821909, NULL, 244, '1623369301480', '测试合约机 蓝 12+256g ', 'http://a.youlai.tech:9000/default/c6e941103d8841acaf2f3f7356e11686.jpg', 999900, 1, 999900, 0, '2021-06-11 08:35:11', '2021-06-11 08:35:11');
INSERT INTO `oms_order_item` VALUES (157, 1351548262424821910, NULL, 236, '1623369301480', '测试合约机 黑 6+64g ', 'http://a.youlai.tech:9000/default/c6e941103d8841acaf2f3f7356e11686.jpg', 999900, 1, 999900, 0, '2021-06-12 14:16:13', '2021-06-12 14:16:13');
INSERT INTO `oms_order_item` VALUES (158, 1351548262424821911, NULL, 236, '1623369301480', '测试合约机 黑 6+64g ', 'http://a.youlai.tech:9000/default/c6e941103d8841acaf2f3f7356e11686.jpg', 999900, 2, 1999800, 0, '2021-06-17 08:48:45', '2021-06-17 08:48:45');
INSERT INTO `oms_order_item` VALUES (163, 1351548262424821918, NULL, 236, '1623369301480', '测试合约机 黑 6+64g ', 'http://a.youlai.tech:9000/default/c6e941103d8841acaf2f3f7356e11686.jpg', 999900, 2, 1999800, 0, NULL, NULL);
INSERT INTO `oms_order_item` VALUES (164, 1351548262424821919, NULL, 236, '1623369301480', '测试合约机 黑 6+64g ', 'http://a.youlai.tech:9000/default/c6e941103d8841acaf2f3f7356e11686.jpg', 999900, 1, 999900, 0, NULL, NULL);
INSERT INTO `oms_order_item` VALUES (165, 1351548262424821920, NULL, 236, '1623369301480', '测试合约机 黑 6+64g ', 'http://a.youlai.tech:9000/default/c6e941103d8841acaf2f3f7356e11686.jpg', 999900, 2, 1999800, 0, NULL, NULL);
INSERT INTO `oms_order_item` VALUES (166, 1351548262424821921, NULL, 197, '1616310848932', '测试衣服1 白 M ', 'http://a.youlai.store:9000/default/063350d473a64ee7857e91841add1177.jpg', 29900, 1, 29900, 0, NULL, NULL);
INSERT INTO `oms_order_item` VALUES (167, 1351548262424821922, NULL, 197, '1616310848932', '测试衣服1 白 M ', 'http://a.youlai.store:9000/default/063350d473a64ee7857e91841add1177.jpg', 29900, 4, 119600, 0, NULL, NULL);
INSERT INTO `oms_order_item` VALUES (168, 1351548262424821923, NULL, 245, '1626080416010', 'huawei mate40pro 12 2 3 ', '', 609900, 1, 609900, 0, NULL, NULL);
INSERT INTO `oms_order_item` VALUES (169, 1351548262424821924, NULL, 245, '1626080416010', 'huawei mate40pro 12 2 3 ', '', 609900, 2, 1219800, 0, NULL, NULL);
INSERT INTO `oms_order_item` VALUES (170, 1351548262424821925, NULL, 245, '1626080416010', 'huawei mate40pro 12 2 3 ', '', 609900, 2, 1219800, 0, NULL, NULL);
INSERT INTO `oms_order_item` VALUES (171, 1351548262424821927, NULL, 300, '1', 'tid_1_1|tid_2_1|tid_3_', 'http://a.youlai.tech:9000/default/852f076dd41d4e199c969289838149fa.jpg', 100, 1, 100, 0, '2021-08-13 00:25:33', '2021-08-13 00:25:33');
INSERT INTO `oms_order_item` VALUES (172, 1351548262424821928, NULL, 300, '1', 'tid_1_1|tid_2_1|tid_3_', 'http://a.youlai.tech:9000/default/852f076dd41d4e199c969289838149fa.jpg', 100, 1, 100, 0, '2021-08-13 00:29:25', '2021-08-13 00:29:25');
INSERT INTO `oms_order_item` VALUES (173, 1351548262424821912, NULL, 300, '1', 'tid_1_1|tid_2_1|tid_3_', 'http://a.youlai.tech:9000/default/852f076dd41d4e199c969289838149fa.jpg', 100, 1, 100, 0, '2021-08-13 22:45:19', '2021-08-13 22:45:19');
INSERT INTO `oms_order_item` VALUES (174, 1351548262424821913, NULL, 300, '1', 'tid_1_1|tid_2_1|tid_3_', 'http://a.youlai.tech:9000/default/852f076dd41d4e199c969289838149fa.jpg', 100, 1, 100, 0, '2021-08-13 23:37:42', '2021-08-13 23:37:42');
INSERT INTO `oms_order_item` VALUES (175, 1351548262424821914, NULL, 300, '1', 'tid_1_1|tid_2_1|tid_3_', 'http://a.youlai.tech:9000/default/852f076dd41d4e199c969289838149fa.jpg', 100, 1, 100, 0, '2021-08-14 00:17:19', '2021-08-14 00:17:19');
INSERT INTO `oms_order_item` VALUES (176, 1351548262424821914, NULL, 291, '1', '', 'http://a.youlai.tech:9000/default/fb3f1be8aae644f497255f29aa51c641.jpg', 100, 1, 100, 0, '2021-08-14 00:17:19', '2021-08-14 00:17:19');
INSERT INTO `oms_order_item` VALUES (177, 1351548262424821915, NULL, 300, '1', 'tid_1_1|tid_2_1|tid_3_', 'http://a.youlai.tech:9000/default/852f076dd41d4e199c969289838149fa.jpg', 100, 1, 100, 0, '2021-08-14 01:24:26', '2021-08-14 01:24:26');
INSERT INTO `oms_order_item` VALUES (178, 1351548262424821916, NULL, 300, '1', 'tid_1_1|tid_2_1|tid_3_', 'http://a.youlai.tech:9000/default/852f076dd41d4e199c969289838149fa.jpg', 100, 1, 100, 0, '2021-08-14 17:24:13', '2021-08-14 17:24:13');
INSERT INTO `oms_order_item` VALUES (179, 1351548262424821917, NULL, 300, '1', 'tid_1_1|tid_2_1|tid_3_', 'http://a.youlai.tech:9000/default/852f076dd41d4e199c969289838149fa.jpg', 100, 1, 100, 0, '2021-08-14 17:30:19', '2021-08-14 17:30:19');
INSERT INTO `oms_order_item` VALUES (180, 1351548262424821918, NULL, 300, '1', 'tid_1_1|tid_2_1|tid_3_', 'http://a.youlai.tech:9000/default/852f076dd41d4e199c969289838149fa.jpg', 100, 1, 100, 0, '2021-08-14 18:58:28', '2021-08-14 18:58:28');
INSERT INTO `oms_order_item` VALUES (181, 1351548262424821919, NULL, 300, '1', 'tid_1_1|tid_2_1|tid_3_', 'http://a.youlai.tech:9000/default/852f076dd41d4e199c969289838149fa.jpg', 100, 2, 200, 0, '2021-08-14 19:01:46', '2021-08-14 19:01:46');
INSERT INTO `oms_order_item` VALUES (182, 1351548262424821921, NULL, 300, '1', 'tid_1_1|tid_2_1|tid_3_', 'http://a.youlai.tech:9000/default/852f076dd41d4e199c969289838149fa.jpg', 100, 1, 100, 0, '2021-08-14 21:41:42', '2021-08-14 21:41:42');
INSERT INTO `oms_order_item` VALUES (183, 1351548262424821922, NULL, 300, '1', 'tid_1_1|tid_2_1|tid_3_', 'http://a.youlai.tech:9000/default/852f076dd41d4e199c969289838149fa.jpg', 100, 1, 100, 0, '2021-08-14 22:32:39', '2021-08-14 22:32:39');
INSERT INTO `oms_order_item` VALUES (184, 1351548262424821923, NULL, 300, '1', 'tid_1_1|tid_2_1|tid_3_', 'http://a.youlai.tech:9000/default/852f076dd41d4e199c969289838149fa.jpg', 100, 1, 100, 0, '2021-08-14 22:35:14', '2021-08-14 22:35:14');
INSERT INTO `oms_order_item` VALUES (185, 1351548262424821924, NULL, 300, '1', 'tid_1_1|tid_2_1|tid_3_', 'http://a.youlai.tech:9000/default/852f076dd41d4e199c969289838149fa.jpg', 100, 1, 100, 0, '2021-08-14 22:38:04', '2021-08-14 22:38:04');
INSERT INTO `oms_order_item` VALUES (186, 1351548262424821925, NULL, 300, '1', 'tid_1_1|tid_2_1|tid_3_', 'http://a.youlai.tech:9000/default/852f076dd41d4e199c969289838149fa.jpg', 100, 1, 100, 0, '2021-08-14 23:14:42', '2021-08-14 23:14:42');
INSERT INTO `oms_order_item` VALUES (187, 1351548262424821926, NULL, 300, '1', 'tid_1_1|tid_2_1|tid_3_', 'http://a.youlai.tech:9000/default/852f076dd41d4e199c969289838149fa.jpg', 100, 1, 100, 0, '2021-08-15 00:42:26', '2021-08-15 00:42:26');
INSERT INTO `oms_order_item` VALUES (188, 1351548262424821927, NULL, 300, '1', 'tid_1_1|tid_2_1|tid_3_', 'http://a.youlai.tech:9000/default/852f076dd41d4e199c969289838149fa.jpg', 100, 1, 100, 0, '2021-08-15 00:46:45', '2021-08-15 00:46:45');
INSERT INTO `oms_order_item` VALUES (189, 1351548262424821928, NULL, 300, '1', 'tid_1_1|tid_2_1|tid_3_', 'http://a.youlai.tech:9000/default/852f076dd41d4e199c969289838149fa.jpg', 100, 1, 100, 0, '2021-08-15 00:50:57', '2021-08-15 00:50:57');
INSERT INTO `oms_order_item` VALUES (190, 1351548262424821929, NULL, 300, '1', 'tid_1_1|tid_2_1|tid_3_', 'http://a.youlai.tech:9000/default/852f076dd41d4e199c969289838149fa.jpg', 100, 1, 100, 0, '2021-08-15 00:54:41', '2021-08-15 00:54:41');
INSERT INTO `oms_order_item` VALUES (191, 1351548262424821930, NULL, 300, '1', 'tid_1_1|tid_2_1|tid_3_', 'http://a.youlai.tech:9000/default/852f076dd41d4e199c969289838149fa.jpg', 100, 1, 100, 0, '2021-08-15 01:00:50', '2021-08-15 01:00:50');
INSERT INTO `oms_order_item` VALUES (192, 1351548262424821931, NULL, 300, '1', 'tid_1_1|tid_2_1|tid_3_', 'http://a.youlai.tech:9000/default/852f076dd41d4e199c969289838149fa.jpg', 100, 1, 100, 0, '2021-08-15 09:24:03', '2021-08-15 09:24:03');
INSERT INTO `oms_order_item` VALUES (193, 1351548262424821932, NULL, 300, '1', 'tid_1_1|tid_2_1|tid_3_', 'http://a.youlai.tech:9000/default/852f076dd41d4e199c969289838149fa.jpg', 100, 1, 100, 0, '2021-08-15 09:24:14', '2021-08-15 09:24:14');
INSERT INTO `oms_order_item` VALUES (194, 1351548262424821933, NULL, 300, '1', 'tid_1_1|tid_2_1|tid_3_', 'http://a.youlai.tech:9000/default/852f076dd41d4e199c969289838149fa.jpg', 100, 1, 100, 0, '2021-08-15 12:23:37', '2021-08-15 12:23:37');
INSERT INTO `oms_order_item` VALUES (195, 1351548262424821940, NULL, 300, '1', 'tid_1_1|tid_2_1|tid_3_', 'http://a.youlai.tech:9000/default/852f076dd41d4e199c969289838149fa.jpg', 100, 1, 100, 0, '2021-08-15 12:38:02', '2021-08-15 12:38:02');
INSERT INTO `oms_order_item` VALUES (196, 1351548262424821941, NULL, 331, '4', '黑色 8+256G 套餐一 ', '', 300, 1, 300, 0, '2021-08-15 21:33:56', '2021-08-15 21:33:56');
INSERT INTO `oms_order_item` VALUES (197, 1351548262424821942, NULL, 308, '1', '123 1233 ', '', 100, 1, 100, 0, '2021-08-15 21:34:10', '2021-08-15 21:34:10');
INSERT INTO `oms_order_item` VALUES (198, 1351548262424821943, NULL, 300, '1', 'tid_1_1|tid_2_1|tid_3_', 'http://a.youlai.tech:9000/default/852f076dd41d4e199c969289838149fa.jpg', 100, 1, 100, 0, '2021-08-15 21:44:01', '2021-08-15 21:44:01');
INSERT INTO `oms_order_item` VALUES (199, 1351548262424821944, NULL, 300, '1', 'tid_1_1|tid_2_1|tid_3_', 'http://a.youlai.tech:9000/default/852f076dd41d4e199c969289838149fa.jpg', 100, 1, 100, 0, '2021-08-15 21:45:22', '2021-08-15 21:45:22');
INSERT INTO `oms_order_item` VALUES (200, 1351548262424821945, NULL, 328, '1', '黑色 6+128G 标准 ', '', 100, 1, 100, 0, '2021-08-15 22:00:37', '2021-08-15 22:00:37');
INSERT INTO `oms_order_item` VALUES (201, 1351548262424821946, NULL, 300, '1', 'tid_1_1|tid_2_1|tid_3_', 'http://a.youlai.tech:9000/default/852f076dd41d4e199c969289838149fa.jpg', 100, 1, 100, 0, '2021-08-16 17:05:17', '2021-08-16 17:05:17');
INSERT INTO `oms_order_item` VALUES (202, 1351548262424821948, NULL, 300, '1', 'tid_1_1|tid_2_1|tid_3_', 'http://a.youlai.tech:9000/default/852f076dd41d4e199c969289838149fa.jpg', 100, 1, 100, 0, '2021-08-16 23:18:16', '2021-08-16 23:18:16');
INSERT INTO `oms_order_item` VALUES (203, 1351548262424821949, NULL, 300, '1', 'tid_1_1|tid_2_1|tid_3_', 'http://a.youlai.tech:9000/default/852f076dd41d4e199c969289838149fa.jpg', 100, 1, 100, 0, NULL, NULL);
INSERT INTO `oms_order_item` VALUES (204, 1351548262424821950, NULL, 300, '1', 'tid_1_1|tid_2_1|tid_3_', 'http://a.youlai.tech:9000/default/852f076dd41d4e199c969289838149fa.jpg', 100, 1, 100, 0, '2022-02-13 21:52:46', '2022-02-13 21:52:46');
INSERT INTO `oms_order_item` VALUES (205, 1351548262424821951, NULL, 300, '1', 'tid_1_1|tid_2_1|tid_3_', 'http://a.youlai.tech:9000/default/852f076dd41d4e199c969289838149fa.jpg', 100, 1, 100, 0, '2022-02-24 00:54:07', '2022-02-24 00:54:07');
INSERT INTO `oms_order_item` VALUES (206, 1351548262424821952, NULL, 296, '1213', '117|11', '', 100, 1, 100, 0, '2022-02-24 00:54:55', '2022-02-24 00:54:55');
INSERT INTO `oms_order_item` VALUES (207, 1351548262424821953, NULL, 300, '1', 'tid_1_1|tid_2_1|tid_3_', 'http://a.youlai.tech:9000/default/852f076dd41d4e199c969289838149fa.jpg', 100, 1, 100, 0, '2022-02-24 01:16:38', '2022-02-24 01:16:38');
INSERT INTO `oms_order_item` VALUES (208, 1351548262424821954, NULL, 300, '1', 'tid_1_1|tid_2_1|tid_3_', 'http://a.youlai.tech:9000/default/852f076dd41d4e199c969289838149fa.jpg', 100, 1, 100, 0, '2022-02-24 01:17:20', '2022-02-24 01:17:20');
INSERT INTO `oms_order_item` VALUES (209, 1351548262424821955, NULL, 291, '1', '', 'http://a.youlai.tech:9000/default/fb3f1be8aae644f497255f29aa51c641.jpg', 100, 1, 100, 0, '2022-02-24 09:34:53', '2022-02-24 09:34:53');
INSERT INTO `oms_order_item` VALUES (210, 1351548262424821956, NULL, 300, '1', 'tid_1_1|tid_2_1|tid_3_', 'http://a.youlai.tech:9000/default/852f076dd41d4e199c969289838149fa.jpg', 100, 1, 100, 0, '2022-02-24 23:23:22', '2022-02-24 23:23:22');

-- ----------------------------
-- Table structure for oms_order_log
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_log`;
CREATE TABLE `oms_order_log`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_id` bigint NOT NULL COMMENT '订单id',
  `user` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '操作人[用户；系统；后台管理员]',
  `detail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '操作详情',
  `order_status` int NULL DEFAULT NULL COMMENT '操作时订单状态',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '备注',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除【0->正常；1->已删除】',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单操作历史记录' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of oms_order_log
-- ----------------------------

-- ----------------------------
-- Table structure for oms_order_pay
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_pay`;
CREATE TABLE `oms_order_pay`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_id` bigint NOT NULL COMMENT '订单id',
  `pay_sn` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '支付流水号',
  `pay_amount` bigint NOT NULL DEFAULT 0 COMMENT '应付总额(分)',
  `pay_time` datetime NULL DEFAULT NULL COMMENT '支付时间',
  `pay_type` tinyint NULL DEFAULT NULL COMMENT '支付方式【1->支付宝；2->微信；3->银联； 4->货到付款；】',
  `pay_status` tinyint NULL DEFAULT NULL COMMENT '支付状态',
  `confirm_time` datetime NULL DEFAULT NULL COMMENT '确认时间',
  `callback_content` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '回调内容',
  `callback_time` datetime NULL DEFAULT NULL COMMENT '回调时间',
  `pay_subject` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '交易内容',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除【0->正常；1->已删除】',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '支付信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of oms_order_pay
-- ----------------------------

-- ----------------------------
-- Table structure for oms_order_setting
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_setting`;
CREATE TABLE `oms_order_setting`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `flash_order_overtime` int NULL DEFAULT NULL COMMENT '秒杀订单超时关闭时间(分)',
  `normal_order_overtime` int NULL DEFAULT NULL COMMENT '正常订单超时时间(分)',
  `confirm_overtime` int NULL DEFAULT NULL COMMENT '发货后自动确认收货时间（天）',
  `finish_overtime` int NULL DEFAULT NULL COMMENT '自动完成交易时间，不能申请退货（天）',
  `comment_overtime` int NULL DEFAULT NULL COMMENT '订单完成后自动好评时间（天）',
  `member_level` tinyint NULL DEFAULT NULL COMMENT '会员等级【0-不限会员等级，全部通用；其他-对应的其他会员等级】',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除【0->正常；1->已删除】',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单配置信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of oms_order_setting
-- ----------------------------

-- ----------------------------
-- Table structure for undo_log
-- ----------------------------
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log`  (
  `branch_id` bigint NOT NULL COMMENT 'branch transaction id',
  `xid` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'global transaction id',
  `context` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'undo_log context,such as serialization',
  `rollback_info` longblob NOT NULL COMMENT 'rollback info',
  `log_status` int NOT NULL COMMENT '0:normal status,1:defense status',
  `log_created` datetime(6) NOT NULL COMMENT 'create datetime',
  `log_modified` datetime(6) NOT NULL COMMENT 'modify datetime',
  UNIQUE INDEX `ux_undo_log`(`xid`, `branch_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'AT transaction mode undo table' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of undo_log
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;

/*
 Navicat Premium Data Transfer

 Source Server         : www.youlai.store
 Source Server Type    : MySQL
 Source Server Version : 80022
 Source Host           : www.youlai.store:3306
 Source Schema         : mall-oms

 Target Server Type    : MySQL
 Target Server Version : 80022
 File Encoding         : 65001

 Date: 10/02/2021 22:03:34
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
  `source_type` tinyint(0) NOT NULL DEFAULT 1 COMMENT '订单来源[0->PC订单；1->app订单]',
  `status` int(0) NOT NULL DEFAULT 101 COMMENT '订单状态【101->待付款；102->用户取消；103->系统取消；201->已付款；202->申请退款；203->已退款；301->待发货；401->已发货；501->用户收货；502->系统收货；901->已完成】',
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
) ENGINE = InnoDB AUTO_INCREMENT = 1351548262424821785 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单详情表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oms_order
-- ----------------------------
INSERT INTO `oms_order` VALUES (1, '2021010910001', 0, 0, 1, 101, '', 0, 0, 0, 0, 0, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO `oms_order` VALUES (1351548262424821761, '202101192312560621351548199921303554', 300, 1, 1, 101, '有来商城第一条订单', 1, 0, 0, 0, 300, NULL, 2, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO `oms_order` VALUES (1351548262424821762, '202101192323309441351550862801010689', 300, 1, 1, 101, '有来商城第一条订单', 1, 0, 0, 0, 300, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO `oms_order` VALUES (1351548262424821763, '202101192323456531351550924469862401', 300, 1, 1, 101, '有来商城第一条订单', 1, 0, 0, 0, 300, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO `oms_order` VALUES (1351548262424821764, '202101192323480691351550934603300865', 300, 1, 1, 101, '有来商城第一条订单', 1, 0, 0, 0, 300, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO `oms_order` VALUES (1351548262424821765, '202101192323498091351550941901389825', 300, 1, 1, 101, '有来商城第一条订单', 1, 0, 0, 0, 300, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO `oms_order` VALUES (1351548262424821766, '202101212025036671352230728940675074', 800000, 2, 1, 101, '', 1, 0, 0, 0, 800000, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO `oms_order` VALUES (1351548262424821767, '202101212026421401352231141970542593', 800000, 2, 1, 101, '', 1, 0, 0, 0, 800000, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO `oms_order` VALUES (1351548262424821768, '202101212028296901352231593055354881', 400000, 1, 1, 101, '', 1, 0, 0, 0, 400000, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO `oms_order` VALUES (1351548262424821769, '202101212033378911352232885748236289', 800000, 2, 1, 101, '', 1, 0, 0, 0, 800000, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO `oms_order` VALUES (1351548262424821770, '202101212059323261352239405529579522', 400000, 1, 1, 101, '', 1, 0, 0, 0, 400000, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO `oms_order` VALUES (1351548262424821771, '202101212104241881352240629687549953', 400000, 1, 1, 101, '', 4, 0, 0, 0, 400000, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO `oms_order` VALUES (1351548262424821772, '202101212124375331352245718812753922', 400000, 1, 1, 101, '', 4, 0, 0, 0, 400000, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO `oms_order` VALUES (1351548262424821773, '202101212234109111352263223232987138', 400000, 1, 1, 101, '', 4, 0, 0, 0, 400000, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO `oms_order` VALUES (1351548262424821774, '202101212234225741352263272151154689', 400000, 1, 1, 101, '', 4, 0, 0, 0, 400000, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO `oms_order` VALUES (1351548262424821775, '202101212234227601352263272931295234', 400000, 1, 1, 101, '', 4, 0, 0, 0, 400000, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO `oms_order` VALUES (1351548262424821776, '202101212235290761352263551080759297', 400000, 1, 1, 101, '', 4, 0, 0, 0, 400000, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO `oms_order` VALUES (1351548262424821777, '202102042015269901357301740292063233', 200, 1, 1, 101, '', 4, 0, 0, 0, 200, NULL, NULL, NULL, NULL, NULL, 0, '2021-02-04 20:15:29', '2021-02-04 20:15:29');
INSERT INTO `oms_order` VALUES (1351548262424821778, '202102042207354881357329961670680578', 200, 1, 1, 101, '', 4, 0, 0, 0, 200, NULL, NULL, NULL, NULL, NULL, 0, '2021-02-04 22:07:36', '2021-02-04 22:07:36');
INSERT INTO `oms_order` VALUES (1351548262424821779, '202102042216593411357332326645772290', 200, 1, 1, 101, '', 4, 0, 0, 0, 200, NULL, NULL, NULL, NULL, NULL, 0, '2021-02-04 22:17:00', '2021-02-04 22:17:00');
INSERT INTO `oms_order` VALUES (1351548262424821780, '202102042222258911357333696291647489', 200, 1, 1, 101, '', 4, 0, 0, 0, 200, NULL, NULL, NULL, NULL, NULL, 0, '2021-02-04 22:22:26', '2021-02-04 22:22:26');
INSERT INTO `oms_order` VALUES (1351548262424821781, '202102042226104191357334638030262274', 200, 1, 1, 101, '', 4, 0, 0, 0, 200, NULL, NULL, NULL, NULL, NULL, 0, '2021-02-04 22:26:11', '2021-02-04 22:26:11');
INSERT INTO `oms_order` VALUES (1351548262424821782, '202102052100376851357675497649123330', 200, 1, 1, 101, '', 4, 0, 0, 0, 200, NULL, NULL, NULL, NULL, NULL, 0, '2021-02-05 21:00:38', '2021-02-05 21:00:38');
INSERT INTO `oms_order` VALUES (1351548262424821783, '202102052104097831357676387252604929', 200, 1, 1, 103, '', 4, 0, 0, 0, 200, NULL, NULL, NULL, NULL, NULL, 0, '2021-02-05 21:04:10', '2021-02-05 21:05:27');
INSERT INTO `oms_order` VALUES (1351548262424821784, '202102052106219701357676941672484865', 200, 1, 1, 103, '', 4, 0, 0, 0, 200, NULL, NULL, NULL, NULL, NULL, 0, '2021-02-05 21:06:22', '2021-02-05 21:07:26');

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
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单物流记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oms_order_delivery
-- ----------------------------
INSERT INTO `oms_order_delivery` VALUES (1, 1351548262424821761, '', '', '', '', '', '', '', '', '', '', 0, NULL, NULL, 0, NULL, NULL);
INSERT INTO `oms_order_delivery` VALUES (2, 1351548262424821762, '', '', '郝先瑞', '17621590365', '100000', '安徽省', '六安市', '金寨县', '白塔畈乡中心村院墙组', '', 0, NULL, NULL, 0, NULL, NULL);
INSERT INTO `oms_order_delivery` VALUES (3, 1351548262424821763, '', '', '郝先瑞', '17621590365', '100000', '安徽省', '六安市', '金寨县', '白塔畈乡中心村院墙组', '', 0, NULL, NULL, 0, NULL, NULL);
INSERT INTO `oms_order_delivery` VALUES (4, 1351548262424821764, '', '', '郝先瑞', '17621590365', '100000', '安徽省', '六安市', '金寨县', '白塔畈乡中心村院墙组', '', 0, NULL, NULL, 0, NULL, NULL);
INSERT INTO `oms_order_delivery` VALUES (5, 1351548262424821765, '', '', '郝先瑞', '17621590365', '100000', '安徽省', '六安市', '金寨县', '白塔畈乡中心村院墙组', '', 0, NULL, NULL, 0, NULL, NULL);
INSERT INTO `oms_order_delivery` VALUES (6, 1351548262424821766, '', '', '花韦', '13151572876', '', '上海', '南京市', '雨花台区', '1号', '', 0, NULL, NULL, 0, NULL, NULL);
INSERT INTO `oms_order_delivery` VALUES (7, 1351548262424821767, '', '', '花韦', '13151572876', '', '上海', '南京市', '雨花台区', '1号', '', 0, NULL, NULL, 0, NULL, NULL);
INSERT INTO `oms_order_delivery` VALUES (8, 1351548262424821768, '', '', '花韦', '13151572876', '', '上海', '南京市', '雨花台区', '1号', '', 0, NULL, NULL, 0, NULL, NULL);
INSERT INTO `oms_order_delivery` VALUES (9, 1351548262424821769, '', '', '花韦', '13151572876', '', '上海', '南京市', '雨花台区', '1号', '', 0, NULL, NULL, 0, NULL, NULL);
INSERT INTO `oms_order_delivery` VALUES (10, 1351548262424821770, '', '', '花韦', '13151572876', '', '上海', '南京市', '雨花台区', '1号', '', 0, NULL, NULL, 0, NULL, NULL);
INSERT INTO `oms_order_delivery` VALUES (11, 1351548262424821771, '', '', '花韦', '13151572876', '', '上海', '南京市', '雨花台区', '1号', '', 0, NULL, NULL, 0, NULL, NULL);
INSERT INTO `oms_order_delivery` VALUES (12, 1351548262424821772, '', '', '花韦', '13151572876', '', '上海', '南京市', '雨花台区', '1号', '', 0, NULL, NULL, 0, NULL, NULL);
INSERT INTO `oms_order_delivery` VALUES (13, 1351548262424821773, '', '', '花韦', '13151572876', '', '上海', '南京市', '雨花台区', '1号', '', 0, NULL, NULL, 0, NULL, NULL);
INSERT INTO `oms_order_delivery` VALUES (14, 1351548262424821774, '', '', '花韦', '13151572876', '', '上海', '南京市', '雨花台区', '1号', '', 0, NULL, NULL, 0, NULL, NULL);
INSERT INTO `oms_order_delivery` VALUES (15, 1351548262424821775, '', '', '花韦', '13151572876', '', '上海', '南京市', '雨花台区', '1号', '', 0, NULL, NULL, 0, NULL, NULL);
INSERT INTO `oms_order_delivery` VALUES (16, 1351548262424821776, '', '', '花韦', '13151572876', '', '上海', '南京市', '雨花台区', '1号', '', 0, NULL, NULL, 0, NULL, NULL);
INSERT INTO `oms_order_delivery` VALUES (17, 1351548262424821777, '', '', '花韦', '13151572876', '', '上海', '南京市', '雨花台区', '1号', '', 0, NULL, NULL, 0, '2021-02-04 20:15:31', '2021-02-04 20:15:31');
INSERT INTO `oms_order_delivery` VALUES (18, 1351548262424821778, '', '', '花韦', '13151572876', '', '上海', '南京市', '雨花台区', '1号', '', 0, NULL, NULL, 0, '2021-02-04 22:07:38', '2021-02-04 22:07:38');
INSERT INTO `oms_order_delivery` VALUES (19, 1351548262424821779, '', '', '花韦', '13151572876', '', '上海', '南京市', '雨花台区', '1号', '', 0, NULL, NULL, 0, '2021-02-04 22:17:01', '2021-02-04 22:17:01');
INSERT INTO `oms_order_delivery` VALUES (20, 1351548262424821780, '', '', '花韦', '13151572876', '', '上海', '南京市', '雨花台区', '1号', '', 0, NULL, NULL, 0, '2021-02-04 22:22:27', '2021-02-04 22:22:27');
INSERT INTO `oms_order_delivery` VALUES (21, 1351548262424821781, '', '', '花韦', '13151572876', '', '上海', '南京市', '雨花台区', '1号', '', 0, NULL, NULL, 0, '2021-02-04 22:26:12', '2021-02-04 22:26:12');
INSERT INTO `oms_order_delivery` VALUES (22, 1351548262424821782, '', '', '花韦', '13151572876', '', '上海', '南京市', '雨花台区', '1号', '', 0, NULL, NULL, 0, '2021-02-05 21:00:41', '2021-02-05 21:00:41');
INSERT INTO `oms_order_delivery` VALUES (23, 1351548262424821783, '', '', '花韦', '13151572876', '', '上海', '南京市', '雨花台区', '1号', '', 0, NULL, NULL, 0, '2021-02-05 21:04:11', '2021-02-05 21:04:11');
INSERT INTO `oms_order_delivery` VALUES (24, 1351548262424821784, '', '', '花韦', '13151572876', '', '上海', '南京市', '雨花台区', '1号', '', 0, NULL, NULL, 0, '2021-02-05 21:06:23', '2021-02-05 21:06:23');

-- ----------------------------
-- Table structure for oms_order_goods
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_goods`;
CREATE TABLE `oms_order_goods`  (
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
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单商品信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oms_order_goods
-- ----------------------------
INSERT INTO `oms_order_goods` VALUES (1, 1351548262424821761, 1, '1610678796037', '测试 2 2 ', 'http://101.37.69.49:9000/default/d23c52a0a4af44b98efb8e5cd71bd55d.jpg', 300, 1, 300, 49, '测试', 'http://101.37.69.49:9000/default/d23c52a0a4af44b98efb8e5cd71bd55d.jpg', 3, '有来', 26, '全面屏手机', 0, NULL, NULL);
INSERT INTO `oms_order_goods` VALUES (2, 1351548262424821762, 1, '1610678796037', '测试 2 2 ', 'http://101.37.69.49:9000/default/d23c52a0a4af44b98efb8e5cd71bd55d.jpg', 300, 1, 300, 49, '测试', 'http://101.37.69.49:9000/default/d23c52a0a4af44b98efb8e5cd71bd55d.jpg', 3, '有来', 26, '全面屏手机', 0, NULL, NULL);
INSERT INTO `oms_order_goods` VALUES (3, 1351548262424821763, 1, '1610678796037', '测试 2 2 ', 'http://101.37.69.49:9000/default/d23c52a0a4af44b98efb8e5cd71bd55d.jpg', 300, 1, 300, 49, '测试', 'http://101.37.69.49:9000/default/d23c52a0a4af44b98efb8e5cd71bd55d.jpg', 3, '有来', 26, '全面屏手机', 0, NULL, NULL);
INSERT INTO `oms_order_goods` VALUES (4, 1351548262424821764, 1, '1610678796037', '测试 2 2 ', 'http://101.37.69.49:9000/default/d23c52a0a4af44b98efb8e5cd71bd55d.jpg', 300, 1, 300, 49, '测试', 'http://101.37.69.49:9000/default/d23c52a0a4af44b98efb8e5cd71bd55d.jpg', 3, '有来', 26, '全面屏手机', 0, NULL, NULL);
INSERT INTO `oms_order_goods` VALUES (5, 1351548262424821765, 1, '1610678796037', '测试 2 2 ', 'http://101.37.69.49:9000/default/d23c52a0a4af44b98efb8e5cd71bd55d.jpg', 300, 1, 300, 49, '测试', 'http://101.37.69.49:9000/default/d23c52a0a4af44b98efb8e5cd71bd55d.jpg', 3, '有来', 26, '全面屏手机', 0, NULL, NULL);
INSERT INTO `oms_order_goods` VALUES (6, 1351548262424821766, 1, '1611107652289', '小米 高端 上档次 ', '', 400000, 2, 800000, 51, '小米', '', 0, '', 26, '全面屏手机', 0, NULL, NULL);
INSERT INTO `oms_order_goods` VALUES (7, 1351548262424821767, 1, '1611107652289', '小米 高端 上档次 ', '', 400000, 2, 800000, 51, '小米', '', 0, '', 26, '全面屏手机', 0, NULL, NULL);
INSERT INTO `oms_order_goods` VALUES (8, 1351548262424821768, 1, '1611107652289', '小米 高端 上档次 ', '', 400000, 1, 400000, 51, '小米', '', 0, '', 26, '全面屏手机', 0, NULL, NULL);
INSERT INTO `oms_order_goods` VALUES (9, 1351548262424821769, 1, '1611107652289', '小米 高端 上档次 ', '', 400000, 2, 800000, 51, '小米', '', 0, '', 26, '全面屏手机', 0, NULL, NULL);
INSERT INTO `oms_order_goods` VALUES (10, 1351548262424821770, 1, '1611107652289', '小米 高端 上档次 ', '', 400000, 1, 400000, 51, '小米', '', 0, '', 26, '全面屏手机', 0, NULL, NULL);
INSERT INTO `oms_order_goods` VALUES (11, 1351548262424821771, 1, '1611107652289', '小米 高端 上档次 ', '', 400000, 1, 400000, 51, '小米', '', 0, '', 26, '全面屏手机', 0, NULL, NULL);
INSERT INTO `oms_order_goods` VALUES (12, 1351548262424821772, 1, '1611107652289', '小米 高端 上档次 ', '', 400000, 1, 400000, 51, '小米', '', 0, '', 26, '全面屏手机', 0, NULL, NULL);
INSERT INTO `oms_order_goods` VALUES (13, 1351548262424821773, 1, '1611107652289', '小米 高端 上档次 ', '', 400000, 1, 400000, 51, '小米', '', 0, '', 26, '全面屏手机', 0, NULL, NULL);
INSERT INTO `oms_order_goods` VALUES (14, 1351548262424821774, 1, '1611107652289', '小米 高端 上档次 ', '', 400000, 1, 400000, 51, '小米', '', 0, '', 26, '全面屏手机', 0, NULL, NULL);
INSERT INTO `oms_order_goods` VALUES (15, 1351548262424821775, 1, '1611107652289', '小米 高端 上档次 ', '', 400000, 1, 400000, 51, '小米', '', 0, '', 26, '全面屏手机', 0, NULL, NULL);
INSERT INTO `oms_order_goods` VALUES (16, 1351548262424821776, 1, '1611107652289', '小米 高端 上档次 ', '', 400000, 1, 400000, 51, '小米', '', 0, '', 26, '全面屏手机', 0, NULL, NULL);
INSERT INTO `oms_order_goods` VALUES (17, 1351548262424821777, 1, '1611500180237', '222 2 3 ', '', 200, 1, 200, 52, '222', '', 0, '', 26, '全面屏手机', 0, '2021-02-04 20:15:31', '2021-02-04 20:15:31');
INSERT INTO `oms_order_goods` VALUES (18, 1351548262424821778, 1, '1611500180237', '222 2 3 ', '', 200, 1, 200, 52, '222', '', 0, '', 26, '全面屏手机', 0, '2021-02-04 22:07:38', '2021-02-04 22:07:38');
INSERT INTO `oms_order_goods` VALUES (19, 1351548262424821779, 1, '1611500180237', '222 2 3 ', '', 200, 1, 200, 52, '222', '', 0, '', 26, '全面屏手机', 0, '2021-02-04 22:17:00', '2021-02-04 22:17:00');
INSERT INTO `oms_order_goods` VALUES (20, 1351548262424821780, 1, '1611500180237', '222 2 3 ', '', 200, 1, 200, 52, '222', '', 0, '', 26, '全面屏手机', 0, '2021-02-04 22:22:27', '2021-02-04 22:22:27');
INSERT INTO `oms_order_goods` VALUES (21, 1351548262424821781, 1, '1611500180237', '222 2 3 ', '', 200, 1, 200, 52, '222', '', 0, '', 26, '全面屏手机', 0, '2021-02-04 22:26:11', '2021-02-04 22:26:11');
INSERT INTO `oms_order_goods` VALUES (22, 1351548262424821782, 1, '1611500180237', '222 2 3 ', '', 200, 1, 200, 52, '222', '', 0, '', 26, '全面屏手机', 0, '2021-02-05 21:00:41', '2021-02-05 21:00:41');
INSERT INTO `oms_order_goods` VALUES (23, 1351548262424821783, 1, '1611500180237', '222 2 3 ', '', 200, 1, 200, 52, '222', '', 0, '', 26, '全面屏手机', 0, '2021-02-05 21:04:11', '2021-02-05 21:04:11');
INSERT INTO `oms_order_goods` VALUES (24, 1351548262424821784, 1, '1611500180237', '222 2 3 ', '', 200, 1, 200, 52, '222', '', 0, '', 26, '全面屏手机', 0, '2021-02-05 21:06:22', '2021-02-05 21:06:22');

-- ----------------------------
-- Table structure for oms_order_logs
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_logs`;
CREATE TABLE `oms_order_logs`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_id` bigint(0) NOT NULL COMMENT '订单id',
  `user` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '操作人[用户；系统；后台管理员]',
  `detail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '操作详情',
  `order_status` tinyint(0) NULL DEFAULT NULL COMMENT '操作时订单状态',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '备注',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除【0->正常；1->已删除】',
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单操作历史记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oms_order_logs
-- ----------------------------
INSERT INTO `oms_order_logs` VALUES (1, 1351548262424821779, 'youlai-mall-weapp', '创建订单', 101, '', 0, '2021-02-04 22:17:01', '2021-02-04 22:17:01');
INSERT INTO `oms_order_logs` VALUES (2, 1351548262424821780, 'youlai-mall-weapp', '创建订单', 101, '', 0, '2021-02-04 22:22:28', '2021-02-04 22:22:28');
INSERT INTO `oms_order_logs` VALUES (3, 1351548262424821781, 'youlai-mall-weapp', '创建订单', 101, '', 0, '2021-02-04 22:26:12', '2021-02-04 22:26:12');
INSERT INTO `oms_order_logs` VALUES (4, 1351548262424821782, 'youlai-mall-weapp', '创建订单', 101, '', 0, '2021-02-05 21:00:42', '2021-02-05 21:00:42');
INSERT INTO `oms_order_logs` VALUES (5, 1351548262424821783, 'youlai-mall-weapp', '创建订单', 101, '', 0, '2021-02-05 21:04:12', '2021-02-05 21:04:12');
INSERT INTO `oms_order_logs` VALUES (6, 1351548262424821783, '系统操作', '系统自动取消', 103, '', 0, '2021-02-05 21:05:28', '2021-02-05 21:05:28');
INSERT INTO `oms_order_logs` VALUES (7, 1351548262424821784, 'youlai-mall-weapp', '创建订单', 101, '', 0, '2021-02-05 21:06:59', '2021-02-05 21:06:59');
INSERT INTO `oms_order_logs` VALUES (8, 1351548262424821784, '系统操作', '系统自动取消', 103, '', 0, '2021-02-05 21:07:30', '2021-02-05 21:07:30');

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
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '支付信息表' ROW_FORMAT = Dynamic;

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
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单配置信息' ROW_FORMAT = Dynamic;

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

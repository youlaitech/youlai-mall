/*
 Navicat Premium Data Transfer

 Source Server         : 101.37.69.49
 Source Server Type    : MySQL
 Source Server Version : 80019
 Source Host           : 101.37.69.49:3306
 Source Schema         : youlai-mall

 Target Server Type    : MySQL
 Target Server Version : 80019
 File Encoding         : 65001

 Date: 23/11/2020 09:18:57
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
  `status` tinyint(0) NOT NULL COMMENT '订单状态',
  `source_type` tinyint(0) NOT NULL COMMENT '订单来源：1-微信小程序',
  `consignee` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '收货人',
  `mobile` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '收货人联系方式',
  `postcode` char(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮编',
  `address` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收货地址',
  `coupon_id` bigint(0) NULL DEFAULT NULL COMMENT '优惠券id',
  `sku_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '商品费用',
  `freight_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '运费',
  `coupon_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '优惠券抵扣费用',
  `order_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '订单费用',
  `integration_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '积分抵扣费用',
  `pay_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '支付费用',
  `pay_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '付款编号',
  `pay_type` tinyint(0) NULL DEFAULT NULL COMMENT '支付方式',
  `pay_time` datetime(0) NULL DEFAULT NULL COMMENT '支付时间',
  `ship_sn` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '快递单号',
  `ship_company` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '快递公司',
  `ship_time` datetime(0) NULL DEFAULT NULL COMMENT '发货时间',
  `refund_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '退款金额',
  `refund_type` tinyint(0) NULL DEFAULT NULL COMMENT '退款方式',
  `refund_time` datetime(0) NULL DEFAULT NULL COMMENT '退款时间',
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT NULL,
  `deleted` tinyint(0) NULL DEFAULT NULL COMMENT '是否删除： 0-未删除 1-已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '订单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oms_order_item
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_item`;
CREATE TABLE `oms_order_item`  (
  `id` bigint(0) NOT NULL,
  `order_id` bigint(0) NOT NULL COMMENT '订单id',
  `order_sn` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '订单编号',
  `spu_id` bigint(0) NOT NULL COMMENT '商品id',
  `spu_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品名称',
  `sku_id` bigint(0) NOT NULL COMMENT '货品id',
  `sku_bar_code` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '货品条码',
  `sku_specifications` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '货品规格描述',
  `sku_price` decimal(10, 2) NOT NULL COMMENT '货品价格',
  `sku_quantity` int(0) NOT NULL COMMENT '货品数量',
  `pic` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '货品图片或者商品图片',
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '订单明细表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pms_brand
-- ----------------------------
DROP TABLE IF EXISTS `pms_brand`;
CREATE TABLE `pms_brand`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '品牌名称',
  `first_letter` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '检索首字母',
  `logo` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '品牌logo图片地址',
  `sort` int(0) NULL DEFAULT NULL COMMENT '排序',
  `status` tinyint(0) NULL DEFAULT NULL COMMENT '状态: 1-正常 0-禁用',
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
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
  `level` int(0) NULL DEFAULT NULL COMMENT '层级',
  `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图标地址',
  `sort` int(0) NULL DEFAULT NULL COMMENT '排序',
  `status` tinyint(1) NULL DEFAULT NULL COMMENT '显示状态: 0-隐藏 1-显示',
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品分类表' ROW_FORMAT = Dynamic;

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
  `bar_code` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品码',
  `pic` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品SKU图片',
  `origin_price` bigint(0) NOT NULL COMMENT '原价',
  `price` bigint(0) NOT NULL COMMENT '现价',
  `stock` int(0) NOT NULL DEFAULT 0 COMMENT '库存',
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `pms_sku_bar_code_uindex`(`bar_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 30 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '货品表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pms_sku
-- ----------------------------
INSERT INTO `pms_sku` VALUES (1, 1, '{\"存储\": \"8+256G\", \"颜色\": \"灰色\"}', '10001', NULL, 1, 1, 0, '2020-11-05 16:13:16', NULL);
INSERT INTO `pms_sku` VALUES (2, 4, '{\"11\": \"22\"}', '1605257904209', NULL, 0, 1, 2, '2020-11-13 16:58:30', '2020-11-13 16:58:30');
INSERT INTO `pms_sku` VALUES (3, 4, '{\"11\": \"33\"}', '1605257905022', NULL, 0, 1, 2, '2020-11-13 16:58:30', '2020-11-13 16:58:30');
INSERT INTO `pms_sku` VALUES (4, 5, '{\"尺寸\": \"L\", \"颜色\": \"蓝色\"}', '1605261224896', 'http://101.37.69.49:9000/default/16e489777c98428bb62497364c3c4908.jpg', 3, 601, 2, '2020-11-13 17:54:13', '2020-11-13 17:54:13');
INSERT INTO `pms_sku` VALUES (5, 5, '{\"尺寸\": \"M\", \"颜色\": \"蓝色\"}', '1605261225408', 'http://101.37.69.49:9000/default/66173bf1dae547ad8d50cebad02697eb.jpg', 4, 401, 2, '2020-11-13 17:54:13', '2020-11-13 17:54:13');
INSERT INTO `pms_sku` VALUES (6, 5, '{\"尺寸\": \"L\", \"颜色\": \"白色\"}', '1605261225976', 'http://101.37.69.49:9000/default/f5ec08165e0f4a7abc92c6ea3cf0efa0.gif', 4, 401, 2, '2020-11-13 17:54:13', '2020-11-13 17:54:13');
INSERT INTO `pms_sku` VALUES (7, 5, '{\"尺寸\": \"M\", \"颜色\": \"白色\"}', '1605261226832', 'http://101.37.69.49:9000/default/21e8bc25f58c428aba6164261f413574.jpg', 3, 301, 2, '2020-11-13 17:54:13', '2020-11-13 17:54:13');
INSERT INTO `pms_sku` VALUES (8, 6, '{\"颜色\": \"黑色\"}', '1605316515149', 'http://101.37.69.49:9000/default/c1f12e5828bc4a3098be0194d0601840.jpg', 0, 1, 2, '2020-11-14 09:15:47', '2020-11-14 09:15:47');
INSERT INTO `pms_sku` VALUES (9, 6, '{\"颜色\": \"白色\"}', '1605316516035', 'http://101.37.69.49:9000/default/70af5ff66f6f4348b74161aabe369702.jpg', 0, 1, 3, '2020-11-14 09:15:47', '2020-11-14 09:15:47');
INSERT INTO `pms_sku` VALUES (22, 10, '{\"内存\": \"4G\", \"存储\": \"64G\", \"颜色\": \"黑色\"}', '1605317058485', 'http://101.37.69.49:9000/default/d7c36e289eb14dcea67d20ebcac79d87.jpg', 1, 401, 2, '2020-11-14 09:46:52', '2020-11-14 09:46:52');
INSERT INTO `pms_sku` VALUES (23, 10, '{\"内存\": \"4G\", \"存储\": \"128G\", \"颜色\": \"黑色\"}', '1605317059016', 'http://101.37.69.49:9000/default/29697a3f43f64172b91b4d1d241ca602.jpg', 1, 301, 1, '2020-11-14 09:46:52', '2020-11-14 09:46:52');
INSERT INTO `pms_sku` VALUES (24, 10, '{\"内存\": \"6G\", \"存储\": \"64G\", \"颜色\": \"黑色\"}', '1605317059753', 'http://101.37.69.49:9000/default/d4b46f2405b54635bb1c0589f68a74e6.png', 1, 200, 1, '2020-11-14 09:46:52', '2020-11-14 09:46:52');
INSERT INTO `pms_sku` VALUES (25, 10, '{\"内存\": \"6G\", \"存储\": \"128G\", \"颜色\": \"黑色\"}', '1605317060895', 'http://101.37.69.49:9000/default/432579106d32465296f930d15eafd466.png', 1, 301, 1, '2020-11-14 09:46:52', '2020-11-14 09:46:52');
INSERT INTO `pms_sku` VALUES (26, 10, '{\"内存\": \"4G\", \"存储\": \"64G\", \"颜色\": \"白色\"}', '1605317061416', 'http://101.37.69.49:9000/default/f5eb5e307adf439cb7da0f847f4ddace.png', 2, 200, 1, '2020-11-14 09:46:52', '2020-11-14 09:46:52');
INSERT INTO `pms_sku` VALUES (27, 10, '{\"内存\": \"4G\", \"存储\": \"128G\", \"颜色\": \"白色\"}', NULL, 'http://101.37.69.49:9000/default/9de00b77c06245538572c09ad689dfda.jpg', 1, 200, 1, '2020-11-14 09:46:52', '2020-11-14 09:46:52');
INSERT INTO `pms_sku` VALUES (28, 10, '{\"内存\": \"6G\", \"存储\": \"64G\", \"颜色\": \"白色\"}', '1605317062900', 'http://101.37.69.49:9000/default/d48ac97541f44cea8087b8f26da588c4.jpg', 1, 200, 1, '2020-11-14 09:46:52', '2020-11-14 09:46:52');
INSERT INTO `pms_sku` VALUES (29, 10, '{\"内存\": \"6G\", \"存储\": \"128G\", \"颜色\": \"白色\"}', '1605317063290', 'http://101.37.69.49:9000/default/9b2a4dfae67b44b89cc9589de691ee8d.jpg', 0, 301, 1, '2020-11-14 09:46:52', '2020-11-14 09:46:52');

-- ----------------------------
-- Table structure for pms_spu
-- ----------------------------
DROP TABLE IF EXISTS `pms_spu`;
CREATE TABLE `pms_spu`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品名称',
  `category_id` bigint(0) NOT NULL COMMENT '商品类型id',
  `brand_id` bigint(0) NULL DEFAULT NULL COMMENT '商品品牌id',
  `origin_price` bigint(0) NOT NULL COMMENT '原价',
  `price` bigint(0) NOT NULL COMMENT '现价',
  `sales` int(0) NULL DEFAULT 0 COMMENT '销量',
  `pic` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品主图',
  `album` json NULL COMMENT '商品相册',
  `unit` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '单位',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品简介',
  `detail` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '商品详情',
  `status` tinyint(0) NULL DEFAULT NULL COMMENT '商品状态：0-下架 1-上架',
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pms_spu
-- ----------------------------
INSERT INTO `pms_spu` VALUES (1, 'Redmi K30S', 1, 1, 1, 1, 1, NULL, NULL, NULL, NULL, NULL, 1, NULL, NULL);
INSERT INTO `pms_spu` VALUES (2, '1', 4, 1, 200, 300, 0, 'http://101.37.69.49:9000/default/f14029ad40704d2ab706346b356a6400.gif', '[\"http://101.37.69.49:9000/default/67b399d579aa41fcaee4bb35c6deac59.jpg\"]', NULL, '111', '<p>22</p>', 1, '2020-11-13 15:45:01', '2020-11-13 15:45:01');
INSERT INTO `pms_spu` VALUES (3, '1', 4, 1, 200, 300, 0, 'http://101.37.69.49:9000/default/f14029ad40704d2ab706346b356a6400.gif', '[\"http://101.37.69.49:9000/default/67b399d579aa41fcaee4bb35c6deac59.jpg\"]', NULL, '111', '<p>22</p>', 1, '2020-11-13 15:46:55', '2020-11-13 15:46:55');
INSERT INTO `pms_spu` VALUES (4, '11', 4, 1, 2200, 3300, 0, 'http://101.37.69.49:9000/default/3b7c5b531312422587e796f7560420ea.gif', '[\"http://101.37.69.49:9000/default/36bba116b5534ff7bd2c7171bdb3d7c9.jpg\"]', NULL, '11231', '<p>123</p>', 1, '2020-11-13 16:58:30', '2020-11-13 16:58:30');
INSERT INTO `pms_spu` VALUES (5, '2020小米T恤', 4, 1, 10000, 6000, 0, 'http://101.37.69.49:9000/default/cf4f6ad55e474cb38dab60ead580f1be.gif', '[\"http://101.37.69.49:9000/default/82fe07b62036411fa37e70cf13df702a.jpg\"]', NULL, '商品简介', '<p>12312</p>', 1, '2020-11-13 17:54:13', '2020-11-13 17:54:13');
INSERT INTO `pms_spu` VALUES (6, '小米手环4', 8, 1, 24900, 15000, 0, 'http://101.37.69.49:9000/default/61e5798588d044b99728fcec7bf8c051.jpg', '[\"http://101.37.69.49:9000/default/232413f3bc60463cacea4c126788cc6e.jpg\", \"http://101.37.69.49:9000/default/f69f1442c11c4fdf905bef18c37aee23.jpg\"]', '个', '小米手环', '<p>小米手环</p>', 1, '2020-11-14 09:15:47', '2020-11-14 09:15:47');
INSERT INTO `pms_spu` VALUES (7, '小米手环5', 8, 1, 24900, 15000, 0, 'http://101.37.69.49:9000/default/61e5798588d044b99728fcec7bf8c051.jpg', '[\"http://101.37.69.49:9000/default/232413f3bc60463cacea4c126788cc6e.jpg\", \"http://101.37.69.49:9000/default/f69f1442c11c4fdf905bef18c37aee23.jpg\"]', '个', '小米手环', '<p>小米手环</p>', 1, '2020-11-14 09:19:16', '2020-11-14 09:19:16');
INSERT INTO `pms_spu` VALUES (8, '小米手机10', 8, 1, 599900, 499900, 0, 'http://101.37.69.49:9000/default/40ffc46040ca431aba23c48798a82bb8.jpg', '[\"http://101.37.69.49:9000/default/dbb1c4e37b6244f3a6b0f635db90bf54.jpg\"]', '台', '商品简介', '<p>商品详情</p>', 1, '2020-11-14 09:25:29', '2020-11-14 09:25:29');
INSERT INTO `pms_spu` VALUES (9, '小米手机10', 8, 1, 599900, 499900, 0, 'http://101.37.69.49:9000/default/40ffc46040ca431aba23c48798a82bb8.jpg', '[\"http://101.37.69.49:9000/default/dbb1c4e37b6244f3a6b0f635db90bf54.jpg\"]', '台', '商品简介', '<p>商品详情</p>', 1, '2020-11-14 09:38:43', '2020-11-14 09:38:43');
INSERT INTO `pms_spu` VALUES (10, '小米手机11', 8, 1, 599900, 499900, 0, 'http://101.37.69.49:9000/default/40ffc46040ca431aba23c48798a82bb8.jpg', '[\"http://101.37.69.49:9000/default/dbb1c4e37b6244f3a6b0f635db90bf54.jpg\"]', '台', '商品简介', '<p>商品详情</p>', 1, '2020-11-14 09:46:50', '2020-11-14 09:46:50');
INSERT INTO `pms_spu` VALUES (11, '华为Mate40 Pro', 8, 1, 6666666, 6666666, 1, 'http://101.37.69.49:9000/default/40ffc46040ca431aba23c48798a82bb8.jpg', '[\"http://101.37.69.49:9000/default/dbb1c4e37b6244f3a6b0f635db90bf54.jpg\"]', '台', '商品简介', '<p>商品详情</p>', 1, '2020-11-19 22:28:49', '2020-11-19 22:28:53');
INSERT INTO `pms_spu` VALUES (12, '华为Mate40 Pro', 8, 1, 6666666, 6666666, 1, 'http://101.37.69.49:9000/default/40ffc46040ca431aba23c48798a82bb8.jpg', NULL, '台', '商品简介', '<p>商品详情</p>', 1, '2020-11-19 22:28:49', '2020-11-19 22:28:53');
INSERT INTO `pms_spu` VALUES (13, '华为Mate40 Pro', 8, 1, 6666666, 6666666, 1, 'http://101.37.69.49:9000/default/40ffc46040ca431aba23c48798a82bb8.jpg', NULL, '台', '商品简介', '<p>商品详情</p>', 1, '2020-11-19 22:28:49', '2020-11-19 22:28:53');
INSERT INTO `pms_spu` VALUES (14, '小米手机10', 8, 1, 599900, 499900, 0, 'http://101.37.69.49:9000/default/40ffc46040ca431aba23c48798a82bb8.jpg', '[\"http://101.37.69.49:9000/default/dbb1c4e37b6244f3a6b0f635db90bf54.jpg\"]', '台', '商品简介', '<p>商品详情</p>', 1, NULL, NULL);
INSERT INTO `pms_spu` VALUES (15, '华为P40', 8, 1, 6666, 6666, 1, 'http://101.37.69.49:9000/default/40ffc46040ca431aba23c48798a82bb8.jpg', '[\"http://101.37.69.49:9000/default/dbb1c4e37b6244f3a6b0f635db90bf54.jpg\"]', '台', '商品简介', '<p>商品详情:华为P40</p>', 1, '2020-11-22 15:22:17', '2020-11-22 15:22:19');

-- ----------------------------
-- Table structure for pms_spu_attribute
-- ----------------------------
DROP TABLE IF EXISTS `pms_spu_attribute`;
CREATE TABLE `pms_spu_attribute`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `spu_id` bigint(0) NOT NULL COMMENT '商品id',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '属性名称',
  `value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '属性值',
  `gmt_create` datetime(0) NULL DEFAULT NULL,
  `gmt_modified` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品属性表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pms_spu_attribute
-- ----------------------------
INSERT INTO `pms_spu_attribute` VALUES (1, 4, '1', '2', '2020-11-13 16:58:30', '2020-11-13 16:58:30');
INSERT INTO `pms_spu_attribute` VALUES (2, 5, '上市时间', '2020-11-13', '2020-11-13 17:54:13', '2020-11-13 17:54:13');
INSERT INTO `pms_spu_attribute` VALUES (3, 6, '上市时间', '2020-01-01', '2020-11-14 09:15:47', '2020-11-14 09:15:47');
INSERT INTO `pms_spu_attribute` VALUES (4, 7, '上市时间', '2020-01-01', '2020-11-14 09:19:16', '2020-11-14 09:19:16');
INSERT INTO `pms_spu_attribute` VALUES (5, 8, '上市时间', '2020-10-10', '2020-11-14 09:25:29', '2020-11-14 09:25:29');
INSERT INTO `pms_spu_attribute` VALUES (6, 9, '上市时间', '2020-10-10', '2020-11-14 09:38:48', '2020-11-14 09:38:48');
INSERT INTO `pms_spu_attribute` VALUES (7, 10, '上市时间', '2020-10-10', '2020-11-14 09:46:52', '2020-11-14 09:46:52');
INSERT INTO `pms_spu_attribute` VALUES (8, 14, '上市时间', '2020-10-10', NULL, NULL);

-- ----------------------------
-- Table structure for pms_spu_specification
-- ----------------------------
DROP TABLE IF EXISTS `pms_spu_specification`;
CREATE TABLE `pms_spu_specification`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `spu_id` bigint(0) NOT NULL COMMENT '商品id',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '规格名称',
  `value` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '规格值',
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品规格表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pms_spu_specification
-- ----------------------------
INSERT INTO `pms_spu_specification` VALUES (1, 4, '11', '22', '2020-11-13 16:58:30', '2020-11-13 16:58:30');
INSERT INTO `pms_spu_specification` VALUES (2, 4, '11', '33', '2020-11-13 16:58:30', '2020-11-13 16:58:30');
INSERT INTO `pms_spu_specification` VALUES (3, 5, '颜色', '蓝色', '2020-11-13 17:54:13', '2020-11-13 17:54:13');
INSERT INTO `pms_spu_specification` VALUES (4, 5, '颜色', '白色', '2020-11-13 17:54:13', '2020-11-13 17:54:13');
INSERT INTO `pms_spu_specification` VALUES (5, 5, '尺寸', 'L', '2020-11-13 17:54:13', '2020-11-13 17:54:13');
INSERT INTO `pms_spu_specification` VALUES (6, 5, '尺寸', 'M', '2020-11-13 17:54:13', '2020-11-13 17:54:13');
INSERT INTO `pms_spu_specification` VALUES (7, 6, '颜色', '黑色', '2020-11-14 09:15:47', '2020-11-14 09:15:47');
INSERT INTO `pms_spu_specification` VALUES (8, 6, '颜色', '白色', '2020-11-14 09:15:47', '2020-11-14 09:15:47');
INSERT INTO `pms_spu_specification` VALUES (9, 7, '颜色', '黑色', '2020-11-14 09:19:16', '2020-11-14 09:19:16');
INSERT INTO `pms_spu_specification` VALUES (10, 7, '颜色', '白色', '2020-11-14 09:19:16', '2020-11-14 09:19:16');
INSERT INTO `pms_spu_specification` VALUES (11, 8, '颜色', '黑色', '2020-11-14 09:25:29', '2020-11-14 09:25:29');
INSERT INTO `pms_spu_specification` VALUES (12, 8, '颜色', '白色', '2020-11-14 09:25:29', '2020-11-14 09:25:29');
INSERT INTO `pms_spu_specification` VALUES (13, 8, '内存', '4G', '2020-11-14 09:25:29', '2020-11-14 09:25:29');
INSERT INTO `pms_spu_specification` VALUES (14, 8, '内存', '6G', '2020-11-14 09:25:29', '2020-11-14 09:25:29');
INSERT INTO `pms_spu_specification` VALUES (15, 8, '存储', '64G', '2020-11-14 09:25:29', '2020-11-14 09:25:29');
INSERT INTO `pms_spu_specification` VALUES (16, 8, '存储', '128G', '2020-11-14 09:25:29', '2020-11-14 09:25:29');
INSERT INTO `pms_spu_specification` VALUES (17, 9, '颜色', '黑色', '2020-11-14 09:38:48', '2020-11-14 09:38:48');
INSERT INTO `pms_spu_specification` VALUES (18, 9, '颜色', '白色', '2020-11-14 09:38:48', '2020-11-14 09:38:48');
INSERT INTO `pms_spu_specification` VALUES (19, 9, '内存', '4G', '2020-11-14 09:38:48', '2020-11-14 09:38:48');
INSERT INTO `pms_spu_specification` VALUES (20, 9, '内存', '6G', '2020-11-14 09:38:48', '2020-11-14 09:38:48');
INSERT INTO `pms_spu_specification` VALUES (21, 9, '存储', '64G', '2020-11-14 09:38:48', '2020-11-14 09:38:48');
INSERT INTO `pms_spu_specification` VALUES (22, 9, '存储', '128G', '2020-11-14 09:38:48', '2020-11-14 09:38:48');
INSERT INTO `pms_spu_specification` VALUES (23, 10, '颜色', '黑色', '2020-11-14 09:46:52', '2020-11-14 09:46:52');
INSERT INTO `pms_spu_specification` VALUES (24, 10, '颜色', '白色', '2020-11-14 09:46:52', '2020-11-14 09:46:52');
INSERT INTO `pms_spu_specification` VALUES (25, 10, '内存', '4G', '2020-11-14 09:46:52', '2020-11-14 09:46:52');
INSERT INTO `pms_spu_specification` VALUES (26, 10, '内存', '6G', '2020-11-14 09:46:52', '2020-11-14 09:46:52');
INSERT INTO `pms_spu_specification` VALUES (27, 10, '存储', '64G', '2020-11-14 09:46:52', '2020-11-14 09:46:52');
INSERT INTO `pms_spu_specification` VALUES (28, 10, '存储', '128G', '2020-11-14 09:46:52', '2020-11-14 09:46:52');
INSERT INTO `pms_spu_specification` VALUES (29, 14, '颜色', '黑色', NULL, NULL);
INSERT INTO `pms_spu_specification` VALUES (30, 14, '颜色', '白色', NULL, NULL);
INSERT INTO `pms_spu_specification` VALUES (31, 14, '内存', '4G', NULL, NULL);
INSERT INTO `pms_spu_specification` VALUES (32, 14, '内存', '6G', NULL, NULL);
INSERT INTO `pms_spu_specification` VALUES (33, 14, '存储', '64G', NULL, NULL);
INSERT INTO `pms_spu_specification` VALUES (34, 14, '存储', '128G', NULL, NULL);

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
  `type` tinyint(0) NULL DEFAULT NULL COMMENT '优惠券类型',
  `discount` decimal(10, 2) NULL DEFAULT NULL COMMENT '优惠金额',
  `code` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '优惠券兑换码',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '优惠券描述',
  `days` int(0) NULL DEFAULT NULL COMMENT '领取时间开始的有效天数',
  `start_time` datetime(0) NULL DEFAULT NULL COMMENT '优惠券有效开始时间',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '优惠券有效结束时间',
  `gmt_create` datetime(0) NULL DEFAULT NULL,
  `gmt_modified` datetime(0) NULL DEFAULT NULL,
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
  `gender` tinyint(1) NULL DEFAULT NULL,
  `nickname` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `mobile` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `birthday` date NULL DEFAULT NULL,
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `openid` char(28) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `session_key` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `status` tinyint(1) NULL DEFAULT NULL,
  `deleted` tinyint(1) NULL DEFAULT NULL,
  `gmt_create` datetime(0) NULL DEFAULT NULL,
  `gmt_modified` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ums_member
-- ----------------------------
INSERT INTO `ums_member` VALUES (1315699931359424513, 'oUBUG5hAB_8EMrSaqd2HjJQBFg74', '$2a$10$izTOlQmmWfYwY56Dc5mdY../m4a3345wSPKN2X5ldh9GgwWKY.sea', 1, '郝先瑞', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/J31cY2qVWviaOqhjPlr18VY5ic1SUvDESG1mQkicQfFugWibYe7VJIhYJBZYDBib0T4TJ8KClTmP3gOF1eOk91ctibRQ/132', 'oUBUG5hAB_8EMrSaqd2HjJQBFg74', NULL, 1, NULL, NULL, NULL);

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
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
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

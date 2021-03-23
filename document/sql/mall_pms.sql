/*
 Navicat Premium Data Transfer

 Source Server         : www.youlai.store
 Source Server Type    : MySQL
 Source Server Version : 80023
 Source Host           : www.youlai.store:3306
 Source Schema         : mall_pms

 Target Server Type    : MySQL
 Target Server Version : 80023
 File Encoding         : 65001

 Date: 22/03/2021 09:27:30
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for pms_attribute
-- ----------------------------
DROP TABLE IF EXISTS `pms_attribute`;
CREATE TABLE `pms_attribute`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '属性名称',
  `category_id` bigint(0) NOT NULL COMMENT '分类ID',
  `gmt_create` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_pms_attr_pms_category`(`category_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商品属性表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pms_attribute
-- ----------------------------
INSERT INTO `pms_attribute` VALUES (1, '上市日期', 2, NULL, NULL);
INSERT INTO `pms_attribute` VALUES (3, '上市时间', 24, NULL, NULL);
INSERT INTO `pms_attribute` VALUES (4, '季节', 24, NULL, NULL);
INSERT INTO `pms_attribute` VALUES (5, '上市时间', 47, NULL, NULL);
INSERT INTO `pms_attribute` VALUES (6, '适合季节', 47, NULL, NULL);
INSERT INTO `pms_attribute` VALUES (7, '上市时间', 26, NULL, NULL);
INSERT INTO `pms_attribute` VALUES (10, '3', 31, NULL, NULL);
INSERT INTO `pms_attribute` VALUES (11, '223', 31, NULL, NULL);
INSERT INTO `pms_attribute` VALUES (12, '去玩儿', 37, NULL, NULL);
INSERT INTO `pms_attribute` VALUES (13, 'VB你', 37, NULL, NULL);
INSERT INTO `pms_attribute` VALUES (14, '200', 50, NULL, NULL);
INSERT INTO `pms_attribute` VALUES (15, '颜色', 63, NULL, NULL);
INSERT INTO `pms_attribute` VALUES (16, '尺寸', 63, NULL, NULL);
INSERT INTO `pms_attribute` VALUES (17, '颜色', 64, NULL, NULL);
INSERT INTO `pms_attribute` VALUES (18, '尺寸', 64, NULL, NULL);
INSERT INTO `pms_attribute` VALUES (19, '上市时间', 62, NULL, NULL);
INSERT INTO `pms_attribute` VALUES (21, '上市时间', 61, NULL, NULL);
INSERT INTO `pms_attribute` VALUES (22, '123', 79, NULL, NULL);
INSERT INTO `pms_attribute` VALUES (23, '4455', 79, NULL, NULL);

-- ----------------------------
-- Table structure for pms_brand
-- ----------------------------
DROP TABLE IF EXISTS `pms_brand`;
CREATE TABLE `pms_brand`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '品牌名称',
  `first_letter` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '首字母',
  `logo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT 'LOGO图片',
  `sort` int(0) DEFAULT NULL COMMENT '排序',
  `status` tinyint(0) DEFAULT NULL COMMENT '状态: 0-禁用  1-正常 ',
  `gmt_create` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商品品牌表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pms_brand
-- ----------------------------
INSERT INTO `pms_brand` VALUES (4, '有来', 'Y', 'http://101.37.69.49:9000/default/1282ca4087fe4b0699827d68b75d765c.png', 1, 1, NULL, '2021-02-20 13:53:33');

-- ----------------------------
-- Table structure for pms_category
-- ----------------------------
DROP TABLE IF EXISTS `pms_category`;
CREATE TABLE `pms_category`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品分类名称',
  `parent_id` bigint(0) NOT NULL COMMENT '父级ID',
  `level` int(0) DEFAULT NULL COMMENT '层级',
  `icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '图标地址',
  `sort` int(0) DEFAULT NULL COMMENT '排序',
  `status` tinyint(1) DEFAULT 1 COMMENT '显示状态: 0-隐藏 1-显示',
  `gmt_create` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 82 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商品分类表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pms_category
-- ----------------------------
INSERT INTO `pms_category` VALUES (1, '手机数码', 0, 1, NULL, 1, 1, NULL, '2021-02-06 17:16:54');
INSERT INTO `pms_category` VALUES (2, '手机通讯', 1, 2, '', 1, 1, NULL, '2021-02-06 17:14:28');
INSERT INTO `pms_category` VALUES (23, '男装女装', 0, 1, NULL, 4, 1, NULL, NULL);
INSERT INTO `pms_category` VALUES (24, '男装', 23, 2, '', 1, 1, NULL, NULL);
INSERT INTO `pms_category` VALUES (25, '运营商', 1, 2, '', 2, 1, NULL, NULL);
INSERT INTO `pms_category` VALUES (26, '全面屏手机', 2, 3, 'http://a.youlai.store:9000/default/c9760b09a86644ccbf56a8e4acb42e32.jpg', 1, 1, NULL, '2021-02-28 10:50:24');
INSERT INTO `pms_category` VALUES (27, '合约机', 25, 3, 'http://a.youlai.store:9000/default/ea166cb2cc1042569e438179b16a6d73.jpg', 1, 1, NULL, '2021-02-28 11:43:30');
INSERT INTO `pms_category` VALUES (28, '游戏手机', 2, 3, 'http://a.youlai.store:9000/default/34eee030d6f548c5803b8d476a29094b.jpg', 2, 1, NULL, '2021-02-28 10:50:33');
INSERT INTO `pms_category` VALUES (32, '选好卡', 25, 3, 'http://a.youlai.store:9000/default/04cfce9dbc44427d83babe9aac02b291.jpg', 2, 1, NULL, '2021-02-28 15:54:27');
INSERT INTO `pms_category` VALUES (33, '办套餐', 25, 3, 'http://a.youlai.store:9000/default/22bdf35e0cfb442c8bae519d111c9621.jpg', 3, 1, NULL, '2021-02-28 10:52:15');
INSERT INTO `pms_category` VALUES (34, '礼品鲜花', 0, 1, NULL, 2, 1, NULL, NULL);
INSERT INTO `pms_category` VALUES (35, '礼品', 34, 2, NULL, 1, 1, NULL, NULL);
INSERT INTO `pms_category` VALUES (36, '鲜花', 34, 2, NULL, 2, 1, NULL, NULL);
INSERT INTO `pms_category` VALUES (37, '公益摆件', 35, 3, 'http://a.youlai.store:9000/default/9db7d7e5d7c24cf2b30185240b061d18.jpg', 1, 1, NULL, '2021-02-28 10:52:45');
INSERT INTO `pms_category` VALUES (38, '创意礼品', 35, 3, 'http://a.youlai.store:9000/default/f23a8a308a5a4926a96061c259e1cf60.jpg', 2, 1, NULL, '2021-02-28 10:53:07');
INSERT INTO `pms_category` VALUES (39, '鲜花', 36, 3, 'http://a.youlai.store:9000/default/49534b0fba0840b384b9df5dd6994742.jpg', 1, 1, NULL, '2021-02-28 10:53:22');
INSERT INTO `pms_category` VALUES (40, '每周一花', 36, 3, 'http://a.youlai.store:9000/default/e49e6b04b2bc458ca6df6e0dd96a9489.jpg', 2, 1, NULL, '2021-02-28 10:55:39');
INSERT INTO `pms_category` VALUES (41, '卡通花束', 36, 3, 'http://a.youlai.store:9000/default/8d13a211631a4424853d25eda09ae5ba.jpg', 3, 1, NULL, '2021-02-28 10:55:09');
INSERT INTO `pms_category` VALUES (42, '永生花', 36, 3, 'http://a.youlai.store:9000/default/ba46f789bae144438aed5249f293f2ad.jpg', 4, 1, NULL, '2021-02-28 10:55:23');
INSERT INTO `pms_category` VALUES (44, '男士T恤', 24, 3, 'http://a.youlai.store:9000/default/27662c9dc55d465d96bacaab23f06112.jpg', 1, 1, NULL, '2021-02-28 11:02:42');
INSERT INTO `pms_category` VALUES (47, '上装', 43, 3, 'http://101.37.69.49:9000/default/5e8e741773b447b0a6a61f67401f4e24.jpg', 2, 1, NULL, NULL);
INSERT INTO `pms_category` VALUES (56, '老人机', 2, 3, 'http://a.youlai.store:9000/default/7dafb86bd11b4a64a2eca13ab886fbd7.jpg', 3, 1, '2021-02-06 17:15:24', '2021-02-28 10:50:53');
INSERT INTO `pms_category` VALUES (57, '拍照手机', 2, 3, 'http://a.youlai.store:9000/default/2186f8f42ed24f29a49af39774a5c1dd.jpg', 4, 1, '2021-02-06 17:15:54', '2021-02-28 10:51:08');
INSERT INTO `pms_category` VALUES (58, '女性手机', 2, 3, 'http://a.youlai.store:9000/default/bef042df51ac4ae8a65b0fcc0ee9b356.jpg', 5, 1, '2021-02-06 17:16:15', '2021-02-28 10:51:15');
INSERT INTO `pms_category` VALUES (59, '男士外套', 24, 3, 'http://a.youlai.store:9000/default/da6a9ee68ee94de4b828b0cd8f371bde.jpg', 2, 1, '2021-02-06 17:18:08', '2021-02-28 11:02:53');
INSERT INTO `pms_category` VALUES (60, '女装', 23, 2, NULL, 2, 1, '2021-02-06 17:18:45', '2021-02-06 17:18:45');
INSERT INTO `pms_category` VALUES (61, '裙装', 60, 3, 'http://a.youlai.store:9000/default/f007f48c33d64e0783c2ec4a1dfe673d.jpg', 1, 1, '2021-02-06 17:19:16', '2021-02-28 16:05:27');
INSERT INTO `pms_category` VALUES (62, 'T恤', 60, 3, 'http://a.youlai.store:9000/default/b188c446c93f4ea28a3f196ef1dac9cd.png', 2, 1, '2021-02-06 17:19:37', '2021-02-28 16:06:35');
INSERT INTO `pms_category` VALUES (63, '上装', 60, 3, 'http://a.youlai.store:9000/default/f0668174364d4c6d869222f5cfe6a082.jpg', 3, 1, '2021-02-06 17:20:04', '2021-02-28 16:13:36');
INSERT INTO `pms_category` VALUES (64, '下装', 60, 3, 'http://a.youlai.store:9000/default/43103c046bc444c8b573f3fc2d2428c0.jpg', 4, 1, '2021-02-06 17:20:22', '2021-02-28 16:15:33');
INSERT INTO `pms_category` VALUES (65, '母婴用品', 0, 1, NULL, 4, 1, '2021-02-06 17:21:00', '2021-02-06 17:21:00');
INSERT INTO `pms_category` VALUES (66, '奶粉', 65, 2, NULL, 1, 1, '2021-02-06 17:21:28', '2021-02-06 17:21:28');
INSERT INTO `pms_category` VALUES (67, '营养辅食', 65, 2, NULL, 2, 1, '2021-02-06 17:21:36', '2021-02-06 17:21:36');
INSERT INTO `pms_category` VALUES (68, '童装', 65, 2, NULL, 3, 1, '2021-02-06 17:21:49', '2021-02-06 17:21:49');
INSERT INTO `pms_category` VALUES (69, '喂养用品', 65, 2, NULL, 4, 1, '2021-02-06 17:21:58', '2021-02-06 17:21:58');
INSERT INTO `pms_category` VALUES (70, '有机奶粉', 66, 3, 'http://a.youlai.store:9000/default/830b1c705b7e44f1b1df259e8f2d08b5.jpg', 1, 1, '2021-02-06 17:22:11', '2021-02-28 10:58:05');
INSERT INTO `pms_category` VALUES (71, '果泥/果汁', 67, 3, 'http://a.youlai.store:9000/default/a480b857fc5345d2b4e7da82ad73c8fe.jpg', 1, 1, '2021-02-06 17:22:31', '2021-02-28 10:58:33');
INSERT INTO `pms_category` VALUES (72, '面条/粥', 67, 3, 'http://a.youlai.store:9000/default/dc5eefd9ebb54da4819d5864760e7bd9.jpg', 2, 1, '2021-02-06 17:22:48', '2021-02-28 16:18:53');
INSERT INTO `pms_category` VALUES (73, '婴童衣橱', 68, 3, 'http://a.youlai.store:9000/default/6aa5f02aba7f4c6db3756fc2cc089514.jpg', 1, 1, '2021-02-06 17:23:07', '2021-02-28 11:01:01');
INSERT INTO `pms_category` VALUES (74, '吸奶器', 69, 3, 'http://a.youlai.store:9000/default/89e977f798494eab9f7cf5f443d29851.jpg', 1, 1, '2021-02-06 17:23:24', '2021-02-28 11:01:11');
INSERT INTO `pms_category` VALUES (75, '儿童餐具', 69, 3, 'http://a.youlai.store:9000/default/63cdc5eebbc64dd597b879a563762a1d.jpg', 2, 1, '2021-02-06 17:24:01', '2021-02-28 11:02:19');
INSERT INTO `pms_category` VALUES (76, '牙胶安抚', 69, 3, 'http://a.youlai.store:9000/default/26e819ef86aa4089a6d7d81dc236ec66.jpg', 3, 1, '2021-02-06 17:24:35', '2021-02-28 11:02:31');
INSERT INTO `pms_category` VALUES (77, '围兜', 69, 3, 'http://a.youlai.store:9000/default/1211bde195bc4ed4a3040301e1e2291b.jpg', 4, 1, '2021-02-06 17:24:49', '2021-02-28 11:02:08');

-- ----------------------------
-- Table structure for pms_sku
-- ----------------------------
DROP TABLE IF EXISTS `pms_sku`;
CREATE TABLE `pms_sku`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `spu_id` bigint(0) NOT NULL COMMENT 'SPU ID',
  `name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '标题',
  `code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT 'SKU编码',
  `pic` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT 'SKU图片',
  `spec_value_ids` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '商品规格值ID，以,分割',
  `origin_price` bigint(0) NOT NULL COMMENT '原价',
  `price` bigint(0) NOT NULL COMMENT '现价',
  `stock` int(0) NOT NULL DEFAULT 0 COMMENT '库存',
  `locked_stock` int(0) NOT NULL DEFAULT 0 COMMENT '锁定库存',
  `gmt_create` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_pms_sku_pms_spu`(`spu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 199 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商品库存表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pms_sku
-- ----------------------------
INSERT INTO `pms_sku` VALUES (186, 54, '测试手机1 黑 6+128 ', '1614505517559', 'http://a.youlai.store:9000/default/9715dde8c35c429b8c56cbe800ebd205.jpg', '1614505504702,1614505510650', 399900, 299900, 999, 0, '2021-02-28 17:45:43', '2021-03-11 20:10:35');
INSERT INTO `pms_sku` VALUES (187, 54, '测试手机1 黑 8+256 ', '1614505517560', 'http://a.youlai.store:9000/default/9715dde8c35c429b8c56cbe800ebd205.jpg', '1614505504702,1614505517558', 399900, 299900, 999, 0, '2021-02-28 17:45:43', '2021-03-11 20:10:35');
INSERT INTO `pms_sku` VALUES (190, 52, '测试手机2 黑 4+64 ', '1614505936507', 'http://a.youlai.store:9000/default/37729cd58065419f84b15f44b0e4f27f.jpg', '1614505920109,1614505925142', 199900, 159900, 984, 32, '2021-02-28 17:52:33', '2021-02-28 18:13:28');
INSERT INTO `pms_sku` VALUES (191, 52, '测试手机2 黑 6+128 ', '1614505936509', 'http://a.youlai.store:9000/default/37729cd58065419f84b15f44b0e4f27f.jpg', '1614505920109,1614505931577', 199900, 179900, 998, 0, '2021-02-28 17:52:33', '2021-02-28 18:13:28');
INSERT INTO `pms_sku` VALUES (192, 52, '测试手机2 黑 8+256 ', '1614505936511', 'http://a.youlai.store:9000/default/37729cd58065419f84b15f44b0e4f27f.jpg', '1614505920109,1614505936504', 199900, 199900, 999, 0, '2021-02-28 17:52:33', '2021-02-28 18:13:28');
INSERT INTO `pms_sku` VALUES (195, 54, '测试手机1 白 6+128 ', '1615464633246', 'http://a.youlai.store:9000/default/9715dde8c35c429b8c56cbe800ebd205.jpg', '1614505510650,1615464621209', 29900, 299900, 999, 0, '2021-03-11 20:10:35', '2021-03-11 20:10:35');
INSERT INTO `pms_sku` VALUES (196, 54, '测试手机1 白 8+256 ', '1615464630696', 'http://a.youlai.store:9000/default/9715dde8c35c429b8c56cbe800ebd205.jpg', '1614505517558,1615464621209', 34900, 299900, 999, 0, '2021-03-11 20:10:35', '2021-03-11 20:10:35');
INSERT INTO `pms_sku` VALUES (197, 53, '测试衣服1 白 M ', '1616310848932', 'http://a.youlai.store:9000/default/063350d473a64ee7857e91841add1177.jpg', '1616310839626,1616310844063', 39900, 29900, 9999, 0, '2021-03-21 15:14:13', '2021-03-21 15:42:04');
INSERT INTO `pms_sku` VALUES (198, 53, '测试衣服1 白 L ', '1616310848933', 'http://a.youlai.store:9000/default/063350d473a64ee7857e91841add1177.jpg', '1616310839626,1616310848931', 39900, 29900, 9999, 0, '2021-03-21 15:14:13', '2021-03-21 15:42:04');

-- ----------------------------
-- Table structure for pms_spec
-- ----------------------------
DROP TABLE IF EXISTS `pms_spec`;
CREATE TABLE `pms_spec`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `category_id` bigint(0) NOT NULL COMMENT '商品分类ID',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '规格名称',
  `gmt_create` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_pms_spec_pms_category`(`category_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商品规格表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pms_spec
-- ----------------------------
INSERT INTO `pms_spec` VALUES (1, 2, '颜色', NULL, NULL);
INSERT INTO `pms_spec` VALUES (2, 2, '版本', NULL, NULL);
INSERT INTO `pms_spec` VALUES (3, 24, '颜色', NULL, NULL);
INSERT INTO `pms_spec` VALUES (4, 24, '尺寸', NULL, NULL);
INSERT INTO `pms_spec` VALUES (6, 47, '颜色', NULL, NULL);
INSERT INTO `pms_spec` VALUES (7, 47, '尺寸', NULL, NULL);
INSERT INTO `pms_spec` VALUES (8, 26, '颜色', NULL, '2021-02-28 16:19:07');
INSERT INTO `pms_spec` VALUES (9, 26, '版本', NULL, '2021-02-28 16:19:07');
INSERT INTO `pms_spec` VALUES (10, 28, '价钱', NULL, NULL);
INSERT INTO `pms_spec` VALUES (11, 44, '颜色', NULL, NULL);
INSERT INTO `pms_spec` VALUES (12, 44, '尺寸', NULL, NULL);
INSERT INTO `pms_spec` VALUES (16, 61, '颜色', NULL, '2021-02-28 17:53:45');
INSERT INTO `pms_spec` VALUES (17, 79, 'vvv', NULL, NULL);
INSERT INTO `pms_spec` VALUES (18, 79, '222', NULL, NULL);
INSERT INTO `pms_spec` VALUES (19, 79, 'vvv', NULL, NULL);
INSERT INTO `pms_spec` VALUES (20, 61, '尺寸', '2021-02-28 17:53:45', '2021-02-28 17:53:45');

-- ----------------------------
-- Table structure for pms_spu
-- ----------------------------
DROP TABLE IF EXISTS `pms_spu`;
CREATE TABLE `pms_spu`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品名称',
  `category_id` bigint(0) NOT NULL COMMENT '商品类型ID',
  `brand_id` bigint(0) DEFAULT NULL COMMENT '商品品牌ID',
  `origin_price` bigint(0) NOT NULL COMMENT '原价【起】',
  `price` bigint(0) NOT NULL COMMENT '现价【起】',
  `sales` int(0) DEFAULT 0 COMMENT '销量',
  `pic` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '商品主图',
  `pics` json COMMENT '商品相册',
  `unit` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '单位',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '商品简介',
  `detail` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '商品详情',
  `status` tinyint(0) DEFAULT NULL COMMENT '商品状态：0-下架 1-上架',
  `gmt_create` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_pms_spu_pms_brand`(`brand_id`) USING BTREE,
  INDEX `fk_pms_spu_pms_category`(`category_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 55 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商品表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pms_spu
-- ----------------------------
INSERT INTO `pms_spu` VALUES (52, '测试手机2', 26, 4, 199900, 159900, 0, 'http://a.youlai.store:9000/default/37729cd58065419f84b15f44b0e4f27f.jpg', '[\"http://a.youlai.store:9000/default/37729cd58065419f84b15f44b0e4f27f.jpg\"]', '台', '测试手机使用', '<p>测试手机使用</p>', 1, NULL, '2021-02-28 18:13:26');
INSERT INTO `pms_spu` VALUES (53, '测试衣服1', 61, 4, 39900, 29900, 0, 'http://a.youlai.store:9000/default/063350d473a64ee7857e91841add1177.jpg', '[\"http://a.youlai.store:9000/default/063350d473a64ee7857e91841add1177.jpg\"]', '件', '测试', '<p>不错</p>', 1, '2021-02-22 09:33:05', '2021-03-21 15:42:04');
INSERT INTO `pms_spu` VALUES (54, '测试手机1', 26, 4, 399900, 299900, 0, 'http://a.youlai.store:9000/default/9715dde8c35c429b8c56cbe800ebd205.jpg', '[\"http://a.youlai.store:9000/default/9715dde8c35c429b8c56cbe800ebd205.jpg\"]', '台', '有来全面屏智能手机1', '<p>123</p>', 1, '2021-02-28 17:45:42', '2021-03-11 20:10:35');

-- ----------------------------
-- Table structure for pms_spu_attribute_value
-- ----------------------------
DROP TABLE IF EXISTS `pms_spu_attribute_value`;
CREATE TABLE `pms_spu_attribute_value`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `spu_id` bigint(0) NOT NULL COMMENT 'SPU ID',
  `attribute_id` bigint(0) DEFAULT NULL COMMENT '属性ID',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '属性名称【冗余字段】',
  `value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '属性值',
  `gmt_create` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_pms_spu_attribute_pms_attr`(`name`) USING BTREE,
  INDEX `fk_pms_spu_attribute_pms_spu`(`spu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 54 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商品属性项表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pms_spu_attribute_value
-- ----------------------------
INSERT INTO `pms_spu_attribute_value` VALUES (50, 53, NULL, '上市时间', '2020-02-28', '2021-02-22 16:30:43', '2021-03-21 15:42:04');
INSERT INTO `pms_spu_attribute_value` VALUES (52, 54, 7, '上市时间', '2020-02-28', '2021-02-28 17:45:42', '2021-03-11 20:10:35');
INSERT INTO `pms_spu_attribute_value` VALUES (53, 52, 7, '上市时间', '2020-02-28', '2021-02-28 17:52:33', '2021-02-28 18:13:27');

-- ----------------------------
-- Table structure for pms_spu_spec_value
-- ----------------------------
DROP TABLE IF EXISTS `pms_spu_spec_value`;
CREATE TABLE `pms_spu_spec_value`  (
  `id` bigint(0) NOT NULL COMMENT '主键',
  `spu_id` bigint(0) DEFAULT NULL COMMENT 'SPU ID',
  `spec_id` bigint(0) DEFAULT NULL COMMENT '规格ID',
  `value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '规格值',
  `gmt_create` datetime(0) DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `gmt_modified` datetime(0) DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_pms_sku_specification_pms_sku`(`spu_id`) USING BTREE,
  INDEX `fk_pms_sku_specification_pms_specification`(`spec_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商品规格关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pms_spu_spec_value
-- ----------------------------
INSERT INTO `pms_spu_spec_value` VALUES (1608802056776, 38, 1, '湖光秋色', NULL, NULL);
INSERT INTO `pms_spu_spec_value` VALUES (1608802062976, 38, 1, '碧海星辰', NULL, NULL);
INSERT INTO `pms_spu_spec_value` VALUES (1608802070118, 38, 1, '静默星空', NULL, NULL);
INSERT INTO `pms_spu_spec_value` VALUES (1608802076696, 38, 2, '6+128G', NULL, NULL);
INSERT INTO `pms_spu_spec_value` VALUES (1608802116726, 38, 2, '8+128G', NULL, NULL);
INSERT INTO `pms_spu_spec_value` VALUES (1608802138192, 38, 2, '8+256G', NULL, NULL);
INSERT INTO `pms_spu_spec_value` VALUES (1609000033689, 39, 1, '1', NULL, NULL);
INSERT INTO `pms_spu_spec_value` VALUES (1609000035657, 39, 2, '2', NULL, NULL);
INSERT INTO `pms_spu_spec_value` VALUES (1609000038179, 39, 1, '3', NULL, NULL);
INSERT INTO `pms_spu_spec_value` VALUES (1609000040282, 39, 2, '4', NULL, NULL);
INSERT INTO `pms_spu_spec_value` VALUES (1609000055065, 39, 1, '4', NULL, NULL);
INSERT INTO `pms_spu_spec_value` VALUES (1609000056291, 39, 2, '5', NULL, NULL);
INSERT INTO `pms_spu_spec_value` VALUES (1609000644446, 40, 1, '1', NULL, NULL);
INSERT INTO `pms_spu_spec_value` VALUES (1609000645513, 40, 2, '2', NULL, NULL);
INSERT INTO `pms_spu_spec_value` VALUES (1609000647035, 40, 1, '3', NULL, NULL);
INSERT INTO `pms_spu_spec_value` VALUES (1609000648202, 40, 2, '4', NULL, NULL);
INSERT INTO `pms_spu_spec_value` VALUES (1609000649901, 40, 1, '5', NULL, NULL);
INSERT INTO `pms_spu_spec_value` VALUES (1609000651182, 40, 2, '6', NULL, NULL);
INSERT INTO `pms_spu_spec_value` VALUES (1609042088936, 41, 1, '1', NULL, NULL);
INSERT INTO `pms_spu_spec_value` VALUES (1609042089916, 41, 2, '2', NULL, NULL);
INSERT INTO `pms_spu_spec_value` VALUES (1609042091253, 41, 1, '3', NULL, NULL);
INSERT INTO `pms_spu_spec_value` VALUES (1609042092450, 41, 2, '4', NULL, NULL);
INSERT INTO `pms_spu_spec_value` VALUES (1609042094404, 41, 1, '5', NULL, NULL);
INSERT INTO `pms_spu_spec_value` VALUES (1609042095699, 41, 2, '6', NULL, NULL);
INSERT INTO `pms_spu_spec_value` VALUES (1609042347511, 42, 1, '1', NULL, NULL);
INSERT INTO `pms_spu_spec_value` VALUES (1609042348372, 42, 2, '2', NULL, NULL);
INSERT INTO `pms_spu_spec_value` VALUES (1609042349850, 42, 1, '3', NULL, NULL);
INSERT INTO `pms_spu_spec_value` VALUES (1609042350769, 42, 2, '4', NULL, NULL);
INSERT INTO `pms_spu_spec_value` VALUES (1609042352933, 42, 1, '5', NULL, NULL);
INSERT INTO `pms_spu_spec_value` VALUES (1609042353617, 42, 2, '6', NULL, NULL);
INSERT INTO `pms_spu_spec_value` VALUES (1609042602199, 43, 1, '1', NULL, NULL);
INSERT INTO `pms_spu_spec_value` VALUES (1609042603267, 43, 2, '2', NULL, NULL);
INSERT INTO `pms_spu_spec_value` VALUES (1609042604496, 43, 1, '3', NULL, NULL);
INSERT INTO `pms_spu_spec_value` VALUES (1609042605719, 43, 2, '4', NULL, NULL);
INSERT INTO `pms_spu_spec_value` VALUES (1609043070853, 43, 1, '5', NULL, NULL);
INSERT INTO `pms_spu_spec_value` VALUES (1609069095406, 44, 3, '粉色', NULL, NULL);
INSERT INTO `pms_spu_spec_value` VALUES (1609069098669, 44, 3, '蓝色', NULL, NULL);
INSERT INTO `pms_spu_spec_value` VALUES (1609069102134, 44, 3, '白色', NULL, NULL);
INSERT INTO `pms_spu_spec_value` VALUES (1609069106105, 44, 4, 'M', NULL, NULL);
INSERT INTO `pms_spu_spec_value` VALUES (1609069109683, 44, 4, 'L', NULL, NULL);
INSERT INTO `pms_spu_spec_value` VALUES (1609069113091, 44, 4, 'XL', NULL, NULL);
INSERT INTO `pms_spu_spec_value` VALUES (1609327260839, 45, 6, '粉', NULL, NULL);
INSERT INTO `pms_spu_spec_value` VALUES (1609327264431, 45, 6, '蓝', NULL, NULL);
INSERT INTO `pms_spu_spec_value` VALUES (1609327268527, 45, 7, 'M', NULL, NULL);
INSERT INTO `pms_spu_spec_value` VALUES (1609327271672, 45, 7, 'L', NULL, NULL);
INSERT INTO `pms_spu_spec_value` VALUES (1609327275785, 45, 7, 'XL', NULL, NULL);
INSERT INTO `pms_spu_spec_value` VALUES (1609327278984, 45, 6, '白', NULL, NULL);
INSERT INTO `pms_spu_spec_value` VALUES (1609329141929, 46, 6, '蓝', NULL, NULL);
INSERT INTO `pms_spu_spec_value` VALUES (1609329145746, 46, 7, 'M', NULL, NULL);
INSERT INTO `pms_spu_spec_value` VALUES (1609329150111, 46, 6, '绿', NULL, NULL);
INSERT INTO `pms_spu_spec_value` VALUES (1609329153393, 46, 7, 'L', NULL, NULL);
INSERT INTO `pms_spu_spec_value` VALUES (1609329157057, 46, 7, 'XL', NULL, NULL);
INSERT INTO `pms_spu_spec_value` VALUES (1609465174892, 47, 6, '黄', NULL, NULL);
INSERT INTO `pms_spu_spec_value` VALUES (1609465178593, 47, 6, '蓝', NULL, NULL);
INSERT INTO `pms_spu_spec_value` VALUES (1609465182665, 47, 6, '绿', NULL, NULL);
INSERT INTO `pms_spu_spec_value` VALUES (1609465184422, 47, 7, 'M', NULL, NULL);
INSERT INTO `pms_spu_spec_value` VALUES (1609465189213, 47, 7, 'L', NULL, NULL);
INSERT INTO `pms_spu_spec_value` VALUES (1609465196239, 47, 7, 'XL', NULL, NULL);
INSERT INTO `pms_spu_spec_value` VALUES (1609861048903, 48, 8, '水水水水', NULL, NULL);
INSERT INTO `pms_spu_spec_value` VALUES (1609861053728, 48, 9, '22222', NULL, NULL);
INSERT INTO `pms_spu_spec_value` VALUES (1609861060714, 48, 8, '1111', NULL, NULL);
INSERT INTO `pms_spu_spec_value` VALUES (1609861063662, 48, 8, '3223232', NULL, NULL);
INSERT INTO `pms_spu_spec_value` VALUES (1609861160023, 48, 9, '112332', NULL, NULL);
INSERT INTO `pms_spu_spec_value` VALUES (1610678793825, 49, 8, '2', NULL, NULL);
INSERT INTO `pms_spu_spec_value` VALUES (1610678796037, 49, 9, '2', NULL, NULL);
INSERT INTO `pms_spu_spec_value` VALUES (1610961553389, 50, 8, '34', NULL, NULL);
INSERT INTO `pms_spu_spec_value` VALUES (1610961561656, 50, 9, '434 ', NULL, NULL);
INSERT INTO `pms_spu_spec_value` VALUES (1611107644444, 51, 8, '高端', NULL, NULL);
INSERT INTO `pms_spu_spec_value` VALUES (1611107652289, 51, 9, '上档次', NULL, NULL);
INSERT INTO `pms_spu_spec_value` VALUES (1614505504702, 54, 8, '黑', '2021-02-28 17:45:42', '2021-03-11 20:10:35');
INSERT INTO `pms_spu_spec_value` VALUES (1614505510650, 54, 9, '6+128', '2021-02-28 17:45:42', '2021-03-11 20:10:35');
INSERT INTO `pms_spu_spec_value` VALUES (1614505517558, 54, 9, '8+256', '2021-02-28 17:45:42', '2021-03-11 20:10:35');
INSERT INTO `pms_spu_spec_value` VALUES (1614505920109, 52, 8, '黑', '2021-02-28 17:52:33', '2021-02-28 18:13:27');
INSERT INTO `pms_spu_spec_value` VALUES (1614505925142, 52, 9, '4+64', '2021-02-28 17:52:33', '2021-02-28 18:13:27');
INSERT INTO `pms_spu_spec_value` VALUES (1614505931577, 52, 9, '6+128', '2021-02-28 17:52:33', '2021-02-28 18:13:27');
INSERT INTO `pms_spu_spec_value` VALUES (1614505936504, 52, 9, '8+256', '2021-02-28 17:52:33', '2021-02-28 18:13:27');
INSERT INTO `pms_spu_spec_value` VALUES (1615464621209, 54, 8, '白', '2021-03-11 20:10:35', '2021-03-11 20:10:35');
INSERT INTO `pms_spu_spec_value` VALUES (1616310839626, 53, 16, '白', '2021-03-21 07:40:34', '2021-03-21 15:42:04');
INSERT INTO `pms_spu_spec_value` VALUES (1616310844063, 53, 20, 'M', '2021-03-21 07:40:38', '2021-03-21 15:42:04');
INSERT INTO `pms_spu_spec_value` VALUES (1616310848931, 53, 20, 'L', '2021-03-21 07:40:44', '2021-03-21 15:42:04');

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

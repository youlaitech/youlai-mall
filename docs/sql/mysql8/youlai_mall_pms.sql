/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80027
 Source Host           : 192.168.10.192:3306
 Source Schema         : youlai_mall_pms

 Target Server Type    : MySQL
 Target Server Version : 80027
 File Encoding         : 65001

 Date: 26/04/2024 23:36:05
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for pms_attribute
-- ----------------------------
DROP TABLE IF EXISTS `pms_attribute`;
CREATE TABLE `pms_attribute`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `attribute_group_id` bigint NOT NULL COMMENT '属性组ID',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '属性名称',
  `input_type` tinyint NOT NULL COMMENT '输入录入方式：1-手动输入，2-从列表选择',
  `options` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '逗号分割的可选值列表，仅当input_type是2使用',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint NOT NULL DEFAULT 0 COMMENT '逻辑删除标识：0-未删除，1-已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pms_attribute
-- ----------------------------

-- ----------------------------
-- Table structure for pms_attribute_group
-- ----------------------------
DROP TABLE IF EXISTS `pms_attribute_group`;
CREATE TABLE `pms_attribute_group`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '属性组ID',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '属性组名称',
  `sort` smallint NOT NULL DEFAULT 1 COMMENT '排序',
  `remark` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint NOT NULL DEFAULT 0 COMMENT '逻辑删除标识：0-未删除，1-已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pms_attribute_group
-- ----------------------------

-- ----------------------------
-- Table structure for pms_brand
-- ----------------------------
DROP TABLE IF EXISTS `pms_brand`;
CREATE TABLE `pms_brand`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '品牌ID',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '品牌名称',
  `logo_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'Logo图片URL',
  `sort` int NOT NULL DEFAULT 1 COMMENT '排序',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint NOT NULL DEFAULT 0 COMMENT '逻辑删除标识：0-未删除，1-已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 34 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商品品牌表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pms_brand
-- ----------------------------
INSERT INTO `pms_brand` VALUES (1, '有来', 'http://a.youlai.tech:9000/default/5409e3deb5a14b8fa8cb4275dee0e25d.png', 1, '2021-07-11 19:56:58', '2021-07-11 20:02:54', 0);
INSERT INTO `pms_brand` VALUES (10, '小米', 'http://a.youlai.tech:9000/default/6a5a606fc60742919149a7861bf26cd5.jpg', 2, '2022-03-05 16:12:16', '2022-03-05 16:12:16', 0);
INSERT INTO `pms_brand` VALUES (11, '华硕', 'http://a.youlai.tech:9000/default/f18083f95e104a0bae3c587dee3bb2ed.png', 3, '2022-03-05 16:12:16', '2022-03-05 16:12:16', 0);
INSERT INTO `pms_brand` VALUES (20, '华为', 'https://oss.youlai.tech/default/ff61bd639b23491d8f2aa85d09fcf788.jpg', 1, '2022-05-06 23:08:33', '2022-05-06 23:08:33', 0);
INSERT INTO `pms_brand` VALUES (33, '惠普', 'https://oss.youlai.tech/default/4cf579add9544c6eaafb41ce1131559e.gif', 1, '2022-07-07 00:12:16', '2022-07-07 00:12:16', 0);

-- ----------------------------
-- Table structure for pms_category
-- ----------------------------
DROP TABLE IF EXISTS `pms_category`;
CREATE TABLE `pms_category`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '分类ID',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '分类名称',
  `parent_id` bigint NOT NULL COMMENT '父级ID',
  `icon_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '图标地址',
  `sort` int NOT NULL DEFAULT 1 COMMENT '排序',
  `visible` tinyint(1) NULL DEFAULT 1 COMMENT '显示状态(1-显示，0-隐藏)',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint NULL DEFAULT 0 COMMENT '逻辑删除标识(0-未删除，1-已删除)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 102 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商品分类表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pms_category
-- ----------------------------
INSERT INTO `pms_category` VALUES (3, '手机配件', 0, NULL, 2, 1, NULL, '2022-07-07 22:56:53', 0);
INSERT INTO `pms_category` VALUES (4, '智能手机', 3, NULL, 1, 1, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (5, '5g手机', 4, 'https://oss.youlai.tech/default/6ffb37110ac2434a9882b9e8968b2887.jpg', 1, 1, NULL, '2022-07-08 00:28:38', 0);
INSERT INTO `pms_category` VALUES (6, '电脑办公', 0, 'https://www.youlai.tech/files/default/776c21c1a71848069093033f461c5f4a.jpg', 1, 1, '2022-02-25 11:22:44', '2022-07-07 22:56:38', 0);
INSERT INTO `pms_category` VALUES (97, '笔记本电脑', 6, NULL, 100, 1, '2022-07-08 00:10:27', '2022-07-08 00:10:27', 0);
INSERT INTO `pms_category` VALUES (99, '三星轻薄本', 97, 'https://oss.youlai.tech/default/2f849b96ebb54ab3a94b1b90137f1b4d.png', 100, 1, '2022-07-08 00:14:03', '2022-07-08 00:26:52', 0);
INSERT INTO `pms_category` VALUES (100, '全能本', 97, 'https://oss.youlai.tech/default/37cc080ec61b4ce7b0583b002568ebaa.png', 100, 1, '2022-07-08 00:14:10', '2022-07-08 00:27:01', 0);
INSERT INTO `pms_category` VALUES (101, '游戏本', 97, 'https://oss.youlai.tech/default/5c1a2d5427534b48bc382caa55197f11.png', 100, 1, '2022-07-08 00:14:18', '2022-07-08 00:27:11', 0);

-- ----------------------------
-- Table structure for pms_category_brand
-- ----------------------------
DROP TABLE IF EXISTS `pms_category_brand`;
CREATE TABLE `pms_category_brand`  (
  `category_id` bigint NOT NULL,
  `brand_id` bigint NOT NULL,
  PRIMARY KEY (`category_id`, `brand_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pms_category_brand
-- ----------------------------

-- ----------------------------
-- Table structure for pms_sku
-- ----------------------------
DROP TABLE IF EXISTS `pms_sku`;
CREATE TABLE `pms_sku`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `sku_sn` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'SKU编码',
  `spu_id` bigint NOT NULL COMMENT 'SPU ID',
  `name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品名称',
  `price` bigint NULL DEFAULT NULL COMMENT '商品价格(单位：分)',
  `stock` int UNSIGNED NULL DEFAULT NULL COMMENT '库存数量',
  `locked_stock` int NULL DEFAULT NULL COMMENT '库存锁定数量',
  `img_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'SKU 图片URL',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint NULL DEFAULT NULL COMMENT '逻辑删除标识：0-未删除，1-已删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_pms_sku_pms_spu`(`spu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 755 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商品库存表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pms_sku
-- ----------------------------
INSERT INTO `pms_sku` VALUES (1, 'sn001', 1, '黑 6+128g', 399900, 990, 150, 'https://www.youlai.tech/files/default/c25b39470474494485633c49101a0f5d.png', '2021-08-08 00:43:26', '2022-07-03 14:16:16', 0);
INSERT INTO `pms_sku` VALUES (2, 'sn002', 1, '黑 8+256g', 499900, 999, 0, 'https://www.youlai.tech/files/default/c25b39470474494485633c49101a0f5d.png', '2021-08-08 00:43:26', '2022-07-03 14:16:16', 0);
INSERT INTO `pms_sku` VALUES (3, 'sn003', 1, '蓝 6+128g', 399900, 999, 0, 'https://www.youlai.tech/files/default/835d73a337964b9b97e5c7c90acc8cb2.png', '2022-03-05 09:25:53', '2022-07-03 14:16:16', 0);
INSERT INTO `pms_sku` VALUES (4, 'sn004', 1, '蓝 8+256g', 499900, 999, 0, 'https://www.youlai.tech/files/default/835d73a337964b9b97e5c7c90acc8cb2.png', '2022-03-05 09:25:53', '2022-07-03 14:16:16', 0);
INSERT INTO `pms_sku` VALUES (5, '10000001', 2, '魔幻青 RTX3060/i7-12700H/165Hz 2.5K屏', 1025000, 998, 0, 'http://a.youlai.tech:9000/default/8815c9a46fcc4b1ea952623406750da5.jpg', '2022-03-11 14:39:21', '2022-07-08 00:29:56', 0);
INSERT INTO `pms_sku` VALUES (6, '10000002', 2, '魔幻青 RTX3050tTi/12代i5/144Hz高色域屏', 925000, 999, 0, 'http://a.youlai.tech:9000/default/8815c9a46fcc4b1ea952623406750da5.jpg', '2022-03-11 14:39:21', '2022-07-08 00:29:56', 0);
INSERT INTO `pms_sku` VALUES (7, '10000003', 2, '日蚀灰 RTX3060/i7-12700H/165Hz 2.5K屏', 1025000, 999, 0, 'http://a.youlai.tech:9000/default/3210cd1ffb6c4346b743a10855d3cb37.jpg', '2022-03-11 14:39:21', '2022-07-08 00:29:56', 0);
INSERT INTO `pms_sku` VALUES (8, '10000004', 2, '日蚀灰 RTX3050tTi/12代i5/144Hz高色域屏', 925000, 999, 0, 'http://a.youlai.tech:9000/default/3210cd1ffb6c4346b743a10855d3cb37.jpg', '2022-03-11 14:39:21', '2022-07-08 00:29:56', 0);
INSERT INTO `pms_sku` VALUES (9, '111', 3, '16g 512g 【2022款】锐龙六核R5-6600U/核芯显卡/100%sRGB高色域', 589900, 992, 1, 'https://oss.youlai.tech/youlai-boot/2023/06/08/78b8efecb753426f81e5dcfcd175495f.jpg', '2022-07-07 00:22:13', '2022-07-08 00:29:41', 0);
INSERT INTO `pms_sku` VALUES (10, '112', 3, '16g 512g 【2022款】锐龙八核R7-6800U/核芯显卡/100%sRGB高色域', 629900, 999, 0, 'https://oss.youlai.tech/youlai-boot/2023/06/08/93cbc9dc6fe144f5a59793a6248479a0.jpg', '2022-07-07 00:22:13', '2022-07-08 00:29:41', 0);
INSERT INTO `pms_sku` VALUES (11, '113', 3, '16g 1t 【2022款】锐龙六核R5-6600U/核芯显卡/100%sRGB高色域', 639900, 999, 0, 'https://oss.youlai.tech/youlai-boot/2023/06/08/78b8efecb753426f81e5dcfcd175495f.jpg', '2022-07-07 00:22:13', '2022-07-08 00:29:41', 0);
INSERT INTO `pms_sku` VALUES (12, '114', 3, '16g 1t 【2022款】锐龙八核R7-6800U/核芯显卡/100%sRGB高色域', 639900, 999, 0, 'https://oss.youlai.tech/youlai-boot/2023/06/08/93cbc9dc6fe144f5a59793a6248479a0.jpg', '2022-07-07 00:22:13', '2022-07-08 00:29:41', 0);
INSERT INTO `pms_sku` VALUES (13, '115', 3, '32g 512g 【2022款】锐龙六核R5-6600U/核芯显卡/100%sRGB高色域', 589900, 999, 0, 'https://oss.youlai.tech/youlai-boot/2023/06/08/78b8efecb753426f81e5dcfcd175495f.jpg', '2022-07-07 00:22:13', '2022-07-08 00:29:41', 0);
INSERT INTO `pms_sku` VALUES (14, '116', 3, '32g 512g 【2022款】锐龙八核R7-6800U/核芯显卡/100%sRGB高色域', 629900, 999, 0, 'https://oss.youlai.tech/youlai-boot/2023/06/08/93cbc9dc6fe144f5a59793a6248479a0.jpg', '2022-07-07 00:22:13', '2022-07-08 00:29:41', 0);
INSERT INTO `pms_sku` VALUES (15, '117', 3, '32g 1t 【2022款】锐龙六核R5-6600U/核芯显卡/100%sRGB高色域', 639900, 999, 0, 'https://oss.youlai.tech/youlai-boot/2023/06/08/78b8efecb753426f81e5dcfcd175495f.jpg', '2022-07-07 00:22:13', '2022-07-08 00:29:41', 0);
INSERT INTO `pms_sku` VALUES (16, '118', 3, '32g 1t 【2022款】锐龙八核R7-6800U/核芯显卡/100%sRGB高色域', 639900, 999, 0, 'https://oss.youlai.tech/youlai-boot/2023/06/08/93cbc9dc6fe144f5a59793a6248479a0.jpg', '2022-07-07 00:22:13', '2022-07-08 00:29:41', 0);
INSERT INTO `pms_sku` VALUES (17, 'sn001', 4, '黑 6+128g', 399900, 999, 0, 'https://oss.youlai.tech/youlai-boot/2023/06/08/6b83dd33eaa248ed8e11cff0003287ee.jpg', '2021-08-08 00:43:26', '2022-07-03 14:16:16', 0);

-- ----------------------------
-- Table structure for pms_sku_spec_value
-- ----------------------------
DROP TABLE IF EXISTS `pms_sku_spec_value`;
CREATE TABLE `pms_sku_spec_value`  (
  `sku_id` bigint NOT NULL COMMENT 'SKU ID',
  `spec_value_id` bigint NOT NULL COMMENT '规格值 ID',
  PRIMARY KEY (`sku_id`, `spec_value_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pms_sku_spec_value
-- ----------------------------

-- ----------------------------
-- Table structure for pms_spu
-- ----------------------------
DROP TABLE IF EXISTS `pms_spu`;
CREATE TABLE `pms_spu`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品名称',
  `status` tinyint NULL DEFAULT 1 COMMENT '状态：1-在售， 0-下架',
  `category_id` bigint NOT NULL COMMENT '类型ID',
  `brand_id` bigint NULL DEFAULT NULL COMMENT '品牌ID',
  `img_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品主图',
  `unit` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '单位',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品简介',
  `detail` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '商品详情',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint NULL DEFAULT NULL COMMENT '逻辑删除标识：0-未删除，1-已删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_pms_spu_pms_brand`(`brand_id`) USING BTREE,
  INDEX `fk_pms_spu_pms_category`(`category_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 288 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商品表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pms_spu
-- ----------------------------
INSERT INTO `pms_spu` VALUES (1, 'Galaxy Z Fold5', 1, 5, 10, 'https://shop-image.samsung.com.cn/productv5/img/2023/07/26/64c0e30fe4b08db29258c50c.png', '台', '好快,好稳,\n好一次强上加强。\n高通全新一代芯片赋能，速度大幅提升。\n三大专业主摄影像加持，能力全面进化。\n大师级设计理念新诠释，质感简而不凡。\n斩获十五项纪录旗舰屏，感官万般出众。', '<p><img src=\"https://shop-image.samsung.com.cn/productv5/img/2023/11/03/65449d3ee4b08db20823bcbe.jpg\" alt=\"\" data-href=\"\" style=\"width: 449.00px;height: 449.00px;\"/></p>', NULL, '2022-07-03 14:16:16', 0);
INSERT INTO `pms_spu` VALUES (2, '华硕天选3', 1, 101, 11, 'https://www.youlai.tech/files/default/d97457b3fd7d4aef8846da96fe032bf8.jpg', NULL, '中国台湾华硕电脑股份有限公司 [1]  是当前全球第一大主板生产商、全球第三大显卡生产商，同时也是全球领先的3C解决方案提供商之一，致力于为个人和企业用户提供最具创新价值的产品及应用方案。华硕的产品线完整覆盖至笔记本电脑、主板、显卡、服务器、光存储、有线/无线网络通讯产品、LCD、掌上电脑、智能手机等全线3C产品。其中显卡和主板以及笔记本电脑三大产品已经成为华硕的主要竞争实力。', '<p><img src=\"http://a.youlai.tech:9000/default/5e4fb81b04244a74aacaabb4685101e2.png\" alt=\"\" data-href=\"\" style=\"\"/><img src=\"http://a.youlai.tech:9000/default/0744c5b6d77b47b294eb111ee992c62b.png\" alt=\"\" data-href=\"\" style=\"\"/></p>', '2022-03-11 14:39:21', '2022-07-08 00:29:56', 0);
INSERT INTO `pms_spu` VALUES (3, '惠普战X ', 1, 99, 33, 'https://oss.youlai.tech/default/e59859e0effb4b66a0f7380ff5369d66.jpg', NULL, '【2022新款】HP/惠普战X 16英寸锐龙新款6000系列R5六核/R7八核高性能学生家用轻薄办公商用笔记本电脑\n六核/八核处理器，高性能集成显卡', '<p><img src=\"https://oss.youlai.tech/default/d645a6f642794e2183cc44d340613b9d.jpg\" alt=\"\" data-href=\"\" style=\"\"/></p>', '2022-07-07 00:22:13', '2022-07-08 00:29:41', 0);
INSERT INTO `pms_spu` VALUES (4, '小米13', 1, 5, 10, 'https://oss.youlai.tech/youlai-boot/2023/06/08/6b83dd33eaa248ed8e11cff0003287ee.jpg', '台', '好快,好稳,\n好一次强上加强。\n高通全新一代芯片赋能，速度大幅提升。\n三大专业主摄影像加持，能力全面进化。\n大师级设计理念新诠释，质感简而不凡。\n斩获十五项纪录旗舰屏，感官万般出众。', '<p><img src=\"http://a.youlai.tech:9000/default/1a69357664c24962ac23953905c3c38f.png\" alt=\"\" data-href=\"\" style=\"width: 449.00px;height: 449.00px;\"/></p>', NULL, '2022-07-03 14:16:16', 0);

-- ----------------------------
-- Table structure for pms_spu_attribute
-- ----------------------------
DROP TABLE IF EXISTS `pms_spu_attribute`;
CREATE TABLE `pms_spu_attribute`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'SPU 属性ID',
  `spu_id` bigint NOT NULL COMMENT 'SPU ID',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '属性名称',
  `value` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '属性值',
  `sort` smallint NOT NULL DEFAULT 1 COMMENT '排序',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint NOT NULL DEFAULT 0 COMMENT '逻辑删除标识：0-未删除，1-已删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_pms_spu_attribute_pms_attr`(`name`) USING BTREE,
  INDEX `fk_pms_spu_attribute_pms_spu`(`spu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 847 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商品属性/规格表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pms_spu_attribute
-- ----------------------------
INSERT INTO `pms_spu_attribute` VALUES (1, 1, '颜色', '黑', 1, NULL, '2022-07-03 14:16:16', 0);
INSERT INTO `pms_spu_attribute` VALUES (3, 1, '规格', '6+128g', 1, NULL, '2022-07-03 14:16:16', 0);
INSERT INTO `pms_spu_attribute` VALUES (4, 1, '规格', '8+256g', 1, NULL, '2022-07-03 14:16:16', 0);
INSERT INTO `pms_spu_attribute` VALUES (5, 1, '上市时间', '2021-07-17', 1, NULL, '2022-07-03 14:16:16', 0);
INSERT INTO `pms_spu_attribute` VALUES (216, 1, '颜色', '蓝', 1, '2022-03-05 09:25:53', '2022-07-03 14:16:16', 0);
INSERT INTO `pms_spu_attribute` VALUES (251, 2, '上市时间', '2022/3/11', 1, '2022-03-11 14:39:21', '2022-07-08 00:29:56', 0);
INSERT INTO `pms_spu_attribute` VALUES (252, 2, '商品名称', '华硕天选3', 1, '2022-03-11 14:39:21', '2022-07-08 00:29:56', 0);
INSERT INTO `pms_spu_attribute` VALUES (253, 2, '商品编号', '100032610338', 1, '2022-03-11 14:39:21', '2022-07-08 00:29:56', 0);
INSERT INTO `pms_spu_attribute` VALUES (254, 2, '商品毛重', '4.05kg', 1, '2022-03-11 14:39:21', '2022-07-08 00:29:56', 0);
INSERT INTO `pms_spu_attribute` VALUES (255, 2, '系统', 'windows11', 1, '2022-03-11 14:39:21', '2022-07-08 00:29:56', 0);
INSERT INTO `pms_spu_attribute` VALUES (256, 2, '颜色', '魔幻青', 1, '2022-03-11 14:39:21', '2022-07-08 00:29:56', 0);
INSERT INTO `pms_spu_attribute` VALUES (257, 2, '颜色', '日蚀灰', 1, '2022-03-11 14:39:21', '2022-07-08 00:29:56', 0);
INSERT INTO `pms_spu_attribute` VALUES (258, 2, '规格', 'RTX3060/i7-12700H/165Hz 2.5K屏', 1, '2022-03-11 14:39:21', '2022-07-08 00:29:56', 0);
INSERT INTO `pms_spu_attribute` VALUES (259, 2, '规格', 'RTX3050tTi/12代i5/144Hz高色域屏', 1, '2022-03-11 14:39:21', '2022-07-08 00:29:56', 0);
INSERT INTO `pms_spu_attribute` VALUES (838, 3, '内存', '16g 32g', 1, '2022-07-07 00:22:13', '2022-07-08 00:29:41', 0);
INSERT INTO `pms_spu_attribute` VALUES (839, 3, '重量', '1.5kg(含)-2kg(不含)', 1, '2022-07-07 00:22:13', '2022-07-08 00:29:41', 0);
INSERT INTO `pms_spu_attribute` VALUES (840, 3, '显卡类型', '核芯显卡', 1, '2022-07-07 00:22:13', '2022-07-08 00:29:41', 0);
INSERT INTO `pms_spu_attribute` VALUES (841, 3, '内存容量', '16g', 1, '2022-07-07 00:22:13', '2022-07-08 00:29:41', 0);
INSERT INTO `pms_spu_attribute` VALUES (842, 3, '内存容量', '32g', 1, '2022-07-07 00:22:13', '2022-07-08 00:29:41', 0);
INSERT INTO `pms_spu_attribute` VALUES (843, 3, '硬盘容量', '512g', 1, '2022-07-07 00:22:13', '2022-07-08 00:29:41', 0);
INSERT INTO `pms_spu_attribute` VALUES (844, 3, '硬盘容量', '1t', 1, '2022-07-07 00:22:13', '2022-07-08 00:29:41', 0);
INSERT INTO `pms_spu_attribute` VALUES (845, 3, '套餐类型', '【2022款】锐龙六核R5-6600U/核芯显卡/100%sRGB高色域', 1, '2022-07-07 00:22:13', '2022-07-08 00:29:41', 0);
INSERT INTO `pms_spu_attribute` VALUES (846, 3, '套餐类型', '【2022款】锐龙八核R7-6800U/核芯显卡/100%sRGB高色域', 1, '2022-07-07 00:22:13', '2022-07-08 00:29:41', 0);

-- ----------------------------
-- Table structure for pms_spu_image
-- ----------------------------
DROP TABLE IF EXISTS `pms_spu_image`;
CREATE TABLE `pms_spu_image`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `spu_id` bigint NOT NULL COMMENT 'SPU ID',
  `img_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '商品图片URL',
  `sort` smallint NOT NULL DEFAULT 1 COMMENT '排序',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint NOT NULL DEFAULT 0 COMMENT '逻辑删除标识：0-未删除，1-已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pms_spu_image
-- ----------------------------

-- ----------------------------
-- Table structure for pms_spu_spec
-- ----------------------------
DROP TABLE IF EXISTS `pms_spu_spec`;
CREATE TABLE `pms_spu_spec`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '规格 ID',
  `spu_id` bigint NULL DEFAULT NULL COMMENT 'SPU ID',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '规格名称(如：颜色、尺寸)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pms_spu_spec
-- ----------------------------

-- ----------------------------
-- Table structure for pms_spu_spec_value
-- ----------------------------
DROP TABLE IF EXISTS `pms_spu_spec_value`;
CREATE TABLE `pms_spu_spec_value`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '规格值ID',
  `spec_id` bigint NOT NULL COMMENT '规格ID',
  `value` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '规格值(如：蓝色、白色)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pms_spu_spec_value
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

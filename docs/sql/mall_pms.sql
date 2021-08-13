/*
 Navicat Premium Data Transfer

 Source Server         : www.youlai.tech
 Source Server Type    : MySQL
 Source Server Version : 80023
 Source Host           : www.youlai.tech:3306
 Source Schema         : mall_pms

 Target Server Type    : MySQL
 Target Server Version : 80023
 File Encoding         : 65001

 Date: 13/08/2021 23:39:20
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for pms_attribute
-- ----------------------------
DROP TABLE IF EXISTS `pms_attribute`;
CREATE TABLE `pms_attribute`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `category_id` bigint NOT NULL COMMENT '分类ID',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '属性名称',
  `type` tinyint NOT NULL COMMENT '类型(1:规格;2:属性;)',
  `gmt_create` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_pms_attr_pms_category`(`category_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 37 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商品属性表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pms_attribute
-- ----------------------------
INSERT INTO `pms_attribute` VALUES (34, 5, '颜色', 1, '2021-07-11 17:57:06', '2021-07-11 18:00:06');
INSERT INTO `pms_attribute` VALUES (35, 5, '规格', 1, '2021-07-11 18:00:06', '2021-07-11 18:00:06');
INSERT INTO `pms_attribute` VALUES (36, 5, '上市时间', 2, '2021-07-11 18:00:08', '2021-07-11 18:00:08');

-- ----------------------------
-- Table structure for pms_brand
-- ----------------------------
DROP TABLE IF EXISTS `pms_brand`;
CREATE TABLE `pms_brand`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '品牌名称',
  `logo_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'LOGO图片',
  `sort` int NULL DEFAULT NULL COMMENT '排序',
  `gmt_create` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商品品牌表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pms_brand
-- ----------------------------
INSERT INTO `pms_brand` VALUES (1, '有来', 'http://a.youlai.tech:9000/default/73bc0adc892646d5b844f76619ec934e.jpg', 1, '2021-07-11 19:56:58', '2021-07-11 20:02:54');

-- ----------------------------
-- Table structure for pms_category
-- ----------------------------
DROP TABLE IF EXISTS `pms_category`;
CREATE TABLE `pms_category`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品分类名称',
  `parent_id` bigint NOT NULL COMMENT '父级ID',
  `level` int NULL DEFAULT NULL COMMENT '层级',
  `icon_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '图标地址',
  `sort` int NULL DEFAULT NULL COMMENT '排序',
  `visible` tinyint(1) NULL DEFAULT 1 COMMENT '显示状态:( 0:隐藏 1:显示)',
  `gmt_create` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商品分类表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pms_category
-- ----------------------------
INSERT INTO `pms_category` VALUES (3, '手机配件', 0, 1, NULL, 1, 1, NULL, NULL);
INSERT INTO `pms_category` VALUES (4, '智能手机', 3, 2, NULL, 1, 1, NULL, NULL);
INSERT INTO `pms_category` VALUES (5, '5g手机', 4, 3, 'http://a.youlai.tech:9000/default/040a0bd0586944959e11ef37e8d11a9f.jfif', 1, 1, NULL, '2021-08-08 13:28:42');

-- ----------------------------
-- Table structure for pms_catetgory_brand
-- ----------------------------
DROP TABLE IF EXISTS `pms_catetgory_brand`;
CREATE TABLE `pms_catetgory_brand`  (
  `category_id` bigint NOT NULL,
  `brand_id` bigint NOT NULL,
  PRIMARY KEY (`category_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pms_catetgory_brand
-- ----------------------------

-- ----------------------------
-- Table structure for pms_sku
-- ----------------------------
DROP TABLE IF EXISTS `pms_sku`;
CREATE TABLE `pms_sku`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `sn` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品编码',
  `spu_id` bigint NOT NULL COMMENT '产品ID',
  `name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品标题',
  `spec_ids` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品规格值，以,分割',
  `price` bigint NULL DEFAULT NULL COMMENT '价格（单位：分）',
  `stock` int NULL DEFAULT 0 COMMENT '库存',
  `locked_stock` int NULL DEFAULT 0 COMMENT '锁定库存',
  `pic_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品图片',
  `gmt_create` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_pms_sku_pms_spu`(`spu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 312 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商品库存表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pms_sku
-- ----------------------------
INSERT INTO `pms_sku` VALUES (291, '1', 1, NULL, '1_3', 100, 1, 0, 'http://a.youlai.tech:9000/default/fb3f1be8aae644f497255f29aa51c641.jpg', '2021-08-08 00:43:26', '2021-08-08 00:56:24');
INSERT INTO `pms_sku` VALUES (292, '2', 1, NULL, '1_4', 200, 2, 0, 'http://a.youlai.tech:9000/default/fb3f1be8aae644f497255f29aa51c641.jpg', '2021-08-08 00:43:26', '2021-08-08 00:56:24');
INSERT INTO `pms_sku` VALUES (293, '3', 1, NULL, '2_3', 300, 3, 0, 'http://a.youlai.tech:9000/default/074dfb3841444a10a7691eac322bb848.jpg', '2021-08-08 00:43:26', '2021-08-08 00:56:25');
INSERT INTO `pms_sku` VALUES (294, '4', 1, NULL, '2_4', 400, 4, 0, 'http://a.youlai.tech:9000/default/074dfb3841444a10a7691eac322bb848.jpg', '2021-08-08 00:43:26', '2021-08-08 00:56:25');
INSERT INTO `pms_sku` VALUES (295, '213', 72, NULL, '114_115', 12300, 123, 0, NULL, '2021-08-08 08:30:39', '2021-08-08 08:30:39');
INSERT INTO `pms_sku` VALUES (296, '1213', 73, '117|11', '117_119', 100, 12, 0, NULL, '2021-08-08 11:28:50', '2021-08-08 11:29:49');
INSERT INTO `pms_sku` VALUES (297, '2123', 73, '117|12', '117_120', 200, 123, 0, NULL, '2021-08-08 11:28:50', '2021-08-08 11:29:49');
INSERT INTO `pms_sku` VALUES (298, '3123', 73, '118|11', '118_119', 300, 123, 0, NULL, '2021-08-08 11:28:50', '2021-08-08 11:29:49');
INSERT INTO `pms_sku` VALUES (299, '41244', 73, '118|12', '118_120', 400, 123, 0, NULL, '2021-08-08 11:28:50', '2021-08-08 11:29:49');
INSERT INTO `pms_sku` VALUES (300, '1', 74, 'tid_1_1|tid_2_1|tid_3_', '122_124_126', 100, 98, 17, 'http://a.youlai.tech:9000/default/852f076dd41d4e199c969289838149fa.jpg', '2021-08-09 22:24:46', '2021-08-09 22:24:46');
INSERT INTO `pms_sku` VALUES (301, '2', 74, 'tid_1_1|tid_2_1|tid_3_', '122_124_127', 200, 2, 0, 'http://a.youlai.tech:9000/default/852f076dd41d4e199c969289838149fa.jpg', '2021-08-09 22:24:46', '2021-08-09 22:24:46');
INSERT INTO `pms_sku` VALUES (302, '3', 74, 'tid_1_1|tid_2_2|tid_3_', '122_125_126', 300, 3, 0, 'http://a.youlai.tech:9000/default/852f076dd41d4e199c969289838149fa.jpg', '2021-08-09 22:24:46', '2021-08-09 22:24:46');
INSERT INTO `pms_sku` VALUES (303, '4', 74, 'tid_1_1|tid_2_2|tid_3_', '122_125_127', 400, 4, 0, 'http://a.youlai.tech:9000/default/852f076dd41d4e199c969289838149fa.jpg', '2021-08-09 22:24:46', '2021-08-09 22:24:46');
INSERT INTO `pms_sku` VALUES (304, '5', 74, 'tid_1_2|tid_2_1|tid_3_', '123_124_126', 500, 5, 0, 'http://a.youlai.tech:9000/default/5a57b78ec1bf4bfeb20f7a99907cfaf8.jpg', '2021-08-09 22:24:46', '2021-08-09 22:24:46');
INSERT INTO `pms_sku` VALUES (305, '6', 74, 'tid_1_2|tid_2_1|tid_3_', '123_124_127', 600, 6, 0, 'http://a.youlai.tech:9000/default/5a57b78ec1bf4bfeb20f7a99907cfaf8.jpg', '2021-08-09 22:24:46', '2021-08-09 22:24:46');
INSERT INTO `pms_sku` VALUES (306, '7', 74, 'tid_1_2|tid_2_2|tid_3_', '123_125_126', 700, 7, 0, 'http://a.youlai.tech:9000/default/5a57b78ec1bf4bfeb20f7a99907cfaf8.jpg', '2021-08-09 22:24:46', '2021-08-09 22:24:46');
INSERT INTO `pms_sku` VALUES (307, '8', 74, 'tid_1_2|tid_2_2|tid_3_', '123_125_127', 800, 8, 0, 'http://a.youlai.tech:9000/default/5a57b78ec1bf4bfeb20f7a99907cfaf8.jpg', '2021-08-09 22:24:46', '2021-08-09 22:24:46');
INSERT INTO `pms_sku` VALUES (308, '1', 75, '123 1233 ', '129_131', 100, 1, 0, NULL, '2021-08-09 22:40:20', '2021-08-09 22:40:20');
INSERT INTO `pms_sku` VALUES (309, '2', 75, '123 4+64G ', '129_132', 200, 2, 0, NULL, '2021-08-09 22:40:20', '2021-08-09 22:40:20');
INSERT INTO `pms_sku` VALUES (310, '3', 75, '黑 1233 ', '130_131', 300, 3, 0, NULL, '2021-08-09 22:40:20', '2021-08-09 22:40:20');
INSERT INTO `pms_sku` VALUES (311, '4', 75, '黑 4+64G ', '130_132', 400, 4, 0, NULL, '2021-08-09 22:40:20', '2021-08-09 22:40:20');

-- ----------------------------
-- Table structure for pms_spu
-- ----------------------------
DROP TABLE IF EXISTS `pms_spu`;
CREATE TABLE `pms_spu`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品名称',
  `category_id` bigint NOT NULL COMMENT '商品类型ID',
  `brand_id` bigint NULL DEFAULT NULL COMMENT '商品品牌ID',
  `origin_price` bigint NOT NULL COMMENT '原价【起】',
  `price` bigint NOT NULL COMMENT '现价【起】',
  `sales` int NULL DEFAULT 0 COMMENT '销量',
  `pic_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品主图',
  `album` json NULL COMMENT '商品图册',
  `unit` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '单位',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品简介',
  `detail` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '商品详情',
  `status` tinyint NULL DEFAULT NULL COMMENT '商品状态：0-下架 1-上架',
  `gmt_create` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_pms_spu_pms_brand`(`brand_id`) USING BTREE,
  INDEX `fk_pms_spu_pms_category`(`category_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 76 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商品表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pms_spu
-- ----------------------------
INSERT INTO `pms_spu` VALUES (1, '小米10', 5, 1, 599900, 399900, 1, 'http://a.youlai.tech:9000/default/063350d473a64ee7857e91841add1177.jpg', '[\"https://gitee.com/haoxr/image/raw/master/default/image-20210702002909113.png\", \"https://gitee.com/haoxr/image/raw/master/default/image-20210702002909113.png\"]', '台', '商品简介', '商品详情', 1, NULL, '2021-08-08 00:56:25');
INSERT INTO `pms_spu` VALUES (72, '手机2号', 5, 1, 100, 200, 0, 'http://a.youlai.tech:9000/default/26afc0d59f094c6d9dff8c2cf8ac1863.jfif', NULL, NULL, '123', NULL, NULL, '2021-08-08 08:30:38', '2021-08-08 08:30:38');
INSERT INTO `pms_spu` VALUES (73, '123', 5, 1, 12300, 12300, 0, 'http://a.youlai.tech:9000/default/f76fa430756749bab698d3797ca8da04.jfif', '[\"http://a.youlai.tech:9000/default/6813cdaa196d47e8b09436db12323a7f.jpg\"]', NULL, '123', '<p>123</p>', NULL, '2021-08-08 11:28:50', '2021-08-08 11:29:49');
INSERT INTO `pms_spu` VALUES (74, '123', 5, 1, 12300, 12300, 0, 'http://a.youlai.tech:9000/default/aae526c28c114561b249add9e34922f8.jfif', '[\"http://a.youlai.tech:9000/default/2e317170b2c241c4b4a8d86fe0926f8c.jfif\"]', NULL, '123', '', NULL, '2021-08-09 22:24:46', '2021-08-09 22:24:46');
INSERT INTO `pms_spu` VALUES (75, '1', 5, 1, 21300, 12300, 0, 'http://a.youlai.tech:9000/default/1322f05c8ca240e6ada2255c1903e66d.jfif', '[\"http://a.youlai.tech:9000/default/de015e8674e6408a87b01516a746437a.jfif\"]', NULL, '123', NULL, NULL, '2021-08-09 22:40:20', '2021-08-09 22:40:20');

-- ----------------------------
-- Table structure for pms_spu_attribute_value
-- ----------------------------
DROP TABLE IF EXISTS `pms_spu_attribute_value`;
CREATE TABLE `pms_spu_attribute_value`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `spu_id` bigint NOT NULL COMMENT '产品ID',
  `attribute_id` bigint NULL DEFAULT NULL COMMENT '属性ID',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '属性名称',
  `value` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '属性值',
  `type` tinyint NOT NULL COMMENT '类型(1:规格;2:属性;)',
  `pic_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '规格图片',
  `gmt_create` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_pms_spu_attribute_pms_attr`(`name`) USING BTREE,
  INDEX `fk_pms_spu_attribute_pms_spu`(`spu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 133 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商品属性项表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pms_spu_attribute_value
-- ----------------------------
INSERT INTO `pms_spu_attribute_value` VALUES (1, 1, 34, '颜色', '黑', 1, 'http://a.youlai.tech:9000/default/fb3f1be8aae644f497255f29aa51c641.jpg', NULL, '2021-08-08 00:56:24');
INSERT INTO `pms_spu_attribute_value` VALUES (2, 1, 34, '颜色', '白', 1, 'http://a.youlai.tech:9000/default/074dfb3841444a10a7691eac322bb848.jpg', NULL, '2021-08-08 00:56:24');
INSERT INTO `pms_spu_attribute_value` VALUES (3, 1, 35, '规格', '6+128g', 1, NULL, NULL, '2021-08-08 00:56:24');
INSERT INTO `pms_spu_attribute_value` VALUES (4, 1, 35, '规格', '8+256g', 1, NULL, NULL, '2021-08-08 00:56:24');
INSERT INTO `pms_spu_attribute_value` VALUES (5, 1, 36, '上市时间', '2021-07-17', 2, NULL, NULL, '2021-08-08 00:56:24');
INSERT INTO `pms_spu_attribute_value` VALUES (95, 68, NULL, '上市时间', '123', 2, NULL, '2021-08-07 23:38:24', '2021-08-07 23:38:24');
INSERT INTO `pms_spu_attribute_value` VALUES (96, 68, NULL, '颜色', '123', 1, NULL, '2021-08-07 23:38:50', '2021-08-07 23:38:50');
INSERT INTO `pms_spu_attribute_value` VALUES (97, 68, NULL, '颜色', '4123', 1, NULL, '2021-08-07 23:38:50', '2021-08-07 23:38:50');
INSERT INTO `pms_spu_attribute_value` VALUES (98, 68, NULL, '规格', '456', 1, NULL, '2021-08-07 23:38:50', '2021-08-07 23:38:50');
INSERT INTO `pms_spu_attribute_value` VALUES (99, 68, NULL, '规格', '123', 1, NULL, '2021-08-07 23:38:50', '2021-08-07 23:38:50');
INSERT INTO `pms_spu_attribute_value` VALUES (100, 69, NULL, '上市时间', '123', 2, NULL, '2021-08-07 23:41:44', '2021-08-07 23:41:44');
INSERT INTO `pms_spu_attribute_value` VALUES (101, 69, NULL, '颜色', '123', 1, NULL, '2021-08-07 23:41:44', '2021-08-07 23:41:44');
INSERT INTO `pms_spu_attribute_value` VALUES (102, 69, NULL, '颜色', '4123', 1, NULL, '2021-08-07 23:41:44', '2021-08-07 23:41:44');
INSERT INTO `pms_spu_attribute_value` VALUES (103, 69, NULL, '规格', '456', 1, NULL, '2021-08-07 23:41:44', '2021-08-07 23:41:44');
INSERT INTO `pms_spu_attribute_value` VALUES (104, 69, NULL, '规格', '123', 1, NULL, '2021-08-07 23:41:45', '2021-08-07 23:41:45');
INSERT INTO `pms_spu_attribute_value` VALUES (113, 72, NULL, '上市时间', '12313', 2, NULL, '2021-08-08 08:30:38', '2021-08-08 08:30:38');
INSERT INTO `pms_spu_attribute_value` VALUES (114, 72, NULL, '颜色', '123', 1, 'http://a.youlai.tech:9000/default/afdbd92d2f4f467ab87379cc1efcb504.jpg', '2021-08-08 08:30:38', '2021-08-08 08:30:38');
INSERT INTO `pms_spu_attribute_value` VALUES (115, 72, NULL, '规格', '123', 1, NULL, '2021-08-08 08:30:38', '2021-08-08 08:30:38');
INSERT INTO `pms_spu_attribute_value` VALUES (116, 73, NULL, '上市时间', '123', 2, NULL, '2021-08-08 11:28:50', '2021-08-08 11:29:49');
INSERT INTO `pms_spu_attribute_value` VALUES (117, 73, NULL, '颜色', '白', 1, NULL, '2021-08-08 11:28:50', '2021-08-08 11:29:49');
INSERT INTO `pms_spu_attribute_value` VALUES (118, 73, NULL, '颜色', '黑', 1, NULL, '2021-08-08 11:28:50', '2021-08-08 11:29:49');
INSERT INTO `pms_spu_attribute_value` VALUES (119, 73, NULL, '规格', '搜索', 1, NULL, '2021-08-08 11:28:50', '2021-08-08 11:29:49');
INSERT INTO `pms_spu_attribute_value` VALUES (120, 73, NULL, '规格', '水电费', 1, NULL, '2021-08-08 11:28:50', '2021-08-08 11:29:49');
INSERT INTO `pms_spu_attribute_value` VALUES (121, 74, NULL, '上市时间', '112', 2, NULL, '2021-08-09 22:24:46', '2021-08-09 22:24:46');
INSERT INTO `pms_spu_attribute_value` VALUES (122, 74, NULL, '颜色', '123', 1, 'http://a.youlai.tech:9000/default/852f076dd41d4e199c969289838149fa.jpg', '2021-08-09 22:24:46', '2021-08-09 22:24:46');
INSERT INTO `pms_spu_attribute_value` VALUES (123, 74, NULL, '颜色', '132', 1, 'http://a.youlai.tech:9000/default/5a57b78ec1bf4bfeb20f7a99907cfaf8.jpg', '2021-08-09 22:24:46', '2021-08-09 22:24:46');
INSERT INTO `pms_spu_attribute_value` VALUES (124, 74, NULL, '规格', 'sdf', 1, NULL, '2021-08-09 22:24:46', '2021-08-09 22:24:46');
INSERT INTO `pms_spu_attribute_value` VALUES (125, 74, NULL, '规格', '123', 1, NULL, '2021-08-09 22:24:46', '2021-08-09 22:24:46');
INSERT INTO `pms_spu_attribute_value` VALUES (126, 74, NULL, '1223123', '123', 1, NULL, '2021-08-09 22:24:46', '2021-08-09 22:24:46');
INSERT INTO `pms_spu_attribute_value` VALUES (127, 74, NULL, '1223123', '123444', 1, NULL, '2021-08-09 22:24:46', '2021-08-09 22:24:46');
INSERT INTO `pms_spu_attribute_value` VALUES (128, 75, NULL, '上市时间', '123', 2, NULL, '2021-08-09 22:40:20', '2021-08-09 22:40:20');
INSERT INTO `pms_spu_attribute_value` VALUES (129, 75, NULL, '颜色', '123', 1, NULL, '2021-08-09 22:40:20', '2021-08-09 22:40:20');
INSERT INTO `pms_spu_attribute_value` VALUES (130, 75, NULL, '颜色', '黑', 1, NULL, '2021-08-09 22:40:20', '2021-08-09 22:40:20');
INSERT INTO `pms_spu_attribute_value` VALUES (131, 75, NULL, '规格', '1233', 1, NULL, '2021-08-09 22:40:20', '2021-08-09 22:40:20');
INSERT INTO `pms_spu_attribute_value` VALUES (132, 75, NULL, '规格', '4+64G', 1, NULL, '2021-08-09 22:40:20', '2021-08-09 22:40:20');

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

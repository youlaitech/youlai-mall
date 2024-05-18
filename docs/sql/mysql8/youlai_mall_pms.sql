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

 Date: 18/05/2024 09:42:13
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for pms_attribute
-- ----------------------------
DROP TABLE IF EXISTS `pms_attribute`;
CREATE TABLE `pms_attribute`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '属性名称',
  `attribute_group_id` bigint NULL DEFAULT NULL COMMENT '属性组ID',
  `type` tinyint NOT NULL COMMENT '属性类型（1：基础属性，2：销售属性）',
  `input_method` tinyint NOT NULL COMMENT '输入方式（1：手动输入，2：从列表选择）',
  `selectable_values` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '可选值列表（以逗号分隔，仅当输入方式为2时使用）',
  `category_id` bigint NOT NULL COMMENT '分类ID',
  `sort` smallint NULL DEFAULT NULL COMMENT '排序',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint NOT NULL DEFAULT 0 COMMENT '逻辑删除标识（0-未删除，1-已删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pms_attribute
-- ----------------------------
INSERT INTO `pms_attribute` VALUES (1, '屏幕分辨率', 1, 1, 2, '1080P,2K,4K', 3, NULL, '2024-05-17 14:32:15', '2024-05-17 14:32:15', 0);
INSERT INTO `pms_attribute` VALUES (2, '屏幕尺寸', 1, 1, 1, NULL, 3, NULL, '2024-05-17 14:32:15', '2024-05-17 14:32:15', 0);
INSERT INTO `pms_attribute` VALUES (3, '屏幕材料', 1, 1, 1, 'LCD,AMOLED', 3, NULL, '2024-05-17 14:32:15', '2024-05-17 14:32:15', 0);
INSERT INTO `pms_attribute` VALUES (4, '摄像头像素', 2, 1, 1, NULL, 3, NULL, '2024-05-17 14:32:15', '2024-05-17 14:32:15', 0);
INSERT INTO `pms_attribute` VALUES (5, '指纹类型', 2, 1, 2, '屏下指纹,屏后指纹', 3, NULL, '2024-05-17 14:32:15', '2024-05-17 14:32:15', 0);
INSERT INTO `pms_attribute` VALUES (6, '刷新率', 2, 1, 2, '90HZ,120HZ', 3, NULL, '2024-05-17 14:32:15', '2024-05-17 14:32:15', 0);
INSERT INTO `pms_attribute` VALUES (7, '颜色', 3, 2, 1, NULL, 3, NULL, '2024-05-17 14:32:15', '2024-05-17 14:32:15', 0);
INSERT INTO `pms_attribute` VALUES (8, '存储容量', 4, 2, 2, '8+256G,12+512G,16+1T', 3, NULL, '2024-05-17 14:32:15', '2024-05-17 14:32:15', 0);

-- ----------------------------
-- Table structure for pms_attribute_group
-- ----------------------------
DROP TABLE IF EXISTS `pms_attribute_group`;
CREATE TABLE `pms_attribute_group`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '属性组ID',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '属性组名称',
  `category_id` bigint NULL DEFAULT NULL COMMENT '商品分类ID',
  `sort` smallint NOT NULL DEFAULT 1 COMMENT '排序',
  `remark` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint NOT NULL DEFAULT 0 COMMENT '逻辑删除标识[0-未删除，1-已删除]',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pms_attribute_group
-- ----------------------------
INSERT INTO `pms_attribute_group` VALUES (1, '屏幕属性', 3, 1, NULL, NULL, NULL, 0);
INSERT INTO `pms_attribute_group` VALUES (2, '功能属性', 3, 2, NULL, NULL, NULL, 0);
INSERT INTO `pms_attribute_group` VALUES (3, '外观属性', 3, 1, NULL, NULL, NULL, 0);
INSERT INTO `pms_attribute_group` VALUES (4, '性能属性', 3, 2, NULL, NULL, NULL, 0);

-- ----------------------------
-- Table structure for pms_brand
-- ----------------------------
DROP TABLE IF EXISTS `pms_brand`;
CREATE TABLE `pms_brand`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '品牌ID',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '品牌名称',
  `first_letter` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '检索首字母',
  `is_visible` tinyint NULL DEFAULT NULL COMMENT '是否显示[0-隐藏，1-显示]',
  `sort` smallint NULL DEFAULT 1 COMMENT '排序',
  `logo_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'Logo URL',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '品牌描述',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint NOT NULL DEFAULT 0 COMMENT '逻辑删除标识[0-未删除，1-已删除]',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 34 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商品品牌表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pms_brand
-- ----------------------------
INSERT INTO `pms_brand` VALUES (1, '有来', 'Y', NULL, 1, 'http://a.youlai.tech:9000/default/5409e3deb5a14b8fa8cb4275dee0e25d.png', NULL, '2021-07-11 19:56:58', '2021-07-11 20:02:54', 0);
INSERT INTO `pms_brand` VALUES (10, '小米', 'X', NULL, 2, 'http://a.youlai.tech:9000/default/6a5a606fc60742919149a7861bf26cd5.jpg', NULL, '2022-03-05 16:12:16', '2022-03-05 16:12:16', 0);
INSERT INTO `pms_brand` VALUES (11, '华硕', 'H', NULL, 3, 'http://a.youlai.tech:9000/default/f18083f95e104a0bae3c587dee3bb2ed.png', NULL, '2022-03-05 16:12:16', '2022-03-05 16:12:16', 0);
INSERT INTO `pms_brand` VALUES (20, '华为', 'H', NULL, 1, 'https://oss.youlai.tech/default/ff61bd639b23491d8f2aa85d09fcf788.jpg', NULL, '2022-05-06 23:08:33', '2022-05-06 23:08:33', 0);
INSERT INTO `pms_brand` VALUES (33, '惠普', 'H', NULL, 1, 'https://oss.youlai.tech/default/4cf579add9544c6eaafb41ce1131559e.gif', NULL, '2022-07-07 00:12:16', '2022-07-07 00:12:16', 0);

-- ----------------------------
-- Table structure for pms_brand_category
-- ----------------------------
DROP TABLE IF EXISTS `pms_brand_category`;
CREATE TABLE `pms_brand_category`  (
  `brand_id` bigint NOT NULL COMMENT '品牌ID',
  `category_id` bigint NOT NULL COMMENT '分类ID'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '品牌分类关联' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pms_brand_category
-- ----------------------------

-- ----------------------------
-- Table structure for pms_category
-- ----------------------------
DROP TABLE IF EXISTS `pms_category`;
CREATE TABLE `pms_category`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '分类ID',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '分类名称',
  `parent_id` bigint NOT NULL COMMENT '父级ID',
  `tree_path` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '层级路径',
  `icon_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '图标地址',
  `sort` int NULL DEFAULT 1 COMMENT '排序',
  `is_visible` tinyint(1) NULL DEFAULT 1 COMMENT '是否显示[1-显示，0-隐藏]',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint NOT NULL DEFAULT 0 COMMENT '逻辑删除标识[0-未删除，1-已删除]',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 102 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商品分类表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pms_category
-- ----------------------------
INSERT INTO `pms_category` VALUES (1, '手机配件', 0, NULL, NULL, 1, 1, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (2, '智能手机', 1, NULL, NULL, 1, 1, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (3, '5g手机', 2, NULL, 'https://oss.youlai.tech/default/6ffb37110ac2434a9882b9e8968b2887.jpg', 1, 1, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (10, '电脑办公', 0, NULL, 'https://www.youlai.tech/files/default/776c21c1a71848069093033f461c5f4a.jpg', 2, 1, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (97, '笔记本电脑', 6, NULL, NULL, 1, 1, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (99, '轻薄本', 97, NULL, 'https://oss.youlai.tech/default/2f849b96ebb54ab3a94b1b90137f1b4d.png', 1, 1, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (100, '全能本', 97, NULL, 'https://oss.youlai.tech/default/37cc080ec61b4ce7b0583b002568ebaa.png', 2, 1, NULL, NULL, 0);
INSERT INTO `pms_category` VALUES (101, '游戏本', 97, NULL, 'https://oss.youlai.tech/default/5c1a2d5427534b48bc382caa55197f11.png', 3, 1, NULL, NULL, 0);

-- ----------------------------
-- Table structure for pms_sku
-- ----------------------------
DROP TABLE IF EXISTS `pms_sku`;
CREATE TABLE `pms_sku`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'SKU 主键',
  `sku_sn` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'SKU 编码',
  `spu_id` bigint NOT NULL COMMENT 'SPU ID',
  `name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品名称',
  `price` bigint NOT NULL COMMENT '商品价格(单位：分)',
  `stock` int UNSIGNED NOT NULL COMMENT '库存数量',
  `locked_stock` int NULL DEFAULT 0 COMMENT '库存锁定数量',
  `img_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'SKU 图片URL',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint NOT NULL DEFAULT 0 COMMENT '逻辑删除标识[0-未删除，1-已删除]',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_pms_sku_pms_spu`(`spu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 759 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商品库存表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pms_sku
-- ----------------------------
INSERT INTO `pms_sku` VALUES (1, 'sn001', 1, '白色 8+256G', 699900, 100, 0, 'https://www.youlai.tech/files/default/c25b39470474494485633c49101a0f5d.png', '2021-08-08 00:43:26', '2024-05-08 10:51:34', 0);
INSERT INTO `pms_sku` VALUES (2, 'sn002', 1, '黑色 8+256G', 699900, 100, 0, 'https://www.youlai.tech/files/default/c25b39470474494485633c49101a0f5d.png', '2024-05-08 10:54:43', '2024-05-08 10:54:43', 0);
INSERT INTO `pms_sku` VALUES (3, 'sn003', 1, '白色 12+512G', 799900, 100, 0, 'https://www.youlai.tech/files/default/c25b39470474494485633c49101a0f5d.png', '2021-08-08 00:43:26', '2024-05-08 10:51:34', 0);
INSERT INTO `pms_sku` VALUES (4, 'sn004', 1, '黑色 12+512G', 799900, 100, 0, 'https://www.youlai.tech/files/default/c25b39470474494485633c49101a0f5d.png', '2021-08-08 00:43:26', '2024-05-08 10:51:34', 0);

-- ----------------------------
-- Table structure for pms_sku_attribute_value
-- ----------------------------
DROP TABLE IF EXISTS `pms_sku_attribute_value`;
CREATE TABLE `pms_sku_attribute_value`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '销售属性ID',
  `sku_id` bigint NULL DEFAULT NULL COMMENT 'SKU ID',
  `attribute_id` bigint NULL DEFAULT NULL COMMENT '属性 ID',
  `attribute_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '属性名称',
  `attribute_value` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '属性值',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pms_sku_attribute_value
-- ----------------------------
INSERT INTO `pms_sku_attribute_value` VALUES (1, 1, 7, '颜色', '白色');
INSERT INTO `pms_sku_attribute_value` VALUES (2, 1, 8, '存储容量', '8+256G');
INSERT INTO `pms_sku_attribute_value` VALUES (3, 2, 7, '颜色', '黑色');
INSERT INTO `pms_sku_attribute_value` VALUES (4, 2, 8, '存储容量', '8+256G');
INSERT INTO `pms_sku_attribute_value` VALUES (5, 3, 7, '颜色', '白色');
INSERT INTO `pms_sku_attribute_value` VALUES (6, 3, 8, '存储容量', '12+512G');
INSERT INTO `pms_sku_attribute_value` VALUES (7, 4, 7, '颜色', '黑色');
INSERT INTO `pms_sku_attribute_value` VALUES (8, 4, 8, '存储容量', '12+512G');

-- ----------------------------
-- Table structure for pms_spu
-- ----------------------------
DROP TABLE IF EXISTS `pms_spu`;
CREATE TABLE `pms_spu`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品名称',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态[0-未上架，1-已上架]',
  `category_id` bigint NOT NULL COMMENT '类型ID',
  `brand_id` bigint NULL DEFAULT NULL COMMENT '品牌ID',
  `img_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品主图',
  `unit` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '单位',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品简介',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint NOT NULL DEFAULT 0 COMMENT '逻辑删除[0-未删除，1-已删除]',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_pms_spu_pms_brand`(`brand_id`) USING BTREE,
  INDEX `fk_pms_spu_pms_category`(`category_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 291 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商品表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pms_spu
-- ----------------------------
INSERT INTO `pms_spu` VALUES (1, '小米14', 1, 3, 10, 'https://oss.youlai.tech/youlai-boot/2023/06/08/6b83dd33eaa248ed8e11cff0003287ee.jpg', '台', '好快,好稳,\n好一次强上加强。\n高通全新一代芯片赋能，速度大幅提升。\n三大专业主摄影像加持，能力全面进化。\n大师级设计理念新诠释，质感简而不凡。\n斩获十五项纪录旗舰屏，感官万般出众。', '2024-05-17 13:38:53', '2022-07-03 14:16:16', 0);

-- ----------------------------
-- Table structure for pms_spu_attribute_value
-- ----------------------------
DROP TABLE IF EXISTS `pms_spu_attribute_value`;
CREATE TABLE `pms_spu_attribute_value`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '基础属性ID',
  `spu_id` bigint NOT NULL COMMENT 'SPU ID',
  `attribute_id` bigint NULL DEFAULT NULL COMMENT '属性ID',
  `attribute_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '属性名称',
  `attribute_value` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '属性值',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_pms_spu_attribute_pms_attr`(`attribute_name`) USING BTREE,
  INDEX `fk_pms_spu_attribute_pms_spu`(`spu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 851 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商品属性/规格表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pms_spu_attribute_value
-- ----------------------------
INSERT INTO `pms_spu_attribute_value` VALUES (1, 1, 1, '屏幕分辨率', '2K');
INSERT INTO `pms_spu_attribute_value` VALUES (2, 1, 2, '屏幕尺寸', '6英寸');
INSERT INTO `pms_spu_attribute_value` VALUES (3, 1, 3, '屏幕材料', '123');
INSERT INTO `pms_spu_attribute_value` VALUES (4, 1, 4, '摄像头像素', '5000万');
INSERT INTO `pms_spu_attribute_value` VALUES (5, 1, 5, '指纹类型', '屏下指纹');
INSERT INTO `pms_spu_attribute_value` VALUES (6, 1, 6, '刷新率', '150HZ');

-- ----------------------------
-- Table structure for pms_spu_detail
-- ----------------------------
DROP TABLE IF EXISTS `pms_spu_detail`;
CREATE TABLE `pms_spu_detail`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `spu_id` bigint NOT NULL COMMENT 'SPU ID',
  `detail` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '详情',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint NOT NULL DEFAULT 0 COMMENT '逻辑删除[0-未删除，1-已删除]',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '商品详情' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pms_spu_detail
-- ----------------------------
INSERT INTO `pms_spu_detail` VALUES (1, 1, '<p><img src=\"http://a.youlai.tech:9000/default/1a69357664c24962ac23953905c3c38f.png\" alt=\"\" data-href=\"\" style=\"width: 449.00px;height: 449.00px;\"/></p>', '2024-05-07 16:18:35', '2024-05-07 16:18:39', 0);
INSERT INTO `pms_spu_detail` VALUES (2, 290, '<p>Apple iPhone 12 64GB 绿色</p>', '2024-05-08 16:22:55', '2024-05-08 16:22:55', 0);

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
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pms_spu_image
-- ----------------------------
INSERT INTO `pms_spu_image` VALUES (1, 288, 'https://youlai-mall.oss-cn-shenzhen.aliyuncs.com/images/2021/04/23/1619163660000.jpg', 1, '2024-05-07 17:35:42', '2024-05-07 17:35:42', 1);
INSERT INTO `pms_spu_image` VALUES (2, 289, 'https://youlai-mall.oss-cn-shenzhen.aliyuncs.com/images/2021/04/23/1619163660000.jpg', 1, '2024-05-08 10:25:53', '2024-05-08 10:25:53', 0);
INSERT INTO `pms_spu_image` VALUES (3, 288, 'https://youlai-mall.oss-cn-shenzhen.aliyuncs.com/images/2021/04/23/1619163660000.jpg', 1, '2024-05-08 10:51:34', '2024-05-08 10:51:34', 1);
INSERT INTO `pms_spu_image` VALUES (4, 288, 'https://youlai-mall.oss-cn-shenzhen.aliyuncs.com/images/2021/04/23/1619163660000.jpg', 1, '2024-05-08 10:54:43', '2024-05-08 10:54:43', 0);
INSERT INTO `pms_spu_image` VALUES (5, 290, 'https://youlai-mall.oss-cn-shenzhen.aliyuncs.com/images/2021/04/23/1619163660000.jpg', 1, '2024-05-08 10:58:19', '2024-05-08 10:58:19', 1);
INSERT INTO `pms_spu_image` VALUES (6, 290, 'https://youlai-mall.oss-cn-shenzhen.aliyuncs.com/images/2021/04/23/1619163660000.jpg', 1, '2024-05-08 16:22:56', '2024-05-08 16:22:56', 0);

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

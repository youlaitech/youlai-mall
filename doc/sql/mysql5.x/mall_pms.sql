/*
* 商城商品表
* MySQL5.x版本
*/
use mall_pms;

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for pms_attribute
-- ----------------------------
DROP TABLE IF EXISTS `pms_attribute`;
CREATE TABLE `pms_attribute`  (
                                  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                                  `category_id` bigint NOT NULL COMMENT '分类ID',
                                  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '属性名称',
                                  `type` tinyint NOT NULL COMMENT '类型(1:规格;2:属性;)',
                                  `gmt_create` datetime NULL DEFAULT NULL COMMENT '创建时间',
                                  `gmt_modified` datetime NULL DEFAULT NULL COMMENT '更新时间',
                                  PRIMARY KEY (`id`) USING BTREE,
                                  INDEX `fk_pms_attr_pms_category`(`category_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 48 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品属性表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pms_attribute
-- ----------------------------
INSERT INTO `pms_attribute` VALUES (34, 5, '颜色', 1, '2021-07-11 17:57:06', '2022-03-05 13:16:30');
INSERT INTO `pms_attribute` VALUES (35, 5, '规格', 1, '2021-07-11 18:00:06', '2022-03-05 13:16:30');
INSERT INTO `pms_attribute` VALUES (36, 5, '上市时间', 2, '2021-07-11 18:00:08', '2022-03-05 13:16:31');
INSERT INTO `pms_attribute` VALUES (47, 8, '阿斯顿', 1, '2022-03-04 13:00:43', '2022-03-04 13:00:43');

-- ----------------------------
-- Table structure for pms_brand
-- ----------------------------
DROP TABLE IF EXISTS `pms_brand`;
CREATE TABLE `pms_brand`  (
                              `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                              `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '品牌名称',
                              `logo_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'LOGO图片',
                              `sort` int NULL DEFAULT NULL COMMENT '排序',
                              `gmt_create` datetime NULL DEFAULT NULL COMMENT '创建时间',
                              `gmt_modified` datetime NULL DEFAULT NULL COMMENT '更新时间',
                              PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品品牌表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pms_brand
-- ----------------------------
INSERT INTO `pms_brand` VALUES (1, '有来', 'http://a.youlai.tech:9000/default/5409e3deb5a14b8fa8cb4275dee0e25d.png', 1, '2021-07-11 19:56:58', '2021-07-11 20:02:54');
INSERT INTO `pms_brand` VALUES (10, '小米', 'http://a.youlai.tech:9000/default/6c5433c84cc54120996a151c8e6d4cf3.jpg', 1, '2022-03-05 16:12:16', '2022-03-05 16:12:16');

-- ----------------------------
-- Table structure for pms_category
-- ----------------------------
DROP TABLE IF EXISTS `pms_category`;
CREATE TABLE `pms_category`  (
                                 `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                                 `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品分类名称',
                                 `parent_id` bigint NOT NULL COMMENT '父级ID',
                                 `level` int NULL DEFAULT NULL COMMENT '层级',
                                 `icon_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图标地址',
                                 `sort` int NULL DEFAULT NULL COMMENT '排序',
                                 `visible` tinyint(1) NULL DEFAULT 1 COMMENT '显示状态:( 0:隐藏 1:显示)',
                                 `gmt_create` datetime NULL DEFAULT NULL COMMENT '创建时间',
                                 `gmt_modified` datetime NULL DEFAULT NULL COMMENT '更新时间',
                                 PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品分类表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pms_category
-- ----------------------------
INSERT INTO `pms_category` VALUES (3, '手机配件', 0, 1, NULL, 1, 1, NULL, NULL);
INSERT INTO `pms_category` VALUES (4, '智能手机', 3, 2, NULL, 1, 1, NULL, NULL);
INSERT INTO `pms_category` VALUES (5, '5g手机', 4, 3, 'http://a.youlai.tech:9000/default/f4a27e240c184758942670aad9ce5639.jpg', 1, 1, NULL, '2022-03-05 16:16:16');
INSERT INTO `pms_category` VALUES (6, '电脑', 0, 1, 'http://a.youlai.tech:9000/default/776c21c1a71848069093033f461c5f4a.jpg', 1, 1, '2022-02-25 11:22:44', '2022-02-25 11:22:44');
INSERT INTO `pms_category` VALUES (7, '游戏本', 6, 2, 'http://a.youlai.tech:9000/default/f41d764d7ce64054b75fe9be5fb3f700.jpg', 1, 1, '2022-02-25 11:23:06', '2022-02-25 11:23:06');
INSERT INTO `pms_category` VALUES (8, '轻薄本', 6, 2, 'http://a.youlai.tech:9000/default/840ddc78c93d422b9929821c97f3dfbe.jpg', 2, 1, '2022-02-25 11:23:24', '2022-02-25 11:23:24');

-- ----------------------------
-- Table structure for pms_catetgory_brand
-- ----------------------------
DROP TABLE IF EXISTS `pms_catetgory_brand`;
CREATE TABLE `pms_catetgory_brand`  (
                                        `category_id` bigint NOT NULL,
                                        `brand_id` bigint NOT NULL,
                                        PRIMARY KEY (`category_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pms_catetgory_brand
-- ----------------------------

-- ----------------------------
-- Table structure for pms_sku
-- ----------------------------
DROP TABLE IF EXISTS `pms_sku`;
CREATE TABLE `pms_sku`  (
                            `id` bigint NOT NULL AUTO_INCREMENT,
                            `sku_sn` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品编码',
                            `spu_id` bigint NOT NULL COMMENT 'SPU ID',
                            `name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品名称',
                            `spec_ids` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品规格值，以英文逗号(,)分割',
                            `price` bigint NULL DEFAULT NULL COMMENT '商品价格(单位：分)',
                            `stock_num` int NULL DEFAULT 0 COMMENT '库存数量',
                            `locked_stock_num` int NULL DEFAULT 0 COMMENT '锁定库存数量',
                            `pic_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品图片地址',
                            `gmt_create` datetime NULL DEFAULT NULL COMMENT '创建时间',
                            `gmt_modified` datetime NULL DEFAULT NULL COMMENT '更新时间',
                            PRIMARY KEY (`id`) USING BTREE,
                            INDEX `fk_pms_sku_pms_spu`(`spu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 353 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品库存表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pms_sku
-- ----------------------------
INSERT INTO `pms_sku` VALUES (291, 'sn001', 1, '黑 6+128g', '1_3', 399900, 999, 0, 'http://a.youlai.tech:9000/default/6759b824e6d04af69f6f3e55190e7e79.png', '2021-08-08 00:43:26', '2022-03-05 15:01:53');
INSERT INTO `pms_sku` VALUES (292, 'sn002', 1, '黑 8+256g', '1_4', 499900, 999, 0, 'http://a.youlai.tech:9000/default/6759b824e6d04af69f6f3e55190e7e79.png', '2021-08-08 00:43:26', '2022-03-05 15:01:53');
INSERT INTO `pms_sku` VALUES (353, 'sn003', 1, '蓝 6+128g', '216_3', 399900, 999, 0, 'http://a.youlai.tech:9000/default/aed7966ff68640f08d110f4fbcd1cdc2.png', '2022-03-05 09:25:53', '2022-03-05 15:01:53');
INSERT INTO `pms_sku` VALUES (354, 'sn004', 1, '蓝 8+256g', '216_4', 499900, 998, 0, 'http://a.youlai.tech:9000/default/aed7966ff68640f08d110f4fbcd1cdc2.png', '2022-03-05 09:25:53', '2022-03-05 15:01:53');

-- ----------------------------
-- Table structure for pms_spu
-- ----------------------------
DROP TABLE IF EXISTS `pms_spu`;
CREATE TABLE `pms_spu`  (
                            `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                            `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品名称',
                            `category_id` bigint NOT NULL COMMENT '商品类型ID',
                            `brand_id` bigint NULL DEFAULT NULL COMMENT '商品品牌ID',
                            `origin_price` bigint NOT NULL COMMENT '原价【起】',
                            `price` bigint NOT NULL COMMENT '现价【起】',
                            `sales` int NULL DEFAULT 0 COMMENT '销量',
                            `pic_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品主图',
                            `album` json NULL COMMENT '商品图册',
                            `unit` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '单位',
                            `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品简介',
                            `detail` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '商品详情',
                            `status` tinyint NULL DEFAULT NULL COMMENT '商品状态：0-下架 1-上架',
                            `gmt_create` datetime NULL DEFAULT NULL COMMENT '创建时间',
                            `gmt_modified` datetime NULL DEFAULT NULL COMMENT '更新时间',
                            PRIMARY KEY (`id`) USING BTREE,
                            INDEX `fk_pms_spu_pms_brand`(`brand_id`) USING BTREE,
                            INDEX `fk_pms_spu_pms_category`(`category_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 97 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pms_spu
-- ----------------------------
INSERT INTO `pms_spu` VALUES (1, '小米12 PRO', 5, 1, 599900, 599900, 1, 'http://a.youlai.tech:9000/default/1fb5d61cae2c4831a6122ca9ec747624.png', '[\"https://gitee.com/haoxr/image/raw/master/default/image-20210702002909113.png\", \"https://gitee.com/haoxr/image/raw/master/default/image-20210702002909113.png\"]', '台', '好快,好稳,\n好一次强上加强。\n高通全新一代芯片赋能，速度大幅提升。\n三大专业主摄影像加持，能力全面进化。\n大师级设计理念新诠释，质感简而不凡。\n斩获十五项纪录旗舰屏，感官万般出众。', '<p>123123<img src=\"http://a.youlai.tech:9000/default/1a69357664c24962ac23953905c3c38f.png\" alt=\"\" data-href=\"\" style=\"width: 449.00px;height: 449.00px;\"/></p>', 1, NULL, '2022-03-05 15:01:53');

-- ----------------------------
-- Table structure for pms_spu_attribute_value
-- ----------------------------
DROP TABLE IF EXISTS `pms_spu_attribute_value`;
CREATE TABLE `pms_spu_attribute_value`  (
                                            `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                                            `spu_id` bigint NOT NULL COMMENT '产品ID',
                                            `attribute_id` bigint NULL DEFAULT NULL COMMENT '属性ID',
                                            `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '属性名称',
                                            `value` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '属性值',
                                            `type` tinyint NOT NULL COMMENT '类型(1:规格;2:属性;)',
                                            `pic_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '规格图片',
                                            `gmt_create` datetime NULL DEFAULT NULL COMMENT '创建时间',
                                            `gmt_modified` datetime NULL DEFAULT NULL COMMENT '更新时间',
                                            PRIMARY KEY (`id`) USING BTREE,
                                            INDEX `fk_pms_spu_attribute_pms_attr`(`name`) USING BTREE,
                                            INDEX `fk_pms_spu_attribute_pms_spu`(`spu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 216 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品属性项表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pms_spu_attribute_value
-- ----------------------------
INSERT INTO `pms_spu_attribute_value` VALUES (1, 1, 34, '颜色', '黑', 1, 'http://a.youlai.tech:9000/default/6759b824e6d04af69f6f3e55190e7e79.png', NULL, '2022-03-05 15:01:53');
INSERT INTO `pms_spu_attribute_value` VALUES (3, 1, 35, '规格', '6+128g', 1, NULL, NULL, '2022-03-05 15:01:53');
INSERT INTO `pms_spu_attribute_value` VALUES (4, 1, 35, '规格', '8+256g', 1, NULL, NULL, '2022-03-05 15:01:53');
INSERT INTO `pms_spu_attribute_value` VALUES (5, 1, 36, '上市时间', '2021-07-17', 2, NULL, NULL, '2022-03-05 15:01:53');
INSERT INTO `pms_spu_attribute_value` VALUES (97, 68, NULL, '颜色', '4123', 1, NULL, '2021-08-07 23:38:50', '2021-08-07 23:38:50');
INSERT INTO `pms_spu_attribute_value` VALUES (98, 68, NULL, '规格', '456', 1, NULL, '2021-08-07 23:38:50', '2021-08-07 23:38:50');
INSERT INTO `pms_spu_attribute_value` VALUES (99, 68, NULL, '规格', '123', 1, NULL, '2021-08-07 23:38:50', '2021-08-07 23:38:50');
INSERT INTO `pms_spu_attribute_value` VALUES (100, 69, NULL, '上市时间', '123', 2, NULL, '2021-08-07 23:41:44', '2021-08-07 23:41:44');
INSERT INTO `pms_spu_attribute_value` VALUES (101, 69, NULL, '颜色', '123', 1, NULL, '2021-08-07 23:41:44', '2021-08-07 23:41:44');
INSERT INTO `pms_spu_attribute_value` VALUES (102, 69, NULL, '颜色', '4123', 1, NULL, '2021-08-07 23:41:44', '2021-08-07 23:41:44');
INSERT INTO `pms_spu_attribute_value` VALUES (103, 69, NULL, '规格', '456', 1, NULL, '2021-08-07 23:41:44', '2021-08-07 23:41:44');
INSERT INTO `pms_spu_attribute_value` VALUES (104, 69, NULL, '规格', '123', 1, NULL, '2021-08-07 23:41:45', '2021-08-07 23:41:45');
INSERT INTO `pms_spu_attribute_value` VALUES (216, 1, NULL, '颜色', '蓝', 1, 'http://a.youlai.tech:9000/default/aed7966ff68640f08d110f4fbcd1cdc2.png', '2022-03-05 09:25:53', '2022-03-05 15:01:53');

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

SET FOREIGN_KEY_CHECKS = 1;
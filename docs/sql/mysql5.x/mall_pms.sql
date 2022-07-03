/*
* 商城商品表
* MySQL5.x版本
*/
use mall_pms;
    
SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for pms_brand
-- ----------------------------
DROP TABLE IF EXISTS `pms_brand`;
CREATE TABLE `pms_brand`  (
                              `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                              `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '品牌名称',
                              `logo_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'LOGO图片',
                              `sort` int NULL DEFAULT NULL COMMENT '排序',
                              `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
                              `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
                              PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 33 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品品牌表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pms_brand
-- ----------------------------
INSERT INTO `pms_brand` VALUES (1, '有来', 'http://a.youlai.tech:9000/default/5409e3deb5a14b8fa8cb4275dee0e25d.png', 1, '2021-07-11 19:56:58', '2021-07-11 20:02:54');
INSERT INTO `pms_brand` VALUES (10, '小米', 'http://a.youlai.tech:9000/default/6a5a606fc60742919149a7861bf26cd5.jpg', 2, '2022-03-05 16:12:16', '2022-03-05 16:12:16');
INSERT INTO `pms_brand` VALUES (11, '华硕', 'http://a.youlai.tech:9000/default/f18083f95e104a0bae3c587dee3bb2ed.png', 3, '2022-03-05 16:12:16', '2022-03-05 16:12:16');
INSERT INTO `pms_brand` VALUES (20, '华为', NULL, 1, '2022-05-06 23:08:33', '2022-05-06 23:08:33');

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
                                 `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
                                 `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
                                 PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 96 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品分类表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pms_category
-- ----------------------------
INSERT INTO `pms_category` VALUES (3, '手机配件', 0, 1, NULL, 1, 1, NULL, NULL);
INSERT INTO `pms_category` VALUES (4, '智能手机', 3, 2, NULL, 1, 1, NULL, NULL);
INSERT INTO `pms_category` VALUES (5, '5g手机', 4, 3, 'https://www.youlai.tech/files/default/f4a27e240c184758942670aad9ce5639.jpg', 1, 1, NULL, '2022-03-05 16:16:16');
INSERT INTO `pms_category` VALUES (6, '电脑', 0, 1, 'https://www.youlai.tech/files/default/776c21c1a71848069093033f461c5f4a.jpg', 1, 1, '2022-02-25 11:22:44', '2022-02-25 11:22:44');
INSERT INTO `pms_category` VALUES (7, '游戏本', 6, 2, 'https://www.youlai.tech/files/default/f41d764d7ce64054b75fe9be5fb3f700.jpg', 1, 1, '2022-02-25 11:23:06', '2022-02-25 11:23:06');
INSERT INTO `pms_category` VALUES (8, '轻薄本', 6, 2, 'https://www.youlai.tech/files/default/840ddc78c93d422b9929821c97f3dfbe.jpg', 2, 1, '2022-02-25 11:23:24', '2022-02-25 11:23:24');
INSERT INTO `pms_category` VALUES (11, '联想拯救者', 7, 3, NULL, 100, 1, '2022-03-11 14:21:11', '2022-03-11 14:21:11');
INSERT INTO `pms_category` VALUES (13, '华硕', 7, 3, NULL, 100, 1, '2022-03-11 14:23:50', '2022-03-11 14:23:50');
INSERT INTO `pms_category` VALUES (14, '手机壳', 3, 2, NULL, 1, 1, '2022-03-15 10:16:40', '2022-03-15 10:16:40');
INSERT INTO `pms_category` VALUES (15, '服饰', 0, 1, NULL, 1, 1, '2022-03-15 10:16:55', '2022-03-15 10:16:55');
INSERT INTO `pms_category` VALUES (16, '男装', 15, 2, NULL, 1, 1, '2022-03-15 10:17:06', '2022-03-15 10:17:06');
INSERT INTO `pms_category` VALUES (17, '女装', 15, 2, NULL, 1, 1, '2022-03-15 10:17:12', '2022-07-03 14:16:36');
INSERT INTO `pms_category` VALUES (18, '水果', 0, 1, NULL, 1, 1, '2022-03-15 10:17:28', '2022-03-15 10:17:28');
INSERT INTO `pms_category` VALUES (19, '国产水果', 18, 2, NULL, 1, 1, '2022-03-15 10:17:42', '2022-03-15 10:17:42');
INSERT INTO `pms_category` VALUES (20, '热带水果', 18, 2, NULL, 1, 1, '2022-03-15 10:17:50', '2022-03-15 10:17:50');
INSERT INTO `pms_category` VALUES (21, '衬衫', 16, 3, NULL, 1, 1, '2022-03-15 10:18:05', '2022-03-15 10:18:05');
INSERT INTO `pms_category` VALUES (22, '裙子', 17, 3, NULL, 1, 1, '2022-03-15 10:18:18', '2022-03-15 10:18:18');
INSERT INTO `pms_category` VALUES (23, '水果', 19, 3, 'https://www.youlai.tech/files/default/9204260996c9430284428d2e13b45d71.jpg', 100, 1, '2022-03-16 16:37:10', '2022-03-16 16:37:10');

-- ----------------------------
-- Table structure for pms_category_attribute
-- ----------------------------
DROP TABLE IF EXISTS `pms_category_attribute`;
CREATE TABLE `pms_category_attribute`  (
                                           `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                                           `category_id` bigint NOT NULL COMMENT '分类ID',
                                           `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '属性名称',
                                           `type` tinyint NOT NULL COMMENT '类型(1:规格;2:属性;)',
                                           `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
                                           `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
                                           PRIMARY KEY (`id`) USING BTREE,
                                           INDEX `fk_pms_attr_pms_category`(`category_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 172 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品属性表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pms_category_attribute
-- ----------------------------
INSERT INTO `pms_category_attribute` VALUES (34, 5, '颜色', 1, '2021-07-11 17:57:06', '2022-07-01 00:08:19');
INSERT INTO `pms_category_attribute` VALUES (35, 5, '规格', 1, '2021-07-11 18:00:06', '2022-07-01 00:08:19');
INSERT INTO `pms_category_attribute` VALUES (36, 5, '上市时间', 2, '2021-07-11 18:00:08', '2022-06-01 17:41:05');
INSERT INTO `pms_category_attribute` VALUES (47, 8, '阿斯顿', 1, '2022-03-04 13:00:43', '2022-03-18 15:57:49');
INSERT INTO `pms_category_attribute` VALUES (48, 8, '内存', 2, '2022-03-08 16:51:48', '2022-03-08 16:51:48');
INSERT INTO `pms_category_attribute` VALUES (54, 21, '颜色', 1, '2022-03-21 18:10:58', '2022-05-10 10:37:35');
INSERT INTO `pms_category_attribute` VALUES (55, 21, '尺寸', 1, '2022-03-21 18:10:58', '2022-05-10 10:37:35');
INSERT INTO `pms_category_attribute` VALUES (56, 21, '布料', 2, '2022-03-21 18:11:22', '2022-05-10 10:37:37');

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
                            `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
                            `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
                            PRIMARY KEY (`id`) USING BTREE,
                            INDEX `fk_pms_sku_pms_spu`(`spu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 745 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品库存表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pms_sku
-- ----------------------------
INSERT INTO `pms_sku` VALUES (1, 'sn001', 1, '黑 6+128g', '1_3', 399900, 9998, 3, 'https://www.youlai.tech/files/default/c25b39470474494485633c49101a0f5d.png', '2021-08-08 00:43:26', '2022-07-03 14:16:16');
INSERT INTO `pms_sku` VALUES (2, 'sn002', 1, '黑 8+256g', '1_4', 499900, 9998, 0, 'https://www.youlai.tech/files/default/c25b39470474494485633c49101a0f5d.png', '2021-08-08 00:43:26', '2022-07-03 14:16:16');
INSERT INTO `pms_sku` VALUES (3, 'sn003', 1, '蓝 6+128g', '216_3', 399900, 9998, 0, 'https://www.youlai.tech/files/default/835d73a337964b9b97e5c7c90acc8cb2.png', '2022-03-05 09:25:53', '2022-07-03 14:16:16');
INSERT INTO `pms_sku` VALUES (4, 'sn004', 1, '蓝 8+256g', '216_4', 499900, 9988, 0, 'https://www.youlai.tech/files/default/835d73a337964b9b97e5c7c90acc8cb2.png', '2022-03-05 09:25:53', '2022-07-03 14:16:16');
INSERT INTO `pms_sku` VALUES (5, '10000001', 2, '魔幻青 RTX3060/i7-12700H/165Hz 2.5K屏', '256_258', 1025000, 9823, 14, 'http://a.youlai.tech:9000/default/8815c9a46fcc4b1ea952623406750da5.jpg', '2022-03-11 14:39:21', '2022-04-15 23:47:45');
INSERT INTO `pms_sku` VALUES (6, '10000002', 2, '魔幻青 RTX3050tTi/12代i5/144Hz高色域屏', '256_259', 925000, 10000, 0, 'http://a.youlai.tech:9000/default/8815c9a46fcc4b1ea952623406750da5.jpg', '2022-03-11 14:39:21', '2022-04-15 23:47:46');
INSERT INTO `pms_sku` VALUES (7, '10000003', 2, '日蚀灰 RTX3060/i7-12700H/165Hz 2.5K屏', '257_258', 1025000, 10000, 0, 'http://a.youlai.tech:9000/default/3210cd1ffb6c4346b743a10855d3cb37.jpg', '2022-03-11 14:39:21', '2022-04-15 23:47:46');
INSERT INTO `pms_sku` VALUES (8, '10000004', 2, '日蚀灰 RTX3050tTi/12代i5/144Hz高色域屏', '257_259', 925000, 10000, 0, 'http://a.youlai.tech:9000/default/3210cd1ffb6c4346b743a10855d3cb37.jpg', '2022-03-11 14:39:21', '2022-04-15 23:47:46');

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
                            `status` tinyint NULL DEFAULT 1 COMMENT '商品状态(0:下架 1:上架)',
                            `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
                            `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
                            PRIMARY KEY (`id`) USING BTREE,
                            INDEX `fk_pms_spu_pms_brand`(`brand_id`) USING BTREE,
                            INDEX `fk_pms_spu_pms_category`(`category_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 285 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pms_spu
-- ----------------------------
INSERT INTO `pms_spu` VALUES (1, '小米12 PRO', 5, 10, 599900, 599900, 1, 'https://oss.youlai.tech/default/0ccd5bf5cf384185be1f18a01da076b9.png', '[]', '台', '好快,好稳,\n好一次强上加强。\n高通全新一代芯片赋能，速度大幅提升。\n三大专业主摄影像加持，能力全面进化。\n大师级设计理念新诠释，质感简而不凡。\n斩获十五项纪录旗舰屏，感官万般出众。', '<p><img src=\"http://a.youlai.tech:9000/default/1a69357664c24962ac23953905c3c38f.png\" alt=\"\" data-href=\"\" style=\"width: 449.00px;height: 449.00px;\"/></p>', 1, NULL, '2022-07-03 14:16:16');
INSERT INTO `pms_spu` VALUES (2, '华硕天选3', 13, 11, 1145000, 929900, 0, 'https://www.youlai.tech/files/default/d97457b3fd7d4aef8846da96fe032bf8.jpg', '[\"https://www.youlai.tech/files/default/3edd01c723ff456384cea9bd3c9b19e7.jpg\", \"https://www.youlai.tech/files/default/a6681c18fc294ee49efb8e121b8e943f.jpg\", \"https://www.youlai.tech/files/default/97458ae9ea734bc498724660abb1c6cd.jpg\", \"https://www.youlai.tech/files/default/501b0e6dcb3f4d69b7e40e90b3d3ac32.jpg\"]', NULL, '中国台湾华硕电脑股份有限公司 [1]  是当前全球第一大主板生产商、全球第三大显卡生产商，同时也是全球领先的3C解决方案提供商之一，致力于为个人和企业用户提供最具创新价值的产品及应用方案。华硕的产品线完整覆盖至笔记本电脑、主板、显卡、服务器、光存储、有线/无线网络通讯产品、LCD、掌上电脑、智能手机等全线3C产品。其中显卡和主板以及笔记本电脑三大产品已经成为华硕的主要竞争实力。', '<p><img src=\"http://a.youlai.tech:9000/default/5e4fb81b04244a74aacaabb4685101e2.png\" alt=\"\" data-href=\"\" style=\"\"/><img src=\"http://a.youlai.tech:9000/default/0744c5b6d77b47b294eb111ee992c62b.png\" alt=\"\" data-href=\"\" style=\"\"/></p>', 1, '2022-03-11 14:39:21', '2022-04-15 23:47:46');

-- ----------------------------
-- Table structure for pms_spu_attribute
-- ----------------------------
DROP TABLE IF EXISTS `pms_spu_attribute`;
CREATE TABLE `pms_spu_attribute`  (
                                      `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                                      `spu_id` bigint NOT NULL COMMENT '产品ID',
                                      `attribute_id` bigint NULL DEFAULT NULL COMMENT '属性ID',
                                      `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '属性名称',
                                      `value` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '属性值',
                                      `type` tinyint NOT NULL COMMENT '类型(1:规格;2:属性;)',
                                      `pic_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '规格图片',
                                      `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
                                      `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
                                      PRIMARY KEY (`id`) USING BTREE,
                                      INDEX `fk_pms_spu_attribute_pms_attr`(`name`) USING BTREE,
                                      INDEX `fk_pms_spu_attribute_pms_spu`(`spu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 835 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品属性/规格表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pms_spu_attribute
-- ----------------------------
INSERT INTO `pms_spu_attribute` VALUES (1, 1, 34, '颜色', '黑', 1, 'https://www.youlai.tech/files/default/c25b39470474494485633c49101a0f5d.png', NULL, '2022-07-03 14:16:16');
INSERT INTO `pms_spu_attribute` VALUES (3, 1, 35, '规格', '6+128g', 1, NULL, NULL, '2022-07-03 14:16:16');
INSERT INTO `pms_spu_attribute` VALUES (4, 1, 35, '规格', '8+256g', 1, NULL, NULL, '2022-07-03 14:16:16');
INSERT INTO `pms_spu_attribute` VALUES (5, 1, 36, '上市时间', '2021-07-17', 2, NULL, NULL, '2022-07-03 14:16:16');
INSERT INTO `pms_spu_attribute` VALUES (216, 1, NULL, '颜色', '蓝', 1, 'https://www.youlai.tech/files/default/835d73a337964b9b97e5c7c90acc8cb2.png', '2022-03-05 09:25:53', '2022-07-03 14:16:16');
INSERT INTO `pms_spu_attribute` VALUES (251, 2, NULL, '上市时间', '2022/3/11', 2, NULL, '2022-03-11 14:39:21', '2022-04-15 23:47:45');
INSERT INTO `pms_spu_attribute` VALUES (252, 2, NULL, '商品名称', '华硕天选3', 2, NULL, '2022-03-11 14:39:21', '2022-04-15 23:47:45');
INSERT INTO `pms_spu_attribute` VALUES (253, 2, NULL, '商品编号', '100032610338', 2, NULL, '2022-03-11 14:39:21', '2022-04-15 23:47:45');
INSERT INTO `pms_spu_attribute` VALUES (254, 2, NULL, '商品毛重', '4.05kg', 2, NULL, '2022-03-11 14:39:21', '2022-04-15 23:47:45');
INSERT INTO `pms_spu_attribute` VALUES (255, 2, NULL, '系统', 'windows11', 2, NULL, '2022-03-11 14:39:21', '2022-04-15 23:47:45');
INSERT INTO `pms_spu_attribute` VALUES (256, 2, NULL, '颜色', '魔幻青', 1, 'http://a.youlai.tech:9000/default/8815c9a46fcc4b1ea952623406750da5.jpg', '2022-03-11 14:39:21', '2022-04-15 23:47:45');
INSERT INTO `pms_spu_attribute` VALUES (257, 2, NULL, '颜色', '日蚀灰', 1, 'http://a.youlai.tech:9000/default/3210cd1ffb6c4346b743a10855d3cb37.jpg', '2022-03-11 14:39:21', '2022-04-15 23:47:45');
INSERT INTO `pms_spu_attribute` VALUES (258, 2, NULL, '规格', 'RTX3060/i7-12700H/165Hz 2.5K屏', 1, NULL, '2022-03-11 14:39:21', '2022-04-15 23:47:45');
INSERT INTO `pms_spu_attribute` VALUES (259, 2, NULL, '规格', 'RTX3050tTi/12代i5/144Hz高色域屏', 1, NULL, '2022-03-11 14:39:21', '2022-04-15 23:47:45');

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
INSERT INTO `undo_log` VALUES (27295877623459842, 'c.youlai.tech:8091:27295877623459841', 'serializer=jackson&compressorType=NONE', 0x7B7D, 1, '2022-06-25 00:23:42.978264', '2022-06-25 00:23:42.978264');

SET FOREIGN_KEY_CHECKS = 1;

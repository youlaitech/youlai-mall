/*
* 商城营销表
* MySQL5.x版本
*/

use mall_sms;

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sms_advert
-- ----------------------------
DROP TABLE IF EXISTS `sms_advert`;
CREATE TABLE `sms_advert`  (
                               `id` bigint NOT NULL AUTO_INCREMENT,
                               `title` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '广告标题',
                               `pic_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片地址',
                               `begin_time` datetime NULL DEFAULT NULL COMMENT '开始时间',
                               `end_time` datetime NULL DEFAULT NULL COMMENT '结束时间',
                               `status` tinyint(1) NULL DEFAULT NULL COMMENT '状态(1:开启；0:关闭)',
                               `sort` int NULL DEFAULT NULL COMMENT '排序',
                               `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '链接地址',
                               `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
                               `gmt_create` datetime NULL DEFAULT NULL,
                               `gmt_modified` datetime NULL DEFAULT NULL,
                               PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '广告表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sms_advert
-- ----------------------------
INSERT INTO `sms_advert` VALUES (12, '测试', 'http://a.youlai.tech:9000/default/0b6d7d2dfc334c88aa9e6eb5d6f4c090.jpg', '2021-12-26 00:00:00', '2021-08-02 00:00:00', 1, 1, '111', '111', NULL, '2021-12-26 22:36:45');
INSERT INTO `sms_advert` VALUES (13, '123', 'http://a.youlai.tech:9000/default/3fe04a5074ae48a385072619a6798a6a.jpg', '2021-12-07 00:00:00', '2021-09-13 00:00:00', 1, 1, '11', '1', NULL, '2021-12-26 22:37:57');
INSERT INTO `sms_advert` VALUES (18, '123213', 'http://a.youlai.tech:9000/default/3fdf5afdd1da4b42a340709c8f1c7000.jpg', NULL, '2021-09-07 00:00:00', 1, 1, NULL, NULL, NULL, NULL);
INSERT INTO `sms_advert` VALUES (22, '123', 'http://a.youlai.tech:9000/default/4fda488a5ffa4268a441e838d0ec5c34.png', '2021-12-07 00:00:00', '2021-12-08 00:00:00', 1, 1, NULL, NULL, '2021-12-08 13:46:26', '2021-12-08 13:46:26');
INSERT INTO `sms_advert` VALUES (23, '123', 'http://a.youlai.tech:9000/default/51ddcae192894fe690d24d7a4dc0e8ae.jpg', '2021-12-14 00:00:00', '2021-12-09 00:00:00', 1, 11, NULL, NULL, '2021-12-08 13:46:52', '2021-12-08 13:46:52');
INSERT INTO `sms_advert` VALUES (27, '测试广告', 'http://a.youlai.tech:9000/default/9b53df224c094204a335b8552e9ec69c.jpg', '2021-12-13 00:00:00', '2021-12-31 00:00:00', 1, 1, NULL, NULL, '2021-12-24 11:04:26', '2021-12-24 11:04:26');

-- ----------------------------
-- Table structure for sms_coupon
-- ----------------------------
DROP TABLE IF EXISTS `sms_coupon`;
CREATE TABLE `sms_coupon`  (
                               `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
                               `title` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '优惠券标题（有图片则显示图片）：无门槛50元优惠券 | 单品最高减2000元',
                               `img` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片',
                               `type` int NOT NULL DEFAULT 1 COMMENT '1满减券 2叠加满减券 3无门槛券（需要限制大小）',
                               `publish` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发布状态, PUBLISH发布，DRAFT草稿，OFFLINE下线',
                               `condition_price` bigint NOT NULL DEFAULT 0 COMMENT '满多少才可以使用（为0则不限制金额）',
                               `price` bigint NOT NULL COMMENT '抵扣价格',
                               `publish_count` int NOT NULL DEFAULT 1 COMMENT '优惠券总量',
                               `limit_count` int NOT NULL DEFAULT 1 COMMENT '限领张数(默认为1，为0则不限制)',
                               `take_count` int NOT NULL DEFAULT 0 COMMENT '已领取的优惠券数量',
                               `used_count` int NOT NULL DEFAULT 0 COMMENT '已使用的优惠券数量',
                               `start_time` datetime NOT NULL COMMENT '发放开始时间',
                               `end_time` datetime NOT NULL COMMENT '发放结束时间',
                               `valid_days` int NOT NULL DEFAULT 1 COMMENT '自领取之日起有效天数',
                               `status` int NOT NULL DEFAULT 1 COMMENT '逻辑删除使用',
                               `gmt_create` datetime NULL DEFAULT NULL COMMENT '创建时间',
                               `gmt_modified` datetime NULL DEFAULT NULL COMMENT '修改时间',
                               PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '优惠券表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sms_coupon
-- ----------------------------
INSERT INTO `sms_coupon` VALUES (1, '新人无门槛优惠券,立减20元', 'http://file.gorun996.com/img/2021/03/17/211545.png', 3, ' PUBLISH', 0, 1500, 1000, 1, 0, 0, '2021-03-17 21:10:35', '2025-03-17 21:10:38', 7, 1, '2021-03-17 21:10:54', '2021-03-17 21:10:56');
INSERT INTO `sms_coupon` VALUES (2, '满减优惠券', NULL, 1, 'PUBLISH', 50, 2000, 100, 1, 0, 0, '2021-03-17 21:19:22', '2022-03-17 21:19:24', 7, 1, '2021-03-17 21:19:44', '2021-03-17 21:19:46');

-- ----------------------------
-- Table structure for sms_coupon_record
-- ----------------------------
DROP TABLE IF EXISTS `sms_coupon_record`;
CREATE TABLE `sms_coupon_record`  (
                                      `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
                                      `coupon_id` bigint NULL DEFAULT NULL COMMENT '优惠券id',
                                      `use_state` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '使用状态  可用 NEW,已使用USED,过期 EXPIRED;',
                                      `user_id` bigint NULL DEFAULT NULL COMMENT '用户id',
                                      `user_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户昵称（冗余字段）',
                                      `coupon_type` int NOT NULL DEFAULT 1 COMMENT '1满减券 2叠加满减券 3无门槛券',
                                      `coupon_title` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '优惠券标题',
                                      `condition_price` bigint NOT NULL DEFAULT 0 COMMENT '满多少才可以使用（为0则不限制金额）',
                                      `price` bigint NOT NULL COMMENT '抵扣价格',
                                      `start_time` datetime NULL DEFAULT NULL COMMENT '开始时间',
                                      `end_time` datetime NULL DEFAULT NULL COMMENT '结束时间',
                                      `order_id` bigint NULL DEFAULT NULL COMMENT '订单id',
                                      `status` int NOT NULL DEFAULT 1 COMMENT '逻辑删除使用',
                                      `gmt_create` datetime NULL DEFAULT NULL COMMENT '创建时间',
                                      `gmt_modified` datetime NULL DEFAULT NULL COMMENT '修改时间',
                                      PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '优惠券领劵使用记录' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sms_coupon_record
-- ----------------------------
INSERT INTO `sms_coupon_record` VALUES (1, 1, 'NEWS', 1, 'zhgsan', 1, '大促销', 50, 20, '2021-03-19 11:12:42', '2021-04-19 11:12:50', NULL, 1, NULL, NULL);

-- ----------------------------
-- Table structure for sms_coupon_template
-- ----------------------------
DROP TABLE IF EXISTS `sms_coupon_template`;
CREATE TABLE `sms_coupon_template`  (
                                        `id` bigint NOT NULL COMMENT '主键自增 ID',
                                        `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '优惠券模板名称',
                                        `logo` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '优惠券模板logo',
                                        `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '优惠券模板描述',
                                        `category` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '优惠券模板分类',
                                        `verify_state` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否审核(0:待审核;1:已审核)',
                                        `offer_state` tinyint(1) NOT NULL DEFAULT 0 COMMENT '优惠券发放状态( 0: 未开始; 1: 进行中; 2: 已结束)\r\n',
                                        `offer_start_time` bigint NULL DEFAULT NULL COMMENT '优惠券发放最早时间',
                                        `offer_end_time` bigint NULL DEFAULT NULL COMMENT '优惠券发放结束时间',
                                        `used_state` tinyint(1) NOT NULL DEFAULT 0 COMMENT '优惠券使用状态(0：未生效; 1：生效中; 2：已结束)',
                                        `used_start_time` bigint NULL DEFAULT NULL COMMENT '优惠券最早使用时间',
                                        `used_end_time` bigint NULL DEFAULT NULL COMMENT '优惠券最晚使用时间',
                                        `total` int NOT NULL DEFAULT 0 COMMENT '总优惠券数量',
                                        `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '优惠券模板编码',
                                        `rule` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '优惠券规则',
                                        `gmt_create` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP,
                                        `gmt_created_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
                                        `gmt_modified` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP,
                                        `gmt_modified_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
                                        PRIMARY KEY (`id`) USING BTREE,
                                        UNIQUE INDEX `sms_coupon_template_name_uindex`(`name`) USING BTREE COMMENT '优惠券模板名称全局唯一',
                                        UNIQUE INDEX `sms_coupon_template_code_uindex`(`code`) USING BTREE COMMENT '优惠券模板编码全局唯一'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '优惠券模板表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sms_coupon_template
-- ----------------------------

-- ----------------------------
-- Table structure for sms_seckill_session
-- ----------------------------
DROP TABLE IF EXISTS `sms_seckill_session`;
CREATE TABLE `sms_seckill_session`  (
                                        `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
                                        `name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '场次名称',
                                        `start_time` datetime NULL DEFAULT NULL COMMENT '每日开始时间',
                                        `end_time` datetime NULL DEFAULT NULL COMMENT '每日结束时间',
                                        `status` tinyint(1) NULL DEFAULT NULL COMMENT '启用状态 1-开启  0-关闭',
                                        `gmt_create` datetime NULL DEFAULT NULL COMMENT '创建时间',
                                        `gmt_modified` datetime NULL DEFAULT NULL COMMENT '修改时间',
                                        PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '秒杀活动场次' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sms_seckill_session
-- ----------------------------
INSERT INTO `sms_seckill_session` VALUES (1, '测试秒杀场次1', '2021-03-07 20:20:50', '2021-03-09 20:20:53', 1, '2021-03-07 20:21:04', '2021-03-07 20:21:07');
INSERT INTO `sms_seckill_session` VALUES (2, '测试秒杀场次2', '2021-03-07 20:20:50', '2021-03-09 20:20:53', 1, '2021-03-07 20:21:04', '2021-03-07 20:21:07');

-- ----------------------------
-- Table structure for sms_seckill_sku_relation
-- ----------------------------
DROP TABLE IF EXISTS `sms_seckill_sku_relation`;
CREATE TABLE `sms_seckill_sku_relation`  (
                                             `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
                                             `session_id` bigint NULL DEFAULT NULL COMMENT '活动场次id',
                                             `sku_id` bigint NULL DEFAULT NULL COMMENT '商品id',
                                             `seckill_price` bigint NOT NULL DEFAULT 0 COMMENT '秒杀价格',
                                             `seckill_count` int NOT NULL DEFAULT 0 COMMENT '秒杀总量',
                                             `seckill_limit` int NOT NULL DEFAULT 0 COMMENT '每人限购数量',
                                             `seckill_sort` int NULL DEFAULT NULL COMMENT '排序',
                                             `gmt_create` datetime NULL DEFAULT NULL COMMENT '创建时间',
                                             `gmt_modified` datetime NULL DEFAULT NULL COMMENT '修改时间',
                                             PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '秒杀活动商品关联' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sms_seckill_sku_relation
-- ----------------------------
INSERT INTO `sms_seckill_sku_relation` VALUES (1, 1, 186, 10, 100, 1, 1, '2021-03-07 20:22:01', '2021-03-07 20:22:03');
INSERT INTO `sms_seckill_sku_relation` VALUES (2, 2, 186, 10, 100, 1, 1, '2021-03-07 20:22:01', '2021-03-07 20:22:03');
INSERT INTO `sms_seckill_sku_relation` VALUES (3, 2, 187, 10, 100, 1, 1, '2021-03-07 20:22:01', '2021-03-07 20:22:03');

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

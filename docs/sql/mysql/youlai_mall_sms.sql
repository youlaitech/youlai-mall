-- ----------------------------
-- 商城营销数据库
-- ----------------------------
CREATE DATABASE IF NOT EXISTS youlai_mall_sms DEFAULT CHARACTER SET utf8mb4 DEFAULT COLLATE utf8mb4_unicode_ci;

-- ----------------------------
-- 创建表 && 数据初始化
-- ----------------------------
use youlai_mall_sms;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;


-- ----------------------------
-- Table structure for sms_advert
-- ----------------------------
DROP TABLE IF EXISTS `sms_advert`;
CREATE TABLE `sms_advert`  (
                             `id` bigint NOT NULL AUTO_INCREMENT COMMENT '广告ID',
                             `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '广告标题',
                             `img_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '图片地址',
                             `start_time` datetime NULL DEFAULT NULL COMMENT '开始时间',
                             `end_time` datetime NULL DEFAULT NULL COMMENT '结束时间',
                             `status` tinyint(1) NOT NULL COMMENT '状态(1:开启；0:关闭)',
                             `sort` int NULL DEFAULT NULL COMMENT '排序',
                             `redirect_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '跳转链接',
                             `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
                             `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
                             `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间(新增有值)',
                             `is_deleted` tinyint NULL DEFAULT NULL COMMENT '逻辑删除标识(1-已删除 0-未删除)',
                             PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '广告表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sms_advert
-- ----------------------------
INSERT INTO `sms_advert` VALUES (1, 'banner1', 'https://oss.youlai.tech/default/14d9b8fb471a43429334080124a9c65d.png', '2025-04-05 23:37:54', '2099-10-07 00:00:00', 1, 3, NULL, NULL, '2025-04-05 23:37:54', '2025-04-05 23:37:54', NULL);
INSERT INTO `sms_advert` VALUES (2, 'banner2', 'https://oss.youlai.tech/default/2022/07/17/34f2b48e346a4726bbe5352a18d493fc.jpg', '2025-04-05 23:37:54', '2099-10-07 00:00:00', 1, 2, NULL, NULL, '2025-04-05 23:37:54', '2025-04-05 23:37:54', NULL);
INSERT INTO `sms_advert` VALUES (3, 'banner3', 'https://oss.youlai.tech/default/2022/07/17/91765310d3d845999a8a55600ad9ccb3.png', '2025-04-05 23:37:54', '2099-10-07 00:00:00', 1, 1, NULL, NULL, '2025-04-05 23:37:54', '2025-04-05 23:37:54', NULL);

-- ----------------------------
-- Table structure for sms_coupon
-- ----------------------------
DROP TABLE IF EXISTS `sms_coupon`;
CREATE TABLE `sms_coupon`  (
                             `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                             `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '优惠券名称',
                             `code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '优惠券兑换码',
                             `type` tinyint NOT NULL DEFAULT 1 COMMENT '优惠券类型(1-满减券;2-直减券;3-折扣券;4-包邮券)',
                             `discount_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '优惠金额/折扣值',
                             `discount_limit` decimal(10, 2) NULL DEFAULT NULL COMMENT '最高折扣金额',
                             `min_amount` decimal(10, 2) NULL DEFAULT 0.00 COMMENT '使用门槛金额',
                             `status` tinyint NOT NULL DEFAULT 0 COMMENT '优惠券状态(0-草稿;1-未开始;2-进行中;3-已结束;4-已失效)',
                             `publish_time` datetime NULL DEFAULT NULL COMMENT '发布时间',
                             `total_count` int NULL DEFAULT -1 COMMENT '发行数量(-1表示不限制)',
                             `receive_count` int NULL DEFAULT 0 COMMENT '已领取数量',
                             `used_count` int NULL DEFAULT 0 COMMENT '已使用数量',
                             `per_limit` int NULL DEFAULT -1 COMMENT '每人限领数量(-1表示不限制)',
                             `valid_type` tinyint NOT NULL DEFAULT 1 COMMENT '有效期类型(1-固定日期范围;2-领取后天数)',
                             `valid_days` int NULL DEFAULT NULL COMMENT '领取后有效天数',
                             `valid_start_time` datetime NULL DEFAULT NULL COMMENT '有效期开始时间',
                             `valid_end_time` datetime NULL DEFAULT NULL COMMENT '有效期结束时间',
                             `use_scope` int NULL DEFAULT 1 COMMENT '使用范围(位运算: 1-全场通用(0001);2-指定分类(0010);4-指定商品(0100))',
                             `can_superimpose` tinyint NULL DEFAULT 0 COMMENT '是否可叠加使用(0-否;1-是)',
                             `first_order_only` tinyint NULL DEFAULT 0 COMMENT '是否仅首单可用(0-否;1-是)',
                             `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注说明',
                             `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                             `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                             `is_deleted` tinyint NULL DEFAULT 0 COMMENT '是否删除(0-否;1-是)',
                             PRIMARY KEY (`id`) USING BTREE,
                             INDEX `idx_code`(`code` ASC) USING BTREE,
                             INDEX `idx_status`(`status` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '优惠券表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sms_coupon
-- ----------------------------
INSERT INTO `sms_coupon` VALUES (1, '123', NULL, 1, 12300.00, 2100.00, 200.00, 0, NULL, -1, 0, 0, -1, 1, 12, '2025-04-23 00:00:00', '2025-05-11 00:00:00', 6, 0, 0, '', '2025-04-20 23:47:07', '2025-04-20 23:47:07', 0);

-- ----------------------------
-- Table structure for sms_coupon_category
-- ----------------------------
DROP TABLE IF EXISTS `sms_coupon_category`;
CREATE TABLE `sms_coupon_category`  (
                                      `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                                      `coupon_id` bigint NOT NULL COMMENT '优惠券ID',
                                      `category_id` bigint NOT NULL COMMENT '分类ID',
                                      `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                      PRIMARY KEY (`id`) USING BTREE,
                                      INDEX `idx_coupon_id`(`coupon_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '优惠券-分类关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sms_coupon_category
-- ----------------------------
INSERT INTO `sms_coupon_category` VALUES (1, 1, 3, '2025-04-20 23:47:07');

-- ----------------------------
-- Table structure for sms_coupon_record
-- ----------------------------
DROP TABLE IF EXISTS `sms_coupon_record`;
CREATE TABLE `sms_coupon_record`  (
                                    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                                    `coupon_id` bigint NOT NULL COMMENT '优惠券ID',
                                    `user_id` bigint NOT NULL COMMENT '用户ID',
                                    `coupon_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '优惠券码',
                                    `get_type` tinyint NULL DEFAULT 1 COMMENT '获取类型(1-主动领取;2-后台发放)',
                                    `status` tinyint NULL DEFAULT 0 COMMENT '使用状态(0-未使用;1-已使用;2-已过期)',
                                    `use_time` datetime NULL DEFAULT NULL COMMENT '使用时间',
                                    `order_id` bigint NULL DEFAULT NULL COMMENT '订单ID',
                                    `order_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '订单编号',
                                    `use_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '使用优惠金额',
                                    `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                    PRIMARY KEY (`id`) USING BTREE,
                                    INDEX `idx_coupon_id`(`coupon_id` ASC) USING BTREE,
                                    INDEX `idx_user_id`(`user_id` ASC) USING BTREE,
                                    INDEX `idx_order_id`(`order_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '优惠券领取记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sms_coupon_record
-- ----------------------------

-- ----------------------------
-- Table structure for sms_coupon_spu
-- ----------------------------
DROP TABLE IF EXISTS `sms_coupon_spu`;
CREATE TABLE `sms_coupon_spu`  (
                                 `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                                 `coupon_id` bigint NOT NULL COMMENT '优惠券ID',
                                 `spu_id` bigint NOT NULL COMMENT '商品ID',
                                 `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                 PRIMARY KEY (`id`) USING BTREE,
                                 INDEX `idx_coupon_id`(`coupon_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '优惠券-商品关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sms_coupon_spu
-- ----------------------------
INSERT INTO `sms_coupon_spu` VALUES (1, 1, 1, '2025-04-20 23:47:07');

-- ----------------------------
-- Table structure for undo_log
-- ----------------------------
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log`  (
                           `branch_id` bigint NOT NULL COMMENT 'branch transaction id',
                           `xid` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'global transaction id',
                           `context` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'undo_log context,such as serialization',
                           `rollback_info` longblob NOT NULL COMMENT 'rollback info',
                           `log_status` int NOT NULL COMMENT '0:normal status,1:defense status',
                           `log_created` datetime(6) NOT NULL COMMENT 'create datetime',
                           `log_modified` datetime(6) NOT NULL COMMENT 'modify datetime',
                           UNIQUE INDEX `ux_undo_log`(`xid` ASC, `branch_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'AT transaction mode undo table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of undo_log
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;


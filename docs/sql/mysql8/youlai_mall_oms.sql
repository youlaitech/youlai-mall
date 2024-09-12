use youlai_mall_oms;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for oms_order
-- ----------------------------
DROP TABLE IF EXISTS `oms_order`;
CREATE TABLE `oms_order`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '订单号',
  `total_fee` bigint NOT NULL DEFAULT 0 COMMENT '订单总额（分）',
  `status` tinyint NOT NULL DEFAULT 101 COMMENT '订单状态（0：待支付，1：待发货，2：待收货，3：交易完成，4：已取消，5：退货中，6：已退货，7：退款处理中，8：已退款，9：待评价，10：已评价）',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '订单备注',
  `member_id` bigint NOT NULL DEFAULT 0 COMMENT '会员id',
  `coupon_id` bigint NOT NULL DEFAULT 0 COMMENT '使用的优惠券',
  `coupon_amount` bigint NOT NULL DEFAULT 0 COMMENT '优惠券抵扣金额（分）',
  `freight_amount` bigint NOT NULL DEFAULT 0 COMMENT '运费金额（分）',
  `payment_amount` bigint NOT NULL DEFAULT 0 COMMENT '应付总额（分）',
  `payment_time` datetime NULL DEFAULT NULL COMMENT '支付时间',
  `payment_method` tinyint NULL DEFAULT NULL COMMENT '支付方式(1：微信JSAPI；2：支付宝；3：余额；4：微信APP)',
  `out_trade_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '微信支付等第三方支付平台的商户订单号',
  `transaction_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '微信支付订单号',
  `out_refund_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商户退款单号',
  `refund_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '微信退款单号',
  `delivery_time` datetime NULL DEFAULT NULL COMMENT '发货时间',
  `receive_time` datetime NULL DEFAULT NULL COMMENT '确认收货时间',
  `comment_time` datetime NULL DEFAULT NULL COMMENT '评价时间',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除（1：已删除，0：未删除）',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `index_order_sn`(`order_no`) USING BTREE COMMENT '订单号唯一索引',
  UNIQUE INDEX `index_otn`(`out_trade_no`) USING BTREE COMMENT '商户订单号唯一索引',
  UNIQUE INDEX `index_ti`(`transaction_id`) USING BTREE COMMENT '商户支付单号唯一索引',
  UNIQUE INDEX `index_orn`(`out_refund_no`) USING BTREE COMMENT '商户退款单号唯一索引',
  UNIQUE INDEX `index_ri`(`refund_id`) USING BTREE COMMENT '退款单号唯一索引'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单详情表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of oms_order
-- ----------------------------

-- ----------------------------
-- Table structure for oms_order_delivery
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_delivery`;
CREATE TABLE `oms_order_delivery`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_id` bigint NOT NULL COMMENT '订单id',
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
  `delivery_status` tinyint NULL DEFAULT 0 COMMENT '物流状态【0->运输中；1->已收货】',
  `delivery_time` datetime NULL DEFAULT NULL COMMENT '发货时间',
  `receive_time` datetime NULL DEFAULT NULL COMMENT '确认收货时间',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除【0->正常；1->已删除】',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单物流记录表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of oms_order_delivery
-- ----------------------------

-- ----------------------------
-- Table structure for oms_order_item
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_item`;
CREATE TABLE `oms_order_item`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_id` bigint NOT NULL COMMENT '订单ID',
  `spu_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品名称',
  `sku_id` bigint NOT NULL DEFAULT 0 COMMENT '商品ID',
  `sku_sn` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '商品编码',
  `sku_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '规格名称',
  `pic_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '商品图片',
  `price` bigint NOT NULL DEFAULT 0 COMMENT '商品单价(单位：分)',
  `quantity` int NULL DEFAULT NULL COMMENT '商品数量',
  `total_amount` bigint NOT NULL DEFAULT 0 COMMENT '商品总价(单位：分)',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除标识(1:已删除；0:正常)',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_order_id`(`order_id`) USING BTREE COMMENT '订单id索引'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单商品信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of oms_order_item
-- ----------------------------

-- ----------------------------
-- Table structure for oms_order_log
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_log`;
CREATE TABLE `oms_order_log`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_id` bigint NOT NULL COMMENT '订单id',
  `user` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '操作人[用户；系统；后台管理员]',
  `detail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '操作详情',
  `order_status` int NULL DEFAULT NULL COMMENT '操作时订单状态',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '备注',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除【0->正常；1->已删除】',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单操作历史记录' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of oms_order_log
-- ----------------------------

-- ----------------------------
-- Table structure for oms_order_pay
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_pay`;
CREATE TABLE `oms_order_pay`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_id` bigint NOT NULL COMMENT '订单id',
  `pay_sn` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '支付流水号',
  `pay_amount` bigint NOT NULL DEFAULT 0 COMMENT '应付总额(分)',
  `pay_time` datetime NULL DEFAULT NULL COMMENT '支付时间',
  `pay_type` tinyint NULL DEFAULT NULL COMMENT '支付方式【1->支付宝；2->微信；3->银联； 4->货到付款；】',
  `pay_status` tinyint NULL DEFAULT NULL COMMENT '支付状态',
  `confirm_time` datetime NULL DEFAULT NULL COMMENT '确认时间',
  `callback_content` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '回调内容',
  `callback_time` datetime NULL DEFAULT NULL COMMENT '回调时间',
  `pay_subject` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '交易内容',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除【0->正常；1->已删除】',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '支付信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of oms_order_pay
-- ----------------------------

-- ----------------------------
-- Table structure for oms_order_refund
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_refund`;
CREATE TABLE `oms_order_refund`  (
  `id` bigint NOT NULL COMMENT '主键',
  `order_id` bigint NOT NULL COMMENT '订单ID',
  `reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '退款原因',
  `status` tinyint NOT NULL COMMENT '状态（0：等待审批，1：已批准，-1：已拒绝）',
  `total_fee` bigint NULL DEFAULT NULL COMMENT '订单总金额（单位：分）',
  `refund_fee` bigint NULL DEFAULT NULL COMMENT '退款金额（单位：分）',
  `approver_id` bigint NULL DEFAULT NULL COMMENT '审批人ID',
  `approval_time` datetime NULL DEFAULT NULL COMMENT '审批时间',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `update_by` bigint NULL DEFAULT NULL COMMENT '更新人ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint NULL DEFAULT NULL COMMENT '逻辑删除标识（1：已删除，0：未删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '订单退款记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oms_order_refund
-- ----------------------------

-- ----------------------------
-- Table structure for oms_order_setting
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_setting`;
CREATE TABLE `oms_order_setting`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `flash_order_overtime` int NULL DEFAULT NULL COMMENT '秒杀订单超时关闭时间(分)',
  `normal_order_overtime` int NULL DEFAULT NULL COMMENT '正常订单超时时间(分)',
  `confirm_overtime` int NULL DEFAULT NULL COMMENT '发货后自动确认收货时间（天）',
  `finish_overtime` int NULL DEFAULT NULL COMMENT '自动完成交易时间，不能申请退货（天）',
  `comment_overtime` int NULL DEFAULT NULL COMMENT '订单完成后自动好评时间（天）',
  `member_level` tinyint NULL DEFAULT NULL COMMENT '会员等级【0-不限会员等级，全部通用；其他-对应的其他会员等级】',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除【0->正常；1->已删除】',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单配置信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of oms_order_setting
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

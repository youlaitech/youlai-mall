/*
 Navicat Premium Data Transfer

 Source Server         : www.youlai.store
 Source Server Type    : MySQL
 Source Server Version : 80022
 Source Host           : www.youlai.store:3306
 Source Schema         : youlai

 Target Server Type    : MySQL
 Target Server Version : 80022
 File Encoding         : 65001

 Date: 10/02/2021 22:05:51
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details`  (
  `client_id` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `resource_ids` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `client_secret` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `scope` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `authorized_grant_types` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `web_server_redirect_uri` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `authorities` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `access_token_validity` int(0) NULL DEFAULT NULL,
  `refresh_token_validity` int(0) NULL DEFAULT NULL,
  `additional_information` varchar(4096) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `autoapprove` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`client_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oauth_client_details
-- ----------------------------
INSERT INTO `oauth_client_details` VALUES ('test', NULL, '123456', 'all', 'client_credentials', NULL, NULL, NULL, NULL, NULL, 'false');
INSERT INTO `oauth_client_details` VALUES ('test-admin', '', '$2a$10$dLq3.pXNwTNqWabsRfJX4ej8Htk/vUWuHh.LvITq5BrU8u.dYvZpC', 'all', 'password,refresh_token,sms_code,client_credentials,authorization_code,implicit', 'http://www.baidu.com', NULL, 3600, 7200, NULL, 'true');
INSERT INTO `oauth_client_details` VALUES ('youlai-admin', '求别改数据', '123456', 'all', 'password,client_credentials,refresh_token,authorization_code', '', NULL, 3600, 7200, NULL, 'true');
INSERT INTO `oauth_client_details` VALUES ('youlai-mall-weapp', '求别改数据', '123456', 'all', 'authorization_code,password,refresh_token,implicit', NULL, NULL, 3600, 7200, NULL, 'true');

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '部门名称',
  `parent_id` int(0) NULL DEFAULT 0 COMMENT '父节点id',
  `tree_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '父节点id路径',
  `sort` int(0) NULL DEFAULT 0 COMMENT '显示顺序',
  `leader` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '负责人',
  `mobile` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱地址',
  `status` tinyint(1) NULL DEFAULT 0 COMMENT '部门状态（0正常 1停用）',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '删除标志（0存在 1删除）',
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '部门表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES (1, '技术部', 0, '0', 1, '张三', '17621590365', '1490493387@qq.com', 1, 0, '2021-01-26 13:52:50', '2021-02-10 12:29:32');
INSERT INTO `sys_dept` VALUES (2, '测试部', 0, '0', 2, '李四', NULL, NULL, 1, 0, '2021-01-26 13:53:46', '2021-01-26 13:53:58');

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键 ',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '类型名称',
  `code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '类型编码',
  `status` tinyint(1) NULL DEFAULT 0 COMMENT '状态（0-正常 ,1-停用）',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `type_code`(`code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '字典类型表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES (1, '性别', 'gender', 1, '性别', '2019-12-06 19:03:32', '2021-02-08 14:58:01');
INSERT INTO `sys_dict` VALUES (11, '授权方式', 'grant_type', 1, NULL, '2020-10-17 08:09:50', '2021-01-31 09:48:24');
INSERT INTO `sys_dict` VALUES (15, '物流渠道', 'logistics_channel', 1, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (17, '设备类型', 'device', 1, NULL, '2021-02-08 21:07:42', '2021-02-08 21:07:42');

-- ----------------------------
-- Table structure for sys_dict_item
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_item`;
CREATE TABLE `sys_dict_item`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '字典项名称',
  `value` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '字典项值',
  `dict_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '字典编码',
  `sort` int(0) NULL DEFAULT 0 COMMENT '排序',
  `status` tinyint(1) NULL DEFAULT 0 COMMENT '状态（0 停用 1正常）',
  `defaulted` tinyint(1) NULL DEFAULT 0 COMMENT '是否默认（0否 1是）',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '备注',
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '字典数据表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict_item
-- ----------------------------
INSERT INTO `sys_dict_item` VALUES (1, '男', '1', 'gender', 1, 1, 0, '性别男', '2019-05-05 13:07:52', '2019-07-02 14:23:05');
INSERT INTO `sys_dict_item` VALUES (2, '女', '2', 'gender', 2, 1, 0, '性别女', '2019-04-19 11:33:00', '2019-07-02 14:23:05');
INSERT INTO `sys_dict_item` VALUES (5, '未知', '0', 'gender', 1, 1, 0, '', '2020-10-17 08:09:31', '2020-10-17 08:09:31');
INSERT INTO `sys_dict_item` VALUES (6, '密码模式', 'password', 'grant_type', 1, 1, 0, '', '2020-10-17 09:11:52', '2021-01-31 09:48:18');
INSERT INTO `sys_dict_item` VALUES (7, '授权码模式', 'authorization_code', 'grant_type', 1, 1, 0, '', '2020-10-17 09:12:15', '2020-12-14 10:11:00');
INSERT INTO `sys_dict_item` VALUES (8, '客户端模式', 'client_credentials', 'grant_type', 1, 1, 0, '', '2020-10-17 09:12:36', '2020-12-14 10:11:00');
INSERT INTO `sys_dict_item` VALUES (9, '刷新模式', 'refresh_token', 'grant_type', 1, 1, 0, '', '2020-10-17 09:12:57', '2021-01-08 17:33:12');
INSERT INTO `sys_dict_item` VALUES (10, '简化模式', 'implicit', 'grant_type', 1, 1, 0, '', '2020-10-17 09:13:23', '2020-12-14 10:11:00');
INSERT INTO `sys_dict_item` VALUES (11, '后端开发', 'Back-end development', 'project', 1, 1, 0, '', NULL, NULL);
INSERT INTO `sys_dict_item` VALUES (12, '前端开发人员', 'Front-end development', 'project', 1, 1, 0, '', NULL, NULL);
INSERT INTO `sys_dict_item` VALUES (13, '测试人员', 'Test development', 'project', 1, 1, 0, '', NULL, NULL);
INSERT INTO `sys_dict_item` VALUES (14, '顺丰速运', 'SF', 'logistics_channel', 1, 1, 0, '', NULL, NULL);
INSERT INTO `sys_dict_item` VALUES (15, '中通快递', 'ZTO', 'logistics_channel', 1, 1, 0, '', NULL, NULL);
INSERT INTO `sys_dict_item` VALUES (16, '圆通速递', 'YTO', 'logistics_channel', 1, 1, 0, '', NULL, NULL);
INSERT INTO `sys_dict_item` VALUES (17, '韵达速递', 'YD', 'logistics_channel', 1, 1, 0, '', NULL, NULL);
INSERT INTO `sys_dict_item` VALUES (18, '京东快递', 'JD', 'logistics_channel', 1, 1, 0, '', NULL, NULL);
INSERT INTO `sys_dict_item` VALUES (19, '百世快递', 'HTKY', 'logistics_channel', 1, 1, 0, '', NULL, NULL);
INSERT INTO `sys_dict_item` VALUES (20, '邮政快递包裹', 'YZPY', 'logistics_channel', 1, 1, 0, '', NULL, NULL);
INSERT INTO `sys_dict_item` VALUES (21, 'EMS', 'EMS', 'logistics_channel', 1, 1, 0, '', NULL, NULL);
INSERT INTO `sys_dict_item` VALUES (22, '德邦快递', 'DBL', 'logistics_channel', 1, 1, 0, '', NULL, NULL);
INSERT INTO `sys_dict_item` VALUES (23, '宅急送', 'ZJS', 'logistics_channel', 1, 1, 0, '', NULL, NULL);
INSERT INTO `sys_dict_item` VALUES (26, '双性', 'middle', 'gender', 1, 1, 0, '', '2021-02-08 16:10:43', '2021-02-08 16:10:43');
INSERT INTO `sys_dict_item` VALUES (27, '摄像头', 'camera', 'device', 1, 1, 0, '', '2021-02-08 21:08:19', '2021-02-08 21:08:19');
INSERT INTO `sys_dict_item` VALUES (28, '人像卡口', 'image_screen', 'device', 1, 1, 0, '', '2021-02-08 21:09:29', '2021-02-08 21:09:29');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '菜单名称',
  `parent_id` bigint(0) NULL DEFAULT NULL COMMENT '父菜单ID',
  `type` int(0) NULL DEFAULT 0,
  `path` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '路由路径',
  `component` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组件路径',
  `redirect` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '跳转路径',
  `icon` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '菜单图标',
  `sort` int(0) NULL DEFAULT 0 COMMENT '排序',
  `visible` tinyint(1) NULL DEFAULT 1 COMMENT '状态：0-禁用 1-开启',
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 31 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '菜单管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, '系统管理', 0, 0, '/admin', 'Layout', '', 'table', 1, 1, '2020-09-23 09:12:21', '2021-02-06 15:04:07');
INSERT INTO `sys_menu` VALUES (2, '用户管理', 1, 0, 'user', 'admin/user/index', '', 'user', 1, 1, NULL, '2021-02-06 14:52:56');
INSERT INTO `sys_menu` VALUES (4, '菜单管理', 1, 0, 'menu', 'admin/menu/index', NULL, 'tree-table', 8, 1, '2020-09-23 09:12:21', '2021-02-02 12:50:30');
INSERT INTO `sys_menu` VALUES (5, '字典管理', 1, 0, 'dict', 'admin/dict/index', NULL, 'education', 10, 1, '2020-09-23 09:12:21', '2021-02-01 19:06:31');
INSERT INTO `sys_menu` VALUES (6, '部门管理', 1, 0, 'dept', 'admin/dept/index', NULL, 'tree', 1, 1, '2020-09-23 09:12:21', '2021-02-01 18:40:25');
INSERT INTO `sys_menu` VALUES (8, '客户端管理', 1, 0, 'client', 'admin/client/index', NULL, 'tab', 11, 1, '2020-10-17 08:04:08', '2021-02-01 19:06:41');
INSERT INTO `sys_menu` VALUES (9, '营销管理', 0, 0, '/sms', 'Layout', '', 'number', 3, 1, '2020-10-24 15:24:04', '2021-02-06 14:59:06');
INSERT INTO `sys_menu` VALUES (10, '广告管理', 9, 0, 'advert', 'sms/advert/index', NULL, 'documentation', 1, 1, '2020-10-24 15:25:15', '2021-02-01 19:26:21');
INSERT INTO `sys_menu` VALUES (11, '商品管理', 0, 0, '/pms', 'Layout', NULL, 'phone', 2, 1, '2020-10-31 10:44:58', '2021-02-06 14:56:44');
INSERT INTO `sys_menu` VALUES (12, '商品列表', 11, 0, 'product', 'pms/product/index', NULL, 'component', 1, 1, '2020-11-06 11:54:37', '2021-02-01 19:22:17');
INSERT INTO `sys_menu` VALUES (13, '订单管理', 0, 0, '/oms', 'Layout', NULL, 'shopping', 3, 1, '2020-10-31 10:49:46', '2021-02-06 14:57:03');
INSERT INTO `sys_menu` VALUES (14, '订单列表', 13, 0, 'oms/order', NULL, NULL, 'component', 3, 1, '2020-10-31 10:50:23', '2020-10-31 10:50:38');
INSERT INTO `sys_menu` VALUES (15, '会员管理', 0, 0, '/ums', 'Layout', NULL, 'user', 4, 1, '2020-10-31 10:51:07', '2021-02-06 14:57:13');
INSERT INTO `sys_menu` VALUES (16, '会员列表', 15, 0, 'user', 'ums/user/index', NULL, 'peoples', 1, 1, '2020-10-31 10:51:43', '2021-02-01 20:18:49');
INSERT INTO `sys_menu` VALUES (17, '品牌管理', 11, 0, 'brand', 'pms/brand/index', NULL, 'component', 4, 1, '2020-09-23 09:12:21', '2021-02-01 19:25:06');
INSERT INTO `sys_menu` VALUES (18, '类目管理', 11, 0, 'category', 'pms/category/index', NULL, 'component', 3, 1, '2020-09-23 09:12:21', '2021-02-01 19:24:38');
INSERT INTO `sys_menu` VALUES (19, '商品详情', 11, 0, 'product_detail', 'pms/product/detail', NULL, 'component', 2, 1, '2020-11-06 13:16:26', '2021-02-01 19:23:26');
INSERT INTO `sys_menu` VALUES (22, '商品上架', 11, 0, 'product_add', 'pms/product/detail', '', 'component', 2, 1, NULL, '2021-02-01 19:24:19');
INSERT INTO `sys_menu` VALUES (23, '角色管理', 1, 0, 'role', 'admin/role/index', '', 'peoples', 2, 1, NULL, '2021-02-06 14:53:49');
INSERT INTO `sys_menu` VALUES (25, '实验室', 0, 0, '/laboratory', 'Layout', '', 'build', 9, 1, NULL, '2021-02-06 14:57:42');
INSERT INTO `sys_menu` VALUES (26, 'Seata分布式事务', 25, 0, 'seata', 'laboratory/seata', '', 'component', 1, 1, NULL, '2021-02-09 11:20:50');

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限名称',
  `perm` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限标识',
  `method` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `type` tinyint(0) NULL DEFAULT NULL COMMENT '权限类型 1-路由权限 2-按钮权限',
  `menu_id` bigint(0) NULL DEFAULT NULL,
  `gmt_create` datetime(0) NULL DEFAULT NULL,
  `gmt_modified` datetime(0) NULL DEFAULT NULL,
  `perms` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限标识',
  `module_id` bigint(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `id`(`id`, `name`) USING BTREE,
  INDEX `id_2`(`id`, `name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 39 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES (9, '营销管理', '/mall-sms/**', '*', 1, 9, '2020-10-24 15:29:01', '2021-02-05 19:59:09', '/mall-sms/**', 9);
INSERT INTO `sys_permission` VALUES (12, '订单管理', '/mall-oms/**', '*', 1, 13, '2020-10-31 10:40:35', '2021-02-05 19:59:17', '/mall-oms/**', 13);
INSERT INTO `sys_permission` VALUES (13, '会员管理', '/mall-ums/**', '*', 1, 15, '2020-10-31 10:41:08', '2021-02-05 19:59:23', '/mall-ums/**', 15);
INSERT INTO `sys_permission` VALUES (14, '商品管理', '/mall-pms/**', '*', 1, 11, '2020-10-31 10:41:37', '2021-02-05 19:59:01', '/mall-pms/**', 11);
INSERT INTO `sys_permission` VALUES (18, '查询用户', '/youlai-admin/api.admin/v1/users/**', 'GET', 1, 2, '2021-02-02 14:16:07', '2021-02-10 10:19:28', '/youlai-admin/api.admin/v1/users', 2);
INSERT INTO `sys_permission` VALUES (19, '新增', 'system:user:add', NULL, 2, 2, '2021-02-02 14:16:46', '2021-02-03 00:20:37', 'system:user:add', 2);
INSERT INTO `sys_permission` VALUES (21, '修改', 'system:user:edit', NULL, 2, 2, '2021-02-02 14:19:45', '2021-02-03 00:20:32', 'system:user:edit', 2);
INSERT INTO `sys_permission` VALUES (22, '删除', 'system:user:delete', NULL, 2, 2, '2021-02-02 14:20:12', '2021-02-03 00:20:27', 'system:user:delete', 2);
INSERT INTO `sys_permission` VALUES (23, '查询', 'system:user:query', NULL, 2, 2, '2021-02-02 14:20:42', '2021-02-03 00:20:21', 'system:user:query', 2);
INSERT INTO `sys_permission` VALUES (26, '重置密码', 'system:user:reset_password', NULL, 2, 2, '2021-02-05 14:31:53', '2021-02-05 14:33:27', 'system:user:reset_password', 2);
INSERT INTO `sys_permission` VALUES (29, '添加权限', NULL, NULL, 2, NULL, '2021-02-09 14:03:16', '2021-02-09 14:03:16', NULL, NULL);
INSERT INTO `sys_permission` VALUES (30, '新增', '/youlai-admin/api.admin/v1/users/**', 'POST', 1, NULL, '2021-02-10 10:20:08', '2021-02-10 10:20:08', NULL, 2);
INSERT INTO `sys_permission` VALUES (31, '修改用户', '/youlai-admin/api.admin/v1/users/**', 'PUT', 1, NULL, '2021-02-10 10:20:32', '2021-02-10 10:20:32', NULL, 2);
INSERT INTO `sys_permission` VALUES (32, '删除用户', '/youlai-admin/api.admin/v1/users/**', 'DELETE', 1, NULL, '2021-02-10 10:20:47', '2021-02-10 10:20:47', NULL, 2);
INSERT INTO `sys_permission` VALUES (34, '菜单所有', '/youlai-admin/api.admin/v1/menus/**', '*', 1, NULL, '2021-02-10 11:00:26', '2021-02-10 11:00:26', NULL, 4);
INSERT INTO `sys_permission` VALUES (35, '部门所有', '/youlai-admin/api.admin/v1/depts/**', '*', 1, NULL, '2021-02-10 11:02:45', '2021-02-10 11:02:45', NULL, 6);
INSERT INTO `sys_permission` VALUES (36, '角色所有', '/youlai-admin/api.admin/v1/roles/**', '*', 1, NULL, '2021-02-10 11:03:05', '2021-02-10 11:03:05', NULL, 23);
INSERT INTO `sys_permission` VALUES (37, '字典所有', '/youlai-admin/api.admin/v1/dicts/**', '*', 1, NULL, '2021-02-10 11:03:49', '2021-02-10 11:03:49', NULL, 5);
INSERT INTO `sys_permission` VALUES (38, '客户端所有', '/youlai-admin/api.admin/v1/clients/**', '*', 1, NULL, '2021-02-10 11:05:34', '2021-02-10 11:05:34', NULL, 8);
INSERT INTO `sys_permission` VALUES (39, '权限所有', '/youlai-admin/api.admin/v1/permissions/**', '*', 1, NULL, NULL, NULL, NULL, 4);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '角色名称',
  `sort` int(0) NULL DEFAULT NULL COMMENT '显示顺序',
  `status` tinyint(1) NULL DEFAULT 1 COMMENT '角色状态（0正常 1停用）',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '删除标识  (0未删除 1已删除)',
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '超级管理员', 1, 1, 0, '2018-12-23 16:00:00', '2020-09-11 17:04:23');
INSERT INTO `sys_role` VALUES (2, '系统管理员', 2, 1, 0, '2018-12-23 16:00:00', '2020-09-22 17:01:44');
INSERT INTO `sys_role` VALUES (3, '普通用户', 3, 1, 0, '2019-05-05 16:00:00', '2020-09-22 17:09:54');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `role_id` bigint(0) NOT NULL COMMENT '角色ID',
  `menu_id` bigint(0) NOT NULL COMMENT '菜单ID'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色和菜单关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (3, 2);
INSERT INTO `sys_role_menu` VALUES (3, 9);
INSERT INTO `sys_role_menu` VALUES (2, 1);
INSERT INTO `sys_role_menu` VALUES (2, 6);
INSERT INTO `sys_role_menu` VALUES (2, 2);
INSERT INTO `sys_role_menu` VALUES (2, 11);
INSERT INTO `sys_role_menu` VALUES (2, 12);
INSERT INTO `sys_role_menu` VALUES (2, 19);
INSERT INTO `sys_role_menu` VALUES (2, 22);
INSERT INTO `sys_role_menu` VALUES (2, 18);
INSERT INTO `sys_role_menu` VALUES (2, 10);
INSERT INTO `sys_role_menu` VALUES (2, 14);
INSERT INTO `sys_role_menu` VALUES (2, 16);
INSERT INTO `sys_role_menu` VALUES (1, 4);
INSERT INTO `sys_role_menu` VALUES (1, 23);
INSERT INTO `sys_role_menu` VALUES (1, 1);
INSERT INTO `sys_role_menu` VALUES (1, 10);
INSERT INTO `sys_role_menu` VALUES (1, 11);
INSERT INTO `sys_role_menu` VALUES (1, 12);
INSERT INTO `sys_role_menu` VALUES (1, 13);
INSERT INTO `sys_role_menu` VALUES (1, 14);
INSERT INTO `sys_role_menu` VALUES (1, 15);
INSERT INTO `sys_role_menu` VALUES (1, 16);
INSERT INTO `sys_role_menu` VALUES (1, 17);
INSERT INTO `sys_role_menu` VALUES (1, 18);
INSERT INTO `sys_role_menu` VALUES (1, 19);
INSERT INTO `sys_role_menu` VALUES (1, 2);
INSERT INTO `sys_role_menu` VALUES (1, 22);
INSERT INTO `sys_role_menu` VALUES (1, 25);
INSERT INTO `sys_role_menu` VALUES (1, 26);
INSERT INTO `sys_role_menu` VALUES (1, 5);
INSERT INTO `sys_role_menu` VALUES (1, 6);
INSERT INTO `sys_role_menu` VALUES (1, 8);
INSERT INTO `sys_role_menu` VALUES (1, 9);
INSERT INTO `sys_role_menu` VALUES (2, 23);
INSERT INTO `sys_role_menu` VALUES (2, 4);
INSERT INTO `sys_role_menu` VALUES (2, 5);
INSERT INTO `sys_role_menu` VALUES (2, 8);

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission`  (
  `role_id` bigint(0) NULL DEFAULT NULL COMMENT '角色id',
  `permission_id` bigint(0) NULL DEFAULT NULL COMMENT '资源id',
  INDEX `role_id`(`role_id`) USING BTREE,
  INDEX `permission_id`(`permission_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
INSERT INTO `sys_role_permission` VALUES (2, 14);
INSERT INTO `sys_role_permission` VALUES (2, 13);
INSERT INTO `sys_role_permission` VALUES (2, 12);
INSERT INTO `sys_role_permission` VALUES (2, 9);
INSERT INTO `sys_role_permission` VALUES (2, 1);
INSERT INTO `sys_role_permission` VALUES (4, 14);
INSERT INTO `sys_role_permission` VALUES (4, 13);
INSERT INTO `sys_role_permission` VALUES (4, 12);
INSERT INTO `sys_role_permission` VALUES (4, 9);
INSERT INTO `sys_role_permission` VALUES (1, 22);
INSERT INTO `sys_role_permission` VALUES (1, 23);
INSERT INTO `sys_role_permission` VALUES (1, 19);
INSERT INTO `sys_role_permission` VALUES (1, 21);
INSERT INTO `sys_role_permission` VALUES (1, 26);
INSERT INTO `sys_role_permission` VALUES (13, 23);
INSERT INTO `sys_role_permission` VALUES (1, 18);
INSERT INTO `sys_role_permission` VALUES (1, 35);
INSERT INTO `sys_role_permission` VALUES (1, 36);
INSERT INTO `sys_role_permission` VALUES (1, 34);
INSERT INTO `sys_role_permission` VALUES (1, 37);
INSERT INTO `sys_role_permission` VALUES (1, 38);
INSERT INTO `sys_role_permission` VALUES (1, 39);
INSERT INTO `sys_role_permission` VALUES (1, 31);
INSERT INTO `sys_role_permission` VALUES (1, 30);
INSERT INTO `sys_role_permission` VALUES (1, 32);
INSERT INTO `sys_role_permission` VALUES (2, 36);
INSERT INTO `sys_role_permission` VALUES (2, 18);
INSERT INTO `sys_role_permission` VALUES (2, 35);
INSERT INTO `sys_role_permission` VALUES (2, 34);
INSERT INTO `sys_role_permission` VALUES (2, 39);
INSERT INTO `sys_role_permission` VALUES (2, 37);
INSERT INTO `sys_role_permission` VALUES (2, 38);
INSERT INTO `sys_role_permission` VALUES (2, 26);
INSERT INTO `sys_role_permission` VALUES (2, 19);
INSERT INTO `sys_role_permission` VALUES (2, 22);
INSERT INTO `sys_role_permission` VALUES (2, 23);
INSERT INTO `sys_role_permission` VALUES (2, 21);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `username` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `nickname` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `gender` tinyint(1) NULL DEFAULT 0 COMMENT '性别',
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `dept_id` int(0) NULL DEFAULT NULL COMMENT '部门ID',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '删除标识（0未删除 1已删除）',
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '用户头像',
  `mobile` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系方式',
  `status` tinyint(1) NULL DEFAULT 0 COMMENT '用户状态（0正常 1禁用）',
  `email` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户邮箱',
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `login_name`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1358627050295459842 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'root', '超级管理员', 1, '$2a$10$P97nHj/AVu6JBVCxmj5qEOwsI7rUhFeyu.DrK4ER7sebzv8jp7R5S', 0, 0, 'https://gitee.com/haoxr/image/raw/master/default/807b1042ed4c674d97bcf1f2976234d.jpg', '17621590365', 1, '1490493387@qq.com', '2021-02-10 12:27:30', '2021-02-10 12:29:21');
INSERT INTO `sys_user` VALUES (2, 'admin', '系统管理员', 1, '$2a$10$dLq3.pXNwTNqWabsRfJX4ej8Htk/vUWuHh.LvITq5BrU8u.dYvZpC', 1, 0, 'https://gitee.com/haoxr/image/raw/master/default/807b1042ed4c674d97bcf1f2976234d.jpg', '17621210366', 1, '1490493387@qq.com', '2019-10-10 13:41:22', '2021-02-10 12:29:13');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `user_id` bigint(0) NOT NULL COMMENT '用户ID',
  `role_id` bigint(0) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户和角色关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1);
INSERT INTO `sys_user_role` VALUES (2, 2);
INSERT INTO `sys_user_role` VALUES (1358627050295459842, 1);

SET FOREIGN_KEY_CHECKS = 1;

/*
 Navicat Premium Data Transfer

 Source Server         : www.youlai.tech
 Source Server Type    : MySQL
 Source Server Version : 80023
 Source Host           : www.youlai.tech:3306
 Source Schema         : youlai

 Target Server Type    : MySQL
 Target Server Version : 80023
 File Encoding         : 65001

 Date: 21/05/2021 00:43:14
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
INSERT INTO `oauth_client_details` VALUES ('client', NULL, '123456', 'all', 'authorization_code,password,refresh_token,implicit', NULL, NULL, 3600, 7200, NULL, 'true');
INSERT INTO `oauth_client_details` VALUES ('youlai-system', '', '123456', 'all', 'password,client_credentials,refresh_token,authorization_code', '', NULL, 3600, 7200, NULL, 'true');
INSERT INTO `oauth_client_details` VALUES ('youlai-mall-weapp', '', '123456', 'all', 'authorization_code,password,refresh_token,implicit', NULL, NULL, 3600, 7200, NULL, 'true');

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
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '部门表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES (1, '技术部', 10, '0,1,10', 1, '张三45', '17621590365', '1490493387@qq.com', 1, 0, '2021-01-26 13:52:50', '2021-03-16 10:09:53');

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
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '字典类型表' ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB AUTO_INCREMENT = 36 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '字典数据表' ROW_FORMAT = Dynamic;

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

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '菜单名称',
  `parent_id` bigint(0) NULL DEFAULT NULL COMMENT '父菜单ID',
  `path` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '路由路径',
  `component` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组件路径',
  `redirect` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '跳转路径',
  `icon` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '菜单图标',
  `sort` int(0) NULL DEFAULT 0 COMMENT '排序',
  `visible` tinyint(1) NULL DEFAULT 1 COMMENT '状态：0-禁用 1-开启',
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 67 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '菜单管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, '系统管理', 0, '/admin', 'Layout', '', 'table', 1, 1, '2020-09-23 09:12:21', '2021-03-23 18:33:43');
INSERT INTO `sys_menu` VALUES (2, '用户管理', 1, 'OAuthUserDetails', 'admin/OAuthUserDetails/index', '', 'OAuthUserDetails', 3, 1, NULL, '2021-03-25 15:05:31');
INSERT INTO `sys_menu` VALUES (4, '菜单管理', 1, 'menu', 'admin/menu/index', NULL, 'tree-table', 3, 1, '2020-09-23 09:12:21', '2021-03-09 09:30:44');
INSERT INTO `sys_menu` VALUES (5, '字典管理', 1, 'dict', 'admin/dict/index', NULL, 'education', 5, 1, '2020-09-23 09:12:21', '2021-03-09 09:30:53');
INSERT INTO `sys_menu` VALUES (6, '部门管理', 1, 'dept', 'admin/dept/index', NULL, 'tree', 4, 1, '2020-09-23 09:12:21', '2021-03-09 09:30:50');
INSERT INTO `sys_menu` VALUES (8, '客户端管理', 1, 'client', 'admin/client/index', NULL, 'tab', 6, 1, '2020-10-17 08:04:08', '2021-03-09 09:30:56');
INSERT INTO `sys_menu` VALUES (9, '营销管理', 0, '/sms', 'Layout', '', 'number', 3, 1, '2020-10-24 15:24:04', '2021-03-02 18:00:13');
INSERT INTO `sys_menu` VALUES (10, '广告管理', 9, 'advert', 'sms/advert/index', NULL, 'documentation', 1, 1, '2020-10-24 15:25:15', '2021-02-01 19:26:21');
INSERT INTO `sys_menu` VALUES (11, '商品管理', 0, '/pms', 'Layout', NULL, 'phone', 2, 1, '2020-10-31 10:44:58', '2021-03-25 22:25:01');
INSERT INTO `sys_menu` VALUES (12, '商品列表', 11, 'product', 'pms/product/index', NULL, 'component', 1, 1, '2020-11-06 11:54:37', '2021-03-11 20:18:14');
INSERT INTO `sys_menu` VALUES (13, '订单管理', 0, '/oms', 'Layout', NULL, 'shopping', 3, 1, '2020-10-31 10:49:46', '2021-03-02 18:00:08');
INSERT INTO `sys_menu` VALUES (14, '订单列表', 13, 'order', 'oms/order', '', 'component', 3, 1, '2020-10-31 10:50:23', '2021-03-25 19:52:05');
INSERT INTO `sys_menu` VALUES (15, '会员管理', 0, '/ums', 'Layout', NULL, 'OAuthUserDetails', 4, 1, '2020-10-31 10:51:07', '2021-02-06 14:57:13');
INSERT INTO `sys_menu` VALUES (16, '会员列表', 15, 'OAuthUserDetails', 'ums/OAuthUserDetails/index', NULL, 'peoples', 1, 1, '2020-10-31 10:51:43', '2021-03-02 10:41:56');
INSERT INTO `sys_menu` VALUES (17, '品牌管理', 11, 'brand', 'pms/brand/index', NULL, 'component', 4, 1, '2020-09-23 09:12:21', '2021-02-01 19:25:06');
INSERT INTO `sys_menu` VALUES (18, '商品分类', 11, 'category', 'pms/category/index', NULL, 'component', 3, 1, '2020-09-23 09:12:21', '2021-03-17 11:17:06');
INSERT INTO `sys_menu` VALUES (22, '商品上架', 11, 'product_add', 'pms/product/detail', '', 'component', 2, 1, NULL, '2021-02-19 18:43:23');
INSERT INTO `sys_menu` VALUES (23, '角色管理', 1, 'role', 'admin/role/index', '', 'peoples', 3, 1, NULL, '2021-03-29 10:44:13');
INSERT INTO `sys_menu` VALUES (25, '实验室', 0, '/laboratory', 'Layout', '', 'build', 9, 1, NULL, '2021-02-06 14:57:42');
INSERT INTO `sys_menu` VALUES (26, 'Seata分布式事务', 25, 'seata', 'laboratory/seata', '', 'component', 1, 1, NULL, '2021-02-09 11:20:50');
INSERT INTO `sys_menu` VALUES (34, '特殊权限', 0, '/common', 'Layout', '', 'checkbox', 9999, 1, '2021-02-27 23:34:16', '2021-03-07 14:10:18');
INSERT INTO `sys_menu` VALUES (41, '登录记录', 1, 'login_record', 'admin/record/login/index', '', 'list', 7, 1, '2021-03-09 09:40:37', '2021-03-09 14:30:32');

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限名称',
  `perm` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限标识',
  `method` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求方法类型（POST/PUT/DELETE/PATCH）',
  `type` tinyint(0) NULL DEFAULT NULL COMMENT '权限类型 1-路由权限 2-按钮权限',
  `module_id` bigint(0) NULL DEFAULT NULL COMMENT '菜单模块ID\r\n',
  `gmt_create` datetime(0) NULL DEFAULT NULL,
  `gmt_modified` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `id`(`id`, `name`) USING BTREE,
  INDEX `id_2`(`id`, `name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 69 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES (9, '营销管理', '/mall-sms/**', '*', 1, 9, '2020-10-24 15:29:01', '2021-02-05 19:59:09');
INSERT INTO `sys_permission` VALUES (12, '订单管理', '/mall-oms/**', '*', 1, 13, '2020-10-31 10:40:35', '2021-02-05 19:59:17');
INSERT INTO `sys_permission` VALUES (13, '会员管理', '/mall-ums/**', '*', 1, 15, '2020-10-31 10:41:08', '2021-02-05 19:59:23');
INSERT INTO `sys_permission` VALUES (14, '商品管理', '/mall-pms/**', '*', 1, 11, '2020-10-31 10:41:37', '2021-02-05 19:59:01');
INSERT INTO `sys_permission` VALUES (18, '查询用户', '/youlai-system/api.admin/v1/users/**', 'GET', 1, 2, '2021-02-02 14:16:07', '2021-02-10 10:19:28');
INSERT INTO `sys_permission` VALUES (19, '新增', 'system:OAuthUserDetails:add', NULL, 2, 2, '2021-02-02 14:16:46', '2021-02-22 17:03:21');
INSERT INTO `sys_permission` VALUES (21, '修改', 'system:OAuthUserDetails:edit', NULL, 2, 2, '2021-02-02 14:19:45', '2021-02-03 00:20:32');
INSERT INTO `sys_permission` VALUES (22, '删除', 'system:OAuthUserDetails:delete', NULL, 2, 2, '2021-02-02 14:20:12', '2021-02-28 17:22:52');
INSERT INTO `sys_permission` VALUES (23, '查询', 'system:OAuthUserDetails:query', NULL, 2, 2, '2021-02-02 14:20:42', '2021-02-03 00:20:21');
INSERT INTO `sys_permission` VALUES (26, '重置密码', 'system:OAuthUserDetails:reset_password', NULL, 2, 2, '2021-02-05 14:31:53', '2021-02-05 14:33:27');
INSERT INTO `sys_permission` VALUES (30, '新增用户', '/youlai-system/api.admin/v1/users/**', 'POST', 1, 2, '2021-02-10 10:20:08', '2021-02-10 10:20:08');
INSERT INTO `sys_permission` VALUES (31, '修改用户', '/youlai-system/api.admin/v1/users/**', 'PUT', 1, 2, '2021-02-10 10:20:32', '2021-02-10 10:20:32');
INSERT INTO `sys_permission` VALUES (32, '删除用户', '/youlai-system/api.admin/v1/users/**', 'DELETE', 1, 2, '2021-02-10 10:20:47', '2021-02-10 10:20:47');
INSERT INTO `sys_permission` VALUES (34, '菜单所有', '/youlai-system/api.admin/v1/menus/**', '*', 1, 4, '2021-02-10 11:00:26', '2021-02-10 11:00:26');
INSERT INTO `sys_permission` VALUES (35, '部门所有', '/youlai-system/api.admin/v1/depts/**', '*', 1, 6, '2021-02-10 11:02:45', '2021-02-10 11:02:45');
INSERT INTO `sys_permission` VALUES (36, '角色所有', '/youlai-system/api.admin/v1/roles/**', '*', 1, 23, '2021-02-10 11:03:05', '2021-03-10 17:36:53');
INSERT INTO `sys_permission` VALUES (37, '字典所有', '/youlai-system/api.admin/v1/dicts/**', '*', 1, 5, '2021-02-10 11:03:49', '2021-02-10 11:03:49');
INSERT INTO `sys_permission` VALUES (38, '客户端所有', '/youlai-system/api.admin/v1/clients/**', '*', 1, 8, '2021-02-10 11:05:34', '2021-02-10 11:05:34');
INSERT INTO `sys_permission` VALUES (39, '权限所有', '/youlai-system/api.admin/v1/permissions/**', '*', 1, 4, NULL, NULL);
INSERT INTO `sys_permission` VALUES (42, '字典项所有', '/youlai-system/api.admin/v1/dict_items/**', '*', 1, 5, '2021-02-14 10:48:17', '2021-03-21 16:11:38');
INSERT INTO `sys_permission` VALUES (44, '新增', 'system:dict:add', NULL, 2, 5, '2021-02-23 13:24:31', '2021-02-23 13:24:31');
INSERT INTO `sys_permission` VALUES (45, '文件上传', '/youlai-system/api.admin/v1/files', '*', 1, 34, '2021-02-27 23:35:27', '2021-02-27 23:35:27');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '角色名称',
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色编码',
  `sort` int(0) NULL DEFAULT NULL COMMENT '显示顺序',
  `status` tinyint(1) NULL DEFAULT 1 COMMENT '角色状态（0正常 1停用）',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '删除标识  (0未删除 1已删除)',
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 56 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '超级管理员', 'ROOT', 1, 1, 0, '2018-12-23 16:00:00', '2020-09-11 17:04:23');
INSERT INTO `sys_role` VALUES (2, '系统管理员', 'ADMIN', 2, 1, 0, '2018-12-23 16:00:00', '2021-03-25 12:39:54');
INSERT INTO `sys_role` VALUES (3, '游客', 'GUEST', 3, 1, 0, '2019-05-05 16:00:00', '2021-03-26 10:35:19');

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
INSERT INTO `sys_role_menu` VALUES (3, 12);
INSERT INTO `sys_role_menu` VALUES (3, 1);
INSERT INTO `sys_role_menu` VALUES (3, 11);
INSERT INTO `sys_role_menu` VALUES (3, 2);
INSERT INTO `sys_role_menu` VALUES (3, 6);
INSERT INTO `sys_role_menu` VALUES (2, 1);
INSERT INTO `sys_role_menu` VALUES (2, 10);
INSERT INTO `sys_role_menu` VALUES (2, 13);
INSERT INTO `sys_role_menu` VALUES (2, 14);
INSERT INTO `sys_role_menu` VALUES (2, 15);
INSERT INTO `sys_role_menu` VALUES (2, 16);
INSERT INTO `sys_role_menu` VALUES (2, 23);
INSERT INTO `sys_role_menu` VALUES (2, 2);
INSERT INTO `sys_role_menu` VALUES (2, 25);
INSERT INTO `sys_role_menu` VALUES (2, 26);
INSERT INTO `sys_role_menu` VALUES (2, 4);
INSERT INTO `sys_role_menu` VALUES (2, 6);
INSERT INTO `sys_role_menu` VALUES (2, 8);
INSERT INTO `sys_role_menu` VALUES (2, 9);
INSERT INTO `sys_role_menu` VALUES (3, 23);
INSERT INTO `sys_role_menu` VALUES (3, 4);
INSERT INTO `sys_role_menu` VALUES (3, 5);
INSERT INTO `sys_role_menu` VALUES (3, 10);
INSERT INTO `sys_role_menu` VALUES (3, 14);
INSERT INTO `sys_role_menu` VALUES (2, 5);
INSERT INTO `sys_role_menu` VALUES (1, 25);
INSERT INTO `sys_role_menu` VALUES (2, 41);
INSERT INTO `sys_role_menu` VALUES (18, 1);
INSERT INTO `sys_role_menu` VALUES (26, 1);
INSERT INTO `sys_role_menu` VALUES (26, 2);
INSERT INTO `sys_role_menu` VALUES (26, 23);
INSERT INTO `sys_role_menu` VALUES (26, 4);
INSERT INTO `sys_role_menu` VALUES (26, 5);
INSERT INTO `sys_role_menu` VALUES (26, 6);
INSERT INTO `sys_role_menu` VALUES (26, 8);
INSERT INTO `sys_role_menu` VALUES (26, 11);
INSERT INTO `sys_role_menu` VALUES (26, 12);
INSERT INTO `sys_role_menu` VALUES (26, 22);
INSERT INTO `sys_role_menu` VALUES (2, 34);
INSERT INTO `sys_role_menu` VALUES (18, 47);
INSERT INTO `sys_role_menu` VALUES (15, 2);
INSERT INTO `sys_role_menu` VALUES (15, 1);
INSERT INTO `sys_role_menu` VALUES (2, 61);
INSERT INTO `sys_role_menu` VALUES (2, 62);
INSERT INTO `sys_role_menu` VALUES (2, 63);
INSERT INTO `sys_role_menu` VALUES (3, 61);
INSERT INTO `sys_role_menu` VALUES (3, 62);
INSERT INTO `sys_role_menu` VALUES (3, 8);
INSERT INTO `sys_role_menu` VALUES (1, 23);
INSERT INTO `sys_role_menu` VALUES (2, 11);
INSERT INTO `sys_role_menu` VALUES (2, 12);
INSERT INTO `sys_role_menu` VALUES (2, 18);
INSERT INTO `sys_role_menu` VALUES (2, 22);
INSERT INTO `sys_role_menu` VALUES (49, 61);
INSERT INTO `sys_role_menu` VALUES (49, 62);
INSERT INTO `sys_role_menu` VALUES (49, 1);
INSERT INTO `sys_role_menu` VALUES (49, 11);
INSERT INTO `sys_role_menu` VALUES (49, 13);
INSERT INTO `sys_role_menu` VALUES (50, 11);
INSERT INTO `sys_role_menu` VALUES (50, 12);
INSERT INTO `sys_role_menu` VALUES (50, 1);
INSERT INTO `sys_role_menu` VALUES (50, 23);
INSERT INTO `sys_role_menu` VALUES (50, 61);
INSERT INTO `sys_role_menu` VALUES (1, 64);
INSERT INTO `sys_role_menu` VALUES (51, 1);
INSERT INTO `sys_role_menu` VALUES (51, 2);
INSERT INTO `sys_role_menu` VALUES (51, 23);
INSERT INTO `sys_role_menu` VALUES (51, 4);
INSERT INTO `sys_role_menu` VALUES (51, 62);
INSERT INTO `sys_role_menu` VALUES (1, 2);
INSERT INTO `sys_role_menu` VALUES (49, 2);
INSERT INTO `sys_role_menu` VALUES (49, 23);
INSERT INTO `sys_role_menu` VALUES (53, 1);
INSERT INTO `sys_role_menu` VALUES (53, 12);
INSERT INTO `sys_role_menu` VALUES (53, 18);
INSERT INTO `sys_role_menu` VALUES (53, 61);
INSERT INTO `sys_role_menu` VALUES (53, 62);
INSERT INTO `sys_role_menu` VALUES (55, 1);
INSERT INTO `sys_role_menu` VALUES (55, 11);
INSERT INTO `sys_role_menu` VALUES (55, 12);
INSERT INTO `sys_role_menu` VALUES (55, 2);
INSERT INTO `sys_role_menu` VALUES (55, 6);

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
INSERT INTO `sys_role_permission` VALUES (1, 23);
INSERT INTO `sys_role_permission` VALUES (1, 19);
INSERT INTO `sys_role_permission` VALUES (1, 21);
INSERT INTO `sys_role_permission` VALUES (1, 26);
INSERT INTO `sys_role_permission` VALUES (1, 18);
INSERT INTO `sys_role_permission` VALUES (1, 35);
INSERT INTO `sys_role_permission` VALUES (1, 36);
INSERT INTO `sys_role_permission` VALUES (1, 34);
INSERT INTO `sys_role_permission` VALUES (1, 37);
INSERT INTO `sys_role_permission` VALUES (1, 38);
INSERT INTO `sys_role_permission` VALUES (1, 39);
INSERT INTO `sys_role_permission` VALUES (1, 31);
INSERT INTO `sys_role_permission` VALUES (1, 30);
INSERT INTO `sys_role_permission` VALUES (2, 18);
INSERT INTO `sys_role_permission` VALUES (2, 39);
INSERT INTO `sys_role_permission` VALUES (2, 37);
INSERT INTO `sys_role_permission` VALUES (2, 38);
INSERT INTO `sys_role_permission` VALUES (2, 26);
INSERT INTO `sys_role_permission` VALUES (2, 22);
INSERT INTO `sys_role_permission` VALUES (2, 23);
INSERT INTO `sys_role_permission` VALUES (2, 21);
INSERT INTO `sys_role_permission` VALUES (1, 41);
INSERT INTO `sys_role_permission` VALUES (1, 12);
INSERT INTO `sys_role_permission` VALUES (1, 43);
INSERT INTO `sys_role_permission` VALUES (1, 42);
INSERT INTO `sys_role_permission` VALUES (2, 34);
INSERT INTO `sys_role_permission` VALUES (2, 42);
INSERT INTO `sys_role_permission` VALUES (2, 30);
INSERT INTO `sys_role_permission` VALUES (2, 31);
INSERT INTO `sys_role_permission` VALUES (15, 31);
INSERT INTO `sys_role_permission` VALUES (15, 30);
INSERT INTO `sys_role_permission` VALUES (15, 18);
INSERT INTO `sys_role_permission` VALUES (15, 26);
INSERT INTO `sys_role_permission` VALUES (15, 42);
INSERT INTO `sys_role_permission` VALUES (18, 44);
INSERT INTO `sys_role_permission` VALUES (18, 42);
INSERT INTO `sys_role_permission` VALUES (18, 37);
INSERT INTO `sys_role_permission` VALUES (2, 35);
INSERT INTO `sys_role_permission` VALUES (3, 19);
INSERT INTO `sys_role_permission` VALUES (2, 45);
INSERT INTO `sys_role_permission` VALUES (1, 45);
INSERT INTO `sys_role_permission` VALUES (18, 19);
INSERT INTO `sys_role_permission` VALUES (18, 26);
INSERT INTO `sys_role_permission` VALUES (18, 21);
INSERT INTO `sys_role_permission` VALUES (18, 23);
INSERT INTO `sys_role_permission` VALUES (18, 32);
INSERT INTO `sys_role_permission` VALUES (18, 31);
INSERT INTO `sys_role_permission` VALUES (18, 30);
INSERT INTO `sys_role_permission` VALUES (18, 18);
INSERT INTO `sys_role_permission` VALUES (2, 49);
INSERT INTO `sys_role_permission` VALUES (2, 54);
INSERT INTO `sys_role_permission` VALUES (2, 52);
INSERT INTO `sys_role_permission` VALUES (2, 55);
INSERT INTO `sys_role_permission` VALUES (2, 36);
INSERT INTO `sys_role_permission` VALUES (2, 19);
INSERT INTO `sys_role_permission` VALUES (1, 32);
INSERT INTO `sys_role_permission` VALUES (1, 56);
INSERT INTO `sys_role_permission` VALUES (1, 60);
INSERT INTO `sys_role_permission` VALUES (1, 61);
INSERT INTO `sys_role_permission` VALUES (15, 61);
INSERT INTO `sys_role_permission` VALUES (15, 22);
INSERT INTO `sys_role_permission` VALUES (15, 19);
INSERT INTO `sys_role_permission` VALUES (15, 21);
INSERT INTO `sys_role_permission` VALUES (15, 23);
INSERT INTO `sys_role_permission` VALUES (2, 44);
INSERT INTO `sys_role_permission` VALUES (15, 60);
INSERT INTO `sys_role_permission` VALUES (3, 22);
INSERT INTO `sys_role_permission` VALUES (3, 61);
INSERT INTO `sys_role_permission` VALUES (3, 26);
INSERT INTO `sys_role_permission` VALUES (3, 21);
INSERT INTO `sys_role_permission` VALUES (3, 23);
INSERT INTO `sys_role_permission` VALUES (3, 30);
INSERT INTO `sys_role_permission` VALUES (49, 68);
INSERT INTO `sys_role_permission` VALUES (49, 60);
INSERT INTO `sys_role_permission` VALUES (49, 56);
INSERT INTO `sys_role_permission` VALUES (49, 32);
INSERT INTO `sys_role_permission` VALUES (49, 31);
INSERT INTO `sys_role_permission` VALUES (49, 30);
INSERT INTO `sys_role_permission` VALUES (49, 18);
INSERT INTO `sys_role_permission` VALUES (49, 61);
INSERT INTO `sys_role_permission` VALUES (49, 22);
INSERT INTO `sys_role_permission` VALUES (49, 19);
INSERT INTO `sys_role_permission` VALUES (49, 26);
INSERT INTO `sys_role_permission` VALUES (49, 21);
INSERT INTO `sys_role_permission` VALUES (49, 23);
INSERT INTO `sys_role_permission` VALUES (53, 50);
INSERT INTO `sys_role_permission` VALUES (53, 47);
INSERT INTO `sys_role_permission` VALUES (55, 48);

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
) ENGINE = InnoDB AUTO_INCREMENT = 31 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'root', '超级管理员', 1, '$2a$10$P97nHj/AVu6JBVCxmj5qEOwsI7rUhFeyu.DrK4ER7sebzv8jp7R5S', 0, 0, 'https://gitee.com/haoxr/image/raw/master/default/807b1042ed4c674d97bcf1f2976234d.jpg', '17621590365', 1, '1490493387@qq.com', '2021-02-10 12:27:30', '2021-03-16 08:36:35');
INSERT INTO `sys_user` VALUES (2, 'admin', '系统管理员', 1, '$2a$10$yJSqqr6sTxNuYtA6EKcVUe2I4USFCzJ29sNcRrBvtAkSYcNg5ydQ6', 21, 0, 'https://gitee.com/haoxr/image/raw/master/default/807b1042ed4c674d97bcf1f2976234d.jpg', '17621210366', 1, '1490493387@qq.com', '2019-10-10 13:41:22', '2021-03-25 15:02:19');

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
INSERT INTO `sys_user_role` VALUES (1, 2);
INSERT INTO `sys_user_role` VALUES (2, 1);
INSERT INTO `sys_user_role` VALUES (2, 2);
INSERT INTO `sys_user_role` VALUES (5, 15);
INSERT INTO `sys_user_role` VALUES (6, 3);
INSERT INTO `sys_user_role` VALUES (6, 18);
INSERT INTO `sys_user_role` VALUES (7, 2);
INSERT INTO `sys_user_role` VALUES (7, 15);
INSERT INTO `sys_user_role` VALUES (8, 3);
INSERT INTO `sys_user_role` VALUES (9, 2);
INSERT INTO `sys_user_role` VALUES (10, 26);
INSERT INTO `sys_user_role` VALUES (12, 2);
INSERT INTO `sys_user_role` VALUES (13, 2);
INSERT INTO `sys_user_role` VALUES (17, 1);
INSERT INTO `sys_user_role` VALUES (19, 2);
INSERT INTO `sys_user_role` VALUES (21, 3);
INSERT INTO `sys_user_role` VALUES (21, 15);
INSERT INTO `sys_user_role` VALUES (23, 15);
INSERT INTO `sys_user_role` VALUES (24, 1);
INSERT INTO `sys_user_role` VALUES (25, 14);
INSERT INTO `sys_user_role` VALUES (25, 50);
INSERT INTO `sys_user_role` VALUES (26, 50);
INSERT INTO `sys_user_role` VALUES (31, 15);

SET FOREIGN_KEY_CHECKS = 1;

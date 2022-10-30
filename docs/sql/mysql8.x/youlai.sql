/*
 Navicat Premium Data Transfer

 Source Server         : mysql8.0
 Source Server Type    : MySQL
 Source Server Version : 80023
 Source Host           : localhost:3306
 Source Schema         : youlai

 Target Server Type    : MySQL
 Target Server Version : 80023
 File Encoding         : 65001

 Date: 21/09/2022 00:03:49
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '部门名称',
  `parent_id` bigint NULL DEFAULT 0 COMMENT '父节点id',
  `tree_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '父节点id路径',
  `sort` int NULL DEFAULT 0 COMMENT '显示顺序',
  `status` tinyint NULL DEFAULT 1 COMMENT '状态(1:正常;0:禁用)',
  `deleted` tinyint NULL DEFAULT 0 COMMENT '逻辑删除标识(1:已删除;0:未删除)',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 51 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '部门表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES (1, '有来技术', 0, '0', 1, 1, 0, NULL, NULL);
INSERT INTO `sys_dept` VALUES (2, '研发部门', 1, '0,1', 1, 1, 0, NULL, '2022-04-19 12:46:37');
INSERT INTO `sys_dept` VALUES (3, '测试部门', 1, '0,1', 2, 1, 0, NULL, NULL);
INSERT INTO `sys_dept` VALUES (47, '研发子部门1', 2, '0,1,2', 1, 1, 0, '2022-09-19 22:06:23', '2022-09-19 22:06:23');
INSERT INTO `sys_dept` VALUES (48, '研发子部门2', 2, '0,1,2', 1, 1, 0, '2022-09-19 22:06:41', '2022-09-19 22:06:41');
INSERT INTO `sys_dept` VALUES (49, '测试子部门1', 3, '0,1,3', 1, 1, 0, '2022-09-19 22:06:57', '2022-09-19 22:06:57');
INSERT INTO `sys_dept` VALUES (50, '测试子部门2', 3, '0,1,3', 1, 1, 0, '2022-09-19 22:07:11', '2022-09-19 22:07:11');

-- ----------------------------
-- Table structure for sys_dict_item
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_item`;
CREATE TABLE `sys_dict_item`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `type_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字典类型编码',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '字典项名称',
  `value` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '字典项值',
  `sort` int NULL DEFAULT 0 COMMENT '排序',
  `status` tinyint NULL DEFAULT 0 COMMENT '状态(1:正常;0:禁用)',
  `defaulted` tinyint NULL DEFAULT 0 COMMENT '是否默认(1:是;0:否)',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '备注',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 35 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '字典数据表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dict_item
-- ----------------------------
INSERT INTO `sys_dict_item` VALUES (1, 'gender', '男', '1', 1, 1, 0, NULL, '2019-05-05 13:07:52', '2022-06-12 23:20:39');
INSERT INTO `sys_dict_item` VALUES (2, 'gender', '女', '2', 2, 1, 0, NULL, '2019-04-19 11:33:00', '2019-07-02 14:23:05');
INSERT INTO `sys_dict_item` VALUES (3, 'gender', '未知', '0', 1, 1, 0, NULL, '2020-10-17 08:09:31', '2020-10-17 08:09:31');
INSERT INTO `sys_dict_item` VALUES (6, 'grant_type', '密码模式', 'password', 1, 1, 0, NULL, '2020-10-17 09:11:52', '2021-01-31 09:48:18');
INSERT INTO `sys_dict_item` VALUES (7, 'grant_type', '授权码模式', 'authorization_code', 1, 1, 0, NULL, '2020-10-17 09:12:15', '2020-12-14 10:11:00');
INSERT INTO `sys_dict_item` VALUES (8, 'grant_type', '客户端模式', 'client_credentials', 1, 1, 0, NULL, '2020-10-17 09:12:36', '2020-12-14 10:11:00');
INSERT INTO `sys_dict_item` VALUES (9, 'grant_type', '刷新模式', 'refresh_token', 1, 1, 0, NULL, '2020-10-17 09:12:57', '2021-01-08 17:33:12');
INSERT INTO `sys_dict_item` VALUES (10, 'grant_type', '简化模式', 'implicit', 1, 1, 0, NULL, '2020-10-17 09:13:23', '2020-12-14 10:11:00');
INSERT INTO `sys_dict_item` VALUES (11, 'micro_service', '系统服务', 'youlai-admin', 1, 1, 0, NULL, '2021-06-17 00:14:12', '2021-06-17 00:14:12');
INSERT INTO `sys_dict_item` VALUES (12, 'micro_service', '会员服务', 'youlai-ums', 2, 1, 0, NULL, '2021-06-17 00:15:06', '2021-06-17 00:15:06');
INSERT INTO `sys_dict_item` VALUES (13, 'micro_service', '商品服务', 'youlai-pms', 3, 1, 0, NULL, '2021-06-17 00:15:26', '2021-06-17 00:16:18');
INSERT INTO `sys_dict_item` VALUES (14, 'micro_service', '订单服务', 'youlai-oms', 4, 1, 0, NULL, '2021-06-17 00:15:40', '2021-06-17 00:16:10');
INSERT INTO `sys_dict_item` VALUES (15, 'micro_service', '营销服务', 'youlai-sms', 5, 1, 0, NULL, '2021-06-17 00:16:01', '2021-06-17 00:16:01');
INSERT INTO `sys_dict_item` VALUES (16, 'request_method', '不限', '*', 1, 1, 0, NULL, '2021-06-17 00:18:34', '2021-06-17 00:18:34');
INSERT INTO `sys_dict_item` VALUES (17, 'request_method', 'GET', 'GET', 2, 1, 0, NULL, '2021-06-17 00:18:55', '2021-06-17 00:18:55');
INSERT INTO `sys_dict_item` VALUES (18, 'request_method', 'POST', 'POST', 3, 1, 0, NULL, '2021-06-17 00:19:06', '2021-06-17 00:19:06');
INSERT INTO `sys_dict_item` VALUES (19, 'request_method', 'PUT', 'PUT', 4, 1, 0, NULL, '2021-06-17 00:19:17', '2021-06-17 00:19:17');
INSERT INTO `sys_dict_item` VALUES (20, 'request_method', 'DELETE', 'DELETE', 5, 1, 0, NULL, '2021-06-17 00:19:30', '2021-06-17 00:19:30');
INSERT INTO `sys_dict_item` VALUES (21, 'request_method', 'PATCH', 'PATCH', 6, 1, 0, NULL, '2021-06-17 00:19:42', '2021-06-17 00:19:42');
INSERT INTO `sys_dict_item` VALUES (22, 'grant_type', '验证码', 'captcha', 1, 1, 0, '', '2022-05-28 11:44:28', '2022-05-28 11:44:28');

-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键 ',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '类型名称',
  `code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '类型编码',
  `status` tinyint(1) NULL DEFAULT 0 COMMENT '状态(0:正常;1:禁用)',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `type_code`(`code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 31 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '字典类型表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dict_type
-- ----------------------------
INSERT INTO `sys_dict_type` VALUES (1, '性别', 'gender', 1, NULL, '2019-12-06 19:03:32', '2022-06-12 16:21:28');
INSERT INTO `sys_dict_type` VALUES (2, '授权方式', 'grant_type', 1, NULL, '2020-10-17 08:09:50', '2021-01-31 09:48:24');
INSERT INTO `sys_dict_type` VALUES (3, '微服务列表', 'micro_service', 1, NULL, '2021-06-17 00:13:43', '2021-06-17 00:17:22');
INSERT INTO `sys_dict_type` VALUES (4, '请求方式', 'request_method', 1, NULL, '2021-06-17 00:18:07', '2021-06-17 00:18:07');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `parent_id` bigint NULL DEFAULT NULL COMMENT '父菜单ID',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '菜单名称',
  `path` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '路由路径(浏览器地址栏路径)',
  `component` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组件路径(vue页面完整路径，省略.vue后缀)',
  `icon` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '菜单图标',
  `sort` int NULL DEFAULT 0 COMMENT '排序',
  `visible` tinyint(1) NULL DEFAULT 1 COMMENT '状态(0:禁用;1:开启)',
  `redirect` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '跳转路径',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `type` tinyint NULL DEFAULT NULL COMMENT '菜单类型(1:菜单;2:目录;3:外链)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 40 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '菜单管理' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, 0, '系统管理', '/system', 'Layout', 'system', 1, 1, '/system/user', '2021-08-28 09:12:21', '2021-08-28 09:12:21', 2);
INSERT INTO `sys_menu` VALUES (2, 1, '用户管理', 'user', 'system/user/index', 'user', 1, 1, NULL, '2021-08-28 09:12:21', '2021-08-28 09:12:21', 1);
INSERT INTO `sys_menu` VALUES (3, 1, '角色管理', 'role', 'system/role/index', 'role', 2, 1, NULL, '2021-08-28 09:12:21', '2021-08-28 09:12:21', 1);
INSERT INTO `sys_menu` VALUES (4, 1, '菜单管理', 'cmenu', 'system/menu/index', 'menu', 3, 1, NULL, '2021-08-28 09:12:21', '2021-08-28 09:12:21', 1);
INSERT INTO `sys_menu` VALUES (5, 1, '部门管理', 'dept', 'system/dept/index', 'tree', 4, 1, NULL, '2021-08-28 09:12:21', '2021-08-28 09:12:21', 1);
INSERT INTO `sys_menu` VALUES (6, 1, '字典管理', 'dict', 'system/dict/index', 'dict', 5, 1, NULL, '2021-08-28 09:12:21', '2021-08-28 09:12:21', 1);
INSERT INTO `sys_menu` VALUES (7, 1, '客户端管理', 'client', 'system/client/index', 'client', 6, 1, NULL, '2021-08-28 09:12:21', '2021-08-28 09:12:21', 1);
INSERT INTO `sys_menu` VALUES (9, 0, '营销管理', '/sms', 'Layout', 'number', 5, 1, NULL, '2021-08-28 09:12:21', '2021-08-28 09:12:21', 2);
INSERT INTO `sys_menu` VALUES (10, 9, '广告列表', 'advert', 'sms/advert/index', 'advert', 1, 1, NULL, '2021-08-28 09:12:21', '2021-08-28 09:12:21', 1);
INSERT INTO `sys_menu` VALUES (11, 0, '商品管理', '/pms', 'Layout', 'goods', 2, 1, '/pms/goods', '2021-08-28 09:12:21', '2021-08-28 09:12:21', 1);
INSERT INTO `sys_menu` VALUES (12, 11, '商品列表', 'goods', 'pms/goods/index', 'goods-list', 1, 1, NULL, '2021-08-28 09:12:21', '2021-08-28 09:12:21', 1);
INSERT INTO `sys_menu` VALUES (13, 0, '订单管理', '/oms', 'Layout', 'shopping', 3, 1, '/oms/order', '2021-08-28 09:12:21', '2021-08-28 09:12:21', 1);
INSERT INTO `sys_menu` VALUES (14, 13, '订单列表', 'order', 'oms/order/index', 'order', 1, 1, NULL, '2021-08-28 09:12:21', '2021-08-28 09:12:21', 1);
INSERT INTO `sys_menu` VALUES (15, 0, '会员管理', '/ums', 'Layout', 'user', 4, 1, '/ums/member', '2021-08-28 09:12:21', '2021-08-28 09:12:21', 1);
INSERT INTO `sys_menu` VALUES (16, 15, '会员列表', 'member', 'ums/member/index', 'peoples', 1, 1, NULL, '2021-08-28 09:12:21', '2021-08-28 09:12:21', 1);
INSERT INTO `sys_menu` VALUES (17, 11, '品牌管理', 'brand', 'pms/brand/index', 'brand', 5, 1, NULL, '2021-08-28 09:12:21', '2021-08-28 09:12:21', 1);
INSERT INTO `sys_menu` VALUES (18, 11, '商品分类', 'category', 'pms/category/index', 'menu', 3, 1, NULL, '2021-08-28 09:12:21', '2021-08-28 09:12:21', 1);
INSERT INTO `sys_menu` VALUES (19, 11, '商品上架', 'goods-detail', 'pms/goods/detail', 'publish', 2, 1, NULL, '2021-08-28 09:12:21', '2021-08-28 09:12:21', 1);
INSERT INTO `sys_menu` VALUES (20, 0, '多级菜单', '/multi-level-menu', 'Layout', 'nested', 7, 1, '/nested/level1/level2', '2022-02-16 23:11:00', '2022-02-16 23:11:00', 2);
INSERT INTO `sys_menu` VALUES (21, 20, '菜单一级', 'nested_level1_index', 'nested/level1/index', '', 1, 1, '/nested/level1/level2', '2022-02-16 23:13:38', '2022-02-16 23:13:38', 1);
INSERT INTO `sys_menu` VALUES (22, 21, '菜单二级', 'nested_level1_level2_index', 'nested/level1/level2/index', '', 1, 1, '/nested/level1/level2/level3', '2022-02-16 23:14:23', '2022-02-16 23:14:23', 1);
INSERT INTO `sys_menu` VALUES (23, 22, '菜单三级-1', 'nested_level1_level2_level3_index1', 'nested/level1/level2/level3/index1', '', 1, 1, '', '2022-02-16 23:14:51', '2022-02-16 23:14:51', 1);
INSERT INTO `sys_menu` VALUES (24, 22, '菜单三级-2', 'nested_level1_level2_level3_index2', 'nested/level1/level2/level3/index2', '', 2, 1, '', '2022-02-16 23:15:08', '2022-02-16 23:15:08', 1);
INSERT INTO `sys_menu` VALUES (26, 0, '外部链接', '/external-link', 'Layout', 'link', 9, 1, 'noredirect', '2022-02-17 22:51:20', '2022-02-17 22:51:20', 1);
INSERT INTO `sys_menu` VALUES (30, 26, 'document', 'https://www.cnblogs.com/haoxianrui/', '', 'link', 1, 1, '', '2022-02-18 00:01:40', '2022-02-18 00:01:40', 3);
INSERT INTO `sys_menu` VALUES (32, 0, '实验室', '/lab', 'Layout', 'lab', 8, 1, 'noredirect', '2022-04-19 09:35:38', '2022-04-19 09:35:38', 2);
INSERT INTO `sys_menu` VALUES (33, 32, 'Seata', 'seata', 'lab/seata/index', 'security', 1, 1, '', '2022-04-19 09:35:38', '2022-04-19 09:35:38', 1);
INSERT INTO `sys_menu` VALUES (34, 32, 'RabbitMQ', 'rabbitmq', 'lab/rabbit/index', 'rabbitmq', 2, 1, '', '2022-04-19 09:38:25', '2022-04-19 09:38:25', 1);
INSERT INTO `sys_menu` VALUES (37, 9, '优惠券列表', 'coupon', 'sms/coupon/index', 'input', 2, 1, '', '2022-05-29 00:24:07', '2022-05-29 00:24:07', 1);
INSERT INTO `sys_menu` VALUES (39, 32, 'Sentinel', 'sentinel', 'lab/sentinel/index', 'security', 2, 1, '', '2022-07-23 09:52:41', '2022-07-23 09:52:41', 1);

-- ----------------------------
-- Table structure for sys_oauth_client
-- ----------------------------
DROP TABLE IF EXISTS `sys_oauth_client`;
CREATE TABLE `sys_oauth_client`  (
  `client_id` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `resource_ids` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `client_secret` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `scope` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `authorized_grant_types` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `web_server_redirect_uri` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `authorities` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `access_token_validity` int NULL DEFAULT NULL,
  `refresh_token_validity` int NULL DEFAULT NULL,
  `additional_information` varchar(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `autoapprove` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`client_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_oauth_client
-- ----------------------------
INSERT INTO `sys_oauth_client` VALUES ('client', '', '123456', 'all', 'password,refresh_token', NULL, NULL, 3600, 7200, NULL, 'true');
INSERT INTO `sys_oauth_client` VALUES ('mall-admin-web', '', '123456', 'all', 'password,refresh_token,captcha', NULL, '', 3600, 7200, NULL, 'true');
INSERT INTO `sys_oauth_client` VALUES ('mall-app', '', '123456', 'all', 'sms_code,refresh_token', NULL, NULL, 3600, 7200, NULL, 'true');
INSERT INTO `sys_oauth_client` VALUES ('mall-weapp', '', '123456', 'all', 'wechat,refresh_token', NULL, NULL, 3600, 7200, NULL, 'true');
INSERT INTO `sys_oauth_client` VALUES ('youlai-admin', '', '123456', 'all', 'password,client_credentials,refresh_token,authorization_code', NULL, '', 3600, 7200, NULL, 'true');
INSERT INTO `sys_oauth_client` VALUES ('youlai-mall-app', '', '123456', 'all', 'authorization_code,password,refresh_token,implicit,wechat,refresh_token', NULL, NULL, 3600, 7200, NULL, 'true');

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限名称',
  `menu_id` int NULL DEFAULT NULL COMMENT '菜单模块ID\r\n',
  `url_perm` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'URL权限标识',
  `btn_perm` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '按钮权限标识',
  `create_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `id`(`id`, `name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '权限表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES (1, '查看用户', 2, 'GET:/youlai-admin/api/v1/users/*', 'sys:user:view', '2021-02-02 14:16:07', '2021-06-16 22:25:24');
INSERT INTO `sys_permission` VALUES (2, '编辑用户', 2, 'PUT:/youlai-admin/users/*', 'sys:user:edit', '2021-06-16 16:19:44', '2021-06-16 23:36:53');
INSERT INTO `sys_permission` VALUES (3, '新增用户', 2, 'POST:/youlai-admin/api/v1/users', 'sys:user:add', '2021-06-16 23:36:37', '2021-06-16 23:37:03');
INSERT INTO `sys_permission` VALUES (4, '删除用户', 2, 'DELETE:/youlai-admin/api/v1/users/*', 'sys:user:delete', '2021-06-16 23:43:54', '2021-06-16 23:43:54');
INSERT INTO `sys_permission` VALUES (5, '路由列表', 4, 'GET:/youlai-admin/api/v1/menus/route', 'sys:route:query', NULL, NULL);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '角色名称',
  `code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色编码',
  `sort` int NULL DEFAULT NULL COMMENT '显示顺序',
  `status` tinyint(1) NULL DEFAULT 1 COMMENT '角色状态(1-正常；0-停用)',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除标识(0-未删除；1-已删除)',
  `create_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `data_scope` int NULL DEFAULT 1 COMMENT '数据范围（1：全部数据权限  2：本部门数据权限 3：本部门及以下数据权限 4:本人数据）',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 31 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '超级管理员', 'ROOT', 1, 1, 0, '2021-05-21 14:56:51', '2018-12-23 16:00:00', 1);
INSERT INTO `sys_role` VALUES (2, '系统管理员', 'ADMIN', 2, 1, 0, '2021-03-25 12:39:54', '2022-09-19 22:05:50', 2);
INSERT INTO `sys_role` VALUES (3, '访问游客', 'GUEST', 3, 1, 0, '2021-05-26 15:49:05', '2022-09-18 23:02:42', 2);
INSERT INTO `sys_role` VALUES (21, '研发部经理', 'YF', 0, 1, 0, '2022-09-19 22:10:42', '2022-09-21 00:01:37', 3);
INSERT INTO `sys_role` VALUES (22, '研发子部门1经理', 'YF1', 0, 1, 0, '2022-09-19 22:11:30', '2022-09-19 22:11:30', 2);
INSERT INTO `sys_role` VALUES (23, '研发子部门2经理', 'YF2', NULL, 1, 0, '2022-09-19 22:12:01', '2022-09-19 22:17:58', 3);
INSERT INTO `sys_role` VALUES (24, '测试部门经理', 'TEST', 1, 1, 0, '2022-09-19 22:12:36', '2022-09-19 22:12:42', 3);
INSERT INTO `sys_role` VALUES (25, '测试子部门1经理', 'TEST1', 0, 1, 0, '2022-09-19 22:13:13', '2022-09-19 22:17:50', 3);
INSERT INTO `sys_role` VALUES (26, '测试子部门2经理', 'TEST2', 1, 1, 0, '2022-09-19 22:13:39', '2022-09-19 22:18:08', 3);
INSERT INTO `sys_role` VALUES (27, '研发部1-1', 'YF1-1', 0, 1, 0, '2022-09-19 22:17:13', '2022-09-19 22:17:13', 2);
INSERT INTO `sys_role` VALUES (28, '研发部2-1', 'YF2-1', 0, 1, 0, '2022-09-19 22:17:42', '2022-09-19 22:19:54', 2);
INSERT INTO `sys_role` VALUES (29, '测试部1-1', 'TEST1-1', 1, 1, 0, '2022-09-19 22:18:54', '2022-09-19 22:18:54', 2);
INSERT INTO `sys_role` VALUES (30, '测试部2-1', 'TEST2-1', 1, 1, 0, '2022-09-19 22:19:33', '2022-09-19 22:19:33', 2);

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `menu_id` bigint NOT NULL COMMENT '菜单ID'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色和菜单关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (2, 1);
INSERT INTO `sys_role_menu` VALUES (2, 2);
INSERT INTO `sys_role_menu` VALUES (2, -1);
INSERT INTO `sys_role_menu` VALUES (2, 38);
INSERT INTO `sys_role_menu` VALUES (2, 3);
INSERT INTO `sys_role_menu` VALUES (2, 4);
INSERT INTO `sys_role_menu` VALUES (2, -1);
INSERT INTO `sys_role_menu` VALUES (2, 5);
INSERT INTO `sys_role_menu` VALUES (2, 6);
INSERT INTO `sys_role_menu` VALUES (2, 7);
INSERT INTO `sys_role_menu` VALUES (2, -1);
INSERT INTO `sys_role_menu` VALUES (2, 11);
INSERT INTO `sys_role_menu` VALUES (2, 12);
INSERT INTO `sys_role_menu` VALUES (2, 19);
INSERT INTO `sys_role_menu` VALUES (2, 18);
INSERT INTO `sys_role_menu` VALUES (2, 17);
INSERT INTO `sys_role_menu` VALUES (2, 13);
INSERT INTO `sys_role_menu` VALUES (2, 14);
INSERT INTO `sys_role_menu` VALUES (2, 15);
INSERT INTO `sys_role_menu` VALUES (2, 16);
INSERT INTO `sys_role_menu` VALUES (2, 9);
INSERT INTO `sys_role_menu` VALUES (2, 10);
INSERT INTO `sys_role_menu` VALUES (2, 37);
INSERT INTO `sys_role_menu` VALUES (2, 20);
INSERT INTO `sys_role_menu` VALUES (2, 21);
INSERT INTO `sys_role_menu` VALUES (2, 22);
INSERT INTO `sys_role_menu` VALUES (2, 23);
INSERT INTO `sys_role_menu` VALUES (2, 24);
INSERT INTO `sys_role_menu` VALUES (2, 32);
INSERT INTO `sys_role_menu` VALUES (2, 33);
INSERT INTO `sys_role_menu` VALUES (2, 34);
INSERT INTO `sys_role_menu` VALUES (2, 26);
INSERT INTO `sys_role_menu` VALUES (2, 30);
INSERT INTO `sys_role_menu` VALUES (2, 39);
INSERT INTO `sys_role_menu` VALUES (21, 1);
INSERT INTO `sys_role_menu` VALUES (21, 2);
INSERT INTO `sys_role_menu` VALUES (21, 3);
INSERT INTO `sys_role_menu` VALUES (21, 4);
INSERT INTO `sys_role_menu` VALUES (21, 5);
INSERT INTO `sys_role_menu` VALUES (21, 6);
INSERT INTO `sys_role_menu` VALUES (21, 7);
INSERT INTO `sys_role_menu` VALUES (22, 1);
INSERT INTO `sys_role_menu` VALUES (22, 2);
INSERT INTO `sys_role_menu` VALUES (22, 3);
INSERT INTO `sys_role_menu` VALUES (22, 4);
INSERT INTO `sys_role_menu` VALUES (22, 5);
INSERT INTO `sys_role_menu` VALUES (22, 6);
INSERT INTO `sys_role_menu` VALUES (22, 7);
INSERT INTO `sys_role_menu` VALUES (23, 1);
INSERT INTO `sys_role_menu` VALUES (23, 2);
INSERT INTO `sys_role_menu` VALUES (23, 3);
INSERT INTO `sys_role_menu` VALUES (23, 4);
INSERT INTO `sys_role_menu` VALUES (23, 5);
INSERT INTO `sys_role_menu` VALUES (23, 6);
INSERT INTO `sys_role_menu` VALUES (23, 7);
INSERT INTO `sys_role_menu` VALUES (24, 1);
INSERT INTO `sys_role_menu` VALUES (24, 2);
INSERT INTO `sys_role_menu` VALUES (24, 3);
INSERT INTO `sys_role_menu` VALUES (24, 4);
INSERT INTO `sys_role_menu` VALUES (24, 5);
INSERT INTO `sys_role_menu` VALUES (24, 6);
INSERT INTO `sys_role_menu` VALUES (24, 7);
INSERT INTO `sys_role_menu` VALUES (25, 1);
INSERT INTO `sys_role_menu` VALUES (25, 2);
INSERT INTO `sys_role_menu` VALUES (25, 3);
INSERT INTO `sys_role_menu` VALUES (25, 4);
INSERT INTO `sys_role_menu` VALUES (25, 5);
INSERT INTO `sys_role_menu` VALUES (25, 6);
INSERT INTO `sys_role_menu` VALUES (25, 7);
INSERT INTO `sys_role_menu` VALUES (26, 1);
INSERT INTO `sys_role_menu` VALUES (26, 2);
INSERT INTO `sys_role_menu` VALUES (26, 3);
INSERT INTO `sys_role_menu` VALUES (26, 4);
INSERT INTO `sys_role_menu` VALUES (26, 5);
INSERT INTO `sys_role_menu` VALUES (26, 6);
INSERT INTO `sys_role_menu` VALUES (26, 7);
INSERT INTO `sys_role_menu` VALUES (27, 1);
INSERT INTO `sys_role_menu` VALUES (27, 2);
INSERT INTO `sys_role_menu` VALUES (27, 3);
INSERT INTO `sys_role_menu` VALUES (27, 4);
INSERT INTO `sys_role_menu` VALUES (27, 5);
INSERT INTO `sys_role_menu` VALUES (27, 6);
INSERT INTO `sys_role_menu` VALUES (27, 7);
INSERT INTO `sys_role_menu` VALUES (28, 1);
INSERT INTO `sys_role_menu` VALUES (28, 2);
INSERT INTO `sys_role_menu` VALUES (28, 3);
INSERT INTO `sys_role_menu` VALUES (28, 4);
INSERT INTO `sys_role_menu` VALUES (28, 5);
INSERT INTO `sys_role_menu` VALUES (28, 6);
INSERT INTO `sys_role_menu` VALUES (28, 7);
INSERT INTO `sys_role_menu` VALUES (29, 1);
INSERT INTO `sys_role_menu` VALUES (29, 2);
INSERT INTO `sys_role_menu` VALUES (29, 3);
INSERT INTO `sys_role_menu` VALUES (29, 4);
INSERT INTO `sys_role_menu` VALUES (29, 5);
INSERT INTO `sys_role_menu` VALUES (29, 6);
INSERT INTO `sys_role_menu` VALUES (29, 7);
INSERT INTO `sys_role_menu` VALUES (30, 1);
INSERT INTO `sys_role_menu` VALUES (30, 2);
INSERT INTO `sys_role_menu` VALUES (30, 3);
INSERT INTO `sys_role_menu` VALUES (30, 4);
INSERT INTO `sys_role_menu` VALUES (30, 5);
INSERT INTO `sys_role_menu` VALUES (30, 6);
INSERT INTO `sys_role_menu` VALUES (30, 7);

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission`  (
  `role_id` int NULL DEFAULT NULL COMMENT '角色id',
  `permission_id` int NULL DEFAULT NULL COMMENT '资源id',
  INDEX `role_id`(`role_id`) USING BTREE,
  INDEX `permission_id`(`permission_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色权限表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
INSERT INTO `sys_role_permission` VALUES (2, 1);
INSERT INTO `sys_role_permission` VALUES (2, 2);
INSERT INTO `sys_role_permission` VALUES (2, 3);
INSERT INTO `sys_role_permission` VALUES (2, 4);
INSERT INTO `sys_role_permission` VALUES (21, 1);
INSERT INTO `sys_role_permission` VALUES (21, 2);
INSERT INTO `sys_role_permission` VALUES (21, 3);
INSERT INTO `sys_role_permission` VALUES (21, 4);
INSERT INTO `sys_role_permission` VALUES (21, 5);
INSERT INTO `sys_role_permission` VALUES (22, 1);
INSERT INTO `sys_role_permission` VALUES (22, 2);
INSERT INTO `sys_role_permission` VALUES (22, 3);
INSERT INTO `sys_role_permission` VALUES (22, 4);
INSERT INTO `sys_role_permission` VALUES (22, 5);
INSERT INTO `sys_role_permission` VALUES (23, 1);
INSERT INTO `sys_role_permission` VALUES (23, 2);
INSERT INTO `sys_role_permission` VALUES (23, 3);
INSERT INTO `sys_role_permission` VALUES (23, 4);
INSERT INTO `sys_role_permission` VALUES (23, 5);
INSERT INTO `sys_role_permission` VALUES (24, 1);
INSERT INTO `sys_role_permission` VALUES (24, 2);
INSERT INTO `sys_role_permission` VALUES (24, 3);
INSERT INTO `sys_role_permission` VALUES (24, 4);
INSERT INTO `sys_role_permission` VALUES (24, 5);
INSERT INTO `sys_role_permission` VALUES (25, 1);
INSERT INTO `sys_role_permission` VALUES (25, 2);
INSERT INTO `sys_role_permission` VALUES (25, 3);
INSERT INTO `sys_role_permission` VALUES (25, 4);
INSERT INTO `sys_role_permission` VALUES (25, 5);
INSERT INTO `sys_role_permission` VALUES (26, 1);
INSERT INTO `sys_role_permission` VALUES (26, 2);
INSERT INTO `sys_role_permission` VALUES (26, 3);
INSERT INTO `sys_role_permission` VALUES (26, 4);
INSERT INTO `sys_role_permission` VALUES (26, 5);
INSERT INTO `sys_role_permission` VALUES (27, 1);
INSERT INTO `sys_role_permission` VALUES (27, 2);
INSERT INTO `sys_role_permission` VALUES (27, 3);
INSERT INTO `sys_role_permission` VALUES (27, 4);
INSERT INTO `sys_role_permission` VALUES (27, 5);
INSERT INTO `sys_role_permission` VALUES (28, 1);
INSERT INTO `sys_role_permission` VALUES (28, 2);
INSERT INTO `sys_role_permission` VALUES (28, 3);
INSERT INTO `sys_role_permission` VALUES (28, 4);
INSERT INTO `sys_role_permission` VALUES (28, 5);
INSERT INTO `sys_role_permission` VALUES (29, 1);
INSERT INTO `sys_role_permission` VALUES (29, 2);
INSERT INTO `sys_role_permission` VALUES (29, 3);
INSERT INTO `sys_role_permission` VALUES (29, 4);
INSERT INTO `sys_role_permission` VALUES (29, 5);
INSERT INTO `sys_role_permission` VALUES (30, 1);
INSERT INTO `sys_role_permission` VALUES (30, 2);
INSERT INTO `sys_role_permission` VALUES (30, 3);
INSERT INTO `sys_role_permission` VALUES (30, 4);
INSERT INTO `sys_role_permission` VALUES (30, 5);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `nickname` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `gender` tinyint(1) NULL DEFAULT 1 COMMENT '性别((1:男;2:女))',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `dept_id` int NULL DEFAULT NULL COMMENT '部门ID',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '用户头像',
  `mobile` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系方式',
  `status` tinyint(1) NULL DEFAULT 1 COMMENT '用户状态((1:正常;0:禁用))',
  `email` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户邮箱',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除标识(0:未删除;1:已删除)',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `login_name`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 108 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'root', '有来技术', 0, '$2a$10$xVWsNOhHrCxh5UbpCE7/HuJ.PAOKcYAqRxD2CO2nVnJS.IAXkr5aq', NULL, 'https://s2.loli.net/2022/04/07/gw1L2Z5sPtS8GIl.gif', '17621590365', 1, 'youlaitech@163.com', 0, NULL, NULL);
INSERT INTO `sys_user` VALUES (2, 'admin', '系统管理员', 1, '$2a$10$8/8PxGHA.30EeWg8x4/4BuWuCUJubFbGJXyUYRs7RaJEdVvEMRbWe', 2, 'https://s2.loli.net/2022/04/07/gw1L2Z5sPtS8GIl.gif', '17621210366', 1, '', 0, '2019-10-10 13:41:22', '2022-09-20 23:16:09');
INSERT INTO `sys_user` VALUES (3, 'test', '测试小用户', 1, '$2a$10$MPJkNw.hKT/fZOgwYP8q9eu/rFJJDsNov697AmdkHNJkpjIpVSw2q', 3, 'https://s2.loli.net/2022/04/07/gw1L2Z5sPtS8GIl.gif', '17621210366', 1, 'youlaitech@163.com', 0, '2021-06-05 01:31:29', '2021-06-05 01:31:29');
INSERT INTO `sys_user` VALUES (99, '研发经理', '研发经理', 1, '$2a$10$aphBXZpRqU.uo3dqfJuIWuzVpL628iok6wufUj2z0yFgGw1nusQP2', 2, 'https://s2.loli.net/2022/04/07/gw1L2Z5sPtS8GIl.gif', NULL, 1, NULL, 0, '2022-09-19 22:15:03', '2022-09-19 22:15:03');
INSERT INTO `sys_user` VALUES (100, '研发1-1', '研发1-1', 1, '$2a$10$YscLYog22KpHrSjEgpsXOeYSpxyW8R3TEPxadw5W1QY3KLb6mf9Pi', 47, 'https://s2.loli.net/2022/04/07/gw1L2Z5sPtS8GIl.gif', NULL, 1, NULL, 0, '2022-09-19 22:16:01', '2022-09-19 22:21:54');
INSERT INTO `sys_user` VALUES (101, '研发2-1', '研发2-1', 1, '$2a$10$cw.JNXZ..ynlVsvRzpyOYu0w0JjQ/VHV9UkxERs.LuEW5R8cHIwFm', 48, 'https://s2.loli.net/2022/04/07/gw1L2Z5sPtS8GIl.gif', NULL, 1, NULL, 0, '2022-09-19 22:22:35', '2022-09-19 22:22:35');
INSERT INTO `sys_user` VALUES (102, '研发1-1-1', '研发1-1-1', 1, '$2a$10$9zfzDEGMkwdSuwLmro82LOST7cGu5j.Aln1UGnpdFVqpjZs7Ub2e.', 47, 'https://s2.loli.net/2022/04/07/gw1L2Z5sPtS8GIl.gif', NULL, 1, NULL, 0, '2022-09-19 22:23:11', '2022-09-19 22:23:11');
INSERT INTO `sys_user` VALUES (103, '研发2-1-1', '研发2-1-1', 1, '$2a$10$PwhfVrPeI12Ia.n6H6jjw.LT2H51YJPZPmi4tVCNLI4tO7OFzFa.y', 48, 'https://s2.loli.net/2022/04/07/gw1L2Z5sPtS8GIl.gif', NULL, 1, NULL, 0, '2022-09-19 22:23:45', '2022-09-19 22:23:45');
INSERT INTO `sys_user` VALUES (104, '测试经理', '测试经理', 1, '$2a$10$6v4RAwspGShJbkwF4tPuue5jDmOYZBc0czZg0ly8JyP4KkxN7GWWG', 3, 'https://s2.loli.net/2022/04/07/gw1L2Z5sPtS8GIl.gif', NULL, 1, NULL, 0, '2022-09-19 22:24:16', '2022-09-19 22:24:16');
INSERT INTO `sys_user` VALUES (105, '测试1-1', '测试1-1', 1, '$2a$10$CRJdFobDJk8nxRNPDHNqsuDQXTSpkD1rSZjX4PA8wToLCIqaK.04W', 49, 'https://s2.loli.net/2022/04/07/gw1L2Z5sPtS8GIl.gif', NULL, 1, NULL, 0, '2022-09-19 22:26:05', '2022-09-19 22:26:05');
INSERT INTO `sys_user` VALUES (106, '测试1-1-1', '测试1-1-1', 1, '$2a$10$2iMk0z5urtG4EnzzWC4/Xu7pl.CSS4/q5Yvl21LamyY2doMSL8NEe', 49, 'https://s2.loli.net/2022/04/07/gw1L2Z5sPtS8GIl.gif', NULL, 1, NULL, 0, '2022-09-19 22:26:48', '2022-09-19 22:26:48');
INSERT INTO `sys_user` VALUES (107, '测试2-1-1', '测试2-1-1', 1, '$2a$10$Raf3uyTP/OEmf0laUkGxSur1zgq/cQ.fScAYOSKxFa0rSzbYkQ4tW', 50, 'https://s2.loli.net/2022/04/07/gw1L2Z5sPtS8GIl.gif', NULL, 1, NULL, 0, '2022-09-19 22:27:12', '2022-09-19 22:27:12');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `user_id` int NOT NULL COMMENT '用户ID',
  `role_id` int NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户和角色关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1);
INSERT INTO `sys_user_role` VALUES (2, 2);
INSERT INTO `sys_user_role` VALUES (2, 21);
INSERT INTO `sys_user_role` VALUES (3, 3);
INSERT INTO `sys_user_role` VALUES (99, 21);
INSERT INTO `sys_user_role` VALUES (100, 22);
INSERT INTO `sys_user_role` VALUES (101, 23);
INSERT INTO `sys_user_role` VALUES (102, 27);
INSERT INTO `sys_user_role` VALUES (103, 28);
INSERT INTO `sys_user_role` VALUES (104, 24);
INSERT INTO `sys_user_role` VALUES (105, 25);
INSERT INTO `sys_user_role` VALUES (106, 29);
INSERT INTO `sys_user_role` VALUES (107, 30);

SET FOREIGN_KEY_CHECKS = 1;

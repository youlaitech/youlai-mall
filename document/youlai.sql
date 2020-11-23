/*
 Navicat Premium Data Transfer

 Source Server         : 101.37.69.49
 Source Server Type    : MySQL
 Source Server Version : 80019
 Source Host           : 101.37.69.49:3306
 Source Schema         : youlai

 Target Server Type    : MySQL
 Target Server Version : 80019
 File Encoding         : 65001

 Date: 23/11/2020 09:18:36
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
INSERT INTO `oauth_client_details` VALUES ('mall-weapp', NULL, '123456', 'all', 'password,refresh_token', NULL, NULL, 600, 7200, NULL, NULL);
INSERT INTO `oauth_client_details` VALUES ('youlai-admin', NULL, '123456', 'all', 'password,refresh_token,authorization_code', NULL, NULL, 1, 120, NULL, NULL);

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '部门名称',
  `parent_id` int(0) NULL DEFAULT 0 COMMENT '父节点id',
  `tree_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '父节点id路径',
  `sort` int(0) NULL DEFAULT 0 COMMENT '显示顺序',
  `leader` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '负责人',
  `mobile` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱地址',
  `status` tinyint(1) NULL DEFAULT 0 COMMENT '部门状态（0正常 1停用）',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '删除标志（0存在 1删除）',
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '部门表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES (8, '研发部', 0, '0', 1, '郝先瑞', '17621590365', '1490493387@qq.com', 1, 0, '2020-09-23 11:32:05', '2020-09-23 11:32:05');
INSERT INTO `sys_dept` VALUES (9, '测试部', 0, '0', 1, '张三', '15123569856', 'youlai@163.com', 1, 0, '2020-09-23 11:33:06', '2020-09-23 11:33:06');

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '字典名称',
  `value` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '字典值',
  `type_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '类型编码',
  `sort` int(0) NULL DEFAULT 0 COMMENT '排序',
  `status` tinyint(1) NULL DEFAULT 0 COMMENT '状态（0 停用 1正常）',
  `defaulted` tinyint(1) NULL DEFAULT 0 COMMENT '是否默认（0否 1是）',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '备注',
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '字典数据表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES (1, '男', '1', 'gender', 1, 1, 0, '性别男', '2019-05-05 13:07:52', '2019-07-02 14:23:05');
INSERT INTO `sys_dict` VALUES (2, '女', '2', 'gender', 2, 1, 0, '性别女', '2019-04-19 11:33:00', '2019-07-02 14:23:05');
INSERT INTO `sys_dict` VALUES (5, '未知', '0', 'gender', 1, 1, 0, '', '2020-10-17 08:09:31', '2020-10-17 08:09:31');
INSERT INTO `sys_dict` VALUES (6, '密码模式', 'password', 'grant_type', 1, 1, 0, '', '2020-10-17 09:11:52', '2020-11-22 12:02:10');
INSERT INTO `sys_dict` VALUES (7, '授权码模式', 'authorization_code', 'grant_type', 1, 1, 0, '', '2020-10-17 09:12:15', '2020-11-22 12:02:10');
INSERT INTO `sys_dict` VALUES (8, '客户端模式', 'client_credentials', 'grant_type', 1, 1, 0, '', '2020-10-17 09:12:36', '2020-11-22 12:02:10');
INSERT INTO `sys_dict` VALUES (9, '刷新模式', 'refresh_token', 'grant_type', 1, 1, 0, '', '2020-10-17 09:12:57', '2020-11-22 12:02:10');
INSERT INTO `sys_dict` VALUES (10, '简化模式', 'implicit', 'grant_type', 1, 1, 0, '', '2020-10-17 09:13:23', '2020-11-22 12:02:10');
INSERT INTO `sys_dict` VALUES (11, '后端开发', 'Back-end development', 'project', 1, 1, 0, '', NULL, NULL);
INSERT INTO `sys_dict` VALUES (12, '前端开发人员', 'Front-end development', 'project', 1, 1, 0, '', NULL, NULL);
INSERT INTO `sys_dict` VALUES (13, '测试人员', 'Test development', 'project', 1, 1, 0, '', NULL, NULL);

-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键 ',
  `code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '类型编码',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '类型名称',
  `status` tinyint(1) NULL DEFAULT 0 COMMENT '状态（0-正常 ,1-停用）',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `type_code`(`code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '字典类型表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict_type
-- ----------------------------
INSERT INTO `sys_dict_type` VALUES (1, 'gender', '性别', 1, '性别', '2019-12-06 19:03:32', '2019-12-12 19:03:15');
INSERT INTO `sys_dict_type` VALUES (11, 'grant_type', '授权方式', 1, NULL, '2020-10-17 08:09:50', '2020-10-17 08:09:50');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `title` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '菜单名称',
  `parent_id` int(0) NULL DEFAULT NULL COMMENT '父菜单ID',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '路由名称',
  `path` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '路由路径',
  `component` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '组件路径',
  `redirect` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '跳转路径',
  `icon` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '菜单图标',
  `sort` int(0) NULL DEFAULT 0 COMMENT '排序',
  `visible` tinyint(1) NULL DEFAULT 1 COMMENT '是否可见：0-隐藏 1-显示',
  `status` tinyint(1) NULL DEFAULT 1 COMMENT '状态：0-禁用 1-开启',
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '菜单管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, '系统管理', 0, 'Admin', 'admin', '', '', 'component', 6, 1, 1, '2020-09-23 09:12:21', '2020-09-15 13:10:45');
INSERT INTO `sys_menu` VALUES (2, '用户管理', 1, 'User', 'user', 'admin/user', NULL, 'user', 0, 1, 1, '2020-09-23 09:12:26', '2020-09-15 13:12:05');
INSERT INTO `sys_menu` VALUES (3, '角色管理', 1, 'Role', 'role', 'admin/role', NULL, 'peoples', 0, 1, 1, '2020-09-23 09:12:21', '2020-09-23 09:12:21');
INSERT INTO `sys_menu` VALUES (4, '菜单管理', 1, 'Menu', 'menu', 'admin/menu', NULL, 'tree-table', 8, 1, 1, '2020-09-23 09:12:21', '2020-09-15 13:12:20');
INSERT INTO `sys_menu` VALUES (5, '字典管理', 1, 'Dict', 'dict', 'admin/dict', NULL, 'education', 10, 1, 1, '2020-09-23 09:12:21', '2020-09-15 13:11:37');
INSERT INTO `sys_menu` VALUES (6, '部门管理', 1, 'Dept', 'dept', 'admin/dept', NULL, 'tree', 0, 1, 1, '2020-09-23 09:12:21', '2020-09-23 09:12:21');
INSERT INTO `sys_menu` VALUES (7, '资源管理', 1, 'Resource', 'resource', 'admin/resource', NULL, 'list', 9, 1, 1, '2020-09-22 17:00:01', '2020-09-22 17:00:01');
INSERT INTO `sys_menu` VALUES (8, '客户端管理', 1, 'Client', 'client', 'admin/client', NULL, 'tab', 11, 1, 1, '2020-10-17 08:04:08', '2020-10-17 08:04:08');
INSERT INTO `sys_menu` VALUES (9, '营销管理', 0, 'Sms', 'sms', NULL, '', 'list', 1, 1, 1, '2020-10-24 15:24:04', '2020-10-31 10:51:53');
INSERT INTO `sys_menu` VALUES (10, '广告管理', 9, 'Advert', 'advert', 'sms/advert', NULL, 'documentation', 1, 1, 1, '2020-10-24 15:25:15', '2020-10-24 15:25:15');
INSERT INTO `sys_menu` VALUES (11, '商品管理', 0, 'Pms', 'pms', '', NULL, 'list', 2, 1, 1, '2020-10-31 10:44:58', '2020-10-31 10:51:57');
INSERT INTO `sys_menu` VALUES (12, '商品列表', 11, 'Goods', 'goods', 'pms/goods', NULL, 'component', 1, 1, 1, '2020-11-06 11:54:37', '2020-10-31 10:48:19');
INSERT INTO `sys_menu` VALUES (13, '订单管理', 0, 'Oms', 'oms', NULL, NULL, 'list', 3, 1, 1, '2020-10-31 10:49:46', '2020-10-31 10:52:01');
INSERT INTO `sys_menu` VALUES (14, '订单列表', 13, 'Order', 'order', 'oms/order', NULL, 'component', 3, 1, 1, '2020-10-31 10:50:23', '2020-10-31 10:50:38');
INSERT INTO `sys_menu` VALUES (15, '会员管理', 0, 'Ums', 'ums', NULL, NULL, 'list', 4, 1, 1, '2020-10-31 10:51:07', '2020-10-31 10:51:07');
INSERT INTO `sys_menu` VALUES (16, '会员列表', 15, 'Member', 'member', 'ums/member', NULL, 'component', 1, 1, 1, '2020-10-31 10:51:43', '2020-10-31 10:51:43');
INSERT INTO `sys_menu` VALUES (17, '品牌管理', 11, 'Brand', 'brand', 'pms/brand', NULL, 'component', 2, 1, 1, '2020-09-23 09:12:21', '2020-09-23 09:12:21');
INSERT INTO `sys_menu` VALUES (18, '类目管理', 11, 'Category', 'category', 'pms/category', NULL, 'component', 3, 1, 1, '2020-09-23 09:12:21', '2020-09-23 09:12:21');
INSERT INTO `sys_menu` VALUES (19, '商品详情', 11, 'GoodsDetail', 'goodsDetail', 'pms/goods/detail', NULL, 'component', 2, 0, 1, '2020-11-06 13:16:26', '2020-11-06 11:57:22');

-- ----------------------------
-- Table structure for sys_resource
-- ----------------------------
DROP TABLE IF EXISTS `sys_resource`;
CREATE TABLE `sys_resource`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限名称',
  `url` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '资源路径',
  `gmt_create` datetime(0) NULL DEFAULT NULL,
  `gmt_modified` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `id`(`id`, `name`) USING BTREE,
  INDEX `id_2`(`id`, `name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_resource
-- ----------------------------
INSERT INTO `sys_resource` VALUES (1, '系统管理', '/youlai-admin/**', NULL, NULL);
INSERT INTO `sys_resource` VALUES (9, '营销管理', '/mall-sms/**', '2020-10-24 15:29:01', '2020-10-24 15:29:09');
INSERT INTO `sys_resource` VALUES (12, '订单管理', '/mall-oms/**', '2020-10-31 10:40:35', '2020-10-31 10:40:35');
INSERT INTO `sys_resource` VALUES (13, '会员管理', '/mall-ums/**', '2020-10-31 10:41:08', '2020-10-31 10:41:08');
INSERT INTO `sys_resource` VALUES (14, '商品管理', '/mall-pms/**', '2020-10-31 10:41:37', '2020-10-31 10:41:37');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '角色名称',
  `perms` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '角色权限字符串',
  `sort` int(0) NULL DEFAULT NULL COMMENT '显示顺序',
  `status` tinyint(1) NULL DEFAULT 0 COMMENT '角色状态（0正常 1停用）',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '删除标识  (0未删除 1已删除)',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '描述',
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '超级管理员', 'root', 1, 1, 0, '超级管理员', '2018-12-23 16:00:00', '2020-09-11 17:04:23');
INSERT INTO `sys_role` VALUES (2, '系统管理员', 'admin', 2, 1, 0, '系统管理员', '2018-12-23 16:00:00', '2020-09-22 17:01:44');
INSERT INTO `sys_role` VALUES (3, '普通用户', 'common', 3, 1, 0, '普通用户', '2019-05-05 16:00:00', '2020-09-22 17:09:54');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `role_id` int(0) NOT NULL COMMENT '角色ID',
  `menu_id` int(0) NOT NULL COMMENT '菜单ID'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色和菜单关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (1, 9);
INSERT INTO `sys_role_menu` VALUES (1, 10);
INSERT INTO `sys_role_menu` VALUES (1, 11);
INSERT INTO `sys_role_menu` VALUES (1, 12);
INSERT INTO `sys_role_menu` VALUES (1, 19);
INSERT INTO `sys_role_menu` VALUES (1, 17);
INSERT INTO `sys_role_menu` VALUES (1, 18);
INSERT INTO `sys_role_menu` VALUES (1, 13);
INSERT INTO `sys_role_menu` VALUES (1, 14);
INSERT INTO `sys_role_menu` VALUES (1, 15);
INSERT INTO `sys_role_menu` VALUES (1, 16);
INSERT INTO `sys_role_menu` VALUES (1, 1);
INSERT INTO `sys_role_menu` VALUES (1, 2);
INSERT INTO `sys_role_menu` VALUES (1, 3);
INSERT INTO `sys_role_menu` VALUES (1, 6);
INSERT INTO `sys_role_menu` VALUES (1, 4);
INSERT INTO `sys_role_menu` VALUES (1, 7);
INSERT INTO `sys_role_menu` VALUES (1, 5);
INSERT INTO `sys_role_menu` VALUES (1, 8);
INSERT INTO `sys_role_menu` VALUES (2, 10);
INSERT INTO `sys_role_menu` VALUES (2, 12);
INSERT INTO `sys_role_menu` VALUES (2, 14);
INSERT INTO `sys_role_menu` VALUES (2, 16);
INSERT INTO `sys_role_menu` VALUES (2, 1);
INSERT INTO `sys_role_menu` VALUES (2, 3);
INSERT INTO `sys_role_menu` VALUES (2, 6);
INSERT INTO `sys_role_menu` VALUES (2, 2);

-- ----------------------------
-- Table structure for sys_role_resource
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_resource`;
CREATE TABLE `sys_role_resource`  (
  `role_id` int(0) NULL DEFAULT NULL COMMENT '角色id',
  `resource_id` int(0) NULL DEFAULT NULL COMMENT '资源id',
  INDEX `role_id`(`role_id`) USING BTREE,
  INDEX `permission_id`(`resource_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_resource
-- ----------------------------
INSERT INTO `sys_role_resource` VALUES (1, 12);
INSERT INTO `sys_role_resource` VALUES (1, 9);
INSERT INTO `sys_role_resource` VALUES (1, 1);
INSERT INTO `sys_role_resource` VALUES (2, 1);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `nickname` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `gender` tinyint(1) NULL DEFAULT 0 COMMENT '性别',
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `dept_id` int(0) NULL DEFAULT NULL COMMENT '部门ID',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '删除标识（0未删除 1已删除）',
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '用户头像',
  `mobile` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系方式',
  `status` tinyint(1) NULL DEFAULT 0 COMMENT '用户状态（0正常 1禁用）',
  `email` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户邮箱',
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `login_name`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', '系统管理员', 0, '$2a$10$dLq3.pXNwTNqWabsRfJX4ej8Htk/vUWuHh.LvITq5BrU8u.dYvZpC', 0, 0, 'https://i.loli.net/2020/05/08/dVvpaQ8NHkWAC2c.jpg', '17621210366', 1, '1490493387@qq.com', '2019-10-10 13:41:22', '2020-03-09 10:12:54');
INSERT INTO `sys_user` VALUES (2, 'wangliang', NULL, 0, '$2a$10$IF9q59tYvorJg3VrLhn72uGZyzbfElWLEjri230EkmWXcfZwH5D2C', 9, 0, '', NULL, 1, NULL, '2020-11-12 18:24:17', '2020-11-12 18:24:17');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `user_id` int(0) NOT NULL COMMENT '用户ID',
  `role_id` int(0) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户和角色关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1);

SET FOREIGN_KEY_CHECKS = 1;

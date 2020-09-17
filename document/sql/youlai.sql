/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50720
 Source Host           : localhost:3306
 Source Schema         : youlai

 Target Server Type    : MySQL
 Target Server Version : 50720
 File Encoding         : 65001

 Date: 17/09/2020 19:52:54
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details`  (
  `client_id` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `resource_ids` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `client_secret` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `scope` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `authorized_grant_types` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `web_server_redirect_uri` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `authorities` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `access_token_validity` int(11) DEFAULT NULL,
  `refresh_token_validity` int(11) DEFAULT NULL,
  `additional_information` varchar(4096) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `autoapprove` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`client_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oauth_client_details
-- ----------------------------
INSERT INTO `oauth_client_details` VALUES ('client', NULL, '1234562', 'all', 'password,refresh_token', 'http://tycoding.cn', NULL, NULL, NULL, NULL, 'true');

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '部门名称',
  `parent_id` int(11) DEFAULT 0 COMMENT '父节点id',
  `tree_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '父节点id路径',
  `sort` int(11) DEFAULT 0 COMMENT '显示顺序',
  `leader` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '负责人',
  `mobile` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '联系电话',
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '邮箱地址',
  `status` tinyint(1) DEFAULT 0 COMMENT '部门状态（0正常 1停用）',
  `deleted` tinyint(1) DEFAULT 0 COMMENT '删除标志（0存在 1删除）',
  `create_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '部门表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES (3, '测试', 0, '0', 1, '123', '', NULL, 1, 0, 'admin', '2020-09-10 11:52:52', 'admin', '2020-09-10 11:52:52');
INSERT INTO `sys_dept` VALUES (5, '123', 3, '0,3', 1, '23', NULL, '', 1, 0, 'admin', '2020-09-10 11:54:46', 'admin', '2020-09-10 11:54:46');
INSERT INTO `sys_dept` VALUES (6, '4567', 5, '0,3,5', 1, NULL, NULL, NULL, 1, 0, 'admin', '2020-09-10 17:43:07', 'admin', '2020-09-10 17:43:07');
INSERT INTO `sys_dept` VALUES (7, '2222', 3, '0,3', 1, NULL, NULL, NULL, 1, 0, 'admin', '2020-09-10 17:43:20', 'admin', '2020-09-10 17:43:20');

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '字典名称',
  `value` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '字典值',
  `type_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '类型编码',
  `sort` int(11) DEFAULT 0 COMMENT '排序',
  `status` tinyint(1) DEFAULT 0 COMMENT '状态（0 停用 1正常）',
  `defaulted` tinyint(1) DEFAULT 0 COMMENT '是否默认（0否 1是）',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '备注',
  `create_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 30 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '字典数据表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES (1, '头盔', '01', 'material_type', 1, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (2, '电池', '02', 'material_type', 2, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (3, '个', '01', 'material_unit', 1, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (4, '台', '02', 'material_unit', 2, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (5, '已提交待审核', '0', 'process_status', 0, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict` VALUES (6, '审核通过', '1', 'process_status', 0, 1, NULL, '', '', NULL, '', NULL);
INSERT INTO `sys_dict` VALUES (7, '审核不通过', '2', 'process_status', 0, 1, NULL, '', '', NULL, '', NULL);
INSERT INTO `sys_dict` VALUES (8, '生产入库', '01', 'stock_in_type', 0, 1, NULL, '', '', NULL, '', NULL);
INSERT INTO `sys_dict` VALUES (9, '采购进货', '02', 'stock_in_type', 0, 1, NULL, '', '', NULL, '', NULL);
INSERT INTO `sys_dict` VALUES (10, '采购退货', '03', 'stock_in_type', 0, 1, NULL, '', '', NULL, '', NULL);
INSERT INTO `sys_dict` VALUES (11, '生产退料', '04', 'stock_in_type', 0, 1, NULL, '', '', NULL, '', NULL);
INSERT INTO `sys_dict` VALUES (12, '其他入库', '05', 'stock_in_type', 0, 1, NULL, '', '', NULL, '', NULL);
INSERT INTO `sys_dict` VALUES (13, '销售出库', '03', 'stock_out_type', 3, 1, NULL, '', '', NULL, '', NULL);
INSERT INTO `sys_dict` VALUES (14, '销售退货', '02', 'stock_out_type', 0, 1, NULL, '', '', NULL, '', NULL);
INSERT INTO `sys_dict` VALUES (15, '男', '0', 'sys_user_sex', 1, 1, NULL, '性别男', 'admin', '2019-05-05 13:07:52', 'admin', '2019-07-02 14:23:05');
INSERT INTO `sys_dict` VALUES (16, '女', '1', 'sys_user_sex', 2, 1, NULL, '性别女', 'admin', '2019-04-19 11:33:00', 'admin', '2019-07-02 14:23:05');
INSERT INTO `sys_dict` VALUES (17, '完好', '0', 'material_status', 0, 1, NULL, '', '', NULL, '', NULL);
INSERT INTO `sys_dict` VALUES (18, '损坏', '1', 'material_status', 0, 1, NULL, '', '', NULL, '', NULL);
INSERT INTO `sys_dict` VALUES (19, '丢失', '2', 'material_status', 0, 1, NULL, '', '', NULL, '', NULL);
INSERT INTO `sys_dict` VALUES (20, '拆分', '3', 'material_status', 0, 1, NULL, '', '', NULL, '', NULL);
INSERT INTO `sys_dict` VALUES (21, '组装', '4', 'material_status', 0, 1, NULL, '', '', NULL, '', NULL);
INSERT INTO `sys_dict` VALUES (22, '待入库', '0', 'material_store_status', 0, 1, NULL, '', '', NULL, '', NULL);
INSERT INTO `sys_dict` VALUES (23, '已入库', '1', 'material_store_status', 0, 1, NULL, '', '', NULL, '', NULL);
INSERT INTO `sys_dict` VALUES (24, '待出库', '2', 'material_store_status', 0, 1, NULL, '', '', NULL, '', NULL);
INSERT INTO `sys_dict` VALUES (25, '已出库', '3', 'material_store_status', 0, 1, NULL, '', '', NULL, '', NULL);
INSERT INTO `sys_dict` VALUES (26, '生产领料', '01', 'stock_out_type', 0, 1, NULL, '', '', NULL, '', NULL);
INSERT INTO `sys_dict` VALUES (27, '其他出库', '04', 'stock_out_type', 0, 1, NULL, '', '', NULL, '', NULL);
INSERT INTO `sys_dict` VALUES (28, '正常', '1', 'sys_normal_disable', 1, 1, NULL, '', '', NULL, 'admin', '2020-09-14 14:35:25');
INSERT INTO `sys_dict` VALUES (29, '停用', '0', 'sys_normal_disable', 2, 1, NULL, '', '', NULL, 'admin', '2020-09-14 14:35:31');

-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键 ',
  `code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '类型编码',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '类型名称',
  `status` tinyint(1) DEFAULT 0 COMMENT '状态（0-正常 ,1-停用）',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  `create_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `type_code`(`code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '字典类型表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict_type
-- ----------------------------
INSERT INTO `sys_dict_type` VALUES (1, 'material_type', '物料类别', 1, '物料类别列表', '', '2019-04-19 11:33:00', 'admin', '2019-07-02 14:23:05');
INSERT INTO `sys_dict_type` VALUES (2, 'material_unit', '物料单位', 1, '物料单位列表', '', NULL, '', NULL);
INSERT INTO `sys_dict_type` VALUES (3, 'process_status', '流程状态', 1, '流程状态列表', '', NULL, '', NULL);
INSERT INTO `sys_dict_type` VALUES (4, 'stock_in_type', '入库类型', 1, '入库类型列表', '', NULL, '', NULL);
INSERT INTO `sys_dict_type` VALUES (5, 'stock_out_type', '出库类型', 1, '出库类型列表', '', NULL, '', NULL);
INSERT INTO `sys_dict_type` VALUES (6, 'sys_user_sex', '用戶性别', 1, '性别列表', '', NULL, '', NULL);
INSERT INTO `sys_dict_type` VALUES (7, 'material_status', '物料状态', 1, '物料状态列表', '', NULL, '', NULL);
INSERT INTO `sys_dict_type` VALUES (8, 'material_store_status', '物料库存状态', 1, '物料库存状态列表', '', NULL, '', NULL);
INSERT INTO `sys_dict_type` VALUES (10, 'sys_normal_disable', '系统开关', 1, '系统开关列表', '', '2019-12-06 19:03:32', '', '2019-12-12 19:03:15');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '菜单名称',
  `parent_id` int(11) DEFAULT NULL COMMENT '父菜单ID',
  `path` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '路由地址',
  `component` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '组件路径',
  `redirect` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '跳转路径',
  `perms` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '权限标识',
  `type` tinyint(1) DEFAULT NULL COMMENT '菜单类型 (0 目录，1 菜单，2 按钮)',
  `icon` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '#' COMMENT '菜单图标',
  `sort` int(11) DEFAULT 0 COMMENT '排序',
  `visible` tinyint(1) DEFAULT 1 COMMENT '是否可见',
  `status` tinyint(1) DEFAULT 1 COMMENT '状态（0 禁用，1 开启）',
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '创建人',
  `create_time` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '更新人',
  `update_time` datetime(0) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 41 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '菜单管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, '系统管理', 0, 'admin', '', '/admin/user', NULL, 0, 'component', 6, 1, 1, '', NULL, 'admin', '2020-09-15 13:10:45');
INSERT INTO `sys_menu` VALUES (2, '用户管理', 1, 'user', 'admin/user', NULL, NULL, 1, 'user', 0, 1, 1, '', NULL, 'admin', '2020-09-15 13:12:05');
INSERT INTO `sys_menu` VALUES (37, '角色管理', 1, 'role', 'admin/role', NULL, NULL, 1, 'peoples', 0, 1, 1, '', NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (38, '菜单管理', 1, 'menu', 'admin/menu', NULL, NULL, 1, 'tree-table', 8, 1, 1, '', NULL, 'admin', '2020-09-15 13:12:20');
INSERT INTO `sys_menu` VALUES (39, '字典管理', 1, 'dict', 'admin/dict', NULL, NULL, 1, 'education', 10, 1, 1, '', NULL, 'admin', '2020-09-15 13:11:37');
INSERT INTO `sys_menu` VALUES (40, '部门管理', 1, 'dept', 'admin/dept', NULL, NULL, 1, 'tree', 0, 1, 1, '', NULL, NULL, NULL);

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '权限名称',
  `parent_id` int(11) DEFAULT NULL COMMENT '父编号',
  `perms` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '权限字符串',
  `type` tinyint(1) DEFAULT NULL COMMENT '资源类型',
  `status` tinyint(1) DEFAULT NULL COMMENT '状态（0启用 1禁用）',
  `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'icon',
  `tree_path` tinyint(1) DEFAULT NULL COMMENT '层级',
  `sort` int(11) DEFAULT NULL COMMENT '显示顺序',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '资源路径',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `id`(`id`, `name`, `sort`, `perms`, `status`, `icon`) USING BTREE,
  INDEX `id_2`(`id`, `name`, `sort`, `perms`, `type`, `status`, `icon`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES (1, '系统管理', 0, 'system', NULL, 1, 'System.png', 1, 1, NULL);
INSERT INTO `sys_permission` VALUES (2, '菜单管理', 10, 'system_menu', NULL, 1, NULL, 2, 10, '/system/menu');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '角色名称',
  `perms` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '角色权限字符串',
  `sort` int(11) DEFAULT NULL COMMENT '显示顺序',
  `status` tinyint(1) DEFAULT 0 COMMENT '角色状态（0正常 1停用）',
  `deleted` tinyint(1) DEFAULT 0 COMMENT '删除标识  (0未删除 1已删除)',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '描述',
  `create_time` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '超级管理员', 'root', 1, 1, 0, '超级管理员', '2018-12-23 16:00:00', 'admin', '2020-09-11 17:04:23', 'admin');
INSERT INTO `sys_role` VALUES (2, '仓库管理员', 'admin', 2, 1, 0, '系统管理员', '2018-12-23 16:00:00', 'admin', '2020-09-12 22:22:06', 'admin');
INSERT INTO `sys_role` VALUES (3, '普通用户', 'common', 3, 1, 0, '普通用户', '2019-05-05 16:00:00', 'admin', '2020-09-11 17:13:46', 'admin');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `menu_id` int(11) NOT NULL COMMENT '菜单ID'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色和菜单关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (1, 1);
INSERT INTO `sys_role_menu` VALUES (1, 2);
INSERT INTO `sys_role_menu` VALUES (2, 1);
INSERT INTO `sys_role_menu` VALUES (1, 21);
INSERT INTO `sys_role_menu` VALUES (1, 22);
INSERT INTO `sys_role_menu` VALUES (1, 23);
INSERT INTO `sys_role_menu` VALUES (1, 28);
INSERT INTO `sys_role_menu` VALUES (1, 24);
INSERT INTO `sys_role_menu` VALUES (1, 26);
INSERT INTO `sys_role_menu` VALUES (1, 27);
INSERT INTO `sys_role_menu` VALUES (1, 32);
INSERT INTO `sys_role_menu` VALUES (1, 33);
INSERT INTO `sys_role_menu` VALUES (1, 34);
INSERT INTO `sys_role_menu` VALUES (1, 37);
INSERT INTO `sys_role_menu` VALUES (1, 38);
INSERT INTO `sys_role_menu` VALUES (1, 39);
INSERT INTO `sys_role_menu` VALUES (2, 21);
INSERT INTO `sys_role_menu` VALUES (2, 22);
INSERT INTO `sys_role_menu` VALUES (2, 23);
INSERT INTO `sys_role_menu` VALUES (2, 28);
INSERT INTO `sys_role_menu` VALUES (2, 2);
INSERT INTO `sys_role_menu` VALUES (3, 21);
INSERT INTO `sys_role_menu` VALUES (3, 22);
INSERT INTO `sys_role_menu` VALUES (3, 23);
INSERT INTO `sys_role_menu` VALUES (3, 28);
INSERT INTO `sys_role_menu` VALUES (3, 17);
INSERT INTO `sys_role_menu` VALUES (3, 18);
INSERT INTO `sys_role_menu` VALUES (3, 19);
INSERT INTO `sys_role_menu` VALUES (3, 20);
INSERT INTO `sys_role_menu` VALUES (2, 29);
INSERT INTO `sys_role_menu` VALUES (2, 35);
INSERT INTO `sys_role_menu` VALUES (2, 36);
INSERT INTO `sys_role_menu` VALUES (2, 30);
INSERT INTO `sys_role_menu` VALUES (2, 31);
INSERT INTO `sys_role_menu` VALUES (2, 17);
INSERT INTO `sys_role_menu` VALUES (2, 18);
INSERT INTO `sys_role_menu` VALUES (2, 19);
INSERT INTO `sys_role_menu` VALUES (2, 20);
INSERT INTO `sys_role_menu` VALUES (2, 26);
INSERT INTO `sys_role_menu` VALUES (2, 32);
INSERT INTO `sys_role_menu` VALUES (2, 33);
INSERT INTO `sys_role_menu` VALUES (2, 34);
INSERT INTO `sys_role_menu` VALUES (2, 37);
INSERT INTO `sys_role_menu` VALUES (2, 38);
INSERT INTO `sys_role_menu` VALUES (2, 39);
INSERT INTO `sys_role_menu` VALUES (2, 24);
INSERT INTO `sys_role_menu` VALUES (2, 40);

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission`  (
  `role_id` int(11) DEFAULT NULL COMMENT '角色id',
  `permission_id` int(11) DEFAULT NULL COMMENT '权限id',
  INDEX `role_id`(`role_id`) USING BTREE,
  INDEX `permission_id`(`permission_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
INSERT INTO `sys_role_permission` VALUES (1, 1);
INSERT INTO `sys_role_permission` VALUES (1, 2);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用户名',
  `nickname` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '昵称',
  `gender` tinyint(1) DEFAULT 0 COMMENT '性别',
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '密码',
  `salt` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '加密盐',
  `dept_id` int(11) DEFAULT NULL COMMENT '部门ID',
  `deleted` tinyint(1) DEFAULT 0 COMMENT '删除标识（0未删除 1已删除）',
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '用户头像',
  `mobile` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '联系方式',
  `status` tinyint(1) DEFAULT 0 COMMENT '用户状态（0正常 1禁用）',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用户邮箱',
  `create_time` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建用户',
  `update_time` datetime(0) DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '更新用户',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `login_name`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', '系统管理员', 0, '$2a$10$dLq3.pXNwTNqWabsRfJX4ej8Htk/vUWuHh.LvITq5BrU8u.dYvZpC', '51379ee6-a921-4804-95eb-779bcef24013', 5, 0, 'https://i.loli.net/2020/05/08/dVvpaQ8NHkWAC2c.jpg', '17621210366', 1, '1490493387@qq.com', '2019-10-10 13:41:22', 'admin', '2020-03-09 10:12:54', 'fly4j');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户和角色关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1);
INSERT INTO `sys_user_role` VALUES (1, 2);

SET FOREIGN_KEY_CHECKS = 1;

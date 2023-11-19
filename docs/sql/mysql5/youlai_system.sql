/*
 Navicat Premium Data Transfer

 Source Server         : www.youlai.tech
 Source Server Type    : MySQL
 Source Server Version : 5
 Source Host           : www.youlai.tech:3306
 Source Schema         : youlai_system

 Target Server Type    : MySQL
 Target Server Version : 5
 File Encoding         : 65001

 Date: 18/07/2023 20:14:16
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
                           `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                           `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '部门名称',
                           `parent_id` bigint NOT NULL DEFAULT 0 COMMENT '父节点id',
                           `tree_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '父节点id路径',
                           `sort` int NULL DEFAULT 0 COMMENT '显示顺序',
                           `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态(1:正常;0:禁用)',
                           `deleted` tinyint NULL DEFAULT 0 COMMENT '逻辑删除标识(1:已删除;0:未删除)',
                           `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
                           `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
                           `create_by` bigint NULL DEFAULT NULL COMMENT '创建人ID',
                           `update_by` bigint NULL DEFAULT NULL COMMENT '修改人ID',
                           PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '部门表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES (1, '有来技术', 0, '0', 1, 1, 0, NULL, NULL, 1, 1);
INSERT INTO `sys_dept` VALUES (2, '研发部门', 1, '0,1', 1, 1, 0, NULL, '2022-04-19 12:46:37', 2, 2);
INSERT INTO `sys_dept` VALUES (3, '测试部门', 1, '0,1', 1, 1, 0, NULL, '2022-04-19 12:46:37', 2, 2);

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict`  (
                           `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                           `type_code` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字典类型编码',
                           `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '字典项名称',
                           `value` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '字典项值',
                           `sort` int NULL DEFAULT 0 COMMENT '排序',
                           `status` tinyint NULL DEFAULT 0 COMMENT '状态(1:正常;0:禁用)',
                           `defaulted` tinyint NULL DEFAULT 0 COMMENT '是否默认(1:是;0:否)',
                           `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '备注',
                           `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
                           `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
                           PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '字典数据表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES (1, 'gender', '男', '1', 1, 1, 0, NULL, '2019-05-05 13:07:52', '2022-06-12 23:20:39');
INSERT INTO `sys_dict` VALUES (2, 'gender', '女', '2', 2, 1, 0, NULL, '2019-04-19 11:33:00', '2019-07-02 14:23:05');
INSERT INTO `sys_dict` VALUES (3, 'gender', '未知', '0', 1, 1, 0, NULL, '2020-10-17 08:09:31', '2020-10-17 08:09:31');

-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type`  (
                                `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键 ',
                                `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '类型名称',
                                `code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '类型编码',
                                `status` tinyint(1) NULL DEFAULT 0 COMMENT '状态(0:正常;1:禁用)',
                                `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
                                `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
                                `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
                                PRIMARY KEY (`id`) USING BTREE,
                                UNIQUE INDEX `type_code`(`code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '字典类型表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dict_type
-- ----------------------------
INSERT INTO `sys_dict_type` VALUES (1, '性别', 'gender', 1, NULL, '2019-12-06 19:03:32', '2022-06-12 16:21:28');

--- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
                             `id` bigint NOT NULL AUTO_INCREMENT,
                             `parent_id` bigint NULL DEFAULT NULL COMMENT '父菜单ID',
                             `type` tinyint NULL DEFAULT NULL COMMENT '菜单类型(1：菜单；2：目录；3：外链；4：按钮)',
                             `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '菜单名称',
                             `path` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '路由路径(浏览器地址栏路径)',
                             `component` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组件路径(vue页面完整路径，省略.vue后缀)',
                             `perm` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '按钮权限标识',
                             `icon` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '菜单图标',
                             `sort` int NULL DEFAULT 0 COMMENT '排序',
                             `visible` tinyint(1) NULL DEFAULT 1 COMMENT '状态(0:禁用;1:开启)',
                             `redirect` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '跳转路径',
                             `tree_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                             `always_show` tinyint NULL DEFAULT NULL COMMENT '【目录】只有一个子路由是否始终显示(1:是 0:否)',
                             `keep_alive` tinyint NULL DEFAULT NULL COMMENT '【菜单】是否开启页面缓存(1:是 0:否)',
                             `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
                             `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
                             PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 43 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '菜单管理' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, 0, 2, '系统管理', '/system', 'Layout', NULL, 'system', 1, 1, '/system/user', NULL, NULL, NULL, '2021-08-28 09:12:21', '2021-08-28 09:12:21');
INSERT INTO `sys_menu` VALUES (2, 1, 1, '用户管理', 'user', 'system/user/index', NULL, 'user', 1, 1, NULL, NULL, NULL, 1, '2021-08-28 09:12:21', '2021-08-28 09:12:21');
INSERT INTO `sys_menu` VALUES (3, 1, 1, '角色管理', 'role', 'system/role/index', NULL, 'role', 2, 1, NULL, NULL, NULL, 1, '2021-08-28 09:12:21', '2021-08-28 09:12:21');
INSERT INTO `sys_menu` VALUES (4, 1, 1, '菜单管理', 'cmenu', 'system/menu/index', NULL, 'menu', 3, 1, NULL, NULL, NULL, 1, '2021-08-28 09:12:21', '2021-08-28 09:12:21');
INSERT INTO `sys_menu` VALUES (5, 1, 1, '部门管理', 'dept', 'system/dept/index', NULL, 'tree', 4, 1, NULL, NULL, NULL, 1, '2021-08-28 09:12:21', '2021-08-28 09:12:21');
INSERT INTO `sys_menu` VALUES (6, 1, 1, '字典管理', 'dict', 'system/dict/index', NULL, 'dict', 5, 1, NULL, NULL, NULL, 1, '2021-08-28 09:12:21', '2021-08-28 09:12:21');
INSERT INTO `sys_menu` VALUES (9, 0, 2, '营销管理', '/sms', 'Layout', NULL, 'number', 5, 1, NULL, NULL, NULL, NULL, '2021-08-28 09:12:21', '2021-08-28 09:12:21');
INSERT INTO `sys_menu` VALUES (10, 9, 1, '广告列表', 'advert', 'sms/advert/index', NULL, 'advert', 1, 1, NULL, NULL, NULL, 1, '2021-08-28 09:12:21', '2021-08-28 09:12:21');
INSERT INTO `sys_menu` VALUES (11, 0, 1, '商品管理', '/pms', 'Layout', NULL, 'goods', 2, 1, '/pms/goods', NULL, NULL, 1, '2021-08-28 09:12:21', '2021-08-28 09:12:21');
INSERT INTO `sys_menu` VALUES (12, 11, 1, '商品列表', 'goods', 'pms/goods/index', NULL, 'goods-list', 1, 1, NULL, NULL, NULL, 1, '2021-08-28 09:12:21', '2021-08-28 09:12:21');
INSERT INTO `sys_menu` VALUES (13, 0, 1, '订单管理', '/oms', 'Layout', NULL, 'shopping', 3, 1, '/oms/order', NULL, NULL, 1, '2021-08-28 09:12:21', '2021-08-28 09:12:21');
INSERT INTO `sys_menu` VALUES (14, 13, 1, '订单列表', 'order', 'oms/order/index', NULL, 'order', 1, 1, NULL, NULL, NULL, 1, '2021-08-28 09:12:21', '2021-08-28 09:12:21');
INSERT INTO `sys_menu` VALUES (15, 0, 1, '会员管理', '/ums', 'Layout', NULL, 'user', 4, 1, '/ums/member', NULL, NULL, 1, '2021-08-28 09:12:21', '2021-08-28 09:12:21');
INSERT INTO `sys_menu` VALUES (16, 15, 1, '会员列表', 'member', 'ums/member/index', NULL, 'peoples', 1, 1, NULL, NULL, NULL, 1, '2021-08-28 09:12:21', '2021-08-28 09:12:21');
INSERT INTO `sys_menu` VALUES (17, 11, 1, '品牌管理', 'brand', 'pms/brand/index', NULL, 'brand', 5, 1, NULL, NULL, NULL, 1, '2021-08-28 09:12:21', '2021-08-28 09:12:21');
INSERT INTO `sys_menu` VALUES (18, 11, 1, '商品分类', 'category', 'pms/category/index', NULL, 'menu', 3, 1, NULL, NULL, NULL, 1, '2021-08-28 09:12:21', '2021-08-28 09:12:21');
INSERT INTO `sys_menu` VALUES (19, 11, 1, '商品上架', 'goods-detail', 'pms/goods/detail', NULL, 'publish', 2, 1, NULL, NULL, NULL, NULL, '2021-08-28 09:12:21', '2021-08-28 09:12:21');
INSERT INTO `sys_menu` VALUES (20, 0, 2, '多级菜单', '/multi-level', 'Layout', NULL, 'nested', 7, 1, '/nested/level1/level2', NULL, NULL, NULL, '2022-02-16 23:11:00', '2022-02-16 23:11:00');
INSERT INTO `sys_menu` VALUES (21, 20, 1, '菜单一级', 'level1', 'demo/multi-level/level1', NULL, '', 1, 1, '/nested/level1/level2', NULL, NULL, NULL, '2022-02-16 23:13:38', '2022-02-16 23:13:38');
INSERT INTO `sys_menu` VALUES (22, 21, 1, '菜单二级', 'level2', 'demo/multi-level/children/level2', NULL, '', 1, 1, '/nested/level1/level2/level3', NULL, NULL, NULL, '2022-02-16 23:14:23', '2022-02-16 23:14:23');
INSERT INTO `sys_menu` VALUES (23, 22, 1, '菜单三级-1', 'level3-1', 'demo/multi-level/children/children/level3-1', NULL, '', 1, 1, '', NULL, NULL, NULL, '2022-02-16 23:14:51', '2022-02-16 23:14:51');
INSERT INTO `sys_menu` VALUES (24, 22, 1, '菜单三级-2', 'level3-2', 'demo/multi-level/children/children/level3-2', NULL, '', 2, 1, '', NULL, NULL, NULL, '2022-02-16 23:15:08', '2022-02-16 23:15:08');
INSERT INTO `sys_menu` VALUES (26, 0, 1, '外部链接', '/external-link', 'Layout', NULL, 'link', 9, 1, 'noredirect', NULL, NULL, NULL, '2022-02-17 22:51:20', '2022-02-17 22:51:20');
INSERT INTO `sys_menu` VALUES (30, 26, 3, '项目文档', 'https://juejin.cn/post/7228990409909108793', '', NULL, 'link', 1, 1, '', NULL, NULL, NULL, '2022-02-18 00:01:40', '2022-02-18 00:01:40');
INSERT INTO `sys_menu` VALUES (37, 9, 1, '优惠券列表', 'coupon', 'sms/coupon/index', NULL, 'menu', 2, 1, '', NULL, NULL, NULL, '2022-05-29 00:24:07', '2022-05-29 00:24:07');
INSERT INTO `sys_menu` VALUES (40, 2, 4, '新增用户', '', NULL, 'sys:user:add', '', 1, 1, '', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (41, 2, 4, '修改用户', '', NULL, 'sys:user:edit', '', 2, 1, '', NULL, NULL, NULL, '2022-11-05 01:26:44', '2022-11-05 01:26:44');
INSERT INTO `sys_menu` VALUES (42, 2, 4, '删除用户', '', NULL, 'sys:user:del', '', 3, 1, '', NULL, NULL, NULL, '2022-11-05 01:27:13', '2022-11-05 01:27:13');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
                           `id` bigint NOT NULL AUTO_INCREMENT,
                           `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '角色名称',
                           `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色编码',
                           `sort` int NULL DEFAULT NULL COMMENT '显示顺序',
                           `status` tinyint(1) NULL DEFAULT 1 COMMENT '角色状态(1-正常；0-停用)',
                           `data_scope` tinyint NULL DEFAULT NULL COMMENT '数据权限(0-所有数据；1-部门及子部门数据；2-本部门数据；3-本人数据)',
                           `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除标识(0-未删除；1-已删除)',
                           `create_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
                           `update_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
                           PRIMARY KEY (`id`) USING BTREE,
                           UNIQUE INDEX `name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 128 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '超级管理员', 'ROOT', 1, 1, 0, 0, '2021-05-21 14:56:51', '2018-12-23 16:00:00');
INSERT INTO `sys_role` VALUES (2, '系统管理员', 'ADMIN', 2, 1, 1, 0, '2021-03-25 12:39:54', NULL);
INSERT INTO `sys_role` VALUES (3, '访问游客', 'GUEST', 3, 1, 2, 0, '2021-05-26 15:49:05', '2019-05-05 16:00:00');
INSERT INTO `sys_role` VALUES (4, '系统管理员1', 'ADMIN1', 2, 1, 1, 0, '2021-03-25 12:39:54', NULL);
INSERT INTO `sys_role` VALUES (5, '系统管理员2', 'ADMIN1', 2, 1, 1, 0, '2021-03-25 12:39:54', NULL);
INSERT INTO `sys_role` VALUES (6, '系统管理员3', 'ADMIN1', 2, 1, 1, 0, '2021-03-25 12:39:54', NULL);
INSERT INTO `sys_role` VALUES (7, '系统管理员4', 'ADMIN1', 2, 1, 1, 0, '2021-03-25 12:39:54', NULL);
INSERT INTO `sys_role` VALUES (8, '系统管理员5', 'ADMIN1', 2, 1, 1, 0, '2021-03-25 12:39:54', NULL);
INSERT INTO `sys_role` VALUES (9, '系统管理员6', 'ADMIN1', 2, 1, 1, 0, '2021-03-25 12:39:54', NULL);
INSERT INTO `sys_role` VALUES (10, '系统管理员7', 'ADMIN1', 2, 1, 1, 0, '2021-03-25 12:39:54', NULL);
INSERT INTO `sys_role` VALUES (11, '系统管理员8', 'ADMIN1', 2, 1, 1, 0, '2021-03-25 12:39:54', NULL);
INSERT INTO `sys_role` VALUES (12, '系统管理员9', 'ADMIN1', 2, 1, 1, 0, '2021-03-25 12:39:54', NULL);

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
                                `role_id` bigint NOT NULL COMMENT '角色ID',
                                `menu_id` bigint NOT NULL COMMENT '菜单ID'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色和菜单关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (2, 1);
INSERT INTO `sys_role_menu` VALUES (2, 2);
INSERT INTO `sys_role_menu` VALUES (2, 3);
INSERT INTO `sys_role_menu` VALUES (2, 4);
INSERT INTO `sys_role_menu` VALUES (2, 5);
INSERT INTO `sys_role_menu` VALUES (2, 6);
INSERT INTO `sys_role_menu` VALUES (2, 20);
INSERT INTO `sys_role_menu` VALUES (2, 21);
INSERT INTO `sys_role_menu` VALUES (2, 22);
INSERT INTO `sys_role_menu` VALUES (2, 23);
INSERT INTO `sys_role_menu` VALUES (2, 24);
INSERT INTO `sys_role_menu` VALUES (2, 26);
INSERT INTO `sys_role_menu` VALUES (2, 30);
INSERT INTO `sys_role_menu` VALUES (2, 31);
INSERT INTO `sys_role_menu` VALUES (2, 32);
INSERT INTO `sys_role_menu` VALUES (2, 33);
INSERT INTO `sys_role_menu` VALUES (2, 36);
INSERT INTO `sys_role_menu` VALUES (2, 37);
INSERT INTO `sys_role_menu` VALUES (2, 38);
INSERT INTO `sys_role_menu` VALUES (2, 39);
INSERT INTO `sys_role_menu` VALUES (2, 40);
INSERT INTO `sys_role_menu` VALUES (2, 41);
INSERT INTO `sys_role_menu` VALUES (2, 70);
INSERT INTO `sys_role_menu` VALUES (2, 71);
INSERT INTO `sys_role_menu` VALUES (2, 72);
INSERT INTO `sys_role_menu` VALUES (2, 73);
INSERT INTO `sys_role_menu` VALUES (2, 74);
INSERT INTO `sys_role_menu` VALUES (2, 75);
INSERT INTO `sys_role_menu` VALUES (2, 76);
INSERT INTO `sys_role_menu` VALUES (2, 77);
INSERT INTO `sys_role_menu` VALUES (2, 78);
INSERT INTO `sys_role_menu` VALUES (2, 79);
INSERT INTO `sys_role_menu` VALUES (2, 81);
INSERT INTO `sys_role_menu` VALUES (2, 84);
INSERT INTO `sys_role_menu` VALUES (2, 85);
INSERT INTO `sys_role_menu` VALUES (2, 86);
INSERT INTO `sys_role_menu` VALUES (2, 87);
INSERT INTO `sys_role_menu` VALUES (2, 88);
INSERT INTO `sys_role_menu` VALUES (2, 100);
INSERT INTO `sys_role_menu` VALUES (2, 101);
INSERT INTO `sys_role_menu` VALUES (2, 200);
INSERT INTO `sys_role_menu` VALUES (2, 201);
INSERT INTO `sys_role_menu` VALUES (2, 202);
INSERT INTO `sys_role_menu` VALUES (2, 203);
INSERT INTO `sys_role_menu` VALUES (2, 204);
INSERT INTO `sys_role_menu` VALUES (2, 300);
INSERT INTO `sys_role_menu` VALUES (2, 301);
INSERT INTO `sys_role_menu` VALUES (2, 400);
INSERT INTO `sys_role_menu` VALUES (2, 401);
INSERT INTO `sys_role_menu` VALUES (2, 402);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
                           `id` int NOT NULL AUTO_INCREMENT,
                           `username` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
                           `nickname` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
                           `gender` tinyint(1) NULL DEFAULT 1 COMMENT '性别((1:男;2:女))',
                           `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
                           `dept_id` int NULL DEFAULT NULL COMMENT '部门ID',
                           `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '用户头像',
                           `mobile` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系方式',
                           `status` tinyint(1) NULL DEFAULT 1 COMMENT '用户状态((1:正常;0:禁用))',
                           `email` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户邮箱',
                           `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除标识(0:未删除;1:已删除)',
                           `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
                           `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
                           PRIMARY KEY (`id`) USING BTREE,
                           UNIQUE INDEX `login_name`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 288 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'root', '有来技术', 0, '$2a$10$xVWsNOhHrCxh5UbpCE7/HuJ.PAOKcYAqRxD2CO2nVnJS.IAXkr5aq', NULL, 'https://oss.youlai.tech/youlai-boot/2023/05/16/811270ef31f548af9cffc026dfc3777b.gif', '17621590365', 1, 'youlaitech@163.com', 0, NULL, NULL);
INSERT INTO `sys_user` VALUES (2, 'admin', '系统管理员', 1, '$2a$10$xVWsNOhHrCxh5UbpCE7/HuJ.PAOKcYAqRxD2CO2nVnJS.IAXkr5aq', 1, 'https://oss.youlai.tech/youlai-boot/2023/05/16/811270ef31f548af9cffc026dfc3777b.gif', '17621210366', 1, '', 0, '2019-10-10 13:41:22', '2022-07-31 12:39:30');
INSERT INTO `sys_user` VALUES (3, 'test', '测试小用户', 1, '$2a$10$xVWsNOhHrCxh5UbpCE7/HuJ.PAOKcYAqRxD2CO2nVnJS.IAXkr5aq', 3, 'https://oss.youlai.tech/youlai-boot/2023/05/16/811270ef31f548af9cffc026dfc3777b.gif', '17621210366', 1, 'youlaitech@163.com', 0, '2021-06-05 01:31:29', '2021-06-05 01:31:29');
INSERT INTO `sys_user` VALUES (287, '123', '123', 1, '$2a$10$mVoBVqm1837huf7kcN0wS.GVYKEFv0arb7GvzfFXoTyqDlcRzT.6i', 1, '', NULL, 1, NULL, 1, '2023-05-21 14:11:19', '2023-05-21 14:11:25');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
                                `user_id` bigint NOT NULL COMMENT '用户ID',
                                `role_id` bigint NOT NULL COMMENT '角色ID',
                                PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户和角色关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1);
INSERT INTO `sys_user_role` VALUES (2, 2);
INSERT INTO `sys_user_role` VALUES (3, 3);
INSERT INTO `sys_user_role` VALUES (287, 2);

SET FOREIGN_KEY_CHECKS = 1;

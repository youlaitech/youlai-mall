use youlai_system;

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
                             `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                             `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '部门名称',
                             `parent_id` bigint NULL DEFAULT 0 COMMENT '父节点id',
                             `tree_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '父节点id路径',
                             `sort` int NULL DEFAULT 0 COMMENT '显示顺序',
                             `status` tinyint NULL DEFAULT 1 COMMENT '状态(1:正常;0:禁用)',
                             `deleted` tinyint NULL DEFAULT 0 COMMENT '逻辑删除标识(1:已删除;0:未删除)',
                             `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
                             `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
                             PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 160 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '部门表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES (1, '有来技术', 0, '0', 1, 1, 0, NULL, NULL);
INSERT INTO `sys_dept` VALUES (2, '研发部门', 1, '0,1', 1, 1, 0, NULL, '2022-04-19 12:46:37');
INSERT INTO `sys_dept` VALUES (3, '测试部门', 1, '0,1', 1, 1, 0, NULL, '2022-04-19 12:46:37');

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
                                  UNIQUE INDEX `type_code`(`code` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 31 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '字典类型表' ROW_FORMAT = DYNAMIC;

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
) ENGINE = InnoDB AUTO_INCREMENT = 89 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '菜单管理' ROW_FORMAT = DYNAMIC;

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
INSERT INTO `sys_menu` VALUES (11, 0, 1, '商品管理', '/product', 'Layout', NULL, 'el-icon-Goods', 2, 1, '/product/goods', '0', NULL, 1, '2021-08-28 09:12:21', '2024-03-03 23:54:35');
INSERT INTO `sys_menu` VALUES (12, 11, 1, '商品列表', 'goods', 'product/goods/index', NULL, 'goods-list', 1, 1, NULL, NULL, NULL, 1, '2021-08-28 09:12:21', '2021-08-28 09:12:21');
INSERT INTO `sys_menu` VALUES (13, 0, 2, '订单管理', '/oms', 'Layout', NULL, 'el-icon-ShoppingCart', 3, 1, '/oms/order', '0', 1, 1, '2021-08-28 09:12:21', '2024-03-03 23:52:32');
INSERT INTO `sys_menu` VALUES (14, 13, 1, '订单列表', 'order', 'oms/order/index', NULL, 'el-icon-Document', 1, 1, NULL, 'null,13', NULL, 1, '2021-08-28 09:12:21', '2024-03-03 23:45:09');
INSERT INTO `sys_menu` VALUES (15, 0, 1, '会员管理', '/ums', 'Layout', NULL, 'user', 4, 1, '/ums/member', NULL, NULL, 1, '2021-08-28 09:12:21', '2021-08-28 09:12:21');
INSERT INTO `sys_menu` VALUES (16, 15, 1, '会员列表', 'member', 'ums/member/index', NULL, 'peoples', 1, 1, NULL, NULL, NULL, 1, '2021-08-28 09:12:21', '2021-08-28 09:12:21');
INSERT INTO `sys_menu` VALUES (17, 11, 1, '品牌管理', 'brand', 'product/brand/index', NULL, 'brand', 5, 1, NULL, NULL, NULL, 1, '2021-08-28 09:12:21', '2021-08-28 09:12:21');
INSERT INTO `sys_menu` VALUES (18, 11, 1, '商品分类', 'category', 'product/category/index', NULL, 'menu', 3, 1, NULL, NULL, NULL, 1, '2021-08-28 09:12:21', '2021-08-28 09:12:21');
INSERT INTO `sys_menu` VALUES (19, 11, 1, '商品上架', 'goods-detail', 'product/goods/detail', NULL, 'publish', 2, 1, NULL, NULL, NULL, NULL, '2021-08-28 09:12:21', '2021-08-28 09:12:21');
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
INSERT INTO `sys_menu` VALUES (70, 3, 4, '角色新增', '', NULL, 'sys:role:add', '', 1, 1, NULL, '0,1,3', NULL, NULL, '2023-05-20 23:39:09', '2023-05-20 23:39:09');
INSERT INTO `sys_menu` VALUES (71, 3, 4, '角色编辑', '', NULL, 'sys:role:edit', '', 2, 1, NULL, '0,1,3', NULL, NULL, '2023-05-20 23:40:31', '2023-05-20 23:40:31');
INSERT INTO `sys_menu` VALUES (72, 3, 4, '角色删除', '', NULL, 'sys:role:delete', '', 3, 1, NULL, '0,1,3', NULL, NULL, '2023-05-20 23:41:08', '2023-05-20 23:41:08');
INSERT INTO `sys_menu` VALUES (73, 4, 4, '菜单新增', '', NULL, 'sys:menu:add', '', 1, 1, NULL, '0,1,4', NULL, NULL, '2023-05-20 23:41:35', '2023-05-20 23:41:35');
INSERT INTO `sys_menu` VALUES (74, 4, 4, '菜单编辑', '', NULL, 'sys:menu:edit', '', 3, 1, NULL, '0,1,4', NULL, NULL, '2023-05-20 23:41:58', '2023-05-20 23:41:58');
INSERT INTO `sys_menu` VALUES (75, 4, 4, '菜单删除', '', NULL, 'sys:menu:delete', '', 3, 1, NULL, '0,1,4', NULL, NULL, '2023-05-20 23:44:18', '2023-05-20 23:44:18');
INSERT INTO `sys_menu` VALUES (76, 5, 4, '部门新增', '', NULL, 'sys:dept:add', '', 1, 1, NULL, '0,1,5', NULL, NULL, '2023-05-20 23:45:00', '2023-05-20 23:45:00');
INSERT INTO `sys_menu` VALUES (77, 5, 4, '部门编辑', '', NULL, 'sys:dept:edit', '', 2, 1, NULL, '0,1,5', NULL, NULL, '2023-05-20 23:46:16', '2023-05-20 23:46:16');
INSERT INTO `sys_menu` VALUES (78, 5, 4, '部门删除', '', NULL, 'sys:dept:delete', '', 3, 1, NULL, '0,1,5', NULL, NULL, '2023-05-20 23:46:36', '2023-05-20 23:46:36');
INSERT INTO `sys_menu` VALUES (79, 6, 4, '字典类型新增', '', NULL, 'sys:dict_type:add', '', 1, 1, NULL, '0,1,6', NULL, NULL, '2023-05-21 00:16:06', '2023-05-21 00:16:06');
INSERT INTO `sys_menu` VALUES (81, 6, 4, '字典类型编辑', '', NULL, 'sys:dict_type:edit', '', 2, 1, NULL, '0,1,6', NULL, NULL, '2023-05-21 00:27:37', '2023-05-21 00:27:37');
INSERT INTO `sys_menu` VALUES (84, 6, 4, '字典类型删除', '', NULL, 'sys:dict_type:delete', '', 3, 1, NULL, '0,1,6', NULL, NULL, '2023-05-21 00:29:39', '2023-05-21 00:29:39');
INSERT INTO `sys_menu` VALUES (85, 6, 4, '字典数据新增', '', NULL, 'sys:dict:add', '', 4, 1, NULL, '0,1,6', NULL, NULL, '2023-05-21 00:46:56', '2023-05-21 00:47:06');
INSERT INTO `sys_menu` VALUES (86, 6, 4, '字典数据编辑', '', NULL, 'sys:dict:edit', '', 5, 1, NULL, '0,1,6', NULL, NULL, '2023-05-21 00:47:36', '2023-05-21 00:47:36');
INSERT INTO `sys_menu` VALUES (87, 6, 4, '字典数据删除', '', NULL, 'sys:dict:delete', '', 6, 1, NULL, '0,1,6', NULL, NULL, '2023-05-21 00:48:10', '2023-05-21 00:48:20');
INSERT INTO `sys_menu` VALUES (88, 2, 4, '重置密码', '', NULL, 'sys:user:reset_pwd', '', 4, 1, NULL, '0,1,2', NULL, NULL, '2023-05-21 00:49:18', '2023-05-21 00:49:18');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
                             `id` bigint NOT NULL AUTO_INCREMENT,
                             `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '角色名称',
                             `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色编码',
                             `sort` int NULL DEFAULT NULL COMMENT '显示顺序',
                             `status` tinyint NULL DEFAULT 1 COMMENT '角色状态(1-正常；0-停用)',
                             `deleted` tinyint NOT NULL DEFAULT 0 COMMENT '逻辑删除标识(0-未删除；1-已删除)',
                             `data_scope` tinyint NULL DEFAULT NULL COMMENT '数据权限(0-所有数据；1-部门及子部门数据；2-本部门数据；3-本人数据)',
                             `create_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
                             `update_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
                             PRIMARY KEY (`id`) USING BTREE,
                             UNIQUE INDEX `name`(`name` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '超级管理员', 'ROOT', 1, 1, 0, 0, '2021-05-21 14:56:51', '2018-12-23 16:00:00');
INSERT INTO `sys_role` VALUES (2, '系统管理员', 'ADMIN', 1, 1, 0, 0, '2021-03-25 12:39:54', '2022-11-05 00:22:02');
INSERT INTO `sys_role` VALUES (3, '访问游客', 'GUEST', 3, 1, 0, 30, '2021-05-26 15:49:05', '2019-05-05 16:00:00');

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
INSERT INTO `sys_role_menu` VALUES (2, 40);
INSERT INTO `sys_role_menu` VALUES (2, 41);
INSERT INTO `sys_role_menu` VALUES (2, 42);
INSERT INTO `sys_role_menu` VALUES (2, 88);
INSERT INTO `sys_role_menu` VALUES (2, 3);
INSERT INTO `sys_role_menu` VALUES (2, 70);
INSERT INTO `sys_role_menu` VALUES (2, 71);
INSERT INTO `sys_role_menu` VALUES (2, 72);
INSERT INTO `sys_role_menu` VALUES (2, 4);
INSERT INTO `sys_role_menu` VALUES (2, 73);
INSERT INTO `sys_role_menu` VALUES (2, 74);
INSERT INTO `sys_role_menu` VALUES (2, 75);
INSERT INTO `sys_role_menu` VALUES (2, 5);
INSERT INTO `sys_role_menu` VALUES (2, 76);
INSERT INTO `sys_role_menu` VALUES (2, 77);
INSERT INTO `sys_role_menu` VALUES (2, 78);
INSERT INTO `sys_role_menu` VALUES (2, 6);
INSERT INTO `sys_role_menu` VALUES (2, 79);
INSERT INTO `sys_role_menu` VALUES (2, 81);
INSERT INTO `sys_role_menu` VALUES (2, 84);
INSERT INTO `sys_role_menu` VALUES (2, 85);
INSERT INTO `sys_role_menu` VALUES (2, 86);
INSERT INTO `sys_role_menu` VALUES (2, 87);
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
INSERT INTO `sys_role_menu` VALUES (2, 26);
INSERT INTO `sys_role_menu` VALUES (2, 30);

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
                             `create_by` bigint NULL DEFAULT NULL COMMENT '创建人ID',
                             `update_by` bigint NULL DEFAULT NULL COMMENT '更新人ID',
                             PRIMARY KEY (`id`) USING BTREE,
                             UNIQUE INDEX `login_name`(`username` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 100 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'root', '有来技术', 0, '$2a$10$xVWsNOhHrCxh5UbpCE7/HuJ.PAOKcYAqRxD2CO2nVnJS.IAXkr5aq', NULL, 'https://s2.loli.net/2022/04/07/gw1L2Z5sPtS8GIl.gif', '17621210123', 1, 'youlaitech@163.com', 0, '2023-04-12 01:31:29', '2023-04-12 01:31:29', 1, 1);
INSERT INTO `sys_user` VALUES (2, 'admin', '系统管理员', 1, '$2a$10$8/8PxGHA.30EeWg8x4/4BuWuCUJubFbGJXyUYRs7RaJEdVvEMRbWe', 2, 'https://s2.loli.net/2022/04/07/gw1L2Z5sPtS8GIl.gif', '17621210123', 1, 'youlaitech@163.com', 0, '2023-04-12 01:31:29', '2023-04-12 01:31:29', 1, 1);
INSERT INTO `sys_user` VALUES (3, 'test', '测试小用户', 1, '$2a$10$MPJkNw.hKT/fZOgwYP8q9eu/rFJJDsNov697AmdkHNJkpjIpVSw2q', 3, 'https://s2.loli.net/2022/04/07/gw1L2Z5sPtS8GIl.gif', '17621210123', 1, 'youlaitech@163.com', 0, '2023-04-12 01:31:29', '2023-04-12 01:31:29', 1, 1);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
                                  `user_id` int NOT NULL COMMENT '用户ID',
                                  `role_id` int NOT NULL COMMENT '角色ID',
                                  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户和角色关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1);
INSERT INTO `sys_user_role` VALUES (2, 2);
INSERT INTO `sys_user_role` VALUES (3, 3);

SET FOREIGN_KEY_CHECKS = 1;

use youlai_system;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `config_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '配置名称',
  `config_key` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '配置key',
  `config_value` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '配置值',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述、备注',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` bigint NULL DEFAULT NULL COMMENT '更新人ID',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除标识(0-未删除 1-已删除)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统配置表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_config
-- ----------------------------
INSERT INTO `sys_config` VALUES (1, '系统限流QPS', 'IP_QPS_THRESHOLD_LIMIT', '10', '单个IP请求的最大每秒查询数（QPS）阈值Key', '2024-10-27 10:14:51', 1, NULL, NULL, 0);

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
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint NULL DEFAULT 0 COMMENT '逻辑删除标识(1:已删除;0:未删除)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 160 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '部门表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES (1, '有来技术', 0, '0', 1, 1, NULL, NULL, 0);
INSERT INTO `sys_dept` VALUES (2, '研发部门', 1, '0,1', 1, 1, NULL, '2022-04-19 12:46:37', 0);
INSERT INTO `sys_dept` VALUES (3, '测试部门', 1, '0,1', 1, 1, NULL, '2022-04-19 12:46:37', 0);

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键 ',
  `dict_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '类型编码',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '类型名称',
  `status` tinyint(1) NULL DEFAULT 0 COMMENT '状态(0:正常;1:禁用)',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` bigint NULL DEFAULT NULL COMMENT '修改人ID',
  `is_deleted` tinyint NULL DEFAULT 0 COMMENT '是否删除(1-删除，0-未删除)',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_code`(`dict_code` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 89 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '字典类型表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES (1, 'gender', '性别', 1, NULL, '2024-10-15 11:47:09', 1, '2024-10-15 11:47:09', 1, 0);
INSERT INTO `sys_dict` VALUES (2, 'notice_type', '通知类型', 1, NULL, '2024-10-15 11:47:09', 1, '2024-10-15 11:47:09', 1, 0);
INSERT INTO `sys_dict` VALUES (3, 'notice_level', '通知级别', 1, NULL, '2024-10-15 11:47:09', 1, '2024-10-15 11:47:09', 1, 0);

-- ----------------------------
-- Table structure for sys_dict_data
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_data`;
CREATE TABLE `sys_dict_data`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `dict_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '关联字典编码，与sys_dict表中的dict_code对应',
  `value` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '字典项值',
  `label` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '字典项标签',
  `tag_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标签类型，用于前端样式展示（如success、warning等）',
  `status` tinyint NULL DEFAULT 0 COMMENT '状态（1-正常，0-禁用）',
  `sort` int NULL DEFAULT 0 COMMENT '排序',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '备注',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` bigint NULL DEFAULT NULL COMMENT '更新人ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '字典数据表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dict_data
-- ----------------------------
INSERT INTO `sys_dict_data` VALUES (1, 'gender', '1', '男', 'primary', 1, 1, NULL, '2024-10-15 11:47:15', 1, '2024-10-15 11:47:15', 1);
INSERT INTO `sys_dict_data` VALUES (2, 'gender', '2', '女', 'danger', 1, 2, NULL, '2024-10-15 11:47:15', 1, '2024-10-15 11:47:15', 1);
INSERT INTO `sys_dict_data` VALUES (3, 'gender', '0', '保密', 'info', 1, 3, NULL, '2024-10-15 11:47:15', 1, '2024-10-15 11:47:15', 1);
INSERT INTO `sys_dict_data` VALUES (4, 'notice_type', '1', '系统升级', 'success', 1, 1, '', '2024-10-15 11:47:15', 1, '2024-10-15 11:47:15', 1);
INSERT INTO `sys_dict_data` VALUES (5, 'notice_type', '2', '系统维护', 'primary', 1, 2, '', '2024-10-15 11:47:15', 1, '2024-10-15 11:47:15', 1);
INSERT INTO `sys_dict_data` VALUES (6, 'notice_type', '3', '安全警告', 'danger', 1, 3, '', '2024-10-15 11:47:15', 1, '2024-10-15 11:47:15', 1);
INSERT INTO `sys_dict_data` VALUES (7, 'notice_type', '4', '假期通知', 'success', 1, 4, '', '2024-10-15 11:47:15', 1, '2024-10-15 11:47:15', 1);
INSERT INTO `sys_dict_data` VALUES (8, 'notice_type', '5', '公司新闻', 'primary', 1, 5, '', '2024-10-15 11:47:15', 1, '2024-10-15 11:47:15', 1);
INSERT INTO `sys_dict_data` VALUES (9, 'notice_type', '99', '其他', 'info', 1, 99, '', '2024-10-15 11:47:15', 1, '2024-10-15 11:47:15', 1);
INSERT INTO `sys_dict_data` VALUES (10, 'notice_level', 'L', '低', 'info', 1, 1, '', '2024-10-15 11:47:15', 1, '2024-10-15 11:47:15', 1);
INSERT INTO `sys_dict_data` VALUES (11, 'notice_level', 'M', '中', 'warning', 1, 2, '', '2024-10-15 11:47:15', 1, '2024-10-15 11:47:15', 1);
INSERT INTO `sys_dict_data` VALUES (12, 'notice_level', 'H', '高', 'danger', 1, 3, '', '2024-10-15 11:47:15', 1, '2024-10-15 11:47:15', 1);

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `module` enum('LOGIN','USER','ROLE','DEPT','MENU','DICT','OTHER') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '日志模块',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '日志内容',
  `request_uri` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求路径',
  `ip` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'IP地址',
  `province` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '省份',
  `city` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '城市',
  `execution_time` bigint NULL DEFAULT NULL COMMENT '执行时间(ms)',
  `browser` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '浏览器',
  `browser_version` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '浏览器版本',
  `os` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '终端系统',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `is_deleted` tinyint NOT NULL DEFAULT 0 COMMENT '逻辑删除标识(1-已删除 0-未删除)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统日志表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `parent_id` bigint NULL DEFAULT NULL COMMENT '父菜单ID',
  `type` tinyint NULL DEFAULT NULL COMMENT '菜单类型(1：菜单；2：目录；3：外链；4：按钮)',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '菜单名称',
  `route_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '路由名称',
  `route_path` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '路由路径',
  `component` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组件路径(vue页面完整路径，省略.vue后缀)',
  `perm` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '按钮权限标识',
  `icon` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '菜单图标',
  `sort` int NULL DEFAULT 0 COMMENT '排序',
  `visible` tinyint(1) NULL DEFAULT 1 COMMENT '状态(0:禁用;1:开启)',
  `redirect` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '跳转路径',
  `tree_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `always_show` tinyint NULL DEFAULT NULL COMMENT '【目录】只有一个子路由是否始终显示(1:是 0:否)',
  `keep_alive` tinyint NULL DEFAULT NULL COMMENT '【菜单】是否开启页面缓存(1:是 0:否)',
  `params` json NULL COMMENT '【菜单】路由参数',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 139 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '菜单管理' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, 0, 2, '系统管理', NULL, '/system', 'Layout', NULL, 'system', 1, 1, '/system/user', NULL, NULL, NULL, NULL, '2021-08-28 09:12:21', '2021-08-28 09:12:21');
INSERT INTO `sys_menu` VALUES (2, 1, 1, '用户管理', 'User', 'user', 'system/user/index', NULL, 'el-icon-User', 1, 1, NULL, NULL, NULL, 1, NULL, '2021-08-28 09:12:21', '2021-08-28 09:12:21');
INSERT INTO `sys_menu` VALUES (3, 1, 1, '角色管理', 'Role', 'role', 'system/role/index', NULL, 'role', 2, 1, NULL, NULL, NULL, 1, NULL, '2021-08-28 09:12:21', '2021-08-28 09:12:21');
INSERT INTO `sys_menu` VALUES (4, 1, 1, '菜单管理', 'Menu', 'menu', 'system/menu/index', NULL, 'menu', 3, 1, NULL, NULL, NULL, 1, NULL, '2021-08-28 09:12:21', '2021-08-28 09:12:21');
INSERT INTO `sys_menu` VALUES (5, 1, 1, '部门管理', 'Dept', 'dept', 'system/dept/index', NULL, 'tree', 4, 1, NULL, NULL, NULL, 1, NULL, '2021-08-28 09:12:21', '2021-08-28 09:12:21');
INSERT INTO `sys_menu` VALUES (6, 1, 1, '字典管理', 'Dict', 'dict', 'system/dict/index', NULL, 'dict', 5, 1, NULL, '0,1', NULL, 1, NULL, '2024-10-05 23:36:02', '2024-10-05 23:36:02');
INSERT INTO `sys_menu` VALUES (9, 0, 2, '营销管理', NULL, '/sale', 'Layout', NULL, 'number', 5, 1, NULL, NULL, NULL, NULL, NULL, '2021-08-28 09:12:21', '2021-08-28 09:12:21');
INSERT INTO `sys_menu` VALUES (10, 9, 1, '广告列表', NULL, 'advert', 'sale/advert/index', NULL, 'advert', 1, 1, NULL, NULL, NULL, 1, NULL, '2021-08-28 09:12:21', '2021-08-28 09:12:21');
INSERT INTO `sys_menu` VALUES (11, 0, 1, '商品管理', NULL, '/product', 'Layout', NULL, 'el-icon-Goods', 2, 1, '/product/goods', '0', NULL, 1, NULL, '2021-08-28 09:12:21', '2024-03-03 23:54:35');
INSERT INTO `sys_menu` VALUES (12, 11, 1, '商品列表', NULL, 'spu', 'product/spu/index', NULL, 'goods-list', 1, 1, NULL, NULL, NULL, 1, NULL, '2021-08-28 09:12:21', '2021-08-28 09:12:21');
INSERT INTO `sys_menu` VALUES (13, 0, 2, '订单管理', NULL, '/order', 'Layout', NULL, 'el-icon-ShoppingCart', 3, 1, '/oms/order', '0', 1, 1, NULL, '2021-08-28 09:12:21', '2024-03-03 23:52:32');
INSERT INTO `sys_menu` VALUES (14, 13, 1, '订单列表', NULL, 'order', 'order/index', NULL, 'el-icon-Document', 1, 1, NULL, 'null,13', NULL, 1, NULL, '2021-08-28 09:12:21', '2024-03-03 23:45:09');
INSERT INTO `sys_menu` VALUES (15, 0, 1, '会员管理', NULL, '/member', 'Layout', NULL, 'user', 4, 1, '/ums/member', NULL, NULL, 1, NULL, '2021-08-28 09:12:21', '2021-08-28 09:12:21');
INSERT INTO `sys_menu` VALUES (16, 15, 1, '会员列表', NULL, 'member', 'member/index', NULL, 'peoples', 1, 1, NULL, NULL, NULL, 1, NULL, '2021-08-28 09:12:21', '2021-08-28 09:12:21');
INSERT INTO `sys_menu` VALUES (17, 11, 1, '品牌管理', NULL, 'brand', 'product/brand/index', NULL, 'brand', 5, 1, NULL, NULL, NULL, 1, NULL, '2021-08-28 09:12:21', '2021-08-28 09:12:21');
INSERT INTO `sys_menu` VALUES (18, 11, 1, '商品分类', NULL, 'category', 'product/category/index', NULL, 'menu', 3, 1, NULL, NULL, NULL, 1, NULL, '2021-08-28 09:12:21', '2021-08-28 09:12:21');
INSERT INTO `sys_menu` VALUES (20, 0, 2, '多级菜单', NULL, '/multi-level', 'Layout', NULL, 'nested', 7, 1, '/nested/level1/level2', NULL, NULL, NULL, NULL, '2022-02-16 23:11:00', '2022-02-16 23:11:00');
INSERT INTO `sys_menu` VALUES (21, 20, 1, '菜单一级', NULL, 'level1', 'demo/multi-level/level1', NULL, '', 1, 1, '/nested/level1/level2', NULL, NULL, NULL, NULL, '2022-02-16 23:13:38', '2022-02-16 23:13:38');
INSERT INTO `sys_menu` VALUES (22, 21, 1, '菜单二级', NULL, 'level2', 'demo/multi-level/children/level2', NULL, '', 1, 1, '/nested/level1/level2/level3', NULL, NULL, NULL, NULL, '2022-02-16 23:14:23', '2022-02-16 23:14:23');
INSERT INTO `sys_menu` VALUES (23, 22, 1, '菜单三级-1', NULL, 'level3-1', 'demo/multi-level/children/children/level3-1', NULL, '', 1, 1, '', NULL, NULL, NULL, NULL, '2022-02-16 23:14:51', '2022-02-16 23:14:51');
INSERT INTO `sys_menu` VALUES (24, 22, 1, '菜单三级-2', NULL, 'level3-2', 'demo/multi-level/children/children/level3-2', NULL, '', 2, 1, '', NULL, NULL, NULL, NULL, '2022-02-16 23:15:08', '2022-02-16 23:15:08');
INSERT INTO `sys_menu` VALUES (26, 0, 1, '外部链接', NULL, '/external-link', 'Layout', NULL, 'link', 9, 1, 'noredirect', NULL, NULL, NULL, NULL, '2022-02-17 22:51:20', '2022-02-17 22:51:20');
INSERT INTO `sys_menu` VALUES (30, 26, 3, '项目文档', NULL, 'https://juejin.cn/post/7228990409909108793', '', NULL, 'link', 1, 1, '', NULL, NULL, NULL, NULL, '2022-02-18 00:01:40', '2022-02-18 00:01:40');
INSERT INTO `sys_menu` VALUES (37, 9, 1, '优惠券列表', NULL, 'coupon', 'sale/coupon/index', NULL, 'menu', 2, 1, '', NULL, NULL, NULL, NULL, '2022-05-29 00:24:07', '2022-05-29 00:24:07');
INSERT INTO `sys_menu` VALUES (40, 2, 4, '新增用户', NULL, '', NULL, 'sys:user:add', '', 1, 1, '', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (41, 2, 4, '修改用户', NULL, '', NULL, 'sys:user:edit', '', 2, 1, '', NULL, NULL, NULL, NULL, '2022-11-05 01:26:44', '2022-11-05 01:26:44');
INSERT INTO `sys_menu` VALUES (42, 2, 4, '删除用户', NULL, '', NULL, 'sys:user:del', '', 3, 1, '', NULL, NULL, NULL, NULL, '2022-11-05 01:27:13', '2022-11-05 01:27:13');
INSERT INTO `sys_menu` VALUES (70, 3, 4, '角色新增', NULL, '', NULL, 'sys:role:add', '', 1, 1, NULL, '0,1,3', NULL, NULL, NULL, '2023-05-20 23:39:09', '2023-05-20 23:39:09');
INSERT INTO `sys_menu` VALUES (71, 3, 4, '角色编辑', NULL, '', NULL, 'sys:role:edit', '', 2, 1, NULL, '0,1,3', NULL, NULL, NULL, '2023-05-20 23:40:31', '2023-05-20 23:40:31');
INSERT INTO `sys_menu` VALUES (72, 3, 4, '角色删除', NULL, '', NULL, 'sys:role:delete', '', 3, 1, NULL, '0,1,3', NULL, NULL, NULL, '2023-05-20 23:41:08', '2023-05-20 23:41:08');
INSERT INTO `sys_menu` VALUES (73, 4, 4, '菜单新增', NULL, '', NULL, 'sys:menu:add', '', 1, 1, NULL, '0,1,4', NULL, NULL, NULL, '2023-05-20 23:41:35', '2023-05-20 23:41:35');
INSERT INTO `sys_menu` VALUES (74, 4, 4, '菜单编辑', NULL, '', NULL, 'sys:menu:edit', '', 3, 1, NULL, '0,1,4', NULL, NULL, NULL, '2023-05-20 23:41:58', '2023-05-20 23:41:58');
INSERT INTO `sys_menu` VALUES (75, 4, 4, '菜单删除', NULL, '', NULL, 'sys:menu:delete', '', 3, 1, NULL, '0,1,4', NULL, NULL, NULL, '2023-05-20 23:44:18', '2023-05-20 23:44:18');
INSERT INTO `sys_menu` VALUES (76, 5, 4, '部门新增', NULL, '', NULL, 'sys:dept:add', '', 1, 1, NULL, '0,1,5', NULL, NULL, NULL, '2023-05-20 23:45:00', '2023-05-20 23:45:00');
INSERT INTO `sys_menu` VALUES (77, 5, 4, '部门编辑', NULL, '', NULL, 'sys:dept:edit', '', 2, 1, NULL, '0,1,5', NULL, NULL, NULL, '2023-05-20 23:46:16', '2023-05-20 23:46:16');
INSERT INTO `sys_menu` VALUES (78, 5, 4, '部门删除', NULL, '', NULL, 'sys:dept:delete', '', 3, 1, NULL, '0,1,5', NULL, NULL, NULL, '2023-05-20 23:46:36', '2023-05-20 23:46:36');
INSERT INTO `sys_menu` VALUES (79, 6, 4, '字典新增', NULL, '', NULL, 'sys:dict:add', '', 1, 1, NULL, '0,1,6', NULL, NULL, NULL, '2024-10-05 23:36:04', '2024-10-05 23:36:04');
INSERT INTO `sys_menu` VALUES (81, 6, 4, '字典编辑', NULL, '', NULL, 'sys:dict_type:edit', '', 2, 1, NULL, '0,1,6', NULL, NULL, NULL, '2024-10-05 23:36:04', '2024-10-05 23:36:04');
INSERT INTO `sys_menu` VALUES (84, 6, 4, '字典删除', NULL, '', NULL, 'sys:dict_type:delete', '', 3, 1, NULL, '0,1,6', NULL, NULL, NULL, '2024-10-05 23:36:04', '2024-10-05 23:36:04');
INSERT INTO `sys_menu` VALUES (88, 2, 4, '重置密码', NULL, '', NULL, 'sys:user:reset_pwd', '', 4, 1, NULL, '0,1,2', NULL, NULL, NULL, '2023-05-21 00:49:18', '2023-05-21 00:49:18');
INSERT INTO `sys_menu` VALUES (89, 2, 4, '用户查询', NULL, '', NULL, 'sys:user:query', '', 0, 1, '', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (90, 17, 4, '品牌查询', NULL, '', NULL, 'pms:brand:query', '', 1, 1, '', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (91, 17, 4, '品牌新增', NULL, '', NULL, 'pms:brand:add', '', 2, 1, '', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (92, 17, 4, '品牌修改', NULL, '', NULL, 'pms:brand:edit', '', 3, 1, '', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (93, 17, 4, '品牌删除', NULL, '', NULL, 'pms:brand:delete', '', 4, 1, '', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (94, 18, 4, '分类查询', NULL, '', NULL, 'pms:category:query', '', 1, 1, '', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (95, 18, 4, '分类新增', NULL, '', NULL, 'pms:category:add', '', 2, 1, '', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (96, 18, 4, '分类修改', NULL, '', NULL, 'pms:category:edit', '', 3, 1, '', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (97, 18, 4, '分类删除', NULL, '', NULL, 'pms:category:delete', '', 4, 1, '', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (98, 0, 2, '系统工具', NULL, '/tool', 'Layout', NULL, 'menu', 2, 1, NULL, '0', 1, 1, NULL, '2024-07-13 08:41:07', '2024-07-30 16:27:05');
INSERT INTO `sys_menu` VALUES (99, 98, 1, '代码生成', 'Codegen', 'codegen', 'codegen/index', NULL, 'code', 1, 1, NULL, '0,118', 0, 1, NULL, '2024-07-13 08:44:51', '2024-07-13 08:44:51');
INSERT INTO `sys_menu` VALUES (120, 11, 1, '规格属性', 'Attribute', 'attribute', 'product/attribute/index', NULL, 'menu', 4, 0, '', '0,11', 0, 1, NULL, NULL, '2024-10-27 23:04:30');
INSERT INTO `sys_menu` VALUES (135, 1, 1, '字典数据', 'DictData', 'dict-data', 'system/dict/data', NULL, '', 6, 0, NULL, '0,1', 0, 1, NULL, '2024-10-05 23:36:08', '2024-10-05 23:36:08');
INSERT INTO `sys_menu` VALUES (136, 135, 4, '字典数据新增', NULL, '', NULL, 'sys:dict-data:add', '', 4, 1, NULL, '0,1,135', NULL, NULL, NULL, '2024-10-05 23:36:08', '2024-10-05 23:36:08');
INSERT INTO `sys_menu` VALUES (137, 135, 4, '字典数据编辑', NULL, '', NULL, 'sys:dict-data:edit', '', 5, 1, NULL, '0,1,135', NULL, NULL, NULL, '2024-10-05 23:36:09', '2024-10-05 23:36:09');
INSERT INTO `sys_menu` VALUES (138, 135, 4, '字典数据删除', NULL, '', NULL, 'sys:dict-data:delete', '', 6, 1, NULL, '0,1,135', NULL, NULL, NULL, '2024-10-05 23:36:09', '2024-10-05 23:36:09');

-- ----------------------------
-- Table structure for sys_notice
-- ----------------------------
DROP TABLE IF EXISTS `sys_notice`;
CREATE TABLE `sys_notice`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '通知标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '通知内容',
  `type` tinyint NOT NULL COMMENT '通知类型（关联字典编码：notice_type）',
  `level` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '通知等级（字典code：notice_level）',
  `target_type` tinyint NOT NULL COMMENT '目标类型（1: 全体, 2: 指定）',
  `target_user_ids` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '目标人ID集合（多个使用英文逗号,分割）',
  `publisher_id` bigint NULL DEFAULT NULL COMMENT '发布人ID',
  `publish_status` tinyint NOT NULL DEFAULT 0 COMMENT '发布状态（0: 未发布, 1: 已发布, -1: 已撤回）',
  `publish_time` datetime NULL DEFAULT NULL COMMENT '发布时间',
  `revoke_time` datetime NULL DEFAULT NULL COMMENT '撤回时间',
  `create_by` bigint NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` bigint NULL DEFAULT NULL COMMENT '更新人ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除（0: 未删除, 1: 已删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '通知公告表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_notice
-- ----------------------------
INSERT INTO `sys_notice` VALUES (1, 'v2.12.0 新增系统日志，访问趋势统计功能。', '<p>1. 消息通知</p><p>2. 字典重构</p><p>3. 代码生成</p>', 1, 'L', 1, '2', 2, 1, '2024-09-30 17:21:41', '2024-09-30 17:21:04', 2, '2024-09-28 11:21:06', 2, '2024-09-29 14:47:07', 0);
INSERT INTO `sys_notice` VALUES (2, 'v2.13.0 新增菜单搜索。', '<p>1. 消息通知</p><p>2. 字典重构</p><p>3. 代码生成</p>', 1, 'L', 1, '2', 2, 1, '2024-09-30 17:22:41', '2024-09-30 17:21:04', 2, '2024-09-28 11:21:06', 2, '2024-09-29 14:47:07', 0);
INSERT INTO `sys_notice` VALUES (3, '\r\nv2.14.0 新增个人中心。', '<p>1. 消息通知</p><p>2. 字典重构</p><p>3. 代码生成</p>', 1, 'L', 1, '2', 2, 1, '2024-09-30 17:23:41', '2024-09-30 17:21:04', 2, '2024-09-28 11:21:06', 2, '2024-09-29 14:47:07', 0);
INSERT INTO `sys_notice` VALUES (4, 'v2.15.0 登录页面改造。', '<p>1. 消息通知</p><p>2. 字典重构</p><p>3. 代码生成</p>', 1, 'L', 1, '2', 2, 1, '2024-09-30 17:24:41', '2024-09-30 17:21:04', 2, '2024-09-28 11:21:06', 2, '2024-09-29 14:47:07', 0);
INSERT INTO `sys_notice` VALUES (5, 'v2.16.0 通知公告、字典翻译组件。', '<p>1. 消息通知</p><p>2. 字典重构</p><p>3. 代码生成</p>', 1, 'L', 1, '2', 2, 1, '2024-09-30 17:25:41', '2024-09-30 17:21:04', 2, '2024-09-28 11:21:06', 2, '2024-09-29 14:47:07', 0);
INSERT INTO `sys_notice` VALUES (6, '系统将于本周六凌晨 2 点进行维护，预计维护时间为 2 小时。', '<p>1. 消息通知</p><p>2. 字典重构</p><p>3. 代码生成</p>', 2, 'H', 1, '2', 2, 1, '2024-09-30 17:26:41', '2024-09-30 17:21:04', 2, '2024-09-28 11:21:06', 2, '2024-09-29 14:47:07', 0);
INSERT INTO `sys_notice` VALUES (7, '最近发现一些钓鱼邮件，请大家提高警惕，不要点击陌生链接。', '<p>1. 消息通知</p><p>2. 字典重构</p><p>3. 代码生成</p>', 3, 'L', 1, '2', 2, 1, '2024-09-30 17:27:41', '2024-09-30 17:21:04', 2, '2024-09-28 11:21:06', 2, '2024-09-29 14:47:07', 0);
INSERT INTO `sys_notice` VALUES (8, '国庆假期从 10 月 1 日至 10 月 7 日放假，共 7 天。', '<p>1. 消息通知</p><p>2. 字典重构</p><p>3. 代码生成</p>', 4, 'L', 1, '2', 2, 1, '2024-09-30 17:28:41', '2024-09-30 17:21:04', 2, '2024-09-28 11:21:06', 2, '2024-09-29 14:47:07', 0);
INSERT INTO `sys_notice` VALUES (9, '公司将在 10 月 15 日举办新产品发布会，敬请期待。', '<p>1. 消息通知</p><p>2. 字典重构</p><p>3. 代码生成</p>', 5, 'H', 1, '2', 2, 1, '2024-09-30 17:29:41', '2024-09-30 17:21:04', 2, '2024-09-28 11:21:06', 2, '2024-09-29 14:47:07', 0);
INSERT INTO `sys_notice` VALUES (10, 'v2.16.1 版本修复了 WebSocket 重复连接导致的后台线程阻塞问题，优化了通知公告。', '<p>1. 消息通知</p><p>2. 字典重构</p><p>3. 代码生成</p>', 1, 'M', 1, '2', 2, 1, '2024-09-30 17:30:41', '2024-09-30 17:21:04', 2, '2024-09-28 11:21:06', 2, '2024-09-29 14:47:07', 0);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '角色名称',
  `code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色编码',
  `sort` int NULL DEFAULT NULL COMMENT '显示顺序',
  `status` tinyint NULL DEFAULT 1 COMMENT '角色状态(1-正常；0-停用)',
  `data_scope` tinyint NULL DEFAULT NULL COMMENT '数据权限(0-所有数据；1-部门及子部门数据；2-本部门数据；3-本人数据)',
  `create_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `is_deleted` tinyint NOT NULL DEFAULT 0 COMMENT '逻辑删除标识(0-未删除；1-已删除)',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '超级管理员', 'ROOT', 1, 1, 0, '2021-05-21 14:56:51', '2018-12-23 16:00:00', 0);
INSERT INTO `sys_role` VALUES (2, '系统管理员', 'ADMIN', 1, 1, 0, '2021-03-25 12:39:54', '2022-11-05 00:22:02', 0);
INSERT INTO `sys_role` VALUES (3, '访问游客', 'GUEST', 3, 1, 30, '2021-05-26 15:49:05', '2019-05-05 16:00:00', 0);

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `menu_id` bigint NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_id`, `menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色和菜单关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (2, 1);
INSERT INTO `sys_role_menu` VALUES (2, 2);
INSERT INTO `sys_role_menu` VALUES (2, 3);
INSERT INTO `sys_role_menu` VALUES (2, 4);
INSERT INTO `sys_role_menu` VALUES (2, 5);
INSERT INTO `sys_role_menu` VALUES (2, 6);
INSERT INTO `sys_role_menu` VALUES (2, 9);
INSERT INTO `sys_role_menu` VALUES (2, 10);
INSERT INTO `sys_role_menu` VALUES (2, 11);
INSERT INTO `sys_role_menu` VALUES (2, 12);
INSERT INTO `sys_role_menu` VALUES (2, 13);
INSERT INTO `sys_role_menu` VALUES (2, 14);
INSERT INTO `sys_role_menu` VALUES (2, 15);
INSERT INTO `sys_role_menu` VALUES (2, 16);
INSERT INTO `sys_role_menu` VALUES (2, 17);
INSERT INTO `sys_role_menu` VALUES (2, 18);
INSERT INTO `sys_role_menu` VALUES (2, 20);
INSERT INTO `sys_role_menu` VALUES (2, 21);
INSERT INTO `sys_role_menu` VALUES (2, 22);
INSERT INTO `sys_role_menu` VALUES (2, 23);
INSERT INTO `sys_role_menu` VALUES (2, 24);
INSERT INTO `sys_role_menu` VALUES (2, 26);
INSERT INTO `sys_role_menu` VALUES (2, 30);
INSERT INTO `sys_role_menu` VALUES (2, 37);
INSERT INTO `sys_role_menu` VALUES (2, 40);
INSERT INTO `sys_role_menu` VALUES (2, 41);
INSERT INTO `sys_role_menu` VALUES (2, 42);
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
INSERT INTO `sys_role_menu` VALUES (2, 88);
INSERT INTO `sys_role_menu` VALUES (2, 89);
INSERT INTO `sys_role_menu` VALUES (2, 90);
INSERT INTO `sys_role_menu` VALUES (2, 91);
INSERT INTO `sys_role_menu` VALUES (2, 92);
INSERT INTO `sys_role_menu` VALUES (2, 93);
INSERT INTO `sys_role_menu` VALUES (2, 94);
INSERT INTO `sys_role_menu` VALUES (2, 95);
INSERT INTO `sys_role_menu` VALUES (2, 96);
INSERT INTO `sys_role_menu` VALUES (2, 97);
INSERT INTO `sys_role_menu` VALUES (2, 98);
INSERT INTO `sys_role_menu` VALUES (2, 99);
INSERT INTO `sys_role_menu` VALUES (2, 135);
INSERT INTO `sys_role_menu` VALUES (2, 136);
INSERT INTO `sys_role_menu` VALUES (2, 137);
INSERT INTO `sys_role_menu` VALUES (2, 138);

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
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `update_by` bigint NULL DEFAULT NULL COMMENT '更新人ID',
  `is_deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除标识(0:未删除;1:已删除)',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `login_name`(`username` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 100 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'root', '有来技术', 0, '$2a$10$xVWsNOhHrCxh5UbpCE7/HuJ.PAOKcYAqRxD2CO2nVnJS.IAXkr5aq', NULL, 'https://s2.loli.net/2022/04/07/gw1L2Z5sPtS8GIl.gif', '17621210123', 1, 'youlaitech@163.com', '2023-04-12 01:31:29', '2023-04-12 01:31:29', 1, 1, 0);
INSERT INTO `sys_user` VALUES (2, 'admin', '系统管理员', 1, '$2a$10$8/8PxGHA.30EeWg8x4/4BuWuCUJubFbGJXyUYRs7RaJEdVvEMRbWe', 2, 'https://s2.loli.net/2022/04/07/gw1L2Z5sPtS8GIl.gif', '17621210123', 1, 'youlaitech@163.com', '2023-04-12 01:31:29', '2023-04-12 01:31:29', 1, 1, 0);
INSERT INTO `sys_user` VALUES (3, 'test', '测试小用户', 1, '$2a$10$MPJkNw.hKT/fZOgwYP8q9eu/rFJJDsNov697AmdkHNJkpjIpVSw2q', 3, 'https://s2.loli.net/2022/04/07/gw1L2Z5sPtS8GIl.gif', '17621210123', 1, 'youlaitech@163.com', '2023-04-12 01:31:29', '2023-04-12 01:31:29', 1, 1, 0);

-- ----------------------------
-- Table structure for sys_user_notice
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_notice`;
CREATE TABLE `sys_user_notice`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `notice_id` bigint NOT NULL COMMENT '公共通知id',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `is_read` bigint NOT NULL DEFAULT 0 COMMENT '读取状态（0: 未读, 1: 已读）',
  `read_time` datetime NULL DEFAULT NULL COMMENT '阅读时间',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint NOT NULL DEFAULT 0 COMMENT '逻辑删除(0: 未删除, 1: 已删除)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户通知公告表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_notice
-- ----------------------------

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
INSERT INTO `sys_user_role` VALUES (3, 3);

SET FOREIGN_KEY_CHECKS = 1;

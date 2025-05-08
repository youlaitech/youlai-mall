-- ----------------------------
-- 系统数据库
-- ----------------------------
CREATE DATABASE IF NOT EXISTS youlai_system DEFAULT CHARACTER SET utf8mb4 DEFAULT COLLATE utf8mb4_unicode_ci;

-- ----------------------------
-- 创建表 && 数据初始化
-- ----------------------------
use youlai_system;

SET NAMES utf8mb4;  # 设置字符集
SET FOREIGN_KEY_CHECKS = 0; # 关闭外键检查，加快导入速度

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
                            `id` bigint NOT NULL AUTO_INCREMENT,
                            `username` varchar(64)  DEFAULT NULL COMMENT '用户名',
                            `nickname` varchar(64)  DEFAULT NULL COMMENT '昵称',
                            `gender` tinyint(1) DEFAULT '1' COMMENT '性别((1-男 2-女 0-保密)',
                            `password` varchar(100)  DEFAULT NULL COMMENT '密码',
                            `dept_id` int DEFAULT NULL COMMENT '部门ID',
                            `avatar` varchar(255)  DEFAULT NULL COMMENT '用户头像',
                            `mobile` varchar(20)  DEFAULT NULL COMMENT '联系方式',
                            `status` tinyint(1) DEFAULT '1' COMMENT '状态(1-正常 0-禁用)',
                            `email` varchar(128)  DEFAULT NULL COMMENT '用户邮箱',
                            `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                            `create_by` bigint DEFAULT NULL COMMENT '创建人ID',
                            `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                            `update_by` bigint DEFAULT NULL COMMENT '修改人ID',
                            `is_deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除标识(0-未删除 1-已删除)',
                            `openid` char(28)  DEFAULT NULL COMMENT '微信 openid',
                            PRIMARY KEY (`id`) USING BTREE,
                            UNIQUE KEY `login_name` (`username`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户信息表';

INSERT INTO `sys_user` VALUES (1, 'root', '有来技术', 0, '$2a$10$xVWsNOhHrCxh5UbpCE7/HuJ.PAOKcYAqRxD2CO2nVnJS.IAXkr5aq', NULL, 'https://foruda.gitee.com/images/1723603502796844527/03cdca2a_716974.gif', '18812345677', 1, 'youlaitech@163.com', now(), NULL, now(), NULL, 0, NULL);
INSERT INTO `sys_user` VALUES (2, 'admin', '系统管理员', 1, '$2a$10$xVWsNOhHrCxh5UbpCE7/HuJ.PAOKcYAqRxD2CO2nVnJS.IAXkr5aq', 1, 'https://foruda.gitee.com/images/1723603502796844527/03cdca2a_716974.gif', '18812345678', 1, 'youlaitech@163.com', now(), NULL, now(), NULL, 0, NULL);
INSERT INTO `sys_user` VALUES (3, 'test', '测试小用户', 1, '$2a$10$xVWsNOhHrCxh5UbpCE7/HuJ.PAOKcYAqRxD2CO2nVnJS.IAXkr5aq', 3, 'https://foruda.gitee.com/images/1723603502796844527/03cdca2a_716974.gif', '18812345679', 1, 'youlaitech@163.com', now(), NULL, now(), NULL, 0, NULL);

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
                             `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                             `name` varchar(100) NOT NULL COMMENT '部门名称',
                             `code` varchar(100) NOT NULL COMMENT '部门编号',
                             `parent_id` bigint DEFAULT 0 COMMENT '父节点id',
                             `tree_path` varchar(255) NOT NULL COMMENT '父节点id路径',
                             `sort` smallint DEFAULT 0 COMMENT '显示顺序',
                             `status` tinyint DEFAULT 1 COMMENT '状态(1-正常 0-禁用)',
                             `create_by` bigint COMMENT '创建人ID',
                             `create_time` datetime NULL COMMENT '创建时间',
                             `update_by` bigint COMMENT '修改人ID',
                             `update_time` datetime NULL COMMENT '更新时间',
                             `is_deleted` tinyint DEFAULT 0 COMMENT '逻辑删除标识(1-已删除 0-未删除)',
                             PRIMARY KEY (`id`) USING BTREE,
                             UNIQUE INDEX `uk_code`(`code` ASC) USING BTREE COMMENT '部门编号唯一索引'
) ENGINE = InnoDB DEFAULT CHARSET=utf8mb4 COMMENT = '部门表';

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES (1, '有来技术', 'YOULAI', 0, '0', 1, 1, 1, NULL, 1, now(), 0);
INSERT INTO `sys_dept` VALUES (2, '研发部门', 'RD001', 1, '0,1', 1, 1, 2, NULL, 2, now(), 0);
INSERT INTO `sys_dept` VALUES (3, '测试部门', 'QA001', 1, '0,1', 1, 1, 2, NULL, 2, now(), 0);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
                             `id` bigint NOT NULL AUTO_INCREMENT,
                             `name` varchar(64) NOT NULL COMMENT '角色名称',
                             `code` varchar(32) NOT NULL COMMENT '角色编码',
                             `sort` int COMMENT '显示顺序',
                             `status` tinyint(1) DEFAULT 1 COMMENT '角色状态(1-正常 0-停用)',
                             `data_scope` tinyint COMMENT '数据权限(1-所有数据 2-部门及子部门数据 3-本部门数据 4-本人数据)',
                             `create_by` bigint COMMENT '创建人 ID',
                             `create_time` datetime NULL COMMENT '创建时间',
                             `update_by` bigint COMMENT '更新人ID',
                             `update_time` datetime NULL COMMENT '更新时间',
                             `is_deleted` tinyint(1) DEFAULT 0 COMMENT '逻辑删除标识(0-未删除 1-已删除)',
                             PRIMARY KEY (`id`) USING BTREE,
                             UNIQUE INDEX `uk_name`(`name` ASC) USING BTREE COMMENT '角色名称唯一索引',
                             UNIQUE INDEX `uk_code`(`code` ASC) USING BTREE COMMENT '角色编码唯一索引'
) ENGINE = InnoDB DEFAULT CHARSET=utf8mb4 COMMENT = '角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '超级管理员', 'ROOT', 1, 1, 1, NULL, now(), NULL, now(), 0);
INSERT INTO `sys_role` VALUES (2, '系统管理员', 'ADMIN', 2, 1, 1, NULL, now(), NULL, NULL, 0);
INSERT INTO `sys_role` VALUES (3, '访问游客', 'GUEST', 3, 1, 4, NULL, now(), NULL, now(), 0);


-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
                                 `user_id` bigint NOT NULL COMMENT '用户ID',
                                 `role_id` bigint NOT NULL COMMENT '角色ID',
                                 PRIMARY KEY (`user_id`,`role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户和角色关联表';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` (`user_id`, `role_id`) VALUES (1, 1);
INSERT INTO `sys_user_role` (`user_id`, `role_id`) VALUES (2, 2);
INSERT INTO `sys_user_role` (`user_id`, `role_id`) VALUES (3, 3);

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
                            `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键 ',
                            `dict_code` varchar(50) COMMENT '类型编码',
                            `name` varchar(50) COMMENT '类型名称',
                            `status` tinyint(1) DEFAULT '0' COMMENT '状态(0:正常;1:禁用)',
                            `remark` varchar(255) COMMENT '备注',
                            `create_time` datetime COMMENT '创建时间',
                            `create_by` bigint COMMENT '创建人ID',
                            `update_time` datetime COMMENT '更新时间',
                            `update_by` bigint COMMENT '修改人ID',
                            `is_deleted` tinyint DEFAULT '0' COMMENT '是否删除(1-删除，0-未删除)',
                            PRIMARY KEY (`id`) USING BTREE,
                            KEY `idx_dict_code` (`dict_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='字典表';
-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES (1, 'gender', '性别', 1, NULL, now() , 1,now(), 1,0);
INSERT INTO `sys_dict` VALUES (2, 'notice_type', '通知类型', 1, NULL, now(), 1,now(), 1,0);
INSERT INTO `sys_dict` VALUES (3, 'notice_level', '通知级别', 1, NULL, now(), 1,now(), 1,0);


-- ----------------------------
-- Table structure for sys_dict_item
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_item`;
CREATE TABLE `sys_dict_item` (
                                 `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                                 `dict_code` varchar(50) COMMENT '关联字典编码，与sys_dict表中的dict_code对应',
                                 `value` varchar(50) COMMENT '字典项值',
                                 `label` varchar(100) COMMENT '字典项标签',
                                 `tag_type` varchar(50) COMMENT '标签类型，用于前端样式展示（如success、warning等）',
                                 `status` tinyint DEFAULT '0' COMMENT '状态（1-正常，0-禁用）',
                                 `sort` int DEFAULT '0' COMMENT '排序',
                                 `remark` varchar(255) COMMENT '备注',
                                 `create_time` datetime COMMENT '创建时间',
                                 `create_by` bigint COMMENT '创建人ID',
                                 `update_time` datetime COMMENT '更新时间',
                                 `update_by` bigint COMMENT '修改人ID',
                                 PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='字典数据表';

-- ----------------------------
-- Records of sys_dict_item
-- ----------------------------
INSERT INTO `sys_dict_item` VALUES (1, 'gender', '1', '男', 'primary', 1, 1, NULL, now(), 1,now(),1);
INSERT INTO `sys_dict_item` VALUES (2, 'gender', '2', '女', 'danger', 1, 2, NULL, now(), 1,now(),1);
INSERT INTO `sys_dict_item` VALUES (3, 'gender', '0', '保密', 'info', 1, 3, NULL, now(), 1,now(),1);
INSERT INTO `sys_dict_item` VALUES (4, 'notice_type', '1', '系统升级', 'success', 1, 1, '', now(), 1,now(),1);
INSERT INTO `sys_dict_item` VALUES (5, 'notice_type', '2', '系统维护', 'primary', 1, 2, '', now(), 1,now(),1);
INSERT INTO `sys_dict_item` VALUES (6, 'notice_type', '3', '安全警告', 'danger', 1, 3, '', now(), 1,now(),1);
INSERT INTO `sys_dict_item` VALUES (7, 'notice_type', '4', '假期通知', 'success', 1, 4, '', now(), 1,now(),1);
INSERT INTO `sys_dict_item` VALUES (8, 'notice_type', '5', '公司新闻', 'primary', 1, 5, '', now(), 1,now(),1);
INSERT INTO `sys_dict_item` VALUES (9, 'notice_type', '99', '其他', 'info', 1, 99, '', now(), 1,now(),1);
INSERT INTO `sys_dict_item` VALUES (10, 'notice_level', 'L', '低', 'info', 1, 1, '', now(), 1,now(),1);
INSERT INTO `sys_dict_item` VALUES (11, 'notice_level', 'M', '中', 'warning', 1, 2, '', now(), 1,now(),1);
INSERT INTO `sys_dict_item` VALUES (12, 'notice_level', 'H', '高', 'danger', 1, 3, '', now(), 1,now(),1);

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
                             `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
                             `parent_id` bigint NOT NULL COMMENT '父菜单ID',
                             `tree_path` varchar(255) COMMENT '父节点ID路径',
                             `name` varchar(64) NOT NULL COMMENT '菜单名称',
                             `type` tinyint NOT NULL COMMENT '菜单类型（1-菜单 2-目录 3-外链 4-按钮）',
                             `route_name` varchar(255) COMMENT '路由名称（Vue Router 中用于命名路由）',
                             `route_path` varchar(128) COMMENT '路由路径（Vue Router 中定义的 URL 路径）',
                             `component` varchar(128) COMMENT '组件路径（组件页面完整路径，相对于 src/views/，缺省后缀 .vue）',
                             `perm` varchar(128) COMMENT '【按钮】权限标识',
                             `always_show` tinyint DEFAULT 0 COMMENT '【目录】只有一个子路由是否始终显示（1-是 0-否）',
                             `keep_alive` tinyint DEFAULT 0 COMMENT '【菜单】是否开启页面缓存（1-是 0-否）',
                             `visible` tinyint(1) DEFAULT 1 COMMENT '显示状态（1-显示 0-隐藏）',
                             `sort` int DEFAULT 0 COMMENT '排序',
                             `icon` varchar(64) COMMENT '菜单图标',
                             `redirect` varchar(128) COMMENT '跳转路径',
                             `create_time` datetime NULL COMMENT '创建时间',
                             `update_time` datetime NULL COMMENT '更新时间',
                             `params` varchar(255) NULL COMMENT '路由参数',
                             PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB DEFAULT CHARSET=utf8mb4 COMMENT = '菜单管理';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
-- 一级目录
INSERT INTO `sys_menu` VALUES (1, 0, '0', '系统管理', 2, NULL, '/system', 'Layout', NULL, NULL, NULL, 1, 1, 'system', '/system/user', now(), now(), NULL);
INSERT INTO `sys_menu` VALUES (2, 0, '0', '商品管理', 2, NULL, '/product', 'Layout', NULL, NULL, 1, 1, 2, 'el-icon-Goods', '/product/goods', now(), now(), NULL);
INSERT INTO `sys_menu` VALUES (3, 0, '0', '订单管理', 2, NULL, '/order', 'Layout', NULL, 1, 1, 1, 3, 'el-icon-ShoppingCart', '/oms/order', now(), now(), NULL);
INSERT INTO `sys_menu` VALUES (4, 0, '0', '会员管理', 1, NULL, '/member', 'Layout', NULL, NULL, 1, 1, 4, 'user', '/ums/member', now(), now(), NULL);
INSERT INTO `sys_menu` VALUES (5, 0, '0', '营销管理', 2, NULL, '/sale', 'Layout', NULL, NULL, NULL, 1, 5, 'number', NULL, now(), now(), NULL);
INSERT INTO `sys_menu` VALUES (6, 0, '0', '系统工具', 2, NULL, '/tool', 'Layout', NULL, 1, 1, 1, 6, 'menu', NULL, now(), now(), NULL);

-- 系统管理子菜单
INSERT INTO `sys_menu` VALUES (10, 1, '0,1', '用户管理', 1, 'User', 'user', 'system/user/index', NULL, NULL, 1, 1, 1, 'el-icon-User', NULL, now(), now(), NULL);
INSERT INTO `sys_menu` VALUES (11, 1, '0,1', '角色管理', 1, 'Role', 'role', 'system/role/index', NULL, NULL, 1, 1, 2, 'role', NULL, now(), now(), NULL);
INSERT INTO `sys_menu` VALUES (12, 1, '0,1', '菜单管理', 1, 'Menu', 'menu', 'system/menu/index', NULL, NULL, 1, 1, 3, 'menu', NULL, now(), now(), NULL);
INSERT INTO `sys_menu` VALUES (13, 1, '0,1', '部门管理', 1, 'Dept', 'dept', 'system/dept/index', NULL, NULL, 1, 1, 4, 'tree', NULL, now(), now(), NULL);
INSERT INTO `sys_menu` VALUES (14, 1, '0,1', '字典管理', 1, 'Dict', 'dict', 'system/dict/index', NULL, NULL, 1, 1, 5, 'dict', NULL, now(), now(), NULL);
INSERT INTO `sys_menu` VALUES (15, 1, '0,1', '字典数据', 1, 'DictItem', 'dict-item', 'system/dict/dict-item', NULL, 0, 1, 0, 6, '', NULL, now(), now(), NULL);

-- 商品管理子菜单
INSERT INTO `sys_menu` VALUES (20, 2, '0,2', '商品列表', 1, NULL, 'spu', 'product/spu/index', NULL, NULL, 1, 1, 1, 'goods-list', NULL, now(), now(), NULL);
INSERT INTO `sys_menu` VALUES (21, 2, '0,2', '商品分类', 1, NULL, 'category', 'product/category/index', NULL, NULL, 1, 1, 2, 'menu', NULL, now(), now(), NULL);
INSERT INTO `sys_menu` VALUES (22, 2, '0,2', '品牌管理', 1, NULL, 'brand', 'product/brand/index', NULL, NULL, 1, 1, 3, 'brand', NULL, now(), now(), NULL);
INSERT INTO `sys_menu` VALUES (23, 2, '0,2', '规格属性', 1, 'Attribute', 'attribute', 'product/attribute/index', NULL, 0, 1, 0, 4, 'menu', '', now(), now(), NULL);

-- 订单管理子菜单
INSERT INTO `sys_menu` VALUES (30, 3, '0,3', '订单列表', 1, NULL, 'order', 'order/index', NULL, NULL, 1, 1, 1, 'el-icon-Document', NULL, now(), now(), NULL);

-- 会员管理子菜单
INSERT INTO `sys_menu` VALUES (40, 4, '0,4', '会员列表', 1, NULL, 'member', 'member/index', NULL, NULL, 1, 1, 1, 'peoples', NULL, now(), now(), NULL);

-- 营销管理子菜单
INSERT INTO `sys_menu` VALUES (50, 5, '0,5', '广告列表', 1, NULL, 'advert', 'sale/advert/index', NULL, NULL, 1, 1, 1, 'advert', NULL, now(), now(), NULL);
INSERT INTO `sys_menu` VALUES (51, 5, '0,5', '优惠券列表', 1, NULL, 'coupon', 'sale/coupon/index', NULL, NULL, NULL, 1, 2, 'menu', '', now(), now(), NULL);

-- 系统工具子菜单
INSERT INTO `sys_menu` VALUES (60, 6, '0,6', '代码生成', 1, 'Codegen', 'codegen', 'codegen/index', NULL, 0, 1, 1, 1, 'code', NULL, now(), now(), NULL);

-- 用户管理按钮
INSERT INTO `sys_menu` VALUES (100, 10, '0,1,10', '用户查询', 4, NULL, '', NULL, 'sys:user:query', NULL, NULL, 1, 1, '', '', now(), now(), NULL);
INSERT INTO `sys_menu` VALUES (101, 10, '0,1,10', '新增用户', 4, NULL, '', NULL, 'sys:user:add', NULL, NULL, 1, 2, '', '', now(), now(), NULL);
INSERT INTO `sys_menu` VALUES (102, 10, '0,1,10', '修改用户', 4, NULL, '', NULL, 'sys:user:edit', NULL, NULL, 1, 3, '', '', now(), now(), NULL);
INSERT INTO `sys_menu` VALUES (103, 10, '0,1,10', '删除用户', 4, NULL, '', NULL, 'sys:user:del', NULL, NULL, 1, 4, '', '', now(), now(), NULL);
INSERT INTO `sys_menu` VALUES (104, 10, '0,1,10', '重置密码', 4, NULL, '', NULL, 'sys:user:reset_pwd', NULL, NULL, 1, 5, '', NULL, now(), now(), NULL);

-- 角色管理按钮
INSERT INTO `sys_menu` VALUES (110, 11, '0,1,11', '角色新增', 4, NULL, '', NULL, 'sys:role:add', NULL, NULL, 1, 1, '', NULL, now(), now(), NULL);
INSERT INTO `sys_menu` VALUES (111, 11, '0,1,11', '角色编辑', 4, NULL, '', NULL, 'sys:role:edit', NULL, NULL, 1, 2, '', NULL, now(), now(), NULL);
INSERT INTO `sys_menu` VALUES (112, 11, '0,1,11', '角色删除', 4, NULL, '', NULL, 'sys:role:delete', NULL, NULL, 1, 3, '', NULL, now(), now(), NULL);

-- 菜单管理按钮
INSERT INTO `sys_menu` VALUES (120, 12, '0,1,12', '菜单新增', 4, NULL, '', NULL, 'sys:menu:add', NULL, NULL, 1, 1, '', NULL, now(), now(), NULL);
INSERT INTO `sys_menu` VALUES (121, 12, '0,1,12', '菜单编辑', 4, NULL, '', NULL, 'sys:menu:edit', NULL, NULL, 1, 2, '', NULL, now(), now(), NULL);
INSERT INTO `sys_menu` VALUES (122, 12, '0,1,12', '菜单删除', 4, NULL, '', NULL, 'sys:menu:delete', NULL, NULL, 1, 3, '', NULL, now(), now(), NULL);

-- 部门管理按钮
INSERT INTO `sys_menu` VALUES (130, 13, '0,1,13', '部门新增', 4, NULL, '', NULL, 'sys:dept:add', NULL, NULL, 1, 1, '', NULL, now(), now(), NULL);
INSERT INTO `sys_menu` VALUES (131, 13, '0,1,13', '部门编辑', 4, NULL, '', NULL, 'sys:dept:edit', NULL, NULL, 1, 2, '', NULL, now(), now(), NULL);
INSERT INTO `sys_menu` VALUES (132, 13, '0,1,13', '部门删除', 4, NULL, '', NULL, 'sys:dept:delete', NULL, NULL, 1, 3, '', NULL, now(), now(), NULL);

-- 字典管理按钮
INSERT INTO `sys_menu` VALUES (140, 14, '0,1,14', '字典新增', 4, NULL, '', NULL, 'sys:dict:add', NULL, NULL, 1, 1, '', NULL, now(), now(), NULL);
INSERT INTO `sys_menu` VALUES (141, 14, '0,1,14', '字典编辑', 4, NULL, '', NULL, 'sys:dict_type:edit', NULL, NULL, 1, 2, '', NULL, now(), now(), NULL);
INSERT INTO `sys_menu` VALUES (142, 14, '0,1,14', '字典删除', 4, NULL, '', NULL, 'sys:dict_type:delete', NULL, NULL, 1, 3, '', NULL, now(), now(), NULL);

-- 字典数据按钮
INSERT INTO `sys_menu` VALUES (150, 15, '0,1,15', '字典数据新增', 4, NULL, '', NULL, 'sys:dict-item:add', NULL, NULL, 1, 1, '', NULL, now(), now(), NULL);
INSERT INTO `sys_menu` VALUES (151, 15, '0,1,15', '字典数据编辑', 4, NULL, '', NULL, 'sys:dict-item:edit', NULL, NULL, 1, 2, '', NULL, now(), now(), NULL);
INSERT INTO `sys_menu` VALUES (152, 15, '0,1,15', '字典数据删除', 4, NULL, '', NULL, 'sys:dict-item:delete', NULL, NULL, 1, 3, '', NULL, now(), now(), NULL);

-- 商品管理按钮
INSERT INTO `sys_menu` VALUES (200, 20, '0,2,20', '商品查询', 4, NULL, NULL, NULL, 'pms:spu:query', 0, 1, 1, 1, NULL, NULL, now(), now(), NULL);
INSERT INTO `sys_menu` VALUES (201, 20, '0,2,20', '新增商品', 4, NULL, NULL, NULL, 'pms:spu:add', 0, 1, 1, 2, NULL, NULL, now(), now(), NULL);
INSERT INTO `sys_menu` VALUES (202, 20, '0,2,20', '编辑商品', 4, NULL, NULL, NULL, 'pms:spu:edit', 0, 1, 1, 3, NULL, NULL, now(), now(), NULL);
INSERT INTO `sys_menu` VALUES (203, 20, '0,2,20', '删除商品', 4, NULL, NULL, NULL, 'pms:spu:delete', 0, 1, 1, 4, NULL, NULL, now(), now(), NULL);

-- 分类管理按钮
INSERT INTO `sys_menu` VALUES (210, 21, '0,2,21', '分类查询', 4, NULL, '', NULL, 'pms:category:query', NULL, NULL, 1, 1, '', '', now(), now(), NULL);
INSERT INTO `sys_menu` VALUES (211, 21, '0,2,21', '分类新增', 4, NULL, '', NULL, 'pms:category:add', NULL, NULL, 1, 2, '', '', now(), now(), NULL);
INSERT INTO `sys_menu` VALUES (212, 21, '0,2,21', '分类修改', 4, NULL, '', NULL, 'pms:category:edit', NULL, NULL, 1, 3, '', '', now(), now(), NULL);
INSERT INTO `sys_menu` VALUES (213, 21, '0,2,21', '分类删除', 4, NULL, '', NULL, 'pms:category:delete', NULL, NULL, 1, 4, '', '', now(), now(), NULL);

-- 品牌管理按钮
INSERT INTO `sys_menu` VALUES (220, 22, '0,2,22', '品牌查询', 4, NULL, '', NULL, 'pms:brand:query', NULL, NULL, 1, 1, '', '', now(), now(), NULL);
INSERT INTO `sys_menu` VALUES (221, 22, '0,2,22', '品牌新增', 4, NULL, '', NULL, 'pms:brand:add', NULL, NULL, 1, 2, '', '', now(), now(), NULL);
INSERT INTO `sys_menu` VALUES (222, 22, '0,2,22', '品牌修改', 4, NULL, '', NULL, 'pms:brand:edit', NULL, NULL, 1, 3, '', '', now(), now(), NULL);
INSERT INTO `sys_menu` VALUES (223, 22, '0,2,22', '品牌删除', 4, NULL, '', NULL, 'pms:brand:delete', NULL, NULL, 1, 4, '', '', now(), now(), NULL);

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
                                  `role_id` bigint NOT NULL COMMENT '角色ID',
                                  `menu_id` bigint NOT NULL COMMENT '菜单ID',
                                  UNIQUE INDEX `uk_roleid_menuid`(`role_id` ASC, `menu_id` ASC) USING BTREE COMMENT '角色菜单唯一索引'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COMMENT = '角色和菜单关联表';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (2, 1);
INSERT INTO `sys_role_menu` VALUES (2, 2);
INSERT INTO `sys_role_menu` VALUES (2, 3);
INSERT INTO `sys_role_menu` VALUES (2, 4);
INSERT INTO `sys_role_menu` VALUES (2, 5);
INSERT INTO `sys_role_menu` VALUES (2, 6);
INSERT INTO `sys_role_menu` VALUES (2, 10);
INSERT INTO `sys_role_menu` VALUES (2, 11);
INSERT INTO `sys_role_menu` VALUES (2, 12);
INSERT INTO `sys_role_menu` VALUES (2, 13);
INSERT INTO `sys_role_menu` VALUES (2, 14);
INSERT INTO `sys_role_menu` VALUES (2, 15);
INSERT INTO `sys_role_menu` VALUES (2, 20);
INSERT INTO `sys_role_menu` VALUES (2, 21);
INSERT INTO `sys_role_menu` VALUES (2, 22);
INSERT INTO `sys_role_menu` VALUES (2, 23);
INSERT INTO `sys_role_menu` VALUES (2, 30);
INSERT INTO `sys_role_menu` VALUES (2, 40);
INSERT INTO `sys_role_menu` VALUES (2, 50);
INSERT INTO `sys_role_menu` VALUES (2, 51);
INSERT INTO `sys_role_menu` VALUES (2, 60);
INSERT INTO `sys_role_menu` VALUES (2, 100);
INSERT INTO `sys_role_menu` VALUES (2, 101);
INSERT INTO `sys_role_menu` VALUES (2, 102);
INSERT INTO `sys_role_menu` VALUES (2, 103);
INSERT INTO `sys_role_menu` VALUES (2, 104);
INSERT INTO `sys_role_menu` VALUES (2, 110);
INSERT INTO `sys_role_menu` VALUES (2, 111);
INSERT INTO `sys_role_menu` VALUES (2, 112);
INSERT INTO `sys_role_menu` VALUES (2, 120);
INSERT INTO `sys_role_menu` VALUES (2, 121);
INSERT INTO `sys_role_menu` VALUES (2, 122);
INSERT INTO `sys_role_menu` VALUES (2, 130);
INSERT INTO `sys_role_menu` VALUES (2, 131);
INSERT INTO `sys_role_menu` VALUES (2, 132);
INSERT INTO `sys_role_menu` VALUES (2, 140);
INSERT INTO `sys_role_menu` VALUES (2, 141);
INSERT INTO `sys_role_menu` VALUES (2, 142);
INSERT INTO `sys_role_menu` VALUES (2, 150);
INSERT INTO `sys_role_menu` VALUES (2, 151);
INSERT INTO `sys_role_menu` VALUES (2, 152);
INSERT INTO `sys_role_menu` VALUES (2, 200);
INSERT INTO `sys_role_menu` VALUES (2, 201);
INSERT INTO `sys_role_menu` VALUES (2, 202);
INSERT INTO `sys_role_menu` VALUES (2, 203);
INSERT INTO `sys_role_menu` VALUES (2, 210);
INSERT INTO `sys_role_menu` VALUES (2, 211);
INSERT INTO `sys_role_menu` VALUES (2, 212);
INSERT INTO `sys_role_menu` VALUES (2, 213);
INSERT INTO `sys_role_menu` VALUES (2, 220);
INSERT INTO `sys_role_menu` VALUES (2, 221);
INSERT INTO `sys_role_menu` VALUES (2, 222);
INSERT INTO `sys_role_menu` VALUES (2, 223);


-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config` (
                             `id` bigint NOT NULL AUTO_INCREMENT,
                              `config_name` varchar(50)NOT NULL COMMENT '配置名称',
                              `config_key` varchar(50)NOT NULL COMMENT '配置key',
                              `config_value` varchar(100)NOT NULL COMMENT '配置值',
                              `remark` varchar(255)DEFAULT NULL COMMENT '备注',
                              `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                              `create_by` bigint DEFAULT NULL COMMENT '创建人ID',
                              `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                              `update_by` bigint DEFAULT NULL COMMENT '更新人ID',
                              `is_deleted` tinyint NOT NULL DEFAULT '0' COMMENT '逻辑删除标识(0-未删除 1-已删除)',
                              PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统配置表';
-- ----------------------------
-- Records of sys_config
-- ----------------------------
INSERT INTO `sys_config` VALUES (1, '系统限流QPS', 'IP_QPS_THRESHOLD_LIMIT', '10', '单个IP请求的最大每秒查询数（QPS）阈值Key', '2025-02-26 15:25:07', 1, NULL, NULL, 0);

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
                           `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                           `module` varchar(50)  NOT NULL COMMENT '日志模块',
                           `request_method` varchar(64)  NOT NULL COMMENT '请求方式',
                           `request_params` text  COMMENT '请求参数(批量请求参数可能会超过text)',
                           `response_content` mediumtext  COMMENT '返回参数',
                           `content` varchar(255)  NOT NULL COMMENT '日志内容',
                           `request_uri` varchar(255)  DEFAULT NULL COMMENT '请求路径',
                           `method` varchar(255)  DEFAULT NULL COMMENT '方法名',
                           `ip` varchar(45)  DEFAULT NULL COMMENT 'IP地址',
                           `province` varchar(100)  DEFAULT NULL COMMENT '省份',
                           `city` varchar(100)  DEFAULT NULL COMMENT '城市',
                           `execution_time` bigint DEFAULT NULL COMMENT '执行时间(ms)',
                           `browser` varchar(100)  DEFAULT NULL COMMENT '浏览器',
                           `browser_version` varchar(100)  DEFAULT NULL COMMENT '浏览器版本',
                           `os` varchar(100)  DEFAULT NULL COMMENT '终端系统',
                           `create_by` bigint DEFAULT NULL COMMENT '创建人ID',
                           `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                           `is_deleted` tinyint DEFAULT '0' COMMENT '逻辑删除标识(1-已删除 0-未删除)',
                           PRIMARY KEY (`id`) USING BTREE,
                           KEY `idx_create_time` (`create_time`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COMMENT='系统日志表';

-- ----------------------------
-- Table structure for sys_notice
-- ----------------------------
DROP TABLE IF EXISTS `sys_notice`;
CREATE TABLE `sys_notice` (
                              `id` bigint NOT NULL AUTO_INCREMENT,
                              `title` varchar(50)  DEFAULT NULL COMMENT '通知标题',
                              `content` text  COMMENT '通知内容',
                              `type` tinyint NOT NULL COMMENT '通知类型（关联字典编码：notice_type）',
                              `level` varchar(5)  NOT NULL COMMENT '通知等级（字典code：notice_level）',
                              `target_type` tinyint NOT NULL COMMENT '目标类型（1: 全体, 2: 指定）',
                              `target_user_ids` varchar(255)  DEFAULT NULL COMMENT '目标人ID集合（多个使用英文逗号,分割）',
                              `publisher_id` bigint DEFAULT NULL COMMENT '发布人ID',
                              `publish_status` tinyint DEFAULT 0 COMMENT '发布状态（0: 未发布, 1: 已发布, -1: 已撤回）',
                              `publish_time` datetime DEFAULT NULL COMMENT '发布时间',
                              `revoke_time` datetime DEFAULT NULL COMMENT '撤回时间',
                              `create_by` bigint NOT NULL COMMENT '创建人ID',
                              `create_time` datetime NOT NULL COMMENT '创建时间',
                              `update_by` bigint DEFAULT NULL COMMENT '更新人ID',
                              `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                              `is_deleted` tinyint(1) DEFAULT '0' COMMENT '是否删除（0: 未删除, 1: 已删除）',
                              PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='通知公告表';

-- ----------------------------
-- Table structure for sys_user_notice
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_notice`;
CREATE TABLE `sys_user_notice` (
                                   `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
                                   `notice_id` bigint NOT NULL COMMENT '公共通知id',
                                   `user_id` bigint NOT NULL COMMENT '用户id',
                                   `is_read` bigint DEFAULT '0' COMMENT '读取状态（0: 未读, 1: 已读）',
                                   `read_time` datetime DEFAULT NULL COMMENT '阅读时间',
                                   `create_time` datetime NOT NULL COMMENT '创建时间',
                                   `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                                   `is_deleted` tinyint DEFAULT '0' COMMENT '逻辑删除(0: 未删除, 1: 已删除)',
                                   PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户通知公告表';
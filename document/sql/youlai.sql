/*
 Navicat Premium Data Transfer

 Source Server         : a.youlai.store
 Source Server Type    : MySQL
 Source Server Version : 80023
 Source Host           : a.youlai.store:3306
 Source Schema         : youlai

 Target Server Type    : MySQL
 Target Server Version : 80023
 File Encoding         : 65001

 Date: 01/04/2021 00:48:23
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
INSERT INTO `oauth_client_details` VALUES ('youlai-admin', '', '123456', 'all', 'password,client_credentials,refresh_token,authorization_code', '', NULL, 3600, 7200, NULL, 'true');
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
INSERT INTO `sys_dept` VALUES (2, '测试部', 0, '0', 2, '李四', NULL, NULL, 1, 0, '2021-01-26 13:53:46', '2021-03-22 15:38:03');
INSERT INTO `sys_dept` VALUES (7, '运维部', 1, '0,1', 1, NULL, NULL, NULL, 1, 0, '2021-02-18 18:02:38', '2021-02-18 18:02:38');
INSERT INTO `sys_dept` VALUES (8, '工控部', 1, '0,1', 1, NULL, NULL, NULL, 1, 0, '2021-02-18 18:03:05', '2021-02-18 18:03:05');
INSERT INTO `sys_dept` VALUES (9, '工控部', 1, '0,1', 1, NULL, NULL, NULL, 1, 0, '2021-02-18 18:03:05', '2021-02-18 18:03:05');
INSERT INTO `sys_dept` VALUES (10, '4545', 1, '0,1', 1, '5433', '13599999999', '354@123.com', 1, 0, '2021-02-20 15:45:51', '2021-02-20 15:45:51');
INSERT INTO `sys_dept` VALUES (11, 'nibilty', 10, '0,1,10', 1, 'asdfasdf', '14400000000', 'asdfadf@qq.com', 1, 0, '2021-02-20 15:46:29', '2021-02-28 15:33:30');
INSERT INTO `sys_dept` VALUES (12, '问问哦', 0, '0', 5, NULL, NULL, NULL, 1, 0, '2021-03-06 20:19:02', '2021-03-06 20:19:17');
INSERT INTO `sys_dept` VALUES (13, 'dsadsa', 8, '0,1,8', 1, 'dsadsa', '13546554451', 'adsadsa@qq.com', 1, 0, '2021-03-08 13:31:43', '2021-03-08 13:31:43');
INSERT INTO `sys_dept` VALUES (14, '工控1', 8, '0,1,8', 1, '1123', '15296495065', '30312312312@11.com', 1, 0, '2021-03-09 15:26:35', '2021-03-09 15:26:35');
INSERT INTO `sys_dept` VALUES (15, 'd', 14, '0,1,8,14', 1, NULL, NULL, NULL, 1, 0, '2021-03-09 19:23:18', '2021-03-09 19:23:18');
INSERT INTO `sys_dept` VALUES (16, 'a', 15, '0,1,8,14,15', 1, NULL, NULL, NULL, 1, 0, '2021-03-09 19:23:22', '2021-03-09 19:23:22');
INSERT INTO `sys_dept` VALUES (17, 'a', 16, '0,1,8,14,15,16', 1, NULL, NULL, NULL, 1, 0, '2021-03-09 19:23:25', '2021-03-09 19:23:25');
INSERT INTO `sys_dept` VALUES (18, 'a', 17, '0,1,8,14,15,16,17', 1, NULL, NULL, NULL, 1, 0, '2021-03-09 19:23:28', '2021-03-09 19:23:28');
INSERT INTO `sys_dept` VALUES (19, '测试部门', 1, '0,1', 1, NULL, NULL, NULL, 0, 0, '2021-03-12 16:55:40', '2021-03-12 16:56:11');
INSERT INTO `sys_dept` VALUES (20, '啊啊', 2, '0,2', 1, NULL, NULL, NULL, 1, 0, '2021-03-17 15:20:23', '2021-03-26 22:09:55');
INSERT INTO `sys_dept` VALUES (21, '研发部', 0, '0', 1, '李总', NULL, NULL, 1, 0, '2021-03-23 10:35:37', '2021-03-23 10:35:37');
INSERT INTO `sys_dept` VALUES (22, '爬虫组', 21, '0,21', 1, '叉总', NULL, NULL, 1, 0, '2021-03-23 10:36:16', '2021-03-23 10:36:16');
INSERT INTO `sys_dept` VALUES (23, '前端组', 21, '0,21', 1, '噢总', NULL, NULL, 1, 0, '2021-03-23 10:36:36', '2021-03-23 11:09:58');
INSERT INTO `sys_dept` VALUES (24, 'aaa', 22, '0,21,22', 1, NULL, NULL, NULL, 1, 0, '2021-03-30 14:51:19', '2021-03-30 14:51:19');

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
INSERT INTO `sys_dict_item` VALUES (29, '参数1', 'canshuone', '', 1, 1, 0, '', '2021-02-20 14:00:32', '2021-02-20 14:00:32');
INSERT INTO `sys_dict_item` VALUES (32, 'camera', '123', 'gender', 0, 0, 0, '', '2021-03-29 13:55:09', '2021-03-29 13:55:09');
INSERT INTO `sys_dict_item` VALUES (33, '摄像头', '1', 'gender', 0, 0, 0, '', '2021-03-29 13:55:25', '2021-03-29 13:55:25');
INSERT INTO `sys_dict_item` VALUES (34, '摄像头', 'camera', 'gender', 0, 0, 0, '', '2021-03-29 13:56:06', '2021-03-29 13:56:06');
INSERT INTO `sys_dict_item` VALUES (35, '摄像头', 'camera', 'device', 0, 0, 0, '', '2021-03-29 13:58:46', '2021-03-29 13:58:46');

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
) ENGINE = InnoDB AUTO_INCREMENT = 65 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '菜单管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, '系统管理', 0, '/admin', 'Layout', '', 'table', 1, 1, '2020-09-23 09:12:21', '2021-03-23 18:33:43');
INSERT INTO `sys_menu` VALUES (2, '用户管理', 1, 'user', 'admin/user/index', '', 'user', 3, 1, NULL, '2021-03-25 15:05:31');
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
INSERT INTO `sys_menu` VALUES (15, '会员管理', 0, '/ums', 'Layout', NULL, 'user', 4, 1, '2020-10-31 10:51:07', '2021-02-06 14:57:13');
INSERT INTO `sys_menu` VALUES (16, '会员列表', 15, 'user', 'ums/user/index', NULL, 'peoples', 1, 1, '2020-10-31 10:51:43', '2021-03-02 10:41:56');
INSERT INTO `sys_menu` VALUES (17, '品牌管理', 11, 'brand', 'pms/brand/index', NULL, 'component', 4, 1, '2020-09-23 09:12:21', '2021-02-01 19:25:06');
INSERT INTO `sys_menu` VALUES (18, '商品分类', 11, 'category', 'pms/category/index', NULL, 'component', 3, 1, '2020-09-23 09:12:21', '2021-03-17 11:17:06');
INSERT INTO `sys_menu` VALUES (22, '商品上架', 11, 'product_add', 'pms/product/detail', '', 'component', 2, 1, NULL, '2021-02-19 18:43:23');
INSERT INTO `sys_menu` VALUES (23, '角色管理', 1, 'role', 'admin/role/index', '', 'peoples', 3, 1, NULL, '2021-03-29 10:44:13');
INSERT INTO `sys_menu` VALUES (25, '实验室', 0, '/laboratory', 'Layout', '', 'build', 9, 1, NULL, '2021-02-06 14:57:42');
INSERT INTO `sys_menu` VALUES (26, 'Seata分布式事务', 25, 'seata', 'laboratory/seata', '', 'component', 1, 1, NULL, '2021-02-09 11:20:50');
INSERT INTO `sys_menu` VALUES (34, '特殊权限', 0, '/common', 'Layout', '', 'checkbox', 9999, 1, '2021-02-27 23:34:16', '2021-03-07 14:10:18');
INSERT INTO `sys_menu` VALUES (41, '登录记录', 1, 'login_record', 'admin/record/login/index', '', 'list', 7, 1, '2021-03-09 09:40:37', '2021-03-09 14:30:32');
INSERT INTO `sys_menu` VALUES (61, '一级菜单', 0, '/nested', 'Layout', '/nested/menu1/menu11', 'cascader', 1, 1, '2021-03-25 20:07:35', '2021-03-25 20:07:35');
INSERT INTO `sys_menu` VALUES (62, '二级菜单', 61, 'menu1', 'nested/menu1/index', '/nested/menu1/menu11', 'cascader', 1, 1, '2021-03-25 20:08:41', '2021-03-25 20:08:41');
INSERT INTO `sys_menu` VALUES (63, '三级菜单', 62, 'menu11', 'nested/menu1/menu11/index', '/nested/menu1/menu11', 'cascader', 1, 1, '2021-03-25 20:09:33', '2021-03-26 14:10:25');
INSERT INTO `sys_menu` VALUES (65, '23', 0, '23234', 'Layout', '', '', 1, 1, '2021-03-31 14:55:41', '2021-03-31 14:55:41');

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
INSERT INTO `sys_permission` VALUES (18, '查询用户', '/youlai-admin/api.admin/v1/users/**', 'GET', 1, 2, '2021-02-02 14:16:07', '2021-02-10 10:19:28');
INSERT INTO `sys_permission` VALUES (19, '新增', 'system:user:add', NULL, 2, 2, '2021-02-02 14:16:46', '2021-02-22 17:03:21');
INSERT INTO `sys_permission` VALUES (21, '修改', 'system:user:edit', NULL, 2, 2, '2021-02-02 14:19:45', '2021-02-03 00:20:32');
INSERT INTO `sys_permission` VALUES (22, '删除', 'system:user:delete', NULL, 2, 2, '2021-02-02 14:20:12', '2021-02-28 17:22:52');
INSERT INTO `sys_permission` VALUES (23, '查询', 'system:user:query', NULL, 2, 2, '2021-02-02 14:20:42', '2021-02-03 00:20:21');
INSERT INTO `sys_permission` VALUES (26, '重置密码', 'system:user:reset_password', NULL, 2, 2, '2021-02-05 14:31:53', '2021-02-05 14:33:27');
INSERT INTO `sys_permission` VALUES (30, '新增用户', '/youlai-admin/api.admin/v1/users/**', 'POST', 1, 2, '2021-02-10 10:20:08', '2021-02-10 10:20:08');
INSERT INTO `sys_permission` VALUES (31, '修改用户', '/youlai-admin/api.admin/v1/users/**', 'PUT', 1, 2, '2021-02-10 10:20:32', '2021-02-10 10:20:32');
INSERT INTO `sys_permission` VALUES (32, '删除用户', '/youlai-admin/api.admin/v1/users/**', 'DELETE', 1, 2, '2021-02-10 10:20:47', '2021-02-10 10:20:47');
INSERT INTO `sys_permission` VALUES (34, '菜单所有', '/youlai-admin/api.admin/v1/menus/**', '*', 1, 4, '2021-02-10 11:00:26', '2021-02-10 11:00:26');
INSERT INTO `sys_permission` VALUES (35, '部门所有', '/youlai-admin/api.admin/v1/depts/**', '*', 1, 6, '2021-02-10 11:02:45', '2021-02-10 11:02:45');
INSERT INTO `sys_permission` VALUES (36, '角色所有', '/youlai-admin/api.admin/v1/roles/**', '*', 1, 23, '2021-02-10 11:03:05', '2021-03-10 17:36:53');
INSERT INTO `sys_permission` VALUES (37, '字典所有', '/youlai-admin/api.admin/v1/dicts/**', '*', 1, 5, '2021-02-10 11:03:49', '2021-02-10 11:03:49');
INSERT INTO `sys_permission` VALUES (38, '客户端所有', '/youlai-admin/api.admin/v1/clients/**', '*', 1, 8, '2021-02-10 11:05:34', '2021-02-10 11:05:34');
INSERT INTO `sys_permission` VALUES (39, '权限所有', '/youlai-admin/api.admin/v1/permissions/**', '*', 1, 4, NULL, NULL);
INSERT INTO `sys_permission` VALUES (42, '字典项所有', '/youlai-admin/api.admin/v1/dict_items/**', '*', 1, 5, '2021-02-14 10:48:17', '2021-03-21 16:11:38');
INSERT INTO `sys_permission` VALUES (44, '新增', 'system:dict:add', NULL, 2, 5, '2021-02-23 13:24:31', '2021-02-23 13:24:31');
INSERT INTO `sys_permission` VALUES (45, '文件上传', '/youlai-admin/api.admin/v1/files', '*', 1, 34, '2021-02-27 23:35:27', '2021-02-27 23:35:27');
INSERT INTO `sys_permission` VALUES (46, '90', 'rty', NULL, 2, 4, '2021-03-01 10:38:09', '2021-03-01 10:38:09');
INSERT INTO `sys_permission` VALUES (47, '添加', 'add', NULL, 2, 1, '2021-03-03 15:44:33', '2021-03-03 15:44:33');
INSERT INTO `sys_permission` VALUES (48, '添加', '/menu/add', 'GET', 1, 1, '2021-03-03 15:45:02', '2021-03-12 14:38:11');
INSERT INTO `sys_permission` VALUES (49, '首页控制台', '/youlai-admin/api.admin/v1/dashboard/**', '*', 1, 34, '2021-03-08 14:32:34', '2021-03-08 14:32:34');
INSERT INTO `sys_permission` VALUES (50, '删除1', 'delete', NULL, 2, 1, '2021-03-08 15:59:38', '2021-03-24 10:14:07');
INSERT INTO `sys_permission` VALUES (51, '编辑', 'system:user:edit', NULL, 2, 5, '2021-03-09 09:40:06', '2021-03-09 09:40:50');
INSERT INTO `sys_permission` VALUES (53, '扩展', 'yingxiao:extends', NULL, 2, 9, '2021-03-09 14:48:08', '2021-03-09 14:48:08');
INSERT INTO `sys_permission` VALUES (54, '令牌操作', '/youlai-admin/api.admin/v1/tokens/**', '*', 1, 34, '2021-03-09 15:34:51', '2021-03-09 15:34:51');
INSERT INTO `sys_permission` VALUES (55, '所有权限', '/youlai-admin/api.admin/v1/login_records/**', '*', 1, 41, '2021-03-10 00:00:14', '2021-03-10 12:23:11');
INSERT INTO `sys_permission` VALUES (56, '重置密码', '/youlai-admin/api.admin/v1/users/**', '*', 1, 2, '2021-03-12 14:52:05', '2021-03-12 14:52:05');
INSERT INTO `sys_permission` VALUES (57, '1212331', 'qweqwe', NULL, 2, 50, '2021-03-16 15:09:22', '2021-03-16 15:09:22');
INSERT INTO `sys_permission` VALUES (58, '2344', '324234234', 'GET', 1, 50, '2021-03-16 15:09:49', '2021-03-16 15:09:49');
INSERT INTO `sys_permission` VALUES (59, '2121', '/sdfs', 'PUT', 1, 51, '2021-03-16 16:22:00', '2021-03-16 16:22:00');
INSERT INTO `sys_permission` VALUES (60, '审核', '/system/user/audit', 'POST', 1, 2, '2021-03-17 11:20:24', '2021-03-17 11:20:24');
INSERT INTO `sys_permission` VALUES (61, '审核', 'system:user:audit', NULL, 2, 2, '2021-03-17 11:21:19', '2021-03-17 11:21:19');
INSERT INTO `sys_permission` VALUES (62, '111', '/system', '*', 1, 52, '2021-03-18 08:26:49', '2021-03-18 08:26:49');
INSERT INTO `sys_permission` VALUES (63, '1232', 'system:user:add', NULL, 2, 52, '2021-03-18 08:27:47', '2021-03-18 08:27:47');
INSERT INTO `sys_permission` VALUES (64, '请求', '/system/baidu/com/test', 'GET', 1, 59, '2021-03-25 14:29:18', '2021-03-25 14:29:18');
INSERT INTO `sys_permission` VALUES (65, '角色按钮', 'system:user:add', NULL, 2, 59, '2021-03-25 14:29:57', '2021-03-25 14:29:57');
INSERT INTO `sys_permission` VALUES (66, 'quanxianmingcheng ', 'quanxianbiaoshi ', NULL, 2, 1, '2021-03-26 10:54:36', '2021-03-26 10:54:36');
INSERT INTO `sys_permission` VALUES (67, 'test', '/sad/sdf/add', '*', 1, 23, '2021-03-30 09:10:26', '2021-03-30 09:25:11');
INSERT INTO `sys_permission` VALUES (68, 'test', '/sad/sdf/add', '*', 1, 2, '2021-03-30 09:11:02', '2021-03-30 09:11:02');

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
) ENGINE = InnoDB AUTO_INCREMENT = 53 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '超级管理员', 1, 1, 0, '2018-12-23 16:00:00', '2020-09-11 17:04:23');
INSERT INTO `sys_role` VALUES (2, '系统管理员', 2, 1, 0, '2018-12-23 16:00:00', '2021-03-25 12:39:54');
INSERT INTO `sys_role` VALUES (3, '普通用户', 3, 1, 0, '2019-05-05 16:00:00', '2021-03-26 10:35:19');
INSERT INTO `sys_role` VALUES (14, '硕士', 1, 1, 0, '2021-02-19 12:03:07', '2021-02-19 12:03:07');
INSERT INTO `sys_role` VALUES (15, '测试', 1, 1, 0, '2021-02-19 21:41:58', '2021-02-19 21:41:58');
INSERT INTO `sys_role` VALUES (16, '測測', 1, 1, 1, '2021-02-20 11:28:28', '2021-02-20 11:28:31');
INSERT INTO `sys_role` VALUES (18, 'jia', 3, 1, 0, '2021-02-23 12:13:50', '2021-03-15 09:36:57');
INSERT INTO `sys_role` VALUES (20, '12', 1, 1, 1, '2021-03-01 10:56:17', '2021-03-01 10:56:17');
INSERT INTO `sys_role` VALUES (23, 'super', 1, 1, 1, '2021-03-04 15:50:02', '2021-03-04 15:50:02');
INSERT INTO `sys_role` VALUES (24, '是的', 1, 1, 1, '2021-03-08 16:29:19', '2021-03-08 16:29:19');
INSERT INTO `sys_role` VALUES (25, '所属', 1, 1, 1, '2021-03-08 16:29:23', '2021-03-08 16:29:23');
INSERT INTO `sys_role` VALUES (26, '124', 8, 1, 0, '2021-03-10 15:37:07', '2021-03-15 09:37:05');
INSERT INTO `sys_role` VALUES (27, 'dsadsd1请', 1, 1, 1, '2021-03-10 16:14:50', '2021-03-12 16:59:15');
INSERT INTO `sys_role` VALUES (28, 'dsadsa', 1, 1, 1, '2021-03-10 16:14:55', '2021-03-10 16:14:55');
INSERT INTO `sys_role` VALUES (31, 'AAAA', 1, 1, 1, '2021-03-10 16:15:08', '2021-03-11 17:41:51');
INSERT INTO `sys_role` VALUES (32, 'ss', 1, 1, 1, '2021-03-12 14:27:07', '2021-03-15 09:38:08');
INSERT INTO `sys_role` VALUES (36, '凡人歌', 1, 1, 1, '2021-03-15 15:07:23', '2021-03-15 15:07:23');
INSERT INTO `sys_role` VALUES (37, '额', 1, 1, 1, '2021-03-15 15:07:35', '2021-03-15 15:07:35');
INSERT INTO `sys_role` VALUES (38, '233', 1, 1, 1, '2021-03-15 15:07:39', '2021-03-15 15:07:39');
INSERT INTO `sys_role` VALUES (39, '456', 1, 0, 1, '2021-03-15 15:07:43', '2021-03-15 15:07:43');
INSERT INTO `sys_role` VALUES (40, '688', 1, 1, 1, '2021-03-15 15:07:46', '2021-03-15 15:07:46');
INSERT INTO `sys_role` VALUES (41, '787', 1, 1, 1, '2021-03-15 15:07:49', '2021-03-15 15:07:49');
INSERT INTO `sys_role` VALUES (42, '5', 1, 0, 1, '2021-03-15 15:07:52', '2021-03-15 15:07:52');
INSERT INTO `sys_role` VALUES (43, '78856', 1, 0, 1, '2021-03-15 15:07:55', '2021-03-15 15:07:55');
INSERT INTO `sys_role` VALUES (44, '567', 1, 0, 1, '2021-03-15 15:08:00', '2021-03-16 09:54:02');
INSERT INTO `sys_role` VALUES (45, 'adfd', 1, 0, 1, '2021-03-16 11:25:17', '2021-03-18 15:19:13');
INSERT INTO `sys_role` VALUES (47, '凄凄切切群群群群', 1, 0, 1, '2021-03-19 09:55:18', '2021-03-19 09:55:18');
INSERT INTO `sys_role` VALUES (48, 'aa', 1, 1, 0, '2021-03-25 09:31:02', '2021-03-25 09:31:02');
INSERT INTO `sys_role` VALUES (49, 'asa', 1, 1, 0, '2021-03-29 13:40:19', '2021-03-29 13:40:25');
INSERT INTO `sys_role` VALUES (50, 'dd', 1, 1, 0, '2021-03-29 13:43:37', '2021-03-29 13:43:37');
INSERT INTO `sys_role` VALUES (51, 'sas', 1, 1, 0, '2021-03-29 17:16:12', '2021-03-29 17:16:12');
INSERT INTO `sys_role` VALUES (52, 'ggg', 1, 1, 0, '2021-03-29 23:43:40', '2021-03-29 23:43:40');
INSERT INTO `sys_role` VALUES (53, '测试1', 1, 1, 0, '2021-03-31 14:43:10', '2021-03-31 14:50:36');
INSERT INTO `sys_role` VALUES (54, '331', 1, 1, 0, '2021-03-31 14:49:31', '2021-03-31 14:50:06');
INSERT INTO `sys_role` VALUES (55, 'nihaoya', 1, 1, 0, '2021-03-31 15:05:44', '2021-03-31 15:05:44');

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
) ENGINE = InnoDB AUTO_INCREMENT = 30 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'root', '超级管理员', 1, '$2a$10$P97nHj/AVu6JBVCxmj5qEOwsI7rUhFeyu.DrK4ER7sebzv8jp7R5S', 0, 0, 'https://gitee.com/haoxr/image/raw/master/default/807b1042ed4c674d97bcf1f2976234d.jpg', '17621590365', 1, '1490493387@qq.com', '2021-02-10 12:27:30', '2021-03-16 08:36:35');
INSERT INTO `sys_user` VALUES (2, 'admin', '系统管理员', 1, '$2a$10$yJSqqr6sTxNuYtA6EKcVUe2I4USFCzJ29sNcRrBvtAkSYcNg5ydQ6', 21, 0, 'https://gitee.com/haoxr/image/raw/master/default/807b1042ed4c674d97bcf1f2976234d.jpg', '17621210366', 1, '1490493387@qq.com', '2019-10-10 13:41:22', '2021-03-25 15:02:19');
INSERT INTO `sys_user` VALUES (4, 'testaaaaaaaaaa', NULL, 0, '$2a$10$bqE1OGmFaT5OOp2GWd8IhecZscLEuHQWtgn6vHbKskOrTj4R7W9aq', 8, 0, '', NULL, 1, NULL, '2021-02-19 21:37:08', '2021-02-19 21:40:18');
INSERT INTO `sys_user` VALUES (5, 'testmenu', '测试菜单', 0, '$2a$10$8aW6BdSUjmC49tpk3Y342uRix66GvuLQM7o10/e8.EvCa0aed.VnS', 8, 0, '', NULL, 1, NULL, '2021-02-19 21:41:08', '2021-02-19 21:44:17');
INSERT INTO `sys_user` VALUES (6, 'testlq', '測測', 1, '$2a$10$dbBFjOMrNmQOPipvD8AkOO6C1ZtA9ovVvWkReafb344PPP1rl/ioa', 8, 0, '', NULL, 1, NULL, '2021-02-20 11:28:38', '2021-02-25 14:42:58');
INSERT INTO `sys_user` VALUES (7, 'admin1', NULL, 1, '$2a$10$aiuaZVzEIwXEGHBBLVEsf.ge892b5iab.decgAYQ6fs8VwI0NTGTK', 1, 0, '', NULL, 1, NULL, '2021-02-23 14:24:17', '2021-02-23 14:30:09');
INSERT INTO `sys_user` VALUES (8, 'test011', 'xxxxx', 0, '$2a$10$3MktZpqH6wckXhUO2AU7S.FrapiUajgoKe7ZRB.YqxQRP7ICtysP.', 0, 0, '', NULL, 1, NULL, '2021-02-25 10:48:37', '2021-02-25 10:48:37');
INSERT INTO `sys_user` VALUES (9, 'qqq', 'qq', 0, '$2a$10$vbyn6YrCiYDNA8dhnI0O.uBFSGOQMUpITwjf1WJy9K8Kh.tds3NIG', 0, 0, '', '13544741225', 1, NULL, '2021-02-26 15:14:00', '2021-02-26 15:14:00');
INSERT INTO `sys_user` VALUES (10, 'wen', 'java', 1, '$2a$10$vuVIzkfsKQ0iKT5CCpCOuOZS8SZHyB/j4R96s//Joa8H6uWJ.lsz.', 0, 0, '', NULL, 1, NULL, '2021-03-04 15:40:44', '2021-03-15 11:44:59');
INSERT INTO `sys_user` VALUES (11, 'aaaaaaaaaaaa', NULL, 0, '$2a$10$qE7tDDq2zDMQJmz8kMNTOOkYA/zQJsSvI3yOElojls9GsIA45RBNq', 0, 0, '', NULL, 1, NULL, '2021-03-07 13:07:31', '2021-03-07 13:07:31');
INSERT INTO `sys_user` VALUES (12, 'lisi', '11', 1, '$2a$10$8Fu34bEHdl6sEfvnds6OP.R3ycw730saW8/PU099T.voGHY/VkqcW', 0, 0, '', '13311112222', 1, NULL, '2021-03-08 16:07:49', '2021-03-08 16:07:49');
INSERT INTO `sys_user` VALUES (13, 'fuhaha', '让人', 1, '$2a$10$gvLQ7cA0tK8.laK8otWu8uh.ssAfOhuXL5obcLpbonnwCXpDN.xhG', 0, 0, '', '13300009999', 1, NULL, '2021-03-08 16:09:33', '2021-03-08 16:09:33');
INSERT INTO `sys_user` VALUES (14, NULL, NULL, 0, '$2a$10$rvu3Um/01j81n7IDlQ5dtuUOfPsrXSUCr9Uh0sbxUatV2.vjzJMvq', NULL, 0, '', NULL, 0, NULL, '2021-03-10 19:37:19', '2021-03-10 19:37:19');
INSERT INTO `sys_user` VALUES (15, NULL, NULL, 0, '$2a$10$slWJZ9qQ6vVHYwWByQotZ.ZwVM0QY6PzvH/3L02u6KUritSaDEY7u', NULL, 0, '', NULL, 0, NULL, '2021-03-10 19:41:55', '2021-03-10 19:41:55');
INSERT INTO `sys_user` VALUES (16, '测试2', NULL, 0, '$2a$10$/UVobV//VguMXNOQzclWUOcnKoFByXOtH8g2FZq6h.Uxi5mEmKWmK', 19, 0, '', NULL, 1, NULL, '2021-03-14 16:07:35', '2021-03-14 16:07:35');
INSERT INTO `sys_user` VALUES (17, '111', '11', 0, '$2a$10$W4yKbMy7QhFYmNZNT1HXz.vOudxGpDWJaxa9YddmJ6seHXRkJyheO', 0, 0, '', '', 1, NULL, '2021-03-14 18:22:12', '2021-03-14 18:22:12');
INSERT INTO `sys_user` VALUES (18, 'sunTest', NULL, 1, '$2a$10$8WJQGedSzW7BS381ovKK1ew77nK.91OiwwZyx4x2PauCUxEQqptC2', 0, 0, '', NULL, 1, NULL, '2021-03-15 11:32:20', '2021-03-15 11:32:20');
INSERT INTO `sys_user` VALUES (19, 'd', 'd', 1, '$2a$10$u2TYJ.CjjA3cqrWuB050RuL/g8z19mih1pRBaNp46Cx74qkDq.psy', 0, 0, '', '15321676543', 1, NULL, '2021-03-15 16:31:05', '2021-03-15 16:31:05');
INSERT INTO `sys_user` VALUES (20, 'ds', NULL, 0, '$2a$10$MsIRuwHvEei3zlWoDjZZXe4PFwpKXO3/lbw09Vs8OTQ5qj3zlbfum', 0, 0, '', NULL, 1, NULL, '2021-03-20 18:29:01', '2021-03-20 18:29:01');
INSERT INTO `sys_user` VALUES (21, 'ewqewq', NULL, 0, '$2a$10$5RBazqdDXBpaqRnxgQIWUeBLEY4ueLEDY9SqhKXMplLoR7aompioK', 0, 0, '', NULL, 1, NULL, '2021-03-21 20:50:47', '2021-03-21 20:50:47');
INSERT INTO `sys_user` VALUES (22, '李总', NULL, 0, '$2a$10$LHkIDScS2rHTXVIlDXMDT.9l1RcT/PVgGjszv/k7AvsdV9ZzRZsFq', 21, 0, '', NULL, 1, NULL, '2021-03-23 10:38:04', '2021-03-23 10:38:04');
INSERT INTO `sys_user` VALUES (23, 'testrole', 'test', 0, '$2a$10$e/tCPBxugaSi/qSZ3UjrrOS8aGkR2aA4dQfw//0eGpFUu7IvwcpcK', 0, 0, '', NULL, 1, NULL, '2021-03-24 14:49:03', '2021-03-24 14:49:03');
INSERT INTO `sys_user` VALUES (24, '12', '12', 1, '$2a$10$cuknOQpMmFFgdePySR14feUlbQGFKBdJBd75NyrrLBUQL.Hr8zXqi', 0, 0, '', '18337878909', 1, NULL, '2021-03-26 10:11:38', '2021-03-26 10:11:38');
INSERT INTO `sys_user` VALUES (25, 'dd', 'dd', 0, '$2a$10$WEHaSWx6UexFiDYtuTbjE.TTYUUS1JULrc/24zIQV51XIhmu9xgPS', 23, 0, '', NULL, 1, NULL, '2021-03-29 13:46:07', '2021-03-30 11:41:53');
INSERT INTO `sys_user` VALUES (26, 'ddd', NULL, 0, '$2a$10$UhG4GKzR8KFsPib4EB2vnueQ.2bc6eUJkAlnrQpCJS/w0qhSY/67S', 23, 0, '', NULL, 1, NULL, '2021-03-29 13:47:18', '2021-03-29 13:47:18');
INSERT INTO `sys_user` VALUES (29, '钱全去翁', NULL, 0, '$2a$10$Yen3wnQccAE74TIRyLZaK.ImXVJabPwxzLmGoSbOvqaTM99/vdXoy', 21, 0, '', NULL, 1, NULL, '2021-03-30 11:57:35', '2021-03-30 11:57:35');
INSERT INTO `sys_user` VALUES (30, 'nishi', NULL, 0, '$2a$10$rqrETPNUfzZ7Y9JLp.tqw.NzCbKsfI1qNr8Cz4cc7QwJ8931b/Jam', 0, 0, '', NULL, 1, NULL, '2021-03-31 16:01:24', '2021-03-31 16:01:24');

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

SET FOREIGN_KEY_CHECKS = 1;

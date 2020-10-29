/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50720
 Source Host           : localhost:3306
 Source Schema         : youlai-mall

 Target Server Type    : MySQL
 Target Server Version : 50720
 File Encoding         : 65001

 Date: 29/10/2020 20:12:43
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
INSERT INTO `oauth_client_details` VALUES ('youlai-mall-admin', NULL, '123456', 'all', 'password,refresh_token,authorization_code', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `oauth_client_details` VALUES ('youlai-mall-weapp', NULL, '123456', 'all', 'password,refresh_token', NULL, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for oms_order
-- ----------------------------
DROP TABLE IF EXISTS `oms_order`;
CREATE TABLE `oms_order`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_sn` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '订单编号',
  `member_id` bigint(20) NOT NULL COMMENT '会员id',
  `status` tinyint(4) DEFAULT NULL COMMENT '订单状态',
  `source_type` tinyint(4) DEFAULT NULL COMMENT '订单来源：1-微信 2-APP',
  `receiver_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '收货人姓名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '订单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pms_brand
-- ----------------------------
DROP TABLE IF EXISTS `pms_brand`;
CREATE TABLE `pms_brand`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '品牌名称',
  `logo` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '品牌logo图片地址',
  `first_letter` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '检索首字母',
  `sort` tinyint(4) DEFAULT NULL COMMENT '排序',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态: 1-启用 0-关闭',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品品牌' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pms_sku
-- ----------------------------
DROP TABLE IF EXISTS `pms_sku`;
CREATE TABLE `pms_sku`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `spu_id` bigint(20) NOT NULL COMMENT '商品id',
  `specification` json NOT NULL COMMENT '商品规格',
  `bar_code` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品码',
  `origin_price` decimal(10, 2) NOT NULL COMMENT '原价',
  `pic` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '商品SKU图片',
  `price` decimal(10, 2) NOT NULL COMMENT '现价',
  `vip_price` decimal(10, 2) DEFAULT NULL COMMENT '会员价',
  `stock` int(11) NOT NULL DEFAULT 0 COMMENT '库存',
  `create_time` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `pms_sku_bar_code_uindex`(`bar_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品SKU表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pms_spu
-- ----------------------------
DROP TABLE IF EXISTS `pms_spu`;
CREATE TABLE `pms_spu`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品名称',
  `category_id` bigint(20) NOT NULL COMMENT '商品类型id',
  `brand_id` bigint(20) DEFAULT NULL COMMENT '商品品牌id',
  `origin_price` decimal(10, 2) DEFAULT NULL COMMENT '原价',
  `price` decimal(10, 2) DEFAULT NULL COMMENT '现价',
  `vip_price` decimal(10, 2) DEFAULT NULL COMMENT '会员价',
  `sale` int(11) DEFAULT NULL COMMENT '销量',
  `pic` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '商品主图',
  `album` json COMMENT '商品相册，JSON格式',
  `unit` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '单位',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '商品简介',
  `detail` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '商品详情',
  `status` tinyint(4) DEFAULT NULL COMMENT '商品状态：0-下架 1-上架',
  `create_time` datetime(0) DEFAULT NULL,
  `update_time` datetime(0) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pms_spu_attribute
-- ----------------------------
DROP TABLE IF EXISTS `pms_spu_attribute`;
CREATE TABLE `pms_spu_attribute`  (
  `id` bigint(20) DEFAULT NULL,
  `spu_id` bigint(20) DEFAULT NULL COMMENT '商品id',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '属性名称',
  `value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '属性值'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品属性' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pms_spu_category
-- ----------------------------
DROP TABLE IF EXISTS `pms_spu_category`;
CREATE TABLE `pms_spu_category`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '分类名称',
  `parent_id` bigint(20) NOT NULL,
  `level` int(11) DEFAULT NULL COMMENT '层级',
  `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '图标地址',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `unit` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '单位',
  `count` int(11) DEFAULT NULL COMMENT '商品数量',
  `visible` tinyint(1) DEFAULT NULL COMMENT '显示状态: 0-隐藏 1-显示',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品分类' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pms_spu_specification
-- ----------------------------
DROP TABLE IF EXISTS `pms_spu_specification`;
CREATE TABLE `pms_spu_specification`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `spu_id` bigint(20) DEFAULT NULL COMMENT '商品id',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品规格名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品规格' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sms_advert
-- ----------------------------
DROP TABLE IF EXISTS `sms_advert`;
CREATE TABLE `sms_advert`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '广告名称',
  `pic` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '图片地址',
  `start_time` datetime(0) DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime(0) DEFAULT NULL COMMENT '结束时间',
  `status` tinyint(1) DEFAULT NULL COMMENT '状态：1-开启  0-关闭',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '链接地址',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0) DEFAULT NULL,
  `create_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `update_time` datetime(0) DEFAULT NULL,
  `update_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sms_advert
-- ----------------------------
INSERT INTO `sms_advert` VALUES (1, '小程序首页轮播图1', 'http://101.37.69.49:9000/default/19248a50b674402bb6825683e7ffd070.jpg', '2020-10-25 09:12:14', '2020-10-31 09:12:31', 1, 1, NULL, NULL, '2020-10-25 00:13:51', 'admin', '2020-10-25 10:51:22', 'admin');
INSERT INTO `sms_advert` VALUES (2, '小程序首页轮播图2', 'http://101.37.69.49:9000/default/2c8f4023f98c4fa0a843907b9af6b8a4.jpg', '2020-10-25 09:25:07', '2020-10-31 00:00:00', 1, 2, NULL, NULL, '2020-10-25 09:25:23', 'admin', '2020-10-25 10:51:19', 'admin');
INSERT INTO `sms_advert` VALUES (3, '小程序首页轮播图3', 'http://101.37.69.49:9000/default/7d21e401bea247428dc5b64bf67b9351.jpg', '2020-10-25 09:25:37', '2020-10-31 00:00:00', 1, 3, NULL, NULL, '2020-10-25 09:25:56', 'admin', '2020-10-25 10:51:14', 'admin');
INSERT INTO `sms_advert` VALUES (4, '小程序首页轮播图4', 'http://101.37.69.49:9000/default/545b9ba066d246f7a26bea54622b966c.jpg', '2020-10-25 00:00:00', '2020-10-31 00:00:00', 1, 4, NULL, NULL, '2020-10-25 09:26:47', 'admin', '2020-10-25 10:51:10', 'admin');
INSERT INTO `sms_advert` VALUES (5, '小程序首页轮播图5', 'http://101.37.69.49:9000/default/a804f39f69a84b08abb3dc7fbda51efe.jpg', '2020-10-25 00:00:00', '2020-10-31 00:00:00', 1, 5, NULL, NULL, '2020-10-25 10:51:02', 'admin', '2020-10-25 10:51:02', 'admin');

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
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '部门表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES (8, '研发部', 0, '0', 1, '郝先瑞', '17621590365', '1490493387@qq.com', 1, 0, 'admin', '2020-09-23 11:32:05', 'admin', '2020-09-23 11:32:05');
INSERT INTO `sys_dept` VALUES (9, '测试部', 0, '0', 1, '张三', '15123569856', 'youlai@163.com', 1, 0, 'admin', '2020-09-23 11:33:06', 'admin', '2020-09-23 11:33:06');

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
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '字典数据表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES (1, '男', '1', 'gender', 1, 1, NULL, '性别男', 'admin', '2019-05-05 13:07:52', 'admin', '2019-07-02 14:23:05');
INSERT INTO `sys_dict` VALUES (2, '女', '2', 'gender', 2, 1, NULL, '性别女', 'admin', '2019-04-19 11:33:00', 'admin', '2019-07-02 14:23:05');
INSERT INTO `sys_dict` VALUES (5, '未知', '0', 'gender', 1, 1, 0, '', 'admin', '2020-10-17 08:09:31', 'admin', '2020-10-17 08:09:31');
INSERT INTO `sys_dict` VALUES (6, '密码模式', 'password', 'grant_type', 1, 1, 0, '', 'admin', '2020-10-17 09:11:52', 'admin', '2020-10-17 09:11:52');
INSERT INTO `sys_dict` VALUES (7, '授权码模式', 'authorization_code', 'grant_type', 1, 1, 0, '', 'admin', '2020-10-17 09:12:15', 'admin', '2020-10-17 09:12:15');
INSERT INTO `sys_dict` VALUES (8, '客户端模式', 'client_credentials', 'grant_type', 1, 1, 0, '', 'admin', '2020-10-17 09:12:36', 'admin', '2020-10-17 09:12:36');
INSERT INTO `sys_dict` VALUES (9, '刷新模式', 'refresh_token', 'grant_type', 1, 1, 0, '', 'admin', '2020-10-17 09:12:57', 'admin', '2020-10-17 09:12:57');
INSERT INTO `sys_dict` VALUES (10, '简化模式', 'implicit', 'grant_type', 1, 1, 0, '', 'admin', '2020-10-17 09:13:23', 'admin', '2020-10-17 09:13:23');

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
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '字典类型表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict_type
-- ----------------------------
INSERT INTO `sys_dict_type` VALUES (1, 'gender', '性别', 1, '性别', '', '2019-12-06 19:03:32', '', '2019-12-12 19:03:15');
INSERT INTO `sys_dict_type` VALUES (11, 'grant_type', '授权方式', 1, NULL, 'admin', '2020-10-17 08:09:50', 'admin', '2020-10-17 08:09:50');

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
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '菜单管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, '系统管理', 0, 'admin', '', '/admin/user', NULL, 0, 'component', 6, 1, 1, 'admin', '2020-09-23 09:12:21', 'admin', '2020-09-15 13:10:45');
INSERT INTO `sys_menu` VALUES (2, '用户管理', 1, 'user', 'admin/user', NULL, NULL, 1, 'user', 0, 1, 1, 'admin', '2020-09-23 09:12:26', 'admin', '2020-09-15 13:12:05');
INSERT INTO `sys_menu` VALUES (3, '角色管理', 1, 'role', 'admin/role', NULL, NULL, 1, 'peoples', 0, 1, 1, 'admin', NULL, 'admin', NULL);
INSERT INTO `sys_menu` VALUES (4, '菜单管理', 1, 'menu', 'admin/menu', NULL, NULL, 1, 'tree-table', 8, 1, 1, 'admin', NULL, 'admin', '2020-09-15 13:12:20');
INSERT INTO `sys_menu` VALUES (5, '字典管理', 1, 'dict', 'admin/dict', NULL, NULL, 1, 'education', 10, 1, 1, 'admin', NULL, 'admin', '2020-09-15 13:11:37');
INSERT INTO `sys_menu` VALUES (6, '部门管理', 1, 'dept', 'admin/dept', NULL, NULL, 1, 'tree', 0, 1, 1, 'admin', NULL, 'admin', NULL);
INSERT INTO `sys_menu` VALUES (7, '资源管理', 1, 'resource', 'admin/resource', NULL, NULL, 1, 'list', 9, 1, 1, 'admin', '2020-09-22 17:00:01', 'admin', '2020-09-22 17:00:01');
INSERT INTO `sys_menu` VALUES (8, '客户端管理', 1, 'client', 'admin/client', NULL, NULL, 1, 'tab', 11, 1, 1, 'admin', '2020-10-17 08:04:08', 'admin', '2020-10-17 08:04:08');
INSERT INTO `sys_menu` VALUES (9, '营销管理', 0, 'sms', NULL, '', NULL, 0, 'list', 0, 1, 1, 'admin', '2020-10-24 15:24:04', 'admin', '2020-10-24 15:25:39');
INSERT INTO `sys_menu` VALUES (10, '广告管理', 9, 'advert', 'sms/advert', NULL, NULL, 1, 'documentation', 1, 1, 1, 'admin', '2020-10-24 15:25:15', 'admin', '2020-10-24 15:25:15');

-- ----------------------------
-- Table structure for sys_resource
-- ----------------------------
DROP TABLE IF EXISTS `sys_resource`;
CREATE TABLE `sys_resource`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '权限名称',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '资源路径',
  `create_time` datetime(0) DEFAULT NULL,
  `create_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `update_time` datetime(0) DEFAULT NULL,
  `update_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `id`(`id`, `name`) USING BTREE,
  INDEX `id_2`(`id`, `name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_resource
-- ----------------------------
INSERT INTO `sys_resource` VALUES (1, '系统管理', '/youlai-admin/**', NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES (2, '菜单管理', '/youlai-admin/menus/**', NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES (3, '用户管理', '/youlai-admin/users/**', NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES (4, '部门管理', '/youlai-admin/depts/**', NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES (5, '字典管理', '/youlai-admin/dictionaries/**', NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES (6, '角色管理', '/youlai-admin/roles/**', NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES (7, '资源管理', '/youlai-admin/resources/**', NULL, NULL, NULL, NULL);
INSERT INTO `sys_resource` VALUES (8, '客户端管理', '/youlai-admin/clients', '2020-10-17 08:06:59', 'admin', '2020-10-17 08:06:59', 'admin');
INSERT INTO `sys_resource` VALUES (9, '营销管理', '/youlai-mall-sms/**', '2020-10-24 15:29:01', 'admin', '2020-10-24 15:29:09', 'admin');
INSERT INTO `sys_resource` VALUES (10, '广告管理', '/youlai-mall-sms/advert', '2020-10-24 15:35:50', 'admin', '2020-10-24 15:35:50', 'admin');
INSERT INTO `sys_resource` VALUES (11, '文件管理', '/youlai-admin/files', '2020-10-24 23:02:56', 'admin', '2020-10-24 23:02:56', 'admin');

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
INSERT INTO `sys_role` VALUES (2, '系统管理员', 'admin', 2, 1, 0, '系统管理员', '2018-12-23 16:00:00', 'admin', '2020-09-22 17:01:44', 'admin');
INSERT INTO `sys_role` VALUES (3, '普通用户', 'common', 3, 1, 0, '普通用户', '2019-05-05 16:00:00', 'admin', '2020-09-22 17:09:54', 'admin');

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
INSERT INTO `sys_role_menu` VALUES (2, 2);
INSERT INTO `sys_role_menu` VALUES (2, 3);
INSERT INTO `sys_role_menu` VALUES (2, 4);
INSERT INTO `sys_role_menu` VALUES (2, 5);
INSERT INTO `sys_role_menu` VALUES (2, 6);
INSERT INTO `sys_role_menu` VALUES (2, 7);
INSERT INTO `sys_role_menu` VALUES (2, 1);
INSERT INTO `sys_role_menu` VALUES (1, 9);
INSERT INTO `sys_role_menu` VALUES (1, 10);
INSERT INTO `sys_role_menu` VALUES (1, 1);
INSERT INTO `sys_role_menu` VALUES (1, 2);
INSERT INTO `sys_role_menu` VALUES (1, 3);
INSERT INTO `sys_role_menu` VALUES (1, 6);
INSERT INTO `sys_role_menu` VALUES (1, 4);
INSERT INTO `sys_role_menu` VALUES (1, 7);
INSERT INTO `sys_role_menu` VALUES (1, 5);
INSERT INTO `sys_role_menu` VALUES (1, 8);

-- ----------------------------
-- Table structure for sys_role_resource
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_resource`;
CREATE TABLE `sys_role_resource`  (
  `role_id` int(11) DEFAULT NULL COMMENT '角色id',
  `resource_id` int(11) DEFAULT NULL COMMENT '资源id',
  INDEX `role_id`(`role_id`) USING BTREE,
  INDEX `permission_id`(`resource_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_resource
-- ----------------------------
INSERT INTO `sys_role_resource` VALUES (2, 10);
INSERT INTO `sys_role_resource` VALUES (2, 9);
INSERT INTO `sys_role_resource` VALUES (2, 8);
INSERT INTO `sys_role_resource` VALUES (2, 1);
INSERT INTO `sys_role_resource` VALUES (2, 2);
INSERT INTO `sys_role_resource` VALUES (2, 3);
INSERT INTO `sys_role_resource` VALUES (2, 4);
INSERT INTO `sys_role_resource` VALUES (2, 5);
INSERT INTO `sys_role_resource` VALUES (2, 6);
INSERT INTO `sys_role_resource` VALUES (2, 7);
INSERT INTO `sys_role_resource` VALUES (1, 11);
INSERT INTO `sys_role_resource` VALUES (1, 10);
INSERT INTO `sys_role_resource` VALUES (1, 9);
INSERT INTO `sys_role_resource` VALUES (1, 8);
INSERT INTO `sys_role_resource` VALUES (1, 1);
INSERT INTO `sys_role_resource` VALUES (1, 2);
INSERT INTO `sys_role_resource` VALUES (1, 3);
INSERT INTO `sys_role_resource` VALUES (1, 4);
INSERT INTO `sys_role_resource` VALUES (1, 5);
INSERT INTO `sys_role_resource` VALUES (1, 6);
INSERT INTO `sys_role_resource` VALUES (1, 7);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用户名',
  `nickname` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '昵称',
  `gender` tinyint(1) DEFAULT 0 COMMENT '性别',
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '密码',
  `dept_id` int(11) DEFAULT NULL COMMENT '部门ID',
  `deleted` tinyint(1) DEFAULT 0 COMMENT '删除标识（0未删除 1已删除）',
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '用户头像',
  `mobile` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '联系方式',
  `status` tinyint(1) DEFAULT 0 COMMENT '用户状态（0正常 1禁用）',
  `email` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用户邮箱',
  `create_time` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建用户',
  `update_time` datetime(0) DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '更新用户',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `login_name`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', '系统管理员', 0, '$2a$10$dLq3.pXNwTNqWabsRfJX4ej8Htk/vUWuHh.LvITq5BrU8u.dYvZpC', 0, 0, 'https://i.loli.net/2020/05/08/dVvpaQ8NHkWAC2c.jpg', '17621210366', 1, '1490493387@qq.com', '2019-10-10 13:41:22', 'admin', '2020-03-09 10:12:54', 'fly4j');

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

-- ----------------------------
-- Table structure for ums_member
-- ----------------------------
DROP TABLE IF EXISTS `ums_member`;
CREATE TABLE `ums_member`  (
  `id` bigint(20) NOT NULL,
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `gender` tinyint(1) DEFAULT NULL,
  `nickname` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `mobile` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `openid` char(28) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `session_key` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `status` tinyint(1) DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT NULL,
  `create_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `create_time` datetime(0) DEFAULT NULL,
  `update_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `update_time` datetime(0) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ums_member
-- ----------------------------
INSERT INTO `ums_member` VALUES (1315699931359424513, 'oUBUG5hAB_8EMrSaqd2HjJQBFg74', '$2a$10$izTOlQmmWfYwY56Dc5mdY../m4a3345wSPKN2X5ldh9GgwWKY.sea', 1, '郝先瑞', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/J31cY2qVWviaOqhjPlr18VY5ic1SUvDESG1mQkicQfFugWibYe7VJIhYJBZYDBib0T4TJ8KClTmP3gOF1eOk91ctibRQ/132', 'oUBUG5hAB_8EMrSaqd2HjJQBFg74', NULL, 1, NULL, NULL, NULL, NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;

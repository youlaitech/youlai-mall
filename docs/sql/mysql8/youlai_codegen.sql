use youlai_codegen;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for gen_config
-- ----------------------------
DROP TABLE IF EXISTS `gen_config`;
CREATE TABLE `gen_config` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `table_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '表名',
  `module_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '模块名(youlai-system)',
  `package_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '包名(com.youlai.system)',
  `business_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '业务名(系统用户)',
  `entity_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '实体类名(User)',
  `author` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '作者',
  `parent_menu_id` bigint DEFAULT NULL COMMENT '上级菜单ID，对应sys_menu的id ',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_tablename` (`table_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=238 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='代码生成配置表';
-- ----------------------------
-- Records of gen_config
-- ----------------------------

-- ----------------------------
-- Table structure for gen_field_config
-- ----------------------------
DROP TABLE IF EXISTS `gen_field_config`;
CREATE TABLE `gen_field_config` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `config_id` bigint NOT NULL COMMENT '关联的配置ID',
    `column_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
    `column_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
    `field_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '字段名称',
    `field_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '字段类型',
    `field_sort` int DEFAULT NULL COMMENT '字段排序',
    `field_comment` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '字段描述',
    `is_required` tinyint(1) DEFAULT NULL COMMENT '是否必填',
    `max_length` int DEFAULT NULL,
    `is_show_in_list` tinyint(1) DEFAULT '0' COMMENT '是否在列表显示',
    `is_show_in_form` tinyint(1) DEFAULT '0' COMMENT '是否在表单显示',
    `is_show_in_query` tinyint(1) DEFAULT '0' COMMENT '是否在查询条件显示',
    `query_type` tinyint DEFAULT NULL COMMENT '查询方式',
    `form_type` tinyint DEFAULT NULL COMMENT '表单类型',
    `dict_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '字典类型',
    `column_length` int DEFAULT NULL,
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `config_id` (`config_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2275 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='字段配置表';

-- ----------------------------
-- Records of gen_field_config
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;

/*
* 有来商城数据库
* MySQL8.x版本
*/

-- ----------------------------
-- 系统管理数据库
-- ----------------------------
CREATE DATABASE IF NOT EXISTS youlai_system DEFAULT CHARACTER SET utf8mb4 DEFAULT COLLATE utf8mb4_general_ci;

-- ----------------------------
-- Nacos 数据库
-- ----------------------------
CREATE DATABASE nacos_config CHARACTER SET utf8 COLLATE utf8_bin;

-- ----------------------------
-- Seata 数据库
-- ----------------------------
CREATE DATABASE seata DEFAULT CHARACTER SET utf8mb4 DEFAULT COLLATE utf8mb4_general_ci;

-- ----------------------------
-- XXL-JOB 数据库
-- ----------------------------
CREATE DATABASE `xxl_job` DEFAULT CHARACTER SET utf8mb4 DEFAULT COLLATE utf8mb4_general_ci;

-- ----------------------------
-- OAuth2数据库
-- ----------------------------
CREATE DATABASE IF NOT EXISTS oauth2_server DEFAULT CHARACTER SET utf8mb4 DEFAULT COLLATE utf8mb4_general_ci;

-- ----------------------------
-- 商城会员数据库
-- ----------------------------
CREATE DATABASE IF NOT EXISTS mall_ums DEFAULT CHARACTER SET utf8mb4 DEFAULT COLLATE utf8mb4_general_ci;
-- ----------------------------
-- 商城商品数据库
-- ----------------------------
CREATE DATABASE IF NOT EXISTS mall_pms DEFAULT CHARACTER SET utf8mb4 DEFAULT COLLATE utf8mb4_general_ci;
-- ----------------------------
-- 商城订单数据库
-- ----------------------------
CREATE DATABASE IF NOT EXISTS mall_oms DEFAULT CHARACTER SET utf8mb4 DEFAULT COLLATE utf8mb4_general_ci;
-- ----------------------------
-- 商城营销数据库
-- ----------------------------
CREATE DATABASE IF NOT EXISTS mall_sms DEFAULT CHARACTER SET utf8mb4 DEFAULT COLLATE utf8mb4_general_ci;


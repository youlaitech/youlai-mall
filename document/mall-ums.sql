/*
 Navicat Premium Data Transfer

 Source Server         : 101.37.69.49
 Source Server Type    : MySQL
 Source Server Version : 80022
 Source Host           : 101.37.69.49:3306
 Source Schema         : mall-ums

 Target Server Type    : MySQL
 Target Server Version : 80022
 File Encoding         : 65001

 Date: 13/01/2021 00:29:46
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for ums_address
-- ----------------------------
DROP TABLE IF EXISTS `ums_address`;
CREATE TABLE `ums_address`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(0) NULL DEFAULT NULL,
  `receiver_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '收货人姓名',
  `receiver_mobile` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '收货人联系方式',
  `province` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '省',
  `city` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '市',
  `area` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '区',
  `address_detail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '地址详细信息',
  `zip_code` char(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮编',
  `defaulted` tinyint(0) NULL DEFAULT NULL COMMENT '是否默认地址',
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_updated` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ums_address
-- ----------------------------
INSERT INTO `ums_address` VALUES (1, 1, '郝先瑞', '17621590365', '安徽省', '六安市', '金寨县', '白塔畈乡中心村院墙组', '100000', 1, '2021-01-12 19:14:56', '2021-01-12 19:15:07');

-- ----------------------------
-- Table structure for ums_member
-- ----------------------------
DROP TABLE IF EXISTS `ums_member`;
CREATE TABLE `ums_member`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `username` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `gender` tinyint(1) NULL DEFAULT NULL,
  `nickname` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `mobile` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `birthday` date NULL DEFAULT NULL,
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `openid` char(28) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `session_key` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `status` tinyint(1) NULL DEFAULT 1,
  `point` int(0) NULL DEFAULT 0 COMMENT '会员积分',
  `deleted` tinyint(1) NULL DEFAULT 0,
  `gmt_create` datetime(0) NULL DEFAULT NULL,
  `gmt_updated` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1345981610566221826 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ums_member
-- ----------------------------
INSERT INTO `ums_member` VALUES (1, 'oUBUG5hAB_8EMrSaqd2HjJQBFg74', '$2a$10$3oFJG7M8hbcYH3Hc4n2dDuKZzqaUdfCvK.nyubJdBI.Oh559OlFAe', 1, '郝先瑞', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/J31cY2qVWviaOqhjPlr18VY5ic1SUvDESG1mQkicQfFugWibYe7VJIhYJBZYDBib0T4TJVhUOtiaW1TGkJRqIWd3K0dQ/132', 'oUBUG5hAB_8EMrSaqd2HjJQBFg74', NULL, 1, 0, 0, '2021-01-12 17:51:59', NULL);
INSERT INTO `ums_member` VALUES (2, 'oEMah4qx8utBwve1_5U2bq4z9Ucw', '$2a$10$1fwuJq88DI8DvYuSkIbiTeTFIAeVQ3ZwgOz6igWQG0uOurqGbuOn2', 1, 'Flamesky', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eorwiaJcRPxKMNHgov0HGBRA8JODQrhw67x61FGEFwic2E2UlhXSKmQ455jqT5RIPsZjmpkdia0pyZdA/132', 'oEMah4qx8utBwve1_5U2bq4z9Ucw', NULL, 1, 0, 0, '2021-01-12 17:52:03', NULL);
INSERT INTO `ums_member` VALUES (3, 'oH-MK0V-N4Lotq-kXIMAMjLdXdtY', '$2a$10$T.jSFW6bQyaVLVeu50BipO0cPwdIBnoT/AwJEjEttt4wKwXJeoSsW', 1, '非洲小白脸', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/PiajxSqBRaEIIs1glKcYOadLFibr2et98eXTADdicLUGrQqF8EtvicIu5e5TwOkuBAzIf8zEl0aYPJaDkfIHTOEWuQ/132', 'oH-MK0V-N4Lotq-kXIMAMjLdXdtY', NULL, 1, 0, 0, '2021-01-12 17:52:06', NULL);
INSERT INTO `ums_member` VALUES (4, 'oUBUG5lBhnnn-HBCF9mYMZbQv7A8', '$2a$10$0p7NIjPsLAe8xLnJPmuY1ON6qwhfLvaMv1x4OkYSUhI0d7YVsIk7y', 1, '花花的世界', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLxWhtkFhVKpfXib0BibMaIzeOAVCGVScnR5ibsibdENiaibjvnfy7AxeSSCTbn9IBvqMe1iaJ6BWTxIjZtg/132', 'oUBUG5lBhnnn-HBCF9mYMZbQv7A8', NULL, 1, 0, 0, '2021-01-12 17:52:09', NULL);
INSERT INTO `ums_member` VALUES (5, 'oZ75o5Kk-opj_ioVOj6vTO7-K0HM', '$2a$10$oVCO0XEsiPjvW88lOLEX/edhYBOCpTOPQqLDXO5iDd6Me6nrSei3q', 1, '微尘', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/vNZqQTZRAia4sz17MJeeXeqhzaBbIEzEXGvgwG4l1KQg2mQAb3eB1q9HLnVJUo4u8OSNSv1seuqHxNPKyYicb4Dw/132', 'oZ75o5Kk-opj_ioVOj6vTO7-K0HM', NULL, 1, 0, 0, '2021-01-12 17:52:12', NULL);
INSERT INTO `ums_member` VALUES (6, 'oUBUG5m_9Gfa3bZmzP_tRNnX2vsg', '$2a$10$do/TaAtRGPjOYRAY9.DrJeIKqXFYv1sAH3J11dqNInbdD8OVkLh8m', 1, '大田', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIPhsrhOFictxk44ialrd4gZP6SyFmz4v2rHBZ3C72O0KsKQDTHlVBqtoSJ5uiaPAvD0t9F5VBjbruQw/132', 'oUBUG5m_9Gfa3bZmzP_tRNnX2vsg', NULL, 1, 0, 0, '2021-01-12 17:52:16', NULL);
INSERT INTO `ums_member` VALUES (7, 'oUBUG5tj5LF8IjJDU2s1cqSdthdo', '$2a$10$xnaqCcQe3U1NKCdfT9vtvuTt0GckjJSuwK.oZ47Eh53TilNRG.OG2', 1, '看好路，向前走！', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/lZibiaShtph66QznR6yiarR7VsBkkjqGqPCwqDGD8WlaxnllcjG7SRiaX0DOujFXX8epAbyvFpHv03uI83xXFhdwZA/132', 'oUBUG5tj5LF8IjJDU2s1cqSdthdo', NULL, 1, 0, 0, NULL, NULL);

-- ----------------------------
-- Table structure for ums_user
-- ----------------------------
DROP TABLE IF EXISTS `ums_user`;
CREATE TABLE `ums_user`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `username` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `gender` tinyint(1) NULL DEFAULT NULL,
  `nickname` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `mobile` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `birthday` date NULL DEFAULT NULL,
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `openid` char(28) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `session_key` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `status` tinyint(1) NULL DEFAULT 1,
  `point` int(0) NULL DEFAULT 0 COMMENT '会员积分',
  `deleted` tinyint(1) NULL DEFAULT 0,
  `gmt_create` datetime(0) NULL DEFAULT NULL,
  `gmt_updated` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1345981610566221827 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ums_user
-- ----------------------------
INSERT INTO `ums_user` VALUES (1, 'oUBUG5hAB_8EMrSaqd2HjJQBFg74', '$2a$10$3oFJG7M8hbcYH3Hc4n2dDuKZzqaUdfCvK.nyubJdBI.Oh559OlFAe', 1, '郝先瑞', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/J31cY2qVWviaOqhjPlr18VY5ic1SUvDESG1mQkicQfFugWibYe7VJIhYJBZYDBib0T4TJVhUOtiaW1TGkJRqIWd3K0dQ/132', 'oUBUG5hAB_8EMrSaqd2HjJQBFg74', NULL, 1, 0, 0, '2021-01-12 17:51:59', NULL);
INSERT INTO `ums_user` VALUES (2, 'oEMah4qx8utBwve1_5U2bq4z9Ucw', '$2a$10$1fwuJq88DI8DvYuSkIbiTeTFIAeVQ3ZwgOz6igWQG0uOurqGbuOn2', 1, 'Flamesky', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eorwiaJcRPxKMNHgov0HGBRA8JODQrhw67x61FGEFwic2E2UlhXSKmQ455jqT5RIPsZjmpkdia0pyZdA/132', 'oEMah4qx8utBwve1_5U2bq4z9Ucw', NULL, 1, 0, 0, '2021-01-12 17:52:03', NULL);
INSERT INTO `ums_user` VALUES (3, 'oH-MK0V-N4Lotq-kXIMAMjLdXdtY', '$2a$10$T.jSFW6bQyaVLVeu50BipO0cPwdIBnoT/AwJEjEttt4wKwXJeoSsW', 1, '非洲小白脸', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/PiajxSqBRaEIIs1glKcYOadLFibr2et98eXTADdicLUGrQqF8EtvicIu5e5TwOkuBAzIf8zEl0aYPJaDkfIHTOEWuQ/132', 'oH-MK0V-N4Lotq-kXIMAMjLdXdtY', NULL, 1, 0, 0, '2021-01-12 17:52:06', NULL);
INSERT INTO `ums_user` VALUES (4, 'oUBUG5lBhnnn-HBCF9mYMZbQv7A8', '$2a$10$0p7NIjPsLAe8xLnJPmuY1ON6qwhfLvaMv1x4OkYSUhI0d7YVsIk7y', 1, '花花的世界', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLxWhtkFhVKpfXib0BibMaIzeOAVCGVScnR5ibsibdENiaibjvnfy7AxeSSCTbn9IBvqMe1iaJ6BWTxIjZtg/132', 'oUBUG5lBhnnn-HBCF9mYMZbQv7A8', NULL, 1, 0, 0, '2021-01-12 17:52:09', NULL);
INSERT INTO `ums_user` VALUES (5, 'oZ75o5Kk-opj_ioVOj6vTO7-K0HM', '$2a$10$oVCO0XEsiPjvW88lOLEX/edhYBOCpTOPQqLDXO5iDd6Me6nrSei3q', 1, '微尘', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/vNZqQTZRAia4sz17MJeeXeqhzaBbIEzEXGvgwG4l1KQg2mQAb3eB1q9HLnVJUo4u8OSNSv1seuqHxNPKyYicb4Dw/132', 'oZ75o5Kk-opj_ioVOj6vTO7-K0HM', NULL, 1, 0, 0, '2021-01-12 17:52:12', NULL);
INSERT INTO `ums_user` VALUES (6, 'oUBUG5m_9Gfa3bZmzP_tRNnX2vsg', '$2a$10$do/TaAtRGPjOYRAY9.DrJeIKqXFYv1sAH3J11dqNInbdD8OVkLh8m', 1, '大田', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIPhsrhOFictxk44ialrd4gZP6SyFmz4v2rHBZ3C72O0KsKQDTHlVBqtoSJ5uiaPAvD0t9F5VBjbruQw/132', 'oUBUG5m_9Gfa3bZmzP_tRNnX2vsg', NULL, 1, 0, 0, '2021-01-12 17:52:16', NULL);
INSERT INTO `ums_user` VALUES (7, 'oUBUG5tj5LF8IjJDU2s1cqSdthdo', '$2a$10$xnaqCcQe3U1NKCdfT9vtvuTt0GckjJSuwK.oZ47Eh53TilNRG.OG2', 1, '看好路，向前走！', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/lZibiaShtph66QznR6yiarR7VsBkkjqGqPCwqDGD8WlaxnllcjG7SRiaX0DOujFXX8epAbyvFpHv03uI83xXFhdwZA/132', 'oUBUG5tj5LF8IjJDU2s1cqSdthdo', NULL, 1, 0, 0, NULL, NULL);

-- ----------------------------
-- Table structure for undo_log
-- ----------------------------
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log`  (
  `branch_id` bigint(0) NOT NULL COMMENT 'branch transaction id',
  `xid` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'global transaction id',
  `context` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'undo_log context,such as serialization',
  `rollback_info` longblob NOT NULL COMMENT 'rollback info',
  `log_status` int(0) NOT NULL COMMENT '0:normal status,1:defense status',
  `log_created` datetime(6) NOT NULL COMMENT 'create datetime',
  `log_modified` datetime(6) NOT NULL COMMENT 'modify datetime',
  UNIQUE INDEX `ux_undo_log`(`xid`, `branch_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'AT transaction mode undo table' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;

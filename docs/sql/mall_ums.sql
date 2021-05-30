/*
 Navicat Premium Data Transfer

 Source Server         : www.youlai.store
 Source Server Type    : MySQL
 Source Server Version : 80023
 Source Host           : www.youlai.store:3306
 Source Schema         : mall_ums

 Target Server Type    : MySQL
 Target Server Version : 80023
 File Encoding         : 65001

 Date: 22/03/2021 09:26:23
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for ums_address
-- ----------------------------
DROP TABLE IF EXISTS `ums_address`;
CREATE TABLE `ums_address`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `member_id` bigint(0) DEFAULT NULL COMMENT '会员ID',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '收货人姓名',
  `mobile` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '收货人联系方式',
  `province` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '省',
  `city` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '市',
  `area` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '区',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '详细地址',
  `zip_code` char(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '邮编',
  `defaulted` tinyint(0) DEFAULT NULL COMMENT '是否默认地址',
  `gmt_create` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ums_address
-- ----------------------------
INSERT INTO `ums_address` VALUES (1, 1, '郝先瑞', '17621590365', '安徽省', '六安市', '金寨县', '白塔畈乡中心村院墙组', '100000', 1, '2021-01-12 19:14:56', '2021-01-12 19:15:07');
INSERT INTO `ums_address` VALUES (2, 1, '你好，旧时光', '15951933116', '上海', '上海市', '浦东新区', '花木街道15号6012', NULL, 0, NULL, '2021-03-21 19:30:12');
INSERT INTO `ums_address` VALUES (3, 4, '花韦', '13151572876', '上海', '南京市', '雨花台区', '1号', NULL, 1, NULL, NULL);
INSERT INTO `ums_address` VALUES (4, 4, '花韦', '13151572876', '上海', '南京市', '雨花台区', '1号', NULL, 0, NULL, NULL);
INSERT INTO `ums_address` VALUES (5, 4, 'niuniu', '13151572870', '上海', '上海市', '浦东新区', '1号路1栋', NULL, 0, NULL, NULL);
INSERT INTO `ums_address` VALUES (6, 4, '123', '13150572890', '上海', '上海市', '浦东新区', '123', NULL, 0, NULL, NULL);
INSERT INTO `ums_address` VALUES (7, 4, '123', '13157282800', '上海', '上海市', '浦东新区', '13157282800', NULL, 0, NULL, NULL);
INSERT INTO `ums_address` VALUES (8, 1, '张三', '17621590365', '上海', '上海市', '浦东新区', '123', NULL, NULL, '2021-03-21 19:36:14', '2021-03-21 19:36:14');
INSERT INTO `ums_address` VALUES (9, 1, '郝先瑞', '15623548756', '上海', '上海市', '浦东新区', '123', NULL, NULL, '2021-03-22 00:42:48', '2021-03-22 00:42:48');

-- ----------------------------
-- Table structure for ums_member
-- ----------------------------
DROP TABLE IF EXISTS `ums_member`;
CREATE TABLE `ums_member`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `username` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `gender` tinyint(1) DEFAULT NULL,
  `nickname` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `mobile` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `openid` char(28) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `session_key` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `status` tinyint(1) DEFAULT 1,
  `point` int(0) DEFAULT 0 COMMENT '会员积分',
  `deleted` tinyint(1) DEFAULT 0,
  `gmt_create` datetime(0) DEFAULT NULL,
  `gmt_modified` datetime(0) DEFAULT NULL,
  `balance` bigint(0) DEFAULT 1000000000,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ums_member
-- ----------------------------
INSERT INTO `ums_member` VALUES (1, 'oUBUG5hAB_8EMrSaqd2HjJQBFg74', '$2a$10$3oFJG7M8hbcYH3Hc4n2dDuKZzqaUdfCvK.nyubJdBI.Oh559OlFAe', 1, '郝先瑞', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/J31cY2qVWviaOqhjPlr18VY5ic1SUvDESG1mQkicQfFugWibYe7VJIhYJBZYDBib0T4TJVhUOtiaW1TGkJRqIWd3K0dQ/132', 'oUBUG5hAB_8EMrSaqd2HjJQBFg74', NULL, 1, 10, 0, '2021-01-12 17:51:59', '2021-03-16 16:31:14', 996941900);
INSERT INTO `ums_member` VALUES (2, 'oEMah4qx8utBwve1_5U2bq4z9Ucw', '$2a$10$1fwuJq88DI8DvYuSkIbiTeTFIAeVQ3ZwgOz6igWQG0uOurqGbuOn2', 1, 'Flamesky', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eorwiaJcRPxKMNHgov0HGBRA8JODQrhw67x61FGEFwic2E2UlhXSKmQ455jqT5RIPsZjmpkdia0pyZdA/132', 'oEMah4qx8utBwve1_5U2bq4z9Ucw', NULL, 1, 0, 0, '2021-01-12 17:52:03', NULL, 1000000000);
INSERT INTO `ums_member` VALUES (3, 'oH-MK0V-N4Lotq-kXIMAMjLdXdtY', '$2a$10$T.jSFW6bQyaVLVeu50BipO0cPwdIBnoT/AwJEjEttt4wKwXJeoSsW', 1, '非洲小白脸', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/PiajxSqBRaEIIs1glKcYOadLFibr2et98eXTADdicLUGrQqF8EtvicIu5e5TwOkuBAzIf8zEl0aYPJaDkfIHTOEWuQ/132', 'oH-MK0V-N4Lotq-kXIMAMjLdXdtY', NULL, 1, 0, 0, '2021-01-12 17:52:06', NULL, 1000000000);
INSERT INTO `ums_member` VALUES (4, 'oUBUG5lBhnnn-HBCF9mYMZbQv7A8', '$2a$10$0p7NIjPsLAe8xLnJPmuY1ON6qwhfLvaMv1x4OkYSUhI0d7YVsIk7y', 1, '花花的世界', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLxWhtkFhVKpfXib0BibMaIzeOAVCGVScnR5ibsibdENiaibjvnfy7AxeSSCTbn9IBvqMe1iaJ6BWTxIjZtg/132', 'oUBUG5lBhnnn-HBCF9mYMZbQv7A8', NULL, 1, 0, 0, '2021-01-12 17:52:09', '2021-02-24 22:43:41', 999840100);
INSERT INTO `ums_member` VALUES (5, 'oZ75o5Kk-opj_ioVOj6vTO7-K0HM', '$2a$10$oVCO0XEsiPjvW88lOLEX/edhYBOCpTOPQqLDXO5iDd6Me6nrSei3q', 1, '微尘', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/vNZqQTZRAia4sz17MJeeXeqhzaBbIEzEXGvgwG4l1KQg2mQAb3eB1q9HLnVJUo4u8OSNSv1seuqHxNPKyYicb4Dw/132', 'oZ75o5Kk-opj_ioVOj6vTO7-K0HM', NULL, 1, 0, 0, '2021-01-12 17:52:12', NULL, 1000000000);
INSERT INTO `ums_member` VALUES (6, 'oUBUG5m_9Gfa3bZmzP_tRNnX2vsg', '$2a$10$do/TaAtRGPjOYRAY9.DrJeIKqXFYv1sAH3J11dqNInbdD8OVkLh8m', 1, '大田', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIPhsrhOFictxk44ialrd4gZP6SyFmz4v2rHBZ3C72O0KsKQDTHlVBqtoSJ5uiaPAvD0t9F5VBjbruQw/132', 'oUBUG5m_9Gfa3bZmzP_tRNnX2vsg', NULL, 1, 0, 0, '2021-01-12 17:52:16', NULL, 1000000000);
INSERT INTO `ums_member` VALUES (7, 'oUBUG5tj5LF8IjJDU2s1cqSdthdo', '$2a$10$xnaqCcQe3U1NKCdfT9vtvuTt0GckjJSuwK.oZ47Eh53TilNRG.OG2', 1, '看好路，向前走！', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/lZibiaShtph66QznR6yiarR7VsBkkjqGqPCwqDGD8WlaxnllcjG7SRiaX0DOujFXX8epAbyvFpHv03uI83xXFhdwZA/132', 'oUBUG5tj5LF8IjJDU2s1cqSdthdo', NULL, 1, 0, 0, NULL, NULL, 1000000000);
INSERT INTO `ums_member` VALUES (8, 'oEeWo5IXHzfBqOQRI5mzSAfzoN2A', '$2a$10$Tnqtz1NomNl3X97RB.677.mzBqD8mHzxV9oguXcEBDI2ylDGsJa5S', 1, 'CIAO！', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/0d5kVzsH20SUXzPjbgamFn7DraWURYE7GJX15rMSVVDCeHN3kKW3ZozlUichS7Ch5jXADocWYW3jzBTj24oZVKw/132', 'oEeWo5IXHzfBqOQRI5mzSAfzoN2A', NULL, 1, 0, 0, NULL, NULL, 1000000000);
INSERT INTO `ums_member` VALUES (10, 'oUBUG5mQmw7d5XMwci78J6nDApA0', '$2a$10$FViz4S4h1cvbfoS3cC0tjeB1h3iN1H.N0KmO.mFKn/guCEFItLIVy', 1, '时光会咬人', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJyb4yl7JJCDKNX3yRwGjZ8fdXBTSVaW9cQIErvibmDR08m0vsrqWonxvRibrFxric0wqAKgVFa1IBlg/132', 'oUBUG5mQmw7d5XMwci78J6nDApA0', NULL, 1, 0, 0, '2021-01-30 17:22:18', NULL, 1000000000);
INSERT INTO `ums_member` VALUES (12, 'oUBUG5rX8pS8O__e3Li-owjcCWvg', '$2a$10$JxxjGgPhD4nqn9A0O.aS5OcjUS9MkJf7cZl5B6rWsVxGlbiBOcvUe', 1, 'I', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJGjiauanv1bnlJxKiaBQtalptWZUnCnE725cS8SjWoVAjLDuFLg3sKDfumhKuMs7NGHIc0gz8dNopQ/132', 'oUBUG5rX8pS8O__e3Li-owjcCWvg', NULL, 1, 0, 0, '2021-02-01 19:45:57', '2021-02-01 19:45:57', 1000000000);
INSERT INTO `ums_member` VALUES (14, 'oUBUG5mOf9q54E6ob0kDC_cymoiw', '$2a$10$wxVMHiUrSKsNx247QL5k/.UCTIdtWCnenAewsHlUZ8jraoDA/d/ky', 1, '77777777', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83ercp0SnvuleWloRkX8y5pibLHtg2OoKGECJH7udBoAoicsO87ibjmsUMiaDgJAJ8ibaiavGv1aEQicle8lMA/132', 'oUBUG5mOf9q54E6ob0kDC_cymoiw', NULL, 1, 0, 0, '2021-02-02 19:49:50', '2021-02-02 19:49:50', 1000000000);
INSERT INTO `ums_member` VALUES (15, 'owHt46JBw46D1cCP8kyLefAoB8Ss', '$2a$10$qwPfYTPqyHXlZ.ibTjXjH.lnH9HQyBrpCREStS5mq0hYJcjGzRPzO', 1, 'Max_Qiu', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIspnkuj3p0Ly4v6dIz5nClVLnNvIE5BVyd6ORaz6kLrwsxbicfqnG7ic4JpqWedpqk1lgx71QlHauQ/132', 'owHt46JBw46D1cCP8kyLefAoB8Ss', NULL, 1, 0, 0, '2021-02-04 12:08:16', '2021-02-04 12:08:16', 1000000000);
INSERT INTO `ums_member` VALUES (16, 'oUBUG5i0B3nUFejoqSlNwFlcJ_oc', '$2a$10$ENv5MBIWhgJOGCkOci43DOIywHM1DJsgdyG5wOz2HEi5qmfUXqP5e', 0, '神经蛙', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/Q3auHgzwzM4Z6nX9KwiaJy2momCR0BLGnXF7ibI9WCJNTpqiaWDYS80624vRibsWr1muV2N8qM5wia0n5lSxOvttjNA/132', 'oUBUG5i0B3nUFejoqSlNwFlcJ_oc', NULL, 1, 0, 0, '2021-02-16 15:48:07', '2021-02-16 15:48:07', 1000000000);
INSERT INTO `ums_member` VALUES (17, 'oUBUG5l9OT1HaIZmOKJciVVaiZaA', '$2a$10$c1em4b..NsL40fmyuTu7mOZ21JYkSY1erfnt6.z/j2emtx6dcPFkC', 1, '自渡.', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/jAiavgRK2sHGs29TfZOlfGibBEpkq5btJQcVib2OoOibDTrsbC3d1R2LEtyEN48Cx8pQBZE174k13ribJamUkrD1ctg/132', 'oUBUG5l9OT1HaIZmOKJciVVaiZaA', NULL, 1, 0, 0, '2021-02-17 13:11:54', '2021-02-17 13:11:54', 1000000000);
INSERT INTO `ums_member` VALUES (18, 'oEJcR0faCvzl8xF5fMKPoPBZQwB8', '$2a$10$5pLp97lgmDTqcE8ei5jQPujIaZNyxYEsnn0aA3/aJNHM4OMou5INu', 1, 'lxm', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKCMRO8bKSzryP9QD8DqOHyaVP8ibK41qpviaqNpTN3IDibiapjqLibibIZS7LrQTfiaNV6YNhYn8vzqcviaw/132', 'oEJcR0faCvzl8xF5fMKPoPBZQwB8', NULL, 1, 0, 0, '2021-02-20 16:47:34', '2021-02-20 16:47:34', 1000000000);
INSERT INTO `ums_member` VALUES (19, 'oUBUG5oCupLtC1SaawIk9uotF66U', '$2a$10$3ly1heBv0DhaKy730LICm.yRlvdjbKsMt9cRixwZhhbNAVa32pcy2', 1, '林育挺', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/pHghIy3YR0f1pyWuENRiaqic03azQnbW6YtjyWrfl0bXZjF4J9UA5QPG9jXUe8BymtngqJ0zPwnS0VSPLIBBJEiaw/132', 'oUBUG5oCupLtC1SaawIk9uotF66U', NULL, 1, 0, 0, '2021-02-20 22:12:13', '2021-02-20 22:12:13', 1000000000);
INSERT INTO `ums_member` VALUES (20, 'oUBUG5soM0AUAgP8PM8e0M2X5qxs', '$2a$10$QL4JynZF/U/7sN91dNFMvuLtkVT4IxWnzoy1vhJUBnd0vAFM6QFK.', 1, '香蕉皮i', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/Z1BicLpfe2ygKc91pm1LhKdLKUtFPdyn4lSkVkA5Pn5iaI5lT3h4M4dFAanxGKEMfPIgOCZjxjiaIHLuqq9Fn5E0Q/132', 'oUBUG5soM0AUAgP8PM8e0M2X5qxs', NULL, 1, 0, 0, '2021-02-24 12:09:26', '2021-02-24 12:09:26', 1000000000);

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
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'AT transaction mode undo table' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;

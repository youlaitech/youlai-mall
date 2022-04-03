/*
* 商城会员表
* MySQL5.x版本
*/
use mall_ums;

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for ums_address
-- ----------------------------
DROP TABLE IF EXISTS `ums_address`;
CREATE TABLE `ums_address`  (
                                `id` bigint NOT NULL AUTO_INCREMENT,
                                `member_id` bigint NULL DEFAULT NULL COMMENT '会员ID',
                                `consignee_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收货人姓名',
                                `consignee_mobile` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收货人联系方式',
                                `province` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '省',
                                `city` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '市',
                                `area` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '区',
                                `detail_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '详细地址',
                                `zip_code` char(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮编',
                                `defaulted` tinyint NULL DEFAULT NULL COMMENT '是否默认地址',
                                `gmt_create` datetime NULL DEFAULT NULL COMMENT '创建时间',
                                `gmt_modified` datetime NULL DEFAULT NULL COMMENT '更新时间',
                                PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of ums_address
-- ----------------------------
INSERT INTO `ums_address` VALUES (3, 4, '花韦', '13151572876', '上海', '南京市', '雨花台区', '1号', NULL, 1, NULL, NULL);
INSERT INTO `ums_address` VALUES (4, 4, '花韦', '13151572876', '上海', '南京市', '雨花台区', '1号', NULL, 0, NULL, NULL);
INSERT INTO `ums_address` VALUES (5, 4, 'niuniu', '13151572870', '上海', '上海市', '浦东新区', '1号路1栋', NULL, 0, NULL, NULL);
INSERT INTO `ums_address` VALUES (6, 4, '123', '13150572890', '上海', '上海市', '浦东新区', '123', NULL, 0, NULL, NULL);
INSERT INTO `ums_address` VALUES (7, 4, '123', '13157282800', '上海', '上海市', '浦东新区', '13157282800', NULL, 0, NULL, NULL);
INSERT INTO `ums_address` VALUES (12, 21, '郝先瑞', '17621250365', '上海', '上海市', '浦东新区', '111111', NULL, NULL, '2021-03-22 21:56:58', '2021-03-22 21:56:58');
INSERT INTO `ums_address` VALUES (13, 21, '测试地址', '17621498765', '上海', '上海市', '浦东新区', '111', NULL, NULL, '2021-03-22 21:59:25', '2021-03-22 21:59:25');
INSERT INTO `ums_address` VALUES (14, 22, 'F', '15297828702', '上海', '上海市', '浦东新区', 'qqq', NULL, 1, '2021-03-22 22:12:15', '2021-03-22 22:12:15');
INSERT INTO `ums_address` VALUES (15, 25, '完全', '13415418395', '上海', '上海市', '浦东新区', '钉钉', NULL, NULL, '2021-03-31 14:22:16', '2021-03-31 14:22:16');
INSERT INTO `ums_address` VALUES (16, 31, '郝先瑞', '17612590365', '上海', '上海市', '浦东新区', '111', NULL, 1, '2021-04-14 23:37:46', '2021-04-14 23:37:46');
INSERT INTO `ums_address` VALUES (17, 32, '张三', '13721071025', '上海', '上海市', '浦东新区', '春秋北路', NULL, 1, '2021-04-14 23:46:39', '2021-04-14 23:46:39');
INSERT INTO `ums_address` VALUES (18, 34, 'zs', '13721071025', '上海', '上海市', '浦东新区', '江山大街', NULL, 1, '2021-04-20 01:00:49', '2021-06-04 00:57:59');
INSERT INTO `ums_address` VALUES (19, 35, '秦忠旺', '15589569011', '上海', '烟台市', '莱山区', '天合城40号楼402', NULL, 1, '2021-04-21 10:05:09', '2021-04-21 10:05:09');
INSERT INTO `ums_address` VALUES (20, 39, '郝先瑞', '17621590365', '上海', '上海市', '浦东新区', '111', NULL, 1, '2021-06-10 23:10:30', '2021-06-10 23:10:30');

-- ----------------------------
-- Table structure for ums_member
-- ----------------------------
DROP TABLE IF EXISTS `ums_member`;
CREATE TABLE `ums_member`  (
                               `id` bigint NOT NULL AUTO_INCREMENT,
                               `gender` tinyint(1) NULL DEFAULT NULL,
                               `nick_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                               `mobile` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                               `birthday` date NULL DEFAULT NULL,
                               `avatar_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                               `openid` char(28) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                               `session_key` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                               `status` tinyint(1) NULL DEFAULT 1,
                               `point` int NULL DEFAULT 0 COMMENT '会员积分',
                               `deleted` tinyint(1) NULL DEFAULT 0,
                               `gmt_create` datetime NULL DEFAULT NULL,
                               `gmt_modified` datetime NULL DEFAULT NULL,
                               `balance` bigint NULL DEFAULT 1000000000,
                               `city` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                               `country` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                               `language` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                               `province` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                               PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 41 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of ums_member
-- ----------------------------
INSERT INTO `ums_member` VALUES (2, 1, 'Flamesky', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eorwiaJcRPxKMNHgov0HGBRA8JODQrhw67x61FGEFwic2E2UlhXSKmQ455jqT5RIPsZjmpkdia0pyZdA/132', 'oEMah4qx8utBwve1_5U2bq4z9Ucw', NULL, 1, 0, 0, '2021-01-12 17:52:03', NULL, 1000000000, NULL, NULL, NULL, NULL);
INSERT INTO `ums_member` VALUES (3, 1, '非洲小白脸', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/PiajxSqBRaEIIs1glKcYOadLFibr2et98eXTADdicLUGrQqF8EtvicIu5e5TwOkuBAzIf8zEl0aYPJaDkfIHTOEWuQ/132', 'oH-MK0V-N4Lotq-kXIMAMjLdXdtY', NULL, 1, 0, 0, '2021-01-12 17:52:06', NULL, 1000000000, NULL, NULL, NULL, NULL);
INSERT INTO `ums_member` VALUES (4, 1, '花花的世界', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLxWhtkFhVKpfXib0BibMaIzeOAVCGVScnR5ibsibdENiaibjvnfy7AxeSSCTbn9IBvqMe1iaJ6BWTxIjZtg/132', 'oUBUG5lBhnnn-HBCF9mYMZbQv7A8', NULL, 1, 0, 0, '2021-01-12 17:52:09', '2021-02-24 22:43:41', 999680200, NULL, NULL, NULL, NULL);
INSERT INTO `ums_member` VALUES (5, 1, '微尘', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/vNZqQTZRAia4sz17MJeeXeqhzaBbIEzEXGvgwG4l1KQg2mQAb3eB1q9HLnVJUo4u8OSNSv1seuqHxNPKyYicb4Dw/132', 'oZ75o5Kk-opj_ioVOj6vTO7-K0HM', NULL, 1, 0, 0, '2021-01-12 17:52:12', NULL, 1000000000, NULL, NULL, NULL, NULL);
INSERT INTO `ums_member` VALUES (6, 1, '大田', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIPhsrhOFictxk44ialrd4gZP6SyFmz4v2rHBZ3C72O0KsKQDTHlVBqtoSJ5uiaPAvD0t9F5VBjbruQw/132', 'oUBUG5m_9Gfa3bZmzP_tRNnX2vsg', NULL, 1, 0, 0, '2021-01-12 17:52:16', NULL, 1000000000, NULL, NULL, NULL, NULL);
INSERT INTO `ums_member` VALUES (7, 1, '看好路，向前走！', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/lZibiaShtph66QznR6yiarR7VsBkkjqGqPCwqDGD8WlaxnllcjG7SRiaX0DOujFXX8epAbyvFpHv03uI83xXFhdwZA/132', 'oUBUG5tj5LF8IjJDU2s1cqSdthdo', NULL, 1, 0, 0, NULL, NULL, 1000000000, NULL, NULL, NULL, NULL);
INSERT INTO `ums_member` VALUES (8, 1, 'CIAO！', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/0d5kVzsH20SUXzPjbgamFn7DraWURYE7GJX15rMSVVDCeHN3kKW3ZozlUichS7Ch5jXADocWYW3jzBTj24oZVKw/132', 'oEeWo5IXHzfBqOQRI5mzSAfzoN2A', NULL, 1, 0, 0, NULL, NULL, 1000000000, NULL, NULL, NULL, NULL);
INSERT INTO `ums_member` VALUES (10, 1, '时光会咬人', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJyb4yl7JJCDKNX3yRwGjZ8fdXBTSVaW9cQIErvibmDR08m0vsrqWonxvRibrFxric0wqAKgVFa1IBlg/132', 'oUBUG5mQmw7d5XMwci78J6nDApA0', NULL, 1, 0, 0, '2021-01-30 17:22:18', NULL, 1000000000, NULL, NULL, NULL, NULL);
INSERT INTO `ums_member` VALUES (12, 1, 'I', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJGjiauanv1bnlJxKiaBQtalptWZUnCnE725cS8SjWoVAjLDuFLg3sKDfumhKuMs7NGHIc0gz8dNopQ/132', 'oUBUG5rX8pS8O__e3Li-owjcCWvg', NULL, 1, 0, 0, '2021-02-01 19:45:57', '2021-02-01 19:45:57', 1000000000, NULL, NULL, NULL, NULL);
INSERT INTO `ums_member` VALUES (14, 1, '77777777', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83ercp0SnvuleWloRkX8y5pibLHtg2OoKGECJH7udBoAoicsO87ibjmsUMiaDgJAJ8ibaiavGv1aEQicle8lMA/132', 'oUBUG5mOf9q54E6ob0kDC_cymoiw', NULL, 1, 0, 0, '2021-02-02 19:49:50', '2021-02-02 19:49:50', 1000000000, NULL, NULL, NULL, NULL);
INSERT INTO `ums_member` VALUES (15, 1, 'Max_Qiu', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIspnkuj3p0Ly4v6dIz5nClVLnNvIE5BVyd6ORaz6kLrwsxbicfqnG7ic4JpqWedpqk1lgx71QlHauQ/132', 'owHt46JBw46D1cCP8kyLefAoB8Ss', NULL, 1, 0, 0, '2021-02-04 12:08:16', '2021-02-04 12:08:16', 1000000000, NULL, NULL, NULL, NULL);
INSERT INTO `ums_member` VALUES (16, 0, '神经蛙', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/Q3auHgzwzM4Z6nX9KwiaJy2momCR0BLGnXF7ibI9WCJNTpqiaWDYS80624vRibsWr1muV2N8qM5wia0n5lSxOvttjNA/132', 'oUBUG5i0B3nUFejoqSlNwFlcJ_oc', NULL, 1, 0, 0, '2021-02-16 15:48:07', '2021-02-16 15:48:07', 1000000000, NULL, NULL, NULL, NULL);
INSERT INTO `ums_member` VALUES (17, 1, '自渡.', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/jAiavgRK2sHGs29TfZOlfGibBEpkq5btJQcVib2OoOibDTrsbC3d1R2LEtyEN48Cx8pQBZE174k13ribJamUkrD1ctg/132', 'oUBUG5l9OT1HaIZmOKJciVVaiZaA', NULL, 1, 0, 0, '2021-02-17 13:11:54', '2021-02-17 13:11:54', 1000000000, NULL, NULL, NULL, NULL);
INSERT INTO `ums_member` VALUES (18, 1, 'lxm', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKCMRO8bKSzryP9QD8DqOHyaVP8ibK41qpviaqNpTN3IDibiapjqLibibIZS7LrQTfiaNV6YNhYn8vzqcviaw/132', 'oEJcR0faCvzl8xF5fMKPoPBZQwB8', NULL, 1, 0, 0, '2021-02-20 16:47:34', '2021-02-20 16:47:34', 1000000000, NULL, NULL, NULL, NULL);
INSERT INTO `ums_member` VALUES (19, 1, '林育挺', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/pHghIy3YR0f1pyWuENRiaqic03azQnbW6YtjyWrfl0bXZjF4J9UA5QPG9jXUe8BymtngqJ0zPwnS0VSPLIBBJEiaw/132', 'oUBUG5oCupLtC1SaawIk9uotF66U', NULL, 1, 0, 0, '2021-02-20 22:12:13', '2021-02-20 22:12:13', 1000000000, NULL, NULL, NULL, NULL);
INSERT INTO `ums_member` VALUES (20, 1, '香蕉皮i', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/Z1BicLpfe2ygKc91pm1LhKdLKUtFPdyn4lSkVkA5Pn5iaI5lT3h4M4dFAanxGKEMfPIgOCZjxjiaIHLuqq9Fn5E0Q/132', 'oUBUG5soM0AUAgP8PM8e0M2X5qxs', NULL, 1, 0, 0, '2021-02-24 12:09:26', '2021-02-24 12:09:26', 1000000000, NULL, NULL, NULL, NULL);
INSERT INTO `ums_member` VALUES (22, 1, 'F', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/geyibXYyaoODjLy2aYP54WjUxYz71pwOMDrnRWBXibgh2gDr4hZuw5qiawic75oacEXYxRicykCRINube7MFd9ANicrw/132', 'oUBUG5glvFzDxwkFEK7MUsRcBqhI', NULL, 1, 0, 0, '2021-03-22 21:37:51', '2021-03-22 21:37:51', 999060500, NULL, NULL, NULL, NULL);
INSERT INTO `ums_member` VALUES (23, 1, '蓝动', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/9cfWYQjJMKsplTQQLJqR3A75j9Hib44jHF0vIEJqfHC2ttfg0GCiaSzQbSQVVxrgicAJallo3eB2qsGyE1Z4RNYCQ/132', 'oUBUG5nOlD91HYvXRhCqKrMSWzUs', NULL, 1, 0, 0, '2021-03-23 17:35:03', '2021-03-23 17:35:03', 1000000000, NULL, NULL, NULL, NULL);
INSERT INTO `ums_member` VALUES (24, 1, '路亚小生', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/tnib4ZCXWGOznmtyoHBL5BFicYZWICNyic0EyPWk70kr9IWzHSCVdIqFKN2o7BxyuYaDib0ogmfpuMTBgo3pOibPt9A/132', 'oUBUG5l8zS6fstLbQh4GNf81w438', NULL, 1, 0, 0, '2021-03-24 12:06:23', '2021-03-24 12:06:23', 1000000000, NULL, NULL, NULL, NULL);
INSERT INTO `ums_member` VALUES (25, 1, 'Alan', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/QohQ9hnZnxF2mJOM1RywBPqToNVicDpeF8KdXrwmtYnRyoWaBHk0R25T1wxzleCJV3Un8iappa70yn8fJmgGAZnQ/132', 'oUBUG5kQ2YvWcX7OIYpw8owuWGqE', NULL, 1, 0, 0, '2021-03-29 15:57:24', '2021-03-29 15:57:24', 999380300, NULL, NULL, NULL, NULL);
INSERT INTO `ums_member` VALUES (39, 1, '郝先瑞', '17621590365', NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/J31cY2qVWviaOqhjPlr18VY5ic1SUvDESG1mQkicQfFugWibYe7VJIhYJBZYDBib0T4TJVhUOtiaW1TGkJRqIWd3K0dQ/132', 'oUBUG5hAB_8EMrSaqd2HjJQBFg74', 'dR9xGLsS+hr98pLqt621hw==', 1, 0, 0, '2021-06-10 01:55:41', '2021-06-10 02:04:31', 994999400, '浦东新区', '中国', 'zh_CN', '上海');
INSERT INTO `ums_member` VALUES (40, 1, '秋城', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/ajNVdqHZLLDfyM5iaYFwhzQ1Xv9zyA3bXDV42niazQlibiajdXba0YK4yAFFWIMY7vwfI1ny8Ej8pm0pmp7OkC2afg/132', 'oUBUG5oPQnarJi7g2mXE_svcHVeU', NULL, 1, 0, 0, '2021-06-19 16:57:51', '2021-06-19 16:57:51', 1000000000, '张家口', '中国', 'zh_CN', '河北');

-- ----------------------------
-- Table structure for undo_log
-- ----------------------------
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log`  (
                             `branch_id` bigint NOT NULL COMMENT 'branch transaction id',
                             `xid` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'global transaction id',
                             `context` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'undo_log context,such as serialization',
                             `rollback_info` longblob NOT NULL COMMENT 'rollback info',
                             `log_status` int NOT NULL COMMENT '0:normal status,1:defense status',
                             `log_created` datetime(6) NOT NULL COMMENT 'create datetime',
                             `log_modified` datetime(6) NOT NULL COMMENT 'modify datetime',
                             UNIQUE INDEX `ux_undo_log`(`xid`, `branch_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'AT transaction mode undo table' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of undo_log
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
-- 商城会员数据库
-- ----------------------------
CREATE DATABASE IF NOT EXISTS youlai_mall_ums DEFAULT CHARACTER SET utf8mb4 DEFAULT COLLATE utf8mb4_unicode_ci;

-- ----------------------------
-- 创建表 && 数据初始化
-- ----------------------------
use youlai_mall_ums;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for ums_address
-- ----------------------------
DROP TABLE IF EXISTS `ums_address`;
CREATE TABLE `ums_address`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `member_id` bigint COMMENT '会员ID',
  `recipient_name` varchar(64) COMMENT '收货人姓名',
  `recipient_mobile` varchar(20) COMMENT '收货人联系方式',
  `province` varchar(64) COMMENT '省',
  `city` varchar(64) COMMENT '市',
  `district` varchar(64) COMMENT '区',
  `street` varchar(255) COMMENT '街道地址',
  `postal_code` char(6) COMMENT '邮编',
  `is_default` tinyint COMMENT '是否默认地址',
  `create_time` datetime COMMENT '创建时间',
  `update_time` datetime COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4;

-- ----------------------------
-- Records of ums_address
-- ----------------------------
INSERT INTO `ums_address` VALUES (1, 1, '有来技术', '18866668888', '上海', '上海市', '浦东新区', '111111', NULL, NULL, '2021-03-22 21:56:58', '2021-03-22 21:56:58');

-- ----------------------------
-- Table structure for ums_member
-- ----------------------------
DROP TABLE IF EXISTS `ums_member`;
CREATE TABLE `ums_member`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `gender` tinyint(1),
  `nick_name` varchar(50),
  `mobile` varchar(20),
  `birthday` date,
  `avatar_url` varchar(255),
  `openid` char(28),
  `session_key` varchar(32),
  `status` tinyint(1) DEFAULT 1,
  `point` int DEFAULT  0 COMMENT '会员积分',
  `is_deleted` tinyint(1) DEFAULT 0,
  `create_time` datetime,
  `update_time` datetime,
  `balance` bigint DEFAULT  1000000000,
  `city` varchar(32),
  `country` varchar(32),
  `language` varchar(10),
  `province` varchar(32),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 76 CHARACTER SET = utf8mb4 ;

-- ----------------------------
-- Records of ums_member
-- ----------------------------
INSERT INTO `ums_member` VALUES (1, 1, '郝先瑞', '18866668888', NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/J31cY2qVWviaOqhjPlr18VY5ic1SUvDESG1mQkicQfFugWibYe7VJIhYJBZYDBib0T4TJVhUOtiaW1TGkJRqIWd3K0dQ/132', 'oUBUG5hAB_8EMrSaqd2HjJQBFg74', NULL, 1, 0, 0, '2022-02-26 20:59:20', '2022-02-26 20:59:20', 812659400, '', '', 'zh_CN', '');
INSERT INTO `ums_member` VALUES (2, 1, 'Flamesky', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eorwiaJcRPxKMNHgov0HGBRA8JODQrhw67x61FGEFwic2E2UlhXSKmQ455jqT5RIPsZjmpkdia0pyZdA/132', 'oEMah4qx8utBwve1_5U2bq4z9Ucw', NULL, 1, 0, 0, '2021-01-12 17:52:03', NULL, 1000000000, NULL, NULL, NULL, NULL);
INSERT INTO `ums_member` VALUES (3, 1, '非洲小白脸', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/PiajxSqBRaEIIs1glKcYOadLFibr2et98eXTADdicLUGrQqF8EtvicIu5e5TwOkuBAzIf8zEl0aYPJaDkfIHTOEWuQ/132', 'oH-MK0V-N4Lotq-kXIMAMjLdXdtY', NULL, 1, 0, 0, '2021-01-12 17:52:06', NULL, 1000000000, NULL, NULL, NULL, NULL);
INSERT INTO `ums_member` VALUES (4, 1, '花花的世界', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLxWhtkFhVKpfXib0BibMaIzeOAVCGVScnR5ibsibdENiaibjvnfy7AxeSSCTbn9IBvqMe1iaJ6BWTxIjZtg/132', 'oUBUG5lBhnnn-HBCF9mYMZbQv7A8', NULL, 1, 0, 0, '2021-01-12 17:52:09', '2021-02-24 22:43:41', 999680200, NULL, NULL, NULL, NULL);
INSERT INTO `ums_member` VALUES (5, 1, '微尘', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/vNZqQTZRAia4sz17MJeeXeqhzaBbIEzEXGvgwG4l1KQg2mQAb3eB1q9HLnVJUo4u8OSNSv1seuqHxNPKyYicb4Dw/132', 'oZ75o5Kk-opj_ioVOj6vTO7-K0HM', NULL, 1, 0, 0, '2021-01-12 17:52:12', NULL, 1000000000, NULL, NULL, NULL, NULL);
INSERT INTO `ums_member` VALUES (6, 1, '大田', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIPhsrhOFictxk44ialrd4gZP6SyFmz4v2rHBZ3C72O0KsKQDTHlVBqtoSJ5uiaPAvD0t9F5VBjbruQw/132', 'oUBUG5m_9Gfa3bZmzP_tRNnX2vsg', NULL, 1, 0, 0, '2021-01-12 17:52:16', NULL, 1000000000, NULL, NULL, NULL, NULL);
INSERT INTO `ums_member` VALUES (7, 1, '看好路，向前走！', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/lZibiaShtph66QznR6yiarR7VsBkkjqGqPCwqDGD8WlaxnllcjG7SRiaX0DOujFXX8epAbyvFpHv03uI83xXFhdwZA/132', 'oUBUG5tj5LF8IjJDU2s1cqSdthdo', NULL, 1, 0, 0, NULL, NULL, 1000000000, NULL, NULL, NULL, NULL);
INSERT INTO `ums_member` VALUES (8, 1, 'CIAO！', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/0d5kVzsH20SUXzPjbgamFn7DraWURYE7GJX15rMSVVDCeHN3kKW3ZozlUichS7Ch5jXADocWYW3jzBTj24oZVKw/132', 'oEeWo5IXHzfBqOQRI5mzSAfzoN2A', NULL, 1, 0, 0, NULL, NULL, 1000000000, NULL, NULL, NULL, NULL);
INSERT INTO `ums_member` VALUES (10, 1, '时光会咬人', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJyb4yl7JJCDKNX3yRwGjZ8fdXBTSVaW9cQIErvibmDR08m0vsrqWonxvRibrFxric0wqAKgVFa1IBlg/132', 'oUBUG5mQmw7d5XMwci78J6nDApA0', NULL, 1, 0, 0, '2021-01-30 17:22:18', NULL, 1000000000, NULL, NULL, NULL, NULL);
INSERT INTO `ums_member` VALUES (14, 1, '77777777', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83ercp0SnvuleWloRkX8y5pibLHtg2OoKGECJH7udBoAoicsO87ibjmsUMiaDgJAJ8ibaiavGv1aEQicle8lMA/132', 'oUBUG5mOf9q54E6ob0kDC_cymoiw', NULL, 1, 0, 0, '2021-02-02 19:49:50', '2021-02-02 19:49:50', 1000000000, NULL, NULL, NULL, NULL);
INSERT INTO `ums_member` VALUES (15, 1, 'Max_Qiu', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIspnkuj3p0Ly4v6dIz5nClVLnNvIE5BVyd6ORaz6kLrwsxbicfqnG7ic4JpqWedpqk1lgx71QlHauQ/132', 'owHt46JBw46D1cCP8kyLefAoB8Ss', NULL, 1, 0, 0, '2021-02-04 12:08:16', '2021-02-04 12:08:16', 1000000000, NULL, NULL, NULL, NULL);
INSERT INTO `ums_member` VALUES (16, 0, '神经蛙', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/Q3auHgzwzM4Z6nX9KwiaJy2momCR0BLGnXF7ibI9WCJNTpqiaWDYS80624vRibsWr1muV2N8qM5wia0n5lSxOvttjNA/132', 'oUBUG5i0B3nUFejoqSlNwFlcJ_oc', NULL, 1, 0, 0, '2021-02-16 15:48:07', '2021-02-16 15:48:07', 1000000000, NULL, NULL, NULL, NULL);
INSERT INTO `ums_member` VALUES (17, 1, '自渡.', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/jAiavgRK2sHGs29TfZOlfGibBEpkq5btJQcVib2OoOibDTrsbC3d1R2LEtyEN48Cx8pQBZE174k13ribJamUkrD1ctg/132', 'oUBUG5l9OT1HaIZmOKJciVVaiZaA', NULL, 1, 0, 0, '2021-02-17 13:11:54', '2021-02-17 13:11:54', 1000000000, NULL, NULL, NULL, NULL);
INSERT INTO `ums_member` VALUES (18, 1, 'lxm', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKCMRO8bKSzryP9QD8DqOHyaVP8ibK41qpviaqNpTN3IDibiapjqLibibIZS7LrQTfiaNV6YNhYn8vzqcviaw/132', 'oEJcR0faCvzl8xF5fMKPoPBZQwB8', NULL, 1, 0, 0, '2021-02-20 16:47:34', '2021-02-20 16:47:34', 1000000000, NULL, NULL, NULL, NULL);
INSERT INTO `ums_member` VALUES (19, 1, '林育挺', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/pHghIy3YR0f1pyWuENRiaqic03azQnbW6YtjyWrfl0bXZjF4J9UA5QPG9jXUe8BymtngqJ0zPwnS0VSPLIBBJEiaw/132', 'oUBUG5oCupLtC1SaawIk9uotF66U', NULL, 1, 0, 0, '2021-02-20 22:12:13', '2021-02-20 22:12:13', 1000000000, NULL, NULL, NULL, NULL);
INSERT INTO `ums_member` VALUES (20, 1, '香蕉皮i', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/Z1BicLpfe2ygKc91pm1LhKdLKUtFPdyn4lSkVkA5Pn5iaI5lT3h4M4dFAanxGKEMfPIgOCZjxjiaIHLuqq9Fn5E0Q/132', 'oUBUG5soM0AUAgP8PM8e0M2X5qxs', NULL, 1, 0, 0, '2021-02-24 12:09:26', '2021-02-24 12:09:26', 1000000000, NULL, NULL, NULL, NULL);
INSERT INTO `ums_member` VALUES (23, 1, '蓝动', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/9cfWYQjJMKsplTQQLJqR3A75j9Hib44jHF0vIEJqfHC2ttfg0GCiaSzQbSQVVxrgicAJallo3eB2qsGyE1Z4RNYCQ/132', 'oUBUG5nOlD91HYvXRhCqKrMSWzUs', NULL, 1, 0, 0, '2021-03-23 17:35:03', '2021-03-23 17:35:03', 1000000000, NULL, NULL, NULL, NULL);
INSERT INTO `ums_member` VALUES (24, 1, '路亚小生', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/tnib4ZCXWGOznmtyoHBL5BFicYZWICNyic0EyPWk70kr9IWzHSCVdIqFKN2o7BxyuYaDib0ogmfpuMTBgo3pOibPt9A/132', 'oUBUG5l8zS6fstLbQh4GNf81w438', NULL, 1, 0, 0, '2021-03-24 12:06:23', '2021-03-24 12:06:23', 1000000000, NULL, NULL, NULL, NULL);
INSERT INTO `ums_member` VALUES (25, 1, 'Alan', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/QohQ9hnZnxF2mJOM1RywBPqToNVicDpeF8KdXrwmtYnRyoWaBHk0R25T1wxzleCJV3Un8iappa70yn8fJmgGAZnQ/132', 'oUBUG5kQ2YvWcX7OIYpw8owuWGqE', NULL, 1, 0, 0, '2021-03-29 15:57:24', '2021-03-29 15:57:24', 999380300, NULL, NULL, NULL, NULL);
INSERT INTO `ums_member` VALUES (40, 1, '秋城', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/ajNVdqHZLLDfyM5iaYFwhzQ1Xv9zyA3bXDV42niazQlibiajdXba0YK4yAFFWIMY7vwfI1ny8Ej8pm0pmp7OkC2afg/132', 'oUBUG5oPQnarJi7g2mXE_svcHVeU', NULL, 1, 0, 0, '2021-06-19 16:57:51', '2021-06-19 16:57:51', 1000000000, '张家口', '中国', 'zh_CN', '河北');
INSERT INTO `ums_member` VALUES (72, 0, 'Benji', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/WjvlGaEv9ajwHfpEGWd4KwjloXjCgvibMibPQiaL7I4ausEzp0rH0AHqKpJeERS2UHiaLSlftOYCj77nLEVNtXZqcQ/132', 'oBdKY4iltAU9HBHUBC-S8p3H1vv4', NULL, 1, 0, 0, '2022-03-13 04:23:33', '2022-03-13 04:23:33', 1000000000, '', '', 'zh_CN', '');
INSERT INTO `ums_member` VALUES (73, 0, '_六月流星', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/gZlE19RpbIuqmicAdL8wC7u26gx6LhRwsicc3icFGYA8TNvy6RJnyGUMTbWkyhg1lJ4yolnOVqCs6gI7Oiaby8lqSQ/132', 'oUBUG5rsrAjW173G25vgL2hq6AZk', NULL, 1, 0, 0, '2022-03-14 16:17:33', '2022-03-14 16:17:33', 1000000000, '', '', 'zh_CN', '');
INSERT INTO `ums_member` VALUES (74, 0, 'zhang', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIOca9icPByKxn3Z6AZAL3l0xfmOOqbRQVn1f2qoqtOYw3bJliawrTvu4F9Tg2aAHicASicXrW74zVUYA/132', 'oYCwr5IaYCS7web81wifWivPEMGw', NULL, 1, 0, 0, '2022-03-16 11:18:36', '2022-03-16 11:18:36', 1000000000, '', '', 'zh_CN', '');
INSERT INTO `ums_member` VALUES (75, 0, '德才-Edward', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/4DgXefgtM24MXSMPomsvuwCiav9v77bA5Ou8S74qrlAALfNPMEqD938jrNVybP5sJzfaKyqHrYcicbIwU8Xcc1YA/132', 'oRJIA43TMiAw76HRFEMli4BSvX6c', NULL, 1, 0, 0, '2022-04-06 18:14:07', '2022-04-06 18:14:07', 1000000000, '', '', 'zh_CN', '');
INSERT INTO `ums_member` VALUES (76, 0, '小乐有点笨', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/gTdG3Zs0OMqruJpGAKu9mZxo4K9WjEzmG759HiaBS5I71LVxktv2zyqNcKiaxeNF3JTSn1epfU3gUz8hZWdqhfPg/132', 'oTAHp5OexvW68nRkn5ISV0zKvEGE', NULL, 1, 0, 0, '2022-04-22 18:55:39', '2022-04-22 18:55:39', 1000000000, '', '', 'zh_CN', '');

-- ----------------------------
-- Table structure for undo_log
-- ----------------------------
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log`  (
  `branch_id` bigint NOT NULL COMMENT 'branch transaction id',
  `xid` varchar(100) NOT NULL COMMENT 'global transaction id',
  `context` varchar(128) NOT NULL COMMENT 'undo_log context,such as serialization',
  `rollback_info` longblob NOT NULL COMMENT 'rollback info',
  `log_status` int NOT NULL COMMENT '0:normal status,1:defense status',
  `log_created` datetime(6) NOT NULL COMMENT 'create datetime',
  `log_modified` datetime(6) NOT NULL COMMENT 'modify datetime',
  UNIQUE INDEX `ux_undo_log`(`xid`, `branch_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COMMENT = 'AT transaction mode undo table';

-- ----------------------------
-- Records of undo_log
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;

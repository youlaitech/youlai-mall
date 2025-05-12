
-- ----------------------------
-- 商城商品数据库
-- ----------------------------
CREATE DATABASE IF NOT EXISTS youlai_mall_pms DEFAULT CHARACTER SET utf8mb4 DEFAULT COLLATE utf8mb4_unicode_ci;

-- ----------------------------
-- 创建表 && 数据初始化
-- ----------------------------
use youlai_mall_pms;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;


-- ----------------------------
-- Table structure for pms_brand
-- ----------------------------
DROP TABLE IF EXISTS `pms_brand`;
CREATE TABLE `pms_brand`  (
                            `id` bigint NOT NULL AUTO_INCREMENT COMMENT '品牌ID',
                            `name` varchar(64)  NOT NULL COMMENT '品牌名称',
                            `first_letter` char(1)  NULL DEFAULT NULL COMMENT '检索首字母',
                            `is_visible` tinyint NULL DEFAULT NULL COMMENT '是否显示[0-隐藏，1-显示]',
                            `sort` smallint NULL DEFAULT 1 COMMENT '排序',
                            `logo_url` varchar(255)  NULL DEFAULT NULL COMMENT 'Logo URL',
                            `description` varchar(255)  NULL DEFAULT NULL COMMENT '品牌描述',
                            `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
                            `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
                            `is_deleted` tinyint NOT NULL DEFAULT 0 COMMENT '逻辑删除标识[0-未删除，1-已删除]',
                            PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 34  COMMENT = '品牌表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pms_brand
-- ----------------------------
INSERT INTO `pms_brand` VALUES (1, '有来', 'Y', 1, 1, 'http://localhost:9010/default/20250419/0c4690e6df334356ad717593ff67580b.jpg', NULL, '2021-07-11 19:56:58', '2025-04-19 19:41:06', 0);
INSERT INTO `pms_brand` VALUES (10, '小米', 'X', 1, 2, 'http://a.youlai.tech:9000/default/6a5a606fc60742919149a7861bf26cd5.jpg', NULL, '2022-03-05 16:12:16', '2022-03-05 16:12:16', 0);
INSERT INTO `pms_brand` VALUES (11, '华硕', 'H', 1, 3, 'http://a.youlai.tech:9000/default/f18083f95e104a0bae3c587dee3bb2ed.png', NULL, '2022-03-05 16:12:16', '2022-03-05 16:12:16', 0);
INSERT INTO `pms_brand` VALUES (20, '华为', 'H', 1, 1, 'https://oss.youlai.tech/default/ff61bd639b23491d8f2aa85d09fcf788.jpg', NULL, '2022-05-06 23:08:33', '2022-05-06 23:08:33', 0);
INSERT INTO `pms_brand` VALUES (33, '惠普', 'H', 1, 1, 'https://oss.youlai.tech/default/4cf579add9544c6eaafb41ce1131559e.gif', NULL, '2022-07-07 00:12:16', '2022-07-07 00:12:16', 0);

-- ----------------------------
-- Table structure for pms_category
-- ----------------------------
DROP TABLE IF EXISTS `pms_category`;
CREATE TABLE `pms_category`  (
                               `id` bigint NOT NULL AUTO_INCREMENT COMMENT '分类ID',
                               `name` varchar(64)  NOT NULL COMMENT '分类名称',
                               `parent_id` bigint NOT NULL COMMENT '父级ID',
                               `tree_path` varchar(100)  NULL DEFAULT NULL COMMENT '层级路径',
                               `icon_url` varchar(255)  NULL DEFAULT NULL COMMENT '图标地址',
                               `sort` int NULL DEFAULT 1 COMMENT '排序',
                               `is_visible` tinyint(1) NULL DEFAULT 1 COMMENT '是否显示[1-显示，0-隐藏]',
                               `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
                               `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
                               `is_deleted` tinyint NOT NULL DEFAULT 0 COMMENT '逻辑删除标识[0-未删除，1-已删除]',
                               PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 218  COMMENT = '商品分类表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pms_category
-- ----------------------------
INSERT INTO `pms_category` VALUES (1, '手机', 0, '0', NULL, 1, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (2, '手机通讯', 1, '0,1', NULL, 2, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (3, '智能手机', 2, '0,1,2', 'https://m.360buyimg.com/n2/jfs/t1/178088/18/6103/40858/60ae120bE8778c70f/7471a46e8181a0ec.jpg', 3, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (4, '游戏手机', 2, '0,1,2', 'https://m.360buyimg.com/n2/jfs/t1/178088/18/6103/40858/60ae120bE8778c70f/7471a46e8181a0ec.jpg', 4, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (5, '拍照手机', 2, '0,1,2', 'https://m.360buyimg.com/n2/jfs/t1/178088/18/6103/40858/60ae120bE8778c70f/7471a46e8181a0ec.jpg', 5, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (23, '手机配件', 1, '0,1', NULL, 6, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (24, '手机耳机', 23, '0,1,23', 'https://m.360buyimg.com/n2/jfs/t1/206917/11/8226/66945/6184e893E5d49b030/9fa0ff3ea1c0f225.jpg', 7, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (25, '蓝牙耳机', 23, '0,1,23', 'https://m.360buyimg.com/n2/jfs/t1/130927/35/4485/194202/5f0ec040Ea97208b8/0eae5c7b0b297997.jpg', 8, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (26, '手机壳/保护套', 23, '0,1,23', 'https://m.360buyimg.com/n2/jfs/t1/120377/21/4107/341055/5ed85e6dE336adeea/2c772c9d717511e9.png', 9, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (27, '手机贴膜', 23, '0,1,23', 'https://m.360buyimg.com/n2/jfs/t1/160485/24/20708/173705/607ecbabE197a1e08/c3b555933f691fa6.jpg', 10, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (28, '运营商', 1, '0,1', NULL, 11, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (29, '办号卡', 28, '0,1,28', 'https://m.360buyimg.com/n2/jfs/t1/212672/25/6617/327229/61aa3c03E9b9ec15f/0442811781abfa3f.png', 12, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (30, '合约机', 28, '0,1,28', 'https://m.360buyimg.com/n2/jfs/t1/178088/18/6103/40858/60ae120bE8778c70f/7471a46e8181a0ec.jpg', 13, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (31, '选号中心', 28, '0,1,28', 'https://m.360buyimg.com/n2/jfs/t1/162272/13/3732/87442/6008d8a9Ee3b2d2a9/a9c704fea3f3d614.jpg', 14, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (32, '办套餐', 28, '0,1,28', 'https://m.360buyimg.com/n2/jfs/t1/220433/36/5663/349755/619f422fEfff5cf6f/9fa248b09a4efd3f.jpg', 15, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (33, '装宽带', 28, '0,1,28', 'https://m.360buyimg.com/n2/jfs/t1/153760/39/13721/29797/5ff67a83E3418fcb0/b096d1562f4a09bc.png', 16, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (34, '手机服务', 1, '0,1', NULL, 17, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (35, '电池换新', 34, '0,1,34', 'https://m.360buyimg.com/n2/jfs/t1/201179/36/6163/687995/6139ea73E848221ba/2f00ea7f90a2421e.png', 18, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (36, '手机故障', 34, '0,1,34', 'https://m.360buyimg.com/n2/jfs/t1/119422/38/7484/85445/5ed4772fE938d1579/c80feb43768d2df9.png', 19, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (37, '保障服务', 34, '0,1,34', 'https://m.360buyimg.com/n2/jfs/t1/180855/25/5073/53913/60a5c434Efef4378a/9d4e4bf3f435136b.jpg', 20, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (38, '家用电器', 0, '0', NULL, 21, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (39, '个护健康', 38, '0,38', NULL, 22, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (40, '剃须刀', 39, '0,38,39', 'https://m.360buyimg.com/n2/jfs/t1/111806/7/6224/135242/5eb8ab4cE5e11f458/7f7c4cee0f56c026.png', 23, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (41, '电吹风', 39, '0,38,39', 'https://m.360buyimg.com/n2/jfs/t1/23018/23/4644/40085/5c330e8aEb906ed16/94f3022b373bf991.jpg', 24, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (42, '电动牙刷', 39, '0,38,39', 'https://m.360buyimg.com/n2/jfs/t1/924/5/6171/84389/5ba0d23eE29525bad/831de1d140d64324.jpg', 25, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (43, '剃/脱毛器', 39, '0,38,39', 'https://m.360buyimg.com/n2/jfs/t1/21909/19/15282/67307/5cada1a9E91d3c650/8357525300892955.jpg', 26, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (44, '美容器', 39, '0,38,39', 'https://m.360buyimg.com/n2/jfs/t1/146229/33/25122/90830/61a61900E8b12f06e/ede18da7f2b2464e.jpg', 27, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (45, '足浴盆', 39, '0,38,39', 'https://m.360buyimg.com/n2/jfs/t1/195392/11/18575/99164/611c7898E753d700b/fcef61c165630da1.jpg', 28, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (46, '生活电器', 38, '0,38', NULL, 29, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (47, '吸尘器', 46, '0,38,46', 'https://m.360buyimg.com/n2/jfs/t1/87733/18/16825/166536/5e805074E26d4452e/c5c065ffaa0210b9.jpg', 30, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (48, '取暖器', 46, '0,38,46', 'https://m.360buyimg.com/n2/jfs/t1/211573/33/672/69416/6141bfa9Ee58f5672/6fae52befc5894f0.jpg', 31, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (49, '加湿器', 46, '0,38,46', 'https://m.360buyimg.com/n2/jfs/t1/155798/29/24190/102698/618ddcafE56de10a2/ffdc73b81c11103a.jpg', 32, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (50, '空气净化器', 46, '0,38,46', 'https://m.360buyimg.com/n2/jfs/t1/92412/37/13642/189343/5e57302aEf8b210ab/77593981fd6dc3e7.png', 33, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (51, '饮水机', 46, '0,38,46', 'https://m.360buyimg.com/n2/jfs/t1/98849/20/14506/136527/5e644d30Ef58bac63/520c7cc48c690660.png', 34, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (52, '干衣机', 46, '0,38,46', 'https://m.360buyimg.com/n2/jfs/t1/156235/36/63/99785/5fd46d24Ec2c1eb5d/6a17096a998b264e.jpg', 35, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (53, '厨房小电', 38, '0,38', NULL, 36, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (54, '电饭煲', 53, '0,38,53', 'https://m.360buyimg.com/n2/jfs/t1/198635/37/19113/111052/61a88cafE8c6b1efd/72ab8492e054f97b.jpg', 37, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (55, '多用途锅', 53, '0,38,53', 'https://m.360buyimg.com/n2/jfs/t1/1003/34/268/263837/5b90c900Ebfa45a56/bb2f0b531efa2748.jpg', 38, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (56, '料理机', 53, '0,38,53', 'https://m.360buyimg.com/n2/jfs/t1/179560/24/2721/53462/6094ef9eEcc91e624/86af729ea287b806.jpg', 39, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (57, '豆浆机', 53, '0,38,53', 'https://m.360buyimg.com/n2/jfs/t27319/289/322424139/158501/c147e059/5b8e57a6N70fc8cd7.jpg', 40, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (58, '电磁炉', 53, '0,38,53', 'https://m.360buyimg.com/n2/jfs/t1/7019/5/7650/123532/5be3a032Ea98f04c8/aa9be3dc737a6a0d.jpg', 41, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (59, '微波炉', 53, '0,38,53', 'https://m.360buyimg.com/n2/jfs/t1/87016/13/13418/303575/5e5732a9Ee1499029/f5988daaa9fe3be6.png', 42, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (60, '电饼铛', 53, '0,38,53', 'https://m.360buyimg.com/n2/jfs/t1/118020/36/1123/275741/5e9521d3Ebb729427/5263ec33aa17c394.png', 43, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (61, '面包机', 53, '0,38,53', 'https://m.360buyimg.com/n2/jfs/t1/689/35/13471/416696/5bd6b22bE9302f10c/ac4cc60490b5745a.jpg', 44, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (62, '果蔬净化清洗机', 53, '0,38,53', 'https://m.360buyimg.com/n2/jfs/t1/129297/10/6713/300795/5f07d05cE5aadb859/133e411945d53c4e.jp', 45, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (63, '煮蛋器', 53, '0,38,53', 'https://m.360buyimg.com/n2/jfs/t1/188265/5/21502/66882/61304c0bE01520e0d/2eef51f48d6c0cf9.jpg', 46, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (64, '电炖锅', 53, '0,38,53', 'https://m.360buyimg.com/n2/jfs/t1/169069/31/21640/281425/61308fa2E15b636f7/51a04acd12e164b1.jpg', 47, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (65, '养生壶', 53, '0,38,53', 'https://m.360buyimg.com/n2/jfs/t1/114491/8/8395/346376/5ecf22e4E5cd62935/f8362dd19d4e5c70.png', 48, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (66, '大 家 电', 38, '0,38', NULL, 49, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (67, '平板电视', 66, '0,38,66', 'https://m.360buyimg.com/n2/jfs/t1/192770/29/7851/123130/60c5ab65Ede341670/957fefcd7aea4944.jpg', 50, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (68, '空调', 66, '0,38,66', 'https://m.360buyimg.com/n2/jfs/t1/162683/40/23245/88224/61407766E57726d97/ef8203d216d0fe99.png', 51, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (69, '冰箱', 66, '0,38,66', 'https://m.360buyimg.com/n2/jfs/t1/158902/15/6386/117369/601e0c0cE07a801c4/0b65cf93dee18e35.jpg', 52, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (70, '洗衣机', 66, '0,38,66', 'https://m.360buyimg.com/n2/jfs/t1/162207/29/11606/108318/60487f15E05196633/9dc849beda2b9ecf.jpg', 53, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (71, '冷柜/冰吧', 66, '0,38,66', 'https://m.360buyimg.com/n2/jfs/t1/194494/6/7259/137017/60c175dcEb0c7d501/0e9622e5629cde85.jpg', 54, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (72, '厨卫大电', 38, '0,38', NULL, 55, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (73, '油烟机', 72, '0,38,72', 'https://m.360buyimg.com/n2/jfs/t1/63075/40/5178/414628/5d3551a4E1abd0749/dc396ff3ec7f2e40.jpg', 56, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (74, '消毒柜', 72, '0,38,72', 'https://m.360buyimg.com/n2/jfs/t1/86163/31/13550/196051/5e5c622fE38026796/5750f728f18cddff.png', 57, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (75, '洗碗机', 72, '0,38,72', 'https://m.360buyimg.com/n2/jfs/t1/86716/8/14218/379572/5e644d3fE65c0be47/2866ef0b5a9f2e2f.png', 58, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (76, '燃气灶', 72, '0,38,72', 'https://m.360buyimg.com/n2/jfs/t1/10279/18/5582/133914/5c18c360E4b2089c9/391c910f23a4f2f5.jpg', 59, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (77, '商用电器', 38, '0,38', NULL, 60, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (78, '商用烤箱', 77, '0,38,77', 'https://m.360buyimg.com/n2/jfs/t1/200342/21/3939/146637/611f2707Ea099625f/1dedb277373069a3.jpg', 61, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (79, '醒发箱', 77, '0,38,77', 'https://m.360buyimg.com/n2/jfs/t1/198137/6/11999/91654/615ffb7dE527fdbe5/e3ba90a5658d794b.jpg', 62, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (80, '商用电饼铛', 77, '0,38,77', 'https://m.360buyimg.com/n2/jfs/t1/59173/4/16826/82895/6135a8faE646794ea/6e0fa5d5ac168d9d.jpg', 63, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (81, '肠粉机', 77, '0,38,77', 'https://m.360buyimg.com/n2/jfs/t1/104362/18/15364/341244/5e71de02Edef01042/af5d9db87e022028.jpg', 64, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (82, '保温售饭台', 77, '0,38,77', 'https://m.360buyimg.com/n2/jfs/t1/120779/6/6476/198887/5f03441eE0edc3eec/2c47e836ae440048.jpg', 65, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (83, '商用开水器', 77, '0,38,77', 'https://m.360buyimg.com/n2/jfs/t1/156615/8/73/92699/5fd466a4E4eba1fc8/f17b10b8f5338ce4.jpg', 66, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (84, '果糖机', 77, '0,38,77', 'https://m.360buyimg.com/n2/jfs/t1/12279/34/1853/168915/5c16fc12Eb4dfe5ef/3a7e0418a8a74a76.jpg', 67, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (85, '香肠/热狗机', 77, '0,38,77', 'https://m.360buyimg.com/n2/jfs/t1/96433/33/13285/234434/5e5730b2Efb8d5151/3cfc58d26753dda6.png', 68, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (86, '电动餐车', 77, '0,38,77', 'https://m.360buyimg.com/n2/jfs/t1/211267/35/7962/38621/61864517Ee3f50fd2/b8861ef0fd029095.jpg', 69, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (87, '商用绞肉机/切肉机', 77, '0,38,77', 'https://m.360buyimg.com/n2/jfs/t1/216048/23/5219/133383/619af275Ed45ea712/2cab380ed4f8bbf1.jpg', 70, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (88, '展示柜', 77, '0,38,77', 'https://m.360buyimg.com/n2/jfs/t1/149056/9/13433/99301/5fa51409E873772c9/9d138f0d366e333d.jpg', 71, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (89, '家电服务', 38, '0,38', NULL, 72, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (90, '家电安装', 89, '0,38,89', 'https://m.360buyimg.com/n2/jfs/t1/99176/38/16243/144180/5e796380Ef4db82ab/00ee2afb2f794451.png', 73, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (91, '家电清洗', 89, '0,38,89', 'https://m.360buyimg.com/n2/jfs/t27532/136/1921084935/101552/b8580619/5bf293d8N96ac6e78.jpg', 74, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (92, '家电维修', 89, '0,38,89', 'https://m.360buyimg.com/n2/jfs/t27532/136/1921084935/101552/b8580619/5bf293d8N96ac6e78.jpg', 75, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (93, '数码', 0, '0', NULL, 76, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (94, '摄影摄像', 93, '0,93', NULL, 77, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (95, '数码相机', 94, '0,93.94', 'https://m.360buyimg.com/n2/jfs/t25420/359/2411692934/85651/b8831335/5be43ee8Nc3be9509.jpg', 78, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (96, '单反相机', 94, '0,93.94', 'https://m.360buyimg.com/n2/jfs/t1/35828/26/5078/324781/5cbea100Eb22bb637/4d2d5e3bcd86fba4.jpg', 79, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (97, '摄像机', 94, '0,93.94', 'https://m.360buyimg.com/n2/jfs/t1/31505/19/15256/302254/5cc15b32E0ef27f91/ecc74ee1929f4b98.jpg', 80, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (98, '镜头', 94, '0,93.94', 'https://m.360buyimg.com/n2/jfs/t27217/164/123983469/100291/8ce505bf/5b85132dN7c54da7d.jpg', 81, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (99, '数码相框', 94, '0,93.94', 'https://m.360buyimg.com/n2/jfs/t26581/257/1933748659/197103/ac1af01/5bf4af9cN731ff59e.jpg', 82, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (100, '微单相机', 94, '0,93.94', 'https://m.360buyimg.com/n2/jfs/t1/110907/1/2262/435001/5ea0fdcdE5de43bf9/bc3c5e5e66f61ede.png', 83, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (101, '拍立得', 94, '0,93.94', 'https://m.360buyimg.com/n2/jfs/t1/49558/5/8512/120845/5d5e36deE2f255ae1/14add63bc0c2105e.jpg', 84, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (102, '数码配件', 93, '0,93', NULL, 85, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (103, '滤镜', 102, '0,93,102', 'https://m.360buyimg.com/n2/jfs/t28927/243/1198717160/216636/43a35e65/5cda169aNd5da1f97.jpg', 86, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (104, '闪光灯/手柄', 102, '0,93,102', 'https://m.360buyimg.com/n2/jfs/t1/965/27/3389/106502/5b988507Eac39c0bd/53c6e926221e0191.jpg', 87, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (105, '存储卡', 102, '0,93,102', 'https://m.360buyimg.com/n2/jfs/t1/164122/21/18307/170951/606c23f0E7ff3fc35/d9a66b058c6f64fc.jpg', 88, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (106, '读卡器', 102, '0,93,102', 'https://m.360buyimg.com/n2/jfs/t1/180559/34/13135/80418/60e697ddE5f40bc5e/0469d7d1d7764923.jpg', 89, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (107, '相机包', 102, '0,93,102', 'https://m.360buyimg.com/n2/jfs/t1/105418/15/6639/145373/5df60725Eaafb4c3d/0816c8238f356b08.jpg', 90, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (108, '三脚架/云台', 102, '0,93,102', 'https://m.360buyimg.com/n2/jfs/t1/45659/34/3381/84131/5d11993fEf95f2562/62c37ab476bdeb00.jpg', 91, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (109, '影音娱乐', 93, '0,93', NULL, 92, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (110, 'MP3/MP4', 102, '0,93,109', 'https://m.360buyimg.com/n2/jfs/t1/198030/11/10316/108414/6151618dE7d5eb219/89fe648ef71a9b3b.jpg', 93, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (111, '音箱/音响', 102, '0,93,109', 'https://m.360buyimg.com/n2/jfs/t1/160046/24/23560/187078/616134beE9a333ad1/26d2c5984dc10ae1.jpg', 94, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (112, '耳机/耳麦', 102, '0,93,109', 'https://m.360buyimg.com/n2/jfs/t1/210855/21/941/153358/614464d6E894fc37f/c86f1b7dcc2f0339.jpg', 95, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (113, '麦克风', 102, '0,93,109', 'https://m.360buyimg.com/n2/jfs/t1/9950/3/1213/172349/5bcd6855Ebfc35884/f145193d604ef1ae.jpg', 96, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (114, '专业音频', 102, '0,93,109', 'https://m.360buyimg.com/n2/jfs/t1/156439/31/6619/96248/60066943E56001d13/16d10f7fb8d835e7.jpg', 97, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (115, '苹果配件', 102, '0,93,109', 'https://m.360buyimg.com/n2/jfs/t1/200676/24/609/29419/610d0af0E5362e966/9d1aef32b1e6fade.jpg', 98, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (116, '电子教育', 93, '0,93', NULL, 99, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (117, '电子词典', 116, '0,93,116', 'https://m.360buyimg.com/n2/jfs/t1/62708/7/1154/300095/5cf6171dE1724a70a/a96afc52bbab03da.jpg', 100, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (118, '录音笔', 116, '0,93,116', 'https://m.360buyimg.com/n2/jfs/t1/144359/19/1392/465976/5ef2c8c0Ed65003a5/09237c9017cc6880.png', 101, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (119, '电纸书', 116, '0,93,116', 'https://m.360buyimg.com/n2/jfs/t1/113845/3/16758/69369/5f505231E36b77f92/aa5da012d5349097.jpg', 102, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (120, '复读机', 116, '0,93,116', 'https://m.360buyimg.com/n2/jfs/t1/47972/11/8203/120944/5d5b960bEb69e1e3b/3868a0c13d198992.jpg', 103, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (121, '点读机/笔', 116, '0,93,116', 'https://m.360buyimg.com/n2/jfs/t1/111247/9/15214/107780/5f3b9f70E6b6d86b2/f50820c3a3e5379c.jpg', 104, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (122, '学生平板', 116, '0,93,116', 'https://m.360buyimg.com/n2/jfs/t1/73905/2/17016/114908/61429dbaEd7104fb8/9a1e699e925aa96e.jpg', 105, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (123, '虚拟商品', 93, '0,93', NULL, 106, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (124, '延保服务', 123, '0,93,123', 'https://m.360buyimg.com/n2/jfs/t1/15442/35/311/98412/5c08ce53Ee980dd2a/2febbd9466191b94.jpg', 107, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (125, '智能设备', 93, '0,93', NULL, 108, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (126, '智能手环', 125, '0,93,125', 'https://m.360buyimg.com/n2/jfs/t1/168905/18/25204/99018/619de3f3E8d8d42e3/db2e0bde9d8ca513.jpg', 109, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (127, '智能手表', 125, '0,93,125', 'https://m.360buyimg.com/n2/jfs/t1/144244/13/4483/47045/5f28cf2eEc21dce00/a7e906be6d41fa46.jpg', 110, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (128, 'VR眼镜', 125, '0,93,125', 'https://m.360buyimg.com/n2/jfs/t27277/335/3069813/79993/66cd0b05/5b7fd200Ne836aae2.jpg', 111, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (129, '运动跟踪器', 125, '0,93,125', 'https://m.360buyimg.com/n2/jfs/t1/18703/10/12450/166846/5c98af08E99a3e374/0cdd2ff68fc6fa46.jpg', 112, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (130, '健康监测', 125, '0,93,125', 'https://m.360buyimg.com/n2/jfs/t1/76305/5/7837/202299/5d5cb524Ed7616b1f/58dbb5a9da73e66c.jpg', 113, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (131, '智能配饰', 125, '0,93,125', 'https://m.360buyimg.com/n2/jfs/t1/205762/9/7700/34808/614beed1Ec4def372/d3078d5fcfc09e67.jpg', 114, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (132, '电脑、办公', 0, '0', NULL, 115, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (133, '电脑整机', 132, '0,132', '', 116, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (134, '笔记本', 133, '0,93,133', 'https://m.360buyimg.com/n2/jfs/t1/185736/8/20860/30110/61244419E228fec46/4d3c0603d3a8b555.jpg', 117, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (135, '台式机', 133, '0,93,133', 'https://m.360buyimg.com/n2/jfs/t1/174779/29/18358/138749/60e41e89Ed5d73229/bed842c889662923.jpg', 118, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (136, '服务器/工作站', 133, '0,93,133', 'https://m.360buyimg.com/n2/jfs/t1/29909/27/1036/253555/5c0f26f2E7f3d5004/1eb24ded8ecbc177.jpg', 119, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (137, '笔记本配件', 133, '0,93,133', 'https://m.360buyimg.com/n2/jfs/t1/147502/6/8170/278953/5f5c3b75Eb9c79df6/fba5fca19d452b4f.png', 120, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (138, '游戏本', 133, '0,93,133', 'https://m.360buyimg.com/n2/jfs/t1/179498/37/9908/196541/60cc5949E35d8816c/174adc6732564fef.jpg', 121, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (139, '平板电脑', 133, '0,93,133', 'https://m.360buyimg.com/n2/jfs/t1/20937/25/11570/118978/5c90682aEa60e4a17/20f239aafde1faa1.jpg', 122, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (140, '电脑组件', 132, '0,132', '', 123, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (141, 'CPU', 140, '0,93,140', 'https://m.360buyimg.com/n2/jfs/t1/201010/7/13094/133768/61694f3fE609d4f5e/5cbe7975e8f135f1.jpg', 124, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (142, '显卡', 140, '0,93,140', 'https://m.360buyimg.com/n2/jfs/t1/148416/4/19398/47178/5fe19f74Eb0769b8c/f4b12168e77b6340.jpg', 125, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (143, '内存', 140, '0,93,140', 'https://m.360buyimg.com/n2/jfs/t1/208644/15/5284/300516/61696163Ee1631c65/5ce015cc41d3c353.png', 126, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (144, '主板', 140, '0,93,140', 'https://m.360buyimg.com/n2/jfs/t1/115260/4/8889/642066/5ed5cee4E0ba41585/0d2f9dec64e48990.jpg', 127, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (145, '散热器', 140, '0,93,140', 'https://m.360buyimg.com/n2/jfs/t1/160559/33/14567/160361/60598d4aE1313beb2/a6905b67911b0dcf.jpg', 128, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (146, '硬盘', 140, '0,93,140', 'https://m.360buyimg.com/n2/jfs/t1/2108/21/2626/259487/5b972db7E43119e11/6eb212035c7955a9.jpg', 129, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (147, '外设产品', 132, '0,132', '', 130, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (148, '键盘', 147, '0,132,147', 'https://m.360buyimg.com/n2/jfs/t1/198519/21/17143/99664/6194b7d5E2ca1e932/7bb0c55d26eecf7a.jpg', 131, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (149, '鼠标', 147, '0,132,147', 'https://m.360buyimg.com/n2/jfs/t1/188348/40/17288/178695/610ce11aE203c54b0/3fdf0ba391279b2c.png', 132, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (150, '摄像头', 147, '0,132,147', 'https://m.360buyimg.com/n2/jfs/t1/135947/25/19732/81210/5fd56d76Edf622a15/d51eeade2d3c6bfc.png', 133, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (151, '移动机械硬盘', 147, '0,132,147', 'https://m.360buyimg.com/n2/jfs/t1/143524/23/21579/88637/61a581ffEd05f4c44/ba625836b8f237cc.jpg', 134, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (152, 'U盘', 147, '0,132,147', 'https://m.360buyimg.com/n2/jfs/t1/194484/20/4837/83221/60ac95beE886cefa4/912712a051a96cd2.jpg', 135, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (153, '办公设备', 132, '0,132', '', 136, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (154, '打印机', 153, '0,132,153', 'https://m.360buyimg.com/n2/jfs/t1/99250/2/15872/103371/5e75702eE539d0da8/704e524239abbb93.png', 137, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (155, '传真设备', 153, '0,132,153', 'https://m.360buyimg.com/n2/jfs/t1/141920/1/3399/74385/5f17d82bEdc56eda7/0d40af07e80bc5d3.jpg', 138, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (156, '复合机', 153, '0,132,153', 'https://m.360buyimg.com/n2/jfs/t1/207527/24/1922/98123/614d3e6eE046a0fcf/48f268e5c81aa3bb.jpg', 139, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (157, '扫描仪', 153, '0,132,153', 'ttps://m.360buyimg.com/n2/jfs/t1/203113/37/4885/282916/61333b7eE22dda16e/fd31b813258a2f5b.jpg', 140, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (158, '投影机', 153, '0,132,153', 'https://m.360buyimg.com/n2/jfs/t1/110171/17/7209/225195/5e574638E1c8ee2bb/240ad34ff94aa2bf.png', 141, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (159, '碎纸机', 153, '0,132,153', 'https://m.360buyimg.com/n2/jfs/t1/186941/14/19527/205582/6122f2e6E8ef28859/54906e93211c3e3d.jpg', 142, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (160, '文具', 132, '0,132', '', 143, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (161, '计算器', 160, '0,132,160', 'https://m.360buyimg.com/n2/jfs/t1/100927/20/8515/315477/5e056961Ed2d3b736/ab972a77af024c51.jpg', 144, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (162, '学生文具', 160, '0,132,160', 'https://m.360buyimg.com/n2/jfs/t1/152506/22/5765/66130/5fae3357E2fa54527/d55432475f77dcec.jpg', 145, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (163, '笔类', 160, '0,132,160', 'https://m.360buyimg.com/n2/jfs/t25270/146/1067048244/35116/47a9f798/5b8635b4N7a2cee7a.jpg', 146, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (164, '办公文具', 160, '0,132,160', 'https://m.360buyimg.com/n2/jfs/t1/200478/11/14185/147508/61726c58Ef81354cc/8e69563bb6eb4c34.jpg', 147, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (165, '文件管理', 160, '0,132,160', 'https://m.360buyimg.com/n2/jfs/t1/209741/11/11225/710515/61a74b0aE586435ab/ceb6323be0c33028.png', 148, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (166, '本册/便签', 160, '0,132,160', 'https://m.360buyimg.com/n2/jfs/t24766/285/812760926/237390/abd267e6/5b7cd922N5b4b8f5b.jpg', 149, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (167, '游戏设备', 132, '0,132', '', 150, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (168, '游戏机', 167, '0,132,167', 'https://m.360buyimg.com/n2/jfs/t1/206841/38/9144/126907/618e3d40Eb6465d31/bf82156849471adb.jpg', 151, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (169, '游戏耳机', 167, '0,132,167', 'https://m.360buyimg.com/n2/jfs/t1/180989/13/12186/161700/60dd96efEb78c40c5/2b2bd6bb59a77eb2.jpg', 152, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (170, '手柄/方向盘', 167, '0,132,167', 'https://m.360buyimg.com/n2/jfs/t1/183531/29/3285/77470/6098e467E67e89d4b/06ee24c69e03e948.jpg', 153, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (171, '游戏软件', 167, '0,132,167', 'https://m.360buyimg.com/n2/jfs/t1/110939/14/9127/388611/5ed727d3E8f4d688c/bbb5b65ad3a172e6.png', 154, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (172, '游戏周边', 167, '0,132,167', 'https://m.360buyimg.com/n2/jfs/t1/57029/27/7832/88398/5d551190E667ec139/d006bbe9e8b4b8f3.jpg', 155, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (173, '电脑办公安装', 132, '0,132', '', 156, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (174, '电脑安装', 173, '0,132,173', 'https://m.360buyimg.com/n2/jfs/t1/149479/11/5835/133911/5f3df83eEd8b8106b/c617eb2a013805bf.jpg', 157, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (175, '办公设备安装', 173, '0,132,173', 'https://m.360buyimg.com/n2/jfs/t1/178697/36/9948/44892/60cc4e1cEd20b2c96/8e6a3e67c09cb776.jpg', 158, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (176, '电脑办公维修', 132, '0,132', '', 159, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (177, '笔记本维修', 176, '0,132,176', 'https://m.360buyimg.com/n2/jfs/t1/202120/26/5241/362323/6135b1dcEaeb2379f/5207b32b6efbbf09.jpg', 160, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (178, '平板维修', 176, '0,132,176', 'https://m.360buyimg.com/n2/jfs/t1/202120/26/5241/362323/6135b1dcEaeb2379f/5207b32b6efbbf09.jpg', 161, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (179, '办公耗材', 132, '0,132', '', 162, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (180, '刻录碟片', 179, '0,132,180', 'https://m.360buyimg.com/n2/jfs/t1/140497/27/20635/108724/5fe94b08E2b2a45f1/f0918579230fb24d.jpg', 163, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (181, '纸类', 180, '0,132,180', 'https://m.360buyimg.com/n2/jfs/t1/94257/16/13195/330258/5e5734fcEbfa1fac3/2f31d37c3595433a.png', 164, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (182, '硒鼓', 180, '0,132,180', 'https://m.360buyimg.com/n2/jfs/t1/206357/18/5127/151060/6135ca3cE53149f6e/5f17655dada7fed9.jpg', 165, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (183, '墨盒', 180, '0,132,180', 'https://m.360buyimg.com/n2/jfs/t1/69527/25/12456/295322/5d9c35c5E2d362238/760b8f1cabc905de.jpg', 166, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (184, '色带', 180, '0,132,180', 'https://m.360buyimg.com/n2/jfs/t1/107302/16/7107/412437/5e572beeE166c6ab3/b8f97bd299be6c76.png', 167, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (185, '箱包皮具', 0, '0', NULL, 168, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (186, '精品男包', 185, '0,185', '', 169, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (187, '商务公文包', 186, '0,185,186', 'https://m.360buyimg.com/n2/jfs/t1/86811/33/15751/689271/5e757002Ec402ae4f/4234f1e93d42477f.png', 170, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (188, '男士钱包', 186, '0,185,186', 'https://m.360buyimg.com/n2/jfs/t1/109530/37/8480/206516/5e68a81bE97e423b1/a4fcf6c1270048bc.jpg', 171, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (189, '男士手包', 186, '0,185,186', 'https://m.360buyimg.com/n2/jfs/t1/99767/4/15178/641744/5e7195cbE6204d137/2203589848a13609.png', 172, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (190, '双肩包', 186, '0,185,186', 'https://m.360buyimg.com/n2/jfs/t1/99009/22/14594/411773/5e686b6bEb5114218/9d39423b17d247de.png', 173, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (191, '单肩/斜挎包', 186, '0,185,186', 'https://m.360buyimg.com/n2/jfs/t1/209476/14/6958/170589/6179044fEafa5008e/5355d2c6d284d200.jpg', 174, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (192, '钥匙包', 186, '0,185,186', 'https://m.360buyimg.com/n2/jfs/t1/151623/21/10589/162201/5fdcaf97Ed52d19c4/a7479db85105b02b.jpg', 175, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (193, '卡包名片夹', 186, '0,185,186', 'https://m.360buyimg.com/n2/jfs/t1/151623/21/10589/162201/5fdcaf97Ed52d19c4/a7479db85105b02b.jpg', 176, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (194, '手机包', 186, '0,185,186', 'https://m.360buyimg.com/n2/jfs/t1/174382/13/6196/206626/6083916aE04c5abad/f6320f2e6394ffef.jpg', 177, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (195, '证件包', 186, '0,185,186', 'https://m.360buyimg.com/n2/jfs/t1/191763/2/11394/485400/60dff12fEa41b7e9a/cba4f4233a16e216.png', 178, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (196, '潮流女包', 185, '0,185', '', 179, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (197, '钱包', 196, '0,185,196', 'https://m.360buyimg.com/n2/jfs/t1/168587/33/15876/157844/6066cf24E611ae6c9/dedac97916652533.jpg', 180, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (198, '手拿包', 196, '0,185,196', 'https://m.360buyimg.com/n2/jfs/t1/104688/20/14793/413783/5e69914fE2638cba6/9f1b91d4b28009e2.png', 181, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (199, '单肩包', 196, '0,185,196', 'https://m.360buyimg.com/n2/jfs/t1/194540/4/19329/667370/6121dbbcE246cd4a3/8b705b2550c414e0.png', 182, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (200, '女士双肩包', 196, '0,185,196', 'https://m.360buyimg.com/n2/jfs/t1/89132/4/16236/441642/5e79630cEaa69c2dc/baa52867d7521f2a.png', 183, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (201, '手提包', 196, '0,185,196', 'https://m.360buyimg.com/n2/jfs/t1/189993/7/17456/189236/61110fe0E6c3b9908/98869265ddd393b2.jpg', 184, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (202, '斜挎包', 196, '0,185,196', 'https://m.360buyimg.com/n2/jfs/t1/206254/22/2721/225164/6124bd85E7b40eeb0/87fc0e6d588e2a24.jpg', 185, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (203, '钥匙包', 196, '0,185,196', 'https://m.360buyimg.com/n2/jfs/t1/168598/14/8887/259057/603f265eEe5055621/b524e5f7cdc9102a.png', 186, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (204, '卡包/零钱包', 196, '0,185,196', 'https://m.360buyimg.com/n2/jfs/t1/137332/33/16752/316096/5fb884f4E80f257d8/39d01ddae44ffa4f.png', 187, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (205, '化妆包', 196, '0,185,196', 'https://m.360buyimg.com/n2/jfs/t1/59161/13/4770/209337/5d26ebf3E327f0859/0fe7aa222bfc29bf.jpg', 188, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (206, '功能箱包', 185, '0,185', '', 189, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (207, '登山包', 206, '0,185,206', 'https://m.360buyimg.com/n2/jfs/t1/135675/36/6344/120410/5f2d5243E139150f1/66c03fd8b44b5bc1.jpg', 190, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (208, '旅行包', 206, '0,185,206', 'https://m.360buyimg.com/n2/jfs/t1/155624/5/23684/86386/618887fbEc4c7033b/adc67d21659d75fb.jpg', 191, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (209, '拉杆箱', 206, '0,185,206', 'https://m.360buyimg.com/n2/jfs/t1/23747/29/11790/126764/5c91fa3aE8881658e/4227598ba4ba8ba4.jpg', 192, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (210, '电脑包', 206, '0,185,206', 'https://m.360buyimg.com/n2/jfs/t1/194841/26/13307/86198/60efe982Eb9874438/ad1aed5a1b609665.jpg', 193, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (211, '休闲运动包', 206, '0,185,206', 'https://m.360buyimg.com/n2/jfs/t29251/208/865778281/344849/6e95ef/5c00ff99N79a7e322.jpg', 194, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (212, '旅行配件', 206, '0,185,206', 'https://m.360buyimg.com/n2/jfs/t24502/173/2397470667/302228/44391882/5b7d36d9N34eaf74c.jpg', 195, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (213, '书包', 206, '0,185,206', 'https://m.360buyimg.com/n2/jfs/t1/10118/21/2046/298944/5be00e3dEfb40af17/f85b15c508646560.jpg', 196, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (214, '腰包/胸包', 206, '0,185,206', 'https://m.360buyimg.com/n2/jfs/t1/169161/21/24193/320681/618f8f03E7eb69a30/fe7ae663d55b6402.jpg', 197, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (215, '拉杆包', 206, '0,185,206', 'https://m.360buyimg.com/n2/jfs/t1/128985/4/13896/70729/5f715aa3E46d9e03a/fccb2ac66b7ccb94.jpg', 198, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (216, '箱包皮具配件', 185, '0,185', '', 199, 1, '2024-05-21 17:53:57', NULL, 0);
INSERT INTO `pms_category` VALUES (217, '箱包配件', 216, '0,185,216', 'https://m.360buyimg.com/n2/jfs/t1/64678/14/16857/69808/613ec336E1b44c59f/08d620e59291eecd.jpg', 200, 1, '2024-05-21 17:53:57', NULL, 0);

-- ----------------------------
-- Table structure for pms_category_attr
-- ----------------------------
DROP TABLE IF EXISTS `pms_category_attr`;
CREATE TABLE `pms_category_attr`  (
                                    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                                    `name` varchar(50)  NOT NULL COMMENT '属性/规格名称',
                                    `group_name` varchar(50)  NULL DEFAULT NULL COMMENT '所属分组名称',
                                    `category_id` bigint NULL DEFAULT NULL COMMENT '分类ID',
                                    `input_type` tinyint NOT NULL COMMENT '输入类型(1:文本 2:单选 3:多选)',
                                    `options` varchar(500)  NULL DEFAULT NULL COMMENT '可选项(逗号分隔)',
                                    `sort` smallint NULL DEFAULT NULL COMMENT '排序',
                                    `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
                                    `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
                                    `is_deleted` tinyint NOT NULL DEFAULT 0 COMMENT '逻辑删除标识(0:未删除 1:已删除)',
                                    PRIMARY KEY (`id`) USING BTREE,
                                    INDEX `idx_category_id`(`category_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11  COMMENT = '商品分类属性/规格表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pms_category_attr
-- ----------------------------
INSERT INTO `pms_category_attr` VALUES (1, '屏幕分辨率', '屏幕', 3, 3, '1080P,2K,4K', 1, '2024-05-17 14:32:15', '2025-04-19 19:15:38', 0);
INSERT INTO `pms_category_attr` VALUES (2, '屏幕尺寸', '屏幕', 3, 1, '', 2, '2024-05-17 14:32:15', '2025-03-05 22:22:44', 0);
INSERT INTO `pms_category_attr` VALUES (3, '屏幕材料', '屏幕', 3, 1, 'LCD,AMOLED', 3, '2024-05-17 14:32:15', '2024-05-17 14:32:15', 0);
INSERT INTO `pms_category_attr` VALUES (4, '摄像头像素', '屏幕', 3, 1, NULL, 4, '2024-05-17 14:32:15', '2024-05-17 14:32:15', 0);
INSERT INTO `pms_category_attr` VALUES (5, '指纹类型', '屏幕', 3, 2, '屏下指纹,屏后指纹', 5, '2024-05-17 14:32:15', '2024-05-17 14:32:15', 0);
INSERT INTO `pms_category_attr` VALUES (6, '刷新率', '屏幕', 3, 2, '90HZ,120HZ', 6, '2024-05-17 14:32:15', '2024-06-01 21:51:28', 0);

-- ----------------------------
-- Table structure for pms_category_spec
-- ----------------------------
DROP TABLE IF EXISTS `pms_category_spec`;
CREATE TABLE `pms_category_spec`  (
                                    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                                    `name` varchar(50)  NOT NULL COMMENT '属性/规格名称',
                                    `category_id` bigint NULL DEFAULT NULL COMMENT '分类ID',
                                    `options` varchar(500)  NULL DEFAULT NULL COMMENT '可选项(逗号分隔)',
                                    `sort` smallint NULL DEFAULT NULL COMMENT '排序',
                                    `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
                                    `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
                                    `is_deleted` tinyint NOT NULL DEFAULT 0 COMMENT '逻辑删除标识(0:未删除 1:已删除)',
                                    PRIMARY KEY (`id`) USING BTREE,
                                    INDEX `idx_category_id`(`category_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11  COMMENT = '商品分类属性/规格表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pms_category_spec
-- ----------------------------
INSERT INTO `pms_category_spec` VALUES (1, '颜色', 3, '红色,蓝色,黑色', 1, '2024-05-17 14:32:15', '2024-05-17 14:32:15', 0);
INSERT INTO `pms_category_spec` VALUES (2, '存储容量', 3, '8+256G,12+512G,16+1T', 2, '2024-05-17 14:32:15', '2024-05-17 14:32:15', 0);

-- ----------------------------
-- Table structure for pms_sku
-- ----------------------------
DROP TABLE IF EXISTS `pms_sku`;
CREATE TABLE `pms_sku`  (
                          `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'SKU 主键',
                          `sku_code` varchar(50)  NULL DEFAULT NULL COMMENT 'SKU 编码',
                          `spu_id` bigint NOT NULL COMMENT 'SPU ID',
                          `spec_hash` char(32)  NULL DEFAULT NULL COMMENT '规格组合的MD5哈希值',
                          `price` bigint NOT NULL COMMENT '商品价格(单位：分)',
                          `stock` int UNSIGNED NOT NULL COMMENT '库存数量',
                          `stock_locked` int NULL DEFAULT 0 COMMENT '库存锁定数量',
                          `stock_sold` int NULL DEFAULT 0 COMMENT '已售数量',
                          `img_url` varchar(255)  NULL DEFAULT NULL COMMENT 'SKU 图片URL',
                          `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
                          `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
                          `is_deleted` tinyint NOT NULL DEFAULT 0 COMMENT '逻辑删除标识（0:未删除1:已删除）',
                          PRIMARY KEY (`id`) USING BTREE,
                          INDEX `fk_pms_sku_pms_spu`(`spu_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21  COMMENT = '商品库存单元表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pms_sku
-- ----------------------------
INSERT INTO `pms_sku` VALUES (17, NULL, 1, 'b0483152f82090c7f1df1e692c1ef82a', 100, 1, 0, 0, 'http://localhost:9010/default/20250418/5f4c1c5abf19408b9f6c74d114cafd26.png', '2025-04-18 01:09:56', '2025-04-18 01:09:56', 0);
INSERT INTO `pms_sku` VALUES (18, NULL, 1, '542c022c46454683d01622b044986b9a', 200, 2, 0, 0, 'http://localhost:9010/default/20250418/65d2f879ff9149f6828d45da743903c6.png', '2025-04-18 01:09:56', '2025-04-18 01:09:56', 0);
INSERT INTO `pms_sku` VALUES (19, NULL, 1, '9dc5196081a595c2c9fcfa3b8cf24843', 300, 3, 0, 0, 'http://localhost:9010/default/20250418/5f4c1c5abf19408b9f6c74d114cafd26.png', '2025-04-18 01:09:56', '2025-04-18 01:09:56', 0);
INSERT INTO `pms_sku` VALUES (20, NULL, 1, '75263904b1ecc0c66a652aa9245f1830', 400, 4, 0, 0, 'http://localhost:9010/default/20250418/65d2f879ff9149f6828d45da743903c6.png', '2025-04-18 01:09:56', '2025-04-18 01:09:56', 0);

-- ----------------------------
-- Table structure for pms_sku_spec
-- ----------------------------
DROP TABLE IF EXISTS `pms_sku_spec`;
CREATE TABLE `pms_sku_spec`  (
                               `id` bigint NOT NULL AUTO_INCREMENT COMMENT '规格ID',
                               `sku_id` bigint NULL DEFAULT NULL COMMENT 'SKU ID',
                               `spec_name` varchar(255)  NULL DEFAULT NULL COMMENT '规格名称（如：颜色）',
                               `spec_value` varchar(100)  NULL DEFAULT NULL COMMENT '规格值（如：白色）',
                               `create_time` datetime NULL DEFAULT NULL,
                               `update_time` datetime NULL DEFAULT NULL,
                               PRIMARY KEY (`id`) USING BTREE,
                               INDEX `idx_skuid`(`sku_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 65  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pms_sku_spec
-- ----------------------------
INSERT INTO `pms_sku_spec` VALUES (57, 17, '颜色', '白色', '2025-04-18 01:09:56', '2025-04-18 01:09:56');
INSERT INTO `pms_sku_spec` VALUES (58, 17, '存储', '8+256G', '2025-04-18 01:09:56', '2025-04-18 01:09:56');
INSERT INTO `pms_sku_spec` VALUES (59, 18, '颜色', '白色', '2025-04-18 01:09:56', '2025-04-18 01:09:56');
INSERT INTO `pms_sku_spec` VALUES (60, 18, '存储', '12+512G', '2025-04-18 01:09:56', '2025-04-18 01:09:56');
INSERT INTO `pms_sku_spec` VALUES (61, 19, '颜色', '黑色', '2025-04-18 01:09:56', '2025-04-18 01:09:56');
INSERT INTO `pms_sku_spec` VALUES (62, 19, '存储', '8+256G', '2025-04-18 01:09:56', '2025-04-18 01:09:56');
INSERT INTO `pms_sku_spec` VALUES (63, 20, '颜色', '黑色', '2025-04-18 01:09:56', '2025-04-18 01:09:56');
INSERT INTO `pms_sku_spec` VALUES (64, 20, '存储', '12+512G', '2025-04-18 01:09:56', '2025-04-18 01:09:56');

-- ----------------------------
-- Table structure for pms_spu
-- ----------------------------
DROP TABLE IF EXISTS `pms_spu`;
CREATE TABLE `pms_spu`  (
                          `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                          `name` varchar(64)  NOT NULL COMMENT '商品名称',
                          `status` tinyint NOT NULL DEFAULT 0 COMMENT '状态（0：未上架，1：已上架）',
                          `category_id` bigint NOT NULL COMMENT '类型ID',
                          `brand_id` bigint NULL DEFAULT NULL COMMENT '品牌ID',
                          `img_url` varchar(255)  NULL DEFAULT NULL COMMENT '商品主图',
                          `unit` varchar(16)  NULL DEFAULT NULL COMMENT '单位',
                          `description` varchar(255)  NULL DEFAULT NULL COMMENT '商品描述',
                          `detail` text  NULL COMMENT '商品详情',
                          `sales` int NULL DEFAULT NULL COMMENT '销量',
                          `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
                          `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
                          `is_deleted` tinyint NOT NULL DEFAULT 0 COMMENT '逻辑删除标识（0：未删除，1：已删除）',
                          PRIMARY KEY (`id`) USING BTREE,
                          INDEX `fk_pms_spu_pms_brand`(`brand_id` ASC) USING BTREE,
                          INDEX `fk_pms_spu_pms_category`(`category_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2  COMMENT = '商品标准化单元表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pms_spu
-- ----------------------------
INSERT INTO `pms_spu` VALUES (1, '小米14', 1, 24, 10, 'http://localhost:9010/default/20250417/661e1ed9f0854e3d848b6766d868e135.jpg', '台', '小米14 旗舰版描述', '<p>123</p>', NULL, '2024-05-17 13:38:53', '2025-04-18 01:09:56', 0);

-- ----------------------------
-- Table structure for pms_spu_attr
-- ----------------------------
DROP TABLE IF EXISTS `pms_spu_attr`;
CREATE TABLE `pms_spu_attr`  (
                               `id` bigint NOT NULL AUTO_INCREMENT COMMENT '基础属性ID',
                               `spu_id` bigint NOT NULL COMMENT 'SPU ID',
                               `attr_id` bigint NULL DEFAULT NULL COMMENT '属性 ID',
                               `attr_name` varchar(50)  NULL DEFAULT NULL COMMENT '属性名称',
                               `attr_value` varchar(255)  NOT NULL COMMENT '属性值',
                               `attr_sort` smallint NULL DEFAULT 0 COMMENT '属性排序',
                               `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
                               `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
                               PRIMARY KEY (`id`) USING BTREE,
                               INDEX `fk_pms_spu_attribute_pms_spu`(`spu_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21  COMMENT = '商品属性/规格表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pms_spu_attr
-- ----------------------------
INSERT INTO `pms_spu_attr` VALUES (19, 1, NULL, '分辨率', '2K', 0, NULL, NULL);
INSERT INTO `pms_spu_attr` VALUES (20, 1, NULL, '尺寸', '6英寸', 0, NULL, NULL);

-- ----------------------------
-- Table structure for pms_spu_comment
-- ----------------------------
DROP TABLE IF EXISTS `pms_spu_comment`;
CREATE TABLE `pms_spu_comment`  (
                                  `id` bigint NOT NULL,
                                  `spu_id` bigint NULL DEFAULT NULL,
                                  `spu_name` varchar(255)  NULL DEFAULT NULL,
                                  `sku_name` varchar(255)  NULL DEFAULT NULL,
                                  `member_id` bigint NULL DEFAULT NULL,
                                  `member_nickname` varchar(255)  NULL DEFAULT NULL,
                                  `star` varchar(255)  NULL DEFAULT NULL,
                                  `content` text  NULL,
                                  `create_time` datetime NULL DEFAULT NULL,
                                  `update_time` datetime NULL DEFAULT NULL,
                                  `is_deleted` tinyint NOT NULL DEFAULT 0,
                                  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pms_spu_comment
-- ----------------------------

-- ----------------------------
-- Table structure for pms_spu_image
-- ----------------------------
DROP TABLE IF EXISTS `pms_spu_image`;
CREATE TABLE `pms_spu_image`  (
                                `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                                `spu_id` bigint NOT NULL COMMENT 'SPU ID',
                                `img_url` varchar(255)  NOT NULL COMMENT '商品图片URL',
                                `sort` smallint NOT NULL DEFAULT 1 COMMENT '排序',
                                `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
                                `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
                                `is_deleted` tinyint NOT NULL DEFAULT 0 COMMENT '逻辑删除标识：0-未删除，1-已删除',
                                PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pms_spu_image
-- ----------------------------
INSERT INTO `pms_spu_image` VALUES (1, 288, 'https://youlai-mall.oss-cn-shenzhen.aliyuncs.com/images/2021/04/23/1619163660000.jpg', 1, '2024-05-07 17:35:42', '2024-05-07 17:35:42', 1);
INSERT INTO `pms_spu_image` VALUES (2, 289, 'https://youlai-mall.oss-cn-shenzhen.aliyuncs.com/images/2021/04/23/1619163660000.jpg', 2, '2024-05-08 10:25:53', '2024-05-08 10:25:53', 0);
INSERT INTO `pms_spu_image` VALUES (3, 288, 'https://youlai-mall.oss-cn-shenzhen.aliyuncs.com/images/2021/04/23/1619163660000.jpg', 3, '2024-05-08 10:51:34', '2024-05-08 10:51:34', 1);
INSERT INTO `pms_spu_image` VALUES (4, 288, 'https://youlai-mall.oss-cn-shenzhen.aliyuncs.com/images/2021/04/23/1619163660000.jpg', 4, '2024-05-08 10:54:43', '2024-05-08 10:54:43', 0);
INSERT INTO `pms_spu_image` VALUES (5, 290, 'https://youlai-mall.oss-cn-shenzhen.aliyuncs.com/images/2021/04/23/1619163660000.jpg', 5, '2024-05-08 10:58:19', '2024-05-08 10:58:19', 1);
INSERT INTO `pms_spu_image` VALUES (6, 290, 'https://youlai-mall.oss-cn-shenzhen.aliyuncs.com/images/2021/04/23/1619163660000.jpg', 6, '2024-05-08 16:22:56', '2024-05-08 16:22:56', 0);
INSERT INTO `pms_spu_image` VALUES (7, 1, 'http://localhost:9010/default/20250417/e0e47a23b4494a29ac42b4ea48566537.jpg', 1, '2025-04-17 00:17:10', '2025-04-17 00:17:10', 1);
INSERT INTO `pms_spu_image` VALUES (8, 1, 'http://localhost:9010/default/20250417/b930dbfe4da343ee9eccdcf89b3af810.png', 2, '2025-04-17 00:17:10', '2025-04-17 00:17:10', 1);
INSERT INTO `pms_spu_image` VALUES (9, 1, 'http://localhost:9010/default/20250417/c3647bff5c084e3eb273d3f00635e992.png', 3, '2025-04-17 00:17:10', '2025-04-17 00:17:10', 1);
INSERT INTO `pms_spu_image` VALUES (10, 1, 'http://localhost:9010/default/20250417/6776a5f6a55841269435f5ddced6c62e.jpg', 1, '2025-04-17 06:59:14', '2025-04-17 06:59:14', 1);
INSERT INTO `pms_spu_image` VALUES (11, 1, 'http://localhost:9010/default/20250417/d7e910cb1a994bc982c0a8355a56520e.png', 2, '2025-04-17 06:59:14', '2025-04-17 06:59:14', 1);
INSERT INTO `pms_spu_image` VALUES (12, 1, 'http://localhost:9010/default/20250417/918b9ce3e60742fbaad5c85e1713ace7.jpg', 3, '2025-04-17 06:59:14', '2025-04-17 06:59:14', 1);
INSERT INTO `pms_spu_image` VALUES (13, 1, 'http://localhost:9010/default/20250417/6776a5f6a55841269435f5ddced6c62e.jpg', 1, '2025-04-17 07:15:00', '2025-04-17 07:15:00', 1);
INSERT INTO `pms_spu_image` VALUES (14, 1, 'http://localhost:9010/default/20250417/918b9ce3e60742fbaad5c85e1713ace7.jpg', 2, '2025-04-17 07:15:00', '2025-04-17 07:15:00', 1);
INSERT INTO `pms_spu_image` VALUES (15, 1, 'http://localhost:9010/default/20250417/0ec895f9e118483b9f7f2ffd62d7bb06.png', 3, '2025-04-17 07:15:00', '2025-04-17 07:15:00', 1);
INSERT INTO `pms_spu_image` VALUES (16, 1, 'http://localhost:9010/default/20250417/6776a5f6a55841269435f5ddced6c62e.jpg', 1, '2025-04-18 01:00:30', '2025-04-18 01:00:30', 1);
INSERT INTO `pms_spu_image` VALUES (17, 1, 'http://localhost:9010/default/20250417/918b9ce3e60742fbaad5c85e1713ace7.jpg', 2, '2025-04-18 01:00:30', '2025-04-18 01:00:30', 1);
INSERT INTO `pms_spu_image` VALUES (18, 1, 'http://localhost:9010/default/20250417/0ec895f9e118483b9f7f2ffd62d7bb06.png', 3, '2025-04-18 01:00:30', '2025-04-18 01:00:30', 1);
INSERT INTO `pms_spu_image` VALUES (19, 1, 'http://localhost:9010/default/20250417/6776a5f6a55841269435f5ddced6c62e.jpg', 1, '2025-04-18 01:07:37', '2025-04-18 01:07:37', 1);
INSERT INTO `pms_spu_image` VALUES (20, 1, 'http://localhost:9010/default/20250417/918b9ce3e60742fbaad5c85e1713ace7.jpg', 2, '2025-04-18 01:07:37', '2025-04-18 01:07:37', 1);
INSERT INTO `pms_spu_image` VALUES (21, 1, 'http://localhost:9010/default/20250417/0ec895f9e118483b9f7f2ffd62d7bb06.png', 3, '2025-04-18 01:07:37', '2025-04-18 01:07:37', 1);
INSERT INTO `pms_spu_image` VALUES (22, 1, 'http://localhost:9010/default/20250417/6776a5f6a55841269435f5ddced6c62e.jpg', 1, '2025-04-18 01:09:56', '2025-04-18 01:09:56', 0);
INSERT INTO `pms_spu_image` VALUES (23, 1, 'http://localhost:9010/default/20250417/918b9ce3e60742fbaad5c85e1713ace7.jpg', 2, '2025-04-18 01:09:56', '2025-04-18 01:09:56', 0);
INSERT INTO `pms_spu_image` VALUES (24, 1, 'http://localhost:9010/default/20250417/0ec895f9e118483b9f7f2ffd62d7bb06.png', 3, '2025-04-18 01:09:56', '2025-04-18 01:09:56', 0);

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
                           UNIQUE INDEX `ux_undo_log`(`xid` ASC, `branch_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'AT transaction mode undo table' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of undo_log
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;


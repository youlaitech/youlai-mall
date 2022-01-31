

![](https://img.shields.io/badge/youlai--mall-v2.0.0-blue) ![](https://img.shields.io/badge/SpringBoot-2.6.3-brightgreen.svg) ![](https://img.shields.io/badge/SpringCloud-2021-green.svg) [![](https://img.shields.io/github/stars/youlaitech/youlai-mall.svg?style=social&label=Stars)](https://github.com/youlaitech/youlai-mall) [![](https://gitee.com/youlaitech/youlai-mall/badge/star.svg)](https://gitee.com/youlaiorg/youlai-mall) [![](https://img.shields.io/badge/Author-有来开源组织-orange.svg)](https://gitee.com/youlaiorg) [![](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg)](https://gitee.com/youlaitech/youlai-mall/blob/master/LICENSE)

[在线预览](http://www.youlai.tech/) | [Gitee 仓库](https://gitee.com/youlaiorg) |  [Github 仓库](https://github.com/youlaitech) | [博客主页](https://www.cnblogs.com/haoxianrui/) | [官方文档](http://youlaitech.gitee.io/youlai-mall/)



# 📚️ 项目简介

[youlai-mall](https://gitee.com/haoxr) 是基于Spring Boot 2.6、Spring Cloud 2021 & Alibaba 2021、Vue3、Element-Plus、uni-app等主流技术栈构建的一整套全栈开源商城项目， 涉及 [后端微服务](https://gitee.com/youlaitech/youlai-mall) 、 [前端管理](https://gitee.com/youlaitech/youlai-mall-admin) 、 [微信小程序](https://gitee.com/youlaitech/youlai-mall-weapp) 和 [APP应用](https://gitee.com/youlaitech/youlai-mall-weapp) 等多端的开发。

# ⛺ 源码地址
| 名称 |  Gitee | Github   | 
|---|---|---|  
| 开源组织 | [有来开源组织](https://gitee.com/youlaiorg) |[有来开源组织](https://github.com/youlaitech) |
| 后端 | [youlai-mall](https://gitee.com/youlaiorg/youlai-mall) | [youlai-mall](https://github.com/youlaitech/youlai-mall) |
| 管理前端 |[mall-admin-web](https://gitee.com/youlaiorg/mall-admin-web) | [mall-admin-web](https://github.com/youlaitech/mall-admin-web) | 
| 小程序/H5/移动端 | [mall-app](https://gitee.com/youlaiorg/mall-app) | [mall-app](https://github.com/youlaitech/mall-app) |



# 🚤 项目启动


### 📁 后端启动

>  `极速启动` 是方便快速启动查看效果的启动方式，其中的数据库和Redis等中间件使用的是有来提供的云环境，切勿修改数据，有时间条件建议`本地启动`。

#### ️🗀 极速启动
1. [x] **启动 Nacos**
   
   IntelliJ IDEA 打开命令行终端 Terminal，输入 `cd middleware/nacos/bin` 切换到Nacos的bin目录，执行 `startup -m standalone` 启动 Nacos 服务；

2. [x] **导入Nacos配置**
   
   打开浏览器，地址栏输入 Nacos 管控台的地址 [ http://localhost:8848/nacos]( http://localhost:8848/nacos) ;
   输入用户名/密码：nacos/nacos;
   进入管控台，点击左侧菜单 `配置管理` → `配置列表` 进入列表页面，点击 `导入配置` 选择项目中的 `doc/nacos/DEFAULT_GROUP.zip` 文件。

3. [x] **服务启动**
   
   进入 `youlai-gateway` 模块的启动类 GatewayApplication 启动网关；
   进入 `youlai-auth` 模块的启动类 AuthApplication 启动认证授权中心；
   进入 `youlai-admin`  → `admin-boot` 模块的启动类 AdminApplication 启动系统管理服务；
   至此已完成基础服务的启动，商城服务按需启动，启动方式和 `youlai-admin` 一致。

4. [x] 启动测试
   
   访问接口文档地址测试  [http://localhost:9999/doc.html](http://localhost:9999/doc.html)


#### 🗀 本地启动

1. [x] **中间件安装(🔴必装  ⚪可选)**
   
    - 🔴MySQL 安装
    - 🔴Redis 安装
    - ⚪RabbitMQ
    - ⚪Seata 安装
    - ⚪Sentinel 安装

2. [x] **数据库创建和数据初始化**

    - **系统数据库**

      进入 `doc/sql` 目录 ， 根据 MySQL 版本选择对应的脚本；
      先执行 `database.sql` 完成数据库的创建；
      再执行 `youlai.sql` 、`mall_*.sql` 完成数据表的创建和数据初始化。

    - **Nacos数据库**

      创建名为 nacos 的数据库；
      执行 `middleware/nacos/conf/nacos-mysql.sql` 脚本完成 Nacos 数据库初始化。


3. [x] Nacos 配置和启动

    - 进入 `middleware/nacos/conf/application.properties` 文件修改 Nacos 配置的数据连接

5. [ ] 123231





### 📁 管理前端启动
1. 本机安装 Node 环境
2. npm install
3. npm run dev
4. 访问 http://localhost:9527

### 📁 微信小程序启动
1. 下载 `HBuilder X` 和 `微信开发者工具` ;
2. 导入 [mall-app](https://gitee.com/youlaitech/youlai-mall-weapp) 源码至 `HBuilder X` ;
3. 微信公众平台申请小程序，获得小程序的AppID  ;
4. `微信开发者工具` 微信扫码登录，开启服务端口，点击工具栏 `设置` -> `安全设置` -> `安全` -> `服务端口`选择打开 ;
5. `Hbuilder X` 替换项目AppID成自己的，点击 `manifest.json` 文件->微信小程序配置 ;
6. Nacos控制台替换 `youlai-auth` 配置中的微信小程序 AppID 和 AppSecret 为自己申请的小程序 ;
7. `Hbuilder X` 工具栏点击 `运行` -> `运行到小程序模拟器` -> `微信开发者工具`。

### 📁 移动端启动
1. 下载 `HBuilder X` ;
2. 导入 [mall-app](https://gitee.com/youlaitech/youlai-mall-weapp) 源码至 `HBuilder X`;
3. `Hbuilder X` 工具栏点击 `运行` -> `运行到内置浏览器` 。

# ✨ 技术栈






# 💻开发人员

| ![](https://gitee.com/haoxr/image/raw/master/hxr.jpg)| ![](https://gitee.com/haoxr/image/raw/master/hxr.jpg) | ![](https://gitee.com/haoxr/image/raw/master/hxr.jpg)  | ![](https://gitee.com/haoxr/image/raw/master/hxr.jpg) |  
|---|---|---|---|
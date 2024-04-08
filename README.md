<p align="center">
    <img alt="有来技术" src="https://img.shields.io/badge/Java-17-brightgreen.svg"/>
    <img alt="有来技术" src="https://img.shields.io/badge/SpringBoot-3.1.5-green.svg"/>
    <img alt="有来技术" src="https://img.shields.io/badge/SpringCloud & Alibaba-2022-yellowgreen.svg"/>
     <a href="https://gitee.com/youlaitech/youlai-mall" target="_blank">
        <img alt="有来技术" src="https://gitee.com/youlaitech/youlai-mall/badge/star.svg"/>
    </a>     
    <a href="https://github.com/hxrui" target="_blank">
        <img alt="有来技术" src="https://img.shields.io/github/stars/youlaitech/youlai-mall.svg?style=social&label=Stars"/>
    </a>
    <br/>
    <img alt="有来技术" src="https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg"/>
    <a href="https://gitee.com/youlaiorg" target="_blank">
        <img alt="有来技术" src="https://img.shields.io/badge/Author-有来开源组织-orange.svg"/>
    </a>
</p>

<p align="center">
   <a target="_blank" href="https://www.youlai.tech">有来技术官网</a> |
   <a target="_blank" href="https://youlai.blog.csdn.net">有来技术博客</a>|
   <a target="_blank" href="https://gitee.com/haoxr">Gitee</a>|
   <a target="_blank" href="https://github.com/haoxianrui">Github</a> 
</p>

## 🌱分支说明
|                   | 说明                                                    | 适配管理前端分支                                                               | 适配移动端分支                                                            |
|-------------------|-------------------------------------------------------|------------------------------------------------------------------------|--------------------------------------------------------------------|
| ✅master            | Java 17 + Spring Boot 3 + Spring Authorization Server | [mall-admin:master](https://gitee.com/youlaiorg/mall-admin)            | [mall-app:master](https://gitee.com/youlaiorg/mall-app)            |
| java8 | Java 8 + Spring Boot 2 + Spring Security OAuth2       | [mall-admin:java8](https://gitee.com/youlaiorg/mall-admin/tree/java8/) | [mall-app:java8](https://gitee.com/youlaiorg/mall-app/tree/java8/) |


## 🚀项目简介

[youlai-mall](https://gitee.com/haoxr) 是基于Spring Boot 3 、Spring Cloud & Alibaba
2022、Vue3、Element-Plus、uni-app等全栈主流技术栈构建的开源商城项目，涉及 [微服务接口](https://gitee.com/youlaitech/youlai-mall)、 [管理前端](https://gitee.com/youlaitech/youlai-mall-admin)、 [微信小程序](https://gitee.com/youlaitech/youlai-mall-weapp)
和 [APP应用](https://gitee.com/youlaitech/youlai-mall-weapp)等多端的开发。

- 项目使用皆是当前主流前后端技术栈(持续更新...)，无过度自定义封装，易理解学习和二次扩展；
- Spring Boot 3 、SpringCloud & Alibaba 2022 一站式微服务开箱即用的解决方案；
- Spring Authorization Server 、 JWT 常用 OAuth2 授权模式扩展；
- 移动端采用终极跨平台解决方案 uni-app， 一套代码编译iOS、Android、H5和小程序等多个平台；
- Jenkins、K8s、Docker实现微服务持续集成与交付(CI/CD)。

## 🌈在线预览

| 项目      | 地址                        | 用户名/密码             |
|---------|---------------------------|--------------------|
| 管理端     | https://admin.youlai.tech | admin/123456       |
| 移动端(H5) | http://app.youlai.tech    | 18866668888/666666 |
| 微信小程序  | 关注【有来技术】公众号| 获取体验码申请体验              |


## 🍀源码地址

|      | Gitee                                                  | Github                                                   | GitCode |
|------|--------------------------------------------------------|----------------------------------------------------------|---------|
| 后端接口 | [youlai-mall](https://gitee.com/youlaiorg/youlai-mall) | [youlai-mall](https://github.com/youlaitech/youlai-mall) | -       |
| 管理前端 | [mall-admin](https://gitee.com/youlaiorg/mall-admin)   | [mall-admin](https://github.com/youlaitech/mall-admin)   | -       |
| 移动端  | [mall-app](https://gitee.com/youlaiorg/mall-app)       | [mall-app](https://github.com/youlaitech/mall-app)       | -       |

## 📁目录结构

``` text
youlai-mall
├── docs  
    ├── nacos                       # Nacos配置
        ├── nacos_config.zip        # Nacos脚本   
    ├── sql                         # SQL脚本
        ├── mysql5                  # MySQL5脚本
        ├── mysql8                  # MySQL8脚本
├── mall-sms                        # 营销服务(Sales Management Service)
    ├── sms-api                     # 营销服务-Feign客户端
    ├── sms-boot                    # 营销服务-启动应用
├── mall-pms                        # 商品服务(Product Management Service)
    ├── pms-api                     # 商品服务-Feign客户端
    ├── pms-boot                    # 商品服务-启动应用
├── mall-oms                        # 订单服务(Order Management Service)
    ├── oms-api                     # 订单服务-Feign客户端
    ├── oms-boot                    # 订单服务-启动应用
├── mall-ums                        # 会员服务(User Management Service)
    ├── ums-api                     # 会员服务-Feign客户端
    ├── ums-boot                    # 会员服务-启动应用
├── youlai-auth                     # 认证授权中心
├── youlai-common                   # 公共模块
    ├── common-core                 # 基础依赖
    ├── common-log                  # 日志公共模块
    ├── common-mybatis              # Mybatis 公共模块
    ├── common-rabbitmq             # RabbitMQ 公共模块
    ├── common-redis                # Redis 公共模块
    ├── common-seata                # Seata 公共模块
    ├── common-security             # 资源服务器安全公共模块
    ├── common-web                  # Web 公共模块
├── youlai-gateway                  # 网关
├── youlai-system                   # 系统服务
    ├── system-api                  # 系统Feign接口
    ├── system-boot                 # 系统管理接口
└── end       
```

## 🌌启动项目

### 环境要求

- JDK 17
- MySQL 8 或 MySQL 5.7
- Nacos 2.2+

### 安装中间件

|          | Windows                                                      | Linux                                                        | 是否必装     |
| -------- | ------------------------------------------------------------ | ------------------------------------------------------------ | ------------ |
| Nacos    | [Windows 安装 Nacos 2.2](https://youlai.blog.csdn.net/article/details/130864925) | [Linux 安装 Nacos 2.3](https://youlai.blog.csdn.net/article/details/132592040) | 是           |
| MySQL    | [Windows 安装 MySQL 8](https://youlai.blog.csdn.net/article/details/133272887) | [Linux 安装 MySQL8](https://youlai.blog.csdn.net/article/details/130398179) | 否(建议安装) |
| Redis    | [Windows 安装 Redis](https://youlai.blog.csdn.net/article/details/133410293) | [Linux 安装 Redis](https://youlai.blog.csdn.net/article/details/130439335) | 否(建议安装) |
| Seata    | [Windows 安装 Seata 1.6](https://youlai.blog.csdn.net/article/details/133295970) | [Linux 安装 Seata 1.7](https://youlai.blog.csdn.net/article/details/133376131) | 否           |
| RabbitMQ | /                                                            | [Linux 安装 RabbitMQ](https://blog.csdn.net/u013737132/article/details/130439122) | 否           |

💡默认中间件使用有来线上的环境，其中线上 MySQL 数据是只读的，如果需要进行修改或删除操作，建议自己安装 MySQL。

### 初始化数据库

进入 `docs/sql` 目录 ， 根据 MySQL 版本选择对应的脚本；

先执行 [database.sql](docs%2Fsql%2Fmysql8%2Fdatabase.sql) 完成数据库的创建；

再执行 [youlai_system.sql](docs%2Fsql%2Fmysql8%2Fyoulai_system.sql) 、[youlai_oauth2_server.sql](docs%2Fsql%2Fmysql8%2Foauth2_server.sql)、mall_*.sql 完成数据表的创建和数据初始化。

### 导入 Nacos 配置

打开浏览器，地址栏输入 Nacos 控制台的地址 [ http://localhost:8848/nacos]( http://localhost:8848/nacos) ；

输入用户名/密码：nacos/nacos ；

进入控制台，点击左侧菜单 `配置管理` → `配置列表` 进入列表页面，点击 `导入配置`
选择项目中的 `docs/nacos/nacos_config.zip` 文件。

### 修改 Nacos 配置

在共享配置文件 youlai-common.yaml 中，包括 MySQL、Redis、RabbitMQ 和 Seata 的连接信息，默认是有来线上的环境。

如果您有自己的环境，可以按需修改相应的配置信息。

如果没有自己的 MySQL、Redis、RabbitMQ 和 Seata 环境，可以直接使用默认的配置。

### 启动服务

- 进入 `youlai-gateway` 模块的启动类 GatewayApplication 启动网关；

- 进入 `youlai-auth` 模块的启动类 AuthApplication 启动认证授权中心；

- 进入 `youlai-system`  → `system-boot` 模块的启动类 SystemApplication 启动系统服务；

- 至此完成基础服务的启动，商城服务按需启动，启动方式和 `youlai-system` 一致;

- 访问接口文档地址测试:  [http://localhost:9999/doc.html](http://localhost:9999/doc.html)


## 📝开发文档

- [Spring Authorization Server 扩展 OAuth2 密码模式](https://youlai.blog.csdn.net/article/details/134024381)
- [Spring Cloud Gateway + Knife4j 网关聚合和 OAuth2 密码模式测试](https://youlai.blog.csdn.net/article/details/134081509)


## 💖加交流群

> 关注公众号【有来技术】，获取交流群二维码，二维码过期请加我微信(`haoxianrui`)备注“有来”，我拉你进群。

| ![](https://s2.loli.net/2022/11/19/OGjum9wr8f6idLX.png) |
|---------------------------------------------------------|

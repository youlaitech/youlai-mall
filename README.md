![](https://img.shields.io/badge/SpringBoot-2.3.3-brightgreen.svg)
![](https://img.shields.io/badge/SpringCloud-Hoxton.SR8-green.svg)

## **项目简介**

youlai-mall是集成当前最新主流技术一套开源的商城系统。整个系统采用微服务架构，前后端分离交互模式。业务模块包括了商品管理、订单管理、营销管理、会员管理等。

## **项目结构**

``` lua
youlai-mall
├── document
    ├── youlai.sql      -- 数据库初始化脚本
    ├── youlai-mall.sql -- 数据库初始化脚本
├── mall-oms
    ├── mall-oms-api -- 订单微服务的远程调用客户端
    ├── mall-oms-biz -- 订单微服务
├── mall-pms
    ├── mall-pms-api -- 商品微服务的远程调用客户端
    ├── mall-pms-biz -- 商品微服务
├── mall-sms
    ├── mall-sms-api -- 营销微服务的远程调用客户端
    ├── mall-sms-biz -- 营销微服务
├── mall-ums
    ├── mall-ums-api -- 会员微服务的远程调用客户端
    ├── mall-ums-biz -- 会员微服务
├── youlai-admin 
    ├── youlai-admin-api -- 后台管理微服务的远程调用客户端
    ├── youlai-admin-biz -- 后台管理微服务
├── youlai-auth     -- 认证中心
├── youlai-common   -- 公共模块
└── youlai-gateway  -- API网关
└── youlai-registry -- 注册中心 
```

## **技术栈**

| 后端技术 |  版本号                     
| -------------------- |  -------------------- |                             
| SpringBoot|2.3.3.RELEASE                      
| SpringCloud|Hoxton.SR8
| SpringCloud Alibaba|  2.2.1.RELEASE
| Spring Security OAuth2| 2.2.2.RELEASE
| MyBatis Plus|3.4.0
| Druid| 1.1.23
| Lombok |1.18.12
| Knife4j | 2.0.4


| 管理前端技术 |  版本号
| -------------------- |  -------------------- |  
| Vue        | 2.6.10
| Element-UI | 2.13.2

| 微信小程序技术 |  版本号
| -------------------- |  -------------------- |  
| Vue| 2.6.10
| uni-app | 2.8.11

## **快速启动**

### 1. 启动管理后台服务

1. 拉取[youlai-mall](https://github.com/hxrui/youlai-mall) 代码并导入，项目依赖Lombok插件，如果IDEA未安装请至插件市场安装后重启IDEA。
2. 创建数据库youlai、youlai-mall数据库，导入document下对应的sql脚本文件完成表结构和数据的初始化，数据库版本MySQL8.0，低版本修改脚本即可。 
3. 创建数据库nacos，导入youlai-registry/nacos/conf/nacos-mysql.sql脚本文件 ，并修改youlai-registry/nacos/conf/application.properties的数据库连接信息
4. 修改微服务数据库连接信息，打开youlai-registry/data/config-data/DEFAULT_GROUP目录修改对应yaml文件的数据库连接信息，MySQL默认用户名/密码是root/123456,一致请忽略此步骤。
5. 启动nacos服务，cmd切换到youlai-registry/nacos/bin，执行命令 startup -m standalone。nacos启动后在控制台导入配置document/DEFAULT_GROUP.zip。
6. 启动youlai-gateway、youlai-auth,youlai-admin等微服务。


### 2. 启动管理后台前端

1. 拉取[youlai-mall-admin](https://github.com/hxrui/youlai-mall-admin.git) 代码并导入IDEA
2. npm install  
3. npm run dev
4. 浏览器打开 http://localhost:9527, 输入用户名/密码: admin/123456 登录管理平台

## 项目文档

> 后台微服务
1. [Spring Cloud实战 | 第一篇：Windows搭建Nacos服务 ](https://www.cnblogs.com/haoxianrui/p/13581881.html)
2. [Spring Cloud实战 | 第二篇：Spring Cloud整合Nacos实现注册中心](https://www.cnblogs.com/haoxianrui/p/13584204.html)
3. [Spring Cloud实战 | 第三篇：Spring Cloud整合Nacos实现配置中心](https://www.cnblogs.com/haoxianrui/p/13585125.html)
4. [Spring Cloud实战 | 第四篇：Spring Cloud整合Gateway实现API网关](https://www.cnblogs.com/haoxianrui/p/13608650.html)
5. [Spring Cloud实战 | 第五篇：Spring Cloud整合OpenFeign实现微服务之间的调用](https://www.cnblogs.com/haoxianrui/p/13615592.html)
6. [Spring Cloud实战 | 第六篇：Spring Cloud Gateway+Spring Security OAuth2+JWT实现微服务统一认证授权](https://www.cnblogs.com/haoxianrui/p/13719356.html)
7. [Spring Cloud实战 | 最终篇：Spring Cloud Gateway+Spring Security OAuth2集成统一认证授权平台下实现注销使JWT失效方案](https://www.cnblogs.com/haoxianrui/p/13740264.html)

> 后台管理前端
1. [vue-element-admin实战 | 第一篇： 移除mock接入后台，搭建有来商城youlai-mall前后端分离管理平台](https://www.cnblogs.com/haoxianrui/p/13624548.html)
2. [vue-element-admin实战 | 第二篇： 最小改动接入后台实现根据权限动态加载菜单](https://www.cnblogs.com/haoxianrui/p/13676619.html)

## 问题

项目在搭建的过程中如您遇到任何问题，都可以联系我（微信号：haoxianrui）。


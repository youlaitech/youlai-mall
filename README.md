![](https://img.shields.io/badge/SpringBoot-2.3.3-brightgreen.svg)
![](https://img.shields.io/badge/SpringCloud-Hoxton.SR8-green.svg)

## **项目简介**

youlai-mall是集成当前最新主流技术一套开源的商城系统。整个系统采用微服务架构，前后端分离交互模式。业务模块包括了商品管理、订单管理、营销管理、会员管理等。

## **项目结构**

``` lua
youlai-mall
├── document
    ├── DEFAULT_GROUP.zip  -- Nacos配置中心存储的配置
    ├── youlai.sql -- 数据库初始化脚本
├── youlai-admin 
    ├── youlai-admin-api -- 后台管理服务的远程调用客户端
    ├── youlai-admin-biz -- 后台管理服务
├── youlai-auth -- 认证中心
├── youlai-common -- 公共模块
└── youlai-gateway -- API网关
```

## **技术栈**

| 后端技术 |  版本号                     
| -------------------- |  -------------------- |                             
| SpringBoot|     2.3.3.RELEASE                      
| SpringCloud|  Hoxton.SR8
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

1. 拉取[youlai-mall](https://github.com/hxrui/youlai-mall)代码并导入，项目依赖Lombok插件，如果IDEA未安装请至插件市场安装后重启IDEA。
2. 创建数据库youlai，导入项目包下的doc/youlai.sql完成数据表的创建和初始化。
3. 启动nacos-server，Windows下如何搭建请参考我的这篇文章 [SpringCloud实战 | 第一篇：Windows搭建Nacos服务](https://www.cnblogs.com/haoxianrui/p/13581881.html) 
4. 进入Nacos管控台，依次点击配置管理->配置列表->导入配置，然后选择项目文件下conf/DEFAULT_GROUP.zip完成项目配置文件导入，成功后 编辑配置文件修改数据库连接信息。
5. 分别启动youlai-admin,youlai-auth,youlai-gateway服务。


### 2. 启动管理后台前端

1. 拉取[youlai-mall-admin-web](https://github.com/hxrui/youlai-mall-admin-web)代码并导入IDEA
2. npm install  
3. npm run dev
4. 浏览器打开 http://localhost:9527, 输入用户名/密码: admin/123456 登录管理平台


## 开发计划

> V 1.0.0 完成以下目标：

1. 微服务基础框架搭建
2. 认证中心集成OAuth2+JWT完成认证鉴权
3. 管理后台前端vue-element-admin改造接入后台

## 项目文档

1. [Spring Cloud实战 | 第一篇：Windows搭建Nacos服务 ](https://www.cnblogs.com/haoxianrui/p/13581881.html)
2. [Spring Cloud实战 | 第二篇：Spring Cloud整合Nacos实现注册中心](https://www.cnblogs.com/haoxianrui/p/13584204.html)
3. [Spring Cloud实战 | 第三篇：Spring Cloud整合Nacos实现配置中心](https://www.cnblogs.com/haoxianrui/p/13585125.html)
4. [Spring Cloud实战 | 第四篇：Spring Cloud整合Gateway实现API网关](https://www.cnblogs.com/haoxianrui/p/13608650.html)
5. [Spring Cloud实战 | 第五篇：Spring Cloud整合OpenFeign实现微服务之间的调用](https://www.cnblogs.com/haoxianrui/p/13615592.html)
6. [Spring Cloud实战 | 第六篇：Spring Cloud Gateway+Spring Security OAuth2+JWT实现微服务统一认证授权](https://www.cnblogs.com/haoxianrui/p/13719356.html)
7. [vue-element-admin实战 | 第一篇： 移除mock接入后台，搭建有来商城youlai-mall前后端分离管理平台](https://www.cnblogs.com/haoxianrui/p/13624548.html)
8. [vue-element-admin实战 | 第二篇： 最小改动接入后台实现根据权限动态加载菜单](https://www.cnblogs.com/haoxianrui/p/13676619.html)


## 问题

项目在搭建的过程中如您遇到任何问题，都可以联系我（微信号：haoxianrui）。


![](https://img.shields.io/badge/youlai--mall-v1.0.0-blue)
[![](https://img.shields.io/github/stars/hxrui/youlai-mall.svg?style=social&label=Stars)](https://github.com/hxrui/youlai-mall/stargazers)
[![](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg)](https://github.com/hxrui/youlai-mall/blob/master/LICENSE)
![](https://img.shields.io/badge/SpringBoot-2.4.4-brightgreen.svg)
![](https://img.shields.io/badge/SpringCloud-2020-green.svg)
![](https://img.shields.io/badge/vue--element--admin-v4.4.0-orange)

## 项目介绍

`youlai-mall` 是基于Spring Boot 2.4、Spring Cloud 2020 & Alibaba、Vue、element-ui、uni-app快速构建的一套**全栈**开源商城平台，包括微服务应用、管理平台、微信小程序及APP应用

## 项目特色

- 项目使用都是最新主流的**开源**框架，无过度自定义封装的逻辑，易理解上手和方便扩展

- 基于Spring Boot 2.4、Spring Cloud 2020 & Alibaba 一站式微服务解决方案快速开发分布式服务

- 实现Spring Cloud OAuth2、Spring Cloud Gateway、JWT分布式统一认证鉴权和`RBAC` 权限系统设计

- 使用vue-element-admin的后台前端解决方案，基于Vue和element-ui快速搭建**前后端分离**的商城管理平台

- 通过`uni-app`使用Vue开发实现跨所有前端的应用，包含微信小程序、APP应用

- 使用Docker快速构建项目环境和一键打包部署微服务项目

## 项目愿景

项目从`0`到`1`的构建过程已无保留的在项目文章中说明，真正的开源不图利益。

项目慢慢成型的路上离不开支持还有默默点star的那些小伙伴，在这里抱拳谢过各位道友了。

## 项目地址

### 1. 体验 
**商城管理平台访问地址:** [http://www.youlai.tech](http://47.117.115.107/)
 
【有来小店】微信小程序体验码，扫描后申请体验看到直接通过

 交流群二维码过期，加我微信我直接拉你进群

![](https://gitee.com/haoxr/image/raw/master/default/%E5%9B%A2%E9%98%9FLOGO_%E5%89%AF%E6%9C%AC.png)

### 2. 源码
 项目名称 | Github | 码云
 ---|---|---
 微服务后台 |[youlai-mall](https://github.com/hxrui/youlai-mall) |[youlai-mall](https://gitee.com/youlaitech/youlai-mall) 
 管理前端 |  [youlai-mall-admin](https://github.com/hxrui/youlai-mall-admin)| [youlai-mall-admin](https://gitee.com/youlaitech/youlai-mall-admin)
 微信小程序 | [youlai-mall-weapp](https://github.com/hxrui/youlai-mall-weapp)| [youlai-mall-weapp](https://gitee.com/youlaitech/youlai-mall-weapp) 
 APP应用 | [youlai-mall-app](https://github.com/hxrui/youlai-mall-app)| [youlai-mall-app](https://gitee.com/youlaitech/youlai-mall-app)

## 项目预览

### 1. 商城管理平台

#### 1.1 系统管理
![](https://gitee.com/haoxr/image/raw/master/default/%E7%B3%BB%E7%BB%9F%E7%AE%A1%E7%90%86-%E5%8E%8B%E7%BC%A9.jpg)
#### 1.2 商品管理
![](https://gitee.com/haoxr/image/raw/master/default/%E5%95%86%E5%9F%8E%E7%AE%A1%E7%90%86.jpg)

### 2. 微信小程序

![](https://gitee.com/haoxr/image/raw/master/default/DGAf84rIWwNFqJE.jpg)

## 项目结构

``` lua
youlai-mall
├── document
    ├── nacos -- Nacos配置文件
    ├── sql   -- mysql数据库脚本
├── mall-oms
    ├── oms-api -- 订单中心对外Feign接口
    ├── oms-boot -- 订单中心
├── mall-pms
    ├── pms-api -- 商品中心对外Feign接口
    ├── pms-boot -- 商品中心
├── mall-sms
    ├── sms-api -- 营销中心对外Feign接口
    ├── sms-boot -- 营销中心
├── mall-ums
    ├── ums-api -- 会员中心对外Feign接口
    ├── ums-boot -- 会员中心
├── youlai-admin 
    ├── admin-api -- 系统管理对外Feign接口
    ├── admin-boot -- 系统管理
├── youlai-auth     -- 认证中心【Oauth2认证服务器】
├── youlai-common   -- 公共模块
└── youlai-gateway  -- Gateway网关【Oauth2资源服务器】
└── youlai-middleware -- Nacos应用
```

## 核心技术栈
| 后端技术 |  版本号                     
| -------------------- |  -------------------- |                             
| SpringBoot|2.4.4                    
| Spring Cloud|2020.0.2
| Spring Cloud Alibaba| 2020.0.RC1
| Nacos| 2.0.0
| Seata| 1.4.1
|Sentinel | 1.8.1
| MyBatis-Plus|3.4.0
| Lombok |1.18.18
| Hutool |5.5.8
| Knife4j | 2.0.8
| MinIO | 7.1.0

| 后台前端 |  版本号 | 微信小程序 |  版本号| APP |  版本号
|---- |  ---- | ---- | ----  |---- | ----
| element-ui | 2.13.2 | uni-app | 2.8.11| vant | 2.5.4

## 项目启动

### 1. 后台微服务启动 

####  云环境项目启动

项目依赖环境（MySQL8、Redis、MinIO、Nacos）默认均使用`有来技术`云环境，项目启动极其方便，步骤如下：

1. **启动`Nacos`服务**

    IDEA下方工具栏点击Terminal终端命令行，执行`cd youlai-middleware/nacos/bin`命令切换到Nacos的启动脚本文件夹下，然后执行`startup -m 
   standalone`命令启动Nacos服务；

2. **启动平台基础服务**

    分别启动`youlai-gateway`、`youlai-auth`、 `youlai-admin`模块，
    启动类分别对应的是GatewayApplication、AuthApplication以及`youlai-admin`的子模块`admin-boot`的AdminApplication类，至此完成整个项目的启动；

3. 至此后台服务启动完毕，如需商城服务，启动对应模块的子模块biz的启动类即可。

**注**： 云环境是无条件的提供给大家，但千万不要改动云环境的数据和配置，因为改动会导致整个项目无法运行，考虑下开发人员和其他小伙伴，手下留情。


####  本地环境项目启动

云环境不能改动数据和配置，如需修改，建议本地环境搭建启动，步骤如下：

1. **安装环境**

    安装`MySQL8`、`Redis`、`MinIO`，其中`MinIO`按需选装
    
2. **创建数据库**
    
    - 新建平台数据库，执行项目`document/sql`下的SQL脚本完成数据库创建，基础sql脚本为`youlai.sql`，商城脚本为`mall-*`，商城数据库按需创建
   
    - 创建`Nacos`数据库，执行脚本`youlai-middleware/nacos/conf/nacos-mysql.sql`完成`Nacos`数据库的初始化
    
3. **Nacos配置**
    
    - 修改`Nacos`数据源，进入配置`youlai-middleware/nacos/conf/application.properties`将数据源修改为自己的环境连接
    
    - 导入`Nacos`配置，在启动`Nacos`服务进入控制台导入`document/nacos/DEFAULT_GROUP.zip`配置，然后分别进入各个微服务配置修改Redis、MySQL、MinIO以及微服务的注册IP
   
4. 至此环境配置准备完毕，接下来按照云环境`启动平台基础服务`步骤启动服务即可。

### 2. 后台前端启动 

1. 本机安装Python和Node环境
2. npm install
3. npm run dev
4. 访问 http://localhost:9527

### 3. 微信小程序启动

1. 下载`HBuilder X`和`微信开发者工具`
2. 微信公众平台申请小程序，获得小程序的AppID
3. `微信开发者工具`微信扫码登录，开启服务端口，点击工具栏`设置`->`安全设置`->`安全`->`服务端口`选择打开
4. `Hbuilder X`替换项目AppID成自己的，点击`manifest.json`文件->微信小程序配置
5.  Nacos控制台修改`youlai-auth`配置中的微信小程序AppID和AppSecret为自己申请的小程序
6. `Hbuilder X`工具栏点击 `运行`->`运行到小程序模拟器`->`微信开发者工具`

## 项目文档

> 后台微服务

1. [Spring Cloud实战 | 第一篇：Windows搭建Nacos服务 ](https://www.cnblogs.com/haoxianrui/p/13581881.html)
2. [Spring Cloud实战 | 第二篇：Spring Cloud整合Nacos实现注册中心](https://www.cnblogs.com/haoxianrui/p/13584204.html)
3. [Spring Cloud实战 | 第三篇：Spring Cloud整合Nacos实现配置中心](https://www.cnblogs.com/haoxianrui/p/13585125.html)
4. [Spring Cloud实战 | 第四篇：Spring Cloud整合Gateway实现API网关](https://www.cnblogs.com/haoxianrui/p/13608650.html)
5. [Spring Cloud实战 | 第五篇：Spring Cloud整合OpenFeign实现微服务之间的调用](https://www.cnblogs.com/haoxianrui/p/13615592.html)
6. [Spring Cloud实战 | 第六篇：Spring Cloud Gateway+Spring Security OAuth2+JWT实现微服务统一认证授权](https://www.cnblogs.com/haoxianrui/p/13719356.html)
7. [Spring Cloud实战 | 最七篇：Spring Cloud Gateway+Spring Security OAuth2集成统一认证授权平台下实现注销使JWT失效方案](https://www.cnblogs.com/haoxianrui/p/13740264.html)
8. [Spring Cloud实战 | 最八篇：Spring Cloud +Spring Security OAuth2+ Vue前后端分离模式下无感知刷新实现JWT续期](https://www.cnblogs.com/haoxianrui/p/14022632.html)
9. [Spring Cloud实战 | 最九篇：Spring Security OAuth2认证服务器统一认证自定义异常处理](https://www.cnblogs.com/haoxianrui/p/14028366.html)
10. [Spring Cloud实战 | 第十篇 ：Spring Cloud + Nacos整合Seata 1.4.1最新版本实现微服务架构中的分布式事务，进阶之路必须要迈过的槛](https://www.cnblogs.com/haoxianrui/p/14280184.html)
11. [Spring Cloud实战 | 第十一篇 ：Spring Cloud Gateway网关实现对RESTful接口权限和按钮权限细粒度控制
    ](https://www.cnblogs.com/haoxianrui/p/14396990.html)

> 后台管理前端

1. [vue-element-admin实战 | 第一篇： 移除mock接入微服务接口，搭建SpringCloud+Vue前后端分离管理平台](https://www.cnblogs.com/haoxianrui/p/13624548.html)
2. [vue-element-admin实战 | 第二篇： 最小改动接入后台实现根据权限动态加载菜单](https://www.cnblogs.com/haoxianrui/p/13676619.html)

> 微信小程序

1. [vue+uni-app商城实战 | 第一篇：从0到1快速开发一个商城微信小程序，无缝接入Spring Cloud OAuth2认证授权登录](https://www.cnblogs.com/haoxianrui/p/13882310.html)

> 应用部署

1. [Docker实战 | 第一篇：Linux 安装 Docker](https://www.cnblogs.com/haoxianrui/p/14067423.html)
2. [Docker实战 | 第二篇：Docker部署nacos-server:1.4.0](https://www.cnblogs.com/haoxianrui/p/14059009.html)
3. [Docker实战 | 第三篇：IDEA集成Docker插件实现一键自动打包部署微服务项目，一劳永逸的技术手段值得一试](https://www.cnblogs.com/haoxianrui/p/14088400.html)
4. [Docker实战 | 第四篇：Docker安装Nginx，实现基于vue-element-admin框架构建的项目线上部署](https://www.cnblogs.com/haoxianrui/p/14091762.html) 
5. [Docker实战 | 第五篇：Docker启用TLS加密解决暴露2375端口引发的安全漏洞，被黑掉三台云主机的教训总结](https://www.cnblogs.com/haoxianrui/p/14095306.html)

> 分布式

1. [分布式实战 | 第一篇 ：SpringBoot整合ELK实现分布式登录日志收集和统计](https://www.cnblogs.com/haoxianrui/p/14596252.html)

## 接口文档

本地启动网关`youlai-gateway`和相应的服务，访问 [http://localhost:9999/doc.html](http://localhost:9999/doc.html)

![](https://gitee.com/haoxr/image/raw/master/default/20210225201144.png)


## 其他说明

- 如果github拉取失败请移步至码云[https://gitee.com/haoxr](https://gitee.com/haoxr),代码是同步的

- 项目在搭建的过程中如您遇到任何问题，可加我微信(haoxianrui)或者微信群，也可在github提issue

- 演示环境禁止修改、删除重要数据，请本地部署后操作


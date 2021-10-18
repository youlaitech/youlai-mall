[![](https://img.shields.io/badge/Author-有来技术-orange.svg)](https://gitee.com/wangjiabin-x/uh5)
![](https://img.shields.io/badge/youlai--mall-v2.0.0-blue)
[![](https://img.shields.io/github/stars/hxrui/youlai-mall.svg?style=social&label=Stars)](https://github.com/hxrui/youlai-mall/stargazers)
[![](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg)](https://github.com/hxrui/youlai-mall/blob/master/LICENSE)
![](https://img.shields.io/badge/SpringBoot-2.5.2-brightgreen.svg)
![](https://img.shields.io/badge/SpringCloud-2020-green.svg)
![](https://img.shields.io/badge/vue--element--admin-v4.4.0-orange)

**线上预览地址：** http://www.youlai.tech

## 项目信息

#### 项目简介

[youlai-mall](https://gitee.com/youlaitech/youlai-mall) 是基于Spring Boot 2.5、Spring Cloud 2020 & Alibaba 2021、vue、element-ui、uni-app快速构建的一套全栈开源商城项目。

项目采用微服务、前后端分离开发模式；汇集全栈主流的技术栈； 涉及 [微服务接口](https://gitee.com/youlaitech/youlai-mall) 、 [前端管理](https://gitee.com/youlaitech/youlai-mall-admin) 、 [微信小程序](https://gitee.com/youlaitech/youlai-mall-weapp) 和 [APP应用](https://gitee.com/youlaitech/youlai-mall-weapp) 等多端的开发。

#### 项目特色
- Spring Cloud + Vue + Docker全栈开发
- 项目使用的都是当前主流的技术栈，无过度自定义封装，易学习理解和方便二次扩展
- 基于Spring Boot 2.5.2、Spring Cloud 2020 & Alibaba 2021一站式微服务解决方案实现快速开发
- 完整的Spring Security OAuth2 认证中心统一认证授权，网关统一鉴权逻辑
- 特有一套微服务+前后端分离的RBAC权限设计，实现Spring Cloud Gateway网关下的RESTful接口细粒度的统一鉴权和vue页面按钮级别权限控制
- 基于vue-element-admin的后台前端解决方案，实现动态路由
- 移动端采用uni-app、实现跨多端移动应用开发，包括微信小程序、Android和IOS等
- Docker快速构建项目环境和一键打包部署微服务项目

#### 项目预览

- **系统管理**

| ![image-20210621004954228](https://gitee.com/haoxr/image/raw/master/image-20210621004954228.png) | ![image-20210621005011310](https://gitee.com/haoxr/image/raw/master/image-20210621005011310.png) |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
|![](https://gitee.com/haoxr/image/raw/master/30719657a4b183428a2472231fee55a6_image-20210621005037964.png) | ![image-20210621005123432](https://gitee.com/haoxr/image/raw/master/image-20210621005123432.png) |



- **微信小程序**

| ![](https://gitee.com/haoxr/image/raw/master/%E5%BE%AE%E4%BF%A1%E5%9B%BE%E7%89%87_20210621005253.jpg) | ![](https://gitee.com/haoxr/image/raw/master/%E5%BE%AE%E4%BF%A1%E5%9B%BE%E7%89%87_20210621005338.jpg) | ![](https://gitee.com/haoxr/image/raw/master/%E5%BE%AE%E4%BF%A1%E5%9B%BE%E7%89%87_20210621005331.jpg) |
| ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| ![](https://gitee.com/haoxr/image/raw/master/%E5%BE%AE%E4%BF%A1%E5%9B%BE%E7%89%87_20210621005349.jpg) | ![](https://gitee.com/haoxr/image/raw/master/%E5%BE%AE%E4%BF%A1%E5%9B%BE%E7%89%87_20210621005356.jpg) | ![](https://gitee.com/haoxr/image/raw/master/%E5%BE%AE%E4%BF%A1%E5%9B%BE%E7%89%87_20210621005344.jpg) |


#### 项目架构流程图

![](https://gitee.com/haoxr/image/raw/master/default/OAuth2.jpg)

#### 技术栈

- **后端技术栈：** Spring Boot、Spring Cloud、Spring Cloud Alibaba、Spring Security OAuth2、JWT、Mybatis-Plus、Seata、Sentinel、ELK、Redis

- **前端技术栈：** vue、element-ui、uni-app、vue-element-admin

#### 项目地址

| 项目名称   | 源码地址                                                        |项目名称   | 源码地址                                                   |
| ---------- | ------------------------------------------------------------ |---------- | ------------------------------------------------------------ |
| 微服务后台 | [youlai-mall](https://gitee.com/youlaitech/youlai-mall)      | 微信小程序 | [youlai-mall-weapp](https://gitee.com/youlaitech/youlai-mall-weapp) |
| 管理前端   | [youlai-mall-admin](https://gitee.com/youlaitech/youlai-mall-admin) |APP应用    | [youlai-mall-app](https://gitee.com/youlaitech/youlai-mall-app) |

#### 项目结构 

``` lua
youlai-mall
├── docs
    ├── nacos -- Nacos配置文件
    ├── sql   -- mysql数据库脚本
├── mall-oms
    ├── oms-api -- 订单中心Feign接口客户端
    ├── oms-boot -- 订单中心
├── mall-pms
    ├── pms-api -- 商品中心Feign接口客户端
    ├── pms-boot -- 商品中心
├── mall-sms
    ├── sms-api -- 营销中心Feign接口客户端
    ├── sms-boot -- 营销中心
├── mall-ums
    ├── ums-api -- 会员中心Feign接口客户端
    ├── ums-boot -- 会员中心
├── middleware -- 中间件（Nacos、Sentinel）
├── youlai-admin 
    ├── admin-api -- 系统管理服务Feign接口客户端
    ├── admin-boot -- 系统管理服务
├── youlai-auth     -- 认证中心【OAuth2认证服务器】
├── youlai-common   -- 公共模块
└── youlai-gateway  -- Gateway网关【OAuth2资源服务器】
```

## 项目启动



### 后台微服务启动

> 后台微服务启动有云环境和本地环境两种方式。为了方便大家快速启动看到效果，`有来技术团队`提供在线的云环境，无需安装项目依赖的中间件。如需切换到你自己的环境，在Nacos控制台修改公共配置文件`youlai-common.yaml`的MySQL、Redis、RabbitMQ等中间件的连接信息即可。

`
✨温馨提示✨ 云环境是团队无条件提供出来方便启动测试，风险多大应该都懂，希望大家多多爱护不要钻漏洞和修改数据，如果时间条件允许，
建议在自己的本地环境启动。
`

#### 一. 云环境启动
1. **Nacos 启动和导入配置**

   - 启动Nacos  
      
     IDEA底部工具栏点击Terminal终端命令行，执行`cd middleware/nacos/bin`命令切换到`Nacos`的启动脚本目录下，执行`startup -m standalone`命令启动`Nacos`服务。
    
     ```✨温馨提示✨ Nacos 默认有内置数据库，这里就先用默认，如有切换MySQL的需要，下文本地环境有说明。```
     
   - 导入配置
     
     浏览器输入 http://localhost:8848/nacos ,输入用户名/密码:nacos/nacos 进入控制台，在 配置管理→配置列表 页面选择项目里的文件`docs/nacos/DEFAULT_GROUP.zip`导入。

     ![](https://gitee.com/haoxr/image/raw/master/20210623012937.png)
    
        
2. **微服务启动**

    进入`youlai-gateway`、`youlai-auth`、 `youlai-admin`3个基础服务，找到对应的启动类，
分别是GatewayApplication、AuthApplication以及`youlai-admin`的子模块`admin-boot`的AdminApplication类，商城模块按需启动。 浏览器访问 http://localhost:9999/doc.html 项目的接口文档，如果界面显示正常，代表服务启动成功。
   

#### 二. 本地环境启动
1. **安装环境**

   安装`MySQL`、`Redis`中间件，MySQL的版本5.x和8.x都可以

2. **创建数据库**
    - 新建平台数据库，进入项目`docs/sql`，根据MySQL版本选择，先执行`database.sql`创建数据库，再执行`youlai.sql`、`mall_*.sql`完成表的创建和数据的导入。
    - 创建`Nacos`数据库，执行脚本`middleware/nacos/conf/nacos-mysql.sql`完成`Nacos`数据库的初始化。

3. **Nacos 启动和配置**

    - 修改`Nacos`数据源，进入配置`middleware/nacos/conf/application.properties`将数据源修改为您自己的环境；
   
    - 启动`Nacos`,  IDEA下方工具栏点击Terminal终端命令行，执行`cd middleware/nacos/bin`命令切换到`Nacos`的启动脚本文件夹下，然后执行`startup -m standalone`命令启动`Nacos`服务；

    -  浏览器输入 http://localhost:8848/nacos ,输入用户名/密码:nacos/nacos 进入控制台，在 配置管理→配置列表 页面选择项目里的文件`docs/nacos/DEFAULT_GROUP.zip`导入。

       ![](https://gitee.com/haoxr/image/raw/master/20210623012937.png)

    - 导入`Nacos`配置，在启动`Nacos`服务进入控制台导入`docs/nacos/DEFAULT_GROUP.zip`配置，进入共享配置`youlai-common.yaml`修改MySQL、Redis、RabbitMQ等中间件的信息，默认是`有来技术团队`云服务器环境。
      ![](https://gitee.com/haoxr/image/raw/master/default/20210829124804.png)
  

4. `Nacos`启动完成和MySQL、Redis连接信息修改完成后，分别启动`youlai-gateway`、`youlai-auth`、 `youlai-admin`模块，
   启动类分别对应的是GatewayApplication、AuthApplication以及`youlai-admin`的子模块`admin-boot`的AdminApplication类，至此完成整个项目基础服务的启动；

### 管理前端启动

1. 本机安装Node环境
2. npm install
3. npm run dev
4. 访问 http://localhost:9527

✨温馨提示✨ `npm install` 有些依赖包需从github拉取，网络不好会有失败的情况，可以切换好点的网络或者找其他童鞋要一份完整的`node_modules`依赖包导入。
![](https://gitee.com/haoxr/image/raw/master/default/20210829131310.png)

### 微信小程序启动

1. 下载`HBuilder X`和`微信开发者工具`
2. 微信公众平台申请小程序，获得小程序的AppID
3. `微信开发者工具`微信扫码登录，开启服务端口，点击工具栏`设置`->`安全设置`->`安全`->`服务端口`选择打开
4. `Hbuilder X`替换项目AppID成自己的，点击`manifest.json`文件->微信小程序配置
5. Nacos控制台修改`youlai-auth`配置中的微信小程序AppID和AppSecret为自己申请的小程序
6. `Hbuilder X`工具栏点击 `运行`->`运行到小程序模拟器`->`微信开发者工具`

![](https://gitee.com/haoxr/image/raw/master/default/20210829131825.png)

## 接口测试

#### Spring Security OAuth2认证授权接口

- **Postman**

     - 请求参数类型为Query Param或者form-data，出现很多错误的情况是将参数是JSON格式放在请求Body中
     - Spring Security OAuth2新版本不再支持将客户端信息client_id和client_secret放在请求路径的这种方式，否者会出现401的错误（已验证）
     - OAuth2客户端信息在Authorization标签选择Basic Auth然后填写client_id和client_secret

  | Query Params参数                                             | Authorization                                                |
    | ------------------------------------------------------------ | ------------------------------------------------------------ |
  | ![](https://gitee.com/haoxr/image/raw/master/image-20210621075338100.png) | ![image-20210621075517108](https://gitee.com/haoxr/image/raw/master/image-20210621075517108.png) |

- **Knife4j接口文档（推荐）**

    - 接口文档地址，启动网关访问 http://localhost:9999/doc.html (默认)
    - 请求接口之前，先执行对应模块下的第一个接口Authorize完成认证，通过后再打开其他接口请求头会**自动**填充token
    - client/123456 是**有来项目**预留用于测试的客户端信息，因为Knife4j完成自动填充不能包装返回值，和大多数实际项目需包装返回值添加状态码不符

| 认证授权                                                     | 认证成功自动填充Header                                       |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| ![image-20210622000304570](https://gitee.com/haoxr/image/raw/master/image-20210622000304570.png) | ![image-20210622000046029](https://gitee.com/haoxr/image/raw/master/image-20210622000046029.png) |

## 项目文档

[项目文档地址](https://www.cnblogs.com/haoxianrui/)

## Star趋势
- Github
[![Github](https://starchart.cc/hxrui/youlai-mall.svg)](https://starchart.cc/hxrui/youlai-mall)
- Giteefv
[![Gitee](https://whnb.wang/stars/youlaitech/youlai-mall)](https://whnb.wang/stars/youlaitech/youlai-mall)

## contributors
[![contributors](https://whnb.wang/contributors/youlaitech/youlai-mall)](https://whnb.wang/contributors/youlaitech/youlai-mall)


## 联系信息
因为微信交流群满200人只能通过邀请进入，如想进入交流群学习可先添加开发人员，备注“**有来**“由其拉进群。



| ![](https://gitee.com/haoxr/image/raw/master/default/113__6c5ed5b1b73ea9cd4cf32848ed350c07_b9b214638a2a406e52dbf51e9bf9a2ef.png) | ![](https://gitee.com/haoxr/image/raw/master/hxr.jpg)        | ![](https://gitee.com/haoxr/image/raw/master/huawei.jpg)     | ![](https://gitee.com/haoxr/image/raw/master/default/1625149769(1).png) |
| ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| ![](https://gitee.com/haoxr/image/raw/master/default/7488479b1e2c193b04b56d1e0ff640c.jpg) | ![image-20210701232803265](https://gitee.com/haoxr/image/raw/master/default/image-20210701232803265.png) | ![](https://gitee.com/haoxr/image/raw/master/default/20210701234946.png) | ![](https://gitee.com/haoxr/image/raw/master/default/image-20210702002909113.png) |
<p align="center">
    <img src="https://img.shields.io/badge/SpringBoot-2.7.8-brightgreen.svg"/>
    <img src="https://img.shields.io/badge/SpringCloud & Alibaba -2021-green.svg"/>
    <a src="https://github.com/hxrui" target="_blank">
        <img src="https://img.shields.io/github/stars/youlaitech/youlai-mall.svg?style=social&label=Stars"/>
    </a>
    <a href="https://gitee.com/youlaitech/youlai-mall" target="_blank">
        <img src="https://gitee.com/youlaitech/youlai-mall/badge/star.svg"/>
    </a> 
    <br/>
    <img src="https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg"/>
    <a href="https://gitee.com/youlaiorg" target="_blank">
        <img src="https://img.shields.io/badge/Author-有来开源组织-orange.svg"/>
    </a>
</p>

<p align="center">
<a target="_blank" href="https://www.youlai.tech">官方文档</a> |
<a target="_blank" href="https://www.cnblogs.com/haoxianrui/"> 团队博客</a> 
</p>

## 🈶项目简介

[youlai-mall](https://gitee.com/haoxr) 是基于Spring Boot 2.7、Spring Cloud 2021 & Alibaba 2021、Vue3、Element-Plus、uni-app等全栈主流技术栈构建的开源商城项目，涉及 [后端微服务](https://gitee.com/youlaitech/youlai-mall)、 [前端管理](https://gitee.com/youlaitech/youlai-mall-admin)、 [微信小程序](https://gitee.com/youlaitech/youlai-mall-weapp)和 [APP应用](https://gitee.com/youlaitech/youlai-mall-weapp)等多端的开发。

##  ✨项目特色

- 项目使用皆是当前主流前后端技术栈(持续更新...)，无过度自定义封装，易理解学习和二次扩展；
- 极速启动模式，无条件提供线上环境，1分钟之内拥有微服务环境；
- SpringBoot 2.7、SpringCloud 2021 & Alibaba 2021 一站式微服务开箱即用的解决方案；
- Spring Security OAuth2 、 Spring Cloud Gateway 、 JWT 统一认证鉴权和常用 OAuth2 授权模式扩展；
- 移动端采用终极跨平台解决方案 uni-app， 一套代码编译iOS、Android、H5和小程序等多个平台；
- Jenkins、K8s、Docker实现微服务持续集成与交付(CI/CD)。

## 💎在线演示

| 项目       | 演示地址                                      | 备用地址                                 |
| ---------- | --------------------------------------------- | ---------------------------------------- |
| 商城管理端 | https://admin.youlai.tech                     | http://vue3.youlai.tech                  |
| 移动应用端 | http://app.youlai.tech                        |                                          |
| 接口文档   | https://api.youlai.tech/doc.html              |                                          |
| 官网地址   | https://www.youlai.tech                       | https://doc.youlai.tech                  |
| 官方博客   | [博客园](https://www.cnblogs.com/haoxianrui/) | [CSDN](https://blog.csdn.net/u013737132) |

## 💫源码地址

| 项目       | Gitee                                                  | Github                                                   | GitCode |
| ---------- | ------------------------------------------------------ | -------------------------------------------------------- | ------- |
| 微服务后端 | [youlai-mall](https://gitee.com/youlaiorg/youlai-mall) | [youlai-mall](https://github.com/youlaitech/youlai-mall) | -       |
| 商城管理端 | [mall-admin](https://gitee.com/youlaiorg/mall-admin)   | [mall-admin](https://github.com/youlaitech/mall-admin)   | -       |
| 移动应用端 | [mall-app](https://gitee.com/youlaiorg/mall-app)       | [mall-app](https://github.com/youlaitech/mall-app)       | -       |

##  🗂项目结构

```
youlai-mall
├── docs  
    ├── nacos      -- Nacos配置
    ├── sql        -- SQL脚本
├── laboratory     -- 实验室
├── mall-oms       -- 订单服务
├── mall-pms       -- 商品服务
├── mall-sms       -- 营销服务
├── mall-ums       -- 会员服务
├── middleware     -- 中间件(nacos/seata)
├── youlai-auth    -- OAuth2认证授权中心
├── youlai-common  -- 公共依赖
├── youlai-gateway -- 网关
├── youlai-system  -- 系统服务
└── end
```

## 🥇极速启动

> `极速启动` 是方便快速启动查看效果的启动方式，其中的数据库和Redis等中间件使用的是有来提供的云环境，切勿修改数据，有时间条件建议`本地启动`。

1. **启动 Nacos**


- IDEA 打开命令行终端 Terminal，输入 `cd middleware/nacos/bin` 切换到 Nacos 的 bin 目录，执行 `startup -m standalone` 启动 Nacos 服务。


2. **服务启动**

   - `youlai-gateway` 模块的启动类 GatewayApplication 启动网关；

   - `youlai-auth` 模块的启动类 AuthApplication 启动认证中心；

   - `youlai-system`  → `system-boot` 模块的启动类 SystemApplication 启动系统服务；

   - 至此完成基础服务的启动，商城服务按需启动，启动方式和 `youlai-system` 一致；

   - 访问接口文档地址测试: [http://localhost:9999/doc.html](http://localhost:9999/doc.html)

## 🥈本地启动

1. **中间件安装**

   > 为了避免数据和线上环境冲突，MySQL 和 Redis 必装，其他不安装可默认使用有来线上环境(🔴必装 ⚪可选)

   - 🔴 MySQL &nbsp;&nbsp;[Linux部署](https://www.youlai.tech/pages/vjoqc/)
   - 🔴 Redis &nbsp;&nbsp;[Linux部署](https://www.youlai.tech/pages/k2a20/)
   - ⚪ RabbitMQ &nbsp;&nbsp;[Linux部署](https://www.youlai.tech/pages/8znee/)
   - ⚪ Seata &nbsp;&nbsp;[本地启动](https://www.youlai.tech/pages/0bzvi/) &nbsp; |&nbsp;[Linux部署](https://www.youlai.tech/pages/4vjq5/)
   - ⚪ Sentinel &nbsp;&nbsp;[本地启动]() &nbsp; | &nbsp;[Linux部署]()
   - ⚪ Canal &nbsp;&nbsp;[本地启动]() &nbsp; | &nbsp;[Linux部署]()

2. **数据库创建和数据初始化**

   - **系统数据库**

     进入 `docs/sql` 目录 ， 根据 MySQL 版本选择对应的脚本；

     先执行 `database.sql` 完成数据库的创建；

     再执行 `youlai.sql` 、`oauth2.sql`、`mall_*.sql` 完成数据表的创建和数据初始化。

   - **Nacos 数据库**

     创建名为 `nacos` 的数据库，执行 `middleware/nacos/conf/nacos-mysql.sql` 脚本完成 Nacos 数据库初始化。

3. **Nacos 配置持久化至 MySQL**

   进入项目的 `middleware/nacos/conf/application.properties` 文件修改 Nacos 配置的数据连接，需要修改配置如下：

   ```properties
   spring.datasource.platform=mysql
   db.num=1
   db.url.0=jdbc:mysql://localhost:3306/nacos?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useUnicode=true&useSSL=false&serverTimezone=UTC
   db.user.0=root
   db.password.0=123456
   ```

4. **导入Nacos配置**

   IDEA 打开命令行终端 Terminal，输入 `cd middleware/nacos/bin` 切换到 Nacos 的 bin 目录，执行 `startup -m standalone` 启动 Nacos 服务。

   打开浏览器，地址栏输入 Nacos 控制台的地址 [ http://localhost:8848/nacos]( http://localhost:8848/nacos) ；

   输入用户名/密码：nacos/nacos ；

   进入控制台，点击左侧菜单 `配置管理` → `配置列表` 进入列表页面，点击 `导入配置` 选择项目中的 `docs/nacos/nacos_config.zip` 文件。

5. **修改Nacos配置**

   在 Nacos 控制台配置列表选择共享配置 `youlai-common.yaml` 进行编辑，修改 MySQL、Redis、RabbitMQ等中间件信息为您自己本地环境，默认「有来」线上环境。

6. **修改Nacos配置中心地址**

   批量替换应用的 bootstrap-dev.yml 文件的配置中心地址 `http://f.youlai.tech:8848` → `http://localhost:8848` ，默认「有来」线上的配置中心地址。

7. **服务启动**

   - 进入 `youlai-gateway` 模块的启动类 GatewayApplication 启动网关；

   - 进入 `youlai-auth` 模块的启动类 AuthApplication 启动认证授权中心；

   - 进入 `youlai-system`  → `system-boot` 模块的启动类 SystemApplication 启动系统服务；

   - 至此完成基础服务的启动，商城服务按需启动，启动方式和 `youlai-system` 一致;

   - 访问接口文档地址测试:  [http://localhost:9999/doc.html](http://localhost:9999/doc.html)

##  🧩项目截图

| 「App」Spring Security OAuth2 手机短信验证码模式             | 「小程序」Spring Security OAuth2 微信授权模式                |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| <img src="https://www.youlai.tech/files/blog/smsauth.gif" width="100%" height="400px"/> | <img src="https://www.youlai.tech/files/blog/wechatauth.gif" width="100%" height="400px"/> |
| **「管理前端」Spring Security OAuth2 密码模式**              | **「管理前端」Spring Security OAuth2 验证码模式**            |
| <img src="https://www.youlai.tech/files/blog/passwordauth.gif" width="100%" height="400px"/> | <img src="https://www.youlai.tech/files/blog/captchaauth.gif" width="100%" height="400px"/> |

## 

## 💹趋势统计



[![Star History Chart](https://api.star-history.com/svg?repos=youlaitech/youlai-mall&type=Timeline)](https://star-history.com/#youlaitech/youlai-mall&Timeline)

## 💻贡献者们



<a href="https://github.com/youlaitech/youlai-mall/graphs/contributors"><img src="https://opencollective.com/youlai-mall/contributors.svg?width=890" /></a>



## 💥加交流群

> 群二维码失效添加开发者，备注“有来”进群即可

| 交流群                                                                             | 开发者                                             | 开发者                                               |
|---------------------------------------------------------------------------------|-------------------------------------------------|---------------------------------------------------|
| ![](https://s2.loli.net/2023/05/06/7OJ96qfybNznMaP.png) | ![](https://www.youlai.tech/files/blog/rui.jpg) | ![](https://www.youlai.tech/files/blog/chuan.jpg) |



| ![](https://s2.loli.net/2022/11/19/OGjum9wr8f6idLX.png) |
| ------------------------------------------------------- |



## 📰开源协议

Apache Licence 2.0 是著名的非盈利开源组织Apache采用的协议。该协议鼓励代码共享和尊重原作者的著作权，商用或二次开源须要满足的条件：

- 需要给代码的用户一份Apache Licence。
- 如果你修改了代码，需要在被修改的文件中说明。
- 在延伸的代码中（修改和有源代码衍生的代码中）需要带有原来代码中的协议，商标，专利声明和其他原来作者规定需要包括的说明。
- 如果再发布的产品中包含一个Notice文件，则在Notice文件中需要带有Apache Licence。你可以在Notice中增加自己的许可，但不可以表现为对Apache Licence构成更改。 Apache Licence也是对商业应用友好的许可。使用者也可以在需要的时候修改代码来满足需要并作为开源或商业产品发布/销售。
- 如果你参考或借鉴了本项目的源码，请你在项目的说明文档添加对本项目的引用申明和添加Git仓库地址(https://gitee.com/youlaitech/youlai-mall)。


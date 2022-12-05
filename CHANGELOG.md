# 2.2.0 (2022/12/5)

### 🍏 feat
- 新增 seata 实验室。

# 2.1.4 (2022/11/22)

### 🍎 fix
- token 过期自定义异常未生效问题修复。

### 🍇 refactor
- 删除文件接口入参由文件名修改为文件完整路径。


# 2.1.3 (2022/11/21)

> 升级需更新Nacos配置

### 🍇 refactor
- 文件上传代码重构。

# 2.1.2 (2022/11/14)

> 升级需更新Nacos配置、mall_sms 数据表脚本

### 🍏 feat
- Spring Security 自定义未认证和访问无权限异常；
- 网关开启CORS资源共享允许跨域访问。

### 🍎 fix
- 商品购买、订单支付流程异常等相关问题修复；

### 🍇 refactor
- 营销广告、优惠券代码优化；
- 业务异常类名、包结构修改调增。

# 2.1.1 (2022/11/10)

> 升级需更新Nacos配置

### 🍎 fix
- 注销方法报错问题修复

### 🍇 refactor
- 参数校验validation移动至common-web


# 2.1.0 (2022/11/6)

> 升级需同步更新SQL、Nacos配置和前端应用

### 🍉 build

- SpringBoot 版本升级至2.7.5
- SpringCloud 版本2021.0.0升级至2021.0.5

### 🍏 feat
- 完善部门数据权限，增加本部门和本人数据权限


### 🍎 fix
- 用户名唯一索引，未校验是否存在，导致新增用户失败。 [#9cc6b34](https://gitee.com/youlaitech/youlai-mall/commit/9cc6b340a6761edc01b7917e0b2030636a4b5d52)

### 🍑 docs
- SQL脚本更新
- Nacos配置更新
- README 文档修改

### 🍇 refactor
- 网关统一鉴权移除，token 网关中继各资源服务器进行有效和权限校验；


### 🍌 other

- 认证中心端口 8000 → 9000


# 2.0.0 (2022/1/30)

### 🍉 build

- SpringBoot 版本2.5.4升级至2.6.3
- SpringCloud 版本2020.0.3升级至2021.0.0

### 🍏 feat
- 系统添加数据权限
- 接口适配 Vue3 前端版本
- 动态创建、绑定RabbitMQ队列和交换机(2022/4/5)

### 🍎 fix
- 超级管理员部门为null查询部门列表报错问题

### 🍑 docs
- SQL脚本更新
- Nacos配置文件更新
- 类注释完善

### 🍇 refactor
- Swagger 参数注解精简优化

### 🍌 style 

- 代码格式化，删除无用引用
 




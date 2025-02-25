package com.youlai.common.constant;

/**
 * Redis 常量
 *
 * @author Ray.Hao
 * @since 4.0.0
 */
public interface RedisConstants {


    /**
     * 认证模块
     */
    interface Auth {
        String BLACKLIST_TOKEN = "auth:token:blacklist";  // 黑名单Token
        String JWK_SET = "auth:jwk:set";                 // JWT密钥对
    }

    /**
     * 验证码模块
     */
    interface Captcha {
        String IMAGE_CODE = "captcha:image";              // 图形验证码
        String SMS_LOGIN_CODE = "captcha:sms:login";      // 登录短信验证码
        String SMS_REGISTER_CODE = "captcha:sms:register";// 注册短信验证码
        String MOBILE_CODE = "captcha:mobile";            // 手机验证码（通用）
        String EMAIL_CODE = "captcha:email";              // 邮箱验证码
    }

    /**
     * 会员模块
     */
    interface Member {
        String INFO = "member:info";                     // 会员信息
        String CART = "member:cart";                     // 会员购物车
    }

    /**
     * 订单模块
     */
    interface Order {
        String SUBMIT_TOKEN = "order:submit:token";      // 防重提交令牌
        String PAYMENT_LOCK = "order:payment:lock";      // 支付锁
    }

    /**
     * 商品模块
     */
    interface Product {
        String SKU_LOCK = "product:sku:lock";            // SKU库存锁
    }

    /**
     * 系统模块
     */
    interface System {
        String CONFIG = "system:config";                 // 系统配置
        String ROLE_PERMS = "system:role:perms"; // 系统角色和权限映射
    }


    interface Common {
        String REGION_DATA = "common:region";       // 省市区数据
    }



}

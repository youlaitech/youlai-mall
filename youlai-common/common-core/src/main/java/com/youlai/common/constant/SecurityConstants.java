package com.youlai.common.constant;

import java.util.Arrays;
import java.util.List;

public interface SecurityConstants {

    /**
     * 黑名单token前缀
     */
    String TOKEN_BLACKLIST_PREFIX = "auth:token:blacklist:";


    String USER_NAME_KEY = "username";


    /**
     * 认证身份标识
     */
    String AUTHENTICATION_IDENTITY_KEY = "authenticationIdentity";

    /**
     * 验证码key前缀
     */
    String VALIDATION_CODE_KEY_PREFIX = "CAPTCHA:";

    /**
     * 短信验证码key前缀
     */
    String SMS_CODE_PREFIX = "SMS_CODE:";

    /**
     * 接口文档 Knife4j 测试客户端ID
     */
    String TEST_CLIENT_ID = "client";

    /**
     * 系统管理 web 客户端ID
     */
    String ADMIN_CLIENT_ID = "mall-admin-web";

    /**
     * 移动端（H5/Android/IOS）客户端ID
     */
    String APP_CLIENT_ID = "mall-app";

    /**
     * 微信小程序客户端ID
     */
    String WEAPP_CLIENT_ID = "mall-weapp";


    /**
     * 线上环境放行的请求路径
     */
    List<String> PERMIT_PATHS= Arrays.asList("/youlai-lab","/app-api","/youlai-auth/oauth/logout");

    /**
     * 线上环境禁止的请求路径
     */
    List<String> FORBID_PATHS= Arrays.asList("/youlai-admin/api/v1/menus","/mall-pms/api");




}

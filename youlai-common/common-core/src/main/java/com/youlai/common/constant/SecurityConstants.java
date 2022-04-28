package com.youlai.common.constant;

import java.util.Arrays;
import java.util.List;

public interface SecurityConstants {

    /**
     * 认证请求头key
     */
    String AUTHORIZATION_KEY = "Authorization";

    /**
     * JWT令牌前缀
     */
    String JWT_PREFIX = "Bearer ";


    /**
     * Basic认证前缀
     */
    String BASIC_PREFIX = "Basic ";

    /**
     * JWT载体key
     */
    String JWT_PAYLOAD_KEY = "payload";

    /**
     * JWT ID 唯一标识
     */
    String JWT_JTI = "jti";

    /**
     * JWT ID 唯一标识
     */
    String JWT_EXP = "exp";

    /**
     * 黑名单token前缀
     */
    String TOKEN_BLACKLIST_PREFIX = "auth:token:blacklist:";


    String USER_NAME_KEY = "username";

    String CLIENT_ID_KEY = "client_id";

    /**
     * JWT存储权限前缀
     */
    String AUTHORITY_PREFIX = "ROLE_";

    /**
     * JWT存储权限属性
     */
    String JWT_AUTHORITIES_KEY = "authorities";

    String GRANT_TYPE_KEY = "grant_type";

    String REFRESH_TOKEN_KEY = "refresh_token";

    /**
     * 认证身份标识
     */
    String AUTHENTICATION_IDENTITY_KEY = "authenticationIdentity";

    /**
     * 验证码key前缀
     */
    String VALIDATE_CODE_PREFIX = "VALIDATE_CODE:";

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
     * 小程序端（微信小程序、....） 客户端ID
     */
    String WEAPP_CLIENT_ID = "mall-weapp";


    /**
     * 线上环境放行的请求路径
     */
    List<String> PROD_PERMIT_PATHS= Arrays.asList("/youlai-lab","/app-api","/youlai-auth/oauth/logout");

    /**
     * 线上环境禁止的请求路径
     */
    List<String> PROD_FORBID_PATHS= Arrays.asList("/youlai-admin/api/v1/menus");

    /**
     * 线上环境禁止方法
     */
    List<String> PROD_FORBID_METHODS= Arrays.asList("PUT","DELETE");


}

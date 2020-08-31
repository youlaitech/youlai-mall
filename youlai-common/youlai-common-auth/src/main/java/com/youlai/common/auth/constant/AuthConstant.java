package com.youlai.common.auth.constant;

public interface AuthConstant {

    /**
     * JWT存储权限前缀
     */
    String AUTHORITY_PREFIX="ROLE_";


    /**
     * JWT存储权限属性
     */
    String AUTHORITY_CLAIM_NAME="authorities";

    /**
     * 认证信息Http请求头
     */
    String JWT_TOKEN_HEADER = "Authorization";

    /**
     * JWT令牌前缀
     */
    String JWT_TOKEN_PREFIX = "Bearer ";


    /**
     * 用户信息HTTP请求头
     */
    String USER_TOKEN_HEADER = "user";


    /**
     * 后台管理client_id
     */
    String ADMIN_CLIENT_ID = "admin-client";


    /**
     * 前台client_id
     */
    String PORTAL_CLIENT_ID="portal-client";


    /**
     * 小程序client_id
     */
    String MP_CLIENT_ID="mp-client";


    /**
     * 后台管理接口路径匹配
     */
    String ADMIN_URL_PATTERN="/youlai-service/**";

    /**
     * Redis缓存权限规则key
     */
    String RESOURCE_ROLES_MAP_KEY = "auth:resourceRolesMap";

}

package com.youlai.common.core.constant;

public interface AuthConstants {




    /**
     * 认证信息Http请求头
     */
    String JWT_TOKEN_HEADER = "Authorization";

    /**
     * JWT令牌前缀
     */
    String JWT_TOKEN_PREFIX = "Bearer ";

    /**
     * JWT载体key
     */
    String JWT_PAYLOAD_KEY = "payload";

    /**
     * Redis缓存权限规则key
     */
    String PERMISSION_RULES_KEY = "auth:permission:rules";

    /**
     * 黑名单token前缀
     */
    String TOKEN_BLACKLIST_PREFIX = "auth:token:blacklist:";

    String CLIENT_DETAILS_FIELDS = "client_id, CONCAT('{noop}',client_secret) as client_secret, resource_ids, scope, "
            + "authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, "
            + "refresh_token_validity, additional_information, autoapprove";

    String BASE_CLIENT_DETAILS_SQL = "select " + CLIENT_DETAILS_FIELDS + " from oauth_client_details";

    String FIND_CLIENT_DETAILS_SQL = BASE_CLIENT_DETAILS_SQL + " order by client_id";

    String SELECT_CLIENT_DETAILS_SQL = BASE_CLIENT_DETAILS_SQL + " where client_id = ?";

    /**
     * 密码加密方式
     */
    String BCRYPT = "{bcrypt}";

    String JWT_USER_ID_KEY = "id";

    String JWT_CLIENT_ID_KEY = "client_id";

    /**
     * JWT存储权限前缀
     */
    String AUTHORITY_PREFIX = "ROLE_";

    /**
     * JWT存储权限属性
     */
    String JWT_AUTHORITIES_KEY = "authorities";


    /**
     * 有来商城后台管理客户端ID
     */
    String ADMIN_CLIENT_ID="youlai-admin";


    /**
     * 有来商城微信小程序客户端ID
     */
    String WEAPP_CLIENT_ID="youlai-mall-weapp";

    /**
     * 后台管理接口路径匹配
     */
    String ADMIN_URL_PATTERN ="/youlai-admin/**" ;
}

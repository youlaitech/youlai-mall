package com.youlai.admin.pojo.vo.client;

import lombok.Data;


@Data
public class ClientPageVO {

    /**
     * 客户端ID
     */
    private String clientId;

    /**
     * 客户端密钥
     */
    private String clientSecret;

    /**
     * 资源id集合
     */
    private String resourceIds;

    /**
     * 作用域
     */
    private String scope;

    /**
     * 授权方式
     */
    private String authorizedGrantTypes;

    /**
     * 回调地址
     */
    private String webServerRedirectUri;

    /**
     * 权限集合
     */
    private String authorities;

    /**
     * 认证令牌时效()
     */
    private Integer accessTokenValidity;

    /**
     * 刷新令牌时效(单位:秒)
     */
    private Integer refreshTokenValidity;

    /**
     * 扩展信息
     */
    private String additionalInformation;

    /**
     * 是否自动放行
     */
    private String autoapprove;

}

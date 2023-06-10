package com.youlai.auth.authentication.miniapp;

import lombok.Getter;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationCodeAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationGrantAuthenticationToken;

import java.util.Map;

/**
 * 微信小程序
 *
 * @author haoxr
 * @see OAuth2AuthorizationCodeAuthenticationToken
 * @since 3.0.0
 */
public class WxMiniAppAuthenticationToken extends OAuth2AuthorizationGrantAuthenticationToken {

    /**
     * 授权类型：微信小程序
     */
    public static final AuthorizationGrantType WX_MINI_APP = new AuthorizationGrantType("wx_mini_app");

    @Getter
    private final String code;

    @Getter
    private final String encryptedData;

    @Getter
    private final String iv;


    /**
     * Sub-class constructor.
     *
     * @param clientPrincipal      the authenticated client principal
     * @param additionalParameters the additional parameters
     */
    protected WxMiniAppAuthenticationToken(
            Authentication clientPrincipal,
            Map<String, Object> additionalParameters,
            String code,
            String encryptedData,
            String iv
    ) {
        super(WxMiniAppAuthenticationToken.WX_MINI_APP, clientPrincipal, additionalParameters);
        this.code = code;
        this.encryptedData = encryptedData;
        this.iv = iv;
    }
}

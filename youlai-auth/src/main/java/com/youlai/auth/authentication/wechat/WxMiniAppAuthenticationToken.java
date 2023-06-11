package com.youlai.auth.authentication.wechat;

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
 * @see org.springframework.security.authentication.UsernamePasswordAuthenticationToken
 * @see OAuth2AuthorizationGrantAuthenticationToken
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
     *
     * @param clientPrincipal
     * @param additionalParameters
     * @param code
     * @param encryptedData
     * @param iv
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
        super.setAuthenticated(false);
    }



}

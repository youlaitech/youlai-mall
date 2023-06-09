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
 * @see OAuth2AuthorizationCodeAuthenticationToken
 * @since 3.0.0
 */
public class WeChatMiniProgramAuthenticationToken extends OAuth2AuthorizationGrantAuthenticationToken {

    /**
     * 授权类型：微信小程序
     */
    public static final AuthorizationGrantType WECHAT_MINI_PROGRAM = new AuthorizationGrantType("wechat_mini_program");

    @Getter
    private final String code;

    @Getter
    private final String encryptedData;

    @Getter
    private final String iv;


    /**
     * @see org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames#SCOPE
     */
    private final String scope;


    /**
     * Sub-class constructor.
     *
     * @param clientPrincipal      the authenticated client principal
     * @param additionalParameters the additional parameters
     */
    protected WeChatMiniProgramAuthenticationToken(
            Authentication clientPrincipal,
            Map<String, Object> additionalParameters,
            String code,
            String encryptedData,
            String iv,
            String scope
    ) {
        super(WeChatMiniProgramAuthenticationToken.WECHAT_MINI_PROGRAM, clientPrincipal, additionalParameters);
        this.code = code;
        this.encryptedData = encryptedData;
        this.iv = iv;
        this.scope = scope;
    }
}

package com.youlai.auth.oauth2.extension.smscode;

import jakarta.annotation.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationGrantAuthenticationToken;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 短信验证码身份验证令牌
 *
 * @author Ray Hao
 * @since 3.0.0
 */
public class SmsCodeAuthenticationToken extends OAuth2AuthorizationGrantAuthenticationToken {

    /**
     * 令牌申请访问范围
     */
    private final Set<String> scopes;

    /**
     * 授权类型(短信验证码: sms_code)
     */
    public static final AuthorizationGrantType SMS_CODE = new AuthorizationGrantType("sms_code");


    protected SmsCodeAuthenticationToken(
            Authentication clientPrincipal,
            Set<String> scopes,
            @Nullable Map<String, Object> additionalParameters
    ) {
        super(SmsCodeAuthenticationToken.SMS_CODE, clientPrincipal, additionalParameters);
        this.scopes = Collections.unmodifiableSet(scopes != null ? new HashSet<>(scopes) : Collections.emptySet());
    }


    /**
     * 用户凭证(微信小程序 Code)
     */
    @Override
    public Object getCredentials() {
        return this.getAdditionalParameters().get(OAuth2ParameterNames.CODE);
    }

    public Set<String> getScopes() {
        return scopes;
    }

}

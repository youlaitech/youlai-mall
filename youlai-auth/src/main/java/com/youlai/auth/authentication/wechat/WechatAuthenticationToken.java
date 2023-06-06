package com.youlai.auth.authentication.wechat;

import jakarta.annotation.Nullable;
import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationGrantAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.util.SpringAuthorizationServerVersion;
import org.springframework.util.Assert;

import java.util.*;

/**
 * 微信授权登录
 *
 * @author haoxr
 * @since 3.0.0
 */
public class WechatAuthenticationToken extends OAuth2AuthorizationGrantAuthenticationToken {

    private final Set<String> scopes;

    /**
     * {@link  org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientCredentialsAuthenticationToken}
     *
     * @param clientPrincipal
     * @param additionalParameters
     */
    public WechatAuthenticationToken(
            Authentication clientPrincipal,
            @Nullable Set<String> scopes,
            Map<String, Object> additionalParameters
    ) {
        super(AuthorizationGrantType.PASSWORD, clientPrincipal, additionalParameters);
        this.scopes = Collections.unmodifiableSet(scopes != null ? new HashSet<>(scopes) : Collections.emptySet());

    }

    public Set<String> getScopes() {
        return this.scopes;
    }

    @Override
    public Object getCredentials() {
        return this.getAdditionalParameters().get(OAuth2ParameterNames.PASSWORD);
    }

}

package com.youlai.auth.authentication.wechat;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.hutool.core.lang.Assert;
import com.youlai.auth.userdetails.member.OpenidUserDetailsService;
import com.youlai.auth.util.OAuth2AuthenticationProviderUtils;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.context.AuthorizationServerContextHolder;
import org.springframework.security.oauth2.server.authorization.token.DefaultOAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;

import java.security.Principal;
import java.util.Map;

/**
 * 微信授权认证 Provider
 *
 * @author haoxr
 * @since 3.0.0
 */
@Slf4j
public class WechatMiniAppAuthenticationProvider implements AuthenticationProvider {

    private static final String ERROR_URI = "https://datatracker.ietf.org/doc/html/rfc6749#section-5.2";

    private final OAuth2AuthorizationService authorizationService;
    private final OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator;

    private final OpenidUserDetailsService openidUserDetailsService;

    private final WxMaService wxMaService;


    /**
     * Constructs an {@code OAuth2ResourceOwnerPasswordAuthenticationProviderNew} using the provided parameters.
     *
     * @param authorizationService the authorization service
     * @param tokenGenerator       the token generator
     * @since 0.2.3
     */
    public WechatMiniAppAuthenticationProvider(
            OAuth2AuthorizationService authorizationService,
            OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator,
            OpenidUserDetailsService openidUserDetailsService,
            WxMaService wxMaService

    ) {
        Assert.notNull(authorizationService, "authorizationService cannot be null");
        Assert.notNull(tokenGenerator, "tokenGenerator cannot be null");
        Assert.notNull(openidUserDetailsService, "userDetailsService cannot be null");
        Assert.notNull(wxMaService, "wxMaService cannot be null");
        this.authorizationService = authorizationService;
        this.tokenGenerator = tokenGenerator;
        this.openidUserDetailsService = openidUserDetailsService;
        this.wxMaService = wxMaService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        WechatMiniAppAuthenticationToken wechatMiniAppAuthenticationToken = (WechatMiniAppAuthenticationToken) authentication;

        OAuth2ClientAuthenticationToken clientPrincipal = OAuth2AuthenticationProviderUtils
                .getAuthenticatedClientElseThrowInvalidClient(wechatMiniAppAuthenticationToken);
        RegisteredClient registeredClient = clientPrincipal.getRegisteredClient();

        // 验证客户端是否支持授权类型(grant_type=wechat_mini_app)
        if (!registeredClient.getAuthorizationGrantTypes().contains(WechatMiniAppAuthenticationToken.WECHAT_MINI_APP)) {
            throw new OAuth2AuthenticationException(OAuth2ErrorCodes.UNAUTHORIZED_CLIENT);
        }

        // 微信 code 获取 openid
        Map<String, Object> additionalParameters = wechatMiniAppAuthenticationToken.getAdditionalParameters();
        String code = (String) additionalParameters.get(OAuth2ParameterNames.CODE);
        WxMaJscode2SessionResult sessionInfo;
        try {
            sessionInfo = wxMaService.getUserService().getSessionInfo(code);
        } catch (WxErrorException e) {
            e.printStackTrace();
            throw new OAuth2AuthenticationException(e.getMessage());
        }
        String openid = sessionInfo.getOpenid();
        // 根据 openid 获取会员信息
        UserDetails userDetails = openidUserDetailsService.loadUserByUsername(openid);

        Authentication usernamePasswordAuthentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword());

        // 访问令牌(Access Token) 构造器
        DefaultOAuth2TokenContext.Builder tokenContextBuilder = DefaultOAuth2TokenContext.builder()
                .registeredClient(registeredClient)
                .principal(usernamePasswordAuthentication)
                .authorizationServerContext(AuthorizationServerContextHolder.getContext())
                .authorizationGrantType(WechatMiniAppAuthenticationToken.WECHAT_MINI_APP)
                .authorizationGrant(wechatMiniAppAuthenticationToken);

        // 生成访问令牌(Access Token)
        OAuth2TokenContext tokenContext = tokenContextBuilder.tokenType(OAuth2TokenType.ACCESS_TOKEN).build();
        OAuth2Token generatedAccessToken = this.tokenGenerator.generate(tokenContext);
        if (generatedAccessToken == null) {
            OAuth2Error error = new OAuth2Error(OAuth2ErrorCodes.SERVER_ERROR,
                    "The token generator failed to generate the access token.", ERROR_URI);
            throw new OAuth2AuthenticationException(error);
        }

        OAuth2AccessToken accessToken = new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER,
                generatedAccessToken.getTokenValue(), generatedAccessToken.getIssuedAt(),
                generatedAccessToken.getExpiresAt(), tokenContext.getAuthorizedScopes());
        OAuth2Authorization.Builder authorizationBuilder = OAuth2Authorization.withRegisteredClient(registeredClient)
                .principalName(userDetails.getUsername())
                .authorizationGrantType(WechatMiniAppAuthenticationToken.WECHAT_MINI_APP)
                .attribute(Principal.class.getName(), usernamePasswordAuthentication);
        if (generatedAccessToken instanceof ClaimAccessor) {
            authorizationBuilder.token(accessToken, (metadata) ->
                    metadata.put(OAuth2Authorization.Token.CLAIMS_METADATA_NAME, ((ClaimAccessor) generatedAccessToken).getClaims()));
        } else {
            authorizationBuilder.accessToken(accessToken);
        }

        // 生成刷新令牌(Refresh token)
        OAuth2RefreshToken refreshToken = null;
        if (registeredClient.getAuthorizationGrantTypes().contains(AuthorizationGrantType.REFRESH_TOKEN) &&
                // Do not issue refresh token to public client
                !clientPrincipal.getClientAuthenticationMethod().equals(ClientAuthenticationMethod.NONE)) {

            tokenContext = tokenContextBuilder.tokenType(OAuth2TokenType.REFRESH_TOKEN).build();
            OAuth2Token generatedRefreshToken = this.tokenGenerator.generate(tokenContext);
            if (!(generatedRefreshToken instanceof OAuth2RefreshToken)) {
                OAuth2Error error = new OAuth2Error(OAuth2ErrorCodes.SERVER_ERROR,
                        "The token generator failed to generate the refresh token.", ERROR_URI);
                throw new OAuth2AuthenticationException(error);
            }


            refreshToken = (OAuth2RefreshToken) generatedRefreshToken;
            authorizationBuilder.refreshToken(refreshToken);
        }

        OAuth2Authorization authorization = authorizationBuilder.build();
        this.authorizationService.save(authorization);
        return new OAuth2AccessTokenAuthenticationToken(registeredClient, clientPrincipal, accessToken, refreshToken, additionalParameters);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return WechatMiniAppAuthenticationToken.class.isAssignableFrom(authentication);
    }

}

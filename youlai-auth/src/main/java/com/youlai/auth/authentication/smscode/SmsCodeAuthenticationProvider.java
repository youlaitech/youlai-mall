package com.youlai.auth.authentication.smscode;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.youlai.auth.userdetails.member.MobileUserDetailsService;
import com.youlai.auth.util.OAuth2AuthenticationProviderUtils;
import com.youlai.common.constant.SecurityConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.*;
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
 * 短信验证码认证授权提供者
 *
 * @author haoxr
 * @since 3.0.0
 */
@Slf4j
public class SmsCodeAuthenticationProvider implements AuthenticationProvider {

    private static final String ERROR_URI = "https://datatracker.ietf.org/doc/html/rfc6749#section-5.2";

    private final OAuth2AuthorizationService authorizationService;
    private final OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator;

    private final MobileUserDetailsService mobileUserDetailsService;

    private final RedisTemplate redisTemplate;

    /**
     * Constructs an {@code OAuth2ResourceOwnerPasswordAuthenticationProviderNew} using the provided parameters.
     *
     * @param authorizationService the authorization service
     * @param tokenGenerator       the token generator
     * @since 0.2.3
     */
    public SmsCodeAuthenticationProvider(
            OAuth2AuthorizationService authorizationService,
            OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator,
            MobileUserDetailsService mobileUserDetailsService,
            RedisTemplate redisTemplate

    ) {
        Assert.notNull(authorizationService, "authorizationService cannot be null");
        Assert.notNull(tokenGenerator, "tokenGenerator cannot be null");
        Assert.notNull(mobileUserDetailsService, "userDetailsService cannot be null");
        Assert.notNull(redisTemplate, "redisTemplate cannot be null");
        this.authorizationService = authorizationService;
        this.tokenGenerator = tokenGenerator;
        this.mobileUserDetailsService = mobileUserDetailsService;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        SmsCodeAuthenticationToken smsCodeAuthenticationToken = (SmsCodeAuthenticationToken) authentication;

        OAuth2ClientAuthenticationToken clientPrincipal = OAuth2AuthenticationProviderUtils
                .getAuthenticatedClientElseThrowInvalidClient(smsCodeAuthenticationToken);
        RegisteredClient registeredClient = clientPrincipal.getRegisteredClient();

        // 验证客户端是否支持授权类型(grant_type=wechat_mini_app)
        if (!registeredClient.getAuthorizationGrantTypes().contains(SmsCodeAuthenticationToken.SMS_CODE)) {
            throw new OAuth2AuthenticationException(OAuth2ErrorCodes.UNAUTHORIZED_CLIENT);
        }

        // 微信 code 获取 openid
        Map<String, Object> additionalParameters = smsCodeAuthenticationToken.getAdditionalParameters();
        String mobile = (String) additionalParameters.get("mobile");
        String verifyCode = (String) additionalParameters.get("verifyCode");

        if (!verifyCode.equals("666666")) { // 666666 是后门，因为短信收费，正式环境删除这个if
            String codeKey = SecurityConstants.SMS_CODE_PREFIX + mobile;
            String cacheCode = (String) redisTemplate.opsForValue().get(codeKey);

            if (!StrUtil.equals(verifyCode, cacheCode)) {
                throw new OAuth2AuthenticationException("验证码错误");
            }
        }

        // 根据手机号获取会员信息
        UserDetails userDetails = mobileUserDetailsService.loadUserByUsername(mobile);

        Authentication usernamePasswordAuthentication = new UsernamePasswordAuthenticationToken(userDetails, null);

        // 访问令牌(Access Token) 构造器
        DefaultOAuth2TokenContext.Builder tokenContextBuilder = DefaultOAuth2TokenContext.builder()
                .registeredClient(registeredClient)
                .principal(usernamePasswordAuthentication)
                .authorizationServerContext(AuthorizationServerContextHolder.getContext())
                .authorizationGrantType(SmsCodeAuthenticationToken.SMS_CODE)
                .authorizationGrant(smsCodeAuthenticationToken);

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
                .authorizationGrantType(SmsCodeAuthenticationToken.SMS_CODE)
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
        return SmsCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }

}

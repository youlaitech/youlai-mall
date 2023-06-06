package com.youlai.auth.authentication.wechat;

import cn.hutool.core.lang.Assert;
import com.youlai.auth.authentication.password.ResourceOwnerPasswordAuthenticationToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames;
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
import org.springframework.util.CollectionUtils;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 微信认证提供者
 *
 * @author haoxr
 * @since 3.0.0
 */
@Slf4j
public class WechatAuthenticationProvider implements AuthenticationProvider {

        private static final String ERROR_URI = "https://datatracker.ietf.org/doc/html/rfc6749#section-5.2";

        private final AuthenticationManager authenticationManager;
        private final OAuth2AuthorizationService authorizationService;
        private final OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator;

        /**
         * Constructs an {@code OAuth2ResourceOwnerPasswordAuthenticationProviderNew} using the provided parameters.
         *
         * @param authenticationManager the authentication manager
         * @param authorizationService  the authorization service
         * @param tokenGenerator        the token generator
         * @since 0.2.3
         */
        public WechatAuthenticationProvider(AuthenticationManager authenticationManager,
                                                           OAuth2AuthorizationService authorizationService,
                                                           OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator
        ) {
            Assert.notNull(authorizationService, "authorizationService cannot be null");
            Assert.notNull(tokenGenerator, "tokenGenerator cannot be null");
            this.authenticationManager = authenticationManager;
            this.authorizationService = authorizationService;
            this.tokenGenerator = tokenGenerator;
        }

        @Override
        public Authentication authenticate(Authentication authentication) throws AuthenticationException {

            ResourceOwnerPasswordAuthenticationToken authenticationToken = (ResourceOwnerPasswordAuthenticationToken) authentication;

            // 验证客户端是否已认证
            OAuth2ClientAuthenticationToken clientPrincipal = getAuthenticatedClientElseThrowInvalidClient(authenticationToken);
            RegisteredClient registeredClient = clientPrincipal.getRegisteredClient();

            // 验证客户端是否支持(grant_type=password)授权模式
            if (!registeredClient.getAuthorizationGrantTypes().contains(AuthorizationGrantType.PASSWORD)) {
                throw new OAuth2AuthenticationException(OAuth2ErrorCodes.UNAUTHORIZED_CLIENT);
            }

            // 密码验证
            Map<String, Object> additionalParameters = authenticationToken.getAdditionalParameters();
            String username = (String) additionalParameters.get("code");
            String encryptedData = (String) additionalParameters.get("encryptedData");
            String iv = (String) additionalParameters.get("iv");
            UsernamePasswordAuthenticationToken passwordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password);
            log.debug("got usernamePasswordAuthenticationToken=" + passwordAuthenticationToken);
            Authentication usernamePasswordAuthentication = authenticationManager.authenticate(passwordAuthenticationToken);

            usernamePasswordAuthentication.setAuthenticated(true);



            // 生成 access_token
            // @formatter:off
            DefaultOAuth2TokenContext.Builder tokenContextBuilder = DefaultOAuth2TokenContext.builder()
                    .registeredClient(registeredClient)
                    .principal(usernamePasswordAuthentication)
                    .authorizationServerContext(AuthorizationServerContextHolder.getContext())
                    .authorizationGrantType(AuthorizationGrantType.PASSWORD)
                    .authorizationGrant(passwordAuthenticationToken);
            // @formatter:on

            // ----- Access token -----
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

            // @formatter:off
            OAuth2Authorization.Builder authorizationBuilder = OAuth2Authorization.withRegisteredClient(registeredClient)
                    .principalName(usernamePasswordAuthentication.getName())
                    .authorizationGrantType(AuthorizationGrantType.PASSWORD)
                    .attribute(Principal.class.getName(), usernamePasswordAuthentication);
            // @formatter:on
            if (generatedAccessToken instanceof ClaimAccessor) {
                authorizationBuilder.token(accessToken, (metadata) ->
                        metadata.put(OAuth2Authorization.Token.CLAIMS_METADATA_NAME, ((ClaimAccessor) generatedAccessToken).getClaims()));
            } else {
                authorizationBuilder.accessToken(accessToken);
            }

            // ----- Refresh token -----
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
            return ResourceOwnerPasswordAuthenticationToken.class.isAssignableFrom(authentication);
        }

        private static OAuth2ClientAuthenticationToken getAuthenticatedClientElseThrowInvalidClient(Authentication authentication) {
            OAuth2ClientAuthenticationToken clientPrincipal = null;
            if (OAuth2ClientAuthenticationToken.class.isAssignableFrom(authentication.getPrincipal().getClass())) {
                clientPrincipal = (OAuth2ClientAuthenticationToken) authentication.getPrincipal();
            }
            if (clientPrincipal != null && clientPrincipal.isAuthenticated()) {
                return clientPrincipal;
            }
            throw new OAuth2AuthenticationException(OAuth2ErrorCodes.INVALID_CLIENT);
        }

    }

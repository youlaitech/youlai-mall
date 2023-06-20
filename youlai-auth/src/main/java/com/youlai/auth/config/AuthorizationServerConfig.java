
package com.youlai.auth.config;

import cn.binarywang.wx.miniapp.api.WxMaService;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.youlai.auth.authentication.password.ResourceOwnerPasswordAuthenticationConverter;
import com.youlai.auth.authentication.password.ResourceOwnerPasswordAuthenticationProvider;
import com.youlai.auth.authentication.smscode.SmsCodeAuthenticationConverter;
import com.youlai.auth.authentication.smscode.SmsCodeAuthenticationProvider;
import com.youlai.auth.authentication.wechat.WechatMiniAppAuthenticationConverter;
import com.youlai.auth.authentication.wechat.WechatMiniAppAuthenticationProvider;
import com.youlai.auth.userdetails.member.MemberUserDetails;
import com.youlai.auth.userdetails.member.MobileUserDetailsService;
import com.youlai.auth.userdetails.member.OpenidUserDetailsService;
import com.youlai.auth.userdetails.user.SysUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.authorization.*;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.token.*;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Configuration
@RequiredArgsConstructor
public class AuthorizationServerConfig {

    private final WxMaService wxMaService;
    private final MobileUserDetailsService mobileUserDetailsService;
    private final OpenidUserDetailsService openidUserDetailsService;
    private final RedisTemplate redisTemplate;


    /**
     * 授权配置
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authorizationServerSecurityFilterChain(
            HttpSecurity http,
            AuthenticationManager authenticationManager,
            OAuth2AuthorizationService authorizationService,
            OAuth2TokenGenerator<?> tokenGenerator

    ) throws Exception {

        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer =
                new OAuth2AuthorizationServerConfigurer();
        authorizationServerConfigurer
                .tokenEndpoint(tokenEndpoint ->
                        tokenEndpoint
                                .accessTokenRequestConverters( // <1>
                                        authenticationConverters ->
                                                authenticationConverters.addAll(
                                                        List.of(
                                                                new ResourceOwnerPasswordAuthenticationConverter(),
                                                                new WechatMiniAppAuthenticationConverter(),
                                                                new SmsCodeAuthenticationConverter()
                                                        )
                                                )
                                )
                                .authenticationProviders( // <2>
                                    authenticationProviders ->
                                            authenticationProviders.addAll(
                                                List.of(
                                                    new ResourceOwnerPasswordAuthenticationProvider(authenticationManager, authorizationService, tokenGenerator),
                                                    new WechatMiniAppAuthenticationProvider(authorizationService, tokenGenerator, openidUserDetailsService,wxMaService),
                                                    new SmsCodeAuthenticationProvider(authorizationService, tokenGenerator, mobileUserDetailsService,redisTemplate)
                                                )
                                            )
                                )
                );

        RequestMatcher endpointsMatcher = authorizationServerConfigurer.getEndpointsMatcher();

        http
                .securityMatcher(endpointsMatcher)
                .authorizeHttpRequests(authorize ->
                        authorize
                                .anyRequest().authenticated()
                )
                .csrf(csrf -> csrf.ignoringRequestMatchers(endpointsMatcher))
                .apply(authorizationServerConfigurer);

        return http.build();
    }


    @Bean // <5>
    public JWKSource<SecurityContext> jwkSource() {
        KeyPair keyPair = generateRsaKey();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        // @formatter:off
        RSAKey rsaKey = new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString())
                .build();
        // @formatter:on
        JWKSet jwkSet = new JWKSet(rsaKey);
        return new ImmutableJWKSet<>(jwkSet);
    }

    private static KeyPair generateRsaKey() { // <6>
        KeyPair keyPair;
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            keyPair = keyPairGenerator.generateKeyPair();
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
        return keyPair;
    }

    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }

    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder().build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return  PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public RegisteredClientRepository registeredClientRepository(JdbcTemplate jdbcTemplate) {
        return new JdbcRegisteredClientRepository(jdbcTemplate);
    }

    @Bean
    public OAuth2AuthorizationService authorizationService(JdbcTemplate jdbcTemplate,
                                                           RegisteredClientRepository registeredClientRepository) {
        return new JdbcOAuth2AuthorizationService(jdbcTemplate, registeredClientRepository);
    }

    @Bean
    public OAuth2AuthorizationConsentService authorizationConsentService(JdbcTemplate jdbcTemplate,
                                                                         RegisteredClientRepository registeredClientRepository) {
        // Will be used by the ConsentController
        return new JdbcOAuth2AuthorizationConsentService(jdbcTemplate, registeredClientRepository);
    }



    @Bean
    OAuth2TokenGenerator<?> tokenGenerator(JWKSource<SecurityContext> jwkSource) {
        JwtGenerator jwtGenerator = new JwtGenerator(new NimbusJwtEncoder(jwkSource));
        jwtGenerator.setJwtCustomizer(jwtCustomizer());

        OAuth2AccessTokenGenerator accessTokenGenerator = new OAuth2AccessTokenGenerator();
        OAuth2RefreshTokenGenerator refreshTokenGenerator = new OAuth2RefreshTokenGenerator();
        return new DelegatingOAuth2TokenGenerator(
                jwtGenerator, accessTokenGenerator, refreshTokenGenerator);
    }


    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> jwtCustomizer() {
        return context -> {
            if (OAuth2TokenType.ACCESS_TOKEN .equals(context.getTokenType()) && context.getPrincipal() instanceof UsernamePasswordAuthenticationToken) {
                // Customize headers/claims for access_token
                Optional.ofNullable(context.getPrincipal().getPrincipal()).ifPresent(principal -> {
                    JwtClaimsSet.Builder claims = context.getClaims();
                    if (principal instanceof SysUserDetails userDetails) {
                        claims.claim("user_id", String.valueOf(userDetails.getUserId()));
                    } else if (principal instanceof MemberUserDetails userDetails) {
                        claims.claim("member_id", String.valueOf(userDetails.getId()));
                    }
                });
            } else if (context.getTokenType().getValue().equals(OidcParameterNames.ID_TOKEN)) {
                // Customize headers/claims for id_token

            }
        };
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}

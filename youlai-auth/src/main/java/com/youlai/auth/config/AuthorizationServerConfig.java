
package com.youlai.auth.config;

import cn.binarywang.wx.miniapp.api.WxMaService;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.youlai.auth.authentication.captcha.CaptchaAuthenticationConverter;
import com.youlai.auth.authentication.captcha.CaptchaAuthenticationProvider;
import com.youlai.auth.authentication.captcha.CaptchaAuthenticationToken;
import com.youlai.auth.authentication.miniapp.WxMiniAppAuthenticationConverter;
import com.youlai.auth.authentication.miniapp.WxMiniAppAuthenticationProvider;
import com.youlai.auth.authentication.miniapp.WxMiniAppAuthenticationToken;
import com.youlai.auth.authentication.password.PasswordAuthenticationConverter;
import com.youlai.auth.authentication.password.PasswordAuthenticationProvider;
import com.youlai.auth.authentication.smscode.SmsCodeAuthenticationConverter;
import com.youlai.auth.authentication.smscode.SmsCodeAuthenticationProvider;
import com.youlai.auth.authentication.smscode.SmsCodeAuthenticationToken;
import com.youlai.auth.handler.MyAuthenticationFailureHandler;
import com.youlai.auth.handler.MyAuthenticationSuccessHandler;
import com.youlai.auth.userdetails.member.MemberDetailsService;
import com.youlai.auth.userdetails.user.SysUserDetails;
import com.youlai.auth.userdetails.user.jackson.SysUserMixin;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.jackson2.OAuth2AuthorizationServerJackson2Module;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.oauth2.server.authorization.token.*;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;
import java.util.List;
import java.util.UUID;

/**
 * 授权服务器配置
 *
 * @author haoxr
 * @since 3.0.0
 */
@Configuration
@RequiredArgsConstructor
@Slf4j
public class AuthorizationServerConfig {

    private final WxMaService wxMaService;
    private final StringRedisTemplate redisTemplate;
    private final MemberDetailsService memberDetailsService;
    private final OAuth2TokenCustomizer<JwtEncodingContext> jwtCustomizer;

    /**
     * 授权服务器端点配置
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authorizationServerSecurityFilterChain(
            HttpSecurity http,
            AuthenticationManager authenticationManager,
            OAuth2AuthorizationService authorizationService,
            OAuth2TokenGenerator<?> tokenGenerator

    ) throws Exception {

        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer = new OAuth2AuthorizationServerConfigurer();

        authorizationServerConfigurer
                .tokenEndpoint(tokenEndpoint ->
                        tokenEndpoint
                                .accessTokenRequestConverters(
                                        authenticationConverters ->// <1>
                                                // 自定义授权模式转换器(Converter)
                                                authenticationConverters.addAll(
                                                        List.of(
                                                                new PasswordAuthenticationConverter(),
                                                                new CaptchaAuthenticationConverter(),
                                                                new WxMiniAppAuthenticationConverter(),
                                                                new SmsCodeAuthenticationConverter()
                                                        )
                                                )
                                )
                                .authenticationProviders(authenticationProviders ->// <2>
                                        // 自定义授权模式提供者(Provider)
                                        authenticationProviders.addAll(
                                                List.of(
                                                        new PasswordAuthenticationProvider(authenticationManager, authorizationService, tokenGenerator),
                                                        new CaptchaAuthenticationProvider(authenticationManager, authorizationService, tokenGenerator, redisTemplate),
                                                        new WxMiniAppAuthenticationProvider(authorizationService, tokenGenerator, memberDetailsService, wxMaService),
                                                        new SmsCodeAuthenticationProvider(authorizationService, tokenGenerator, memberDetailsService, redisTemplate)
                                                )
                                        )
                                )
                                .accessTokenResponseHandler(new MyAuthenticationSuccessHandler()) // 自定义成功响应
                                .errorResponseHandler(new MyAuthenticationFailureHandler()) // 自定义失败响应
                );


        RequestMatcher endpointsMatcher = authorizationServerConfigurer.getEndpointsMatcher();
        http.securityMatcher(endpointsMatcher)
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests.anyRequest().authenticated())
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
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public RegisteredClientRepository registeredClientRepository(JdbcTemplate jdbcTemplate) {
        JdbcRegisteredClientRepository registeredClientRepository = new JdbcRegisteredClientRepository(jdbcTemplate);

        // 初始化 OAuth2 客户端
        initMallAppClient(registeredClientRepository);
        initMallAdminClient(registeredClientRepository);

        return registeredClientRepository;
    }


    @Bean
    public OAuth2AuthorizationService authorizationService(JdbcTemplate jdbcTemplate,
                                                           RegisteredClientRepository registeredClientRepository) {

        JdbcOAuth2AuthorizationService service = new JdbcOAuth2AuthorizationService(jdbcTemplate, registeredClientRepository);
        JdbcOAuth2AuthorizationService.OAuth2AuthorizationRowMapper rowMapper = new JdbcOAuth2AuthorizationService.OAuth2AuthorizationRowMapper(registeredClientRepository);
        rowMapper.setLobHandler(new DefaultLobHandler());
        ObjectMapper objectMapper = new ObjectMapper();
        ClassLoader classLoader = JdbcOAuth2AuthorizationService.class.getClassLoader();
        List<Module> securityModules = SecurityJackson2Modules.getModules(classLoader);
        objectMapper.registerModules(securityModules);
        objectMapper.registerModule(new OAuth2AuthorizationServerJackson2Module());
        // You will need to write the Mixin for your class so Jackson can marshall it.
        objectMapper.addMixIn(SysUserDetails.class, SysUserMixin.class);
        objectMapper.addMixIn(Long.class, Object.class);
        rowMapper.setObjectMapper(objectMapper);
        service.setAuthorizationRowMapper(rowMapper);

        return service;
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
        jwtGenerator.setJwtCustomizer(jwtCustomizer);

        OAuth2AccessTokenGenerator accessTokenGenerator = new OAuth2AccessTokenGenerator();
        OAuth2RefreshTokenGenerator refreshTokenGenerator = new OAuth2RefreshTokenGenerator();
        return new DelegatingOAuth2TokenGenerator(
                jwtGenerator, accessTokenGenerator, refreshTokenGenerator);
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {

        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * 初始化创建商城管理客户端
     *
     * @param registeredClientRepository
     */
    private void initMallAdminClient(JdbcRegisteredClientRepository registeredClientRepository) {

        String clientId = "mall-admin";
        String clientSecret = "123456";
        String clientName = "商城管理客户端";


        /*
          如果使用明文，客户端认证时会自动升级加密方式，换句话说直接修改客户端密码，所以直接使用 bcrypt 加密避免不必要的麻烦
          官方ISSUE： https://github.com/spring-projects/spring-authorization-server/issues/1099
         */
        String encodeSecret = passwordEncoder().encode(clientSecret);

        RegisteredClient registeredMallAdminClient = registeredClientRepository.findByClientId(clientId);
        String id = registeredMallAdminClient != null ? registeredMallAdminClient.getId() : UUID.randomUUID().toString();

        RegisteredClient mallAppClient = RegisteredClient.withId(id)
                .clientId(clientId)
                .clientSecret(encodeSecret)
                .clientName(clientName)
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .authorizationGrantType(AuthorizationGrantType.PASSWORD) // 密码模式
                .authorizationGrantType(CaptchaAuthenticationToken.CAPTCHA) // 验证码模式
                .redirectUri("http://127.0.0.1:8080/authorized")
                .postLogoutRedirectUri("http://127.0.0.1:8080/logged-out")
                .scope(OidcScopes.OPENID)
                .scope(OidcScopes.PROFILE)
                .tokenSettings(TokenSettings.builder().accessTokenTimeToLive(Duration.ofDays(1)).build())
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
                .build();
        registeredClientRepository.save(mallAppClient);
    }

    /**
     * 初始化创建商城APP客户端
     *
     * @param registeredClientRepository
     */
    private void initMallAppClient(JdbcRegisteredClientRepository registeredClientRepository) {

        String clientId = "mall-app";
        String clientSecret = "123456";
        String clientName = "商城APP客户端";

        // 如果使用明文，在客户端认证的时候会自动升级加密方式，直接使用 bcrypt 加密避免不必要的麻烦
        String encodeSecret = passwordEncoder().encode(clientSecret);

        RegisteredClient registeredMallAppClient = registeredClientRepository.findByClientId(clientId);
        String id = registeredMallAppClient != null ? registeredMallAppClient.getId() : UUID.randomUUID().toString();

        RegisteredClient mallAppClient = RegisteredClient.withId(id)
                .clientId(clientId)
                .clientSecret(encodeSecret)
                .clientName(clientName)
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .authorizationGrantType(WxMiniAppAuthenticationToken.WECHAT_MINI_APP) // 微信小程序模式
                .authorizationGrantType(SmsCodeAuthenticationToken.SMS_CODE) // 短信验证码模式
                .redirectUri("http://127.0.0.1:8080/authorized")
                .postLogoutRedirectUri("http://127.0.0.1:8080/logged-out")
                .scope(OidcScopes.OPENID)
                .scope(OidcScopes.PROFILE)
                .tokenSettings(TokenSettings.builder().accessTokenTimeToLive(Duration.ofDays(1)).build())
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
                .build();
        registeredClientRepository.save(mallAppClient);
    }

}

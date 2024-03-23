
package com.youlai.auth.config;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.hutool.captcha.generator.CodeGenerator;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.youlai.auth.model.SysUserDetails;
import com.youlai.auth.oauth2.extension.captcha.CaptchaAuthenticationConverter;
import com.youlai.auth.oauth2.extension.captcha.CaptchaAuthenticationProvider;
import com.youlai.auth.oauth2.extension.wechat.WechatAuthenticationConverter;
import com.youlai.auth.oauth2.extension.wechat.WechatAuthenticationProvider;
import com.youlai.auth.oauth2.extension.wechat.WechatAuthenticationToken;
import com.youlai.auth.oauth2.extension.password.PasswordAuthenticationConverter;
import com.youlai.auth.oauth2.extension.password.PasswordAuthenticationProvider;
import com.youlai.auth.oauth2.extension.smscode.SmsCodeAuthenticationConverter;
import com.youlai.auth.oauth2.extension.smscode.SmsCodeAuthenticationProvider;
import com.youlai.auth.oauth2.extension.smscode.SmsCodeAuthenticationToken;
import com.youlai.auth.oauth2.handler.MyAuthenticationFailureHandler;
import com.youlai.auth.oauth2.handler.MyAuthenticationSuccessHandler;
import com.youlai.auth.oauth2.jackson.SysUserMixin;
import com.youlai.auth.oauth2.oidc.CustomOidcAuthenticationConverter;
import com.youlai.auth.oauth2.oidc.CustomOidcAuthenticationProvider;
import com.youlai.auth.oauth2.oidc.CustomOidcUserInfoService;
import com.youlai.auth.service.MemberDetailsService;
import com.youlai.common.constant.RedisConstants;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
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
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;

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
 * @author Ray Hao
 * @see <a href="https://github.com/spring-projects/spring-authorization-server/blob/49b199c5b41b5f9279d9758fc2f5d24ed1fe4afa/samples/demo-authorizationserver/src/main/java/sample/config/AuthorizationServerConfig.java#L112">AuthorizationServerConfig</a>
 * @since 3.0.0
 */
@Configuration
@RequiredArgsConstructor
@Slf4j
public class AuthorizationServerConfig {

    private final WxMaService wxMaService;
    private final MemberDetailsService memberDetailsService;
    private final CustomOidcUserInfoService customOidcUserInfoService;

    private final OAuth2TokenCustomizer<JwtEncodingContext> jwtCustomizer;

    private final StringRedisTemplate redisTemplate;

    private final CodeGenerator codeGenerator;


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
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);

        http.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
                // 自定义授权模式转换器(Converter)
                .tokenEndpoint(tokenEndpoint -> tokenEndpoint
                        .accessTokenRequestConverters(
                                authenticationConverters -> // <1>
                                        // 自定义授权模式转换器(Converter)
                                        authenticationConverters.addAll(
                                                List.of(
                                                        new PasswordAuthenticationConverter(),
                                                        new CaptchaAuthenticationConverter(),
                                                        new WechatAuthenticationConverter(),
                                                        new SmsCodeAuthenticationConverter()
                                                )
                                        )
                        )
                        .authenticationProviders(
                                authenticationProviders -> // <2>
                                        // 自定义授权模式提供者(Provider)
                                        authenticationProviders.addAll(
                                                List.of(
                                                        new PasswordAuthenticationProvider(authenticationManager, authorizationService, tokenGenerator),
                                                        new CaptchaAuthenticationProvider(authenticationManager, authorizationService, tokenGenerator, redisTemplate, codeGenerator),
                                                        new WechatAuthenticationProvider(authorizationService, tokenGenerator, memberDetailsService, wxMaService),
                                                        new SmsCodeAuthenticationProvider(authorizationService, tokenGenerator, memberDetailsService, redisTemplate)
                                                )
                                        )
                        )
                        .accessTokenResponseHandler(new MyAuthenticationSuccessHandler()) // 自定义成功响应
                        .errorResponseHandler(new MyAuthenticationFailureHandler()) // 自定义失败响应
                )
                // Enable OpenID Connect 1.0 自定义
                .oidc(oidcCustomizer ->
                        oidcCustomizer.userInfoEndpoint(userInfoEndpointCustomizer ->
                                {
                                    userInfoEndpointCustomizer.userInfoRequestConverter(new CustomOidcAuthenticationConverter(customOidcUserInfoService));
                                    userInfoEndpointCustomizer.authenticationProvider(new CustomOidcAuthenticationProvider(authorizationService));
                                }
                        )
                );

        http
                .exceptionHandling((exceptions) -> exceptions
                        .defaultAuthenticationEntryPointFor(
                                new LoginUrlAuthenticationEntryPoint("/login"),
                                new MediaTypeRequestMatcher(MediaType.TEXT_HTML)
                        )
                )
                .oauth2ResourceServer(oauth2ResourceServer ->
                        oauth2ResourceServer.jwt(Customizer.withDefaults()));
        return http.build();
    }


    /**
     * JWK（JWT密钥对）源
     */
    @Bean // <5>
    @SneakyThrows
    public JWKSource<SecurityContext> jwkSource() {

        // 尝试从Redis中获取JWKSet(JWT密钥对，包含非对称加密的公钥和私钥)
        String jwkSetStr = redisTemplate.opsForValue().get(RedisConstants.JWK_SET_KEY);
        if (StrUtil.isNotBlank(jwkSetStr)) {
            // 如果存在，解析JWKSet并返回
            JWKSet jwkSet = JWKSet.parse(jwkSetStr);
            return new ImmutableJWKSet<>(jwkSet);
        } else {
            // 如果Redis中不存在JWKSet，生成新的JWKSet
            KeyPair keyPair = generateRsaKey();
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

            // 构建RSAKey
            RSAKey rsaKey = new RSAKey.Builder(publicKey)
                    .privateKey(privateKey)
                    .keyID(UUID.randomUUID().toString())
                    .build();

            // 构建JWKSet
            JWKSet jwkSet = new JWKSet(rsaKey);

            // 将JWKSet存储在Redis中
            redisTemplate.opsForValue().set(RedisConstants.JWK_SET_KEY, jwkSet.toString(Boolean.FALSE));
            return new ImmutableJWKSet<>(jwkSet);
        }

    }

    /**
     * 生成RSA密钥对
     */
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

    /**
     * 授权服务器配置(令牌签发者、获取令牌等端点)
     */
    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder().build();
    }

    /**
     * 密码加密器
     */
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
        // 创建基于JDBC的OAuth2授权服务。这个服务使用JdbcTemplate和客户端仓库来存储和检索OAuth2授权数据。
        JdbcOAuth2AuthorizationService service = new JdbcOAuth2AuthorizationService(jdbcTemplate, registeredClientRepository);

        // 创建并配置用于处理数据库中OAuth2授权数据的行映射器。
        JdbcOAuth2AuthorizationService.OAuth2AuthorizationRowMapper rowMapper = new JdbcOAuth2AuthorizationService.OAuth2AuthorizationRowMapper(registeredClientRepository);
        rowMapper.setLobHandler(new DefaultLobHandler());
        ObjectMapper objectMapper = new ObjectMapper();
        ClassLoader classLoader = JdbcOAuth2AuthorizationService.class.getClassLoader();
        List<Module> securityModules = SecurityJackson2Modules.getModules(classLoader);
        objectMapper.registerModules(securityModules);
        objectMapper.registerModule(new OAuth2AuthorizationServerJackson2Module());
        // You will need to write the Mixin for your class so Jackson can marshall it.

        // 添加自定义Mixin，用于序列化/反序列化特定的类。
        // Mixin类需要自行实现，以便Jackson可以处理这些类的序列化。
        objectMapper.addMixIn(SysUserDetails.class, SysUserMixin.class);
        objectMapper.addMixIn(Long.class, Object.class);

        // 将配置好的ObjectMapper设置到行映射器中。
        rowMapper.setObjectMapper(objectMapper);

        // 将自定义的行映射器设置到授权服务中。
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
                .authorizationGrantType(WechatAuthenticationToken.WECHAT_MINI_APP) // 微信小程序模式
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

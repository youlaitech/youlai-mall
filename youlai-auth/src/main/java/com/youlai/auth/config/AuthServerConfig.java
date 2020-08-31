package com.youlai.auth.config;

import com.youlai.auth.component.JwtTokenEnhancer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.security.KeyPair;
import java.util.ArrayList;
import java.util.List;

/**
 * 认证服务器
 */
@Configuration
@EnableAuthorizationServer
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenEnhancer jwtTokenEnhancer;

    /**
     * 配置客户端详情（可以把客户端信息写死在此或者通过数据库来存储调取详情信息）
     *
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("client")
                .secret(passwordEncoder.encode("123456"))
                .scopes("all")
                .authorizedGrantTypes("password", "refresh_token")
                .accessTokenValiditySeconds(3600)
                .refreshTokenValiditySeconds(86400);
    }

    /**
     * 配置令牌端点的安全约束
     *
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        // 配置JWT的内容增强器
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        List<TokenEnhancer> tokenEnhancers = new ArrayList<>();
        tokenEnhancers.add(jwtTokenEnhancer);
        tokenEnhancers.add(jwtAccessTokenConverter());
        tokenEnhancerChain.setTokenEnhancers(tokenEnhancers);

        endpoints.authenticationManager(authenticationManager)
                .accessTokenConverter(jwtAccessTokenConverter())
                .tokenEnhancer(tokenEnhancerChain);
    }

    /**
     * 用来配置授权一级令牌访问端点和令牌服务
     *
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.allowFormAuthenticationForClients();
    }


    /**
     * 使用非对称加密算法对token签名
     *
     * @return
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setKeyPair(keyPair());
        return jwtAccessTokenConverter;
    }

    /**
     * 从classpath下的证书中获取秘钥对
     *
     * @return
     */
    @Bean
    public KeyPair keyPair() {
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("jwt.jks"), "123456".toCharArray());
        KeyPair keyPair = keyStoreKeyFactory.getKeyPair("jwt", "123456".toCharArray());
        return keyPair;
    }
}

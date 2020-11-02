package com.youlai.gateway.config;


import cn.hutool.core.util.ArrayUtil;
import com.youlai.common.core.constant.AuthConstants;
import com.youlai.gateway.component.AuthorizationManager;
import com.youlai.gateway.component.CustomServerAccessDeniedHandler;
import com.youlai.gateway.component.CustomServerAuthenticationEntryPoint;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

/**
 * 资源服务器配置
 */
@AllArgsConstructor
@Configuration
@EnableWebFluxSecurity
public class ResourceServerConfig {

    private AuthorizationManager authorizationManager;
    private CustomServerAccessDeniedHandler customServerAccessDeniedHandler;
    private CustomServerAuthenticationEntryPoint customServerAuthenticationEntryPoint;
    private WhiteListConfig whiteListConfig;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http.oauth2ResourceServer().jwt()
                .jwtAuthenticationConverter(jwtAuthenticationConverter());
        // 自定义处理JWT请求头过期或签名错误的结果
        http.oauth2ResourceServer().authenticationEntryPoint(customServerAuthenticationEntryPoint);
        http.authorizeExchange()
                .pathMatchers(ArrayUtil.toArray(whiteListConfig.getUrls(),String.class)).permitAll()
                .anyExchange().access(authorizationManager)
                .and()
                .exceptionHandling()
                .accessDeniedHandler(customServerAccessDeniedHandler) // 处理未授权
                .authenticationEntryPoint(customServerAuthenticationEntryPoint) //处理未认证
                .and().csrf().disable();

        return http.build();
    }

    /**
     * @link https://blog.csdn.net/qq_24230139/article/details/105091273
     * ServerHttpSecurity没有将jwt中authorities的负载部分当做Authentication
     * 需要把jwt的Claim中的authorities加入
     * 方案：重新定义ReactiveAuthenticationManager权限管理器，默认转换器JwtGrantedAuthoritiesConverter
     * @return
     */
    @Bean
    public Converter<Jwt, ? extends Mono<? extends AbstractAuthenticationToken>> jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix(AuthConstants.AUTHORITY_PREFIX);
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName(AuthConstants.AUTHORITY_CLAIM_NAME);

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
    }

}

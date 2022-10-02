package com.youlai.gateway.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;


/**
 * 认证管理器(token验证)
 *
 * @author haoxr
 * @date 2022/10/1
 */
@Component
@Slf4j
public class ResourceServerAuthenticationManager implements ReactiveAuthenticationManager {

    @Resource
    private TokenStore redisTokenStore;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        return Mono.justOrEmpty(authentication)
                .filter(a -> a instanceof BearerTokenAuthenticationToken)
                .cast(BearerTokenAuthenticationToken.class)
                .map(BearerTokenAuthenticationToken::getToken)
                .flatMap((accessToken -> {
                    OAuth2AccessToken oAuth2AccessToken = this.redisTokenStore.readAccessToken(accessToken);

                    if (oAuth2AccessToken == null) {
                        return Mono.error(new InvalidTokenException("invalid access token,please check"));
                    } else if (oAuth2AccessToken.isExpired()) {
                        return Mono.error(new InvalidTokenException("access token has expired,please reacquire token"));
                    }

                    OAuth2Authentication oAuth2Authentication = this.redisTokenStore.readAuthentication(accessToken);
                    if (oAuth2Authentication == null) {
                        return Mono.error(new InvalidTokenException("access_token invalid!"));
                    } else {
                        return Mono.just(oAuth2Authentication);
                    }
                })).cast(Authentication.class);
    }
}

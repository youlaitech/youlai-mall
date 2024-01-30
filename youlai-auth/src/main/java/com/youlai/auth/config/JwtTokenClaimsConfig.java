package com.youlai.auth.config;

import com.youlai.auth.model.MemberDetails;
import com.youlai.auth.model.SysUserDetails;
import com.youlai.common.constant.JwtClaimConstants;
import com.youlai.common.constant.SecurityConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * JWT 自定义字段配置
 *
 * @author haoxr
 * @see <a href="https://github.com/spring-projects/spring-authorization-server/pull/1264">How-to: Authorize an access token containing custom authorities</a>
 * @since 3.0.0
 */
@Configuration
public class JwtTokenClaimsConfig {

    /**
     * JWT 自定义字段
     */
    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> jwtTokenCustomizer() {
        return context -> {
            if (OAuth2TokenType.ACCESS_TOKEN.equals(context.getTokenType()) && context.getPrincipal() instanceof UsernamePasswordAuthenticationToken) {
                // Customize headers/claims for access_token
                Optional.ofNullable(context.getPrincipal().getPrincipal()).ifPresent(principal -> {
                    JwtClaimsSet.Builder claims = context.getClaims();
                    if (principal instanceof SysUserDetails userDetails) { // 系统用户添加自定义字段

                        claims.claim(JwtClaimConstants.USER_ID, userDetails.getUserId());
                        claims.claim(JwtClaimConstants.USERNAME, userDetails.getUsername());
                        claims.claim(JwtClaimConstants.DEPT_ID, userDetails.getDeptId());
                        claims.claim(JwtClaimConstants.DATA_SCOPE, userDetails.getDataScope());

                        // 这里存入角色至JWT，解析JWT的角色用于鉴权的位置: ResourceServerConfig#jwtAuthenticationConverter
                        var authorities = AuthorityUtils.authorityListToSet(context.getPrincipal().getAuthorities())
                                .stream()
                                .collect(Collectors.collectingAndThen(Collectors.toSet(), Collections::unmodifiableSet));
                        claims.claim(JwtClaimConstants.AUTHORITIES, authorities);

                    } else if (principal instanceof MemberDetails userDetails) { // 商城会员添加自定义字段
                        claims.claim(JwtClaimConstants.MEMBER_ID, String.valueOf(userDetails.getId()));
                    }
                });
            }
        };
    }

}

package com.youlai.gateway.security.jwt;

import com.youlai.common.constant.SecurityConstants;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;

/**
 * @author yeziz2Z
 */
public class JwtValidator implements OAuth2TokenValidator<Jwt> {

    private RedisTemplate redisTemplate;

    public JwtValidator(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 因为校验不多  并没有将该校验 写在两个类中
     * 参考 系统默认的两个 validator：JwtTimestampValidator，JwtIssuerValidator
     * @param token
     * @return
     */
    @Override
    public OAuth2TokenValidatorResult validate(Jwt token) {
        // 包含 ATI 说明此 token是 refresh token, 该类型token应仅作为获取刷新token时使用
        if (token.getClaims().containsKey(SecurityConstants.JWT_ATI)) {
            OAuth2Error oAuth2Error = createOAuth2Error("Encoded token is not a access token");
            return OAuth2TokenValidatorResult.failure(oAuth2Error);
        }
        // 调用过注销接口  黑名单校验
        if (redisTemplate.hasKey(SecurityConstants.TOKEN_BLACKLIST_PREFIX + token.getId())) {
            OAuth2Error oAuth2Error = createOAuth2Error("token已被禁止访问");
            return OAuth2TokenValidatorResult.failure(oAuth2Error);
        }
        return OAuth2TokenValidatorResult.success();
    }

    private OAuth2Error createOAuth2Error(String reason) {
        return new OAuth2Error(OAuth2ErrorCodes.INVALID_TOKEN, reason,
                null);
    }
}

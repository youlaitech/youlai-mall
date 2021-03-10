package com.youlai.admin.service.impl;


import com.youlai.admin.common.util.JWTUtils;
import com.youlai.admin.service.ITokenService;
import com.youlai.common.constant.AuthConstants;
import com.youlai.common.domain.JWTPayload;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author haoxr
 * @date 2021-03-10
 */
@Service
@AllArgsConstructor
public class TokenServiceImpl implements ITokenService {


    RedisTemplate redisTemplate;

    @Override
    @SneakyThrows
    public boolean invalidateToken(String token) {

        JWTPayload payload = JWTUtils.getJWTPayload(token);

        // 计算是否过期
        long currentTimeSeconds = System.currentTimeMillis() / 1000;
        Long exp = payload.getExp();
        if (exp < currentTimeSeconds) { // token已过期，无需加入黑名单
            return true;
        }
        // 添加至黑名单使其失效
        redisTemplate.opsForValue().set(AuthConstants.TOKEN_BLACKLIST_PREFIX + payload.getJti(), null, (exp - currentTimeSeconds), TimeUnit.SECONDS);
        return true;
    }

    @Override
    public int getTokenStatus(String token) {
        JWTPayload payload = JWTUtils.getJWTPayload(token);

        // 计算是否过期
        long currentTimeSeconds = System.currentTimeMillis() / 1000;
        Long exp = payload.getExp();
        if (exp < currentTimeSeconds) { // token已过期  返回失效
            return 0;
        }

        // 判断是否存在黑名单
        String jti = payload.getJti();
        Boolean isExists = redisTemplate.hasKey(AuthConstants.TOKEN_BLACKLIST_PREFIX + jti);
        if (isExists == true) { // 被添加到黑名单  返回失效
            return 0;
        }
        return 1;
    }

}

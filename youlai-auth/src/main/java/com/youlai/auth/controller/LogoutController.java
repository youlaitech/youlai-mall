package com.youlai.auth.controller;

import cn.hutool.json.JSONObject;
import com.youlai.common.constant.AuthConstants;
import com.youlai.common.result.Result;
import com.youlai.common.web.util.JwtUtils;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;


@Api(tags = "注销")
@RestController
@RequestMapping("/oauth")
@AllArgsConstructor
@Slf4j
public class LogoutController {

    private RedisTemplate redisTemplate;

    @DeleteMapping("/logout")
    public Result logout() {
        JSONObject jsonObject = JwtUtils.getJwtPayload();
        String jti = jsonObject.getStr(AuthConstants.JWT_JTI); // JWT唯一标识
        Long exp = jsonObject.getLong(AuthConstants.JWT_EXP); // JWT过期时间戳
        if (exp != null) {
            long currentTimeSeconds = System.currentTimeMillis() / 1000;
            if (exp < currentTimeSeconds) { // token已过期，无需加入黑名单
                return Result.success();
            }
            redisTemplate.opsForValue().set(AuthConstants.TOKEN_BLACKLIST_PREFIX + jti, null, (exp - currentTimeSeconds), TimeUnit.SECONDS);
        } else { // token 永不过期则永久加入黑名单
            redisTemplate.opsForValue().set(AuthConstants.TOKEN_BLACKLIST_PREFIX + jti, null);
        }
        return Result.success();
    }

}

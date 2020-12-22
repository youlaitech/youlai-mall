package com.youlai.auth.controller;

import cn.hutool.json.JSONObject;
import com.youlai.common.core.constant.AuthConstants;
import com.youlai.common.core.result.Result;
import com.youlai.common.web.util.WebUtils;
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
        JSONObject jsonObject = WebUtils.getJwtPayload();
        String jti = jsonObject.getStr("jti"); // JWT唯一标识
        long exp = jsonObject.getLong("exp"); // JWT过期时间戳

        long currentTimeSeconds = System.currentTimeMillis() / 1000;

        if (exp < currentTimeSeconds) { // token已过期，无需加入黑名单
            return Result.success();
        }
        redisTemplate.opsForValue().set(AuthConstants.TOKEN_BLACKLIST_PREFIX + jti, null, (exp - currentTimeSeconds), TimeUnit.SECONDS);
        return Result.success();
    }

}

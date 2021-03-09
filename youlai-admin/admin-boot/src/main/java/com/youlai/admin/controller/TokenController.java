package com.youlai.admin.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.nimbusds.jose.JWSObject;
import com.youlai.common.constant.AuthConstants;
import com.youlai.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

/**
 * @author hxr
 * @date 2021-03-09
 */

@Api(tags = "令牌接口")
@RestController
@RequestMapping("/api.admin/v1/tokens")
@Slf4j
@AllArgsConstructor
public class TokenController {

    RedisTemplate redisTemplate;

    @ApiOperation(value = "强制下线", httpMethod = "POST")
    @ApiImplicitParam(name = "token", value = "访问令牌", required = true, paramType = "query", dataType = "String")
    @PostMapping("/{token}/_invalid")
    @SneakyThrows
    public Result invalidToken(@PathVariable String token) {

        token = token.replace(AuthConstants.AUTHORIZATION_PREFIX, Strings.EMPTY);
        JWSObject jwsObject = JWSObject.parse(token);
        String payload = jwsObject.getPayload().toString();

        JSONObject jsonObject = JSONUtil.parseObj(payload);
        long currentTimeSeconds = System.currentTimeMillis() / 1000;

        String jti = jsonObject.getStr(AuthConstants.JWT_JTI); // JWT唯一标识
        long exp = jsonObject.getLong(AuthConstants.JWT_EXP); // JWT过期时间戳

        if (exp < currentTimeSeconds) { // token已过期，无需加入黑名单
            return Result.success();
        }
        redisTemplate.opsForValue().set(AuthConstants.TOKEN_BLACKLIST_PREFIX + jti, null, (exp - currentTimeSeconds), TimeUnit.SECONDS);
        return Result.success();
    }

}

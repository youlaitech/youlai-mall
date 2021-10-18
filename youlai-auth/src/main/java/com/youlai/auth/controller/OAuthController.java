package com.youlai.auth.controller;

import cn.hutool.json.JSONObject;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.youlai.auth.common.enums.OAuthClientEnum;
import com.youlai.auth.service.impl.WeAppServiceImpl;
import com.youlai.common.constant.AuthConstants;
import com.youlai.common.result.Result;
import com.youlai.common.web.util.JwtUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.security.KeyPair;
import java.security.Principal;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Api(tags = "认证中心")
@RestController
@RequestMapping("/oauth")
@AllArgsConstructor
@Slf4j
public class OAuthController {

    private TokenEndpoint tokenEndpoint;
    private WeAppServiceImpl weAppServiceImpl;
    private RedisTemplate redisTemplate;
    private KeyPair keyPair;

    @ApiOperation(value = "OAuth2认证", notes = "login")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "grant_type", defaultValue = "password", value = "授权模式", required = true),
            @ApiImplicitParam(name = "client_id", defaultValue = "client", value = "Oauth2客户端ID", required = true),
            @ApiImplicitParam(name = "client_secret", defaultValue = "123456", value = "Oauth2客户端秘钥", required = true),
            @ApiImplicitParam(name = "refresh_token", value = "刷新token"),
            @ApiImplicitParam(name = "username", defaultValue = "admin", value = "登录用户名"),
            @ApiImplicitParam(name = "password", defaultValue = "123456", value = "登录密码")
    })
    @PostMapping("/token")
    public Object postAccessToken(
            @ApiIgnore Principal principal,
            @ApiIgnore @RequestParam Map<String, String> parameters
    ) throws HttpRequestMethodNotSupportedException {

        /**
         * 获取登录认证的客户端ID
         *
         * 兼容两种方式获取Oauth2客户端信息（client_id、client_secret）
         * 方式一：client_id、client_secret放在请求路径中
         * 方式二：放在请求头（Request Headers）中的Authorization字段，且经过加密，例如 Basic Y2xpZW50OnNlY3JldA== 明文等于 client:secret
         */
        String clientId = JwtUtils.getAuthClientId();
        OAuthClientEnum client = OAuthClientEnum.getByClientId(clientId);
        switch (client) {
            case WEAPP:  // 微信小程序
                return Result.success(weAppServiceImpl.login(parameters));
            case TEST: // knife4j接口测试文档使用 client_id/client_secret : client/123456
                return tokenEndpoint.postAccessToken(principal, parameters).getBody();
            default:
                return Result.success(tokenEndpoint.postAccessToken(principal, parameters).getBody());
        }
    }

    @ApiOperation(value = "注销", notes = "logout")
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

    @ApiOperation(value = "获取公钥", notes = "login")
    @GetMapping("/public-key")
    public Map<String, Object> getPublicKey() {
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAKey key = new RSAKey.Builder(publicKey).build();
        return new JWKSet(key).toJSONObject();
    }

}

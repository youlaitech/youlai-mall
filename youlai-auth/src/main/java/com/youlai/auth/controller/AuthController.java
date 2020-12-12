package com.youlai.auth.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.youlai.auth.domain.Oauth2Token;
import com.youlai.common.core.constant.AuthConstants;
import com.youlai.common.core.constant.Constants;
import com.youlai.common.core.result.Result;
import com.youlai.common.core.result.ResultCode;
import com.youlai.common.web.exception.BizException;
import com.youlai.common.web.util.WebUtils;
import com.youlai.mall.ums.dto.MemberDTO;
import com.youlai.mall.ums.pojo.UmsMember;
import com.youlai.mall.ums.api.MemberFeignService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
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
public class AuthController {

    private TokenEndpoint tokenEndpoint;
    private RedisTemplate redisTemplate;
    private WxMaService wxService;
    private MemberFeignService memberFeignService;
    private PasswordEncoder passwordEncoder;
    private KeyPair keyPair;


    @ApiOperation("OAuth2认证生成token")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "grant_type", defaultValue = "password", value = "授权模式", required = true),
            @ApiImplicitParam(name = "client_id", defaultValue = "client", value = "Oauth2客户端ID", required = true),
            @ApiImplicitParam(name = "client_secret", defaultValue = "123456", value = "Oauth2客户端秘钥", required = true),
            @ApiImplicitParam(name = "refresh_token", value = "刷新token"),
            @ApiImplicitParam(name = "username", defaultValue = "admin", value = "登录用户名"),
            @ApiImplicitParam(name = "password", defaultValue = "123456", value = "登录密码"),

            @ApiImplicitParam(name = "code", value = "小程序code"),
            @ApiImplicitParam(name = "encryptedData", value = "包括敏感数据在内的完整用户信息的加密数据"),
            @ApiImplicitParam(name = "iv", value = "加密算法的初始向量"),
    })
    @PostMapping("/token")
    public Result postAccessToken(
            @ApiIgnore Principal principal,
            @ApiIgnore @RequestParam Map<String, String> parameters
    ) throws HttpRequestMethodNotSupportedException, WxErrorException {
        String clientId = parameters.get("client_id");

        if (StrUtil.isBlank(clientId)) {
            throw new BizException("客户端ID不能为空");
        }

        // 微信小程序端认证处理
        if (AuthConstants.WEAPP_CLIENT_ID.equals(clientId)) {
            return this.handleForWxAppAuth(principal, parameters);
        }

        OAuth2AccessToken oAuth2AccessToken = tokenEndpoint.postAccessToken(principal, parameters).getBody();
        Oauth2Token oauth2Token = Oauth2Token.builder()
                .token(oAuth2AccessToken.getValue())
                .refreshToken(oAuth2AccessToken.getRefreshToken().getValue())
                .expiresIn(oAuth2AccessToken.getExpiresIn())
                .build();

        return Result.success(oauth2Token);
    }

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

    private Result handleForWxAppAuth(Principal principal, Map<String, String> parameters) throws WxErrorException, HttpRequestMethodNotSupportedException {

        String code = parameters.get("code");
        if (StrUtil.isBlank(code)) {
            throw new BizException("code不能为空");
        }

        WxMaJscode2SessionResult session = wxService.getUserService().getSessionInfo(code);
        String openid = session.getOpenid();
        String sessionKey = session.getSessionKey();

        Result<MemberDTO> result = memberFeignService.loadMemberByOpenid(openid, 1);

        String username;
        if (ResultCode.USER_NOT_EXIST.getCode().equals(result.getCode())) { // 微信授权登录 会员信息不存在时 注册会员
            String encryptedData = parameters.get("encryptedData");
            String iv = parameters.get("iv");

            WxMaUserInfo userInfo = wxService.getUserService().getUserInfo(sessionKey, encryptedData, iv);
            if (userInfo == null) {
                throw new BizException("获取用户信息失败");
            }
            UmsMember member = new UmsMember()
                    .setNickname(userInfo.getNickName())
                    .setAvatar(userInfo.getAvatarUrl())
                    .setGender(Integer.valueOf(userInfo.getGender()))
                    .setOpenid(openid)
                    .setUsername(openid)
                    .setPassword(passwordEncoder.encode(openid).replace(AuthConstants.BCRYPT, Strings.EMPTY)) // 加密密码移除前缀加密方式 {bcrypt}
                    .setStatus(Constants.STATUS_NORMAL_VALUE);

            Result res = memberFeignService.add(member);
            if (!ResultCode.SUCCESS.getCode().equals(res.getCode())) {
                throw new BizException("注册会员失败");
            }
            username = openid;
        } else {
            MemberDTO memberDTO = result.getData();
            username = memberDTO.getUsername();
        }

        // oauth2认证参数对应授权登录时注册会员的username、password信息，模拟通过oauth2的密码模式认证
        parameters.put("username", username);
        parameters.put("password", username);

        OAuth2AccessToken oAuth2AccessToken = tokenEndpoint.postAccessToken(principal, parameters).getBody();
        Oauth2Token oauth2Token = Oauth2Token.builder()
                .token(oAuth2AccessToken.getValue())
                .refreshToken(oAuth2AccessToken.getRefreshToken().getValue())
                .expiresIn(oAuth2AccessToken.getExpiresIn())
                .build();
        return Result.success(oauth2Token);

    }

    @GetMapping("/public_key")
    public Map<String, Object> getKey() {
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAKey key = new RSAKey.Builder(publicKey).build();
        return new JWKSet(key).toJSONObject();
    }

}

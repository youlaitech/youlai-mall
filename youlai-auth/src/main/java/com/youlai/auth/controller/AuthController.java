package com.youlai.auth.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.youlai.auth.domain.Oauth2Token;
import com.youlai.common.core.constant.AuthConstants;
import com.youlai.common.core.result.Result;
import com.youlai.common.core.result.ResultCode;
import com.youlai.common.web.exception.BizException;
import com.youlai.mall.ums.api.dto.MemberDTO;
import com.youlai.mall.ums.api.entity.UmsMember;
import com.youlai.mall.ums.api.feign.RemoteUmsMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
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
import java.security.Principal;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Api(tags = "认证中心")
@RestController
@RequestMapping("/oauth")
@AllArgsConstructor
public class AuthController {

    private TokenEndpoint tokenEndpoint;
    private RedisTemplate redisTemplate;
    private WxMaService wxService;
    private RemoteUmsMemberService remoteUmsMemberService;
    private PasswordEncoder passwordEncoder;


    @ApiOperation("Oauth2获取token")
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
    ) throws HttpRequestMethodNotSupportedException {

        String clientId = parameters.get("client_id");

        if (StrUtil.isBlank(clientId)) {
            throw new BizException("客户端ID不能为空");
        }

        // 微信小程序逻辑处理
        WxMaUserInfo wxMaUserInfo = null;
        if (AuthConstants.WEAPP_CLIENT_ID.equals(clientId)) {
            wxMaUserInfo = this.handleParametersForWeapp(parameters);
        }

        OAuth2AccessToken oAuth2AccessToken = tokenEndpoint.postAccessToken(principal, parameters).getBody();
        Oauth2Token oauth2Token = Oauth2Token.builder()
                .token(oAuth2AccessToken.getValue())
                .refreshToken(oAuth2AccessToken.getRefreshToken().getValue())
                .expiresIn(oAuth2AccessToken.getExpiresIn())
                .userInfo(wxMaUserInfo)
                .build();

        return Result.success(oauth2Token);
    }

    @DeleteMapping("/logout")
    public Result logout(HttpServletRequest request) {
        String payload = request.getHeader(AuthConstants.JWT_PAYLOAD_KEY);
        JSONObject jsonObject = JSONUtil.parseObj(payload);

        String jti = jsonObject.getStr("jti"); // JWT唯一标识
        long exp = jsonObject.getLong("exp"); // JWT过期时间戳

        long currentTimeSeconds = System.currentTimeMillis() / 1000;

        if (exp < currentTimeSeconds) { // token已过期
            return Result.custom(ResultCode.TOKEN_INVALID_OR_EXPIRED);
        }
        redisTemplate.opsForValue().set(AuthConstants.TOKEN_BLACKLIST_PREFIX + jti, null, (exp - currentTimeSeconds), TimeUnit.SECONDS);
        return Result.success();
    }


    private WxMaUserInfo handleParametersForWeapp(Map<String, String> parameters) {

        try {
            String code = parameters.get("code");
            if (StrUtil.isBlank(code)) {
                throw new BizException("code不能为空");
            }
            WxMaJscode2SessionResult session = wxService.getUserService().getSessionInfo(code);
            String openid = session.getOpenid();
            String sessionKey = session.getSessionKey();

            MemberDTO memberDTO = remoteUmsMemberService.loadMemberByOpenid(openid);
            WxMaUserInfo userInfo;
            if (memberDTO == null || memberDTO.getId() == null) {
                // 注册会员
                String encryptedData = parameters.get("encryptedData");
                String iv = parameters.get("iv");

                userInfo = wxService.getUserService().getUserInfo(sessionKey, encryptedData, iv);
                UmsMember member = UmsMember.builder()
                        .nickname(userInfo.getNickName())
                        .avatar(userInfo.getAvatarUrl())
                        .gender(Integer.valueOf(userInfo.getGender()))
                        .openid(openid)
                        .username(openid)
                        .password(passwordEncoder.encode(openid).replace(AuthConstants.BCRYPT, Strings.EMPTY)) // 加密密码移除前缀加密方式 {bcrypt}
                        .build();
                Result result = remoteUmsMemberService.add(member);
                if (!ResultCode.SUCCESS.getCode().equals(result.getCode())) {
                    throw new BizException("注册会员失败");
                }

                // 微信授权登录数据模拟生成token
                parameters.put("username", member.getUsername());
                parameters.put("password", member.getUsername());

            } else {
                userInfo = new WxMaUserInfo();
                userInfo.setAvatarUrl(memberDTO.getAvatar());
                userInfo.setNickName(memberDTO.getNickname());

                parameters.put("username", memberDTO.getUsername());
                parameters.put("password", memberDTO.getUsername());
            }

            return userInfo;
        } catch (WxErrorException e) {
            e.printStackTrace();
            throw new BizException("auth failed");
        }

    }

}

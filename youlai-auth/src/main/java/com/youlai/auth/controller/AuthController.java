package com.youlai.auth.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import cn.hutool.core.util.StrUtil;
import com.youlai.common.constant.AuthConstants;
import com.youlai.common.constant.GlobalConstants;
import com.youlai.common.result.Result;
import com.youlai.common.result.ResultCode;
import com.youlai.common.web.exception.BizException;
import com.youlai.common.web.util.RequestUtils;
import com.youlai.mall.ums.api.MemberFeignClient;
import com.youlai.mall.ums.pojo.domain.UmsMember;
import com.youlai.mall.ums.pojo.dto.AuthMemberDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.security.Principal;
import java.util.Map;

@Api(tags = "认证中心")
@RestController
@RequestMapping("/oauth")
@AllArgsConstructor
@Slf4j
public class AuthController {

    private TokenEndpoint tokenEndpoint;

    @ApiOperation(value = "OAuth2认证", notes = "login")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "grant_type", defaultValue = "password", value = "授权模式", required = true),
            @ApiImplicitParam(name = "client_id", defaultValue = "client", value = "Oauth2客户端ID", required = true),
            @ApiImplicitParam(name = "client_secret", defaultValue = "123456", value = "Oauth2客户端秘钥", required = true),
            @ApiImplicitParam(name = "refresh_token", value = "刷新token"),
            @ApiImplicitParam(name = "username", defaultValue = "admin", value = "登录用户名"),
            @ApiImplicitParam(name = "password", defaultValue = "123456", value = "登录密码"),

            // 微信小程序认证参数（无小程序可忽略）
            @ApiImplicitParam(name = "code", value = "小程序授权code"),
            @ApiImplicitParam(name = "encryptedData", value = "包括敏感数据在内的完整用户信息的加密数据"),
            @ApiImplicitParam(name = "iv", value = "加密算法的初始向量"),
    })
    @PostMapping("/token")
    public OAuth2AccessToken postAccessToken(
            @ApiIgnore Principal principal,
            @ApiIgnore @RequestParam Map<String, String> parameters
    ) throws HttpRequestMethodNotSupportedException {
        OAuth2AccessToken oAuth2AccessToken;

        /**
         * 获取登录认证的客户端ID
         *
         * 兼容两种方式获取Oauth2客户端信息（client_id、client_secret）
         * 方式一：client_id、client_secret放在请求路径中
         * 方式二：放在请求头（Request Headers）中的Authorization字段，且经过加密，例如 Basic Y2xpZW50OnNlY3JldA== 明文等于 client:secret
         */
        String clientId = RequestUtils.getAuthClientId();
        switch (clientId) {
            case AuthConstants.WEAPP_CLIENT_ID:  // 微信认证
                oAuth2AccessToken = this.handleForWxAuth(principal, parameters);
                break;
            default:
                oAuth2AccessToken = tokenEndpoint.postAccessToken(principal, parameters).getBody();
                break;
        }
        return oAuth2AccessToken;
    }


    private WxMaService wxService;
    private MemberFeignClient memberFeignClient;
    private PasswordEncoder passwordEncoder;

    @SneakyThrows
    public OAuth2AccessToken handleForWxAuth(Principal principal, Map<String, String> parameters) throws HttpRequestMethodNotSupportedException {

        String code = parameters.get("code");

        if (StrUtil.isBlank(code)) {
            throw new BizException("code不能为空");
        }

        WxMaJscode2SessionResult session = null;
        // 根据授权code获取微信用户信息
        session = wxService.getUserService().getSessionInfo(code);
        String openid = session.getOpenid();
        String sessionKey = session.getSessionKey();

        Result<AuthMemberDTO> result = memberFeignClient.getUserByOpenid(openid);

        if (ResultCode.USER_NOT_EXIST.getCode().equals(result.getCode())) { // 微信授权登录 会员信息不存在时 注册会员
            String encryptedData = parameters.get("encryptedData");
            String iv = parameters.get("iv");

            WxMaUserInfo userInfo = wxService.getUserService().getUserInfo(sessionKey, encryptedData, iv);
            if (userInfo == null) {
                throw new BizException("获取用户信息失败");
            }
            UmsMember user = new UmsMember()
                    .setNickname(userInfo.getNickName())
                    .setAvatar(userInfo.getAvatarUrl())
                    .setGender(Integer.valueOf(userInfo.getGender()))
                    .setOpenid(openid)
                    .setUsername(openid)
                    .setPassword(passwordEncoder.encode(openid).replace(AuthConstants.BCRYPT, Strings.EMPTY)) // 加密密码移除前缀加密方式 {bcrypt}
                    .setStatus(GlobalConstants.STATUS_NORMAL_VALUE);

            Result res = memberFeignClient.add(user);
            if (!ResultCode.SUCCESS.getCode().equals(res.getCode())) {
                throw new BizException("注册会员失败");
            }
        }

        // oauth2认证参数对应授权登录时注册会员的username、password信息，模拟通过oauth2的密码模式认证生成JWT
        parameters.put("username", openid);
        parameters.put("password", openid);

        OAuth2AccessToken oAuth2AccessToken = tokenEndpoint.postAccessToken(principal, parameters).getBody();
        return oAuth2AccessToken;
    }


}

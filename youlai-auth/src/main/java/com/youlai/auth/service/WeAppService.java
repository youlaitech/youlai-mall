package com.youlai.auth.service;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import cn.hutool.core.util.StrUtil;
import com.youlai.auth.enums.PasswordEncoderTypeEnum;
import com.youlai.common.constant.AuthConstants;
import com.youlai.common.constant.GlobalConstants;
import com.youlai.common.result.Result;
import com.youlai.common.result.ResultCode;
import com.youlai.common.web.exception.BizException;
import com.youlai.mall.ums.api.MemberFeignClient;
import com.youlai.mall.ums.pojo.domain.UmsMember;
import com.youlai.mall.ums.pojo.dto.AuthMemberDTO;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Map;

/**
 * @author haoxr
 * @description 微信小程序认证接口
 * @createTime 2021/5/20 23:37
 */
@Service
@AllArgsConstructor
public class WeAppService {
    private WxMaService wxService;
    private MemberFeignClient memberFeignClient;
    private PasswordEncoder passwordEncoder;
    private TokenEndpoint tokenEndpoint;


    /**
     * @param principal
     * @param parameters code=小程序授权code
     *                   encryptedData=包括敏感数据在内的完整用户信息的加密数据
     *                   iv=加密算法的初始向量
     * @return
     */
    @SneakyThrows
    public OAuth2AccessToken login(Principal principal, Map<String, String> parameters) {

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
                    .setPassword(passwordEncoder.encode(openid).replace(PasswordEncoderTypeEnum.BCRYPT.getPrefix(),
                            Strings.EMPTY)) // 加密密码移除前缀加密方式 {bcrypt}
                    .setStatus(GlobalConstants.STATUS_YES);

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

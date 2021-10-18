package com.youlai.auth.service.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.youlai.auth.common.jwt.JwtGenerator;
import com.youlai.auth.domain.UserInfo;
import com.youlai.auth.service.IAuthService;
import com.youlai.common.result.Result;
import com.youlai.common.result.ResultCode;
import com.youlai.mall.ums.api.MemberFeignClient;
import com.youlai.mall.ums.pojo.domain.UmsMember;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author haoxr
 * @description 微信小程序认证接口
 * @createTime 2021/5/20 23:37
 */
@Service
@AllArgsConstructor
public class WeAppServiceImpl implements IAuthService {

    private MemberFeignClient memberFeignClient;
    private PasswordEncoder passwordEncoder;
    private WxMaService wxMaService;
    private JwtGenerator jwtGenerator;

    /**
     * @param parameters code=小程序授权code
     *                   encryptedData=包括敏感数据在内的完整用户信息的加密数据
     *                   iv=
     * @return
     */
    @SneakyThrows
    @Override
    public Map<String, Object> login(Map<String, String> parameters) {
        String code = parameters.get("code");
        String rawData = parameters.get("rawData");
        String signature = parameters.get("signature");
        WxMaJscode2SessionResult sessionInfo = wxMaService.getUserService().getSessionInfo(code);
        String sessionKey = sessionInfo.getSessionKey();
        boolean checkResult = wxMaService.getUserService().checkUserInfo(sessionKey, rawData, signature);
        if (checkResult) {
            String openid = sessionInfo.getOpenid();
            Result<UmsMember> result = memberFeignClient.getByOpenid(openid);

            UmsMember member = null;
            Result memberResult;
            if (ResultCode.USER_NOT_EXIST.getCode().equals(result.getCode())) {
                // 用户不存在，注册成为新用户
                UserInfo userInfo = JSONUtil.toBean(rawData, UserInfo.class);
                member = new UmsMember();
                BeanUtil.copyProperties(userInfo, member);
                memberResult = memberFeignClient.add(member);
            } else if (ResultCode.SUCCESS.getCode().equals(result.getCode()) && result.getData() != null) {
                member = result.getData();
                UserInfo userInfo = JSONUtil.toBean(rawData, UserInfo.class);
                BeanUtil.copyProperties(userInfo, member);
                memberResult = memberFeignClient.update(member.getId(), member);
            }


        }


        // String userInfo = parameters.get("userInfo");
       /* if (StrUtil.isBlank(code)) {
            throw new BizException("code不能为空");
        }

        WxMaJscode2SessionResult session;
        // 根据授权code获取微信用户信息
        session = wxMaService.getUserService().getSessionInfo(code);
        String openid = session.getOpenid();
        String sessionKey = session.getSessionKey();

        Result<AuthMemberDTO> result = memberFeignClient.getUserByOpenid(openid);
        Long userId = result.getData().getId();

        if (ResultCode.USER_NOT_EXIST.getCode().equals(result.getCode())) { // 微信授权登录 会员信息不存在时 注册会员
            String encryptedData = parameters.get("encryptedData");
            String iv = parameters.get("iv");

            WxMaUserInfo userInfo = wxMaService.getUserService().getUserInfo(sessionKey, encryptedData, iv);
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

        HashSet<String> roles = new HashSet<>();
        HashMap<String, String> additional = new HashMap<>();
        additional.put("userId", String.valueOf(userId));*/
        // jwtGenerator.createAccessToken(openid, roles, additional);
        return null;
    }
}

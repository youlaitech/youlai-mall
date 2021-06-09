package com.youlai.auth.service.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.json.JSONUtil;
import com.youlai.auth.common.jwt.JwtGenerator;
import com.youlai.auth.domain.UserInfo;
import com.youlai.auth.service.IAuthService;
import com.youlai.common.constant.AuthConstants;
import com.youlai.common.result.Result;
import com.youlai.common.result.ResultCode;
import com.youlai.common.web.exception.BizException;
import com.youlai.mall.ums.api.MemberFeignClient;
import com.youlai.mall.ums.pojo.domain.UmsMember;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author haoxr
 * @description 微信小程序认证接口
 * @createTime 2021/5/20 23:37
 */
@Service
@AllArgsConstructor
public class WeAppServiceImpl implements IAuthService {

    private MemberFeignClient memberFeignClient;
    private WxMaService wxMaService;
    private JwtGenerator jwtGenerator;

    /**
     * @param parameters code=小程序授权code
     *                   rawData=不包括敏感信息的原始数据字符串，用于计算签名
     *                   signature=使用 sha1( rawData + sessionkey ) 得到字符串，用于校验用户信息，详见 用户数据的签名验证和加解密
     * @return
     */
    @SneakyThrows
    @Override
    public Map<String, Object> login(Map<String, String> parameters) {
        Map<String, Object> resultMap = new HashMap<>();

        String code = parameters.get("code");
        String rawData = parameters.get("rawData");
        String signature = parameters.get("signature");
        WxMaJscode2SessionResult sessionInfo = wxMaService.getUserService().getSessionInfo(code);
        String sessionKey = sessionInfo.getSessionKey();
        // 校验微信用户信息
        boolean checkResult = wxMaService.getUserService().checkUserInfo(sessionKey, rawData, signature);
        if (checkResult) {
            String openid = sessionInfo.getOpenid();
            Result<UmsMember> result = memberFeignClient.getByOpenid(openid);

            UmsMember member = null;
            Result memberOptResult = null;
            if (ResultCode.USER_NOT_EXIST.getCode().equals(result.getCode())) {
                // 用户不存在，注册成为新用户
                UserInfo userInfo = JSONUtil.toBean(rawData, UserInfo.class);
                member = new UmsMember();
                BeanUtil.copyProperties(userInfo, member);
                member.setOpenid(openid);
                member.setSessionKey(sessionKey);
                memberOptResult = memberFeignClient.add(member);
                if (ResultCode.SUCCESS.getCode().equals(memberOptResult.getCode())) {
                    member = (UmsMember) memberOptResult.getData();
                }
            } else if (ResultCode.SUCCESS.getCode().equals(result.getCode()) && result.getData() != null) {
                member = result.getData();
                UserInfo userInfo = JSONUtil.toBean(rawData, UserInfo.class);
                BeanUtil.copyProperties(userInfo, member);
                member.setSessionKey(sessionKey);
                memberOptResult = memberFeignClient.update(member.getId(), member);
            }
            if (memberOptResult != null && ResultCode.SUCCESS.getCode().equals(memberOptResult.getCode())) {

                // JWT授权，一般存放用户的角色标识，用于资源服务器（网关）鉴权
                Set<String> authorities = new HashSet<>();

                // JWT增强，携带用户ID等信息
                Map<String, String> additional = new HashMap<>();
                additional.put(AuthConstants.USER_ID_KEY, Convert.toStr(member.getId()));

                String accessToken = jwtGenerator.createAccessToken(authorities, additional);
                String tokenType = "bearer";

                resultMap.put("access_token", accessToken);
                resultMap.put("token_type", tokenType);
                return resultMap;
            }
        } else {
            throw new BizException("非法用户");
        }
        throw new BizException("认证失败");
    }
}

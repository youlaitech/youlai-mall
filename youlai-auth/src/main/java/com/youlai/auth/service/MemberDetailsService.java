package com.youlai.auth.service;

import cn.hutool.core.lang.Assert;
import com.youlai.auth.model.MemberDetails;
import com.youlai.common.enums.StatusEnum;
import com.youlai.common.result.Result;
import com.youlai.common.result.ResultCode;
import com.youlai.mall.member.api.MemberFeignClient;
import com.youlai.mall.member.dto.MemberAuthDTO;
import com.youlai.mall.member.dto.MemberRegisterDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 商城会员用户认证服务
 *
 * @author Ray.Hao
 * @since 3.0.0
 */
@Service
@RequiredArgsConstructor
public class MemberDetailsService {

    private final MemberFeignClient memberFeignClient;


    /**
     * 手机号码认证方式
     *
     * @param mobile 手机号
     * @return 用户信息
     */
    public UserDetails getUserByMobile(String mobile) {
        MemberAuthDTO memberAuthInfo = memberFeignClient.getUserByMobile(mobile);

        if (memberAuthInfo == null) {
            throw new UsernameNotFoundException(ResultCode.USER_NOT_EXIST.getMsg());
        }
        MemberDetails userDetails = new MemberDetails(memberAuthInfo);
        if (!userDetails.isEnabled()) {
            throw new DisabledException("该账户已被禁用!");
        } else if (!userDetails.isAccountNonLocked()) {
            throw new LockedException("该账号已被锁定!");
        } else if (!userDetails.isAccountNonExpired()) {
            throw new AccountExpiredException("该账号已过期!");
        }
        return userDetails;
    }

    /**
     * 根据用户名获取用户信息
     *
     * @param openid 微信公众平台唯一身份标识
     * @return {@link MemberDetails}
     */
    public UserDetails getUserByOpenId(String openid) {
        // 根据 openid 获取微信用户认证信息
        MemberAuthDTO memberAuthInfo = memberFeignClient.getUserByOpenId(openid);

        // 会员不存在，注册成为新会员
        if (memberAuthInfo == null) {
            MemberRegisterDTO memberRegisterInfo = new MemberRegisterDTO();
            memberRegisterInfo.setOpenid(openid);
            memberRegisterInfo.setNickName("微信用户");
            // 注册会员
            Long memberId = memberFeignClient.registerMember(memberRegisterInfo);
            // 注册成功将会员信息赋值给会员认证信息

            memberAuthInfo = new MemberAuthDTO(memberId, openid, StatusEnum.ENABLE.getValue());
        }

        UserDetails userDetails = new MemberDetails(memberAuthInfo);
        if (!userDetails.isEnabled()) {
            throw new DisabledException("该账户已被禁用!");
        } else if (!userDetails.isAccountNonLocked()) {
            throw new LockedException("该账号已被锁定!");
        } else if (!userDetails.isAccountNonExpired()) {
            throw new AccountExpiredException("该账号已过期!");
        }
        return userDetails;
    }

}

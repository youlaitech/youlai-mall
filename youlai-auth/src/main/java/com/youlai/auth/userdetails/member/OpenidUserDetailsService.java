package com.youlai.auth.userdetails.member;

import cn.hutool.core.lang.Assert;
import com.youlai.common.enums.StatusEnum;
import com.youlai.common.result.Result;
import com.youlai.common.result.ResultCode;
import com.youlai.mall.ums.api.MemberFeignClient;
import com.youlai.mall.ums.dto.MemberAuthDTO;
import com.youlai.mall.ums.dto.MemberRegisterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 会员信息(openid为主体)加载实现类
 *
 * @author haoxr
 * @since 3.0.0
 */
@Service
@RequiredArgsConstructor
public class OpenidUserDetailsService implements UserDetailsService {

    private final MemberFeignClient memberFeignClient;

    /**
     * 根据用户名获取用户信息
     *
     * @param openid  微信公众平台唯一身份标识
     * @return {@link MemberUserDetails}
     */
    public UserDetails loadUserByUsername(String openid) {
        // 根据 openid 获取微信用户认证信息
        Result<MemberAuthDTO> getMemberAuthInfoResult = memberFeignClient.loadUserByOpenId(openid);

        MemberAuthDTO memberAuthInfo = null;

        // 会员不存在，注册成为新会员
        if (ResultCode.USER_NOT_EXIST.getCode().equals(getMemberAuthInfoResult.getCode())) {
            MemberRegisterDto memberRegisterInfo = new MemberRegisterDto();
            memberRegisterInfo.setOpenid(openid);
            memberRegisterInfo.setNickName("微信用户");
            // 注册会员
            Result<Long> registerMemberResult = memberFeignClient.registerMember(memberRegisterInfo);
            // 注册成功将会员信息赋值给会员认证信息
            Long memberId;
            if (Result.isSuccess(registerMemberResult) && (memberId = registerMemberResult.getData()) != null) {
                memberAuthInfo = new MemberAuthDTO(memberId, openid, StatusEnum.ENABLE.getValue());
            }
        } else {
            Assert.isTrue((memberAuthInfo = getMemberAuthInfoResult.getData()) != null, "会员认证信息失败");
        }

        // 用户不存在
        if (memberAuthInfo == null) {
            throw new UsernameNotFoundException(ResultCode.USER_NOT_EXIST.getMsg());
        }

        UserDetails userDetails = new MemberUserDetails(memberAuthInfo);
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

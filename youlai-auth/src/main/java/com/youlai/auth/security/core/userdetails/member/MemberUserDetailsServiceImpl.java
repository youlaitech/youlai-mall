package com.youlai.auth.security.core.userdetails.member;

import com.youlai.common.enums.AuthenticationIdentityEnum;
import com.youlai.common.result.Result;
import com.youlai.common.result.ResultCode;
import com.youlai.mall.ums.api.MemberFeignClient;
import com.youlai.mall.ums.dto.MemberAuthInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 商城会员用户认证服务
 *
 * @author <a href="mailto:xianrui0365@163.com">haoxr</a>
 */
@Service("memberUserDetailsService")
@RequiredArgsConstructor
public class MemberUserDetailsServiceImpl implements UserDetailsService {

    private final MemberFeignClient memberFeignClient;

    @Override
    public UserDetails loadUserByUsername(String username) {
        return null;
    }


    /**
     * 手机号码认证方式
     *
     * @param mobile
     * @return
     */
    public UserDetails loadUserByMobile(String mobile) {
        MemberUserDetails userDetails = null;
        Result<MemberAuthInfoDTO> result = memberFeignClient.loadUserByMobile(mobile);
        if (Result.isSuccess(result)) {
            MemberAuthInfoDTO member = result.getData();
            if (null != member) {
                userDetails = new MemberUserDetails(member);
                userDetails.setAuthenticationIdentity(AuthenticationIdentityEnum.MOBILE.getValue());   // 认证身份标识:mobile
            }
        }
        if (userDetails == null) {
            throw new UsernameNotFoundException(ResultCode.USER_NOT_EXIST.getMsg());
        } else if (!userDetails.isEnabled()) {
            throw new DisabledException("该账户已被禁用!");
        } else if (!userDetails.isAccountNonLocked()) {
            throw new LockedException("该账号已被锁定!");
        } else if (!userDetails.isAccountNonExpired()) {
            throw new AccountExpiredException("该账号已过期!");
        }
        return userDetails;
    }

    /**
     * openid 认证方式
     *
     * @param openId
     * @return
     */
    public UserDetails loadUserByOpenId(String openId) {
        MemberUserDetails userDetails = null;
        Result<MemberAuthInfoDTO> result = memberFeignClient.loadUserByOpenId(openId);

        if (Result.isSuccess(result)) {
            MemberAuthInfoDTO member = result.getData();
            if (null != member) {
                userDetails = new MemberUserDetails(member);
                userDetails.setAuthenticationIdentity(AuthenticationIdentityEnum.OPENID.getValue());   // 认证身份标识:openId
            }
        }
        if (userDetails == null) {
            throw new UsernameNotFoundException(ResultCode.USER_NOT_EXIST.getMsg());
        } else if (!userDetails.isEnabled()) {
            throw new DisabledException("该账户已被禁用!");
        } else if (!userDetails.isAccountNonLocked()) {
            throw new LockedException("该账号已被锁定!");
        } else if (!userDetails.isAccountNonExpired()) {
            throw new AccountExpiredException("该账号已过期!");
        }
        return userDetails;
    }
}

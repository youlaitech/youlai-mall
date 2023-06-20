package com.youlai.auth.userdetails.member;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.hutool.core.lang.Assert;
import com.youlai.common.enums.StatusEnum;
import com.youlai.common.result.Result;
import com.youlai.common.result.ResultCode;
import com.youlai.mall.ums.api.MemberFeignClient;
import com.youlai.mall.ums.dto.MemberAuthDTO;
import com.youlai.mall.ums.dto.MemberRegisterDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
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
 * @author haoxr
 * @since 3.0.0
 */
@Service
@RequiredArgsConstructor
public class MobileUserDetailsService implements UserDetailsService {

    private final MemberFeignClient memberFeignClient;


    /**
     * 手机号码认证方式
     *
     * @param mobile 手机号
     * @return 用户信息
     */
    @Override
    public UserDetails loadUserByUsername(String mobile) {
        Result<MemberAuthDTO> result = memberFeignClient.loadUserByMobile(mobile);

        MemberAuthDTO memberAuthInfo;
        if (!(Result.isSuccess(result) && (memberAuthInfo = result.getData()) != null)) {
            throw new UsernameNotFoundException(ResultCode.USER_NOT_EXIST.getMsg());
        }
        MemberUserDetails userDetails = new MemberUserDetails(memberAuthInfo);
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

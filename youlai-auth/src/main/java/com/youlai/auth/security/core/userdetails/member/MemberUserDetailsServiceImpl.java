package com.youlai.auth.security.core.userdetails.member;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.youlai.common.result.Result;
import com.youlai.common.result.ResultCode;
import com.youlai.mall.ums.api.MemberFeignClient;
import com.youlai.mall.ums.pojo.dto.MemberAuthDTO;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 系统用户体系业务类
 *
 * @author <a href="mailto:xianrui0365@163.com">xianrui</a>
 */
@Service("memberUserDetailsService")
@Slf4j
@RequiredArgsConstructor
public class MemberUserDetailsServiceImpl implements UserDetailsService {

    private final MemberFeignClient memberFeignClient;

    private final WxMaService wxMaService;

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String code) throws UsernameNotFoundException {
        MemberUserDetails userDetails = null;
        WxMaJscode2SessionResult sessionInfo = wxMaService.getUserService().getSessionInfo(code);
        String openid = sessionInfo.getOpenid();


        Result<MemberAuthDTO> result = memberFeignClient.loadUserByOpenId(openid);
        if (Result.isSuccess(result)) {
            MemberAuthDTO member = result.getData();
            if (null != member) {
                userDetails = new MemberUserDetails(member);
            } else { // 微信用户不存在同步微信用户信息注册为小程序会员

                //wxMaService.getUserService().getUserInfo()


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

package com.youlai.auth.service;

import com.youlai.admin.api.UserFeignClient;
import com.youlai.admin.pojo.dto.UserDTO;
import com.youlai.auth.domain.OAuthUserDetails;
import com.youlai.common.constant.AuthConstants;
import com.youlai.common.result.Result;
import com.youlai.common.result.ResultCode;
import com.youlai.common.web.util.JwtUtils;
import com.youlai.mall.ums.api.MemberFeignClient;
import com.youlai.mall.ums.pojo.dto.AuthMemberDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 自定义用户认证和授权
 */
@Service
@AllArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private UserFeignClient userFeignClient;
    private MemberFeignClient memberFeignClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String clientId = JwtUtils.getAuthClientId();

        OAuthUserDetails OAuthUserDetails = null;
        Result result;
        switch (clientId) {
            case AuthConstants.ADMIN_CLIENT_ID: // 后台用户
                result = userFeignClient.getUserByUsername(username);
                log.info("获取用户信息：{}", result.toString());
                if (ResultCode.SUCCESS.getCode().equals(result.getCode())) {
                    UserDTO userDTO = (UserDTO) result.getData();
                    OAuthUserDetails = new OAuthUserDetails(userDTO);
                }
                break;
            case AuthConstants.WEAPP_CLIENT_ID: // 小程序会员
                result = memberFeignClient.getUserByOpenid(username);
                if (ResultCode.SUCCESS.getCode().equals(result.getCode())) {
                    AuthMemberDTO authMemberDTO = (AuthMemberDTO) result.getData();
                    OAuthUserDetails = new OAuthUserDetails(authMemberDTO);
                }
                break;
        }
        if (OAuthUserDetails == null || OAuthUserDetails.getId() == null) {
            throw new UsernameNotFoundException(ResultCode.USER_NOT_EXIST.getMsg());
        } else if (!OAuthUserDetails.isEnabled()) {
            throw new DisabledException("该账户已被禁用!");
        } else if (!OAuthUserDetails.isAccountNonLocked()) {
            throw new LockedException("该账号已被锁定!");
        } else if (!OAuthUserDetails.isAccountNonExpired()) {
            throw new AccountExpiredException("该账号已过期!");
        }
        return OAuthUserDetails;
    }

}

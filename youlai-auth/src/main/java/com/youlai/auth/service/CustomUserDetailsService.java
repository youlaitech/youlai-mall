package com.youlai.auth.service;

import com.youlai.admin.api.UserFeignClient;
import com.youlai.admin.pojo.dto.UserDTO;
import com.youlai.admin.pojo.entity.SysUser;
import com.youlai.auth.domain.OAuthUserDetails;
import com.youlai.auth.enums.OAuthClientEnum;
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
        OAuthClientEnum client = OAuthClientEnum.getByClientId(clientId);

        Result result;
        OAuthUserDetails oauthUser = null;
        switch (client) {
            case WEAPP: // 小程序会员
                result = memberFeignClient.getUserByOpenid(username);
                if (ResultCode.SUCCESS.getCode().equals(result.getCode())) {
                    AuthMemberDTO authMemberDTO = (AuthMemberDTO) result.getData();
                    oauthUser = new OAuthUserDetails(authMemberDTO);
                }
                break;
            default:
                result = userFeignClient.getUserByUsername(username);
                if (ResultCode.SUCCESS.getCode().equals(result.getCode())) {
                    SysUser sysUser = (SysUser)result.getData();
                    oauthUser = new OAuthUserDetails(sysUser);
                }
                break;
        }
        if (oauthUser == null || oauthUser.getId() == null) {
            throw new UsernameNotFoundException(ResultCode.USER_NOT_EXIST.getMsg());
        } else if (!oauthUser.isEnabled()) {
            throw new DisabledException("该账户已被禁用!");
        } else if (!oauthUser.isAccountNonLocked()) {
            throw new LockedException("该账号已被锁定!");
        } else if (!oauthUser.isAccountNonExpired()) {
            throw new AccountExpiredException("该账号已过期!");
        }
        return oauthUser;
    }

}

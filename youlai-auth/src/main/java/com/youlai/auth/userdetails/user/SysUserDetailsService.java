package com.youlai.auth.userdetails.user;

import cn.hutool.core.lang.Assert;
import com.youlai.common.result.Result;
import com.youlai.system.api.UserFeignClient;
import com.youlai.system.dto.UserAuthInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * 系统用户信息加载实现类
 *
 * @author haoxr
 * @since 3.0.0
 */
@Primary // UserDetailsService 默认的实现，其他需要显式声明
@Service
@RequiredArgsConstructor
public class SysUserDetailsService implements UserDetailsService {

    private final UserFeignClient userFeignClient;

    /**
     * 根据用户名获取用户信息(用户名、密码和角色权限)
     * <p>
     * 用户名、密码用于后续认证，认证成功之后将权限授予用户
     *
     * @param username 前端登录表单的用户名
     * @return {@link  SysUserDetails}
     */
    @Override
    public UserDetails loadUserByUsername(String username) {
        Result<UserAuthInfo> result = userFeignClient.getUserAuthInfo(username);

        UserAuthInfo userAuthInfo = null;
        Assert.isTrue(Result.isSuccess(result) && (userAuthInfo = result.getData()) != null,
                "用户不存在");

        SysUserDetails userDetails = new SysUserDetails(userAuthInfo);
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

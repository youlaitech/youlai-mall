package com.youlai.auth.service;

import cn.hutool.core.lang.Assert;
import com.youlai.auth.model.LoginUserInfo;
import com.youlai.auth.model.SysUserDetails;
import com.youlai.common.enums.StatusEnum;
import com.youlai.system.api.UserFeignClient;
import com.youlai.system.dto.UserAuthInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * 系统用户信息加载实现类
 *
 * @author haoxr
 * @since 3.0.0
 */
@Service
@RequiredArgsConstructor
public class SysUserDetailsService implements UserDetailsService {

    private final UserFeignClient userFeignClient;

    /**
     * 根据用户名获取用户信息(用户名、密码和角色权限)
     * <p>
     * 用户名、密码用于后续认证，认证成功之后将权限授予用户
     *
     * @param username 用户名
     * @return {@link  SysUserDetails}
     */
    @Override
    public UserDetails loadUserByUsername(String username) {
        UserAuthInfo userAuthInfo = userFeignClient.getUserAuthInfo(username);

        Assert.isTrue(userAuthInfo != null, "用户不存在");

        if (!StatusEnum.ENABLE.getValue().equals(userAuthInfo.getStatus())) {
            throw new DisabledException("该账户已被禁用!");
        }

        return new SysUserDetails(userAuthInfo);
    }


    public LoginUserInfo getLoginUserInfo() {
        LoginUserInfo loginUserInfo = new LoginUserInfo();
        loginUserInfo.setId(123L);
        return loginUserInfo;
    }
}

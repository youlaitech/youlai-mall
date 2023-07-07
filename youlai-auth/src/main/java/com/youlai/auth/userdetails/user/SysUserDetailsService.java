package com.youlai.auth.userdetails.user;

import cn.hutool.core.util.StrUtil;
import com.youlai.common.result.Result;
import com.youlai.system.api.UserFeignClient;
import com.youlai.system.dto.UserAuthInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
        Result<UserAuthInfo> result = userFeignClient.getUserAuthInfo(username);
        if (!Result.isSuccess(result) || result.getData() == null) {
            throw new UsernameNotFoundException(StrUtil.format("用户{}不存在",username));
        }
        return new SysUserDetails(result.getData());
    }

}

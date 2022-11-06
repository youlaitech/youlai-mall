package com.youlai.auth.userdetails.user;

import cn.hutool.core.lang.Assert;
import com.youlai.common.enums.StatusEnum;
import com.youlai.common.result.Result;
import com.youlai.system.api.UserFeignClient;
import com.youlai.system.dto.UserAuthInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * 系统用户体系业务类
 *
 * @author <a href="mailto:xianrui0365@163.com">haoxr</a>
 */
@Service("sysUserDetailsService")
@Slf4j
@RequiredArgsConstructor
public class SysUserDetailsServiceImpl implements UserDetailsService {

    private final UserFeignClient userFeignClient;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Result<UserAuthInfo> result = userFeignClient.getUserAuthInfo(username);

        UserAuthInfo userAuthInfo = null;
        Assert.isTrue(Result.isSuccess(result) && (userAuthInfo = result.getData()) != null,
                "用户不存在");

       if (!StatusEnum.ENABLE.getValue().equals(userAuthInfo.getStatus()) ) {
            throw new DisabledException("该账户已被禁用!");
        }

        SysUserDetails userDetails = new SysUserDetails(userAuthInfo);
        return userDetails;
    }

}

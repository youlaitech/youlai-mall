package com.youlai.auth.security.core;

import com.youlai.auth.security.core.userdetails.member.MemberUserDetailsServiceImpl;
import com.youlai.auth.security.core.userdetails.system.SysUserDetailsServiceImpl;
import com.youlai.auth.security.extension.wechat.WechatAuthenticationToken;
import com.youlai.common.web.util.JwtUtils;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author <a href="mailto:xianrui0365@163.com">xianrui</a>
 * @date 2021/10/2
 */
@NoArgsConstructor
public class CustomUserDetailsByNameServiceWrapper<T extends Authentication> implements AuthenticationUserDetailsService<T>, InitializingBean {


    private List<UserDetailsService> userDetailsServiceList;

    public CustomUserDetailsByNameServiceWrapper(List<UserDetailsService> userDetailsServiceList) {
        Assert.notNull(userDetailsServiceList, "userDetailsService cannot be null.");
        this.userDetailsServiceList = userDetailsServiceList;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(this.userDetailsServiceList, "UserDetailsService must be set");
    }

    @Override
    public UserDetails loadUserDetails(T authentication) throws UsernameNotFoundException {
        UserDetailsService userDetailsService = null;


        String clientId = JwtUtils.getOAuth2ClientId();

        for (UserDetailsService detailsService : userDetailsServiceList) {
            if (clientId.equals("youlai-mall-weapp")) {
                if (detailsService instanceof MemberUserDetailsServiceImpl) {
                    userDetailsService = detailsService;
                    continue;
                }
            }else{
                if (detailsService instanceof SysUserDetailsServiceImpl) {
                    userDetailsService = detailsService;
                    continue;
                }
            }
        }
        return userDetailsService.loadUserByUsername(authentication.getName());
    }
}

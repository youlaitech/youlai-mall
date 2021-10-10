package com.youlai.auth.security.extension.refresh;

import com.youlai.auth.common.constant.AuthConstants;
import com.youlai.auth.security.core.userdetails.member.MemberUserDetailsServiceImpl;
import com.youlai.common.enums.AuthenticationMethodEnum;
import com.youlai.common.web.util.RequestUtils;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.Assert;

import java.util.Map;

/**
 * 刷新token再次认证 UserDetailsService
 *
 * @author <a href="mailto:xianrui0365@163.com">xianrui</a>
 * @date 2021/10/2
 */
@NoArgsConstructor
public class PreAuthenticatedUserDetailsService<T extends Authentication> implements AuthenticationUserDetailsService<T>, InitializingBean {

    private Map<String, UserDetailsService> userDetailsServiceMap;

    public PreAuthenticatedUserDetailsService(Map<String, UserDetailsService> userDetailsServiceMap) {
        Assert.notNull(userDetailsServiceMap, "userDetailsService cannot be null.");
        this.userDetailsServiceMap = userDetailsServiceMap;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(this.userDetailsServiceMap, "UserDetailsService must be set");
    }

    @Override
    public UserDetails loadUserDetails(T authentication) throws UsernameNotFoundException {
        String clientId = RequestUtils.getOAuth2ClientId();
        AuthenticationMethodEnum authenticationMethodEnum = AuthenticationMethodEnum.getByValue(RequestUtils.getAuthenticationMethod());
        UserDetailsService userDetailsService = userDetailsServiceMap.get(clientId);
        if (clientId.equals(AuthConstants.APP_CLIENT_ID)) {
            MemberUserDetailsServiceImpl memberUserDetailsService = (MemberUserDetailsServiceImpl) userDetailsService;
            switch (authenticationMethodEnum) {
                case OPENID:
                    return memberUserDetailsService.loadUserByOpenId(authentication.getName());
                default:
                    return memberUserDetailsService.loadUserByUsername(authentication.getName());
            }
        } else if (clientId.equals(AuthConstants.WEAPP_CLIENT_ID)) {
            MemberUserDetailsServiceImpl memberUserDetailsService = (MemberUserDetailsServiceImpl) userDetailsService;
            switch (authenticationMethodEnum) {
                case MOBILE:
                    return memberUserDetailsService.loadUserByMobile(authentication.getName());
                default:
                    return memberUserDetailsService.loadUserByUsername(authentication.getName());
            }
        } else if (clientId.equals(AuthConstants.ADMIN_CLIENT_ID)) {
            switch (authenticationMethodEnum) {
                default:
                    return userDetailsService.loadUserByUsername(authentication.getName());
            }
        } else {
            return userDetailsService.loadUserByUsername(authentication.getName());
        }
    }
}

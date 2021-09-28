package com.youlai.auth.security.extension.wechat;

import com.youlai.auth.security.core.userdetails.member.MemberUserDetailsServiceImpl;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author <a href="mailto:xianrui0365@163.com">xianrui</a>
 * @date 2021/9/25
 */
public class WechatAuthenticationProvider implements AuthenticationProvider {

    private MemberUserDetailsServiceImpl memberUserDetailsService;

    /**
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        WechatAuthenticationToken authenticationToken = (WechatAuthenticationToken) authentication;
        String openId = (String) authenticationToken.getPrincipal();
        UserDetails userDetails = memberUserDetailsService.loadUserByUsername(openId);

        WechatAuthenticationToken result = new WechatAuthenticationToken(openId, userDetails.getAuthorities());
        result.setDetails(authentication.getDetails());
        return result;
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return WechatAuthenticationToken.class.isAssignableFrom(authentication);
    }
}

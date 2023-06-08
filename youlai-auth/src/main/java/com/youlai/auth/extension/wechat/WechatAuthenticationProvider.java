package com.youlai.auth.extension.wechat;

import cn.binarywang.wx.miniapp.api.WxMaService;
import com.youlai.auth.userdetails.member.MemberUserDetailsServiceImpl;
import com.youlai.mall.ums.api.MemberFeignClient;
import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Collections;

/**
 * 微信认证提供者
 *
 * @author haoxr
 * @since 2021/9/25
 */
@Data
public class WechatAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsService userDetailsService;
    private WxMaService wxMaService;
    private MemberFeignClient memberFeignClient;

    /**
     * 微信认证
     *
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @SneakyThrows
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        WechatAuthenticationToken authenticationToken = (WechatAuthenticationToken) authentication;
        String code = (String) authenticationToken.getPrincipal();
        String encryptedData = authenticationToken.getEncryptedData();
        String iv = authenticationToken.getIv();

        UserDetails userDetails = ((MemberUserDetailsServiceImpl) userDetailsService).loadUserByWechatCode(code,encryptedData,iv);
        WechatAuthenticationToken result = new WechatAuthenticationToken(userDetails, Collections.EMPTY_SET);
        result.setDetails(authentication.getDetails());
        return result;
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return WechatAuthenticationToken.class.isAssignableFrom(authentication);
    }
}

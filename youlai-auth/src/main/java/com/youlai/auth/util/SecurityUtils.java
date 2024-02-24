package com.youlai.auth.util;

import cn.hutool.core.convert.Convert;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Map;

/**
 * Spring Security 工具类
 *
 * @since 3.1.0
 * @author Ray Hao
 */
public class SecurityUtils {


    public static Map<String, Object> getTokenAttributes() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
        return jwtAuthenticationToken.getTokenAttributes();
    }


    public static String getJti() {
        return String.valueOf(getTokenAttributes().get("jti"));
    }

    public static Long getExp() {
        return Convert.toLong(getTokenAttributes().get("exp"));
    }



}

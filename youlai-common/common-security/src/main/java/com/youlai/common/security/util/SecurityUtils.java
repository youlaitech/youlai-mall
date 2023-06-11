package com.youlai.common.security.util;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.youlai.common.constant.GlobalConstants;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class SecurityUtils {

    public static Long getUserId() {
        Long userId = Convert.toLong(getTokenAttributes().get("userId"));
        return userId;
    }

    public static String getUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    public static Map<String, Object> getTokenAttributes() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
        return jwtAuthenticationToken.getTokenAttributes();
    }


    public static Set<String> getRoles() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Set roles;
        if (CollectionUtil.isNotEmpty(authentication.getAuthorities())) {
            roles = authentication.getAuthorities()
                    .stream()
                    .map(item -> StrUtil.removePrefix(item.getAuthority(), "ROLE_")).collect(Collectors.toSet());
        } else {
            roles = Collections.EMPTY_SET;
        }
        return roles;
    }

    /**
     * 获取部门ID
     */
    public static Long getDeptId() {
        return Convert.toLong(getTokenAttributes().get("deptId"));
    }

    public static boolean isRoot() {
        return getRoles().contains(GlobalConstants.ROOT_ROLE_CODE);
    }

    public static String getJti() {
        return String.valueOf(getTokenAttributes().get("jti"));
    }

    public static Integer getDataScope() {
        return Convert.toInt(getTokenAttributes().get("dataScope"));
    }

    public static Long getMemberId() {

        return Convert.toLong(getTokenAttributes().get("memberId"));
    }
}

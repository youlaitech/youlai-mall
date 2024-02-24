package com.youlai.common.security.util;

import cn.hutool.core.convert.Convert;
import com.youlai.common.constant.SystemConstants;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Spring Security 工具类
 *
 * @author Ray Hao
 * @since 2.1.0
 */
public class SecurityUtils {

    public static Long getUserId() {
        return Convert.toLong(getTokenAttributes().get("userId"));
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


    /**
     * 获取用户角色集合
     */
    public static Set<String> getRoles() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return AuthorityUtils.authorityListToSet(authentication.getAuthorities())
                .stream()
                .collect(Collectors.collectingAndThen(Collectors.toSet(), Collections::unmodifiableSet));
    }

    /**
     * 获取部门ID
     */
    public static Long getDeptId() {
        return Convert.toLong(getTokenAttributes().get("deptId"));
    }

    public static boolean isRoot() {
        return getRoles().contains(SystemConstants.ROOT_ROLE_CODE);
    }

    public static String getJti() {
        return String.valueOf(getTokenAttributes().get("jti"));
    }


    public static Long getExp() {
        return Convert.toLong(getTokenAttributes().get("exp"));
    }

    /**
     * 获取数据权限范围
     *
     * @return 数据权限范围
     * @see com.youlai.common.mybatis.enums.DataScopeEnum
     */
    public static Integer getDataScope() {
        return Convert.toInt(getTokenAttributes().get("dataScope"));
    }

    /**
     * 获取会员ID
     *
     * @return 会员ID
     */
    public static Long getMemberId() {
        return Convert.toLong(getTokenAttributes().get("memberId"));
    }
}

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

/**
 * Spring Security Context工具类
 * <p>
 * 获取登录用户信息(用户名、角色、部门)
 *
 * @author haoxr
 * @date 2022/10/29
 */
public class SecurityUtils {

    /**
     * 获取用户ID
     *
     * @return
     */
    public static Long getUserId() {
        Long userId = Convert.toLong(getTokenAttributes().get("userId"));
        return userId;
    }

    /**
     * 获取会员ID
     *
     * @return
     */
    public static Long getMemberId() {
        Long userId = Convert.toLong(getTokenAttributes().get("memberId"));
        return userId;
    }

    /**
     * 获取用户登录名
     *
     * @return username
     */
    public static String getUsername() {
        String username = Convert.toStr(getTokenAttributes().get("username"));
        return username;
    }

    /**
     * 获取用户昵称/姓名
     *
     * @return nickname
     */
    public static String getNickname() {
        String nickname = Convert.toStr(getTokenAttributes().get("nickname"));
        return nickname;
    }

    /**
     * 获取用户角色
     *
     * @return 角色Code集合
     */
    public static Set<String> getRoles() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && CollectionUtil.isNotEmpty(authentication.getAuthorities())) {
            Set<String> roles = authentication.getAuthorities()
                    .stream()
                    .map(item -> StrUtil.removePrefix(item.getAuthority(), "ROLE_"))
                    .collect(Collectors.toSet());
            return roles;
        }
        return Collections.EMPTY_SET;
    }

    /**
     * 获取部门ID
     *
     * @return deptId
     */
    public static Long getDeptId() {
        Long deptId = Convert.toLong(getTokenAttributes().get("deptId"));
        return deptId;
    }

    /**
     * 获取数据权限
     *
     * @return DataScope
     */
    public static Integer getDataScope() {
        Integer dataScope = Convert.toInt(getTokenAttributes().get("dataScope"));
        return dataScope;
    }

    /**
     * 判断用户是否为超级管理员
     *
     * @return true/false
     */
    public static boolean isRoot() {
        return getRoles().contains(GlobalConstants.ROOT_ROLE_CODE);
    }

    public static Map<String, Object> getTokenAttributes() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            if (authentication instanceof JwtAuthenticationToken) {
                JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
                Map<String, Object> tokenAttributes = jwtAuthenticationToken.getTokenAttributes();
                return tokenAttributes;
            }
        }
        return Collections.EMPTY_MAP;
    }
}

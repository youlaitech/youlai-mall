package com.youlai.common.security.util;

import cn.hutool.core.collection.CollectionUtil;
import com.youlai.common.security.userdetails.SysUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class SecurityUtils {

    public static SysUserDetails getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal != null && principal instanceof SysUserDetails) {
                return (SysUserDetails) principal;
            }
        }
        return new SysUserDetails();
    }

    public static Long getUserId() {
        Long userId = getCurrentUser().getUserId();
        return userId;
    }

    public static String getUsername() {
        String username = getCurrentUser().getUsername();
        return username;
    }

    public static Long getDeptId() {
        Long storeId = getCurrentUser().getDeptId();
        return storeId;
    }


    public static Set<String> getRoles() {
        Collection<? extends GrantedAuthority> authorities = getCurrentUser().getAuthorities();
        Set<String> roles = CollectionUtil.emptyIfNull((authorities).stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toSet()));
        return roles;
    }

}

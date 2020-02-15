package com.fly.system.utils;

import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

/**
 * @author tycoding
 * @date 2019-05-24
 */
@UtilityClass
public class SecurityUtils {

    /**
     * 获取Authentication
     */
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 获取用户
     */
    public User getUser() {
        Authentication authentication = getAuthentication();
        if (authentication == null) {
            return null;
        }
        return getUser(authentication);
    }

    /**
     * 获取用户名
     *
     * @return
     */
    public String getUsername() {
        Authentication authentication = getAuthentication();
        Object principal = authentication.getPrincipal();
        if (!(principal instanceof User)) {
            return (String) principal;
        }
        return null;
    }

    /**
     * 获取用户
     */
    public User getUser(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        if (principal instanceof User) {
            return (User) principal;
        }
        return null;
    }
}

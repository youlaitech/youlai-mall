package com.youlai.common.web.util;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONObject;
import com.youlai.common.constant.SecurityConstants;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;

/**
 * JWT工具类
 *
 * @author haoxr
 * @date 2022/2/5
 */
@Slf4j
public class UserUtils {

    /**
     * 解析JWT获取用户ID
     *
     * @return
     */
    public static Long getUserId() {
        Long userId = null;
        JSONObject jwtPayload = JwtUtils.getJwtPayload();
        if (jwtPayload != null) {
            userId = jwtPayload.getLong("userId");
        }
        return userId;
    }

    public static Long getMemberId() {
        Long memberId = null;
        JSONObject jwtPayload =  JwtUtils.getJwtPayload();
        if (jwtPayload != null) {
            memberId = jwtPayload.getLong("memberId");
        }
        return memberId;
    }

    /**
     * 解析JWT获取用户ID
     *
     * @return
     */
    public static Long getDeptId() {
        Long id =  JwtUtils.getJwtPayload().getLong("deptId");
        return id;
    }

    /**
     * 解析JWT获取获取用户名
     *
     * @return
     */
    public static String getUsername() {
        String username =  JwtUtils.getJwtPayload().getStr(SecurityConstants.USER_NAME_KEY);
        return username;
    }


    /**
     * JWT获取用户角色列表
     *
     * @return 角色列表
     */
    public static List<String> getRoles() {
        List<String> roles;
        JSONObject payload =  JwtUtils.getJwtPayload();
        if (payload.containsKey(SecurityConstants.JWT_AUTHORITIES_KEY)) {
            roles = payload.getJSONArray(SecurityConstants.JWT_AUTHORITIES_KEY).toList(String.class);
        } else {
            roles = Collections.emptyList();
        }
        return roles;
    }

    /**
     * 是否「超级管理员」
     *
     * @return
     */
    public static boolean isRoot() {
        List<String> roles = getRoles();
        return CollectionUtil.isNotEmpty(roles) && roles.contains("ROOT");
    }
}

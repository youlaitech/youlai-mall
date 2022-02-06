package com.youlai.common.web.util;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.youlai.common.constant.SecurityConstants;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

/**
 * JWT工具类
 *
 * @author haoxr
 * @date 2022/2/5
 */
@Slf4j
public class JwtUtils {

    @SneakyThrows
    public static JSONObject getJwtPayload() {
        JSONObject jsonObject = null;
        String payload = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader(SecurityConstants.JWT_PAYLOAD_KEY);
        if (StrUtil.isNotBlank(payload)) {
            jsonObject = JSONUtil.parseObj(URLDecoder.decode(payload, StandardCharsets.UTF_8.name()));
        }
        return jsonObject;
    }

    /**
     * 解析JWT获取用户ID
     *
     * @return
     */
    public static Long getUserId() {
        Long userId = null;
        JSONObject jwtPayload = getJwtPayload();
        if (jwtPayload != null) {
            userId = jwtPayload.getLong(SecurityConstants.USER_ID_KEY);
        }
        return userId;
    }

    /**
     * 解析JWT获取用户ID
     *
     * @return
     */
    public static Long getDeptId() {
        Long id = getJwtPayload().getLong("deptId");
        return id;
    }

    /**
     * 解析JWT获取获取用户名
     *
     * @return
     */
    public static String getUsername() {
        String username = getJwtPayload().getStr(SecurityConstants.USER_NAME_KEY);
        return username;
    }


    /**
     * JWT获取用户角色列表
     *
     * @return 角色列表
     */
    public static List<String> getRoles() {
        List<String> roles;
        JSONObject payload = getJwtPayload();
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

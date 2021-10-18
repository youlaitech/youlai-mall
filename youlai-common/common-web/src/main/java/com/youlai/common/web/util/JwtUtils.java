package com.youlai.common.web.util;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.youlai.common.constant.AuthConstants;
import com.youlai.common.web.exception.BizException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

/**
 * JWT工具类
 *
 * @author xianrui
 */
@Slf4j
public class JwtUtils {

    @SneakyThrows
    public static JSONObject getJwtPayload() {
        String payload = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader(AuthConstants.JWT_PAYLOAD_KEY);
        if (null == payload) {
            throw new BizException("请传入认证头");
        }
        JSONObject jsonObject = JSONUtil.parseObj(URLDecoder.decode(payload,StandardCharsets.UTF_8.name()));
        return jsonObject;
    }

    /**
     * 解析JWT获取用户ID
     *
     * @return
     */
    public static Long getUserId() {
        Long id = getJwtPayload().getLong(AuthConstants.USER_ID_KEY);
        return id;
    }

    /**
     * 解析JWT获取获取用户名
     *
     * @return
     */
    public static String getUsername() {
        String username = getJwtPayload().getStr(AuthConstants.USER_NAME_KEY);
        return username;
    }



    /**
     * JWT获取用户角色列表
     *
     * @return 角色列表
     */
    public static List<String> getRoles() {
        List<String> roles = null;
        JSONObject payload = getJwtPayload();
        if (payload.containsKey(AuthConstants.JWT_AUTHORITIES_KEY)) {
            roles = payload.getJSONArray(AuthConstants.JWT_AUTHORITIES_KEY).toList(String.class);
        }
        return roles;
    }
}

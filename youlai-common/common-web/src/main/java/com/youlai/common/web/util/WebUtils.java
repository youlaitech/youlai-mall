package com.youlai.common.web.util;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.youlai.common.core.constant.AuthConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Slf4j
public class WebUtils extends org.springframework.web.util.WebUtils {


    public static JSONObject getJwtPayload(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String jwtPayload = request.getHeader(AuthConstants.JWT_PAYLOAD_KEY);
        JSONObject jsonObject = JSONUtil.parseObj(jwtPayload);
        return jsonObject;
    }

    public static Long getUserId() {
        Long id = getJwtPayload().getLong(AuthConstants.JWT_USER_ID_KEY);
        return id;
    }

    public static String getClientId() {
        String clientId = getJwtPayload().getStr(AuthConstants.JWT_CLIENT_ID_KEY);
        return clientId;
    }

}

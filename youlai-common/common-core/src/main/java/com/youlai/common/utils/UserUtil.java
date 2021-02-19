package com.youlai.common.utils;


import cn.hutool.core.util.ReflectUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.nimbusds.jose.JWSObject;
import com.youlai.common.constant.AuthConstants;
import org.apache.catalina.connector.Request;
import org.apache.logging.log4j.util.Strings;
import org.springframework.web.context.request.RequestAttributes;

/**
 * User工具类
 */
public class UserUtil {

	/** 
     * get user
     * @param requestAttributes
     * @return
     */
    public static JSONObject getUser(RequestAttributes requestAttributes){
        Request request = null;
        JSONObject jsonObject = null;
        try {

            Object temp = ReflectUtil.getFieldValue(requestAttributes, "request");
            temp = ReflectUtil.getFieldValue(temp, "request");
            request = (Request) temp;

            String token = request.getHeader(AuthConstants.JWT_TOKEN_HEADER);
            token = token.replace(AuthConstants.JWT_TOKEN_PREFIX, Strings.EMPTY);
            JWSObject jwsObject = JWSObject.parse(token);
            String payload = jwsObject.getPayload().toString();
            jsonObject = JSONUtil.parseObj(payload);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return jsonObject;
    }
}

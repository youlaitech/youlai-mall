package com.youlai.auth.authentication.wechat;

import cn.hutool.core.util.StrUtil;
import com.youlai.auth.util.OAuth2EndpointUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.util.MultiValueMap;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * 参数解析
 *
 * @see org.springframework.security.oauth2.server.authorization.web.authentication.OAuth2AuthorizationCodeAuthenticationConverter
 */
public class WxMiniAppAuthenticationConverter implements AuthenticationConverter {

    public static final String ACCESS_TOKEN_REQUEST_ERROR_URI = "https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/login/auth.code2Session.html";

    @Override
    public Authentication convert(HttpServletRequest request) {
        // grant_type (REQUIRED)
        String grantType = request.getParameter(OAuth2ParameterNames.GRANT_TYPE);
        if (!WxMiniAppAuthenticationToken.WX_MINI_APP.getValue().equals(grantType)) {
            return null;
        }

        MultiValueMap<String, String> parameters = OAuth2EndpointUtils.getParameters(request);

        // code (REQUIRED)
        String code = parameters.getFirst(WxMiniAppParameterNames.CODE);
        if (StrUtil.isBlank(code)) {
            OAuth2EndpointUtils.throwError(
                    OAuth2ErrorCodes.INVALID_REQUEST,
                    WxMiniAppParameterNames.CODE,
                    ACCESS_TOKEN_REQUEST_ERROR_URI);
        }

        // encryptedData (REQUIRED)
        String encryptedData = parameters.getFirst(WxMiniAppParameterNames.ENCRYPTED_DATA);
        if (StrUtil.isBlank(encryptedData)) {
            OAuth2EndpointUtils.throwError(
                    OAuth2ErrorCodes.INVALID_REQUEST,
                    WxMiniAppParameterNames.ENCRYPTED_DATA,
                    ACCESS_TOKEN_REQUEST_ERROR_URI);
        }

        // iv (REQUIRED)
        String iv = parameters.getFirst(WxMiniAppParameterNames.IV);
        if (StrUtil.isBlank(iv)) {
            OAuth2EndpointUtils.throwError(
                    OAuth2ErrorCodes.INVALID_REQUEST,
                    WxMiniAppParameterNames.IV,
                    ACCESS_TOKEN_REQUEST_ERROR_URI);
        }

        Authentication clientPrincipal = SecurityContextHolder.getContext().getAuthentication();
        if (clientPrincipal == null) {
            OAuth2EndpointUtils.throwError(
                    OAuth2ErrorCodes.INVALID_REQUEST,
                    OAuth2ErrorCodes.INVALID_CLIENT,
                    ACCESS_TOKEN_REQUEST_ERROR_URI);
        }

        Map<String, Object> additionalParameters = parameters
                .entrySet()
                .stream()
                .filter(e ->
                        !e.getKey().equals(OAuth2ParameterNames.GRANT_TYPE)
                                && !e.getKey().equals(OAuth2ParameterNames.SCOPE)
                                && !e.getKey().equals(WxMiniAppParameterNames.CODE)
                                && !e.getKey().equals(WxMiniAppParameterNames.ENCRYPTED_DATA)
                                && !e.getKey().equals(WxMiniAppParameterNames.IV)
                )
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().get(0)));

        return new WxMiniAppAuthenticationToken(
                clientPrincipal,
                additionalParameters,
                code,
                encryptedData,
                iv
        );
    }


}

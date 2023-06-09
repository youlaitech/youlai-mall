package com.youlai.auth.authentication.wechat;

import cn.hutool.core.util.StrUtil;
import com.youlai.auth.authentication.password.ResourceOwnerPasswordAuthenticationToken;
import com.youlai.auth.util.OAuth2EndpointUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 参数解析
 *
 * @see org.springframework.security.oauth2.server.authorization.web.authentication.OAuth2AuthorizationCodeAuthenticationConverter
 */
public class WechatMiniProgramAuthenticationConverter implements AuthenticationConverter {

    public static final String ACCESS_TOKEN_REQUEST_ERROR_URI ="https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/login/auth.code2Session.html";

    String CODE = "code";

    String IV = "iv";

    String ENCRYPTED_DATA = "encryptedData";

    @Override
    public Authentication convert(HttpServletRequest request) {
        // grant_type (REQUIRED)
        String grantType = request.getParameter(OAuth2ParameterNames.GRANT_TYPE);
        if (!WeChatMiniProgramAuthenticationToken.WECHAT_MINI_PROGRAM.getValue().equals(grantType)) {
            return null;
        }

        MultiValueMap<String, String> parameters = OAuth2EndpointUtils.getParameters(request);

        // scope (OPTIONAL)
        String scope = parameters.getFirst(OAuth2ParameterNames.SCOPE);
        if (StringUtils.hasText(scope) &&
                parameters.get(OAuth2ParameterNames.SCOPE).size() != 1) {
            OAuth2EndpointUtils.throwError(OAuth2ErrorCodes.INVALID_REQUEST, OAuth2ParameterNames.SCOPE,
                    ACCESS_TOKEN_REQUEST_ERROR_URI);
        }

        Set<String> requestedScopes = null;
        if (StringUtils.hasText(scope)) {
            requestedScopes = new HashSet<>(
                    Arrays.asList(StringUtils.delimitedListToStringArray(scope, " ")));
        }

        // code (REQUIRED)
        String code = parameters.getFirst(WechatMiniProgramParameterNames.CODE);
        if (StrUtil.isBlank(code)) {
            throwError(
                    OAuth2ErrorCodes.INVALID_REQUEST,
                    WechatMiniProgramParameterNames.CODE,
                    ACCESS_TOKEN_REQUEST_ERROR_URI);
        }

        // encryptedData (REQUIRED)
        String encryptedData = parameters.getFirst(WechatMiniProgramParameterNames.ENCRYPTED_DATA);
        if (StrUtil.isBlank(encryptedData)) {
            throwError(
                    OAuth2ErrorCodes.INVALID_REQUEST,
                    WechatMiniProgramParameterNames.ENCRYPTED_DATA,
                    ACCESS_TOKEN_REQUEST_ERROR_URI);
        }

        // iv (REQUIRED)
        String iv = parameters.getFirst(WechatMiniProgramParameterNames.IV);
        if (StrUtil.isBlank(iv)) {
            throwError(
                    OAuth2ErrorCodes.INVALID_REQUEST,
                    WechatMiniProgramParameterNames.IV,
                    ACCESS_TOKEN_REQUEST_ERROR_URI);
        }

        Authentication clientPrincipal = SecurityContextHolder.getContext().getAuthentication();
        if (clientPrincipal == null) {
            throwError(
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
                )
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().get(0)));

        return new ResourceOwnerPasswordAuthenticationToken(
                clientPrincipal,
                requestedScopes,
                additionalParameters
        );
    }


    public static MultiValueMap<String, String> getParameters(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap(parameterMap.size());
        parameterMap.forEach((key, values) -> {
            for (String value : values) {
                parameters.add(key, value);
            }

        });
        return parameters;
    }

    public static void throwError(String errorCode, String parameterName, String errorUri) {
        OAuth2Error error = new OAuth2Error(errorCode, "OAuth 2.0 Parameter: " + parameterName, errorUri);
        throw new OAuth2AuthenticationException(error);
    }

}

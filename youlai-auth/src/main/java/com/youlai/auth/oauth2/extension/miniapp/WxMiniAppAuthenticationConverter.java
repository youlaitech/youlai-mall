package com.youlai.auth.oauth2.extension.miniapp;

import cn.hutool.core.util.StrUtil;
import com.youlai.auth.util.OAuth2EndpointUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 微信小程序认证参数解析器
 * <p>
 * 解析请求参数中的微信小程序 Code，并构建相应的身份验证(Authentication)对象
 *
 * @author haoxr
 * @see org.springframework.security.oauth2.server.authorization.web.authentication.OAuth2AuthorizationCodeAuthenticationConverter
 * @since 3.0.0
 */
public class WxMiniAppAuthenticationConverter implements AuthenticationConverter {

    public static final String ACCESS_TOKEN_REQUEST_ERROR_URI = "https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/login/auth.code2Session.html";

    @Override
    public Authentication convert(HttpServletRequest request) {
        // 授权类型 (必需)
        String grantType = request.getParameter(OAuth2ParameterNames.GRANT_TYPE);
        if (!WxMiniAppAuthenticationToken.WECHAT_MINI_APP.getValue().equals(grantType)) {
            return null;
        }

        // 客户端信息
        Authentication clientPrincipal = SecurityContextHolder.getContext().getAuthentication();

        // 参数提取验证
        MultiValueMap<String, String> parameters = OAuth2EndpointUtils.getParameters(request);

        // 令牌申请访问范围验证 (可选)
        String scope = parameters.getFirst(OAuth2ParameterNames.SCOPE);
        if (StringUtils.hasText(scope) &&
                parameters.get(OAuth2ParameterNames.SCOPE).size() != 1) {
            OAuth2EndpointUtils.throwError(
                    OAuth2ErrorCodes.INVALID_REQUEST,
                    OAuth2ParameterNames.SCOPE,
                    ACCESS_TOKEN_REQUEST_ERROR_URI);
        }
        Set<String> requestedScopes = null;
        if (StringUtils.hasText(scope)) {
            requestedScopes = new HashSet<>(Arrays.asList(StringUtils.delimitedListToStringArray(scope, " ")));
        }

        // 微信小程序 Code (必需)
        String code = parameters.getFirst(OAuth2ParameterNames.CODE);
        if (StrUtil.isBlank(code)) {
            OAuth2EndpointUtils.throwError(
                    OAuth2ErrorCodes.INVALID_REQUEST,
                    OAuth2ParameterNames.CODE,
                    ACCESS_TOKEN_REQUEST_ERROR_URI);
        }
        // 附加参数(微信小程序 Code)
        Map<String, Object> additionalParameters = parameters
                .entrySet()
                .stream()
                .filter(e ->
                        !e.getKey().equals(OAuth2ParameterNames.GRANT_TYPE)
                                && !e.getKey().equals(OAuth2ParameterNames.SCOPE)
                )
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().get(0)));

        return new WxMiniAppAuthenticationToken(
                clientPrincipal,
                requestedScopes,
                additionalParameters
        );
    }


}

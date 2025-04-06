package com.youlai.auth.oauth2.oidc;

import com.youlai.system.api.UserFeignClient;
import com.youlai.system.dto.UserAuthCredentials;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 自定义 OIDC 用户信息服务
 *
 * @author Ray.Hao
 * @since 3.1.0
 */
@Service
@Slf4j
public class CustomOidcUserInfoService {

    private final UserFeignClient userFeignClient;

    public CustomOidcUserInfoService(UserFeignClient userFeignClient) {
        this.userFeignClient = userFeignClient;
    }

    public CustomOidcUserInfo loadUserByUsername(String username) {
        UserAuthCredentials userAuthCredentials = null;
        try {
            userAuthCredentials = userFeignClient.getUserAuthInfo(username);
            if (userAuthCredentials == null) {
                return null;
            }
            return new CustomOidcUserInfo(createUser(userAuthCredentials));
        } catch (Exception e) {
            log.error("获取用户信息失败", e);
            return null;
        }
    }

    private Map<String, Object> createUser(UserAuthCredentials userAuthCredentials) {
        return CustomOidcUserInfo.customBuilder()
                .username(userAuthCredentials.getUsername())
                .nickname(userAuthCredentials.getNickname())
                .status(userAuthCredentials.getStatus())
                .phoneNumber(userAuthCredentials.getMobile())
                .email(userAuthCredentials.getEmail())
                .profile(userAuthCredentials.getAvatar())
                .build()
                .getClaims();
    }

}

package com.youlai.auth.security.authentication.password;


import com.youlai.auth.authentication.smscode.SmsCodeAuthenticationToken;
import com.youlai.auth.authentication.wxminiapp.WxMiniAppAuthenticationToken;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class SmsCodeAuthenticationTests {


   private final String clientId = "mall-app";
    private final String clientSecret = "secret";


    @Autowired
    private RegisteredClientRepository registeredClientRepository;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        // 注册 mall-app 客户端

        String encodeSecret = passwordEncoder.encode(clientSecret);
        RegisteredClient messagingClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId(clientId)
                .clientSecret(encodeSecret)
                .clientName(clientId)
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .authorizationGrantType(AuthorizationGrantType.PASSWORD)
                .authorizationGrantType(WxMiniAppAuthenticationToken.WECHAT_MINI_APP)
                .authorizationGrantType(SmsCodeAuthenticationToken.SMS_CODE)
                .redirectUri("http://127.0.0.1:8080/login/oauth2/code/messaging-client-oidc")
                .redirectUri("http://127.0.0.1:8080/authorized")
                .postLogoutRedirectUri("http://127.0.0.1:8080/logged-out")
                .scope(OidcScopes.OPENID)
                .scope(OidcScopes.PROFILE)
                .scope("message.read")
                .scope("message.write")
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
                .build();

        RegisteredClient registeredMessagingClient = registeredClientRepository.findByClientId(clientId);
        if (registeredMessagingClient == null) {
            registeredClientRepository.save(messagingClient);
        }
    }


    @Test
    void testSmsCodeMode() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("mall-app", "secret");

        // @formatter:off
        this.mvc.perform(post("/oauth2/token")
                        .param(OAuth2ParameterNames.GRANT_TYPE, "sms_code")
                        .param("mobile", "18866668888")
                        .param("verifyCode", "666666")
                        .headers(headers))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.access_token").isNotEmpty());
        // @formatter:on
    }


}
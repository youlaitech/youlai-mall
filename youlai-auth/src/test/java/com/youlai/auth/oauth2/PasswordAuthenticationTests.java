package com.youlai.auth.oauth2;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * OAuth2 密码模式单元测试
 */
@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class PasswordAuthenticationTests {

    @Autowired
    private MockMvc mvc;

    /**
     * 测试密码模式登录
     */
    @Test
    void testPasswordLogin() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        // 客户端ID和密钥
        headers.setBasicAuth("mall-admin", "123456");

        this.mvc.perform(post("/oauth2/token")
                        .param(OAuth2ParameterNames.GRANT_TYPE, "password") // 密码模式
                        .param(OAuth2ParameterNames.USERNAME, "admin") // 用户名
                        .param(OAuth2ParameterNames.PASSWORD, "123456") // 密码
                        .headers(headers))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.access_token").isNotEmpty());
    }


}
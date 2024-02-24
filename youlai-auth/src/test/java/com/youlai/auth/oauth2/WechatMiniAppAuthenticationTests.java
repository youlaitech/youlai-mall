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

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class WechatMiniAppAuthenticationTests {

    @Autowired
    private MockMvc mvc;

    @Test
    void testWechatMiniAppPasswordAuthentication() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("mall-app", "123456");

        // @formatter:off
        this.mvc.perform(post("/oauth2/token")
                        .param(OAuth2ParameterNames.GRANT_TYPE, "wechat_mini_app")
                        .param(OAuth2ParameterNames.CODE, "codeVal")
                        .headers(headers))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.access_token").isNotEmpty());
        // @formatter:on
    }


}
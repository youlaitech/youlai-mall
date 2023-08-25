package com.youlai.mall.oms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
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
 * 订单单元测试
 *
 * @author haoxr
 * @since 2023/8/25
 */
@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class OrderTests {


    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testOrderFlow() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("mall-app", "123456");

        String responseJson = this.mvc.perform(post("/oauth2/token")
                        .param(OAuth2ParameterNames.GRANT_TYPE, "sms_code")
                        .param("mobile", "18866668888")
                        .param("verifyCode", "666666")
                        .headers(headers))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.access_token").isNotEmpty())
                .andReturn().getResponse().getContentAsString();

        JSONObject jsonObject = objectMapper.readValue(responseJson, JSONObject.class);
        String accessToken = jsonObject.getString("access_token");
    }

}

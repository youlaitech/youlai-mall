package com.youlai.mall.oms.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.youlai.common.result.Result;
import com.youlai.mall.oms.enums.OrderSourceTypeEnum;
import com.youlai.mall.oms.model.form.OrderSubmitForm;
import com.youlai.mall.pms.api.SkuFeignClient;
import com.youlai.mall.pms.pojo.dto.SkuInfoDTO;
import com.youlai.mall.ums.api.MemberFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Base64;

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
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private SkuFeignClient skuFeignClient;


    private MemberFeignClient memberFeignClient;

    private final Long skuId = 1L;// 购买商品ID

    /**
     * 购买商品流程测试
     *
     * @throws Exception
     */
    @Test
    void testPurchaseFlow() throws Exception {

        // 会员登录
        String accessToken = getAccessToken();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        // 添加购物车

        mockMvc.perform(post("/app-api/v1/carts")
                        .param("skuId", String.valueOf(skuId))
                        .headers(headers))
                .andExpect(status().isOk())
                .andReturn();

        // 订单确认
        MvcResult confirmResult = mockMvc.perform(post("/app-api/v1/orders/confirm")
                        .headers(headers))
                .andExpect(status().isOk())
                .andReturn();
        String confirmJsonResponse = confirmResult.getResponse().getContentAsString();
        JsonNode confirmJsonNode = objectMapper.readTree(confirmJsonResponse);
        String orderToken = confirmJsonNode.path("data").path("orderToken").asText();

        // 订单提交
        this.submitOrder(orderToken);

        // 订单支付

    }


    private void submitOrder(String orderToken) throws Exception {
        // 构造请求体
        OrderSubmitForm submitForm = new OrderSubmitForm();
        // 设置 submitForm 的属性
        submitForm.setOrderToken(orderToken);
        submitForm.setSourceType(OrderSourceTypeEnum.APP.getValue());
        submitForm.setRemark("（单元测试）订单备注");
        // submitForm - 商品列表
        OrderSubmitForm.OrderItem orderItem = new OrderSubmitForm.OrderItem();
        SkuInfoDTO skuInfo = skuFeignClient.getSkuInfo(skuId);
        BeanUtils.copyProperties(skuInfo, orderItem);
        submitForm.setOrderItems(Arrays.asList(orderItem));
        // submitForm - 地址
        OrderSubmitForm.ShippingAddress shippingAddress = new OrderSubmitForm.ShippingAddress();


        // 发起 POST 请求
        MockHttpServletRequestBuilder requestBuilder = post("/app-api/v1/orders/submit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(submitForm));

        // 执行请求并断言结果
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value(true))
                .andDo(MockMvcResultHandlers.print());
    }


    private String getAccessToken() {
        String clientId = "mall-app";
        String clientSecret = "123456";
        String mobile = "18866668888";
        String code = "666666";
        String tokenUrl = "http://localhost:9000/oauth/token";

        // 构建请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // 构建请求体
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", "sms_code");
        requestBody.add("client_id", clientId);
        requestBody.add("client_secret", clientSecret);
        requestBody.add("mobile", mobile);
        requestBody.add("code", code);

        // 创建 Basic Auth 头部
        String authHeader = clientId + ":" + clientSecret;
        String encodedAuthHeader = Base64.getEncoder().encodeToString(authHeader.getBytes());
        headers.set("Authorization", "Basic " + encodedAuthHeader);

        // 创建请求实体
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

        // 发送请求
        String jsonStr = restTemplate.postForEntity(tokenUrl, requestEntity, String.class).getBody();

        JSONObject jsonObject = JSONUtil.parseObj(jsonStr);
        JSONObject data = jsonObject.getJSONObject("data");

        String accessToken = data.getStr("access_token");
        return accessToken;
    }


}

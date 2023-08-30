package com.youlai.mall.oms.controller;

import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.youlai.mall.oms.enums.OrderSourceTypeEnum;
import com.youlai.mall.oms.enums.PaymentMethodEnum;
import com.youlai.mall.oms.model.form.OrderPaymentForm;
import com.youlai.mall.oms.model.form.OrderSubmitForm;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 订单单元测试
 *
 * @author haoxr
 * @since 2.3.0
 */
@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class OrderControllerTests {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RestTemplate restTemplate;

    private final Long skuId = 1L;// 购买商品ID
    private final String mobile = "18866668888";// 商城会员手机号
    private final String verifyCode = "666666";// 短信验证码，666666是免校验验证码

    /**
     * 购买商品-正常流程测试
     */
    @Test
    void testPurchaseFlow_Normal() throws Exception {

        // 会员登录
        String accessToken = acquireTokenByLogin(mobile, verifyCode); // 获取 accessToken，填充请求头用于身份认证

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        // 添加购物车
        this.addToCard(skuId, headers);

        // 订单确认
        String orderToken = this.confirmOrder(headers); // 返回订单提交令牌，用于订单提交

        // 订单提交
        String orderSn = this.submitOrder(orderToken, headers); // 返回订单编号，用于订单支付

        // 订单支付
        this.payOrder(orderSn, headers); // 支付成功，商品库存扣减，账户余额扣减，订单状态改变(待支付 → 待发货)
    }

    /**
     * 购买商品-超时未支付流程测试
     */
    @Test
    void testPurchaseFlow_PaymentTimeout() throws Exception {

        // 会员登录
        String accessToken = acquireTokenByLogin(mobile, verifyCode); // 获取 accessToken，填充请求头用于身份认证

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        // 添加购物车
        this.addToCard(skuId, headers);

        // 订单确认
        String orderToken = this.confirmOrder(headers); // 返回订单提交令牌，用于订单提交

        // 订单提交
        String orderSn = this.submitOrder(orderToken, headers); // 返回订单编号，用于订单支付

        // 模拟等待超过支付超时时间
        Thread.sleep(30 * 1000); // OrderRabbitConfig#orderDelayQueue#x-message-ttl 设置10s未支付取消

        // 订单支付
        this.payOrder(orderSn, headers); // 此处支付会异常，因为超时未支付，订单已被系统自动取消，无法再进行支付
    }


    /**
     * 添加商品至购物车
     */
    private void addToCard(Long skuId, HttpHeaders headers) throws Exception {
        mockMvc.perform(post("/app-api/v1/carts")
                        .param("skuId", String.valueOf(skuId))
                        .headers(headers))
                .andExpect(status().isOk())
                .andReturn();
    }

    /**
     * 订单确认
     */
    private String confirmOrder(HttpHeaders headers) throws Exception {
        MvcResult confirmResult = mockMvc.perform(
                        post("/app-api/v1/orders/confirm")
                                .headers(headers)
                ).andExpect(status().isOk())
                .andReturn();
        String confirmJsonResponse = confirmResult.getResponse().getContentAsString();
        log.info("订单确认响应：{}", confirmJsonResponse);
        JsonNode confirmJsonNode = objectMapper.readTree(confirmJsonResponse);
        return confirmJsonNode.path("data").path("orderToken").asText();
    }

    /**
     * 订单提交
     */
    private String submitOrder(String orderToken, HttpHeaders headers) throws Exception {
        // 构造请求体
        OrderSubmitForm submitForm = new OrderSubmitForm();

        // submitForm - 商品列表
        OrderSubmitForm.OrderItem orderItem = new OrderSubmitForm.OrderItem();
        orderItem.setSkuId(skuId);
        orderItem.setCount(1);
        orderItem.setSkuName("REDMI K60 16G+1T");
        orderItem.setSkuSn("sn001");
        orderItem.setSpuName("REDMI K60");
        orderItem.setPrice(399900L);
        orderItem.setPicUrl("https://www.youlai.tech/files/default/c25b39470474494485633c49101a0f5d.png");
        submitForm.setOrderItems(Arrays.asList(orderItem));
        // submitForm - 收货地址
        OrderSubmitForm.ShippingAddress shippingAddress = new OrderSubmitForm.ShippingAddress();
        shippingAddress.setProvince("上海");
        shippingAddress.setCity("上海市");
        shippingAddress.setDistrict("浦东新区");
        shippingAddress.setConsigneeName("法外张三");
        shippingAddress.setConsigneeMobile("18866668888");
        shippingAddress.setDetailAddress("世纪公园");
        submitForm.setShippingAddress(shippingAddress);

        // submitForm - 订单信息
        submitForm.setOrderToken(orderToken);
        submitForm.setPaymentAmount(orderItem.getPrice() * 1);
        submitForm.setSourceType(OrderSourceTypeEnum.APP.getValue());
        submitForm.setRemark("单元测试生成订单");

        // 发起 POST 请求
        MockHttpServletRequestBuilder requestBuilder = post("/app-api/v1/orders/submit")
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(submitForm));

        // 执行请求并断言结果
        MvcResult submitResult = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andReturn();
        String confirmJsonResponse = submitResult.getResponse().getContentAsString();
        JsonNode confirmJsonNode = objectMapper.readTree(confirmJsonResponse);

        return confirmJsonNode.path("data").asText();
    }

    /**
     * 订单支付
     */
    private void payOrder(String orderSn, HttpHeaders headers) throws Exception {

        OrderPaymentForm paymentForm = new OrderPaymentForm();
        paymentForm.setOrderSn(orderSn);
        paymentForm.setPaymentMethodEnum(PaymentMethodEnum.BALANCE);
        mockMvc.perform(post("/app-api/v1/orders/payment")
                        .headers(headers)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(paymentForm))
                ).andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }


    /**
     * 登录获取访问令牌
     *
     * @param mobile     手机号
     * @param verifyCode 短信验证码
     * @return
     */
    private String acquireTokenByLogin(String mobile, String verifyCode) {
        String clientId = "mall-app";
        String clientSecret = "123456";
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
        requestBody.add("code", verifyCode);

        // 创建 Basic Auth 头部
        String authHeader = clientId + ":" + clientSecret;
        String encodedAuthHeader = Base64.getEncoder().encodeToString(authHeader.getBytes());
        headers.set("Authorization", "Basic " + encodedAuthHeader);

        // 创建请求实体
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

        // 发送请求
        String jsonStr = restTemplate.postForEntity(tokenUrl, requestEntity, String.class).getBody();

        return JSONUtil.parseObj(jsonStr).getJSONObject("data").getStr("access_token");
    }


}

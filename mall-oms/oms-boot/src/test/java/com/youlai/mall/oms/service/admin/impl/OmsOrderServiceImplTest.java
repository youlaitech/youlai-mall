package com.youlai.mall.oms.service.admin.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.youlai.mall.oms.model.query.OrderPageQuery;
import com.youlai.mall.oms.model.vo.OmsOrderPageVO;
import com.youlai.mall.oms.service.admin.OmsOrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class OmsOrderServiceImplTest {

    private final String mobile = "18866668888";// 商城会员手机号
    private final String verifyCode = "666666";// 短信验证码，666666是免校验验证码


    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private OmsOrderService omsOrderService;

    @Test
    void testGetOrderPage() {
        // 会员登录
        String accessToken = acquireTokenByLogin(mobile, verifyCode); // 获取 accessToken，填充请求头用于身份认证

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);


        OrderPageQuery queryParams = new OrderPageQuery();
        queryParams.setPageNum(1);
        queryParams.setPageSize(10);
        queryParams.setBeginDate(DateUtil.parseDate("2022-01-01"));
        queryParams.setEndDate(DateUtil.parseDate("2025-01-01"));

        IPage<OmsOrderPageVO> orderPage = omsOrderService.getOrderPage(queryParams);

        log.info(JSONUtil.toJsonStr(orderPage));

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
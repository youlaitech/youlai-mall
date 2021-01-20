package com.youlai.mall.ums.controller.admin;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.youlai.common.core.result.Result;
import com.youlai.mall.ums.pojo.dto.RechargeDTO;
import com.youlai.mall.ums.pojo.dto.ResultPayDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;


@Api(tags = "会员充值接口")
@RestController
@RequestMapping("/api.admin/v1/recharge")
@Slf4j
public class AdminRechargeController {

    @Autowired
    private RestTemplate restTemplate;


    @Value("${zzf.CreateOrderURL}")
    private String createOrderURL;


    @Value("${zzf.FindOrderURL}")
    private String findOrderURL;

    @Value("${zzf.AppKey}")
    private String appKey;


    @Value("${zzf.AppSecret}")
    private String appSecret;


    @ApiOperation(value = "账户余额充值订单", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "会员ID", required = true, paramType = "query", dataType = "Long"),
    })
    @PostMapping
    public Result recharge(@RequestBody RechargeDTO rechargeDTO) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/json;charset=utf-8"));
        headers.set("Payment-Key", appKey);
        headers.set("Payment-Secret", appSecret);

        Map<String, Object> params = new HashMap<>();
        params.put("price", 0);
        params.put("name", "账户余额充值");
        //postMethod.addParameter("callbackurl", "用来通知指定地址");
        //postMethod.addParameter("reurl", "跳转地址");
        //postMethod.addParameter("thirduid", "用户存放您的用户ID");
        //postMethod.addParameter("remarks", "备注");
        //postMethod.addParameter("other", "其他信息");

        HttpEntity<Map> httpEntity = new HttpEntity<>(params, headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(createOrderURL, httpEntity, String.class);
        int statusCode = responseEntity.getStatusCode().value();
        if (statusCode == HttpStatus.SC_OK) {
            String responseBody = responseEntity.getBody();
            JSONObject jsonObject = JSONUtil.parseObj(responseBody);
            if (jsonObject.getStr("code").equals("10001")) {
                Map<String, Object> resultMap = new HashMap<>();
                resultMap.put("name", jsonObject.getStr("name"));
                resultMap.put("price", jsonObject.getStr("price"));
                resultMap.put("orderId", jsonObject.getStr("orderId"));
                return Result.success(resultMap);
            }
        }
        return Result.failed();
    }


    @PostMapping(value = "/callback")
    public void receiveCallBack(@RequestBody ResultPayDTO resultPay) {
        log.info("recharge callback：{}", resultPay.toString());
        //处理自己的业务逻辑
        //例如开通会员、用户充值等等。。。
    }

}

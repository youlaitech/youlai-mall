package com.youlai.mall.ums.controller.admin;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.youlai.common.result.Result;
import com.youlai.mall.ums.pojo.domain.UmsMember;
import com.youlai.mall.ums.pojo.dto.RechargeDTO;
import com.youlai.mall.ums.pojo.dto.ResultPayDTO;
import com.youlai.mall.ums.service.IUmsUserService;
import io.swagger.annotations.Api;
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


@Api(tags = "【系统管理】会员充值")
@RestController("AdminAdminRechargeController")
@RequestMapping("/api.admin/v1/recharge_orders")
@Slf4j
public class RechargeController {

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


    @ApiOperation(value = "账户余额充值订单")
    @PostMapping
    public Result recharge(@RequestBody RechargeDTO rechargeDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        headers.set("Payment-Key", appKey);
        headers.set("Payment-Secret", appSecret);

        HttpEntity<Map> httpEntity = new HttpEntity<>(null, headers);
        String url = createOrderURL + "?price=" + rechargeDTO.getPrice() + "&name=" + rechargeDTO.getName() + "&thirduid=" + rechargeDTO.getThirduid();

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, httpEntity, String.class);
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
            } else {
                return Result.failed(jsonObject.getStr("msg"));
            }
        }
        return Result.failed();
    }


    /**
     * 订单支付状态查询
     */
    @ResponseBody
    @GetMapping("/{id}")
    public Object findOrderState(@PathVariable String id) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        headers.set("Payment-Key", appKey);
        headers.set("Payment-Secret", appSecret);

        String url = findOrderURL + "?orderId=" + id;
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);

        int statusCode = responseEntity.getStatusCode().value();
        if (statusCode == HttpStatus.SC_OK) {
            String responseBody = responseEntity.getBody();
            JSONObject jsonObject = JSONUtil.parseObj(responseBody);
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("code", jsonObject.getStr("code"));
            resultMap.put("msg", jsonObject.getStr("msg"));
            return Result.success(resultMap);

        }
        return Result.failed();
    }

    private IUmsUserService iUmsUserService;

    @PostMapping(value = "/callback")
    public void receiveCallBack(@RequestBody ResultPayDTO resultPay) {
        log.info("recharge callback：{}", resultPay.toString());
        //处理自己的业务逻辑
        //例如开通会员、用户充值等等。。。
        String thirduid = resultPay.getThirduid();
        UmsMember user = iUmsUserService.getById(thirduid);
        if (user != null) {
            user.setBalance((long) (user.getBalance() + Float.valueOf(resultPay.getPrice()) * 100 * 10000));
        }
        iUmsUserService.updateById(user);
    }
}

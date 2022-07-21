package com.youlai.lab.sentinel.controller;

import com.youlai.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@Api(tags = "「实验室」Sentinel接口")
@RestController
@RequestMapping("/api/v1/sentinel")
@RequiredArgsConstructor
@Slf4j
public class SentinelController {

    @ApiOperation("【限流】获取订单列表")
    @GetMapping("/orders/_limit")
    public Result listOrders() {
        List<String> orderList = Arrays.asList("订单1", "订单2", "订单3");
        return Result.success(orderList);
    }

}

package com.youlai.laboratory.sentinel.controller;

import com.youlai.common.result.Result;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "「实验室」Sentinel接口")
@RestController
@RequestMapping("/api/v1/sentinel")
@RequiredArgsConstructor
@Slf4j
public class SentinelController {

    @Operation(summary ="【普通流控】获取数据")
    @GetMapping("/flow_limiting/data")
    public Result getFlowLimitingData() {
        return Result.success("正常数据");
    }

    @Operation(summary ="【网关流控-RouteID】获取数据")
    @GetMapping("/gateway_route_flow_limiting/data")
    public Result getGatewayRouteFlowLimitingData() {
        return Result.success("正常数据");
    }

    @Operation(summary ="【网关流控-API分组】获取数据")
    @GetMapping("/gateway_api_flow_limiting/data")
    public Result getGatewayApiFlowLimitingData() {
        return Result.success("正常数据");
    }

}

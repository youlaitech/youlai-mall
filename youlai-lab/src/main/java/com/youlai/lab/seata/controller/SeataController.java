package com.youlai.lab.seata.controller;

import com.youlai.common.result.Result;
import com.youlai.lab.seata.pojo.vo.SeataDataVO;
import com.youlai.lab.seata.service.ISeataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author <a href="mailto:xianrui0365@163.com">haoxr</a>
 * @date 2022/4/16 20:46
 */

@Api(tags = "「实验室」Seata接口模拟")
@RestController
@RequestMapping("/api/v1/seata")
@RequiredArgsConstructor
public class SeataController {

    private final ISeataService seataService;

    @ApiOperation("订单支付")
    @PostMapping("/order/_pay")
    public Result orderPay() {
        boolean result = seataService.payOrder();
        return Result.success(result);
    }

    @ApiOperation("获取模拟数据")
    @GetMapping("/data")
    public Result getData() {
        SeataDataVO result = seataService.getData();
        return Result.success(result);
    }

    @ApiOperation("重置模拟数据")
    @PutMapping("/data/_reset")
    public Result resetData() {
        boolean result = seataService.resetData();
        return Result.success(result);
    }

}

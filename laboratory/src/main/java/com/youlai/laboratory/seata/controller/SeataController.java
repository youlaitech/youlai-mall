package com.youlai.laboratory.seata.controller;

import com.youlai.common.result.Result;
import com.youlai.laboratory.seata.pojo.form.SeataForm;
import com.youlai.laboratory.seata.pojo.vo.SeataVO;
import com.youlai.laboratory.seata.service.SeataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * Seata 控制层
 *
 * @author haoxr
 * @date 2022/4/16 20:46
 */
@Api(tags = "「实验室」Seata接口")
@RestController
@RequestMapping("/api/v1/seata")
@RequiredArgsConstructor
@Slf4j
public class SeataController {

    private final SeataService seataService;

    @ApiOperation("获取模拟数据")
    @GetMapping("/data")
    public Result getData() {
        SeataVO result = seataService.getData();
        return Result.success(result);
    }

    @ApiOperation("重置模拟数据")
    @PutMapping("/data/_reset")
    public Result resetData() {
        boolean result = seataService.resetData();
        return Result.success(result);
    }

    @ApiOperation("订单支付")
    @PostMapping("/_pay")
    public Result payOrder(@RequestBody SeataForm seataForm) {
        boolean openTx = seataForm.isOpenTx();

        boolean result;
        if (openTx) {
            // 开启全局事务
            result = seataService.payOrderWithGlobalTx(seataForm);
        } else {
            result = seataService.payOrder(seataForm);
        }
        return Result.success(result);
    }
}

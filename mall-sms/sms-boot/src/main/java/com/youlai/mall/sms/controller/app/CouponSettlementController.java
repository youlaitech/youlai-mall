package com.youlai.mall.sms.controller.app;

import com.youlai.common.result.Result;
import com.youlai.mall.sms.executor.ExecutorManager;
import com.youlai.mall.sms.pojo.vo.SettlementInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xinyi
 * @desc
 * @date 2021/7/11
 */
@Api(tags = "「移动端」优惠券核销")
@RestController
@RequestMapping("/api-app/v1/coupon_settlement")
@Slf4j
public class CouponSettlementController {

    @Autowired
    private ExecutorManager executorManager;

    @ApiOperation("优惠券结算")
    @PostMapping("/compute_rule")
    public Result computeRule(@ApiParam("优惠券结算请求") @RequestBody SettlementInfoVO settlement){
        SettlementInfoVO settlementInfoVO = executorManager.computeRule(settlement);
        return Result.success(settlementInfoVO);
    }
}

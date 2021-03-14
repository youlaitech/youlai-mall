package com.youlai.mall.oms.controller.app;


import com.youlai.common.result.Result;
import com.youlai.mall.oms.enums.PayTypeEnum;
import com.youlai.mall.oms.pojo.form.OrderPayForm;
import com.youlai.mall.oms.pojo.vo.PayInfoVO;
import com.youlai.mall.oms.service.IOrderPayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * 订单支付服务
 *
 * @author huawei
 * @email huawei_code@163.com
 * @date 2020-12-30 22:31:10
 */
@Api(tags = "【移动端】订单支付")
@RestController
@RequestMapping("/api.app/v1/order/pay")
@Slf4j
public class OrderPayController {
    @Autowired
    private IOrderPayService orderPayService;

    /**
     * 订单支付
     * 1、根据支付类型选择正确支付方式（1：微信支付；2：支付宝支付；3：余额支付）
     * 2、根据订单ID查询订单价格，进行支付（在整个支付的过程中进行事务控制，保证整个操作的原子性）
     * 3、将支付结果记录日志并返回给前端
     *
     * @param orderPayForm 订单支付表单
     * @return
     */
    @ApiOperation("订单支付")
    @PostMapping
    public Result doPay(@Validated @RequestBody OrderPayForm orderPayForm) {
        PayTypeEnum payTypeEnum = PayTypeEnum.getValue(orderPayForm.getPayType());
        if (payTypeEnum == null) {
            return Result.failed("请选择正确的支付方式");
        }
        log.info("订单支付，orderId={}，支付方式={}", orderPayForm.getOrderId(), payTypeEnum.getText());
        if (payTypeEnum == PayTypeEnum.BALANCE) {
            orderPayService.balancePay(orderPayForm.getOrderId());
        }
        return Result.success();
    }

    @ApiOperation(value = "获取订单支付详情")
    @GetMapping("/info")
    public Result<PayInfoVO> info(
            @ApiParam(name = "orderId", value = "订单ID", required = true, defaultValue = "1")
            @RequestParam("orderId") Long orderId) {
        return Result.success(orderPayService.info(orderId));
    }

}

package com.youlai.mall.oms.controller.app;


import com.youlai.common.result.Result;
import com.youlai.mall.oms.enums.PayTypeEnum;
import com.youlai.mall.oms.pojo.vo.PayInfoVO;
import com.youlai.mall.oms.service.IOrderPayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/api.app/v1/order_pays")
@Slf4j
@AllArgsConstructor
public class OrderPayController {

    private IOrderPayService orderPayService;

    /**
     * 订单支付
     * 1、根据支付类型选择正确支付方式（1：微信支付；2：支付宝支付；3：余额支付）
     * 2、根据订单ID查询订单价格，进行支付（在整个支付的过程中进行事务控制，保证整个操作的原子性）
     * 3、将支付结果记录日志并返回给前端
     *
     * @return
     */
    @ApiOperation("订单支付")
    @PostMapping
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderId", value = "订单ID", paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "payType", value = "支付方式", paramType = "query", dataType = "Integer")

    })
    public Result pay(
            Integer payType,
            Long orderId
    ) {
        PayTypeEnum payTypeEnum = PayTypeEnum.getValue(payType);

        switch (payTypeEnum) {
            case ALIPAY:
            case WEIXIN:
                // TODO
                break;
            case BALANCE:
                orderPayService.payWithBalance(orderId);
                break;
            default:
                return Result.failed("系统暂不支持该支付方式~");
        }
        return Result.success();
    }

    @ApiOperation(value = "获取订单支付详情")
    @GetMapping("/orderId/{orderId}")
    public Result detail(@PathVariable Long orderId) {
        PayInfoVO payInfo = orderPayService.getPayInfo(orderId);
        return Result.success(payInfo);
    }
}

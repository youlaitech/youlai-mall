package com.youlai.mall.oms.controller.app;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.common.result.Result;
import com.youlai.common.web.util.JwtUtils;
import com.youlai.mall.oms.enums.PayTypeEnum;
import com.youlai.mall.oms.pojo.dto.OrderConfirmDTO;
import com.youlai.mall.oms.pojo.dto.OrderSubmitDTO;
import com.youlai.mall.oms.pojo.entity.OmsOrder;
import com.youlai.mall.oms.pojo.vo.OrderConfirmVO;
import com.youlai.mall.oms.pojo.vo.OrderSubmitVO;
import com.youlai.mall.oms.service.IOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


/**
 * @author huawei
 * @email huawei_code@163.com
 * @date 2020-12-30 22:31:10
 */
@Api(tags = "【移动端】订单服务")
@RestController
@RequestMapping("/app-api/v1/orders")
@Slf4j
@RequiredArgsConstructor
public class OrderController {

    final IOrderService orderService;

    @ApiOperation("订单列表")
    @GetMapping
    public Result list(
            @RequestParam(defaultValue = "1") Long page,
            @RequestParam(defaultValue = "10") Long limit,
            Integer status
    ) {
        IPage<OmsOrder> result = orderService.list(new Page<>(page, limit),
                new OmsOrder()
                        .setStatus(status)
                        .setMemberId(JwtUtils.getUserId()));
        return Result.success(result.getRecords(), result.getTotal());
    }

    @ApiOperation("订单确认")
    @PostMapping("/_confirm")
    public Result<OrderConfirmVO> confirm(@RequestBody OrderConfirmDTO orderConfirm) {
        OrderConfirmVO result = orderService.confirm(orderConfirm);
        return Result.success(result);
    }

    @ApiOperation("订单提交")
    @PostMapping("/_submit")
    public Result submit(@Valid @RequestBody OrderSubmitDTO orderSubmitDTO) {
        OrderSubmitVO result = orderService.submit(orderSubmitDTO);
        return Result.success(result);
    }

    @ApiOperation("订单支付")
    @PostMapping("/{orderId}/_pay")
    public Result pay(@PathVariable Long orderId, Integer payType) {
        PayTypeEnum payTypeEnum = PayTypeEnum.getByCode(payType);
        switch (payTypeEnum) {
            case BALANCE:
                orderService.pay(orderId);
                break;
            default:
                return Result.failed("系统暂不支持该支付方式~");
        }
        return Result.success();
    }

    @ApiOperation("订单删除")
    @PostMapping("/{orderId}")
    public Result deleteOrder(@PathVariable Long orderId) {
        boolean result = orderService.deleteOrder(orderId);
        return Result.judge(result);
    }
}

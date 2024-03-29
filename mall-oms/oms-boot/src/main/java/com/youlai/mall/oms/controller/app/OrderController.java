package com.youlai.mall.oms.controller.app;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.youlai.common.result.PageResult;
import com.youlai.common.result.Result;
import com.youlai.mall.oms.model.form.OrderPaymentForm;
import com.youlai.mall.oms.model.form.OrderSubmitForm;
import com.youlai.mall.oms.model.query.OrderPageQuery;
import com.youlai.mall.oms.model.vo.OrderConfirmVO;
import com.youlai.mall.oms.model.vo.OrderPageVO;
import com.youlai.mall.oms.service.app.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * APP-订单控制层
 *
 * @author huawei
 * @since 2020/12/30
 */
@Tag(name  = "APP-订单接口")
@RestController
@RequestMapping("/app-api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @Operation(summary ="订单分页列表")
    @GetMapping
    public PageResult<OrderPageVO> getOrderPage(OrderPageQuery queryParams) {
        IPage<OrderPageVO> result = orderService.getOrderPage(queryParams);
        return PageResult.success(result);
    }

    @Operation(summary = "订单确认", description = "进入订单确认页面有两个入口，1：立即购买；2：购物车结算")
    @PostMapping("/confirm")
    public Result<OrderConfirmVO> confirmOrder(
            @Parameter(name ="立即购买必填，购物车结算不填") @RequestParam(required = false) Long skuId
    ) {
        OrderConfirmVO result = orderService.confirmOrder(skuId);
        return Result.success(result);
    }

    @Operation(summary ="订单提交")
    @PostMapping("/submit")
    public Result<String> submitOrder(@Validated @RequestBody OrderSubmitForm submitForm) {
        String orderSn = orderService.submitOrder(submitForm);
        return Result.success(orderSn);
    }

    @Operation(summary ="订单支付")
    @PostMapping("/payment")
    public Result payOrder(@Validated @RequestBody OrderPaymentForm paymentForm) {
        boolean result = orderService.payOrder(paymentForm);
        return Result.judge(result);
    }

    @Operation(summary ="订单删除")
    @DeleteMapping("/{orderId}")
    public Result deleteOrder(@PathVariable Long orderId) {
        boolean deleted = orderService.deleteOrder(orderId);
        return Result.judge(deleted);
    }

}

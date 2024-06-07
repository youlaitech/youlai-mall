package com.youlai.mall.order.controller.app;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.youlai.common.result.PageResult;
import com.youlai.common.result.Result;
import com.youlai.mall.order.model.form.OrderRefundForm;
import com.youlai.mall.order.model.form.OrderPayForm;
import com.youlai.mall.order.model.form.OrderSubmitForm;
import com.youlai.mall.order.model.query.OrderPageQuery;
import com.youlai.mall.order.model.vo.OrderConfirmVO;
import com.youlai.mall.order.model.vo.OrderPageAppVO;
import com.youlai.mall.order.service.OrderService;
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
@Tag(name = "【APP】订单接口")
@RestController
@RequestMapping("/app-api/v1/orders")
@RequiredArgsConstructor
public class OrderAppController {

    private final OrderService orderService;

    @Operation(summary = "订单分页列表")
    @GetMapping
    public PageResult<OrderPageAppVO> listPagedOrders(OrderPageQuery queryParams) {
        IPage<OrderPageAppVO> result = orderService.listAppPagedOrders(queryParams);
        return PageResult.success(result);
    }

    @Operation(summary = "订单确认", description = "进入订单确认页面有两个入口，1：立即购买；2：购物车结算")
    @PostMapping("/confirm")
    public Result<OrderConfirmVO> confirmOrder(
            @Parameter(description = "立即购买必填，购物车结算不填") @RequestParam(required = false) Long skuId
    ) {
        OrderConfirmVO result = orderService.confirmOrder(skuId);
        return Result.success(result);
    }

    @Operation(summary = "订单提交")
    @PostMapping("/submit")
    public Result<String> submitOrder(@Validated @RequestBody OrderSubmitForm submitForm) {
        String orderNo = orderService.submitOrder(submitForm);
        return Result.success(orderNo);
    }

    @Operation(summary = "订单支付")
    @PostMapping("/pay")
    public Result payOrder(@Validated @RequestBody OrderPayForm paymentForm) {
        boolean result = orderService.payOrder(paymentForm);
        return Result.judge(result);
    }

    @Operation(summary = "订单申请退款")
    @PostMapping("/refund/apply")
    public Result refundApply(
            @Validated @RequestBody OrderRefundForm refundForm
    ) {
        boolean result = orderService.refundApply(refundForm);
        return Result.judge(result);
    }

    @Operation(summary = "订单删除")
    @DeleteMapping("/{orderId}")
    public Result deleteOrder(@PathVariable Long orderId) {
        boolean deleted = orderService.deleteOrder(orderId);
        return Result.judge(deleted);
    }

}

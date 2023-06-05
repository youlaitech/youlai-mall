package com.youlai.mall.oms.controller.app;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.youlai.common.result.PageResult;
import com.youlai.common.result.Result;
import com.youlai.mall.oms.model.entity.OmsOrder;
import com.youlai.mall.oms.model.form.OrderSubmitForm;
import com.youlai.mall.oms.model.query.OrderPageQuery;
import com.youlai.mall.oms.model.vo.OrderConfirmVO;
import com.youlai.mall.oms.model.vo.OrderSubmitResultVO;
import com.youlai.mall.oms.service.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 「移动端」订单控制层
 *
 * @author huawei
 * @since 2020/12/30
 */
@Tag(name = "「移动端」订单接口")
@RestController
@RequestMapping("/app-api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    final OrderService orderService;

    @Operation(summary ="分页列表")
    @GetMapping
    public PageResult listOrderPages(OrderPageQuery queryParams) {
        IPage<OmsOrder> result = orderService.listOrderPages(queryParams);
        return PageResult.success(result);
    }

    /**
     * 订单确认 → 进入创建订单页面
     * <p>
     * 获取购买商品明细、用户默认收货地址、防重提交唯一token
     * 进入订单创建页面有两个入口，1：立即购买；2：购物车结算
     *
     * @param skuId 直接购买必填，购物车结算不填
     * @return
     */
    @Operation(summary ="订单确认")
    @PostMapping("/_confirm")
    public Result<OrderConfirmVO> confirmOrder(@RequestParam(required = false) Long skuId) {
        OrderConfirmVO result = orderService.confirmOrder(skuId);
        return Result.success(result);
    }

    @Operation(summary ="订单提交")
    @PostMapping("/_submit")
    public Result submitOrder(@RequestBody @Validated OrderSubmitForm orderSubmitForm) {
        OrderSubmitResultVO result = orderService.submitOrder(orderSubmitForm);
        return Result.success(result);
    }

    @Operation(summary ="订单支付")
    @PostMapping("/{orderId}/_pay")
    public Result payOrder(@PathVariable Long orderId) {
        boolean result = orderService.payOrder(orderId);
        return Result.judge(result);
    }

    @Operation(summary ="订单删除")
    @DeleteMapping("/{orderId}")
    public Result deleteOrder(@PathVariable Long orderId) {
        boolean result = orderService.deleteOrder(orderId);
        return Result.judge(result);
    }

}

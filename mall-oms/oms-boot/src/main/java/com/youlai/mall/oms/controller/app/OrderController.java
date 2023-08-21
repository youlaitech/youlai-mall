package com.youlai.mall.oms.controller.app;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.youlai.common.result.PageResult;
import com.youlai.common.result.Result;
import com.youlai.mall.oms.common.enums.PayTypeEnum;
import com.youlai.mall.oms.pojo.entity.OmsOrder;
import com.youlai.mall.oms.pojo.form.OrderSubmitForm;
import com.youlai.mall.oms.pojo.query.OrderPageQuery;
import com.youlai.mall.oms.pojo.dto.OrderConfirmResult;
import com.youlai.mall.oms.pojo.dto.OrderSubmitResult;
import com.youlai.mall.oms.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 「移动端」订单控制层
 *
 * @author huawei
 * @date 2020/12/30
 */
@Api(tags = "「移动端」订单接口")
@RestController
@RequestMapping("/app-api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    final OrderService orderService;

    @ApiOperation("订单分页列表")
    @GetMapping
    public PageResult<OmsOrder> getOrderPage(OrderPageQuery queryParams) {
        IPage<OmsOrder> result = orderService.getOrderPage(queryParams);
        return PageResult.success(result);
    }

    /**
     * 订单确认 → 进入创建订单页面
     * <p>
     * 获取购买商品明细、用户默认收货地址、防重提交唯一token
     * 进入订单创建页面有两个入口，1：立即购买；2：购物车结算
     *
     * @param skuId 直接购买必填，购物车结算不填
     * @return {@link  OrderConfirmResult}
     */
    @ApiOperation("订单确认")
    @PostMapping("/confirm")
    public Result<OrderConfirmResult> confirmOrder(@RequestParam(required = false) Long skuId) {
        OrderConfirmResult result = orderService.confirmOrder(skuId);
        return Result.success(result);
    }

    @ApiOperation("订单提交")
    @PostMapping("/submit")
    public Result<OrderSubmitResult> submitOrder(@RequestBody @Validated OrderSubmitForm submitForm) {
        OrderSubmitResult result = orderService.submitOrder(submitForm);
        return Result.success(result);
    }

    @ApiOperation("订单支付")
    @PostMapping("/{orderId}/payment")
    public Result<Boolean> payOrder(@PathVariable Long orderId,
                                    @RequestParam(required = false) String appId,
                                    @RequestParam PayTypeEnum payTypeEnum) {
        boolean result = orderService.payOrder(orderId,appId,payTypeEnum);
        return Result.judge(result);
    }

    @ApiOperation("订单删除")
    @DeleteMapping("/{orderId}")
    public Result<Boolean> deleteOrder(@PathVariable Long orderId) {
        boolean deleted = orderService.deleteOrder(orderId);
        return Result.judge(deleted);
    }

}

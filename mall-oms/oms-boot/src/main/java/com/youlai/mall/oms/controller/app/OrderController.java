package com.youlai.mall.oms.controller.app;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.youlai.common.result.PageResult;
import com.youlai.common.result.Result;
import com.youlai.mall.oms.pojo.entity.OmsOrder;
import com.youlai.mall.oms.pojo.form.OrderSubmitForm;
import com.youlai.mall.oms.pojo.query.OrderPageQuery;
import com.youlai.mall.oms.pojo.vo.OrderConfirmVO;
import com.youlai.mall.oms.pojo.vo.OrderSubmitResultVO;
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

    @ApiOperation("分页列表")
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
    @ApiOperation("订单确认")
    @PostMapping("/_confirm")
    public Result<OrderConfirmVO> confirmOrder(@RequestParam(required = false) Long skuId) {
        OrderConfirmVO result = orderService.confirmOrder(skuId);
        return Result.success(result);
    }

    @ApiOperation("订单提交")
    @PostMapping("/_submit")
    public Result submitOrder(@RequestBody @Validated OrderSubmitForm orderSubmitForm) {
        OrderSubmitResultVO result = orderService.submitOrder(orderSubmitForm);
        return Result.success(result);
    }

    @ApiOperation("订单支付")
    @PostMapping("/{orderId}/_pay")
    public Result payOrder(@PathVariable Long orderId) {
        boolean result = orderService.payOrder(orderId);
        return Result.judge(result);
    }

    @ApiOperation("订单删除")
    @DeleteMapping("/{orderId}")
    public Result deleteOrder(@PathVariable Long orderId) {
        boolean result = orderService.deleteOrder(orderId);
        return Result.judge(result);
    }

}

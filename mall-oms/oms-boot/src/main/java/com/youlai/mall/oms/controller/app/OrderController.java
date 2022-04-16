package com.youlai.mall.oms.controller.app;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.youlai.common.base.IBaseEnum;
import com.youlai.common.result.PageResult;
import com.youlai.common.result.Result;
import com.youlai.mall.oms.enums.PayTypeEnum;
import com.youlai.mall.oms.pojo.entity.OmsOrder;
import com.youlai.mall.oms.pojo.form.OrderSubmitForm;
import com.youlai.mall.oms.pojo.query.OrderPageQuery;
import com.youlai.mall.oms.pojo.vo.OrderConfirmVO;
import com.youlai.mall.oms.pojo.vo.OrderSubmitVO;
import com.youlai.mall.oms.service.IOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author huawei
 * @email huawei_code@163.com
 * @date 2020-12-30 22:31:10
 */
@Api(tags = "「移动端」订单管理")
@RestController
@RequestMapping("/app-api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    final IOrderService orderService;

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
    public Result<OrderConfirmVO> confirm(
            @RequestParam(required = false) Long skuId
    ) {
        OrderConfirmVO result = orderService.confirm(skuId);
        return Result.success(result);
    }

    @ApiOperation("订单提交")
    @PostMapping("/_submit")
    public Result submit(@Valid @RequestBody OrderSubmitForm orderSubmitForm) {
        OrderSubmitVO result = orderService.submit(orderSubmitForm);
        return Result.success(result);
    }

    @ApiOperation("订单支付")
    @PostMapping("/{orderId}/_pay")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderId", value = "订单ID", paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "payType", value = "支付方式", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "appId", value = "小程序appId", paramType = "query", dataType = "String")
    })
    public <T> Result<T> pay(@PathVariable Long orderId, Integer payType, String appId) {

        PayTypeEnum payTypeEnum = IBaseEnum.getEnumByValue(payType, PayTypeEnum.class);
        if (payTypeEnum == null) {
            return Result.failed("系统暂不支持该支付方式~");
        }
        return Result.success(orderService.pay(orderId, appId, payTypeEnum));
    }

    @ApiOperation("订单删除")
    @DeleteMapping("/{orderId}")
    public Result deleteOrder(@PathVariable Long orderId) {
        boolean result = orderService.deleteOrder(orderId);
        return Result.judge(result);
    }

    @ApiOperation("订单取消")
    @PutMapping("/cancel")
    public Result cancel(@RequestParam Long id) {
        boolean result = orderService.cancelOrder(id);
        return Result.judge(result);
    }
}

package com.youlai.mall.oms.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.youlai.common.result.PageResult;
import com.youlai.common.result.Result;
import com.youlai.mall.oms.dto.OrderInfoDTO;
import com.youlai.mall.oms.dto.SeataOrderDTO;
import com.youlai.mall.oms.common.enums.OrderStatusEnum;
import com.youlai.mall.oms.pojo.dto.OrderDTO;
import com.youlai.mall.oms.pojo.entity.OmsOrder;
import com.youlai.mall.oms.pojo.entity.OmsOrderItem;
import com.youlai.mall.oms.pojo.query.OrderPageQuery;
import com.youlai.mall.oms.service.OrderItemService;
import com.youlai.mall.oms.service.admin.OmsOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * 「管理端」订单控制层
 *
 * @author huawei
 * @date 2020/12/30
 */
@Api(tags = "「管理端」订单管理")
@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OmsOrderController {

    private final OmsOrderService orderService;

    private final OrderItemService orderItemService;

    @ApiOperation("订单分页列表")
    @GetMapping
    public PageResult listOrderPages(OrderPageQuery queryParams) {
        IPage<OmsOrder> result = orderService.listOrderPages(queryParams);
        return PageResult.success(result);
    }

    @ApiOperation(value = "订单详情")
    @GetMapping("/{orderId}")
    public Result getOrderDetail(
            @ApiParam("订单ID") @PathVariable Long orderId
    ) {
        OrderDTO orderDTO = new OrderDTO();
        // 订单
        OmsOrder order = orderService.getById(orderId);

        // 订单明细
        List<OmsOrderItem> orderItems = orderItemService.list(new LambdaQueryWrapper<OmsOrderItem>()
                .eq(OmsOrderItem::getOrderId, orderId)
        );
        orderItems = Optional.ofNullable(orderItems).orElse(Collections.EMPTY_LIST);

        orderDTO.setOrder(order).setOrderItems(orderItems);
        return Result.success(orderDTO);
    }

    @ApiOperation(value = "「实验室」获取订单信息", hidden = true)
    @GetMapping("/{orderId}/orderInfo")
    public Result<OrderInfoDTO> getOrderInfo(
            @ApiParam("订单ID") @PathVariable Long orderId
    ) {
        OrderInfoDTO orderInfo = new OrderInfoDTO();

        OmsOrder order = orderService.getById(orderId);
        if (order != null) {
            orderInfo.setOrderSn(order.getOrderSn());
            orderInfo.setStatus(order.getStatus());
        }
        return Result.success(orderInfo);
    }

    @ApiOperation(value = "「实验室」订单支付", hidden = true)
    @PutMapping("/{orderId}/_pay")
    public Result payOrder(
            @ApiParam("订单ID") @PathVariable Long orderId,
            @RequestBody SeataOrderDTO orderDTO
    ) {
        Boolean result = orderService.payOrder(orderId, orderDTO);
        return Result.judge(result);
    }

    @ApiOperation(value = "「实验室」订单重置", hidden = true)
    @PutMapping("/{orderId}/_reset")
    public Result resetOrder(
            @ApiParam("订单ID") @PathVariable Long orderId
    ) {
        boolean result = orderService.update(new LambdaUpdateWrapper<OmsOrder>()
                .eq(OmsOrder::getId, orderId)
                .set(OmsOrder::getStatus, OrderStatusEnum.UNPAID.getValue())
        );
        return Result.judge(result);
    }

}

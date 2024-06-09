package com.youlai.mall.order.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.youlai.common.result.PageResult;
import com.youlai.common.result.Result;
import com.youlai.mall.order.model.dto.OrderDTO;
import com.youlai.mall.order.model.entity.OmsOrder;
import com.youlai.mall.order.model.entity.OmsOrderItem;
import com.youlai.mall.order.model.form.OrderRefundApprovalForm;
import com.youlai.mall.order.model.query.OrderPageQuery;
import com.youlai.mall.order.model.vo.OrderPageAdminVO;
import com.youlai.mall.order.service.OrderService;
import com.youlai.mall.order.service.OrderItemService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Admin-订单控制层
 *
 * @author huawei
 * @since 2.3.0
 */
@Tag(name  = "【Admin】订单管理")
@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderItemService orderItemService;

    @Operation(summary ="订单分页列表")
    @GetMapping("/page")
    public PageResult<OrderPageAdminVO> listAdminPagedOrders(OrderPageQuery queryParams) {
        IPage<OrderPageAdminVO> page = orderService.listAdminPagedOrders(queryParams);
        return PageResult.success(page);
    }

    @Operation(summary = "订单退款审批")
    @PostMapping("/refund/approval")
    public Result refundApproval(
            @Validated @RequestBody OrderRefundApprovalForm refundApprovalForm
    ) {
        boolean result = orderService.refundApproval(refundApprovalForm);
        return Result.judge(result);
    }



    @Operation(summary = "订单详情")
    @GetMapping("/{orderId}")
    public Result getOrderDetail(
            @Parameter(description ="订单ID") @PathVariable Long orderId
    ) {
        OrderDTO orderDTO = new OrderDTO();
        // 订单
        OmsOrder order = orderService.getById(orderId);

        // 订单明细
        List orderItems = orderItemService.list(new LambdaQueryWrapper<OmsOrderItem>()
                .eq(OmsOrderItem::getOrderId, orderId)
        );
        orderItems = Optional.ofNullable(orderItems).orElse(Collections.EMPTY_LIST);

        orderDTO.setOrder(order).setOrderItems(orderItems);
        return Result.success(orderDTO);
    }
}

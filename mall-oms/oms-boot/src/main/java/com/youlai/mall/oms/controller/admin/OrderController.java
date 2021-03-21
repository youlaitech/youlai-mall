package com.youlai.mall.oms.controller.admin;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.common.result.Result;
import com.youlai.mall.oms.pojo.bo.app.OrderBO;
import com.youlai.mall.oms.pojo.domain.OmsOrder;
import com.youlai.mall.oms.pojo.domain.OmsOrderItem;
import com.youlai.mall.oms.service.IOrderItemService;
import com.youlai.mall.oms.service.IOrderService;
import com.youlai.mall.ums.api.UmsMemberFeignService;
import com.youlai.mall.ums.pojo.dto.MemberDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * @author huawei
 * @email huawei_code@163.com
 * @date 2020-12-30 22:31:10
 */
@Api(tags = "【系统管理】订单服务")
@RestController("AdminOrderController")
@RequestMapping("/api.admin/v1/orders")
@Slf4j
@AllArgsConstructor
public class OrderController {

    private IOrderService orderService;
    private IOrderItemService orderItemService;
    private UmsMemberFeignService memberFeignService;

    @ApiOperation("订单列表")
    @GetMapping
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", defaultValue = "1", value = "页码", paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "limit", defaultValue = "10", value = "每页数量", paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "status", value = "订单状态", paramType = "query", dataType = "Integer"),

    })
    public Result list(
            @RequestParam(defaultValue = "1") Long page,
            @RequestParam(defaultValue = "10") Long limit,
            Integer status,
             String startDate,
             String endDate,
            String orderSn
    ) {
        LambdaQueryWrapper<OmsOrder> queryWrapper = new LambdaQueryWrapper<OmsOrder>()
                .like(StrUtil.isNotBlank(orderSn), OmsOrder::getOrderSn, orderSn)
                .eq(status != null, OmsOrder::getStatus, status)
                .apply(StrUtil.isNotBlank(startDate),
                        "date_format (gmt_crate,'%Y-%m-%d') >= date_format('" + startDate + "','%Y-%m-%d')")
                .apply(StrUtil.isNotBlank(endDate),
                        "date_format (gmt_crate,'%Y-%m-%d') <= date_format('" + endDate + "','%Y-%m-%d')")
                .orderByDesc(OmsOrder::getGmtModified)
                .orderByDesc(OmsOrder::getGmtCreate);

        IPage<OmsOrder> result = orderService.list(new Page<>(page, limit), new OmsOrder().setStatus(status));
        return Result.success(result.getRecords(), result.getTotal());
    }


    @ApiOperation(value = "订单详情", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "订单id", required = true, paramType = "path", dataType = "Long")
    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) {
        OrderBO orderBO = new OrderBO();
        // 订单
        OmsOrder order = orderService.getById(id);

        // 订单明细
        List<OmsOrderItem> orderItems = orderItemService.list(
                new LambdaQueryWrapper<OmsOrderItem>().eq(OmsOrderItem::getOrderId, id)
        );
        orderItems = Optional.ofNullable(orderItems).orElse(new ArrayList<>());

        // 会员明细
        Result<MemberDTO> result = memberFeignService.getUserById(order.getMemberId());
        MemberDTO member = result.getData();
        orderBO.setOrder(order).setOrderItems(orderItems).setMember(member);
        return Result.success(orderBO);
    }

}

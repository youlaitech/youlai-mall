package com.youlai.mall.oms.controller.admin;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.common.core.result.Result;
import com.youlai.mall.oms.bo.OrderBO;
import com.youlai.mall.oms.service.IOmsOrderService;
import com.youlai.mall.oms.pojo.OmsOrder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Api(tags = "订单接口")
@RestController
@RequestMapping("/api.admin/v1/orders")
@Slf4j
@AllArgsConstructor
public class OmsOrderController {

    private IOmsOrderService iOmsOrderService;

    @ApiOperation(value = "列表分页", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "limit", value = "每页数量", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "queryMode", value = "查询模式", paramType = "query", dataType = "Integer", defaultValue = "1"),
            @ApiImplicitParam(name = "orderSn", value = "订单编号", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "status", value = "订单状态", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "startDate", value = "开始日期", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "endDate", value = "结束日期", paramType = "query", dataType = "String"),
    })
    @GetMapping
    public Result list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer limit,
            @RequestParam(defaultValue = "1") Integer queryMode,
            @RequestParam(required = false) String orderSn,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate
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

        if (queryMode.equals(1)) { // 分页列表
            Page<OmsOrder> result = iOmsOrderService.page(new Page<>(page, limit), queryWrapper);
            return Result.success(result.getRecords(), result.getTotal());
        } else { // 常规列表
            List<OmsOrder> list = iOmsOrderService.list(queryWrapper);
            return Result.success(list);
        }
    }

    @ApiOperation(value = "订单详情", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "订单id", required = true, paramType = "path", dataType = "Integer")
    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) {
        OrderBO order = iOmsOrderService.getByOrderId(id);
        return Result.success(order);
    }

    @ApiOperation(value = "订单提交", httpMethod = "POST")
    @ApiImplicitParam(name = "orderBO", value = "实体JSON对象", required = true, paramType = "body", dataType = "OrderBO")
    @PostMapping
    public Result add(@RequestBody OrderBO orderBO) {
        boolean status = iOmsOrderService.save(orderBO);
        return Result.status(status);
    }

    @ApiOperation(value = "修改订单", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "订单id", required = true, paramType = "path", dataType = "Integer"),
            @ApiImplicitParam(name = "order", value = "实体JSON对象", required = true, paramType = "body", dataType = "OmsOrder")
    })
    @PutMapping(value = "/{id}")
    public Result update(
            @PathVariable Integer id,
            @RequestBody OmsOrder order) {
        boolean status = iOmsOrderService.updateById(order);
        return Result.status(status);
    }

    @ApiOperation(value = "删除订单", httpMethod = "DELETE")
    @ApiImplicitParam(name = "ids[]", value = "id集合", required = true, paramType = "query", allowMultiple = true, dataType = "Integer")
    @DeleteMapping
    public Result delete(@RequestParam("ids") List<Long> ids) {
        boolean status = iOmsOrderService.removeByIds(ids);
        return Result.status(status);
    }

    @ApiOperation(value = "修改订单(部分更新)", httpMethod = "PATCH")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", required = true, paramType = "path", dataType = "Integer"),
            @ApiImplicitParam(name = "order", value = "实体JSON对象", required = true, paramType = "body", dataType = "OmsOrder")
    })
    @PatchMapping(value = "/{id}")
    public Result patch(@PathVariable Integer id, @RequestBody OmsOrder order) {
        LambdaUpdateWrapper<OmsOrder> luw = new LambdaUpdateWrapper<OmsOrder>().eq(OmsOrder::getId, id);
        if (order.getStatus() != null) { // 状态更新
            luw.set(OmsOrder::getStatus, order.getStatus());
        }
        boolean update = iOmsOrderService.update(luw);
        return Result.success(update);
    }


    @ApiOperation(value = "订单提交", httpMethod = "POST")
    @PostMapping("/submit")
    public Result submit() {
        boolean status = iOmsOrderService.submit();
        return Result.status(status);
    }
}

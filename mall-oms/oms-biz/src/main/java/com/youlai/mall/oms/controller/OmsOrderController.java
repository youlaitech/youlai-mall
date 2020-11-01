package com.youlai.mall.oms.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.common.core.result.PageResult;
import com.youlai.common.core.result.Result;
import com.youlai.mall.oms.service.IOmsOrderService;
import com.youlai.mall.oms.entity.OmsOrder;
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
@RequestMapping("/orders")
@Slf4j
@AllArgsConstructor
public class OmsOrderController {

    private IOmsOrderService iOmsOrderService;

    @ApiOperation(value = "列表分页", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "limit", value = "每页数量", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "order", value = "订单信息", paramType = "query", dataType = "OmsOrder")
    })
    @GetMapping
    public Result list(Integer page, Integer limit, OmsOrder order) {
        LambdaQueryWrapper<OmsOrder> queryWrapper = new LambdaQueryWrapper<OmsOrder>()
                .like(StrUtil.isNotBlank(order.getOrderSn()), OmsOrder::getOrderSn, order.getOrderSn())
                .orderByDesc(OmsOrder::getGmtModified)
                .orderByDesc(OmsOrder::getGmtCreate);
        if (page != null && limit != null) {
            Page<OmsOrder> result = iOmsOrderService.page(new Page<>(page, limit), queryWrapper);
            return PageResult.success(result.getRecords(), result.getTotal());
        } else if (limit != null) {
            queryWrapper.last("LIMIT " + limit);
        }
        List<OmsOrder> list = iOmsOrderService.list(queryWrapper);
        return Result.success(list);
    }

    @ApiOperation(value = "订单详情", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "订单id", required = true, paramType = "path", dataType = "Integer")
    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        OmsOrder order = iOmsOrderService.getById(id);
        return Result.success(order);
    }

    @ApiOperation(value = "新增订单", httpMethod = "POST")
    @ApiImplicitParam(name = "order", value = "实体JSON对象", required = true, paramType = "body", dataType = "OmsOrder")
    @PostMapping
    public Result add(@RequestBody OmsOrder order) {
        boolean status = iOmsOrderService.save(order);
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
}

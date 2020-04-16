package com.fly4j.yshop.oms.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fly4j.common.core.controller.BaseController;
import com.fly4j.yshop.oms.pojo.entity.OmsOrder;
import com.fly4j.yshop.oms.service.IOmsOrderService;
import com.fly4j.yshop.pms.feign.PmsFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "订单接口")
@RestController
@RequestMapping("/orders")
@Slf4j
public class OmsOrderController extends BaseController {

    @Resource
    private IOmsOrderService iOmsOrderService;

    @ApiOperation(value = "订单分页", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", required = true, paramType = "path", dataType = "Integer", defaultValue = "0"),
            @ApiImplicitParam(name = "limit", value = "每页数量", required = true, paramType = "path", dataType = "Integer", defaultValue = "10"),
            @ApiImplicitParam(name = "order_sn", value = "订单编号", paramType = "query", dataType = "String"),
    })
    @GetMapping("/page/{page}/limit/{limit}")
    public R<Page<OmsOrder>> page(
            @PathVariable Integer page,
            @PathVariable Integer limit,
            String order_sn
    ) {
        Page<OmsOrder> data = (Page<OmsOrder>) iOmsOrderService.page(new Page<>(page, limit),
                new LambdaQueryWrapper<OmsOrder>()
                        .eq(StrUtil.isNotBlank(order_sn), OmsOrder::getOrder_sn, order_sn)
                        .orderByDesc(OmsOrder::getCreate_time));
        return R.ok(data);
    }

    @ApiOperation(value = "订单列表", httpMethod = "GET")
    @GetMapping()
    public R list() {
        List<OmsOrder> list = iOmsOrderService.list();
        return R.ok(list);
    }

    @ApiOperation(value = "新增订单", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "omsOrder", value = "实体JSON对象", required = true, paramType = "body", dataType = "OmsOrder")
    })
    @PostMapping
    public R save(@RequestBody OmsOrder omsOrder) {
        boolean status = iOmsOrderService.save(omsOrder);
        return status ? R.ok(null) : R.failed("新增失败");
    }

    @ApiOperation(value = "订单详情", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "订单id", required = true, paramType = "path", dataType = "Long"),
    })
    @GetMapping("/{id}")
    public R get(@PathVariable Long id) {
        OmsOrder user = iOmsOrderService.getById(id);
        return R.ok(user);
    }

    @ApiOperation(value = "修改订单", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "订单id", required = true, paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "omsOrder", value = "实体JSON对象", required = true, paramType = "body", dataType = "OmsOrder")
    })
    @PutMapping(value = "/{id}")
    public R update(@PathVariable("id") Long id, @RequestBody OmsOrder omsOrder) {
        boolean status = iOmsOrderService.updateById(omsOrder);
        return status ? R.ok(null) : R.failed("更新失败");
    }

    @ApiOperation(value = "删除订单", httpMethod = "DELETE")
    @ApiImplicitParam(name = "ids", value = "订单id", required = true, paramType = "query", allowMultiple = true, dataType = "Long")
    @DeleteMapping()
    public R delete(@RequestParam("ids") List<Long> ids) {
        boolean status = iOmsOrderService.removeByIds(ids);
        return status ? R.ok(null) : R.failed("删除失败");
    }

    @ApiOperation(value = "修改订单状态", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "订单id", required = true, paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "status", value = "显示状态", required = true, paramType = "path", dataType = "Integer")
    })
    @PutMapping("/id/{id}/status/{status}")
    public R updateStatus(@PathVariable Integer id, @PathVariable Integer status) {
        boolean result = iOmsOrderService.update(new LambdaUpdateWrapper<OmsOrder>()
                .eq(OmsOrder::getId, id)
                .set(OmsOrder::getStatus, status));
        if (result) {
            return R.ok("更新成功");
        } else {
            return R.failed("更新失败");
        }
    }


    @Autowired
    private PmsFeign pmsFeign;

    @ApiOperation(value = "订单商品详情", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "商品ID", required = true, paramType = "path", dataType = "Long"),
    })
    @GetMapping("/spu/{id}")
    public R getSpu(@PathVariable Long id) {
        return pmsFeign.getSpuById(id);
    }


    @ApiOperation(value = "订单发货", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "订单id", required = true, paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "omsOrder", value = "实体JSON对象", required = true, paramType = "body", dataType = "OmsOrder")
    })
    @PutMapping(value = "/{id}/deliver")
    public R deliver(@PathVariable("id") Long id, @RequestBody OmsOrder omsOrder) {
        boolean status = iOmsOrderService.update(new LambdaUpdateWrapper<OmsOrder>()
                .eq(OmsOrder::getId, id)
                .set(OmsOrder::getLogistics_company, omsOrder.getLogistics_company())
                .set(OmsOrder::getLogistics_number, omsOrder.getLogistics_number())
                .set(OmsOrder::getDelivery_time, new java.util.Date())
        );
        return status ? R.ok(null) : R.failed("发货失败");
    }
}
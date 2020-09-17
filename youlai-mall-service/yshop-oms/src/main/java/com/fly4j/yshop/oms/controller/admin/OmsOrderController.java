package com.fly4j.yshop.oms.controller.admin;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.common.core.base.controller.BaseController;
import com.fly4j.yshop.oms.pojo.dto.OrderDTO;
import com.fly4j.yshop.oms.pojo.entity.OmsOrder;
import com.fly4j.yshop.oms.pojo.entity.OmsOrderItem;
import com.fly4j.yshop.oms.service.IOmsOrderItemService;
import com.fly4j.yshop.oms.service.IOmsOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Api(tags = "ADMIN-订单管理")
@RestController
@RequestMapping("/orders")
@Slf4j
public class OmsOrderController extends BaseController {

    @Resource
    private IOmsOrderService iOmsOrderService;

    @Resource
    private IOmsOrderItemService iOmsOrderItemService;


    @ApiOperation(value = "订单分页", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", required = true, paramType = "path", dataType = "Integer", defaultValue = "0"),
            @ApiImplicitParam(name = "limit", value = "每页数量", required = true, paramType = "path", dataType = "Integer", defaultValue = "10"),
            @ApiImplicitParam(name = "member_id", value = "会员ID", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "order_sn", value = "订单编号", paramType = "query", dataType = "String")
    })
    @GetMapping("/page/{page}/limit/{limit}")
    public R<Page<OmsOrder>> page(
            @PathVariable Integer page,
            @PathVariable Integer limit,
            String member_id,
            String order_sn
    ) {
        Page<OmsOrder> data = iOmsOrderService.page(new Page<>(page, limit),
                new LambdaQueryWrapper<OmsOrder>()
                        .eq(StrUtil.isNotBlank(member_id), OmsOrder::getMember_id, member_id)
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
            @ApiImplicitParam(name = "id", value = "订单id", required = true, paramType = "path", dataType = "Integer"),
    })
    @GetMapping("/{id}")
    public R get(@PathVariable Integer id) {
        OrderDTO orderDTO = new OrderDTO();
        OmsOrder order = iOmsOrderService.getById(id);
        if (order != null) {
            orderDTO.setOrder(order);
            List<OmsOrderItem> orderItemList = iOmsOrderItemService.list(
                    new LambdaQueryWrapper<OmsOrderItem>().eq(OmsOrderItem::getOrder_id, id)
            );
            orderDTO.setOrder_item_list(orderItemList);
        }
        return R.ok(orderDTO);
    }

    @ApiOperation(value = "修改订单", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "订单id", required = true, paramType = "path", dataType = "Integer"),
            @ApiImplicitParam(name = "omsOrder", value = "实体JSON对象", required = true, paramType = "body", dataType = "OmsOrder")
    })
    @PutMapping(value = "/{id}")
    public R update(@PathVariable("id") Integer id, @RequestBody OmsOrder omsOrder) {
        boolean status = iOmsOrderService.updateById(omsOrder);
        return status ? R.ok(null) : R.failed("更新失败");
    }

    @ApiOperation(value = "删除订单", httpMethod = "DELETE")
    @ApiImplicitParam(name = "ids", value = "订单id", required = true, paramType = "query", allowMultiple = true, dataType = "Integer")
    @DeleteMapping()
    public R delete(@RequestParam("ids") List<Long> ids) {
        boolean status = iOmsOrderService.removeByIds(ids);
        return status ? R.ok(null) : R.failed("删除失败");
    }

    @ApiOperation(value = "修改订单状态", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "订单id", required = true, paramType = "path", dataType = "Integer"),
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


    @ApiOperation(value = "订单发货", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "订单id", required = true, paramType = "path", dataType = "Integer"),
            @ApiImplicitParam(name = "omsOrder", value = "实体JSON对象", required = true, paramType = "body", dataType = "OmsOrder")
    })
    @PutMapping(value = "/{id}/deliver")
    public R deliver(@PathVariable("id") Integer id, @RequestBody OmsOrder omsOrder) {
        boolean status = iOmsOrderService.update(new LambdaUpdateWrapper<OmsOrder>()
                .eq(OmsOrder::getId, id)
                .set(OmsOrder::getLogistics_company, omsOrder.getLogistics_company())
                .set(OmsOrder::getLogistics_number, omsOrder.getLogistics_number())
                .set(OmsOrder::getDeliver_time, new Date())
                .set(OmsOrder::getStatus, 2) // 2-已发货
        );
        return status ? R.ok(null) : R.failed("发货失败");
    }


}
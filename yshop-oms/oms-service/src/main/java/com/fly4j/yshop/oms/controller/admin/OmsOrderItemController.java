package com.fly4j.yshop.oms.controller.admin;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fly4j.common.core.controller.BaseController;
import com.fly4j.yshop.oms.pojo.entity.OmsOrderItem;
import com.fly4j.yshop.oms.service.IOmsOrderItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "订单明细接口")
@RestController
@RequestMapping("/orderItems")
@Slf4j
public class OmsOrderItemController extends BaseController {

    @Resource
    private IOmsOrderItemService iOmsOrderItemService;

    @ApiOperation(value = "订单明细分页", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", required = true, paramType = "path", dataType = "Integer", defaultValue = "0"),
            @ApiImplicitParam(name = "limit", value = "每页数量", required = true, paramType = "path", dataType = "Integer", defaultValue = "10"),
            @ApiImplicitParam(name = "name", value = "名称", paramType = "query", dataType = "String"),
    })
    @GetMapping("/page/{page}/limit/{limit}")
    public R<Page<OmsOrderItem>> page(
            @PathVariable Integer page,
            @PathVariable Integer limit,
            String name
    ) {
        Page<OmsOrderItem> data = (Page<OmsOrderItem>) iOmsOrderItemService.page(new Page<>(page, limit),
                new LambdaQueryWrapper<OmsOrderItem>()
                        .eq(StrUtil.isNotBlank(name), OmsOrderItem::getSpu_name, name)
                        .orderByDesc(OmsOrderItem::getCreate_time));
        return R.ok(data);
    }

    @ApiOperation(value = "订单明细列表", httpMethod = "GET")
    @GetMapping()
    public R list() {
        List<OmsOrderItem> list = iOmsOrderItemService.list();
        return R.ok(list);
    }

    @ApiOperation(value = "新增订单明细", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "omsOrderItem", value = "实体JSON对象", required = true, paramType = "body", dataType = "OmsOrderItem")
    })
    @PostMapping
    public R save(@RequestBody OmsOrderItem omsOrderItem) {
        boolean status = iOmsOrderItemService.save(omsOrderItem);
        return status ? R.ok(null) : R.failed("新增失败");
    }

    @ApiOperation(value = "订单明细详情", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "订单明细id", required = true, paramType = "path", dataType = "Long"),
    })
    @GetMapping("/{id}")
    public R get(@PathVariable Long id) {
        OmsOrderItem user = iOmsOrderItemService.getById(id);
        return R.ok(user);
    }

    @ApiOperation(value = "修改订单明细", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "订单明细id", required = true, paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "omsOrderItem", value = "实体JSON对象", required = true, paramType = "body", dataType = "OmsOrderItem")
    })
    @PutMapping(value = "/{id}")
    public R update(@PathVariable("id") Long id, @RequestBody OmsOrderItem omsOrderItem) {
        boolean status = iOmsOrderItemService.updateById(omsOrderItem);
        return status ? R.ok(null) : R.failed("更新失败");
    }

    @ApiOperation(value = "删除订单明细", httpMethod = "DELETE")
    @ApiImplicitParam(name = "ids", value = "订单明细id", required = true, paramType = "query", allowMultiple = true, dataType = "Long")
    @DeleteMapping()
    public R delete(@RequestParam("ids") List<Long> ids) {
        boolean status = iOmsOrderItemService.removeByIds(ids);
        return status ? R.ok(null) : R.failed("删除失败");
    }
}
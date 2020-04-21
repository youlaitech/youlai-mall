package com.fly4j.yshop.sms.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fly4j.common.core.controller.BaseController;
import com.fly4j.yshop.sms.pojo.entity.SmsCoupon;
import com.fly4j.yshop.sms.service.ISmsCouponService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "优惠券API")
@RestController
@RequestMapping("/coupons")
@Slf4j
public class SmsCouponController extends BaseController {
    @Resource
    private ISmsCouponService iSmsCouponService;

    @ApiOperation(value = "优惠券分页", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", required = true, paramType = "path", dataType = "Integer", defaultValue = "0"),
            @ApiImplicitParam(name = "limit", value = "每页数量", required = true, paramType = "path", dataType = "Integer", defaultValue = "10"),
            @ApiImplicitParam(name = "name", value = "优惠券名称", paramType = "query", dataType = "String"),
    })
    @GetMapping("/page/{page}/limit/{limit}")
    public R<Page<SmsCoupon>> page(
            @PathVariable Integer page,
            @PathVariable Integer limit,
            String name
    ) {
        Page<SmsCoupon> data = (Page<SmsCoupon>) iSmsCouponService.page(new Page<>(page, limit),
                new LambdaQueryWrapper<SmsCoupon>()
                        .eq(StrUtil.isNotBlank(name), SmsCoupon::getName, name)
                        .orderByDesc(SmsCoupon::getCreate_time));
        return R.ok(data);
    }

    @ApiOperation(value = "优惠券列表", httpMethod = "GET")
    @GetMapping()
    public R list() {
        List<SmsCoupon> list = iSmsCouponService.list();
        return R.ok(list);
    }

    @ApiOperation(value = "优惠券详情", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "优惠券id", required = true, paramType = "path", dataType = "Long"),
    })
    @GetMapping("/{id}")
    public R get(@PathVariable Long id) {
        SmsCoupon coupon = iSmsCouponService.getById(id);
        return R.ok(coupon);
    }

    @ApiOperation(value = "新增优惠券", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "smsCoupon", value = "实体JSON对象", required = true, paramType = "body", dataType = "SmsCoupon")
    })
    @PostMapping
    public R save(@RequestBody SmsCoupon smsCoupon) {
        boolean status = iSmsCouponService.save(smsCoupon);
        return status ? R.ok(null) : R.failed("新增失败");
    }

    @ApiOperation(value = "修改优惠券", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "优惠券id", required = true, paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "smsCoupon", value = "实体JSON对象", required = true, paramType = "body", dataType = "SmsCoupon")
    })
    @PutMapping(value = "/{id}")
    public R update(@PathVariable("id") Long id, @RequestBody SmsCoupon smsCoupon) {
        boolean status = iSmsCouponService.updateById(smsCoupon);
        return status ? R.ok(null) : R.failed("更新失败");
    }

    @ApiOperation(value = "删除优惠券", httpMethod = "DELETE")
    @ApiImplicitParam(name = "ids", value = "优惠券id", required = true, paramType = "query", allowMultiple = true, dataType = "Long")
    @DeleteMapping()
    public R delete(@RequestParam("ids") List<Long> ids) {
        boolean status = iSmsCouponService.removeByIds(ids);
        return status ? R.ok(null) : R.failed("删除失败");
    }

}

package com.fly4j.yshop.sms.controller.admin;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.common.core.controller.BaseController;
import com.fly4j.yshop.sms.pojo.entity.SmsHomeAd;
import com.fly4j.yshop.sms.service.ISmsHomeAdService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "ADMIN-广告管理")
@RestController
@RequestMapping("/ads")
@Slf4j
public class SmHomeAdController extends BaseController {
    @Resource
    private ISmsHomeAdService iSmsHomeAdService;

    @ApiOperation(value = "广告分页", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", required = true, paramType = "path", dataType = "Integer", defaultValue = "0"),
            @ApiImplicitParam(name = "limit", value = "每页数量", required = true, paramType = "path", dataType = "Integer", defaultValue = "10"),
            @ApiImplicitParam(name = "name", value = "广告名称", paramType = "query", dataType = "String"),
    })
    @GetMapping("/page/{page}/limit/{limit}")
    public R<Page<SmsHomeAd>> page(
            @PathVariable Integer page,
            @PathVariable Integer limit,
            String name
    ) {
        Page<SmsHomeAd> data = iSmsHomeAdService.page(new Page<>(page, limit),
                new LambdaQueryWrapper<SmsHomeAd>()
                        .eq(StrUtil.isNotBlank(name), SmsHomeAd::getName, name)
                        .orderByDesc(SmsHomeAd::getCreate_time));
        return R.ok(data);
    }

    @ApiOperation(value = "广告列表", httpMethod = "GET")
    @GetMapping()
    public R list() {
        List<SmsHomeAd> list = iSmsHomeAdService.list();
        return R.ok(list);
    }

    @ApiOperation(value = "广告详情", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "广告id", required = true, paramType = "path", dataType = "Integer"),
    })
    @GetMapping("/{id}")
    public R get(@PathVariable Long id) {
        SmsHomeAd coupon = iSmsHomeAdService.getById(id);
        return R.ok(coupon);
    }

    @ApiOperation(value = "新增广告", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "smsHomeAd", value = "实体JSON对象", required = true, paramType = "body", dataType = "SmsHomeAd")
    })
    @PostMapping
    public R save(@RequestBody SmsHomeAd smsHomeAd) {
        boolean status = iSmsHomeAdService.save(smsHomeAd);
        return status ? R.ok(null) : R.failed("新增失败");
    }

    @ApiOperation(value = "修改广告", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "广告id", required = true, paramType = "path", dataType = "Integer"),
            @ApiImplicitParam(name = "smsHomeAd", value = "实体JSON对象", required = true, paramType = "body", dataType = "SmsHomeAd")
    })
    @PutMapping(value = "/{id}")
    public R update(@PathVariable("id") Long id, @RequestBody SmsHomeAd smsHomeAd) {
        boolean status = iSmsHomeAdService.updateById(smsHomeAd);
        return status ? R.ok(null) : R.failed("更新失败");
    }

    @ApiOperation(value = "删除广告", httpMethod = "DELETE")
    @ApiImplicitParam(name = "ids", value = "广告id", required = true, paramType = "query", allowMultiple = true, dataType = "Integer")
    @DeleteMapping()
    public R delete(@RequestParam("ids") List<Long> ids) {
        boolean status = iSmsHomeAdService.removeByIds(ids);
        return status ? R.ok(null) : R.failed("删除失败");
    }
}

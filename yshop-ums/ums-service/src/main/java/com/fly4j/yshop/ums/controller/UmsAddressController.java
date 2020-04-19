package com.fly4j.yshop.ums.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fly4j.common.core.controller.BaseController;
import com.fly4j.yshop.ums.service.IUmsAddressService;
import com.fly4j.yshop.ums.pojo.entity.UmsAddress;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "地址接口")
@RestController
@RequestMapping("/addresses")
@Slf4j
public class UmsAddressController extends BaseController {
    @Resource
    private IUmsAddressService iUmsAddressService;
    
    @ApiOperation(value = "地址分页", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", required = true, paramType = "path", dataType = "Integer", defaultValue = "0"),
            @ApiImplicitParam(name = "limit", value = "每页数量", required = true, paramType = "path", dataType = "Integer", defaultValue = "10"),
            @ApiImplicitParam(name = "name", value = "收货人名称", paramType = "query", dataType = "String"),
    })
    @GetMapping("/page/{page}/limit/{limit}")
    public R<Page<UmsAddress>> page(
            @PathVariable Integer page,
            @PathVariable Integer limit,
            String name
    ) {
        Page<UmsAddress> data = iUmsAddressService.page(new Page<>(page, limit),
                new LambdaQueryWrapper<UmsAddress>()
                        .eq(StrUtil.isNotBlank(name), UmsAddress::getName, name)
                        .orderByDesc(UmsAddress::getCreate_time));
        return R.ok(data);
    }

    @ApiOperation(value = "地址列表", httpMethod = "GET")
    @GetMapping()
    public R list() {
        List<UmsAddress> list = iUmsAddressService.list();
        return R.ok(list);
    }

    @ApiOperation(value = "地址详情", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "地址id", required = true, paramType = "path", dataType = "Long"),
    })
    @GetMapping("/{id}")
    public R get(@PathVariable Long id) {
        UmsAddress address = iUmsAddressService.getById(id);
        return R.ok(address);
    }

    @ApiOperation(value = "新增地址", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "umsAddress", value = "实体JSON对象", required = true, paramType = "body", dataType = "UmsAddress")
    })
    @PostMapping
    public R save(@RequestBody UmsAddress umsAddress) {
        boolean status = iUmsAddressService.save(umsAddress);
        return status ? R.ok(null) : R.failed("新增失败");
    }

    @ApiOperation(value = "修改地址", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "地址id", required = true, paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "umsAddress", value = "实体JSON对象", required = true, paramType = "body", dataType = "UmsAddress")
    })
    @PutMapping(value = "/{id}")
    public R update(@PathVariable("id") Long id, @RequestBody UmsAddress umsAddress) {
        boolean status = iUmsAddressService.updateById(umsAddress);
        return status ? R.ok(null) : R.failed("更新失败");
    }

    @ApiOperation(value = "删除地址", httpMethod = "DELETE")
    @ApiImplicitParam(name = "ids", value = "地址id", required = true, paramType = "query", allowMultiple = true, dataType = "Long")
    @DeleteMapping()
    public R delete(@RequestParam("ids") List<Long> ids) {
        boolean status = iUmsAddressService.removeByIds(ids);
        return status ? R.ok(null) : R.failed("删除失败");
    }

}

package com.youlai.mall.pms.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.common.core.constant.SystemConstants;
import com.youlai.common.core.result.Result;
import com.youlai.mall.pms.pojo.PmsBrand;
import com.youlai.mall.pms.service.IPmsBrandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "品牌接口")
@RestController
@RequestMapping("/brands")
@Slf4j
@AllArgsConstructor
public class PmsBrandController {

    private IPmsBrandService iPmsBrandService;

    @ApiOperation(value = "列表分页", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "queryMode", value = "查询模式（1-分页查询 2-下拉列表）", defaultValue = "1", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "page", value = "页码", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "limit", value = "每页数量", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "name", value = "品牌名称", paramType = "query", dataType = "String")
    })
    @GetMapping
    public Result list(@RequestParam(defaultValue = "1") Integer queryMode, Integer page, Integer limit, String name) {
        LambdaQueryWrapper<PmsBrand> queryWrapper = new LambdaQueryWrapper<PmsBrand>()
                .orderByDesc(PmsBrand::getGmtModified)
                .orderByDesc(PmsBrand::getGmtCreate);
        if (queryMode.equals(1)) { // 分页查询
            Page<PmsBrand> result = iPmsBrandService.page(new Page<>(page, limit), queryWrapper);
            return Result.success(result.getRecords(), result.getTotal());
        } else if (queryMode.equals(2)) { // 下拉列表
            queryWrapper
                    .eq(PmsBrand::getStatus, SystemConstants.STATUS_NORMAL_VALUE)
                    .select(PmsBrand::getId, PmsBrand::getName);
        }
        List<PmsBrand> list = iPmsBrandService.list(queryWrapper);
        return Result.success(list);
    }

    @ApiOperation(value = "品牌详情", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "品牌id", required = true, paramType = "path", dataType = "Integer")
    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        PmsBrand brand = iPmsBrandService.getById(id);
        return Result.success(brand);
    }

    @ApiOperation(value = "新增品牌", httpMethod = "POST")
    @ApiImplicitParam(name = "brand", value = "实体JSON对象", required = true, paramType = "body", dataType = "PmsBrand")
    @PostMapping
    public Result add(@RequestBody PmsBrand brand) {
        boolean status = iPmsBrandService.save(brand);
        return Result.status(status);
    }

    @ApiOperation(value = "修改品牌", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "品牌id", required = true, paramType = "path", dataType = "Integer"),
            @ApiImplicitParam(name = "brand", value = "实体JSON对象", required = true, paramType = "body", dataType = "PmsBrand")
    })
    @PutMapping(value = "/{id}")
    public Result update(
            @PathVariable Integer id,
            @RequestBody PmsBrand brand) {
        boolean status = iPmsBrandService.updateById(brand);
        return Result.status(status);
    }

    @ApiOperation(value = "删除品牌", httpMethod = "DELETE")
    @ApiImplicitParam(name = "ids[]", value = "id集合", required = true, paramType = "query", allowMultiple = true, dataType = "Integer")
    @DeleteMapping
    public Result delete(@RequestParam("ids") List<Long> ids) {
        boolean status = iPmsBrandService.removeByIds(ids);
        return Result.status(status);
    }

    @ApiOperation(value = "修改品牌(部分更新)", httpMethod = "PATCH")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", required = true, paramType = "path", dataType = "Integer"),
            @ApiImplicitParam(name = "brand", value = "实体JSON对象", required = true, paramType = "body", dataType = "PmsBrand")
    })
    @PatchMapping(value = "/{id}")
    public Result patch(@PathVariable Integer id, @RequestBody PmsBrand brand) {
        LambdaUpdateWrapper<PmsBrand> luw = new LambdaUpdateWrapper<PmsBrand>().eq(PmsBrand::getId, id);
        if (brand.getStatus() != null) { // 状态更新
            luw.set(PmsBrand::getStatus, brand.getStatus());
        }
        boolean update = iPmsBrandService.update(luw);
        return Result.success(update);
    }
}

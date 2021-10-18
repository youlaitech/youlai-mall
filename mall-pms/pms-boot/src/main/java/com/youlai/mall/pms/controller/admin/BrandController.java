package com.youlai.mall.pms.controller.admin;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.common.result.Result;
import com.youlai.mall.pms.pojo.entity.PmsBrand;
import com.youlai.mall.pms.service.IPmsBrandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @author <a href="mailto:xianrui0365@163.com">xianrui</a>
 */
@Api(tags = "系统管理端-品牌信息")
@RestController
@RequestMapping("/api/v1/brands")
@RequiredArgsConstructor
public class BrandController {

    private final IPmsBrandService iPmsBrandService;

    @ApiOperation(value = "品牌列表分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "limit", value = "每页数量", paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "name", value = "品牌名称", paramType = "query", dataType = "String")
    })
    @GetMapping("/page")
    public Result page(Integer page, Integer limit, String name) {
        LambdaQueryWrapper<PmsBrand> queryWrapper = new LambdaQueryWrapper<PmsBrand>().like(StrUtil.isNotBlank(name), PmsBrand::getName, name);
        Page<PmsBrand> result = iPmsBrandService.page(new Page<>(page, limit), queryWrapper);
        return Result.success(result.getRecords(), result.getTotal());
    }


    @ApiOperation(value = "品牌列表")
    @GetMapping
    public Result list() {
        List<PmsBrand> list = iPmsBrandService.list(new LambdaQueryWrapper<PmsBrand>()
                .select(PmsBrand::getId, PmsBrand::getName));
        return Result.success(list);
    }


    @ApiOperation(value = "品牌详情")
    @ApiImplicitParam(name = "id", value = "品牌id", required = true, paramType = "path", dataType = "Long")
    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        PmsBrand brand = iPmsBrandService.getById(id);
        return Result.success(brand);
    }

    @ApiOperation(value = "新增品牌")
    @ApiImplicitParam(name = "brand", value = "实体JSON对象", required = true, paramType = "body", dataType = "PmsBrand")
    @PostMapping
    public Result add(@RequestBody PmsBrand brand) {
        boolean status = iPmsBrandService.save(brand);
        return Result.judge(status);
    }

    @ApiOperation(value = "修改品牌")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "品牌id", required = true, paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "brand", value = "实体JSON对象", required = true, paramType = "body", dataType = "PmsBrand")
    })
    @PutMapping(value = "/{id}")
    public Result update(
            @PathVariable Long id,
            @RequestBody PmsBrand brand) {
        boolean status = iPmsBrandService.updateById(brand);
        return Result.judge(status);
    }

    @ApiOperation(value = "删除品牌")
    @ApiImplicitParam(name = "ids", value = "id集合", required = true, dataType = "String")
    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable("ids") String ids) {
        boolean status = iPmsBrandService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.judge(status);
    }
}

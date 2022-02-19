package com.youlai.mall.pms.controller.admin;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.common.result.PageResult;
import com.youlai.common.result.Result;
import com.youlai.mall.pms.pojo.entity.PmsBrand;
import com.youlai.mall.pms.service.IPmsBrandService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @author <a href="mailto:xianrui0365@163.com">haoxr</a>
 */
@Api(tags = "「系统端」品牌信息")
@RestController
@RequestMapping("/api/v1/brands")
@RequiredArgsConstructor
public class BrandController {

    private final IPmsBrandService iPmsBrandService;

    @ApiOperation(value = "品牌列表分页")
    @GetMapping("/page")
    public PageResult getBrandPageList(
            @ApiParam("页码") Long pageNum,
            @ApiParam("每页数量") Long pageSize,
            @ApiParam("品牌名称") String name
    ) {
        LambdaQueryWrapper<PmsBrand> queryWrapper = new LambdaQueryWrapper<PmsBrand>()
                .like(StrUtil.isNotBlank(name), PmsBrand::getName, name)
                .orderByDesc(PmsBrand::getGmtModified)
                .orderByDesc(PmsBrand::getGmtCreate);
        Page<PmsBrand> result = iPmsBrandService.page(new Page<>(pageNum, pageSize), queryWrapper);
        return PageResult.success(result);
    }

    @ApiOperation(value = "品牌列表")
    @GetMapping
    public Result getBrandList() {
        List<PmsBrand> list = iPmsBrandService.list(new LambdaQueryWrapper<PmsBrand>()
                .select(PmsBrand::getId, PmsBrand::getName));
        return Result.success(list);
    }

    @ApiOperation(value = "品牌详情")
    @ApiImplicitParam(name = "id", value = "品牌id", required = true, paramType = "path", dataType = "Long")
    @GetMapping("/{id}")
    public Result getBrandList(@PathVariable Integer id) {
        PmsBrand brand = iPmsBrandService.getById(id);
        return Result.success(brand);
    }

    @ApiOperation(value = "新增品牌")
    @ApiImplicitParam(name = "brand", value = "实体JSON对象", required = true, paramType = "body", dataType = "PmsBrand")
    @PostMapping
    public Result addBrand(@RequestBody PmsBrand brand) {
        boolean status = iPmsBrandService.save(brand);
        return Result.judge(status);
    }

    @ApiOperation(value = "修改品牌")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "品牌id", required = true, paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "brand", value = "实体JSON对象", required = true, paramType = "body", dataType = "PmsBrand")
    })
    @PutMapping(value = "/{id}")
    public Result updateBrand(
            @PathVariable Long id,
            @RequestBody PmsBrand brand) {
        boolean status = iPmsBrandService.updateById(brand);
        return Result.judge(status);
    }

    @ApiOperation(value = "删除品牌")
    @ApiImplicitParam(name = "ids", value = "id集合", required = true, dataType = "String")
    @DeleteMapping("/{ids}")
    public Result deleteBrands(@PathVariable("ids") String ids) {
        boolean status = iPmsBrandService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.judge(status);
    }
}

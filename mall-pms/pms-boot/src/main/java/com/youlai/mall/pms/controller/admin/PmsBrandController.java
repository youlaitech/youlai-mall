package com.youlai.mall.pms.controller.admin;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.common.result.PageResult;
import com.youlai.common.result.Result;
import com.youlai.mall.pms.pojo.entity.PmsBrand;
import com.youlai.mall.pms.pojo.query.BrandPageQuery;
import com.youlai.mall.pms.service.BrandService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 品牌管理控制器
 *
 * @author <a href="mailto:xianrui0365@163.com">haoxr</a>
 * @date 2022/7/2
 */
@Api(tags = "「管理端」品牌接口")
@RestController
@RequestMapping("/api/v1/brands")
@RequiredArgsConstructor
public class PmsBrandController {

    private final BrandService brandService;

    @ApiOperation(value = "品牌分页列表")
    @GetMapping("/pages")
    public PageResult listBrandPages(BrandPageQuery queryParams ) {

        // 查询参数
        int pageNum = queryParams.getPageNum();
        int pageSize = queryParams.getPageSize();
        String keywords = queryParams.getKeywords();

        // 分页查询
        Page<PmsBrand> result = brandService.page(new Page<>(pageNum, pageSize), new LambdaQueryWrapper<PmsBrand>()
                .like(StrUtil.isNotBlank(keywords), PmsBrand::getName, keywords)
                .orderByDesc(PmsBrand::getCreateTime));
        return PageResult.success(result);
    }

    @ApiOperation(value = "品牌列表")
    @GetMapping
    public Result getBrandList() {
        List<PmsBrand> list = brandService.list(new LambdaQueryWrapper<PmsBrand>()
                .select(PmsBrand::getId, PmsBrand::getName));
        return Result.success(list);
    }

    @ApiOperation(value = "品牌详情")
    @ApiImplicitParam(name = "id", value = "品牌id", required = true, paramType = "path", dataType = "Long")
    @GetMapping("/{id}")
    public Result getBrandList(@PathVariable Integer id) {
        PmsBrand brand = brandService.getById(id);
        return Result.success(brand);
    }

    @ApiOperation(value = "新增品牌")
    @PostMapping
    public Result addBrand(@RequestBody PmsBrand brand) {
        boolean status = brandService.save(brand);
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
        boolean status = brandService.updateById(brand);
        return Result.judge(status);
    }

    @ApiOperation(value = "删除品牌")
    @DeleteMapping("/{ids}")
    public Result deleteBrands(@ApiParam("品牌ID，多个以英文逗号(,)分割")  @PathVariable("ids") String ids) {
        boolean status = brandService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.judge(status);
    }
}

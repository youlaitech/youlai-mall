package com.youlai.mall.product.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.common.result.PageResult;
import com.youlai.common.result.Result;
import com.youlai.common.core.model.Option;
import com.youlai.mall.product.model.entity.Brand;
import com.youlai.mall.product.model.query.BrandPageQuery;
import com.youlai.mall.product.model.vo.BrandPageVO;
import com.youlai.mall.product.service.BrandCategoryService;
import com.youlai.mall.product.service.BrandService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 【Admin】品牌控制器
 *
 * @author Ray.Hao
 * @since 2024/5/7
 */
@Tag(name = "【Admin】品牌接口")
@RestController
@RequestMapping("/api/v1/brands")
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;

    private final BrandCategoryService brandCategoryService;

    @Operation(summary = "品牌分页列表")
    @GetMapping("/page")
    public PageResult<BrandPageVO> listPagedBrands(BrandPageQuery queryParams) {
        // 分页查询
        Page<BrandPageVO> page = brandService.listPagedBrands(queryParams);
        return PageResult.success(page);
    }

    @Operation(summary = "品牌下拉列表")
    @GetMapping("/options")
    public Result<List<Option<Long>>> listBrandOptions(
          @Parameter(description = "分类ID")  @RequestParam(required = false) Long categoryId
    ) {
        List<Option<Long>> list = brandService.listBrandOptions(categoryId);
        return Result.success(list);
    }

    @Operation(summary = "品牌表单数据")
    @GetMapping("/{id}")
    public Result<Brand> getBrandDetail(@PathVariable Long id) {
        Brand brand = brandService.getById(id);
        return Result.success(brand);
    }

    @Operation(summary = "新增品牌")
    @PostMapping
    public Result addBrand(@RequestBody Brand brand) {
        boolean status = brandService.save(brand);
        return Result.judge(status);
    }

    @Operation(summary = "修改品牌")
    @PutMapping(value = "/{id}")
    public Result updateBrand(
            @PathVariable Long id,
            @RequestBody Brand brand) {
        boolean status = brandService.updateById(brand);
        return Result.judge(status);
    }

    @Operation(summary = "删除品牌")
    @DeleteMapping("/{ids}")
    public Result deleteBrands(
            @Parameter(description = "品牌ID，多个以英文逗号(,)分割") @PathVariable("ids") String ids
    ) {
        boolean status = brandService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.judge(status);
    }

    @Operation(summary = "获取品牌关联的分类列表")
    @GetMapping("/{brandId}/categories")
    public Result<List<Option>> getCategoryOptions(
           @Parameter(description = "品牌ID") @PathVariable Long brandId
    ) {
        List<Option> categories = brandCategoryService.getCategoryOptions(brandId);
        return Result.success(categories);
    }

    @Operation(summary = "修改品牌分类关联")
    @PutMapping("/{brandId}/categories")
    public Result saveBrandCategories(
            @Parameter(description = "品牌ID") @PathVariable Long brandId,
            @RequestBody List<Long> categoryIds) {
        brandCategoryService.saveBrandCategories(brandId, categoryIds);
        return Result.success();
    }

}

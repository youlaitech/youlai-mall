package com.youlai.mall.product.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.common.result.PageResult;
import com.youlai.common.result.Result;
import com.youlai.common.core.model.Option;
import com.youlai.mall.product.model.entity.BrandEntity;
import com.youlai.mall.product.model.query.BrandPageQuery;
import com.youlai.mall.product.model.vo.BrandPageVO;
import com.youlai.mall.product.service.BrandService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 品牌控制层
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

    @Operation(summary = "品牌分页列表")
    @GetMapping("/page")
    public PageResult<BrandPageVO> getBrandPage(BrandPageQuery queryParams) {
        Page<BrandPageVO> page = brandService.getBrandPage(queryParams);
        return PageResult.success(page);
    }

    @Operation(summary = "品牌下拉列表")
    @GetMapping("/options")
    public Result<List<Option<Long>>> listBrandOptions( ) {
        List<Option<Long>> list = brandService.listBrandOptions();
        return Result.success(list);
    }

    @Operation(summary = "品牌表单数据")
    @GetMapping("/{id}/form")
    public Result<BrandEntity> getBrandForm(@PathVariable Long id) {
        BrandEntity brandEntity = brandService.getById(id);
        return Result.success(brandEntity);
    }

    @Operation(summary = "新增品牌")
    @PostMapping
    public Result addBrand(@RequestBody BrandEntity brandEntity) {
        boolean status = brandService.save(brandEntity);
        return Result.judge(status);
    }

    @Operation(summary = "修改品牌")
    @PutMapping(value = "/{id}")
    public Result updateBrand(
            @PathVariable Long id,
            @RequestBody BrandEntity brandEntity) {
        boolean status = brandService.updateById(brandEntity);
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

}

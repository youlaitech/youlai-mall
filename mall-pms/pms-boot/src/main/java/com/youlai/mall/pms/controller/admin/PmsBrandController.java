package com.youlai.mall.pms.controller.admin;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.common.result.PageResult;
import com.youlai.common.result.Result;
import com.youlai.mall.pms.model.entity.PmsBrand;
import com.youlai.mall.pms.model.query.BrandPageQuery;
import com.youlai.mall.pms.service.BrandService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 品牌管理控制器
 *
 * @author <a href="mailto:xianrui0365@163.com">haoxr</a>
 * @since 2022/7/2
 */
@Tag(name = "Admin-品牌接口")
@RestController
@RequestMapping("/api/v1/brands")
@RequiredArgsConstructor
public class PmsBrandController {

    private final BrandService brandService;

    @Operation(summary = "品牌分页列表")
    @GetMapping("/page")
    public PageResult getBrandPage(BrandPageQuery queryParams) {

        // 查询参数
        int pageNum = queryParams.getPageNum();
        int pageSize = queryParams.getPageSize();
        String keywords = queryParams.getKeywords();

        // 分页查询
        Page<PmsBrand> page = brandService.page(new Page<>(pageNum, pageSize),
                new LambdaQueryWrapper<PmsBrand>().like(StrUtil.isNotBlank(keywords), PmsBrand::getName, keywords)
                        .orderByDesc(PmsBrand::getCreateTime));
        return PageResult.success(page);
    }

    @Operation(summary = "品牌列表")
    @GetMapping
    public Result getBrandList() {
        List<PmsBrand> list = brandService.list(new LambdaQueryWrapper<PmsBrand>()
                .select(PmsBrand::getId, PmsBrand::getName));
        return Result.success(list);
    }

    @Operation(summary = "品牌详情")
    @GetMapping("/{id}")
    public Result getBrandList(@PathVariable Integer id) {
        PmsBrand brand = brandService.getById(id);
        return Result.success(brand);
    }

    @Operation(summary = "新增品牌")
    @PostMapping
    public Result addBrand(@RequestBody PmsBrand brand) {
        boolean status = brandService.save(brand);
        return Result.judge(status);
    }

    @Operation(summary = "修改品牌")
    @PutMapping(value = "/{id}")
    public Result updateBrand(
            @PathVariable Long id,
            @RequestBody PmsBrand brand) {
        boolean status = brandService.updateById(brand);
        return Result.judge(status);
    }

    @Operation(summary = "删除品牌")
    @DeleteMapping("/{ids}")
    public Result deleteBrands(@Parameter(name = "品牌ID，多个以英文逗号(,)分割") @PathVariable("ids") String ids) {
        boolean status = brandService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.judge(status);
    }
}

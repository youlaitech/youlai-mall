package com.youlai.mall.pms.controller.admin;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.youlai.common.result.Result;
import com.youlai.common.web.model.Option;
import com.youlai.mall.pms.model.entity.Category;
import com.youlai.mall.pms.model.vo.CategoryVO;
import com.youlai.mall.pms.service.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Admin-商品分类控制器
 *
 * @author haoxr
 * @since 2022/01/01
 */
@Tag(name = "Admin-商品分类")
@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "商品分类列表")
    @GetMapping
    public Result<List<CategoryVO>> getCategoryList() {
        List<CategoryVO> list = categoryService.getCategoryList(null);
        return Result.success(list);
    }

    @Operation(summary = "商品分类级联列表")
    @GetMapping("/options")
    public Result getCategoryOptions() {
        List<Option> list = categoryService.getCategoryOptions();
        return Result.success(list);
    }

    @Operation(summary = "商品分类详情")
    @GetMapping("/{id}")
    public Result detail(
            @Parameter(name = "商品分类ID") @PathVariable Long id
    ) {
        Category category = categoryService.getById(id);
        return Result.success(category);
    }

    @Operation(summary = "新增商品分类")
    @PostMapping
    public Result addCategory(@RequestBody Category category) {
        Long id = categoryService.saveCategory(category);
        return Result.success(id);
    }

    @Operation(summary = "修改商品分类")
    @PutMapping(value = "/{id}")
    public Result update(
            @Parameter(name = "商品分类ID") @PathVariable Long id,
            @RequestBody Category category
    ) {
        category.setId(id);
        id = categoryService.saveCategory(category);
        return Result.success(id);
    }


    @Operation(summary = "选择性修改商品分类")
    @PatchMapping(value = "/{id}")
    @CacheEvict(value = "pms", key = "'categoryList'")
    public Result patch(@PathVariable Long id, @RequestBody Category category) {
        LambdaUpdateWrapper<Category> updateWrapper = new LambdaUpdateWrapper<Category>()
                .eq(Category::getId, id);
        updateWrapper.set(category.getVisible() != null, Category::getVisible, category.getVisible());
        boolean result = categoryService.update(updateWrapper);
        return Result.judge(result);
    }
}

package com.youlai.mall.product.controller.admin;

import com.youlai.common.result.Result;
import com.youlai.common.web.model.Option;
import com.youlai.mall.product.model.form.CategoryForm;
import com.youlai.mall.product.model.vo.CategoryVO;
import com.youlai.mall.product.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Admin-商品分类控制器
 *
 * @author Ray Hao
 * @since 2024/4/20
 */
@Tag(name = "Admin-商品分类")
@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "获取商品分类列表")
    @GetMapping
    public Result<List<CategoryVO>> listCategories() {
        List<CategoryVO> list = categoryService.listCategories();
        return Result.success(list);
    }

    @Operation(summary = "获取商品分类下拉列表")
    @GetMapping("/options")
    public Result<List<Option>> listCategoryOptions() {
        List<Option> list = categoryService.listCategoryOptions();
        return Result.success(list);
    }

    @Operation(summary = "获取商品分类表单数据")
    @GetMapping("/{id}/form")
    public Result getCategoryForm(
            @Parameter(description = "商品分类ID") @PathVariable Long id
    ) {
        CategoryForm categoryForm = categoryService.getCategoryForm(id);
        return Result.success(categoryForm);
    }

    @Operation(summary = "保存商品分类")
    @PostMapping
    public Result saveCategory(
            @Validated @RequestBody CategoryForm formData
    ) {
        Long id = categoryService.saveCategory(formData);
        return Result.success(id);
    }

    @Operation(summary = "删除商品分类")
    @DeleteMapping("/{id}")
    public Result deleteCategory(
            @Parameter(description = "商品分类ID") @PathVariable Long id
    ) {
        categoryService.deleteCategory(id);
        return Result.success();
    }

}

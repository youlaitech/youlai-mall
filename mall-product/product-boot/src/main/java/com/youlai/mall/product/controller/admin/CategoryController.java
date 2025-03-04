package com.youlai.mall.product.controller.admin;

import com.youlai.common.result.Result;
import com.youlai.common.core.model.Option;
import com.youlai.mall.product.model.form.CategoryForm;
import com.youlai.mall.product.model.vo.AttrVO;
import com.youlai.mall.product.model.vo.CategoryVO;
import com.youlai.mall.product.model.vo.SpecVO;
import com.youlai.mall.product.service.AttrService;
import com.youlai.mall.product.service.CategoryService;
import com.youlai.mall.product.service.SpecService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品分类控制层
 *
 * @author Ray.Hao
 * @since 2024/4/20
 */
@Tag(name = "【Admin】商品分类")
@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final AttrService attrService;
    private final SpecService specService;

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

    @Operation(summary = "获取属性列表")
    @GetMapping("/{categoryId}/attrs")
    public Result<List<AttrVO>> getCategoryAttrs(
            @Parameter(description = "分类ID", example = "1") @PathVariable Long categoryId
    ) {
        List<AttrVO> list = attrService.getCategoryAttrs(categoryId);
        return Result.success(list);
    }

    @Operation(summary = "获取规格列表")
    @GetMapping("/{categoryId}/specs")
    public Result<List<SpecVO>> getCategorySpecs(
            @Parameter(description = "分类ID", example = "1") @PathVariable Long categoryId
    ) {
        List<SpecVO> list = specService.getCategorySpecs(categoryId);
        return Result.success(list);
    }

}

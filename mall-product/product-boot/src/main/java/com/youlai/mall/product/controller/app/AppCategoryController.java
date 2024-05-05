package com.youlai.mall.product.controller.app;

import com.youlai.common.result.Result;
import com.youlai.mall.product.model.vo.CategoryVO;
import com.youlai.mall.product.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 商品分类控制器
 *
 * @author haoxr
 * @since 2022/2/5
 */
@Tag(name = "App-商品分类")
@RestController
@RequestMapping("/app-api/v1/categories")
@RequiredArgsConstructor
public class AppCategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "分类列表")
    @GetMapping
    public Result list(@Parameter(name = "上级分类ID") Long parentId) {
        List<CategoryVO> list = categoryService.listCategories(parentId);
        return Result.success(list);
    }
}

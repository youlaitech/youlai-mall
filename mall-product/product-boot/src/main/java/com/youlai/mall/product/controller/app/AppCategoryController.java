package com.youlai.mall.product.controller.app;

import com.youlai.common.result.Result;
import com.youlai.mall.product.model.vo.CategoryAppVO;
import com.youlai.mall.product.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 商品分类控制器
 *
 * @author Ray Hao
 * @since 2022/2/5
 */
@Tag(name = "【App】商品分类")
@RestController
@RequestMapping("/app-api/v1/categories")
@RequiredArgsConstructor
public class AppCategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "分类列表")
    @GetMapping
    public Result<List<CategoryAppVO>> list() {
        List<CategoryAppVO> list = categoryService.listAppCategories();
        return Result.success(list);
    }
}

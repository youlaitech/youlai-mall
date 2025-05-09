package com.youlai.mall.product.controller.client;

import com.youlai.common.result.Result;
import com.youlai.mall.product.model.vo.client.ClientCategoryVO;
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
 * @author Ray.Hao
 * @since 2022/2/5
 */
@Tag(name = "【客户端】商品分类接口")
@RestController
@RequestMapping("/api/client/v1/categories")
@RequiredArgsConstructor
public class ClientCategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "分类列表")
    @GetMapping
    public Result<List<ClientCategoryVO>> list() {
        List<ClientCategoryVO> list = categoryService.listAppCategories();
        return Result.success(list);
    }
}

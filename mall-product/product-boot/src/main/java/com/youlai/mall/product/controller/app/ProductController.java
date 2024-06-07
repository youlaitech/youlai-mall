package com.youlai.mall.product.controller.app;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.youlai.common.result.PageResult;
import com.youlai.common.result.Result;
import com.youlai.mall.product.model.query.ProductPageQuery;
import com.youlai.mall.product.model.vo.ProductDetailVO;
import com.youlai.mall.product.model.vo.ProductPageVO;
import com.youlai.mall.product.service.app.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name  = "【App】商品接口")
@RestController
@RequestMapping("/app-api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "商品分页列表")
    @GetMapping("/page")
    public PageResult<ProductPageVO> listPagedProducts(ProductPageQuery queryParams) {
        IPage<ProductPageVO> result = productService.listPagedProducts(queryParams);
        return PageResult.success(result);
    }

    @Operation(summary = "获取商品详情")
    @GetMapping("/{id}/detail")
    public Result<ProductDetailVO> getProductDetail(
            @Parameter(description ="SPU ID") @PathVariable Long id
    ) {
        ProductDetailVO productDetailVO = productService.getProductDetail(id);
        return Result.success(productDetailVO);
    }

}
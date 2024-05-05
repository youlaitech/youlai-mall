package com.youlai.mall.product.controller.admin;

import com.youlai.common.result.Result;
import com.youlai.mall.product.model.entity.Sku;
import com.youlai.mall.product.service.SkuService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * Admin-商品SKU控制层
 *
 * @author haoxr
 * @since 2022/2/8
 */
@Tag(name = "Admin-商品SKU接口")
@RestController
@RequestMapping("/api/v1/sku")
@RequiredArgsConstructor
public class SkuController {
    private final SkuService skuService;

    @Operation(summary = "商品SKU详情")
    @GetMapping("/{skuId}")
    public Result getSkuDetail(
            @Parameter(name = "SkuId") @PathVariable Long skuId
    ) {
        Sku sku = skuService.getById(skuId);
        return Result.success(sku);
    }

    @Operation(summary = "修改SKU")
    @PutMapping(value = "/{skuId}")
    public Result updateSku(
            @Parameter(name = "SkuId") @PathVariable Long skuId,
            @RequestBody Sku sku
    ) {
        boolean result = skuService.updateById(sku);
        return Result.judge(result);
    }
}

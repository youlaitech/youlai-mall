package com.youlai.mall.product.controller.client;

import com.youlai.common.result.Result;
import com.youlai.mall.product.model.dto.LockSkuDTO;
import com.youlai.mall.product.model.dto.SkuDTO;
import com.youlai.mall.product.service.SkuService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 客户端 SKU 控制器
 *
 * @author Ray.Hao
 * @since 2.0.0
 */
@Tag(name  = "【客户端】SKU 接口")
@RestController
@RequestMapping("/api/client/v1/sku")
@RequiredArgsConstructor
public class ClientSkuController {

    private final SkuService skuService;

    @Operation(summary = "获取商品库存信息")
    @GetMapping("/{skuId}")
    public Result<SkuDTO> getSkuInfo(
            @Parameter(description ="商品ID") @PathVariable Long skuId
    ) {
        SkuDTO skuInfo = skuService.getSkuInfo(skuId);
        return Result.success(skuInfo);
    }

    @Operation(summary = "获取商品列表")
    @GetMapping
    public Result<List<SkuDTO>> listSkusByIds(
            @Parameter(description ="SKU的ID集合") @RequestParam List<Long> skuIds
    ) {
        List<SkuDTO> skuList = skuService.listSkusByIds(skuIds);
        return Result.success(skuList);
    }

    @Operation(summary = "校验并锁定库存")
    @PutMapping("/lock")
    public Result<?> lockStock(
            @RequestParam String orderToken,
            @RequestBody List<LockSkuDTO> lockSkuList
    ) {
        boolean lockStockResult = skuService.lockStock(orderToken,lockSkuList);
        return Result.success(lockStockResult);
    }

    @Operation(summary = "解锁库存")
    @PutMapping("/unlock")
    public Result<?> unlockStock(String orderToken) {
        boolean result = skuService.unlockStock(orderToken);
        return Result.judge(result);
    }

    @Operation(summary = "扣减库存")
    @PutMapping("/deduct")
    public Result<?> deductStock(String orderSn) {
        boolean result = skuService.deductStock(orderSn);
        return Result.judge(result);
    }
}

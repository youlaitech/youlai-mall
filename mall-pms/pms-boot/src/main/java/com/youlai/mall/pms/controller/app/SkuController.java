package com.youlai.mall.pms.controller.app;

import com.youlai.common.result.Result;
import com.youlai.mall.pms.model.dto.CheckPriceDTO;
import com.youlai.mall.pms.model.dto.SkuDTO;
import com.youlai.mall.pms.model.dto.LockStockDTO;
import com.youlai.mall.pms.service.SkuService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * SKU控制层
 *
 * @author haoxr
 * @since 2022/12/21
 */
@Tag(name = "「移动端」SKU接口")
@RestController
@RequestMapping("/app-api/v1/sku")
@RequiredArgsConstructor
public class SkuController {

    private final SkuService skuService;

    @Operation(summary = "获取商品库存信息")
    @GetMapping("/{skuId}/info")
    public Result<SkuDTO> getSkuInfo(
            @Parameter(name = "商品ID") @PathVariable Long skuId
    ) {
        SkuDTO skuInfo = skuService.getSkuInfo(skuId);
        return Result.success(skuInfo);
    }

    @Operation(summary ="获取商品库存数量")
    @GetMapping("/{skuId}/stock_num")
    public Result<Integer> getStockNum(
            @Parameter(name = "商品ID") @PathVariable Long skuId
    ) {
        Integer stockNum = skuService.getStockNum(skuId);
        return Result.success(stockNum);
    }

    @Operation(summary = "锁定库存")
    @PutMapping("/_lock")
    public Result lockStock(@RequestBody LockStockDTO lockStockDTO) {
        boolean lockResult = skuService.lockStock(lockStockDTO);
        return Result.success(lockResult);
    }

    @Operation(summary = "解锁库存")
    @PutMapping("/_unlock")
    public Result<Boolean> unlockStock(String orderToken) {
        boolean result = skuService.unlockStock(orderToken);
        return Result.judge(result);
    }

    @Operation(summary = "扣减库存")
    @PutMapping("/_deduct")
    public Result<Boolean> deductStock(String orderToken) {
        boolean result = skuService.deductStock(orderToken);
        return Result.judge(result);
    }

    @Operation(summary = "商品验价")
    @PostMapping("/price/_check")
    public Result<Boolean> checkPrice(@RequestBody CheckPriceDTO checkPriceDTO) {
        boolean result = skuService.checkPrice(checkPriceDTO);
        return Result.success(result);
    }
}

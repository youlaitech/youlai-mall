package com.youlai.mall.pms.controller.app;

import com.youlai.common.result.Result;
import com.youlai.mall.pms.pojo.dto.SkuInfoDTO;
import com.youlai.mall.pms.pojo.dto.LockedSkuDTO;
import com.youlai.mall.pms.service.SkuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品库存
 *
 * @author haoxr
 * @since 2.0.0
 */
@Api(tags = "「移动端」商品库存接口")
@RestController
@RequestMapping("/app-api/v1/skus")
@RequiredArgsConstructor
public class SkuController {

    private final SkuService skuService;

    @ApiOperation(value = "获取商品库存信息")
    @GetMapping("/{skuId}")
    public Result<SkuInfoDTO> getSkuInfo(
            @ApiParam("商品ID") @PathVariable Long skuId
    ) {
        SkuInfoDTO skuInfo = skuService.getSkuInfo(skuId);
        return Result.success(skuInfo);
    }

    @ApiOperation(value = "获取商品库存列表")
    @GetMapping
    public Result<List<SkuInfoDTO>> getSkuInfoList(
            @ApiParam("SKU ID 列表") @RequestParam List<Long> skuIds
    ) {
        List<SkuInfoDTO> skuInfos = skuService.listSkuInfos(skuIds);
        return Result.success(skuInfos);
    }

    @ApiOperation(value = "校验并锁定库存")
    @PutMapping("/lock")
    public Result<?> lockStock(
            @RequestParam String orderToken,
            @RequestBody List<LockedSkuDTO> lockedSkuList
    ) {
        boolean lockStockResult = skuService.lockStock(orderToken,lockedSkuList);
        return Result.success(lockStockResult);
    }

    @ApiOperation(value = "解锁库存")
    @PutMapping("/unlock")
    public Result<?> unlockStock(String orderToken) {
        boolean result = skuService.unlockStock(orderToken);
        return Result.judge(result);
    }

    @ApiOperation(value = "扣减库存")
    @PutMapping("/deduct")
    public Result<?> deductStock(String orderSn) {
        boolean result = skuService.deductStock(orderSn);
        return Result.judge(result);
    }
}

package com.youlai.mall.pms.controller.admin;

import com.youlai.common.result.Result;
import com.youlai.mall.pms.pojo.entity.PmsSku;
import com.youlai.mall.pms.service.IPmsSkuService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 「管理端」SKU控制器
 *
 * @author haoxr
 * @date 2022/2/8
 */
@Api(tags = "「管理端」SKU接口")
@RestController
@RequestMapping("/api/v1/sku")
@RequiredArgsConstructor
public class PmsSkuController {
    private final IPmsSkuService skuService;

    @ApiOperation(value = "商品SKU详情")
    @GetMapping("/{skuId}")
    public Result getSkuDetail(
            @ApiParam @PathVariable Long skuId
    ) {
        PmsSku sku = skuService.getById(skuId);
        return Result.success(sku);
    }

    @ApiOperation(value = "修改SKU")
    @PutMapping(value = "/{skuId}")
    public Result updateSku(
            @ApiParam @PathVariable Long skuId,
            @RequestBody PmsSku sku
    ) {
        boolean result = skuService.updateById(sku);
        return Result.judge(result);
    }

    @ApiOperation(value = "修改库存数量", notes = "实验室模拟", hidden = true)
    @PutMapping(value = "/{skuId}/stock_num")
    public Result updateStockNum(
            @PathVariable Long skuId,
            @RequestParam Integer stockNum
    ) {
        boolean result = skuService.updateStockNum(skuId, stockNum);
        return Result.judge(result);
    }

    @ApiOperation(value = "扣减库存数量", notes = "实验室模拟", hidden = true)
    @PutMapping(value = "/{skuId}/stock/_deduct")
    public Result deductStock(
            @PathVariable Long skuId,
            @RequestParam Integer num

    ) {
        boolean result = skuService.deductStock(skuId, num);
        return Result.judge(result);
    }

}

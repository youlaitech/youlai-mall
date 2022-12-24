package com.youlai.mall.pms.controller.admin;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.youlai.common.result.Result;
import com.youlai.mall.pms.pojo.entity.PmsSku;
import com.youlai.mall.pms.service.SkuService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 「管理端」商品SKU控制层
 *
 * @author haoxr
 * @date 2022/2/8
 */
@Api(tags = "「管理端」商品SKU接口")
@RestController
@RequestMapping("/api/v1/sku")
@RequiredArgsConstructor
public class PmsSkuController {
    private final SkuService skuService;

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

    @ApiOperation(value = "「实验室」扣减库存数量", hidden = true)
    @PutMapping(value = "/{skuId}/stock/_deduct")
    public Result deductStock(
            @PathVariable Long skuId,
            @RequestParam Integer count

    ) {
        boolean result = skuService.update(new LambdaUpdateWrapper<PmsSku>()
                .setSql("stock_num = stock_num - " + count)
                .eq(PmsSku::getId, skuId)
        );
        return Result.judge(result);
    }

    @ApiOperation(value = "「实验室」重置库存数量", hidden = true)
    @PutMapping(value = "/{skuId}/stock/_reset")
    public Result resetStock(
            @PathVariable Long skuId
    ) {
        boolean result = skuService.update(new LambdaUpdateWrapper<PmsSku>()
                .eq(PmsSku::getId,skuId)
                .set(PmsSku::getStockNum,999)
        );
        return Result.judge(result);
    }

}

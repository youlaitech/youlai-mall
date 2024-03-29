package com.youlai.mall.pms.controller.admin;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.youlai.common.result.Result;
import com.youlai.mall.pms.model.entity.PmsSku;
import com.youlai.mall.pms.service.SkuService;
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
public class PmsSkuController {
    private final SkuService skuService;

    @Operation(summary = "商品SKU详情")
    @GetMapping("/{skuId}")
    public Result getSkuDetail(
            @Parameter(name = "SkuId") @PathVariable Long skuId
    ) {
        PmsSku sku = skuService.getById(skuId);
        return Result.success(sku);
    }

    @Operation(summary = "修改SKU")
    @PutMapping(value = "/{skuId}")
    public Result updateSku(
            @Parameter(name = "SkuId") @PathVariable Long skuId,
            @RequestBody PmsSku sku
    ) {
        boolean result = skuService.updateById(sku);
        return Result.judge(result);
    }
}

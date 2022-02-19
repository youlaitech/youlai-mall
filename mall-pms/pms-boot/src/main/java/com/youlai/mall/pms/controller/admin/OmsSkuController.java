package com.youlai.mall.pms.controller.admin;

import com.youlai.common.result.Result;
import com.youlai.mall.pms.pojo.entity.PmsSku;
import com.youlai.mall.pms.service.IPmsSkuService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author <a href="mailto:xianrui0365@163.com">haoxr</a>
 * @date 2022/2/8
 */
@Api(tags = "「系统端」库存信息")
@RestController
@RequestMapping("/api/v1/sku")
@RequiredArgsConstructor
public class OmsSkuController {

    private final IPmsSkuService skuService;

    @ApiOperation(value = "商品库存详情")
    @GetMapping("/{skuId}")
    public Result getSkuDetail(
            @ApiParam("SKU ID") @PathVariable Long skuId
    ) {
        PmsSku sku = skuService.getById(skuId);
        return Result.success(sku);
    }

    @ApiOperation(value = "修改库存")
    @PutMapping(value = "/{skuId}")
    public Result update(
            @ApiParam("商品库存单元ID") @PathVariable Long skuId,
            @RequestBody PmsSku sku
    ) {
        boolean status = skuService.updateById(sku);
        return Result.judge(status);
    }

}

package com.youlai.mall.pms.controller.app;

import com.youlai.common.result.Result;
import com.youlai.mall.pms.pojo.dto.CheckPriceDTO;
import com.youlai.mall.pms.pojo.dto.SkuDTO;
import com.youlai.mall.pms.pojo.dto.LockStockDTO;
import com.youlai.mall.pms.service.SkuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * SKU控制层
 *
 * @author haoxr
 * @date 2022/12/21
 */
@Api(tags = "「移动端」SKU接口")
@RestController
@RequestMapping("/app-api/v1/sku")
@RequiredArgsConstructor
public class SkuController {

    private final SkuService skuService;

    @ApiOperation(value = "获取商品库存信息")
    @GetMapping("/{skuId}/info")
    public Result<SkuDTO> getSkuInfo(
            @ApiParam("商品ID") @PathVariable Long skuId
    ) {
        SkuDTO skuInfo = skuService.getSkuInfo(skuId);
        return Result.success(skuInfo);
    }

    @ApiOperation("获取商品库存数量")
    @GetMapping("/{skuId}/stock_num")
    public Result<Integer> getStockNum(
            @ApiParam("商品ID") @PathVariable Long skuId
    ) {
        Integer stockNum = skuService.getStockNum(skuId);
        return Result.success(stockNum);
    }

    @ApiOperation(value = "锁定库存")
    @PutMapping("/_lock")
    public Result lockStock(@RequestBody LockStockDTO lockStockDTO) {
        boolean lockResult = skuService.lockStock(lockStockDTO);
        return Result.success(lockResult);
    }

    @ApiOperation(value = "解锁库存")
    @PutMapping("/_unlock")
    public Result<Boolean> unlockStock(String orderToken) {
        boolean result = skuService.unlockStock(orderToken);
        return Result.judge(result);
    }

    @ApiOperation(value = "扣减库存")
    @PutMapping("/_deduct")
    public Result<Boolean> deductStock(String orderToken) {
        boolean result = skuService.deductStock(orderToken);
        return Result.judge(result);
    }

    @ApiOperation(value = "商品验价")
    @PostMapping("/price/_check")
    public Result<Boolean> checkPrice(@RequestBody CheckPriceDTO checkPriceDTO) {
        boolean result = skuService.checkPrice(checkPriceDTO);
        return Result.success(result);
    }
}

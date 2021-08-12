package com.youlai.mall.pms.controller.app;

import com.youlai.common.result.Result;
import com.youlai.mall.pms.pojo.dto.app.SkuDTO;
import com.youlai.mall.pms.pojo.dto.app.LockStockDTO;
import com.youlai.mall.pms.service.IPmsSkuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "移动端-商品库存")
@RestController(value = "appStockController")
@RequestMapping("/app-api/v1/stocks")
@AllArgsConstructor
public class StockController {

    private IPmsSkuService iPmsSkuService;

    @ApiOperation(value = "商品库存单元详情")
    @GetMapping("/{skuId}")
    public Result detail(@PathVariable Long skuId) {
        SkuDTO sku = iPmsSkuService.getSkuById(skuId);
        return Result.success(sku);
    }

    @ApiOperation("获取商品的库存数量")
    @GetMapping("/{skuId}/stock")
    public Result<Integer> getStockById(@PathVariable Long skuId) {
        Integer stock = iPmsSkuService.getStockById(skuId);
        return Result.success(stock);
    }


    @ApiOperation(value = "锁定库存")
    @PutMapping("/_lock")
    public Result<Boolean> lockStock(@RequestBody List<LockStockDTO> list) {
        boolean result = iPmsSkuService.lockStock(list);
        return Result.judge(result);
    }


    @ApiOperation(value = "解锁库存")
    @PutMapping("/_unlock")
    public Result<Boolean> unlockStock(String orderToken) {
        boolean result = iPmsSkuService.unlockStock(orderToken);
        return Result.judge(result);
    }

    @ApiOperation(value = "扣减库存")
    @PutMapping("/_deduct")
    public Result<Boolean> deductStock(String orderToken) {
        boolean result = iPmsSkuService.deductStock(orderToken);
        return Result.judge(result);
    }
}

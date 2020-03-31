package com.fly4j.shop.goods.controller;

import com.fly4j.common.core.domain.Result;
import com.fly4j.shop.goods.pojo.entity.GoodsSkuStock;
import com.fly4j.shop.goods.service.IGoodsSkuStockService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/sku")
public class GoodsSkuStockController {
    @Resource
    private IGoodsSkuStockService iGoodsSkuStockService;

    /**
     * 根据商品编号及编号模糊搜索sku库存
     * @param goodsId
     * @param keyword
     * @return
     */
    @GetMapping("/{goodsId}")
    public Result<List<GoodsSkuStock>> getList(@PathVariable Long goodsId, @RequestParam(value = "keyword",required = false) String keyword) {
        List<GoodsSkuStock> list = iGoodsSkuStockService.getList(goodsId,keyword);
        return Result.success(list);
    }

    /**
     * 批量更新库存信息
     * @param goodsId
     * @param skuStockList
     * @return
     */
    @PutMapping("/{goodsId}")
    public Result putObj(@PathVariable Long goodsId,@RequestBody List<GoodsSkuStock> skuStockList) {
        boolean status = iGoodsSkuStockService.update(goodsId,skuStockList);
        return Result.status(status);
    }

}
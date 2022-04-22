package com.youlai.mall.pms.api;

import com.youlai.common.result.Result;
import com.youlai.mall.pms.pojo.dto.CheckPriceDTO;
import com.youlai.mall.pms.pojo.dto.SkuInfoDTO;
import com.youlai.mall.pms.pojo.dto.app.LockStockDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


@FeignClient(value = "mall-pms", contextId = "sku")
public interface SkuFeignClient {

    /**
     * 获取商品库存单元信息
     */
    @GetMapping("/app-api/v1/sku/{skuId}/info")
    Result<SkuInfoDTO> getSkuInfo(@PathVariable Long skuId);

    /**
     * 锁定商品库存
     */
    @PutMapping("/app-api/v1/sku/_lock")
    Result lockStock(@RequestBody LockStockDTO lockStockDTO);

    /**
     * 解锁商品库存
     */
    @PutMapping("/app-api/v1/sku/_unlock")
    Result unlockStock(@RequestParam String orderToken);

    /**
     * 扣减订单商品库存
     */
    @PutMapping("/app-api/v1/sku/_deduct")
    Result deductStock(@RequestParam String orderToken);

    /**
     * 订单商品验价
     *
     * @param checkPriceDTO
     */
    @PostMapping("/app-api/v1/sku/price/_check")
    Result<Boolean> checkPrice(@RequestBody CheckPriceDTO checkPriceDTO);

    /**
     * 「实验室」修改商品库存
     *
     * @param skuId
     * @param stockNum
     * @return
     */
    @PutMapping("/api/v1/sku/{skuId}/stock_num")
    Result updateStock(@PathVariable Long skuId, @RequestParam Integer stockNum);


    /**
     * 「实验室」扣减商品库存
     *
     * @param skuId
     * @param num   扣减数量
     * @return
     */
    @PutMapping("/api/v1/sku/{skuId}/stock/_deduct")
    Result deductStock(@PathVariable Long skuId, @RequestParam Integer num);

}

package com.youlai.mall.pms.api;

import com.youlai.common.result.Result;
import com.youlai.mall.pms.model.dto.CheckPriceDTO;
import com.youlai.mall.pms.model.dto.SkuDTO;
import com.youlai.mall.pms.model.dto.LockStockDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


@FeignClient(value = "mall-pms", contextId = "sku")
public interface SkuFeignClient {

    /**
     * 获取商品库存单元信息
     */
    @GetMapping("/app-api/v1/sku/{skuId}/info")
    Result<SkuDTO> getSkuInfo(@PathVariable Long skuId);

    /**
     * 锁定商品库存
     */
    @PutMapping("/app-api/v1/sku/_lock")
    Result lockStock(@RequestBody LockStockDTO lockStockDTO);

    /**
     * 解锁商品库存
     */
    @PutMapping("/app-api/v1/sku/_unlock")
    Result unlockStock(@RequestParam String orderSn);

    /**
     * 扣减订单商品库存
     */
    @PutMapping("/app-api/v1/sku/_deduct")
    Result deductStock(@RequestParam String orderSn);

    /**
     * 订单商品验价
     *
     * @param checkPriceDTO
     */
    @PostMapping("/app-api/v1/sku/price/_check")
    Result<Boolean> checkPrice(@RequestBody CheckPriceDTO checkPriceDTO);



}

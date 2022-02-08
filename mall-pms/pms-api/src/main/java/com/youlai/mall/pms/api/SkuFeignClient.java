package com.youlai.mall.pms.api;

import com.youlai.common.result.Result;
import com.youlai.mall.pms.pojo.dto.CheckPriceDTO;
import com.youlai.mall.pms.pojo.dto.SkuInfoDTO;
import com.youlai.mall.pms.pojo.dto.app.LockStockDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "mall-pms", contextId = "sku")
public interface SkuFeignClient {

    /**
     * 获取商品库存单元信息
     */
    @GetMapping("/app-api/v1/sku/{skuId}/info")
    Result<SkuInfoDTO> getSkuInfo(@PathVariable Long skuId);

    /**
     * 锁定库存
     */
    @PutMapping("/app-api/v1/sku/_lock")
    Result lockStock(@RequestBody List<LockStockDTO> list);

    /**
     * 解锁库存
     */
    @PutMapping("/app-api/v1/sku/_unlock")
    Result<Boolean> unlockStock(@RequestParam String orderToken);


    /**
     * 扣减库存
     */
    @PutMapping("/app-api/v1/sku/_deduct")
    Result deductStock(@RequestParam String orderToken);


    /**
     * 商品验价
     *
     * @param checkPriceDTO
     * @return
     */
    @PutMapping("/app-api/v1/sku/price/_check")
    Result<Boolean> checkPrice(@RequestBody CheckPriceDTO checkPriceDTO);
}

package com.youlai.mall.pms.api;

import com.youlai.common.result.Result;
import com.youlai.mall.pms.pojo.dto.SkuDTO;
import com.youlai.mall.pms.pojo.dto.SkuLockDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "mall-pms")
public interface GoodsFeignClient {

    /**
     * 获取商品信息
     */
    @GetMapping("/app-api/v1/goods/{id}")
    Result<SkuDTO> getSkuById(@PathVariable Long id);

    /**
     * 锁定库存
     */
    @PutMapping("/app-api/v1/goods/stocks/_lock")
    Result lockStock(@RequestBody List<SkuLockDTO> list);

    /**
     * 解锁库存
     */
    @PutMapping("/app-api/v1/goods/stocks/_unlock")
    Result<Boolean> unlockStock(@RequestParam String orderToken);


    /**
     * 扣减库存
     */
    @PutMapping("/app-api/v1/goods/stocks/_deduct")
    Result deductStock(@RequestParam  String orderToken);


}

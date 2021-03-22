package com.youlai.mall.pms.api.app;

import com.youlai.common.result.Result;
import com.youlai.mall.pms.pojo.domain.PmsSku;
import com.youlai.mall.pms.pojo.dto.SkuDTO;
import com.youlai.mall.pms.pojo.dto.SkuLockDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "mall-pms")
public interface PmsSkuFeignService {

    /**
     * 获取库存信息
     */
    @GetMapping("/api.app/v1/skus/{id}")
    Result<SkuDTO> getSkuById(@PathVariable Long id);

    /**
     * 锁定库存
     */
    @PutMapping("/api.app/v1/skus/lock_stock")
    Result lockStock(@RequestBody List<SkuLockDTO> list);

    /**
     * 解锁库存
     */
    @PutMapping("/api.app/v1/skus/unlock_stock")
    Result<Boolean> unlockStock(@RequestParam String orderToken);


    @PutMapping("/api.app/v1/skus/deduct_stock")
    Result deductStock(@RequestParam  String orderToken);


}

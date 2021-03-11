package com.youlai.mall.pms.api;

import com.youlai.common.result.Result;
import com.youlai.mall.pms.pojo.dto.SkuDTO;
import com.youlai.mall.pms.pojo.dto.InventoryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("mall-pms")
public interface SkuFeignService {

    /**
     * 获取库存列表
     */
    @GetMapping("/api.app/v1/skus/{ids}")
    Result<List<SkuDTO>> listBySkuIds(@PathVariable String ids);

    /**
     * 获取库存信息
     */
    @GetMapping("/api.app/v1/skus/{id}")
    Result<SkuDTO> getInventoryById(@PathVariable Long id);

    /**
     * 锁定库存
     */
    @PostMapping("/api.app/v1/skus/batch/_lock")
    Result lockStock(@RequestBody List<InventoryDTO> list);

    /**
     * 解锁库存
     */
    @PostMapping("/api.app/v1/skus/batch/_unlock")
    Result<Boolean> unlockInventory(@RequestBody List<InventoryDTO> list);

}

package com.youlai.mall.pms.api;

import com.youlai.common.result.Result;
import com.youlai.mall.pms.pojo.dto.InventoryDTO;
import com.youlai.mall.pms.pojo.dto.InventoryNumDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("mall-pms")
public interface InventoryFeignService {

    /**
     * 获取库存列表
     */
    @GetMapping("/api.app/v1 /inventories/{ids}")
    Result<List<InventoryDTO>> listByInventoryIds(@PathVariable String ids);

    /**
     * 获取商品信息
     */
    @GetMapping("/api.app/v1 /inventories/{id}")
    Result<InventoryDTO> getInventoryById(@PathVariable Long id);

    /**
     * 锁定库存
     */
    @PostMapping("/api.app/v1 /inventories/batch/_lock")
    Result lockStock(@RequestBody List<InventoryNumDTO> list);

    /**
     * 解锁库存
     */
    @PostMapping("/api.app/v1 /inventories/batch/_unlock")
    Result<Boolean> unlockInventory(@RequestBody List<InventoryNumDTO> list);

}

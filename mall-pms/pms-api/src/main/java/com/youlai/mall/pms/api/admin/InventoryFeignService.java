package com.youlai.mall.pms.api.admin;

import com.youlai.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "mall-pms",contextId = "AdminInventoryFeignService")
public interface InventoryFeignService {

    /**
     * 扣减库存
     */
    @PostMapping("/api.app/v1/skus/{id}/stock/_deduct")
    Result deductInventory(@PathVariable Long id, @RequestParam Integer num);


}

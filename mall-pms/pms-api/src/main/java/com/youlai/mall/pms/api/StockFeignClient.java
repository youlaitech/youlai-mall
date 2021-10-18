package com.youlai.mall.pms.api;

import com.youlai.common.result.Result;
import com.youlai.mall.pms.pojo.dto.app.LockStockDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@FeignClient(value = "mall-pms",contextId = "stock")
public interface StockFeignClient {

    /**
     * 锁定库存
     */
    @PutMapping("/app-api/v1/stocks/_lock")
    Result lockStock(@RequestBody List<LockStockDTO> list);

    /**
     * 解锁库存
     */
    @PutMapping("/app-api/v1/stocks/_unlock")
    Result<Boolean> unlockStock(@RequestParam String orderToken);


    /**
     * 扣减库存
     */
    @PutMapping("/app-api/v1/stocks/_deduct")
    Result deductStock(@RequestParam  String orderToken);


}

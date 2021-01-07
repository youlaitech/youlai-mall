package com.youlai.mall.pms.api;

import com.youlai.common.core.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("mall-pms")
public interface SkuFeignService {

    /**
     * 修改商品库存
     */
    @PutMapping("/api.app/v1/skus/{id}/stock")
    Result updateStock(@PathVariable Long id, @RequestParam Integer num);

}



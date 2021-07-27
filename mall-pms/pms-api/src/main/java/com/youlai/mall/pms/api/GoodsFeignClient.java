package com.youlai.mall.pms.api;

import com.youlai.common.result.Result;
import com.youlai.mall.pms.pojo.dto.app.SkuDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "mall-pms")
public interface GoodsFeignClient {

    /**
     * 获取商品信息
     */
    @GetMapping("/app-api/v1/goods/{id}")
    Result<SkuDTO> getSkuById(@PathVariable Long id);
}

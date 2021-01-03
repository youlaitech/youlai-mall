package com.youlai.mall.oms.api;

import com.youlai.common.core.result.Result;
import com.youlai.mall.oms.dto.SkuInfoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("mall-pms")
public interface ProductFeignService {

    @GetMapping("/admin-api/v1/sku/{id}")
    Result<SkuInfoDto> getSkuInfo(@PathVariable Long id);
}

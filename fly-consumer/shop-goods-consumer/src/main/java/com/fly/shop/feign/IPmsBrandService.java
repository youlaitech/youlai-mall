package com.fly.shop.feign;

import com.fly.common.constant.ServiceNameConstants;
import com.fly.shop.domain.PmsBrand;
import com.fly.shop.feign.fallback.PmsBrandServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = ServiceNameConstants.SHOP_GOODS_PROVIDER, contextId = "PMSBRAND", fallbackFactory = PmsBrandServiceFallback.class)
public interface IPmsBrandService {
    @GetMapping("/shop/brand/list")
    List<PmsBrand> getAll();
}

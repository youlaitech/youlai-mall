package com.fly.shop.feign;

import com.fly.common.constant.ServiceNameConstants;
import com.fly.shop.domain.GoodsBrand;
import com.fly.shop.feign.fallback.GoodsBrandServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = ServiceNameConstants.SHOP_GOODS_PROVIDER, contextId = "GOODSBRAND", fallbackFactory = GoodsBrandServiceFallback.class)
public interface IGoodsBrandService {
    @GetMapping("/shop/brand/list")
    List<GoodsBrand> getAll();
}

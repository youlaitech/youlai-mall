package com.fly4j.yshop.pms.feign;

import com.baomidou.mybatisplus.extension.api.R;
import com.fly4j.yshop.pms.feign.factory.PmsAppFeignFallbackFactory;
import com.fly4j.yshop.pms.pojo.entity.PmsSku;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "yshop-pms", fallbackFactory = PmsAppFeignFallbackFactory.class)
public interface PmsAppFeign {

    @GetMapping(value = "/api.app/v1/skus/{id}")
    R<PmsSku> getSkuById(@PathVariable Long id);
}

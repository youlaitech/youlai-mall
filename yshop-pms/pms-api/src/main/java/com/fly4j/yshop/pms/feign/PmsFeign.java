package com.fly4j.yshop.pms.feign;

import com.baomidou.mybatisplus.extension.api.R;
import com.fly4j.yshop.pms.feign.factory.PmsFeignFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "yshop-pms", fallbackFactory = PmsFeignFallbackFactory.class)
public interface PmsFeign {

    @GetMapping(value = "/spus/{id}")
    R getSpuById(@PathVariable Long id);

}

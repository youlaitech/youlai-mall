package com.fly4j.yshop.pms.feign;

import com.fly4j.yshop.pms.feign.factory.PmsFeignClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "yshop-pms", fallbackFactory = PmsFeignClientFallbackFactory.class)
public interface PmsFeignClient {


}

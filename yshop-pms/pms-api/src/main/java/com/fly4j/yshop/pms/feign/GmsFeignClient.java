package com.fly4j.yshop.pms.feign;

import com.fly4j.yshop.pms.feign.factory.GmsFeignClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "yshop-pms", fallbackFactory = GmsFeignClientFallbackFactory.class)
public interface GmsFeignClient {


}

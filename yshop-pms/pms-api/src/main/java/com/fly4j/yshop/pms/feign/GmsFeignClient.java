package com.fly4j.yshop.gms.feign;

import com.fly4j.yshop.gms.feign.factory.GmsFeignClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "yshop-gms", fallbackFactory = GmsFeignClientFallbackFactory.class)
public interface GmsFeignClient {


}

package com.fly4j.yshop.ums.feign.admin.factory;

import com.fly4j.yshop.ums.feign.admin.UmsFeignClient;
import feign.hystrix.FallbackFactory;

public class UmsFeignClientFallbackFactory implements FallbackFactory<UmsFeignClient> {

    @Override
    public UmsFeignClient create(Throwable throwable) {
        return new UmsFeignClient() {
        };
    }
}

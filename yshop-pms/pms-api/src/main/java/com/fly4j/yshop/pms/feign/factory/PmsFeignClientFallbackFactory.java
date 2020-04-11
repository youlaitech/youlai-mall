package com.fly4j.yshop.pms.feign.factory;

import com.fly4j.yshop.pms.feign.PmsFeignClient;
import feign.hystrix.FallbackFactory;

public class PmsFeignClientFallbackFactory implements FallbackFactory<PmsFeignClient> {


    @Override
    public PmsFeignClient create(Throwable throwable) {
        return new PmsFeignClient() {
        };
    }
}

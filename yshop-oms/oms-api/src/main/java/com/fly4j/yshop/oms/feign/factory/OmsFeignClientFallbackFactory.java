package com.fly4j.yshop.oms.feign.factory;

import com.fly4j.yshop.oms.feign.OmsFeignClient;
import feign.hystrix.FallbackFactory;

public class OmsFeignClientFallbackFactory implements FallbackFactory<OmsFeignClient> {


    @Override
    public OmsFeignClient create(Throwable throwable) {
        return new OmsFeignClient() {
        };
    }
}

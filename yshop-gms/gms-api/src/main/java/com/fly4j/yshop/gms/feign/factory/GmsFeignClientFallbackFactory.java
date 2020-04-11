package com.fly4j.yshop.gms.feign.factory;

import com.fly4j.yshop.gms.feign.GmsFeignClient;
import feign.hystrix.FallbackFactory;

public class GmsFeignClientFallback  implements FallbackFactory<GmsFeignClient> {


    @Override
    public GmsFeignClient create(Throwable throwable) {
        return null;
    }
}

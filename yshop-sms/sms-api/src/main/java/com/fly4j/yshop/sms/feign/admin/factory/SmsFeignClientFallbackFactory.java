package com.fly4j.yshop.sms.feign.admin.factory;

import com.fly4j.yshop.sms.feign.SmsFeignClient;
import feign.hystrix.FallbackFactory;

public class SmsFeignClientFallbackFactory implements FallbackFactory<SmsFeignClient> {


    @Override
    public SmsFeignClient create(Throwable throwable) {
        return new SmsFeignClient() {
        };
    }
}

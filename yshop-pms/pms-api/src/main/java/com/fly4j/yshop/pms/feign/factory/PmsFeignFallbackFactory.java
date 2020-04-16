package com.fly4j.yshop.pms.feign.factory;

import com.baomidou.mybatisplus.extension.api.R;
import com.fly4j.yshop.pms.feign.PmsFeign;
import feign.hystrix.FallbackFactory;

public class PmsFeignFallbackFactory implements FallbackFactory<PmsFeign> {


    @Override
    public PmsFeign create(Throwable throwable) {
        return new PmsFeign() {
            @Override
            public R getSpuById(Long id) {
                return null;
            }
        };
    }
}

package com.fly4j.yshop.pms.feign.factory;

import com.baomidou.mybatisplus.extension.api.R;
import com.fly4j.yshop.pms.feign.PmsAppFeign;
import feign.hystrix.FallbackFactory;

public class PmsAppFeignFallbackFactory implements FallbackFactory<PmsAppFeign> {


    @Override
    public PmsAppFeign create(Throwable throwable) {
        return new PmsAppFeign() {
            @Override
            public R getSpuById(Long id) {
                return null;
            }

            @Override
            public R getSkuById(Long id) {
                return null;
            }
        };
    }
}

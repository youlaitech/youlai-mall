package com.fly4j.yshop.pms.feign.app.factory;

import com.baomidou.mybatisplus.extension.api.R;
import com.fly4j.yshop.pms.feign.PmsAppFeign;
import com.fly4j.yshop.pms.pojo.vo.SkuLockVO;
import feign.hystrix.FallbackFactory;

import java.util.List;

public class PmsAppFeignFallbackFactory implements FallbackFactory<PmsAppFeign> {


    @Override
    public PmsAppFeign create(Throwable throwable) {
        return new PmsAppFeign() {
            @Override
            public R getSkuById(Long id) {
                return null;
            }

            @Override
            public R<SkuLockVO> checkAndLockStock(List<SkuLockVO> skuLockVOS) {
                return null;
            }

            @Override
            public Integer minusStock(Long sku_id, Integer sku_quantity) {
                return 0;
            }
        };
    }
}

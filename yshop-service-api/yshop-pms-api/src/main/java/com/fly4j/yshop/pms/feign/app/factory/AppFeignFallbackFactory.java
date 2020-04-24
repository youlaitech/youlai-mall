package com.fly4j.yshop.pms.feign.app.factory;

import com.baomidou.mybatisplus.extension.api.R;
import com.fly4j.yshop.pms.feign.app.AppFeign;
import com.fly4j.yshop.pms.pojo.vo.SkuLockVO;
import feign.hystrix.FallbackFactory;

import java.util.List;

public class AppFeignFallbackFactory implements FallbackFactory<AppFeign> {


    @Override
    public AppFeign create(Throwable throwable) {
        return new AppFeign() {
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

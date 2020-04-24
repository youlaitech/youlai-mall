package com.fly4j.yshop.pms.feign.app.factory;

import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fly4j.yshop.pms.feign.PmsFeign;
import com.fly4j.yshop.pms.pojo.dto.admin.PmsSkuDTO;
import feign.hystrix.FallbackFactory;

public class PmsFeignFallbackFactory implements FallbackFactory<PmsFeign> {


    @Override
    public PmsFeign create(Throwable throwable) {
        return new PmsFeign() {
            @Override
            public R getSpuById(Long id) {
                return null;
            }

            @Override
            public R<Page<PmsSkuDTO>> skuPage(Integer page, Integer limit, String spu_name) {
                return null;
            }
        };
    }
}

package com.fly4j.shop.marketing.remote.fallback;

import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fly4j.common.core.domain.Result;
import com.fly4j.shop.marketing.pojo.dto.SeckillGoodsDTO;
import com.fly4j.shop.marketing.remote.IRemoteGoodsService;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class IRemoteGoodsServiceFallback implements FallbackFactory<IRemoteGoodsService> {

    @Override
    public IRemoteGoodsService create(Throwable throwable) {
        return new IRemoteGoodsService() {
            @Override
            public R<Page<SeckillGoodsDTO>> page(Integer pageNum, Integer pageSize, SeckillGoodsDTO seckillGoodsDTO) {
                return null;
            }

            @Override
            public R<SeckillGoodsDTO> getByGoodsId(Long id) {
                return null;
            }
        };
    }
}

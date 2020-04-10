package com.fly4j.shop.order.remote.fallback;

import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fly4j.common.core.bean.Result;
import com.fly4j.shop.order.pojo.dto.GoodsDTO;
import com.fly4j.shop.order.remote.IRemoteGoodsService;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class IRemoteGoodsServiceFallback implements FallbackFactory<IRemoteGoodsService> {

    @Override
    public IRemoteGoodsService create(Throwable throwable) {
        return new IRemoteGoodsService() {
            @Override
            public Result<Page<GoodsDTO>> page(Integer pageNum, Integer pageSize, GoodsDTO GoodsDTO) {
                return null;
            }

            @Override
            public Result<GoodsDTO> getById(Long id) {
                return null;
            }

            @Override
            public Result add(GoodsDTO GoodsDTO) {
                return null;
            }

            @Override
            public Result update(Long id, GoodsDTO GoodsDTO) {
                return null;
            }

            @Override
            public Result delete(Long[] ids) {
                return null;
            }

            @Override
            public R<GoodsDTO> getOrderGoods(Long id) {
                return null;
            }
        };
    }
}

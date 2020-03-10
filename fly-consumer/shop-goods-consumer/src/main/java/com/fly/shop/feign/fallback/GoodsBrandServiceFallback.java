package com.fly.shop.feign.fallback;

import com.fly.shop.domain.GoodsBrand;
import com.fly.shop.feign.IGoodsBrandService;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description: TODO Feign接口，所有访问都会走feign接口
 * @author: Mr.
 * @create: 2020-03-05 19:41
 **/
@Component
public class GoodsBrandServiceFallback implements FallbackFactory<IGoodsBrandService> {
    @Override
    public IGoodsBrandService create(Throwable throwable) {
        return new IGoodsBrandService(){

            @Override
            public List<GoodsBrand> getAll() {
                return null;
            }
        };
    }
}

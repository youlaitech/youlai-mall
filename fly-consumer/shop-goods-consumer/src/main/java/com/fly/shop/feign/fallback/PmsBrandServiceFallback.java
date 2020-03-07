package com.fly.shop.feign.fallback;

import com.fly.shop.domain.PmsBrand;
import com.fly.shop.feign.IPmsBrandService;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description: TODO Feign接口，所有访问都会走feign接口
 * @author: Mr.
 * @create: 2020-03-05 19:41
 **/
@Component
public class PmsBrandServiceFallback implements FallbackFactory<IPmsBrandService> {
    @Override
    public IPmsBrandService create(Throwable throwable) {
        return new IPmsBrandService(){

            @Override
            public List<PmsBrand> getAll() {
                return null;
            }
        };
    }
}

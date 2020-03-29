package com.fly4j.shop.marketing.remote;

import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fly4j.common.constant.ServiceNameConstants;
import com.fly4j.shop.marketing.pojo.dto.SeckillGoodsDTO;
import com.fly4j.shop.marketing.remote.fallback.IRemoteGoodsServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = ServiceNameConstants.SHOP_GOODS, fallbackFactory = IRemoteGoodsServiceFallback.class)
public interface IRemoteGoodsService {

    @GetMapping("/seckill/pageNum/{pageNum}/pageSize/{pageSize}")
    R<Page<SeckillGoodsDTO>> page(@PathVariable Integer pageNum, @PathVariable Integer pageSize,@SpringQueryMap SeckillGoodsDTO seckillGoodsDTO);


    @GetMapping("/seckill/{id}")
    R<SeckillGoodsDTO> getByGoodsId(@PathVariable Long id);


}

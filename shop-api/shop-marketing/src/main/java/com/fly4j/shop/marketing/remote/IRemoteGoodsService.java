package com.fly4j.shop.marketing.remote;

import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fly4j.common.constant.ServiceNameConstants;
import com.fly4j.shop.marketing.pojo.dto.SpikeGoodsDTO;
import com.fly4j.shop.marketing.remote.fallback.IRemoteGoodsServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = ServiceNameConstants.SHOP_GOODS, fallbackFactory = IRemoteGoodsServiceFallback.class)
public interface IRemoteGoodsService {

    @GetMapping("/api/v1/spikeGoods/pageNum/{pageNum}/pageSize/{pageSize}")
    R<Page<SpikeGoodsDTO>> page(@PathVariable Integer pageNum, @PathVariable Integer pageSize, @SpringQueryMap SpikeGoodsDTO spikeGoodsDTO);


    @GetMapping("/api/v1/spikeGoods/{id}")
    R<SpikeGoodsDTO> spikeGoods(@PathVariable Long id);


}

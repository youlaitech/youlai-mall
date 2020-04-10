package com.fly4j.shop.order.remote;

import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fly4j.common.constant.ServiceNameConstants;
import com.fly4j.common.core.bean.Result;
import com.fly4j.shop.order.pojo.dto.GoodsDTO;
import com.fly4j.shop.order.remote.fallback.IRemoteGoodsServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = ServiceNameConstants.SHOP_GOODS, fallbackFactory = IRemoteGoodsServiceFallback.class)
public interface IRemoteGoodsService {

    @GetMapping("/goods/pageNum/{pageNum}/pageSize/{pageSize}")
    Result<Page<GoodsDTO>> page(@PathVariable Integer pageNum, @PathVariable Integer pageSize, @RequestBody GoodsDTO GoodsDTO);

    @GetMapping("/goods/{id}")
    Result<GoodsDTO> getById(@PathVariable("id") Long id);

    @PostMapping("/goods")
    Result add(@RequestBody GoodsDTO GoodsDTO);

    @PutMapping("/goods/{id}")
    Result update(@PathVariable("id") Long id, @RequestBody GoodsDTO GoodsDTO);

    @DeleteMapping("/goods/{ids}")
    Result delete(@PathVariable Long[] ids);


    @GetMapping("/updateInfo/{id}")
    R<GoodsDTO> getOrderGoods(@PathVariable("id") Long id);

}

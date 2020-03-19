package com.fly.shop.services;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fly.shop.pojo.dto.GoodsDTO;
import com.fly.shop.pojo.entity.Goods;

public interface IGoodsService extends IService<Goods> {
    boolean add(GoodsDTO goodsDto);
}

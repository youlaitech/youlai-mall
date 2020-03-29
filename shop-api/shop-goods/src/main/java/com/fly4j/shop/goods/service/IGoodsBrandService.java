package com.fly4j.shop.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fly4j.shop.goods.pojo.entity.GoodsBrand;

import java.util.List;

public interface IGoodsBrandService extends IService<GoodsBrand>{
    List<GoodsBrand> findAll();
}

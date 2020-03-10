package com.fly.shop.services;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fly.shop.pojo.entity.GoodsBrand;

import java.util.List;

public interface IGoodsBrandService extends IService<GoodsBrand>{
    List<GoodsBrand> findAll();
}

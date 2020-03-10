package com.fly.shop.services;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fly.shop.pojo.entity.GoodsAttribute;
import com.fly.shop.pojo.entity.GoodsCategory;

public interface IGoodsAttributeService extends IService<GoodsAttribute> {

    Page<GoodsAttribute> selectPage(Page<GoodsAttribute> page, GoodsAttribute goodsAttribute);
}

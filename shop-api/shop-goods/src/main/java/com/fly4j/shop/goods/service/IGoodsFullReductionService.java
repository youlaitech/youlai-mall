package com.fly4j.shop.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fly4j.shop.goods.pojo.entity.GoodsFullReduction;

import java.util.List;

public interface IGoodsFullReductionService extends IService<GoodsFullReduction> {
    int insertList(List<GoodsFullReduction> goodsFullReductionList);
}

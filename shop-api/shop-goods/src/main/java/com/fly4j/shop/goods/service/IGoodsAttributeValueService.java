package com.fly4j.shop.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fly4j.shop.goods.pojo.entity.GoodsAttributeValue;

import java.util.List;

public interface IGoodsAttributeValueService extends IService<GoodsAttributeValue> {
    int insertList(List<GoodsAttributeValue> goodsLadderList);
}

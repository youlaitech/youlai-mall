package com.fly4j.shop.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fly4j.shop.goods.pojo.entity.GoodsLadder;

import java.util.List;

public interface IGoodsLadderService extends IService<GoodsLadder> {
    int insertList(List<GoodsLadder> goodsLadderList);
}

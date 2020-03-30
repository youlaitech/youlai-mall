package com.fly4j.shop.goods.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fly4j.shop.goods.mapper.GoodsAttributeValueMapper;
import com.fly4j.shop.goods.pojo.entity.GoodsAttributeValue;
import com.fly4j.shop.goods.service.IGoodsAttributeValueService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class GoodsAttributeValueServiceImpl extends ServiceImpl<GoodsAttributeValueMapper, GoodsAttributeValue> implements IGoodsAttributeValueService {
    @Resource
    private GoodsAttributeValueMapper goodsAttributeValueMapper;

    @Override
    public int insertList(List<GoodsAttributeValue> goodsLadderList) {
        return goodsAttributeValueMapper.insertList(goodsLadderList);
    }
}

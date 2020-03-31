package com.fly4j.shop.goods.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fly4j.shop.goods.mapper.GoodsLadderMapper;
import com.fly4j.shop.goods.pojo.entity.GoodsLadder;
import com.fly4j.shop.goods.service.IGoodsLadderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class GoodsLadderServiceImpl extends ServiceImpl<GoodsLadderMapper, GoodsLadder> implements IGoodsLadderService {
    @Resource
    private GoodsLadderMapper goodsLadderMapper;

    @Override
    public int insertList(List<GoodsLadder> goodsLadderList) {
        return goodsLadderMapper.insertList(goodsLadderList);
    }
}

package com.fly4j.shop.goods.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fly4j.shop.goods.mapper.GoodsFullReductionMapper;
import com.fly4j.shop.goods.pojo.entity.GoodsFullReduction;
import com.fly4j.shop.goods.service.IGoodsFullReductionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class GoodsFullRedutionServiceImpl extends ServiceImpl<GoodsFullReductionMapper, GoodsFullReduction> implements IGoodsFullReductionService {
    @Resource
    private GoodsFullReductionMapper goodsFullReductionMapper;
    @Override
    public int insertList(List<GoodsFullReduction> goodsFullReductionList) {
        return goodsFullReductionMapper.insertList(goodsFullReductionList);
    }
}

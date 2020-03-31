package com.fly4j.shop.goods.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fly4j.shop.goods.mapper.GoodsSkuStockMapper;
import com.fly4j.shop.goods.service.IGoodsSkuStockService;
import com.fly4j.shop.goods.pojo.entity.GoodsSkuStock;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description:
 * @author: Mr.
 * @create: 2020-03-24 15:38
 **/
@Service
public class GoodsSkuStockServiceImpl extends ServiceImpl<GoodsSkuStockMapper, GoodsSkuStock> implements IGoodsSkuStockService {
    @Resource
    private GoodsSkuStockMapper goodsSkuStockMapper;

    @Override
    public int insertList(List<GoodsSkuStock> goodsLadderList) {
        return goodsSkuStockMapper.insertList(goodsLadderList);
    }

    @Override
    public List<GoodsSkuStock> getList(Long goodsId, String keyword) {
        return goodsSkuStockMapper.getList(goodsId, keyword);
    }

    @Override
    public boolean update(Long goodsId, List<GoodsSkuStock> skuStockList) {
        return goodsSkuStockMapper.replaceList(goodsId,skuStockList);
    }
}

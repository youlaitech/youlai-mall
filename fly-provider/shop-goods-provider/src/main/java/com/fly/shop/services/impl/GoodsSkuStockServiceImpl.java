package com.fly.shop.services.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fly.shop.dao.GoodsSkuStockMapper;
import com.fly.shop.pojo.entity.GoodsSkuStock;
import com.fly.shop.services.IGoodsSkuStockService;
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
    public List<GoodsSkuStock> getList(Long goodsId, String keyword) {
        return goodsSkuStockMapper.getList(goodsId, keyword);
    }

    @Override
    public boolean update(Long goodsId, List<GoodsSkuStock> skuStockList) {
        return goodsSkuStockMapper.replaceList(goodsId,skuStockList);
    }
}

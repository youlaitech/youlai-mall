package com.fly.shop.services.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fly.shop.dao.*;
import com.fly.shop.pojo.dto.GoodsDTO;
import com.fly.shop.pojo.entity.Goods;
import com.fly.shop.pojo.vo.GoodsSkuStockVO;
import com.fly.shop.services.IGoodsService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @description: 商品添加
 * @author: Mr.
 * @create: 2020-03-13 16:56
 **/
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements IGoodsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsServiceImpl.class);

    @Resource
    private GoodsMapper goodsMapper;
    @Resource
    private GoodsLadderMapper goodsLadderMapper;
    @Resource
    private GoodsFullReductionMapper goodsFullReductionMapper;
    @Resource
    private GoodsSkuStockMapper goodsSkuStockMapper;
    @Resource
    private GoodsAttributeValueMapper goodsAttributeValueMapper;

    @Override
    public boolean add(GoodsDTO goodsDto) {

        Goods goods = goodsDto;

        goods.setId(null);
        goodsMapper.insert(goods);

        //根据促销类型设置价格：、阶梯价格、满减价格
        Long goodsId = goodsDto.getId();

        //阶梯价格
        relateAndInsertList(goodsLadderMapper, goodsDto.getGoodsLadderList(), goodsId);
        //满减价格
        relateAndInsertList(goodsFullReductionMapper, goodsDto.getGoodsFullReductionList(), goodsId);
        //处理sku的编码
        handleSkuStockCode(goodsDto.getSkuStockList(),goodsId);
        //添加sku库存信息
        relateAndInsertList(goodsSkuStockMapper, goodsDto.getSkuStockList(), goodsId);
        //添加商品参数,添加自定义商品规格
        relateAndInsertList(goodsAttributeValueMapper, goodsDto.getGoodsAttributeValueList(), goodsId);

        return true;
    }

    /**
     * 建立和插入关系表操作
     *
     * @param dao       可以操作的dao
     * @param dataList  要插入的数据
     * @param goodsId 建立关系的id
     */
    private void relateAndInsertList(Object dao, List dataList, Long goodsId) {
        try {
            if (CollectionUtils.isEmpty(dataList)) return;
            for (Object item : dataList) {
                Method setId = item.getClass().getMethod("setId", Long.class);
                setId.invoke(item, (Long) null);
                Method setGoodsId = item.getClass().getMethod("setGoodsId", Long.class);
                setGoodsId.invoke(item, goodsId);
            }
            Method insertList = dao.getClass().getMethod("insertList", List.class);
            insertList.invoke(dao, dataList);
        } catch (Exception e) {
            LOGGER.warn("创建产品出错:{}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     *处理sku的编码
     */
    private void handleSkuStockCode(List<GoodsSkuStockVO> skuStockList, Long goodsId) {
        if(CollectionUtils.isEmpty(skuStockList))return;
        for(int i=0;i<skuStockList.size();i++){
            GoodsSkuStockVO skuStock = skuStockList.get(i);
            if(StringUtils.isEmpty(skuStock.getSkuCode())){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                StringBuilder sb = new StringBuilder();
                //日期
                sb.append(sdf.format(new Date()));
                //四位商品id
                sb.append(String.format("%04d", goodsId));
                //三位索引id
                sb.append(String.format("%03d", i+1));
                skuStock.setSkuCode(sb.toString());
            }
        }
    }
}

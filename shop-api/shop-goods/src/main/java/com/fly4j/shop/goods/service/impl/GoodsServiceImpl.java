package com.fly4j.shop.goods.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fly4j.shop.dao.*;
import com.fly4j.shop.goods.pojo.dto.GoodsDTO;
import com.fly4j.shop.goods.pojo.entity.Goods;
import com.fly4j.shop.goods.service.IGoodsService;
import com.fly4j.shop.goods.pojo.entity.GoodsSkuStock;
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
 * @description: 商品管理
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

    @Override
    public boolean updatePublishStatus(List<Long> ids, Integer publishStatus) {
        Goods goods = new Goods();
        goods.setPublishStatus(publishStatus);
        for(Long id:ids){
            goods.setId(id);
            goodsMapper.updatePublishStatus(goods);
        }
        return true;
    }

    @Override
    public boolean updateNewStatus(List<Long> ids, Integer newStatus) {
        Goods goods = new Goods();
        goods.setNewStatus(newStatus);
        for(Long id:ids){
            goods.setId(id);
            goodsMapper.updateNewStatus(goods);
        }
        return true;
    }

    @Override
    public boolean updateRecommendStatus(List<Long> ids, Integer recommendStatus) {
        Goods goods = new Goods();
        goods.setRecommendStatus(recommendStatus);
        for(Long id:ids){
            goods.setId(id);
            goodsMapper.updateRecommendStatus(goods);
        }
        return true;
    }

    @Override
    public boolean updateDeleteStatus(List<Long> ids, Integer deleteStatus) {
        Goods goods = new Goods();
        goods.setRecommendStatus(deleteStatus);
        for(Long id:ids){
            goods.setId(id);
            goodsMapper.updateDeleteStatus(goods);
        }
        return true;
    }

    @Override
    public boolean update(Long id, GoodsDTO goodsDto) {
        return false;
    }

//    @Override
//    public boolean update(Long id,GoodsDTO goodsDto) {
//        //更新商品信息
//        Goods goods = goodsDto;
//        goods.setId(id);
//        goodsMapper.updateById(goods);
//
//        //阶梯价格
//        goodsLadderMapper.deleteByGoodsId(id);
//        relateAndInsertList(goodsLadderMapper, goodsDto.getGoodsLadderList(), id);
//
//        //满减价格
//        goodsFullReductionMapper.deleteByGoodsId(id);
//        relateAndInsertList(goodsFullReductionMapper, goodsDto.getGoodsFullReductionList(), id);
//
//        //修改sku库存信息
//        handleUpdateSkuStockList(id, goodsDto);
//
//        //修改商品参数,添加自定义商品规格
//        goodsAttributeValueMapper.deleteByGoodsId(id);
//        relateAndInsertList(goodsAttributeValueMapper, goodsDto.getGoodsAttributeValueList(), id);
//
//        return true;
//    }
//
//    private void handleUpdateSkuStockList(Long id, GoodsDTO goodsDto) {
//        //当前的sku信息
//        List<GoodsSkuStockVO> currSkuList = goodsDto.getSkuStockList();
//        //当前没有sku直接删除
//        if(CollUtil.isEmpty(currSkuList)){
//            goodsSkuStockMapper.deleteByGoodsId(id);
//            return;
//        }
//        //获取初始sku信息
//        List<GoodsSkuStockVO> oriStuList = goodsSkuStockMapper.selectListByGoodsId();
//
//        //获取新增sku信息
//        List<GoodsSkuStockVO> insertSkuList = currSkuList.stream().filter(item->item.getId()==null).collect(Collectors.toList());
//        //获取需要更新的sku信息
//        List<GoodsSkuStockVO> updateSkuList = currSkuList.stream().filter(item->item.getId()!=null).collect(Collectors.toList());
//        List<Long> updateSkuIds = updateSkuList.stream().map(GoodsSkuStockVO::getId).collect(Collectors.toList());
//        //获取需要删除的sku信息
//        List<GoodsSkuStockVO> removeSkuList = oriStuList.stream().filter(item-> !updateSkuIds.contains(item.getId())).collect(Collectors.toList());
//        handleSkuStockCode(insertSkuList,id);
//        handleSkuStockCode(updateSkuList,id);
//        //新增sku
//        if(CollUtil.isNotEmpty(insertSkuList)){
//            relateAndInsertList(goodsSkuStockMapper, insertSkuList, id);
//        }
//        //删除sku
//        if(CollUtil.isNotEmpty(removeSkuList)){
//            List<Long> removeSkuIds = removeSkuList.stream().map(GoodsSkuStockVO::getId).collect(Collectors.toList());
//            goodsSkuStockMapper.deleteBatchIds(removeSkuIds);
//        }
//        //修改sku
//        if(CollUtil.isNotEmpty(updateSkuList)){
//            for (GoodsSkuStockVO goodsSkuStock : updateSkuList) {
//                goodsSkuStockMapper.updateById();
//            }
//        }
//
//    }

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
    private void handleSkuStockCode(List<GoodsSkuStock> skuStockList, Long goodsId) {
        if(CollectionUtils.isEmpty(skuStockList))return;
        for(int i=0;i<skuStockList.size();i++){
            GoodsSkuStock skuStock = skuStockList.get(i);
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

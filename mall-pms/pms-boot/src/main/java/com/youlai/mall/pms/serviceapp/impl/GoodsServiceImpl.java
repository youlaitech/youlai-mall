package com.youlai.mall.pms.serviceapp.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.common.web.exception.BizException;
import com.youlai.mall.pms.common.enums.AttributeTypeEnum;
import com.youlai.mall.pms.mapper.PmsSpuMapper;
import com.youlai.mall.pms.pojo.entity.PmsSku;
import com.youlai.mall.pms.pojo.entity.PmsSpu;
import com.youlai.mall.pms.pojo.entity.PmsSpuAttributeValue;
import com.youlai.mall.pms.pojo.vo.ProductHistoryVO;
import com.youlai.mall.pms.pojo.vo.app.GoodsDetailVO;
import com.youlai.mall.pms.service.IPmsSkuService;
import com.youlai.mall.pms.service.IPmsSpuAttributeValueService;
import com.youlai.mall.pms.serviceapp.IGoodsService;
import com.youlai.mall.ums.api.MemberFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:xianrui0365@163.com">xianrui</a>
 * @date 2021/8/8
 */
@Service
@RequiredArgsConstructor
public class GoodsServiceImpl extends ServiceImpl<PmsSpuMapper, PmsSpu> implements IGoodsService {

    final IPmsSpuAttributeValueService spuAttributeValueService;
    final IPmsSkuService skuService;
    final MemberFeignClient memberFeignClient;

    @Override
    public GoodsDetailVO getGoodsById(Long goodsId) {

        GoodsDetailVO goodsDetailVO = new GoodsDetailVO();
        PmsSpu pmsSpu = this.baseMapper.selectById(goodsId);
        Assert.isTrue(pmsSpu != null, "商品不存在");
        // 商品基本信息
        GoodsDetailVO.GoodsInfo goodsInfo = new GoodsDetailVO.GoodsInfo();
        BeanUtil.copyProperties(pmsSpu, goodsInfo, "album");

        List<String> album = new ArrayList<>();

        if (StrUtil.isNotBlank(pmsSpu.getPicUrl())) {
            album.add(pmsSpu.getPicUrl());
        }
        if (pmsSpu.getAlbum() != null && pmsSpu.getAlbum().length > 0) {
            album.addAll(Arrays.asList(pmsSpu.getAlbum()));
            goodsInfo.setAlbum(album);
        }
        goodsDetailVO.setGoodsInfo(goodsInfo);

        // 商品属性列表
        List<GoodsDetailVO.Attribute> attributeList = spuAttributeValueService.list(new LambdaQueryWrapper<PmsSpuAttributeValue>()
                .eq(PmsSpuAttributeValue::getType, AttributeTypeEnum.ATTRIBUTE.getValue())
                .eq(PmsSpuAttributeValue::getSpuId, goodsId)
                .select(PmsSpuAttributeValue::getId, PmsSpuAttributeValue::getName, PmsSpuAttributeValue::getValue)
        ).stream().map(item -> {
            GoodsDetailVO.Attribute attribute = new GoodsDetailVO.Attribute();
            BeanUtil.copyProperties(item, attribute);
            return attribute;
        }).collect(Collectors.toList());
        goodsDetailVO.setAttributeList(attributeList);


        // 商品规格列表
        List<PmsSpuAttributeValue> specSourceList = spuAttributeValueService.list(new LambdaQueryWrapper<PmsSpuAttributeValue>()
                .eq(PmsSpuAttributeValue::getType, AttributeTypeEnum.SPECIFICATION.getValue())
                .eq(PmsSpuAttributeValue::getSpuId, goodsId)
                .select(PmsSpuAttributeValue::getId, PmsSpuAttributeValue::getName, PmsSpuAttributeValue::getValue)
        );

        List<GoodsDetailVO.Specification> specList = new ArrayList<>();
        // 规格Map [key:"颜色",value:[{id:1,value:"黑"},{id:2,value:"白"}]]
        Map<String, List<PmsSpuAttributeValue>> specValueMap = specSourceList.stream()
                .collect(Collectors.groupingBy(PmsSpuAttributeValue::getName));

        for (Map.Entry<String, List<PmsSpuAttributeValue>> entry : specValueMap.entrySet()) {
            String specName = entry.getKey();
            List<PmsSpuAttributeValue> specValueSourceList = entry.getValue();

            // 规格映射处理
            GoodsDetailVO.Specification spec = new GoodsDetailVO.Specification();
            spec.setName(specName);
            if (CollectionUtil.isNotEmpty(specValueSourceList)) {
                List<GoodsDetailVO.Specification.Value> specValueList = specValueSourceList.stream().map(item -> {
                    GoodsDetailVO.Specification.Value specValue = new GoodsDetailVO.Specification.Value();
                    specValue.setId(item.getId());
                    specValue.setValue(item.getValue());
                    return specValue;
                }).collect(Collectors.toList());
                spec.setValues(specValueList);
                specList.add(spec);
            }
        }
        goodsDetailVO.setSpecList(specList);
        // 商品SKU列表
        List<PmsSku> skuSourceList = skuService.list(new LambdaQueryWrapper<PmsSku>().eq(PmsSku::getSpuId, goodsId));
        if (CollectionUtil.isNotEmpty(skuSourceList)) {
            List<GoodsDetailVO.Sku> skuList = skuSourceList.stream().map(item -> {
                GoodsDetailVO.Sku sku = new GoodsDetailVO.Sku();
                BeanUtil.copyProperties(item, sku);
                return sku;
            }).collect(Collectors.toList());
            goodsDetailVO.setSkuList(skuList);
        }
        // 添加用户浏览历史记录
        ProductHistoryVO vo = new ProductHistoryVO();
        vo.setId(goodsInfo.getId());
        vo.setName(goodsInfo.getName());
        vo.setPicUrl(goodsInfo.getAlbum() != null ? goodsInfo.getAlbum().get(0) : null);
        memberFeignClient.addProductViewHistory(vo);
        return goodsDetailVO;
    }

    @Override
    public GoodsDetailVO getGoodsBySkuId(Long skuId) {
        PmsSku skuInfo = skuService.getById(skuId);
        if (null == skuInfo) {
            throw new BizException("商品不存在");
        }
        Long spuId = skuInfo.getSpuId();
        return getGoodsById(spuId);
    }
}

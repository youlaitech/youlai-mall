package com.youlai.mall.pms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.mall.pms.common.constant.PmsConstants;
import com.youlai.mall.pms.common.enums.AttributeTypeEnum;
import com.youlai.mall.pms.component.BloomRedisService;
import com.youlai.mall.pms.mapper.PmsSpuMapper;
import com.youlai.mall.pms.pojo.dto.admin.GoodsFormDTO;
import com.youlai.mall.pms.pojo.entity.PmsSku;
import com.youlai.mall.pms.pojo.entity.PmsSpu;
import com.youlai.mall.pms.pojo.entity.PmsSpuAttributeValue;
import com.youlai.mall.pms.pojo.vo.admin.GoodsDetailVO;
import com.youlai.mall.pms.service.IPmsAttributeService;
import com.youlai.mall.pms.service.IPmsSkuService;
import com.youlai.mall.pms.service.IPmsSpuAttributeValueService;
import com.youlai.mall.pms.service.IPmsSpuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @author <a href="mailto:xianrui0365@163.com">xianrui</a>
 */
@Service
@RequiredArgsConstructor
public class PmsSpuServiceImpl extends ServiceImpl<PmsSpuMapper, PmsSpu> implements IPmsSpuService {
    private final IPmsSkuService iPmsSkuService;
    private final IPmsSpuAttributeValueService iPmsSpuAttributeValueService;
    private final IPmsAttributeService attributeService;
    private final BloomRedisService bloomRedisService;

    @Override
    public IPage<PmsSpu> list(Page<PmsSpu> page, String name, Long categoryId) {
        List<PmsSpu> list = this.baseMapper.list(page, name, categoryId);
        page.setRecords(list);
        return page;
    }

    @Override
    @Transactional
    public boolean addGoods(GoodsFormDTO goodsForm) {
/*        SpuDTO SpuDTO = goodsForm.getSpu();
        List<PmsSpuAttributeValue> attrValues = goodsForm.getAttrs();
        List<PmsSpuAttributeValue> specs = goodsForm.getAttrs();
        List<PmsSku> skuList = goodsForm.getSkus();

        // spu保存
        PmsSpu spu = new PmsSpu();
        BeanUtil.copyProperties(SpuDTO, spu);
        if (SpuDTO.getPics() != null) {
            String picUrls = JSONUtil.toJsonStr(SpuDTO.getPics());
            spu.setAlbum(picUrls);
        }
        this.save(spu);

        // 属性保存
        Optional.ofNullable(attrValues).ifPresent(list -> {
            list.forEach(item -> item.setSpuId(spu.getId()));
            iPmsSpuAttributeValueService.saveBatch(list);
        });

        // 规格保存
        Optional.ofNullable(specs).ifPresent(list -> {
          *//*  list.forEach(item -> item.setSpuId(spu.getId()));
            iPmsSpuSpecValueService.saveBatch(list);*//*
        });

        // sku保存
        Optional.ofNullable(skuList).ifPresent(list -> {
            list.forEach(item -> item.setSpuId(spu.getId()));
            iPmsSkuService.saveBatch(skuList);
        });
        bloomRedisService.addByBloomFilter(PRODUCT_REDIS_BLOOM_FILTER, spu.getId() + "");*/
        return true;
    }


    @Transactional
    @Override
    public boolean updateGoods(GoodsFormDTO goods) {

        Long goodsId = goods.getId();
        // 属性列表
        List<GoodsFormDTO.AttributeValue> attrValList = goods.getAttrList();
        this.saveAttribute(goodsId, attrValList);

        // 规格列表
        List<GoodsFormDTO.AttributeValue> specList = goods.getSpecList();
        Map<String, Long> specTempIdIdMap = this.saveSpecification(goodsId, specList);

        // SKU列表
        List<PmsSku> skuList = goods.getSkuList();
        this.saveSku(goodsId, skuList, specTempIdIdMap);

        // SPU


        return true;
    }

    private boolean saveSku(Long goodsId, List<PmsSku> skuList, Map<String, Long> specTempIdIdMap) {


        List<PmsSku> pmsSkuList = skuList.stream().map(sku -> {
            // 临时规格ID转换
            String specIds = Arrays.asList(sku.getSpecIds().split("_")).stream()
                    .map(specId ->
                            specId.startsWith(PmsConstants.TEMP_ID_PREFIX) ? specTempIdIdMap.get(specId) + "" : specId
                    )
                    .collect(Collectors.joining("_"));
            sku.setSpecIds(specIds);
            sku.setSpuId(goodsId);
            return sku;
        }).collect(Collectors.toList());

        // 新增、修改SKU


        return true;
    }


    /**
     * 保存商品属性
     *
     * @param goodsId
     * @param attrValList
     * @return
     */
    private boolean saveAttribute(Long goodsId, List<GoodsFormDTO.AttributeValue> attrValList) {
        List<Long> formAttrValIds = attrValList.stream()
                .filter(item -> item.getId() != null)
                .map(item -> Convert.toLong(item.getId()))
                .collect(Collectors.toList());

        List<Long> dbAttrValIds = iPmsSpuAttributeValueService.list(new LambdaQueryWrapper<PmsSpuAttributeValue>()
                .eq(PmsSpuAttributeValue::getSpuId, goodsId)
                .eq(PmsSpuAttributeValue::getType, AttributeTypeEnum.ATTRIBUTE.getValue())
                .select(PmsSpuAttributeValue::getId)
        ).stream().map(item -> item.getId()).collect(Collectors.toList());

        List<Long> removeAttrValIds = dbAttrValIds.stream().filter(id -> !formAttrValIds.contains(id)).collect(Collectors.toList());
        if (CollectionUtil.isNotEmpty(removeAttrValIds)) {
            iPmsSpuAttributeValueService.removeByIds(removeAttrValIds);
        }

        // 新增或修改商品属性
        List<PmsSpuAttributeValue> pmsSpuAttributeValueList = attrValList.stream().map(item -> {
            PmsSpuAttributeValue pmsSpuAttributeValue = new PmsSpuAttributeValue();
            BeanUtil.copyProperties(item, pmsSpuAttributeValue);
            pmsSpuAttributeValue.setSpuId(goodsId);
            pmsSpuAttributeValue.setType(AttributeTypeEnum.ATTRIBUTE.getValue());
            return pmsSpuAttributeValue;
        }).collect(Collectors.toList());
        if (CollectionUtil.isNotEmpty(pmsSpuAttributeValueList)) {
            return iPmsSpuAttributeValueService.saveOrUpdateBatch(pmsSpuAttributeValueList);
        }
        return true;
    }

    /**
     * 保存商品规格，新增的规格需要返回临时ID和持久化到数据库的ID的映射关系，替换SKU中的规格ID集合
     *
     * @param goodsId  商品ID
     * @param specList 规格列表
     * @return Map: key-临时ID；value-持久化返回ID
     */
    private Map<String, Long> saveSpecification(Long goodsId, List<GoodsFormDTO.AttributeValue> specList) {

        // 删除规格
        List<Long> formSpecValIds = specList.stream()
                .filter(item -> item.getId() != null && !item.getId().startsWith(PmsConstants.TEMP_ID_PREFIX))
                .map(item -> Convert.toLong(item.getId()))
                .collect(Collectors.toList());

        List<Long> dbSpecValIds = iPmsSpuAttributeValueService.list(new LambdaQueryWrapper<PmsSpuAttributeValue>()
                .eq(PmsSpuAttributeValue::getSpuId, goodsId)
                .eq(PmsSpuAttributeValue::getType, AttributeTypeEnum.SPECIFICATION.getValue())
                .select(PmsSpuAttributeValue::getId)
        ).stream().map(item -> item.getId()).collect(Collectors.toList());

        List<Long> removeAttrValIds = dbSpecValIds.stream().filter(id -> !formSpecValIds.contains(id)).collect(Collectors.toList());
        if (CollectionUtil.isNotEmpty(removeAttrValIds)) {
            iPmsSpuAttributeValueService.removeByIds(removeAttrValIds);
        }
        // 新增规格
        Map<String, Long> tempIdIdMap = new HashMap<>();
        List<GoodsFormDTO.AttributeValue> newSpecList = specList.stream()
                .filter(item -> item.getId().startsWith(PmsConstants.TEMP_ID_PREFIX)).collect(Collectors.toList());
        if (CollectionUtil.isNotEmpty(newSpecList)) {
            newSpecList.forEach(item -> {
                PmsSpuAttributeValue specification = new PmsSpuAttributeValue();
                BeanUtil.copyProperties(item, specification, "id");
                specification.setSpuId(goodsId);
                specification.setType(AttributeTypeEnum.SPECIFICATION.getValue());
                iPmsSpuAttributeValueService.save(specification);
                tempIdIdMap.put(item.getId(), specification.getId());
            });
        }
        // 修改规格
        List<PmsSpuAttributeValue> pmsSpuAttributeValueList = specList.stream()
                .filter(item -> !item.getId().startsWith(PmsConstants.TEMP_ID_PREFIX))
                .map(spec -> {
                    PmsSpuAttributeValue pmsSpuAttributeValue = new PmsSpuAttributeValue();
                    BeanUtil.copyProperties(spec, pmsSpuAttributeValue);
                    pmsSpuAttributeValue.setSpuId(goodsId);
                    pmsSpuAttributeValue.setType(AttributeTypeEnum.SPECIFICATION.getValue());
                    return pmsSpuAttributeValue;
                }).collect(Collectors.toList());
        if (CollectionUtil.isNotEmpty(pmsSpuAttributeValueList)) {
            iPmsSpuAttributeValueService.updateBatchById(pmsSpuAttributeValueList);
        }
        return tempIdIdMap;
    }


    @Override
    public GoodsDetailVO getGoodsById(Long id) {
        GoodsDetailVO goodsDetailVO = new GoodsDetailVO();

        PmsSpu spu = this.getById(id);
        Assert.isTrue(spu != null, "商品不存在");

        BeanUtil.copyProperties(spu, goodsDetailVO);

        // 商品图册JSON字符串转JSON
        String album = spu.getAlbum();
        if (StrUtil.isNotBlank(album)) {
            List<String> picUrls = JSONUtil.toList(album, String.class);
            goodsDetailVO.setSubPicUrls(picUrls);
        }

        // 商品属性列表
        List<PmsSpuAttributeValue> attrList = iPmsSpuAttributeValueService.list(new LambdaQueryWrapper<PmsSpuAttributeValue>()
                .eq(PmsSpuAttributeValue::getSpuId, id)
                .eq(PmsSpuAttributeValue::getType, AttributeTypeEnum.ATTRIBUTE.getValue())
        );
        goodsDetailVO.setAttrList(attrList);

        // 商品规格列表
        List<PmsSpuAttributeValue> specList = iPmsSpuAttributeValueService.list(new LambdaQueryWrapper<PmsSpuAttributeValue>()
                .eq(PmsSpuAttributeValue::getSpuId, id)
                .eq(PmsSpuAttributeValue::getType, AttributeTypeEnum.SPECIFICATION.getValue())
        );
        goodsDetailVO.setSpecList(specList);

        // 商品SKU列表
        List<PmsSku> skuList = iPmsSkuService.list(new LambdaQueryWrapper<PmsSku>().eq(PmsSku::getSpuId, id));
        goodsDetailVO.setSkuList(skuList);

        return goodsDetailVO;
    }

    @Override
    public boolean removeBySpuIds(List<Long> spuIds) {
        Optional.ofNullable(spuIds).ifPresent(
                ids -> ids.forEach(spuId -> {
                    // sku
                    iPmsSkuService.remove(new LambdaQueryWrapper<PmsSku>().eq(PmsSku::getSpuId, spuId));
                    // 规格
                    iPmsSpuAttributeValueService.remove(new LambdaQueryWrapper<PmsSpuAttributeValue>().eq(PmsSpuAttributeValue::getId, spuId));
                    // 属性
                    iPmsSpuAttributeValueService.remove(new LambdaQueryWrapper<PmsSpuAttributeValue>().eq(PmsSpuAttributeValue::getSpuId, spuId));
                    // spu
                    this.removeById(spuId);
                })
        );
        return true;
    }


}

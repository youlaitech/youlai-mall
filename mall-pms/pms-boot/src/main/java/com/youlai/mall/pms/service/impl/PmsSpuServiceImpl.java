package com.youlai.mall.pms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.common.web.util.MemberUtils;
import com.youlai.mall.pms.common.constant.PmsConstants;
import com.youlai.mall.pms.common.enums.AttributeTypeEnum;
import com.youlai.mall.pms.mapper.PmsSpuMapper;
import com.youlai.mall.pms.pojo.dto.admin.GoodsFormDTO;
import com.youlai.mall.pms.pojo.entity.PmsSku;
import com.youlai.mall.pms.pojo.entity.PmsSpu;
import com.youlai.mall.pms.pojo.entity.PmsSpuAttributeValue;
import com.youlai.mall.pms.pojo.query.SpuPageQuery;
import com.youlai.mall.pms.pojo.vo.GoodsDetailVO;
import com.youlai.mall.pms.pojo.vo.GoodsPageVO;
import com.youlai.mall.pms.pojo.vo.ProductHistoryVO;
import com.youlai.mall.pms.pojo.vo.PmsGoodsDetailVO;
import com.youlai.mall.pms.service.IPmsSkuService;
import com.youlai.mall.pms.service.IPmsSpuAttributeValueService;
import com.youlai.mall.pms.service.IPmsSpuService;
import com.youlai.mall.ums.api.MemberFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


/**
 * 商品业务实现类
 *
 * @author <a href="mailto:xianrui0365@163.com">haoxr</a>
 * @date 2021/8/8
 */
@Service
@RequiredArgsConstructor
public class PmsSpuServiceImpl extends ServiceImpl<PmsSpuMapper, PmsSpu> implements IPmsSpuService {

    private final IPmsSkuService skuService;
    private final IPmsSpuAttributeValueService spuAttributeValueService;
    private final MemberFeignClient memberFeignClient;

    /**
     * 「移动端」商品分页列表
     *
     * @param queryParams
     * @return
     */
    @Override
    public IPage<GoodsPageVO> listAppSpuPage(SpuPageQuery queryParams) {
        Page<GoodsPageVO> page = new Page<>(queryParams.getPageNum(), queryParams.getPageSize());
        List<GoodsPageVO> list = this.baseMapper.listAppSpuPage(page, queryParams);
        page.setRecords(list);
        return page;
    }

    /**
     * 「移动端」获取商品详情
     *
     * @param spuId 商品ID
     * @return
     */
    @Override
    public GoodsDetailVO getAppSpuDetail(Long spuId) {
        PmsSpu pmsSpu = this.getById(spuId);
        Assert.isTrue(pmsSpu != null, "商品不存在");

        GoodsDetailVO goodsDetailVO = new GoodsDetailVO();

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
                .eq(PmsSpuAttributeValue::getSpuId, spuId)
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
                .eq(PmsSpuAttributeValue::getSpuId, spuId)
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
        List<PmsSku> skuSourceList = skuService.list(new LambdaQueryWrapper<PmsSku>().eq(PmsSku::getSpuId, spuId));
        if (CollectionUtil.isNotEmpty(skuSourceList)) {
            List<GoodsDetailVO.Sku> skuList = skuSourceList.stream().map(item -> {
                GoodsDetailVO.Sku sku = new GoodsDetailVO.Sku();
                BeanUtil.copyProperties(item, sku);
                return sku;
            }).collect(Collectors.toList());
            goodsDetailVO.setSkuList(skuList);
        }

        // 添加用户浏览历史记录
        Long loginUserId = MemberUtils.getMemberId();
        if (loginUserId != null) {
            ProductHistoryVO vo = new ProductHistoryVO();
            vo.setId(goodsInfo.getId());
            vo.setName(goodsInfo.getName());
            vo.setPicUrl(goodsInfo.getAlbum() != null ? goodsInfo.getAlbum().get(0) : null);
            memberFeignClient.addProductViewHistory(vo);
        }
        return goodsDetailVO;
    }


    /**
     * 获取商品（SPU）详情
     *
     * @param id 商品（SPU）ID
     * @return
     */
    @Override
    public PmsGoodsDetailVO getGoodsById(Long id) {
        PmsGoodsDetailVO pmsGoodsDetailVO = new PmsGoodsDetailVO();

        PmsSpu pmsSpu = this.getById(id);
        Assert.isTrue(pmsSpu != null, "商品不存在");

        BeanUtil.copyProperties(pmsSpu, pmsGoodsDetailVO,"album");
        pmsGoodsDetailVO.setSubPicUrls(pmsSpu.getAlbum());

        // 商品属性列表
        List<PmsSpuAttributeValue> attrList = spuAttributeValueService.list(new LambdaQueryWrapper<PmsSpuAttributeValue>()
                .eq(PmsSpuAttributeValue::getSpuId, id)
                .eq(PmsSpuAttributeValue::getType, AttributeTypeEnum.ATTRIBUTE.getValue())
        );
        pmsGoodsDetailVO.setAttrList(attrList);

        // 商品规格列表
        List<PmsSpuAttributeValue> specList = spuAttributeValueService.list(new LambdaQueryWrapper<PmsSpuAttributeValue>()
                .eq(PmsSpuAttributeValue::getSpuId, id)
                .eq(PmsSpuAttributeValue::getType, AttributeTypeEnum.SPECIFICATION.getValue())
        );
        pmsGoodsDetailVO.setSpecList(specList);

        // 商品SKU列表
        List<PmsSku> skuList = skuService.list(new LambdaQueryWrapper<PmsSku>().eq(PmsSku::getSpuId, id));
        pmsGoodsDetailVO.setSkuList(skuList);
        return pmsGoodsDetailVO;

    }


    /**
     * 商品分页列表
     *
     * @param page
     * @param name
     * @param categoryId
     * @return
     */
    @Override
    public IPage<PmsSpu> list(Page<PmsSpu> page, String name, Long categoryId) {
        List<PmsSpu> list = this.baseMapper.list(page, name, categoryId);
        page.setRecords(list);
        return page;
    }

    /**
     * 添加商品
     *
     * @param goods
     * @return
     */
    @Override
    @Transactional
    public boolean addGoods(GoodsFormDTO goods) {
        Long goodsId = this.saveSpu(goods);
        // 属性保存
        List<GoodsFormDTO.AttributeValue> attrValList = goods.getAttrList();
        this.saveAttribute(goodsId, attrValList);
        // 规格保存
        List<GoodsFormDTO.AttributeValue> specList = goods.getSpecList();
        Map<String, Long> specTempIdIdMap = this.saveSpecification(goodsId, specList);
        // SKU保存
        List<PmsSku> skuList = goods.getSkuList();
        return this.saveSku(goodsId, skuList, specTempIdIdMap);
    }


    /**
     * 修改商品
     *
     * @param goods
     * @return
     */
    @Transactional
    @Override
    public boolean updateGoods(GoodsFormDTO goods) {

        Long goodsId = goods.getId();
        // 属性保存
        List<GoodsFormDTO.AttributeValue> attrValList = goods.getAttrList();
        this.saveAttribute(goodsId, attrValList);

        // 规格保存
        List<GoodsFormDTO.AttributeValue> specList = goods.getSpecList();
        Map<String, Long> specTempIdIdMap = this.saveSpecification(goodsId, specList);

        // SKU保存
        List<PmsSku> skuList = goods.getSkuList();
        this.saveSku(goodsId, skuList, specTempIdIdMap);

        // SPU
        boolean saveResult = this.saveSpu(goods) > 0;
        return saveResult;
    }


    /**
     * 批量删除商品（SPU）
     *
     * @param goodsIds
     * @return
     */
    @Override
    @Transactional
    public boolean removeByGoodsIds(List<Long> goodsIds) {
        boolean result = true;
        for (Long goodsId : goodsIds) {
            // sku
            skuService.remove(new LambdaQueryWrapper<PmsSku>().eq(PmsSku::getSpuId, goodsId));
            // 规格
            spuAttributeValueService.remove(new LambdaQueryWrapper<PmsSpuAttributeValue>().eq(PmsSpuAttributeValue::getId, goodsId));
            // 属性
            spuAttributeValueService.remove(new LambdaQueryWrapper<PmsSpuAttributeValue>().eq(PmsSpuAttributeValue::getSpuId, goodsId));
            // spu
            result = this.removeById(goodsId);
        }
        return result;
    }


    /**
     * 保存商品
     *
     * @param goods
     * @return
     */
    private Long saveSpu(GoodsFormDTO goods) {
        PmsSpu pmsSpu = new PmsSpu();
        BeanUtil.copyProperties(goods, pmsSpu);
        // 商品图册
        pmsSpu.setAlbum(goods.getSubPicUrls());
        boolean result = this.saveOrUpdate(pmsSpu);
        return result ? pmsSpu.getId() : 0;
    }


    /**
     * 保存SKU，需要替换提交表单中的临时规格ID
     *
     * @param goodsId
     * @param skuList
     * @param specTempIdIdMap
     * @return
     */
    private boolean saveSku(Long goodsId, List<PmsSku> skuList, Map<String, Long> specTempIdIdMap) {

        // 删除SKU
        List<Long> formSkuIds = skuList.stream().map(PmsSku::getId).collect(Collectors.toList());

        List<Long> dbSkuIds = skuService.list(new LambdaQueryWrapper<PmsSku>()
                .eq(PmsSku::getSpuId, goodsId)
                .select(PmsSku::getId))
                .stream().map(PmsSku::getId)
                .collect(Collectors.toList());

        List<Long> removeSkuIds = dbSkuIds.stream().filter(dbSkuId -> !formSkuIds.contains(dbSkuId)).collect(Collectors.toList());

        if (CollectionUtil.isNotEmpty(removeSkuIds)) {
            skuService.removeByIds(removeSkuIds);
        }

        // 新增/修改SKU
        List<PmsSku> pmsSkuList = skuList.stream().map(sku -> {
            // 临时规格ID转换
            String specIds = Arrays.stream(sku.getSpecIds().split("\\|"))
                    .map(specId ->
                            specId.startsWith(PmsConstants.TEMP_ID_PREFIX) ? specTempIdIdMap.get(specId) + "" : specId
                    )
                    .collect(Collectors.joining("_"));
            sku.setSpecIds(specIds);
            sku.setSpuId(goodsId);
            return sku;
        }).collect(Collectors.toList());
        return skuService.saveOrUpdateBatch(pmsSkuList);
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

        List<Long> dbAttrValIds = spuAttributeValueService.list(new LambdaQueryWrapper<PmsSpuAttributeValue>()
                .eq(PmsSpuAttributeValue::getSpuId, goodsId)
                .eq(PmsSpuAttributeValue::getType, AttributeTypeEnum.ATTRIBUTE.getValue())
                .select(PmsSpuAttributeValue::getId)
        ).stream().map(PmsSpuAttributeValue::getId).collect(Collectors.toList());

        List<Long> removeAttrValIds = dbAttrValIds.stream().filter(id -> !formAttrValIds.contains(id)).collect(Collectors.toList());
        if (CollectionUtil.isNotEmpty(removeAttrValIds)) {
            spuAttributeValueService.removeByIds(removeAttrValIds);
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
            return spuAttributeValueService.saveOrUpdateBatch(pmsSpuAttributeValueList);
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

        List<Long> dbSpecValIds = spuAttributeValueService.list(new LambdaQueryWrapper<PmsSpuAttributeValue>()
                .eq(PmsSpuAttributeValue::getSpuId, goodsId)
                .eq(PmsSpuAttributeValue::getType, AttributeTypeEnum.SPECIFICATION.getValue())
                .select(PmsSpuAttributeValue::getId)
        ).stream().map(PmsSpuAttributeValue::getId).collect(Collectors.toList());

        List<Long> removeAttrValIds = dbSpecValIds.stream().filter(id -> !formSpecValIds.contains(id)).collect(Collectors.toList());
        if (CollectionUtil.isNotEmpty(removeAttrValIds)) {
            spuAttributeValueService.removeByIds(removeAttrValIds);
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
                spuAttributeValueService.save(specification);
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
            spuAttributeValueService.updateBatchById(pmsSpuAttributeValueList);
        }
        return tempIdIdMap;
    }
}

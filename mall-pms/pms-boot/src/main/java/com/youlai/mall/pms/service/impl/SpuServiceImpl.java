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
import com.youlai.common.security.util.SecurityUtils;
import com.youlai.mall.pms.common.constant.ProductConstants;
import com.youlai.mall.pms.common.enums.AttributeTypeEnum;
import com.youlai.mall.pms.converter.SpuAttributeConverter;
import com.youlai.mall.pms.converter.SpuConverter;
import com.youlai.mall.pms.mapper.PmsSpuMapper;
import com.youlai.mall.pms.pojo.form.PmsSpuAttributeForm;
import com.youlai.mall.pms.pojo.form.PmsSpuForm;
import com.youlai.mall.pms.pojo.entity.PmsSku;
import com.youlai.mall.pms.pojo.entity.PmsSpu;
import com.youlai.mall.pms.pojo.entity.PmsSpuAttribute;
import com.youlai.mall.pms.pojo.query.SpuPageQuery;
import com.youlai.mall.pms.pojo.vo.*;
import com.youlai.mall.pms.service.SkuService;
import com.youlai.mall.pms.service.SpuAttributeService;
import com.youlai.mall.pms.service.SpuService;
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
public class SpuServiceImpl extends ServiceImpl<PmsSpuMapper, PmsSpu> implements SpuService {

    private final SkuService skuService;
    private final SpuAttributeService spuAttributeService;
    private final MemberFeignClient memberFeignClient;

    private final SpuConverter spuConverter;

    private final SpuAttributeConverter spuAttributeConverter;

    /**
     * 「管理端」商品分页列表
     *
     * @param queryParams
     * @return
     */
    @Override
    public IPage<PmsSpuPageVO> listPmsSpuPages(SpuPageQuery queryParams) {
        Page<PmsSpuPageVO> page = new Page<>(queryParams.getPageNum(), queryParams.getPageSize());
        List<PmsSpuPageVO> list = this.baseMapper.listPmsSpuPages(page, queryParams);
        page.setRecords(list);
        return page;
    }

    /**
     * 「应用端」商品分页列表
     *
     * @param queryParams
     * @return
     */
    @Override
    public IPage<SpuPageVO> listSpuPages(SpuPageQuery queryParams) {
        Page<SpuPageVO> page = new Page<>(queryParams.getPageNum(), queryParams.getPageSize());
        List<SpuPageVO> list = this.baseMapper.listSpuPages(page, queryParams);
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
    public SpuDetailVO getSpuDetail(Long spuId) {
        PmsSpu pmsSpu = this.getById(spuId);
        Assert.isTrue(pmsSpu != null, "商品不存在");

        SpuDetailVO spuDetailVO = new SpuDetailVO();

        // 商品基本信息
        SpuDetailVO.GoodsInfo goodsInfo = new SpuDetailVO.GoodsInfo();
        BeanUtil.copyProperties(pmsSpu, goodsInfo, "album");

        List<String> album = new ArrayList<>();

        if (StrUtil.isNotBlank(pmsSpu.getPicUrl())) {
            album.add(pmsSpu.getPicUrl());
        }
        if (pmsSpu.getAlbum() != null && pmsSpu.getAlbum().length > 0) {
            album.addAll(Arrays.asList(pmsSpu.getAlbum()));
            goodsInfo.setAlbum(album);
        }
        spuDetailVO.setGoodsInfo(goodsInfo);

        // 商品属性列表
        List<SpuDetailVO.Attribute> attributeList = spuAttributeService.list(new LambdaQueryWrapper<PmsSpuAttribute>()
                        .eq(PmsSpuAttribute::getType, AttributeTypeEnum.ATTR.getValue()).eq(PmsSpuAttribute::getSpuId, spuId)
                        .select(PmsSpuAttribute::getId, PmsSpuAttribute::getName, PmsSpuAttribute::getValue)).stream().
                map(item -> {
                    SpuDetailVO.Attribute attribute = new SpuDetailVO.Attribute();
                    BeanUtil.copyProperties(item, attribute);
                    return attribute;
                }).collect(Collectors.toList());
        spuDetailVO.setAttributeList(attributeList);


        // 商品规格列表
        List<PmsSpuAttribute> specSourceList = spuAttributeService.list(new LambdaQueryWrapper<PmsSpuAttribute>()
                .eq(PmsSpuAttribute::getType, AttributeTypeEnum.SPEC.getValue())
                .eq(PmsSpuAttribute::getSpuId, spuId)
                .select(PmsSpuAttribute::getId, PmsSpuAttribute::getName, PmsSpuAttribute::getValue));

        List<SpuDetailVO.Specification> specList = new ArrayList<>();
        // 规格Map [key:"颜色",value:[{id:1,value:"黑"},{id:2,value:"白"}]]
        Map<String, List<PmsSpuAttribute>> specValueMap = specSourceList.stream().collect(Collectors.groupingBy(PmsSpuAttribute::getName));

        for (Map.Entry<String, List<PmsSpuAttribute>> entry : specValueMap.entrySet()) {
            String specName = entry.getKey();
            List<PmsSpuAttribute> specValueSourceList = entry.getValue();

            // 规格映射处理
            SpuDetailVO.Specification spec = new SpuDetailVO.Specification();
            spec.setName(specName);
            if (CollectionUtil.isNotEmpty(specValueSourceList)) {
                List<SpuDetailVO.Specification.Value> specValueList = specValueSourceList.stream().map(item -> {
                    SpuDetailVO.Specification.Value specValue = new SpuDetailVO.Specification.Value();
                    specValue.setId(item.getId());
                    specValue.setValue(item.getValue());
                    return specValue;
                }).collect(Collectors.toList());
                spec.setValues(specValueList);
                specList.add(spec);
            }
        }
        spuDetailVO.setSpecList(specList);

        // 商品SKU列表
        List<PmsSku> skuSourceList = skuService.list(new LambdaQueryWrapper<PmsSku>().eq(PmsSku::getSpuId, spuId));
        if (CollectionUtil.isNotEmpty(skuSourceList)) {
            List<SpuDetailVO.Sku> skuList = skuSourceList.stream().map(item -> {
                SpuDetailVO.Sku sku = new SpuDetailVO.Sku();
                BeanUtil.copyProperties(item, sku);
                return sku;
            }).collect(Collectors.toList());
            spuDetailVO.setSkuList(skuList);
        }

        // 添加用户浏览历史记录
        Long memberId = SecurityUtils.getMemberId();
        if (memberId != null) {
            ProductHistoryVO vo = new ProductHistoryVO();
            vo.setId(goodsInfo.getId());
            vo.setName(goodsInfo.getName());
            vo.setPicUrl(goodsInfo.getAlbum() != null ? goodsInfo.getAlbum().get(0) : null);
            memberFeignClient.addProductViewHistory(vo);
        }
        return spuDetailVO;
    }


    /**
     * 获取商品详情
     *
     * @param spuId 商品ID
     * @return
     */
    @Override
    public PmsSpuDetailVO getPmsSpuDetail(Long spuId) {
        PmsSpuDetailVO pmsSpuDetailVO = new PmsSpuDetailVO();

        PmsSpu entity = this.getById(spuId);
        Assert.isTrue(entity != null, "商品不存在");

        BeanUtil.copyProperties(entity, pmsSpuDetailVO, "album");
        pmsSpuDetailVO.setSubPicUrls(entity.getAlbum());

        // 商品属性列表
        List<PmsSpuAttribute> attrList = spuAttributeService.list(new LambdaQueryWrapper<PmsSpuAttribute>()
                .eq(PmsSpuAttribute::getSpuId, spuId)
                .eq(PmsSpuAttribute::getType, AttributeTypeEnum.ATTR.getValue()));
        pmsSpuDetailVO.setAttrList(attrList);

        // 商品规格列表
        List<PmsSpuAttribute> specList = spuAttributeService.list(new LambdaQueryWrapper<PmsSpuAttribute>()
                .eq(PmsSpuAttribute::getSpuId, spuId)
                .eq(PmsSpuAttribute::getType, AttributeTypeEnum.SPEC.getValue()));
        pmsSpuDetailVO.setSpecList(specList);

        // 商品SKU列表
        List<PmsSku> skuList = skuService.list(new LambdaQueryWrapper<PmsSku>().eq(PmsSku::getSpuId, spuId));
        pmsSpuDetailVO.setSkuList(skuList);
        return pmsSpuDetailVO;

    }

    /**
     * 添加商品
     *
     * @param formData
     * @return
     */
    @Override
    @Transactional
    public boolean addSpu(PmsSpuForm formData) {

        PmsSpu entity = spuConverter.form2Entity(formData);

        boolean result = this.save(entity);
        if (result) {
            Long spuId = entity.getId();
            // 保存属性
            List<PmsSpuAttributeForm> attrList = formData.getAttrList();
            this.saveSpuAttrs(spuId, attrList);
            // 保存规格
            List<PmsSpuAttributeForm> specList = formData.getSpecList();
            Map<String, Long> tempWithNewSpecIdMap = this.saveSpuSpecs(spuId, specList);
            // 保存SKU
            List<PmsSku> skuList = formData.getSkuList();
            this.saveSku(spuId, skuList, tempWithNewSpecIdMap);

        }
        // 无异常返回true
        return result;
    }


    /**
     * 修改商品
     *
     * @param spuId    商品ID
     * @param formData 商品表单
     * @return
     */
    @Transactional
    @Override
    public boolean updateSpuById(Long spuId, PmsSpuForm formData) {

        PmsSpu entity = spuConverter.form2Entity(formData);

        boolean result = this.updateById(entity);
        if (result) {

            // 属性保存
            List<PmsSpuAttributeForm> attrList = formData.getAttrList();
            this.saveSpuAttrs(spuId, attrList);

            // 保存商品规格值
            List<PmsSpuAttributeForm> specList = formData.getSpecList();
            Map<String, Long> specTempIdIdMap = this.saveSpuSpecs(spuId, specList);

            // SKU保存
            List<PmsSku> skuList = formData.getSkuList();
            this.saveSku(spuId, skuList, specTempIdIdMap);
        }

        return result;
    }


    /**
     * 删除商品
     *
     * @param ids 商品ID，多个以英文逗号(,)分割
     * @return
     */
    @Override
    @Transactional
    public boolean removeBySpuIds(String ids) {

        String[] spuIds = ids.split(",");

        for (String spuId : spuIds) {
            skuService.remove(new LambdaQueryWrapper<PmsSku>().eq(PmsSku::getSpuId, spuId));
            // 规格
            spuAttributeService.remove(new LambdaQueryWrapper<PmsSpuAttribute>().eq(PmsSpuAttribute::getSpuId, spuId));
            // 属性
            spuAttributeService.remove(new LambdaQueryWrapper<PmsSpuAttribute>().eq(PmsSpuAttribute::getSpuId, spuId));
            // SPU
            this.removeById(spuId);
        }
        // 无异常直接返回true
        return true;
    }

    /**
     * 获取商品秒杀接口
     *
     * @return
     */
    @Override
    public List<SeckillingSpuVO> listSeckillingSpu() {
        List<PmsSpu> entities = this.list(new LambdaQueryWrapper<PmsSpu>()
                .select(PmsSpu::getId, PmsSpu::getName, PmsSpu::getPicUrl, PmsSpu::getPrice)
                .orderByDesc(PmsSpu::getCreateTime)
        );
        List<SeckillingSpuVO> list = spuConverter.entity2SeckillingVO(entities);
        return list;
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

        List<Long> dbSkuIds = skuService.list(new LambdaQueryWrapper<PmsSku>().eq(PmsSku::getSpuId, goodsId)
                        .select(PmsSku::getId)).stream().map(PmsSku::getId)
                .collect(Collectors.toList());

        List<Long> removeSkuIds = dbSkuIds.stream().filter(dbSkuId -> !formSkuIds.contains(dbSkuId)).collect(Collectors.toList());

        if (CollectionUtil.isNotEmpty(removeSkuIds)) {
            skuService.removeByIds(removeSkuIds);
        }

        // 新增/修改SKU
        List<PmsSku> pmsSkuList = skuList.stream().map(sku -> {
            // 临时规格ID转换
            String specIds = Arrays.stream(sku.getSpecIds().split("\\|"))
                    .map(specId -> specId.startsWith(ProductConstants.SPEC_TEMP_ID_PREFIX) ? specTempIdIdMap.get(specId) + "" : specId)
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
     * @param spuId    商品ID
     * @param attrList 商品属性集合
     * @return
     */
    private boolean saveSpuAttrs(Long spuId, List<PmsSpuAttributeForm> attrList) {

        // 1. 【删除】此次提交移除的商品规格

        // 1.1 此次提交保留的商品属性ID
        List<Long> retainAttrIds = attrList.stream()
                .filter(item -> item.getId() != null)
                .map(item -> Convert.toLong(item.getId()))
                .collect(Collectors.toList());
        // 1.2 获取原商品属性ID集合
        List<Long> originAttrIds = spuAttributeService.list(new LambdaQueryWrapper<PmsSpuAttribute>()
                        .eq(PmsSpuAttribute::getSpuId, spuId).eq(PmsSpuAttribute::getType, AttributeTypeEnum.ATTR.getValue())
                        .select(PmsSpuAttribute::getId))
                .stream()
                .map(PmsSpuAttribute::getId)
                .collect(Collectors.toList());
        // 1.3 需要删除的商品属性：原商品属性-此次提交保留的属性
        List<Long> removeAttrValIds = originAttrIds.stream()
                .filter(id -> !retainAttrIds.contains(id))
                .collect(Collectors.toList());
        if (CollectionUtil.isNotEmpty(removeAttrValIds)) {
            spuAttributeService.removeByIds(removeAttrValIds);
        }

        // 新增或修改商品属性
        List<PmsSpuAttribute> entities = attrList.stream().map(item -> {
            PmsSpuAttribute entity = spuAttributeConverter.form2Entity(item);
            entity.setId(Convert.toLong(item.getId()));
            entity.setSpuId(spuId);
            entity.setType(AttributeTypeEnum.ATTR.getValue());
            return entity;
        }).collect(Collectors.toList());
        if (CollectionUtil.isNotEmpty(entities)) {
            return spuAttributeService.saveOrUpdateBatch(entities);
        }
        return true;
    }

    /**
     * 保存商品规格
     * <p>
     * 新增的规格需要返回临时ID和持久化到数据库的ID的映射关系，替换SKU中的规格ID集合
     *
     * @param spuId    商品ID
     * @param specList 规格列表
     * @return Map: key-临时ID；value-持久化返回ID
     */
    private Map<String, Long> saveSpuSpecs(Long spuId, List<PmsSpuAttributeForm> specList) {


        // 1. 【删除】此次提交移除的商品规格
        // 1.1 此次提交保留的规格
        List<Long> retainSpuSpecIds = specList.stream()
                .filter(item -> !item.getId().startsWith(ProductConstants.SPEC_TEMP_ID_PREFIX))
                .map(item -> Convert.toLong(item.getId()))
                .collect(Collectors.toList());

        // 1.2 原商品规格
        List<Long> originSpuSpecIds = spuAttributeService.list(new LambdaQueryWrapper<PmsSpuAttribute>()
                        .eq(PmsSpuAttribute::getSpuId, spuId)
                        .eq(PmsSpuAttribute::getType, AttributeTypeEnum.SPEC.getValue())
                        .select(PmsSpuAttribute::getId))
                .stream().map(PmsSpuAttribute::getId)
                .collect(Collectors.toList());

        // 1.3 需要删除的商品规格：原商品规格-此次提交保留的规格
        List<Long> removeSpuSpecIds = originSpuSpecIds.stream().filter(id -> !retainSpuSpecIds.contains(id))
                .collect(Collectors.toList());

        if (CollectionUtil.isNotEmpty(removeSpuSpecIds)) {
            // 删除商品的规格
            spuAttributeService.removeByIds(removeSpuSpecIds);
        }

        // 2. 【新增】此次提交的新加的商品规格
        // 临时规格ID和持久化数据库得到的规格ID的映射，用于替换SKU临时的规格ID
        Map<String, Long> tempWithNewSpecIdMap = new HashMap<>();
        List<PmsSpuAttributeForm> newSpecList = specList.stream()
                .filter(item -> item.getId().startsWith(ProductConstants.SPEC_TEMP_ID_PREFIX))
                .collect(Collectors.toList());
        if (CollectionUtil.isNotEmpty(newSpecList)) {
            newSpecList.forEach(item -> {
                PmsSpuAttribute entity = spuAttributeConverter.form2Entity(item);
                entity.setSpuId(spuId);
                entity.setType(AttributeTypeEnum.SPEC.getValue());
                spuAttributeService.save(entity);
                tempWithNewSpecIdMap.put(item.getId(), entity.getId());
            });
        }

        //  3. 【修改】此次提交的需要修改的商品规格
        List<PmsSpuAttribute> pmsSpuAttributeList = specList.stream()
                .filter(item -> !item.getId().startsWith(ProductConstants.SPEC_TEMP_ID_PREFIX))
                .map(item -> {
                    PmsSpuAttribute entity = spuAttributeConverter.form2Entity(item);
                    entity.setId(Convert.toLong(item.getId()));
                    entity.setSpuId(spuId);
                    entity.setType(AttributeTypeEnum.SPEC.getValue());
                    return entity;
                }).collect(Collectors.toList());
        if (CollectionUtil.isNotEmpty(pmsSpuAttributeList)) {
            spuAttributeService.updateBatchById(pmsSpuAttributeList);
        }
        return tempWithNewSpecIdMap;
    }
}

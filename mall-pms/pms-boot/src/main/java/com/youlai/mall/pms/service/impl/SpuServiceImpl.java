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
import com.youlai.mall.pms.constant.ProductConstants;
import com.youlai.mall.pms.enums.AttributeTypeEnum;
import com.youlai.mall.pms.converter.SpuAttributeConverter;
import com.youlai.mall.pms.converter.SpuConverter;
import com.youlai.mall.pms.mapper.SpuMapper;
import com.youlai.mall.pms.model.entity.SpuAttributeValue;
import com.youlai.mall.pms.model.entity.Sku;
import com.youlai.mall.pms.model.entity.Spu;
import com.youlai.mall.pms.model.form.PmsSpuAttributeForm;
import com.youlai.mall.pms.model.form.SpuForm;
import com.youlai.mall.pms.model.query.SpuPageQuery;
import com.youlai.mall.pms.model.vo.*;
import com.youlai.mall.pms.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 商品业务实现类
 *
 * @author Ray Hao
 * @since 2021/08/08
 */
@Service
@RequiredArgsConstructor
public class SpuServiceImpl extends ServiceImpl<SpuMapper, Spu> implements SpuService {

    private final SkuService skuService;
    private final SpuAttributeService spuAttributeService;
    private final SpuConverter spuConverter;
    private final SpuAttributeConverter spuAttributeConverter;
    private final SpuImageService spuImageService;

    /**
     * Admin-商品分页列表
     *
     * @param queryParams 查询参数
     * @return 商品分页列表 IPage<PmsSpuPageVO>
     */
    @Override
    public IPage<PmsSpuPageVO> listPagedSpu(SpuPageQuery queryParams) {
        Page<PmsSpuPageVO> page = new Page<>(queryParams.getPageNum(), queryParams.getPageSize());
        List<PmsSpuPageVO> list = this.baseMapper.listPagedSpu(page, queryParams);
        page.setRecords(list);
        return page;
    }

    /**
     * APP-商品分页列表
     *
     * @param queryParams 查询参数
     * @return 商品分页列表 IPage<SpuPageVO>
     */
    @Override
    public IPage<SpuPageVO> listPagedSpuForApp(SpuPageQuery queryParams) {
        Page<SpuPageVO> page = new Page<>(queryParams.getPageNum(), queryParams.getPageSize());
        List<SpuPageVO> list = this.baseMapper.listPagedSpuForApp(page, queryParams);
        page.setRecords(list);
        return page;
    }

    /**
     * App-获取商品详情
     *
     * @param spuId 商品ID
     * @return 商品详情
     */
    @Override
    public SpuDetailVO getSpuDetailForApp(Long spuId) {
        Spu spu = this.getById(spuId);
        Assert.isTrue(spu != null, "商品不存在");

        SpuDetailVO spuDetailVO = new SpuDetailVO();

        // 商品基本信息
        SpuDetailVO.GoodsInfo goodsInfo = new SpuDetailVO.GoodsInfo();
        BeanUtil.copyProperties(spu, goodsInfo, "album");

        List<String> album = new ArrayList<>();

        if (StrUtil.isNotBlank(spu.getImgUrl())) {
            album.add(spu.getImgUrl());
        }
        if (spu.getAlbum() != null && spu.getAlbum().length > 0) {
            album.addAll(Arrays.asList(spu.getAlbum()));
            goodsInfo.setAlbum(album);
        }
        spuDetailVO.setGoodsInfo(goodsInfo);

        // 商品属性列表
        List<SpuDetailVO.Attribute> attributeList = spuAttributeService.list(new LambdaQueryWrapper<SpuAttributeValue>()
                        .eq(SpuAttributeValue::getType, AttributeTypeEnum.ATTR.getValue()).eq(SpuAttributeValue::getSpuId, spuId)
                        .select(SpuAttributeValue::getId, SpuAttributeValue::getName, SpuAttributeValue::getValue)).stream().
                map(item -> {
                    SpuDetailVO.Attribute attribute = new SpuDetailVO.Attribute();
                    BeanUtil.copyProperties(item, attribute);
                    return attribute;
                }).collect(Collectors.toList());
        spuDetailVO.setAttributeList(attributeList);


        // 商品规格列表
        List<SpuAttributeValue> specSourceList = spuAttributeService.list(new LambdaQueryWrapper<SpuAttributeValue>()
                .eq(SpuAttributeValue::getType, AttributeTypeEnum.SPEC.getValue())
                .eq(SpuAttributeValue::getSpuId, spuId)
                .select(SpuAttributeValue::getId, SpuAttributeValue::getName, SpuAttributeValue::getValue));

        List<SpuDetailVO.Specification> specList = new ArrayList<>();
        // 规格Map [key:"颜色",value:[{id:1,value:"黑"},{id:2,value:"白"}]]
        Map<String, List<SpuAttributeValue>> specValueMap = specSourceList.stream().collect(Collectors.groupingBy(SpuAttributeValue::getName));

        for (Map.Entry<String, List<SpuAttributeValue>> entry : specValueMap.entrySet()) {
            String specName = entry.getKey();
            List<SpuAttributeValue> specValueSourceList = entry.getValue();

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
        List<Sku> skuSourceList = skuService.list(new LambdaQueryWrapper<Sku>().eq(Sku::getSpuId, spuId));
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
            vo.setImgUrl(goodsInfo.getAlbum() != null ? goodsInfo.getAlbum().get(0) : null);
        }
        return spuDetailVO;
    }


    /**
     * 获取商品详情
     *
     * @param spuId 商品ID
     * @return 商品详情
     */
    @Override
    public PmsSpuDetailVO getSpuDetail(Long spuId) {
        PmsSpuDetailVO pmsSpuDetailVO = new PmsSpuDetailVO();

        Spu entity = this.getById(spuId);
        Assert.isTrue(entity != null, "商品不存在");

        BeanUtil.copyProperties(entity, pmsSpuDetailVO, "album");
        pmsSpuDetailVO.setSubImgUrls(entity.getAlbum());

        // 商品属性列表
        List<SpuAttributeValue> attrList = spuAttributeService.list(new LambdaQueryWrapper<SpuAttributeValue>()
                .eq(SpuAttributeValue::getSpuId, spuId)
                .eq(SpuAttributeValue::getType, AttributeTypeEnum.ATTR.getValue()));
        pmsSpuDetailVO.setAttrList(attrList);

        // 商品规格列表
        List<SpuAttributeValue> specList = spuAttributeService.list(new LambdaQueryWrapper<SpuAttributeValue>()
                .eq(SpuAttributeValue::getSpuId, spuId)
                .eq(SpuAttributeValue::getType, AttributeTypeEnum.SPEC.getValue()));
        pmsSpuDetailVO.setSpecList(specList);

        // 商品SKU列表
        List<Sku> skuList = skuService.list(new LambdaQueryWrapper<Sku>().eq(Sku::getSpuId, spuId));
        pmsSpuDetailVO.setSkuList(skuList);
        return pmsSpuDetailVO;

    }

    /**
     * 添加商品
     *
     * @param formData 商品表单
     * @return 是否成功
     */
    @Override
    @Transactional
    public boolean saveSpu(SpuForm formData) {

        Spu entity = spuConverter.form2Entity(formData);

        boolean result = this.saveOrUpdate(entity);
        if (result) {
            Long spuId = entity.getId();

            List<SpuForm.Image> galleryImages = formData.getGalleryImages();
            spuImageService.saveSpuImages(spuId, galleryImages);

            List<SpuForm.Attribute> attributes = formData.getAttributes();
            spuAttributeService.saveSpuAttributes(spuId, attributes);

            List<SpuForm.SpuSpec> specList = formData.get();
            spuSpecService.saveSpuSpecs(spuId, specList);

            List<SpuForm.Sku> skuList = formData.getSkuList();
            skuService.saveSkus(spuId, skuList);


            // 保存属性
            List<PmsSpuAttributeForm> attrList = formData.getAttrList();
            this.saveSpuAttrs(spuId, attrList);
            // 保存规格
            List<PmsSpuAttributeForm> specList = formData.getSpecList();
            Map<String, Long> tempWithNewSpecIdMap = this.saveSpuSpecs(spuId, specList);
            // 保存SKU
            List<Sku> skuList = formData.getSkuList();
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
     * @return 是否成功
     */
    @Transactional
    @Override
    public boolean updateSpuById(Long spuId, SpuForm formData) {

        Spu entity = spuConverter.form2Entity(formData);

        boolean result = this.updateById(entity);
        if (result) {

            // 属性保存
            List<PmsSpuAttributeForm> attrList = formData.getAttrList();
            this.saveSpuAttrs(spuId, attrList);

            // 保存商品规格值
            List<PmsSpuAttributeForm> specList = formData.getSpecList();
            Map<String, Long> specTempIdIdMap = this.saveSpuSpecs(spuId, specList);

            // SKU保存
            List<Sku> skuList = formData.getSkuList();
            this.saveSku(spuId, skuList, specTempIdIdMap);
        }

        return result;
    }


    /**
     * 删除商品
     *
     * @param ids 商品ID，多个以英文逗号(,)分割
     * @return 是否成功
     */
    @Override
    @Transactional
    public boolean removeBySpuIds(String ids) {

        String[] spuIds = ids.split(",");

        for (String spuId : spuIds) {
            skuService.remove(new LambdaQueryWrapper<Sku>().eq(Sku::getSpuId, spuId));
            // 规格
            spuAttributeService.remove(new LambdaQueryWrapper<SpuAttributeValue>().eq(SpuAttributeValue::getSpuId, spuId));
            // 属性
            spuAttributeService.remove(new LambdaQueryWrapper<SpuAttributeValue>().eq(SpuAttributeValue::getSpuId, spuId));
            // SPU
            this.removeById(spuId);
        }
        // 无异常直接返回true
        return true;
    }

    /**
     * 获取商品秒杀接口
     *
     * @return 商品秒杀列表
     */
    @Override
    public List<SeckillingSpuVO> listSeckillingSpu() {
        List<Spu> entities = this.list(new LambdaQueryWrapper<Spu>()
                .select(Spu::getId, Spu::getName, Spu::getImgUrl, Spu::getPrice)
                .orderByDesc(Spu::getCreateTime)
        );
        return spuConverter.entity2SeckillingVO(entities);
    }


    /**
     * 保存SKU，需要替换提交表单中的临时规格ID
     *
     * @param spuId           商品ID
     * @param skuList         SKU列表
     * @param specTempIdIdMap 临时规格ID和持久化数据库得到的规格ID的映射
     * @return 是否成功
     */
    private boolean saveSku(Long spuId, List<Sku> skuList, Map<String, Long> specTempIdIdMap) {

        // 删除SKU
        List<Long> formSkuIds = skuList.stream().map(Sku::getId).toList();

        List<Long> dbSkuIds = skuService.list(new LambdaQueryWrapper<Sku>().eq(Sku::getSpuId, spuId)
                        .select(Sku::getId)).stream().map(Sku::getId)
                .toList();

        List<Long> removeSkuIds = dbSkuIds.stream().filter(dbSkuId -> !formSkuIds.contains(dbSkuId)).collect(Collectors.toList());

        if (CollectionUtil.isNotEmpty(removeSkuIds)) {
            skuService.removeByIds(removeSkuIds);
        }

        // 新增/修改SKU
        List<Sku> pmsSkuList = skuList.stream().map(sku -> {
            // 临时规格ID转换
            String specIds = Arrays.stream(sku.getSpecIds().split("\\|"))
                    .map(specId -> specId.startsWith(ProductConstants.SPEC_TEMP_ID_PREFIX) ? specTempIdIdMap.get(specId) + "" : specId)
                    .collect(Collectors.joining("_"));
            sku.setSpecIds(specIds);
            sku.setSpuId(spuId);
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
                .toList();
        // 1.2 获取原商品属性ID集合
        List<Long> originAttrIds = spuAttributeService.list(new LambdaQueryWrapper<SpuAttributeValue>()
                        .eq(SpuAttributeValue::getSpuId, spuId).eq(SpuAttributeValue::getType, AttributeTypeEnum.ATTR.getValue())
                        .select(SpuAttributeValue::getId))
                .stream()
                .map(SpuAttributeValue::getId)
                .toList();
        // 1.3 需要删除的商品属性：原商品属性-此次提交保留的属性
        List<Long> removeAttrValIds = originAttrIds.stream()
                .filter(id -> !retainAttrIds.contains(id))
                .collect(Collectors.toList());
        if (CollectionUtil.isNotEmpty(removeAttrValIds)) {
            spuAttributeService.removeByIds(removeAttrValIds);
        }

        // 新增或修改商品属性
        List<SpuAttributeValue> entities = attrList.stream().map(item -> {
            SpuAttributeValue entity = spuAttributeConverter.form2Entity(item);
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
                .toList();

        // 1.2 原商品规格
        List<Long> originSpuSpecIds = spuAttributeService.list(new LambdaQueryWrapper<SpuAttributeValue>()
                        .eq(SpuAttributeValue::getSpuId, spuId)
                        .eq(SpuAttributeValue::getType, AttributeTypeEnum.SPEC.getValue())
                        .select(SpuAttributeValue::getId))
                .stream().map(SpuAttributeValue::getId)
                .toList();

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
                SpuAttributeValue entity = spuAttributeConverter.form2Entity(item);
                entity.setSpuId(spuId);
                entity.setType(AttributeTypeEnum.SPEC.getValue());
                spuAttributeService.save(entity);
                tempWithNewSpecIdMap.put(item.getId(), entity.getId());
            });
        }

        //  3. 【修改】此次提交的需要修改的商品规格
        List<SpuAttributeValue> spuAttributeValueList = specList.stream()
                .filter(item -> !item.getId().startsWith(ProductConstants.SPEC_TEMP_ID_PREFIX))
                .map(item -> {
                    SpuAttributeValue entity = spuAttributeConverter.form2Entity(item);
                    entity.setId(Convert.toLong(item.getId()));
                    entity.setSpuId(spuId);
                    entity.setType(AttributeTypeEnum.SPEC.getValue());
                    return entity;
                }).collect(Collectors.toList());
        if (CollectionUtil.isNotEmpty(spuAttributeValueList)) {
            spuAttributeService.updateBatchById(spuAttributeValueList);
        }
        return tempWithNewSpecIdMap;
    }
}

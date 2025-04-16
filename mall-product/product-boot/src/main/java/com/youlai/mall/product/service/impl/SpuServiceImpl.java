package com.youlai.mall.product.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.mall.product.converter.SkuConverter;
import com.youlai.mall.product.converter.SkuSpecConverter;
import com.youlai.mall.product.converter.SpuAttrConverter;
import com.youlai.mall.product.converter.SpuConverter;
import com.youlai.mall.product.mapper.SpuMapper;
import com.youlai.mall.product.model.bo.SkuBO;
import com.youlai.mall.product.model.entity.*;
import com.youlai.mall.product.model.form.SpuForm;
import com.youlai.mall.product.model.query.SpuPageQuery;
import com.youlai.mall.product.model.vo.SpuPageVO;
import com.youlai.mall.product.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

/**
 * 商品业务实现类
 *
 * @author Ray.Hao
 * @since 2021/8/8
 */
@Service
@RequiredArgsConstructor
public class SpuServiceImpl extends ServiceImpl<SpuMapper, SpuEntity> implements SpuService {

    private final SpuConverter spuConverter;
    private final SpuAttrConverter spuAttrConverter;
    private final SkuConverter skuConverter;
    private final SkuService skuService;
    private final SpuImageService spuImageService;
    private final SpuAttrService spuAttrService;
    private final SkuSpecConverter skuSpecConverter;

    /**
     * Admin-商品分页列表
     *
     * @param queryParams 查询参数
     * @return 商品分页列表 IPage<PmsSpuPageVO>
     */
    @Override
    public IPage<SpuPageVO> getSpuPage(SpuPageQuery queryParams) {
        Page<SpuPageVO> page = new Page<>(queryParams.getPageNum(), queryParams.getPageSize());
        List<SpuPageVO> list = this.baseMapper.getSpuPage(page, queryParams);
        page.setRecords(list);
        return page;
    }

    /**
     * 保存商品
     *
     * @param formData 商品表单
     * @return 是否成功
     */
    @Override
    public boolean saveSpu(SpuForm formData) {

        SpuEntity entity = spuConverter.toEntity(formData);
        boolean result = this.saveOrUpdate(entity);
        if (result) {
            Long spuId = entity.getId();
            // 保存商品图片
            List<String> imgList = formData.getImgList();
            spuImageService.saveSpuImages(spuId, imgList);
            // 保存商品属性
            List<SpuForm.AttrValue> attrList = formData.getAttrList();
            spuAttrService.saveSpuAttrs(spuId, attrList);
            // 保存 SKU
            List<SpuForm.Sku> skuList = formData.getSkuList();
            skuService.saveSkus(spuId, skuList);
        }
        // 无异常返回true
        return result;
    }


    /**
     * 获取商品表单数据
     *
     * @param spuId SPU ID
     * @return 商品表单数据
     */
    @Override
    public SpuForm getSpuForm(Long spuId) {
        SpuEntity spuEntity = this.getById(spuId);
        Assert.isTrue(spuEntity != null, "商品不存在");

        SpuForm spuForm = spuConverter.toForm(spuEntity);

        // 商品图片
        List<String> imageUrls = spuImageService.list(new LambdaQueryWrapper<SpuImageEntity>()
                        .eq(SpuImageEntity::getSpuId, spuId)
                        .select(SpuImageEntity::getImgUrl)
                ).stream().map(SpuImageEntity::getImgUrl).toList();
        spuForm.setImgList(imageUrls);

        // 属性列表
        List<SpuForm.AttrValue> attrList=  spuAttrConverter.toSpuFormAttr(spuAttrService.listAttrsBySpuId(spuId));
        spuForm.setAttrList(attrList);

        // SKU 列表
        List<SkuBO> skuList = skuService.listSkusBySpuId(spuId);
        if(CollectionUtil.isNotEmpty(skuList)){
            spuForm.setSkuList(skuList.stream().map(skuBO -> {
                SpuForm.Sku sku =  skuConverter.toSpuFormSku(skuBO);
                // 规格列表
                   sku.setSpecList(skuSpecConverter.toSkuFormSpec(skuBO.getSpecList()));
                return sku;
            }).toList());
        }

        return spuForm;
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
            skuService.remove(new LambdaQueryWrapper<SkuEntity>().eq(SkuEntity::getSpuId, spuId));
            // 规格
            spuAttrService.remove(new LambdaQueryWrapper<SpuAttrEntity>().eq(SpuAttrEntity::getSpuId, spuId));
            // 参数
            spuAttrService.remove(new LambdaQueryWrapper<SpuAttrEntity>().eq(SpuAttrEntity::getSpuId, spuId));
            // SPU
            this.removeById(spuId);
        }
        // 无异常直接返回true
        return true;
    }


}

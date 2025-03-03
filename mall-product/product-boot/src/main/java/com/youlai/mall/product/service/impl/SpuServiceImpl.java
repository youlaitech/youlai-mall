package com.youlai.mall.product.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class SpuServiceImpl extends ServiceImpl<SpuMapper, Spu> implements SpuService {

    private final SpuConverter spuConverter;
    private final SkuService skuService;
    private final SpuImageService spuImageService;
    private final SpuDetailService spuDetailService;
    private final SpuAttrValueService spuAttrValueService;

    /**
     * Admin-商品分页列表
     *
     * @param queryParams 查询参数
     * @return 商品分页列表 IPage<PmsSpuPageVO>
     */
    @Override
    public IPage<SpuPageVO> listPagedSpu(SpuPageQuery queryParams) {
        Page<SpuPageVO> page = new Page<>(queryParams.getPageNum(), queryParams.getPageSize());
        List<SpuPageVO> list = this.baseMapper.listPagedSpu(page, queryParams);
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

        Spu entity = spuConverter.toEntity(formData);
        boolean result = this.saveOrUpdate(entity);
        if (result) {
            Long spuId = entity.getId();
            // 保存商品详情
            spuDetailService.saveSpuDetail(spuId, formData.getDetail());
            // 保存商品图片
            List<SpuForm.Image> imgList = formData.getImgList();
            spuImageService.saveSpuImages(spuId, imgList);
            // 保存商品属性
            List<SpuForm.AttrValue> attrValues = formData.getAttrValues();
            spuAttrValueService.saveAttributeValues(spuId, attrValues);
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
        Spu spu = this.getById(spuId);
        Assert.isTrue(spu != null, "商品不存在");

        SpuForm spuForm = spuConverter.toForm(spu);

        // 商品图片
        List<SpuForm.Image> imageList = spuImageService.list(new LambdaQueryWrapper<SpuImage>()
                        .eq(SpuImage::getSpuId, spuId))
                .stream().map(item -> {
                    SpuForm.Image image = new SpuForm.Image();
                    BeanUtil.copyProperties(item, image);
                    return image;
                }).toList();
        spuForm.setImgList(imageList);

        // 商品详情
        SpuDetail spuDetail = spuDetailService.getOne(new LambdaQueryWrapper<SpuDetail>()
                .eq(SpuDetail::getSpuId, spuId)
        );
        if (spuDetail != null) {
            spuForm.setDetail(spuDetail.getDetail());
        }

        // 商品属性
        List<SpuForm.AttrValue> attrValues = spuAttrValueService.list(new LambdaQueryWrapper<SpuAttr>()
                        .eq(SpuAttr::getSpuId, spuId)
                        .select(SpuAttr::getAttrId, SpuAttr::getAttrValue)
                )
                .stream()
                .map(item -> {
                    SpuForm.AttrValue attrValue = new SpuForm.AttrValue();
                    BeanUtil.copyProperties(item, attrValue);
                    return attrValue;
                }).toList();
        spuForm.setAttrValues(attrValues);

        // 商品SKU
        List<SkuBO> skuList = skuService.listSkusBySpuId(spuId);
        spuForm.setSkuList(skuList.stream().map(entity -> {
            SpuForm.Sku sku = new SpuForm.Sku();
            BeanUtil.copyProperties(entity, sku);
            sku.setSpecValues(entity.getSpecValues().stream().map(attrValue -> {
                SpuForm.SpecValue specValue = new SpuForm.SpecValue();
                specValue.setSpecId(attrValue.getSpecId());
                specValue.setSpecValue(attrValue.getSpecValue());
                return specValue;
            }).toList());
            return sku;
        }).toList());

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
            skuService.remove(new LambdaQueryWrapper<Sku>().eq(Sku::getSpuId, spuId));
            // 规格
            spuAttrValueService.remove(new LambdaQueryWrapper<SpuAttr>().eq(SpuAttr::getSpuId, spuId));
            // 参数
            spuAttrValueService.remove(new LambdaQueryWrapper<SpuAttr>().eq(SpuAttr::getSpuId, spuId));
            // SPU
            this.removeById(spuId);
        }
        // 无异常直接返回true
        return true;
    }


}

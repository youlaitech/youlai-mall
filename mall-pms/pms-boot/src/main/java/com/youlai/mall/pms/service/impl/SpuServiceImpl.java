package com.youlai.mall.pms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.mall.pms.converter.SpuConverter;
import com.youlai.mall.pms.mapper.SpuMapper;
import com.youlai.mall.pms.model.entity.*;
import com.youlai.mall.pms.model.form.SpuForm;
import com.youlai.mall.pms.model.query.SpuPageQuery;
import com.youlai.mall.pms.model.vo.PmsSpuPageVO;
import com.youlai.mall.pms.model.vo.SeckillingSpuVO;
import com.youlai.mall.pms.model.vo.SpuDetailVO;
import com.youlai.mall.pms.model.vo.SpuPageVO;
import com.youlai.mall.pms.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    private final SpuImageService spuImageService;
    private final SkuSpecValueService skuSpecValueService;

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

        SpuDetailVO spuDetailVO = new SpuDetailVO();

        return spuDetailVO;
    }

    /**
     * 保存商品
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
            // 保存商品图片
            List<SpuForm.Image> galleryImageList = formData.getGalleryImageList();
            spuImageService.saveSpuImages(spuId, galleryImageList);
            // 保存商品属性
            List<SpuForm.Attribute> attributeList = formData.getAttributeList();
            spuAttributeService.saveSpuAttributes(spuId, attributeList);
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

        SpuForm spuForm = new SpuForm();
        BeanUtil.copyProperties(spu, spuForm);

        // 商品图片
        List<SpuForm.Image> galleryImageList = spuImageService.list(new LambdaQueryWrapper<SpuImage>()
                        .eq(SpuImage::getSpuId, spuId))
                .stream().map(item -> {
                    SpuForm.Image image = new SpuForm.Image();
                    BeanUtil.copyProperties(item, image);
                    return image;
                }).collect(Collectors.toList());
        spuForm.setGalleryImageList(galleryImageList);

        // 商品属性
        List<SpuForm.Attribute> attributeList = spuAttributeService.list(new LambdaQueryWrapper<SpuAttributeValue>()
                        .eq(SpuAttributeValue::getSpuId, spuId))
                .stream().map(item -> {
                    SpuForm.Attribute attribute = new SpuForm.Attribute();
                    BeanUtil.copyProperties(item, attribute);
                    return attribute;
                }).collect(Collectors.toList());
        spuForm.setAttributeList(attributeList);

        // 商品SKU
        List<SpuForm.Sku> skuList = skuService.list(new LambdaQueryWrapper<Sku>().eq(Sku::getSpuId, spuId))
                .stream().map(item -> {
                    SpuForm.Sku sku = new SpuForm.Sku();
                    BeanUtil.copyProperties(item, sku);

                    Long skuId = item.getId();
                    // 获取SKU规格值
                    List<SpuForm.Attribute> skuSpecList = skuSpecValueService.list(new LambdaQueryWrapper<SkuSpecValue>()
                                    .eq(SkuSpecValue::getSkuId, skuId))
                            .stream().map(skuSpec -> {
                                SpuForm.Attribute attribute = new SpuForm.Attribute();
                                BeanUtil.copyProperties(skuSpec, attribute);
                                return attribute;
                            }).collect(Collectors.toList());

                    sku.setSpecList(skuSpecList);
                    return sku;
                }).collect(Collectors.toList());
        spuForm.setSkuList(skuList);

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
            spuAttributeService.remove(new LambdaQueryWrapper<SpuAttributeValue>().eq(SpuAttributeValue::getSpuId, spuId));
            // 参数
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
                .select(Spu::getId, Spu::getName, Spu::getImgUrl)
                .orderByDesc(Spu::getCreateTime)
        );
        return spuConverter.entity2SeckillingVO(entities);
    }


}

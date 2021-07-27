package com.youlai.mall.pms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.mall.pms.common.enums.GoodsAttrTypeEnum;
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
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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


    @Override
    public boolean updateGoods(GoodsFormDTO goodsFormDTO) {
       /* SpuDTO SpuDTO = goodsFormDTO.getSpu();

        List<PmsSpuAttributeValue> attrValues = goodsFormDTO.getAttrs();
        List<PmsSpuAttributeValue> specs = goodsFormDTO.getAttrs();
        List<PmsSku> skuList = goodsFormDTO.getSkus();

        // spu保存
        PmsSpu spu = new PmsSpu();
        BeanUtil.copyProperties(SpuDTO, spu);
        if (SpuDTO.getPics() != null) {
            String pics = JSONUtil.toJsonStr(SpuDTO.getPics());
            spu.setAlbum(pics);
        }
        this.updateById(spu);

        // 属性保存
        Optional.ofNullable(attrValues).ifPresent(list -> {
            list.forEach(item -> item.setSpuId(spu.getId()));

            // 删除此次保存删除的
            List<Long> ids = list.stream().map(item -> item.getId()).collect(Collectors.toList());
            List<Long> dbIds = iPmsSpuAttributeValueService.list(new LambdaQueryWrapper<PmsSpuAttributeValue>().eq(PmsSpuAttributeValue::getSpuId, spu.getId())
                    .select(PmsSpuAttributeValue::getId))
                    .stream()
                    .map(item -> item.getId())
                    .collect(Collectors.toList());
            List<Long> removeIds = dbIds.stream().filter(id -> !ids.contains(id)).collect(Collectors.toList());
            iPmsSpuAttributeValueService.removeByIds(removeIds);

            iPmsSpuAttributeValueService.saveOrUpdateBatch(list);
        });

        // 规格值保存
        Optional.ofNullable(specs).ifPresent(list -> {
            list.forEach(item -> item.setSpuId(spu.getId()));

            // 删除此次保存删除的
            List<Long> ids = list.stream().map(item -> item.getId()).collect(Collectors.toList());
            List<Long> dbIds = iPmsSpuAttributeValueService.list(new LambdaQueryWrapper<PmsSpuAttributeValue>().eq(PmsSpuAttributeValue::getSpuId, spu.getId())
                    .select(PmsSpuAttributeValue::getId))
                    .stream()
                    .map(item -> item.getId())
                    .collect(Collectors.toList());
            List<Long> removeIds = dbIds.stream().filter(id -> !ids.contains(id)).collect(Collectors.toList());
            iPmsSpuAttributeValueService.removeByIds(removeIds);

            iPmsSpuAttributeValueService.saveOrUpdateBatch(list);
        });

        // sku保存
        Optional.ofNullable(skuList).ifPresent(list -> {
            list.forEach(item -> item.setSpuId(spu.getId()));

            // 删除此次保存删除的
            List<Long> ids = list.stream().map(item -> item.getId()).collect(Collectors.toList());
            List<Long> dbIds = iPmsSkuService.list(new LambdaQueryWrapper<PmsSku>().eq(PmsSku::getSpuId, spu.getId())
                    .select(PmsSku::getId))
                    .stream()
                    .map(item -> item.getId())
                    .collect(Collectors.toList());
            List<Long> removeIds = dbIds.stream().filter(id -> !ids.contains(id)).collect(Collectors.toList());
            iPmsSkuService.removeByIds(removeIds);

            iPmsSkuService.saveOrUpdateBatch(skuList);
        });*/
        return true;
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
                .eq(PmsSpuAttributeValue::getType, GoodsAttrTypeEnum.ATTRIBUTE.getValue())
        );
        goodsDetailVO.setAttrList(attrList);

        // 商品规格列表
        List<PmsSpuAttributeValue> specList = iPmsSpuAttributeValueService.list(new LambdaQueryWrapper<PmsSpuAttributeValue>()
                .eq(PmsSpuAttributeValue::getSpuId, id)
                .eq(PmsSpuAttributeValue::getType, GoodsAttrTypeEnum.SPECIFICATION.getValue())
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

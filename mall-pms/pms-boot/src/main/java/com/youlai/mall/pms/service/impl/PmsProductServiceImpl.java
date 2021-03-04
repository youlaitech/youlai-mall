package com.youlai.mall.pms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.mall.pms.bo.app.ProductBO;
import com.youlai.mall.pms.mapper.PmsProductMapper;
import com.youlai.mall.pms.pojo.domain.*;
import com.youlai.mall.pms.pojo.dto.SpuDTO;
import com.youlai.mall.pms.service.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author haoxr
 * @date 2020-11-06
 */
@Service
@AllArgsConstructor
public class PmsProductServiceImpl extends ServiceImpl<PmsProductMapper, PmsProduct> implements IPmsProductService {

    private IPmsInventoryService iPmsInventoryService;
    private IPmsProductAttrValueService iPmsProductAttrValueService;
    private IPmsProductSpecValueService iPmsProductSpecValueService;
    private IPmsCategorySpecService iPmsCategorySpecService;


    @Override
    public IPage<PmsProduct> list(Page<PmsProduct> page, PmsProduct spu) {
        List<PmsProduct> list = this.baseMapper.list(page, spu);
        page.setRecords(list);
        return page;
    }

    @Override
    @Transactional
    public boolean add(com.youlai.mall.pms.bo.admin.ProductBO spuBO) {
        SpuDTO spuDTO = spuBO.getSpu();
        List<PmsProductAttrValue> attrs = spuBO.getAttrs();
        List<PmsProductSpecValue> specs = spuBO.getSpecs();
        List<PmsInventory> skuList = spuBO.getSkuList();

        // spu保存
        PmsProduct spu = new PmsProduct();
        BeanUtil.copyProperties(spuDTO, spu);
        if (spuDTO.getPicUrls() != null) {
            String picUrls = JSONUtil.toJsonStr(spuDTO.getPicUrls());
            spu.setPicUrls(picUrls);
        }
        this.save(spu);

        // 属性保存
        Optional.ofNullable(attrs).ifPresent(list -> {
            list.forEach(item -> item.setProductId(spu.getId()));
            iPmsProductAttrValueService.saveBatch(list);
        });

        // 规格保存
        Optional.ofNullable(specs).ifPresent(list -> {
            list.forEach(item -> item.setProductId(spu.getId()));
            iPmsProductSpecValueService.saveBatch(list);
        });

        // sku保存
        Optional.ofNullable(skuList).ifPresent(list -> {
            list.forEach(item -> item.setProductId(spu.getId()));
            iPmsInventoryService.saveBatch(skuList);
        });

        return true;
    }

    @Override
    public com.youlai.mall.pms.bo.admin.ProductBO getBySpuId(Long id) {
        // spu
        SpuDTO spuDTO = new SpuDTO();
        PmsProduct spu = this.getById(id);
        BeanUtil.copyProperties(spu, spuDTO);

        if (StrUtil.isNotBlank(spu.getPicUrls())) {
            // spu专辑图片转换处理 json字符串 -> List
            List<String> pics = JSONUtil.toList(JSONUtil.parseArray(spu.getPicUrls()), String.class);
            spuDTO.setPicUrls(pics);
        }

        // 属性
        List<PmsProductAttrValue> attrs = iPmsProductAttrValueService.
                list(new LambdaQueryWrapper<PmsProductAttrValue>().eq(PmsProductAttrValue::getProductId, id));
        // 规格
        List<PmsProductSpecValue> specs = iPmsProductSpecValueService.list(new LambdaQueryWrapper<PmsProductSpecValue>().eq(PmsProductSpecValue::getProductId, id));
        // sku
        List<PmsInventory> skuList = iPmsInventoryService.list(new LambdaQueryWrapper<PmsInventory>().eq(PmsInventory::getProductId, id));

        // 组合
        com.youlai.mall.pms.bo.admin.ProductBO spuBO = new com.youlai.mall.pms.bo.admin.ProductBO(spuDTO, attrs, specs, skuList);
        return spuBO;
    }


    @Override
    public boolean updateById(com.youlai.mall.pms.bo.admin.ProductBO spuBO) {
        SpuDTO spuDTO = spuBO.getSpu();

        List<PmsProductAttrValue> attrs = spuBO.getAttrs();
        List<PmsProductSpecValue> specs = spuBO.getSpecs();
        List<PmsInventory> skuList = spuBO.getSkuList();

        // spu保存
        PmsProduct spu = new PmsProduct();
        BeanUtil.copyProperties(spuDTO, spu);
        if (spuDTO.getPicUrls() != null) {
            String picUrls = JSONUtil.toJsonStr(spuDTO.getPicUrls());
            spu.setPicUrls(picUrls);
        }
        this.updateById(spu);

        // 属性保存
        Optional.ofNullable(attrs).ifPresent(list -> {
            list.forEach(item -> item.setProductId(spu.getId()));

            // 删除此次保存删除的
            List<Long> ids = list.stream().map(item -> item.getId()).collect(Collectors.toList());
            List<Long> dbIds = iPmsProductAttrValueService.list(new LambdaQueryWrapper<PmsProductAttrValue>().eq(PmsProductAttrValue::getProductId, spu.getId())
                    .select(PmsProductAttrValue::getId))
                    .stream()
                    .map(item -> item.getId())
                    .collect(Collectors.toList());
            List<Long> removeIds = dbIds.stream().filter(id -> !ids.contains(id)).collect(Collectors.toList());
            iPmsProductAttrValueService.removeByIds(removeIds);

            iPmsProductAttrValueService.saveOrUpdateBatch(list);
        });

        // 规格保存
        Optional.ofNullable(specs).ifPresent(list -> {
            list.forEach(item -> item.setProductId(spu.getId()));

            // 删除此次保存删除的
            List<Long> ids = list.stream().map(item -> item.getId()).collect(Collectors.toList());
            List<Long> dbIds = iPmsProductSpecValueService.list(new LambdaQueryWrapper<PmsProductSpecValue>().eq(PmsProductSpecValue::getProductId, spu.getId())
                    .select(PmsProductSpecValue::getId))
                    .stream()
                    .map(item -> item.getId())
                    .collect(Collectors.toList());
            List<Long> removeIds = dbIds.stream().filter(id -> !ids.contains(id)).collect(Collectors.toList());
            iPmsProductSpecValueService.removeByIds(removeIds);

            iPmsProductSpecValueService.saveOrUpdateBatch(list);
        });

        // sku保存
        Optional.ofNullable(skuList).ifPresent(list -> {
            list.forEach(item -> item.setProductId(spu.getId()));

            // 删除此次保存删除的
            List<Long> ids = list.stream().map(item -> item.getId()).collect(Collectors.toList());
            List<Long> dbIds = iPmsInventoryService.list(new LambdaQueryWrapper<PmsInventory>().eq(PmsInventory::getProductId, spu.getId())
                    .select(PmsInventory::getId))
                    .stream()
                    .map(item -> item.getId())
                    .collect(Collectors.toList());
            List<Long> removeIds = dbIds.stream().filter(id -> !ids.contains(id)).collect(Collectors.toList());
            iPmsInventoryService.removeByIds(removeIds);

            iPmsInventoryService.saveOrUpdateBatch(skuList);
        });
        return true;
    }

    @Override
    public boolean removeBySpuIds(List<Long> spuIds) {
        Optional.ofNullable(spuIds).ifPresent(
                ids -> ids.forEach(spuId -> {
                    // sku
                    iPmsInventoryService.remove(new LambdaQueryWrapper<PmsInventory>().eq(PmsInventory::getProductId, spuId));
                    // 规格
                    iPmsProductSpecValueService.remove(new LambdaQueryWrapper<PmsProductSpecValue>().eq(PmsProductSpecValue::getId, spuId));
                    // 属性
                    iPmsProductAttrValueService.remove(new LambdaQueryWrapper<PmsProductAttrValue>().eq(PmsProductAttrValue::getProductId, spuId));
                    // spu
                    this.removeById(spuId);
                })
        );
        return true;
    }

    @Override
    public ProductBO getProductByIdForApp(Long spuId) {
        // spu
        PmsProduct spu = this.getById(spuId);
        SpuDTO spuDTO = new SpuDTO();
        BeanUtil.copyProperties(spu, spuDTO);
        if (StrUtil.isNotBlank(spu.getPicUrls())) {
            // spu专辑图片转换处理 json字符串 -> List
            List<String> pics = JSONUtil.toList(JSONUtil.parseArray(spu.getPicUrls()), String.class);
            spuDTO.setPicUrls(pics);
        }
        // 属性
        List<PmsProductAttrValue> attrs = iPmsProductAttrValueService.list(
                new LambdaQueryWrapper<PmsProductAttrValue>(
                ).eq(PmsProductAttrValue::getProductId, spuId)
        );

        // 规格
        List<PmsCategorySpec> specs = iPmsCategorySpecService.listBySpuId(spuId);

        // sku
        List<PmsInventory> skuList = iPmsInventoryService.list(new LambdaQueryWrapper<PmsInventory>().eq(PmsInventory::getProductId, spuId));

        ProductBO product = new ProductBO(spuDTO, attrs, specs, skuList);
        return product;
    }
}

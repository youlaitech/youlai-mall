package com.youlai.mall.pms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.mall.pms.bo.AppProductBO;
import com.youlai.mall.pms.bo.ProductBO;
import com.youlai.mall.pms.mapper.PmsSpuMapper;
import com.youlai.mall.pms.pojo.*;
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
public class PmsSpuServiceImpl extends ServiceImpl<PmsSpuMapper, PmsSpu> implements IPmsSpuService {

    private IPmsSkuService iPmsSkuService;
    private IPmsSpuAttrValueService iPmsSpuAttrValueService;
    private IPmsSpuSpecValueService iPmsSpuSpecValueService;
    private IPmsSpecService iPmsSpecService;


    @Override
    public IPage<PmsSpu> list(Page<PmsSpu> page, PmsSpu spu) {
        List<PmsSpu> list = this.baseMapper.list(page, spu);
        page.setRecords(list);
        return page;
    }

    @Override
    @Transactional
    public boolean add(ProductBO spuBO) {
        SpuDTO spuDTO = spuBO.getSpu();
        List<PmsSpuAttrValue> attrs = spuBO.getAttrs();
        List<PmsSpuSpecValue> specs = spuBO.getSpecs();
        List<PmsSku> skuList = spuBO.getSkuList();

        // spu保存
        PmsSpu spu = new PmsSpu();
        BeanUtil.copyProperties(spuDTO, spu);
        if (spuDTO.getPicUrls() != null) {
            String picUrls = JSONUtil.toJsonStr(spuDTO.getPicUrls());
            spu.setPicUrls(picUrls);
        }
        this.save(spu);

        // 属性保存
        Optional.ofNullable(attrs).ifPresent(list -> {
            list.forEach(item -> item.setSpuId(spu.getId()));
            iPmsSpuAttrValueService.saveBatch(list);
        });

        // 规格保存
        Optional.ofNullable(specs).ifPresent(list -> {
            list.forEach(item -> item.setSpuId(spu.getId()));
            iPmsSpuSpecValueService.saveBatch(list);
        });

        // sku保存
        Optional.ofNullable(skuList).ifPresent(list -> {
            list.forEach(item -> item.setSpuId(spu.getId()));
            iPmsSkuService.saveBatch(skuList);
        });

        return true;
    }

    @Override
    public ProductBO getBySpuId(Long id) {
        // spu
        SpuDTO spuDTO = new SpuDTO();
        PmsSpu spu = this.getById(id);
        BeanUtil.copyProperties(spu, spuDTO);

        if (StrUtil.isNotBlank(spu.getPicUrls())) {
            // spu专辑图片转换处理 json字符串 -> List
            List<String> pics = JSONUtil.toList(JSONUtil.parseArray(spu.getPicUrls()), String.class);
            spuDTO.setPicUrls(pics);
        }

        // 属性
        List<PmsSpuAttrValue> attrs = iPmsSpuAttrValueService.
                list(new LambdaQueryWrapper<PmsSpuAttrValue>().eq(PmsSpuAttrValue::getSpuId, id));
        // 规格
        List<PmsSpuSpecValue> specs = iPmsSpuSpecValueService.list(new LambdaQueryWrapper<PmsSpuSpecValue>().eq(PmsSpuSpecValue::getSpuId, id));
        // sku
        List<PmsSku> skuList = iPmsSkuService.list(new LambdaQueryWrapper<PmsSku>().eq(PmsSku::getSpuId, id));

        // 组合
        ProductBO spuBO = new ProductBO(spuDTO, attrs, specs, skuList);
        return spuBO;
    }


    @Override
    public boolean updateById(ProductBO spuBO) {
        SpuDTO spuDTO = spuBO.getSpu();

        List<PmsSpuAttrValue> attrs = spuBO.getAttrs();
        List<PmsSpuSpecValue> specs = spuBO.getSpecs();
        List<PmsSku> skuList = spuBO.getSkuList();

        // spu保存
        PmsSpu spu = new PmsSpu();
        Long spuId = spu.getId();
        BeanUtil.copyProperties(spuDTO, spu);
        if (spuDTO.getPicUrls() != null) {
            String picUrls = JSONUtil.toJsonStr(spuDTO.getPicUrls());
            spu.setPicUrls(picUrls);
        }
        this.updateById(spu);

        // 属性保存
        Optional.ofNullable(attrs).ifPresent(list -> {
            list.forEach(item -> item.setSpuId(spu.getId()));

            // 删除此次保存删除的
            List<Long> ids = list.stream().map(item -> item.getId()).collect(Collectors.toList());
            List<Long> dbIds = iPmsSpuAttrValueService.list(new LambdaQueryWrapper<PmsSpuAttrValue>().eq(PmsSpuAttrValue::getSpuId, spuId)
                    .select(PmsSpuAttrValue::getId))
                    .stream()
                    .map(item -> item.getId())
                    .collect(Collectors.toList());
            List<Long> removeIds = dbIds.stream().filter(id -> !ids.contains(id)).collect(Collectors.toList());
            iPmsSpuAttrValueService.removeByIds(removeIds);

            iPmsSpuAttrValueService.saveOrUpdateBatch(list);
        });

        // 规格保存
        Optional.ofNullable(specs).ifPresent(list -> {
            list.forEach(item -> item.setSpuId(spu.getId()));

            // 删除此次保存删除的
            List<Long> ids = list.stream().map(item -> item.getId()).collect(Collectors.toList());
            List<Long> dbIds = iPmsSpuSpecValueService.list(new LambdaQueryWrapper<PmsSpuSpecValue>().eq(PmsSpuSpecValue::getSpuId, spuId)
                    .select(PmsSpuSpecValue::getId))
                    .stream()
                    .map(item -> item.getId())
                    .collect(Collectors.toList());
            List<Long> removeIds = dbIds.stream().filter(id -> !ids.contains(id)).collect(Collectors.toList());
            iPmsSpuSpecValueService.removeByIds(removeIds);

            iPmsSpuSpecValueService.saveOrUpdateBatch(list);
        });

        // sku保存
        Optional.ofNullable(skuList).ifPresent(list -> {
            list.forEach(item -> item.setSpuId(spu.getId()));

            // 删除此次保存删除的
            List<Long> ids = list.stream().map(item -> item.getId()).collect(Collectors.toList());
            List<Long> dbIds = iPmsSkuService.list(new LambdaQueryWrapper<PmsSku>().eq(PmsSku::getSpuId, spuId)
                    .select(PmsSku::getId))
                    .stream()
                    .map(item -> item.getId())
                    .collect(Collectors.toList());
            List<Long> removeIds = dbIds.stream().filter(id -> !ids.contains(id)).collect(Collectors.toList());
            iPmsSkuService.removeByIds(removeIds);

            iPmsSkuService.saveOrUpdateBatch(skuList);
        });
        return true;
    }

    @Override
    public boolean removeBySpuIds(List<Long> spuIds) {
        Optional.ofNullable(spuIds).ifPresent(
                ids -> ids.forEach(spuId -> {
                    // sku
                    iPmsSkuService.remove(new LambdaQueryWrapper<PmsSku>().eq(PmsSku::getSpuId, spuId));
                    // 规格
                    iPmsSpuSpecValueService.remove(new LambdaQueryWrapper<PmsSpuSpecValue>().eq(PmsSpuSpecValue::getId, spuId));
                    // 属性
                    iPmsSpuAttrValueService.remove(new LambdaQueryWrapper<PmsSpuAttrValue>().eq(PmsSpuAttrValue::getSpuId, spuId));
                    // spu
                    this.removeById(spuId);
                })
        );
        return true;
    }

    @Override
    public AppProductBO getProductByIdForApp(Long spuId) {
        // spu
        PmsSpu spu = this.getById(spuId);
        SpuDTO spuDTO = new SpuDTO();
        BeanUtil.copyProperties(spu, spuDTO);
        if (StrUtil.isNotBlank(spu.getPicUrls())) {
            // spu专辑图片转换处理 json字符串 -> List
            List<String> pics = JSONUtil.toList(JSONUtil.parseArray(spu.getPicUrls()), String.class);
            spuDTO.setPicUrls(pics);
        }
        // 属性
        List<PmsSpuAttrValue> attrs = iPmsSpuAttrValueService.list(
                new LambdaQueryWrapper<PmsSpuAttrValue>(
                ).eq(PmsSpuAttrValue::getSpuId, spuId)
        );

        // 规格
        List<PmsSpec> specs = iPmsSpecService.listBySpuId(spuId);

        // sku
        List<PmsSku> skuList = iPmsSkuService.list(new LambdaQueryWrapper<PmsSku>().eq(PmsSku::getSpuId, spuId));

        AppProductBO product = new AppProductBO(spuDTO, attrs, specs, skuList);
        return product;
    }
}

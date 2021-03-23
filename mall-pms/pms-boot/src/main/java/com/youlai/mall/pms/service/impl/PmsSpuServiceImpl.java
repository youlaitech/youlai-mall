package com.youlai.mall.pms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.mall.pms.mapper.PmsSpuMapper;
import com.youlai.mall.pms.pojo.bo.admin.ProductBO;
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
public class PmsSpuServiceImpl extends ServiceImpl<PmsSpuMapper, PmsSpu> implements IPmsSpuService {

    private IPmsSkuService iPmsSkuService;
    private IPmsSpuAttributeValueService iPmsSpuAttributeValueService;
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
    public boolean add(ProductBO productBO) {
        SpuDTO SpuDTO = productBO.getSpu();
        List<PmsSpuAttributeValue> attrValues = productBO.getAttrs();
        List<PmsSpuSpecValue> specs = productBO.getSpecs();
        List<PmsSku> skuList = productBO.getSkus();

        // spu保存
        PmsSpu spu = new PmsSpu();
        BeanUtil.copyProperties(SpuDTO, spu);
        if (SpuDTO.getPics() != null) {
            String picUrls = JSONUtil.toJsonStr(SpuDTO.getPics());
            spu.setPics(picUrls);
        }
        this.save(spu);

        // 属性保存
        Optional.ofNullable(attrValues).ifPresent(list -> {
            list.forEach(item -> item.setSpuId(spu.getId()));
            iPmsSpuAttributeValueService.saveBatch(list);
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
    public com.youlai.mall.pms.pojo.bo.admin.ProductBO getBySpuId(Long id) {
        // spu
        SpuDTO spuDTO = new SpuDTO();
        PmsSpu spu = this.getById(id);
        BeanUtil.copyProperties(spu, spuDTO);

        if (StrUtil.isNotBlank(spu.getPics())) {
            // spu专辑图片转换处理 json字符串 -> List
            List<String> pics = JSONUtil.toList(JSONUtil.parseArray(spu.getPics()), String.class);
            spuDTO.setPics(pics);
        }

        // 属性
        List<PmsSpuAttributeValue> attrs = iPmsSpuAttributeValueService.
                list(new LambdaQueryWrapper<PmsSpuAttributeValue>().eq(PmsSpuAttributeValue::getSpuId, id));
        // 规格
        List<PmsSpuSpecValue> specs = iPmsSpuSpecValueService.list(new LambdaQueryWrapper<PmsSpuSpecValue>().eq(PmsSpuSpecValue::getSpuId, id));
        // sku
        List<PmsSku> skus = iPmsSkuService.list(new LambdaQueryWrapper<PmsSku>().eq(PmsSku::getSpuId, id));

        // 组合
          ProductBO productBO = new ProductBO(spuDTO, attrs, specs, skus);
        return productBO;
    }


    @Override
    public boolean updateById(com.youlai.mall.pms.pojo.bo.admin.ProductBO productBO) {
        SpuDTO SpuDTO = productBO.getSpu();

        List<PmsSpuAttributeValue> attrValues = productBO.getAttrs();
        List<PmsSpuSpecValue> specs = productBO.getSpecs();
        List<PmsSku> skuList = productBO.getSkus();

        // spu保存
        PmsSpu spu = new PmsSpu();
        BeanUtil.copyProperties(SpuDTO, spu);
        if (SpuDTO.getPics() != null) {
            String pics = JSONUtil.toJsonStr(SpuDTO.getPics());
            spu.setPics(pics);
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
            List<Long> dbIds = iPmsSpuSpecValueService.list(new LambdaQueryWrapper<PmsSpuSpecValue>().eq(PmsSpuSpecValue::getSpuId, spu.getId())
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
            List<Long> dbIds = iPmsSkuService.list(new LambdaQueryWrapper<PmsSku>().eq(PmsSku::getSpuId, spu.getId())
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
                    iPmsSpuAttributeValueService.remove(new LambdaQueryWrapper<PmsSpuAttributeValue>().eq(PmsSpuAttributeValue::getSpuId, spuId));
                    // spu
                    this.removeById(spuId);
                })
        );
        return true;
    }

    @Override
    public com.youlai.mall.pms.pojo.bo.app.ProductBO getProductByIdForApp(Long spuId) {
        // spu
        PmsSpu spu = this.getById(spuId);
        SpuDTO SpuDTO = new SpuDTO();
        BeanUtil.copyProperties(spu, SpuDTO);
        if (StrUtil.isNotBlank(spu.getPics())) {
            // spu专辑图片转换处理 json字符串 -> List
            List<String> pics = JSONUtil.toList(JSONUtil.parseArray(spu.getPics()), String.class);
            SpuDTO.setPics(pics);
        }
        // 属性
        List<PmsSpuAttributeValue> attrs = iPmsSpuAttributeValueService.list(
                new LambdaQueryWrapper<PmsSpuAttributeValue>(
                ).eq(PmsSpuAttributeValue::getSpuId, spuId)
        );

        // 规格
        List<PmsSpec> specs = iPmsSpecService.listBySpuId(spuId);

        // sku
        List<PmsSku> skuList = iPmsSkuService.list(new LambdaQueryWrapper<PmsSku>().eq(PmsSku::getSpuId, spuId));

        com.youlai.mall.pms.pojo.bo.app.ProductBO product = new com.youlai.mall.pms.pojo.bo.app.ProductBO(SpuDTO, attrs, specs, skuList);
        return product;
    }
}

package com.youlai.mall.pms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.mall.pms.mapper.PmsProductMapper;
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
public class PmsSpuServiceImpl extends ServiceImpl<PmsProductMapper, PmsSpu> implements IPmsSpuService {

    private IPmsSkuService iPmsSkuService;
    private IPmsAttributeValueService iPmsAttributeValueService;
    private IPmsSpecificationValueService iPmsSpecificationValueService;
    private IPmsSpecificationService iPmsSpecificationService;


    @Override
    public IPage<PmsSpu> list(Page<PmsSpu> page, PmsSpu spu) {
        List<PmsSpu> list = this.baseMapper.list(page, spu);
        page.setRecords(list);
        return page;
    }

    @Override
    @Transactional
    public boolean add(ProductBO spuBO) {
        SpuDTO SpuDTO = spuBO.getSpu();
        List<PmsAttributeValue> attrValues = spuBO.getAttrValues();
        List<PmsSpecificationValue> specs = spuBO.getSpecValues();
        List<PmsSku> skuList = spuBO.getSkus();

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
            iPmsAttributeValueService.saveBatch(list);
        });

        // 规格保存
        Optional.ofNullable(specs).ifPresent(list -> {
            list.forEach(item -> item.setSpuId(spu.getId()));
            iPmsSpecificationValueService.saveBatch(list);
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
        List<PmsAttributeValue> attrs = iPmsAttributeValueService.
                list(new LambdaQueryWrapper<PmsAttributeValue>().eq(PmsAttributeValue::getSpuId, id));
        // 规格
        List<PmsSpecificationValue> specs = iPmsSpecificationValueService.list(new LambdaQueryWrapper<PmsSpecificationValue>().eq(PmsSpecificationValue::getSpuId, id));
        // sku
        List<PmsSku> skus = iPmsSkuService.list(new LambdaQueryWrapper<PmsSku>().eq(PmsSku::getSpuId, id));

        // 组合
          ProductBO spuBO = new ProductBO(spuDTO, attrs, specs, skus);
        return spuBO;
    }


    @Override
    public boolean updateById(com.youlai.mall.pms.pojo.bo.admin.ProductBO spuBO) {
        SpuDTO SpuDTO = spuBO.getSpu();

        List<PmsAttributeValue> attrValues = spuBO.getAttrValues();
        List<PmsSpecificationValue> specs = spuBO.getSpecValues();
        List<PmsSku> skuList = spuBO.getSkus();

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
            List<Long> dbIds = iPmsAttributeValueService.list(new LambdaQueryWrapper<PmsAttributeValue>().eq(PmsAttributeValue::getSpuId, spu.getId())
                    .select(PmsAttributeValue::getId))
                    .stream()
                    .map(item -> item.getId())
                    .collect(Collectors.toList());
            List<Long> removeIds = dbIds.stream().filter(id -> !ids.contains(id)).collect(Collectors.toList());
            iPmsAttributeValueService.removeByIds(removeIds);

            iPmsAttributeValueService.saveOrUpdateBatch(list);
        });

        // 规格保存
        Optional.ofNullable(specs).ifPresent(list -> {
            list.forEach(item -> item.setSpuId(spu.getId()));

            // 删除此次保存删除的
            List<Long> ids = list.stream().map(item -> item.getId()).collect(Collectors.toList());
            List<Long> dbIds = iPmsSpecificationValueService.list(new LambdaQueryWrapper<PmsSpecificationValue>().eq(PmsSpecificationValue::getSpuId, spu.getId())
                    .select(PmsSpecificationValue::getId))
                    .stream()
                    .map(item -> item.getId())
                    .collect(Collectors.toList());
            List<Long> removeIds = dbIds.stream().filter(id -> !ids.contains(id)).collect(Collectors.toList());
            iPmsSpecificationValueService.removeByIds(removeIds);

            iPmsSpecificationValueService.saveOrUpdateBatch(list);
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
                    iPmsSpecificationValueService.remove(new LambdaQueryWrapper<PmsSpecificationValue>().eq(PmsSpecificationValue::getId, spuId));
                    // 属性
                    iPmsAttributeValueService.remove(new LambdaQueryWrapper<PmsAttributeValue>().eq(PmsAttributeValue::getSpuId, spuId));
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
        List<PmsAttributeValue> attrs = iPmsAttributeValueService.list(
                new LambdaQueryWrapper<PmsAttributeValue>(
                ).eq(PmsAttributeValue::getSpuId, spuId)
        );

        // 规格
        List<PmsSpecification> specs = iPmsSpecificationService.listBySpuId(spuId);

        // sku
        List<PmsSku> skuList = iPmsSkuService.list(new LambdaQueryWrapper<PmsSku>().eq(PmsSku::getSpuId, spuId));

        com.youlai.mall.pms.pojo.bo.app.ProductBO product = new com.youlai.mall.pms.pojo.bo.app.ProductBO(SpuDTO, attrs, specs, skuList);
        return product;
    }
}

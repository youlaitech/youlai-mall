package com.youlai.mall.pms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
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
import com.youlai.mall.pms.pojo.dto.SkuDTO;
import com.youlai.mall.pms.pojo.dto.SpuDTO;
import com.youlai.mall.pms.service.IPmsAttrValueService;
import com.youlai.mall.pms.service.IPmsSkuService;
import com.youlai.mall.pms.service.IPmsCategorySpecService;
import com.youlai.mall.pms.service.IPmsSpuService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
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
    private IPmsAttrValueService iPmsAttrValueService;
    private IPmsCategorySpecService iPmsCategorySpecService;


    @Override
    public IPage<PmsSpu> list(Page<PmsSpu> page, PmsSpu spu) {
        List<PmsSpu> list = this.baseMapper.list(page, spu);
        page.setRecords(list);
        return page;
    }

    @Override
    public boolean add(ProductBO spuBO) {
        SpuDTO spuDTO = spuBO.getSpu();
        List<PmsSpuAttr> attributes = spuBO.getAttributes();
        List<PmsSpuSpec> specifications = null;
        List<PmsSku> skuList = spuBO.getSkuList();

        // spu保存
        PmsSpu spu = new PmsSpu();
        BeanUtil.copyProperties(spuDTO, spu);
        if (spuDTO.getPics() != null) {
            String picUrls = JSONUtil.toJsonStr(spuDTO.getPics());
            spu.setPicUrls(picUrls);
        }
        this.save(spu);

        // 属性保存
        Optional.ofNullable(attributes).ifPresent(list -> {
            list.forEach(item -> item.setSpuId(spu.getId()));
            iPmsAttrValueService.saveBatch(attributes);
        });

        // 规格保存
        Optional.ofNullable(specifications).ifPresent(list -> {
            //iPmsSpecValueService.saveBatch(specifications);
        });

        // sku保存
        Optional.ofNullable(skuList).ifPresent(list -> {
            list.forEach(item -> item.setSpuId(spu.getId()));
            iPmsSkuService.saveBatch(skuList);
        });

        return true;
    }

    @Override
    public ProductBO getSpuById(Long id) {
        // spu
        SpuDTO spuDTO = new SpuDTO();
        PmsSpu spu = this.getById(id);
        BeanUtil.copyProperties(spu, spuDTO);

        if (StrUtil.isNotBlank(spu.getPicUrls())) {
            // spu专辑图片转换处理 json字符串 -> List
            List<String> pics = JSONUtil.toList(JSONUtil.parseArray(spu.getPicUrls()), String.class);
            spuDTO.setPics(pics);
        }

        // 属性
        List<PmsSpuAttr> attributes = iPmsAttrValueService.
                list(new LambdaQueryWrapper<PmsSpuAttr>().eq(PmsSpuAttr::getSpuId, id));

        // 规格
      /*  List<PmsSpecValue> specifications = iPmsSpecValueService.list(
                new LambdaQueryWrapper<PmsSpecValue>().eq(PmsSpecValue::getId, id)
        );*/

        // sku
        List<PmsSku> skuList = iPmsSkuService.list(new LambdaQueryWrapper<PmsSku>().eq(PmsSku::getSpuId, id));

        // 组合
        ProductBO spuBO = new ProductBO(spuDTO, attributes, null, skuList);
        return spuBO;
    }

    @Override
    public boolean removeBySpuIds(List<Long> spuIds) {
        Optional.ofNullable(spuIds).ifPresent(
                ids -> ids.forEach(spuId -> {
                    // sku
                    iPmsSkuService.remove(new LambdaQueryWrapper<PmsSku>().eq(PmsSku::getSpuId, spuId));

                    // 规格
                    //iPmsSpecValueService.remove(new LambdaQueryWrapper<PmsSpecValue>().eq(PmsSpecValue::getId, spuId));

                    // 属性
                    iPmsAttrValueService.remove(new LambdaQueryWrapper<PmsSpuAttr>().eq(PmsSpuAttr::getSpuId, spuId));

                    // spu
                    this.removeById(spuId);
                })
        );
        return true;
    }

    @Override
    public boolean updateById(ProductBO spuBO) {
        // spu
        SpuDTO spuDTO = spuBO.getSpu();
        PmsSpu spu = new PmsSpu();
        BeanUtil.copyProperties(spuDTO, spu);
        if (CollectionUtil.isNotEmpty(spuDTO.getPics())) {
            String picUrls = JSONUtil.toJsonStr(spuDTO.getPics());
            spu.setPicUrls(picUrls);
        }
        this.updateById(spu);

        // 属性保存
        List<PmsSpuAttr> attributes = spuBO.getAttributes();
        Optional.ofNullable(attributes).ifPresent(list -> {
            list.forEach(item -> item.setSpuId(spu.getId()));
            iPmsAttrValueService.updateBatchById(attributes);
        });

        // 规格保存
        List<PmsSpuSpec> specifications = null;
        Optional.ofNullable(specifications).ifPresent(list -> {
            //iPmsSpecValueService.updateBatchById(specifications);
        });

        // sku保存
        List<PmsSku> skuList = spuBO.getSkuList();
        Optional.ofNullable(skuList).ifPresent(list -> {
            list.forEach(item -> item.setSpuId(spu.getId()));
            iPmsSkuService.updateBatchById(skuList);
        });
        return true;
    }

    @Override
    public AppProductBO getAppProductBySpuId(Long spuId) {
        // spu
        PmsSpu spu = this.getById(spuId);
        SpuDTO spuDTO = new SpuDTO();
        BeanUtil.copyProperties(spu, spuDTO);
        if (StrUtil.isNotBlank(spu.getPicUrls())) {
            // spu专辑图片转换处理 json字符串 -> List
            List<String> pics = JSONUtil.toList(JSONUtil.parseArray(spu.getPicUrls()), String.class);
            spuDTO.setPics(pics);
        }
        // 属性
        List<PmsSpuAttr> attributes = iPmsAttrValueService.listBySpuId(spuId);

        // 规格
        List<PmsCategorySpec> specifications = iPmsCategorySpecService.listBySpuId(spuId);

        // sku
        List<PmsSku> skuList = iPmsSkuService.list(new LambdaQueryWrapper<PmsSku>().eq(PmsSku::getSpuId, spuId));
        List<SkuDTO> skuDTOList = new ArrayList<>();
        Optional.ofNullable(skuList).ifPresent(list -> {
            list.forEach(sku -> {
                SkuDTO skuDTO = new SkuDTO();
                BeanUtil.copyProperties(sku, skuDTO, "specValueIds");
                if (StrUtil.isNotBlank(sku.getSpecValueIds())) {
                    List<Long> specValueIds = Arrays.stream(sku.getSpecValueIds().split(","))
                            .map(id -> Long.parseLong(id)).collect(Collectors.toList());
                    skuDTO.setSpecValueIds(specValueIds);
                }
                skuDTOList.add(skuDTO);
            });
        });
        AppProductBO product = new AppProductBO(spuDTO, skuDTOList, attributes, specifications);
        return product;
    }
}

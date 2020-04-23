package com.fly4j.yshop.pms.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fly4j.yshop.pms.mapper.PmsSpuMapper;
import com.fly4j.yshop.pms.pojo.dto.admin.PmsSpuDTO;
import com.fly4j.yshop.pms.pojo.entity.PmsAttribute;
import com.fly4j.yshop.pms.pojo.entity.PmsSku;
import com.fly4j.yshop.pms.pojo.entity.PmsSpec;
import com.fly4j.yshop.pms.pojo.entity.PmsSpu;
import com.fly4j.yshop.pms.service.IPmsAttributeService;
import com.fly4j.yshop.pms.service.IPmsSkuService;
import com.fly4j.yshop.pms.service.IPmsSpecService;
import com.fly4j.yshop.pms.service.IPmsSpuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;


@Service
public class PmsSpuServiceImpl extends ServiceImpl<PmsSpuMapper, PmsSpu> implements IPmsSpuService {

    @Autowired
    private IPmsSpuService iPmsSpuService;

    @Autowired
    private IPmsAttributeService iPmsAttributeService;

    @Autowired
    private IPmsSpecService iPmsSpecService;

    @Autowired
    private IPmsSkuService iPmsSkuService;

    @Override
    public boolean add(PmsSpuDTO pmsSpuDTO) {
        // sku
        List<PmsSku> skuList = pmsSpuDTO.getSku_list();
        BigDecimal retail_price = skuList.stream().min(Comparator.comparing(PmsSku::getPrice)).get().getPrice();

        // spu
        PmsSpu spu = pmsSpuDTO.getSpu();
        spu.setRetail_price(retail_price);
        iPmsSpuService.save(spu);
        Long spuId = spu.getId();

        // 属性
        List<PmsAttribute> attributeList = pmsSpuDTO.getAttribute_list();
        if (attributeList != null && attributeList.size() > 0) {
            attributeList.forEach(attribute -> {
                attribute.setSpu_id(spuId);
            });
            iPmsAttributeService.saveBatch(attributeList);
        }

        // 规格
        List<PmsSpec> specList = pmsSpuDTO.getSpec_list();
        if (specList != null && specList.size() > 0) {
            specList.forEach(spec -> {
                spec.setSpu_id(spuId);
            });
            iPmsSpecService.saveBatch(specList);
        }


        if (skuList != null && skuList.size() > 0) {
            skuList.forEach(sku -> {
                sku.setStock_locked(0);
                sku.setSpu_id(spuId);
            });
            iPmsSkuService.saveBatch(skuList);
        }
        return true;
    }
}

package com.youlai.mall.pms.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.mall.pms.bo.SpuBO;
import com.youlai.mall.pms.pojo.PmsSku;
import com.youlai.mall.pms.pojo.PmsSpu;
import com.youlai.mall.pms.pojo.PmsSpuAttribute;
import com.youlai.mall.pms.pojo.PmsSpuSpecification;
import com.youlai.mall.pms.mapper.PmsSpuMapper;
import com.youlai.mall.pms.service.IPmsSkuService;
import com.youlai.mall.pms.service.IPmsSpuAttributeService;
import com.youlai.mall.pms.service.IPmsSpuService;
import com.youlai.mall.pms.service.IPmsSpuSpecificationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author haoxr
 * @date 2020-11-06
 */
@Service
@AllArgsConstructor
public class PmsSpuServiceImpl extends ServiceImpl<PmsSpuMapper, PmsSpu> implements IPmsSpuService {

    private IPmsSpuAttributeService iPmsSpuAttributeService;
    private IPmsSpuSpecificationService iPmsSpuSpecificationService;
    private IPmsSkuService iPmsSkuService;

    @Override
    public IPage<PmsSpu> list(Page<PmsSpu> page, PmsSpu spu) {
        List<PmsSpu> list = this.baseMapper.list(page, spu);
        page.setRecords(list);
        return page;
    }

    @Override
    public void add(SpuBO spuBO) {
        PmsSpu spu = spuBO.getSpu();
        List<PmsSpuAttribute> attributes = spuBO.getAttributes();
        List<PmsSpuSpecification> specifications = spuBO.getSpecifications();
        List<PmsSku> skuList = spuBO.getSkuList();

        // spu保存
        this.save(spu);

        // 属性保存
        Optional.ofNullable(attributes).ifPresent(list -> {
            list.forEach(item -> item.setSpuId(spu.getId()));
            iPmsSpuAttributeService.saveBatch(attributes);
        });

        // 规格保存
        Optional.ofNullable(specifications).ifPresent(list -> {
            list.forEach(item -> item.setSpuId(spu.getId()));
            iPmsSpuSpecificationService.saveBatch(specifications);
        });
        // sku保存
        Optional.ofNullable(skuList).ifPresent(list -> {
            list.forEach(item -> item.setSpuId(spu.getId()));
            iPmsSkuService.saveBatch(skuList);
        });

    }
}

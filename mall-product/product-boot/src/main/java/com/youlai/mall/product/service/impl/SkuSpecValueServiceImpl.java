package com.youlai.mall.product.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.mall.product.mapper.SkuSpecValueMapper;
import com.youlai.mall.product.model.entity.SkuSpecValue;
import com.youlai.mall.product.model.form.SpuForm;
import com.youlai.mall.product.service.SkuSpecValueService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * SKU规格值服务实现类
 *
 * @author Ray.Hao
 * @since 2024-04-14
 */
@Service
public class SkuSpecValueServiceImpl extends ServiceImpl<SkuSpecValueMapper, SkuSpecValue> implements SkuSpecValueService {

    /**
     * 保存商品规格值
     *
     * @param skuId    SKU ID
     * @param specValues 规格值列表
     */
    @Override
    @Transactional
    public void specSpecValues(Long skuId, List<SpuForm.SpecValue> specValues) {

        // 删除 SKU 的规格值
        this.remove(new LambdaQueryWrapper<SkuSpecValue>().eq(SkuSpecValue::getSkuId, skuId));

        // 保存 SKU 的规格值
        if (CollectionUtil.isNotEmpty(specValues)) {
            List<SkuSpecValue> skuSpecValues = specValues.stream().map(specValue -> {
                SkuSpecValue skuSpecValue = new SkuSpecValue();
                skuSpecValue.setSkuId(skuId);
                skuSpecValue.setSpecId(specValue.getSpecId());
                skuSpecValue.setSpecValue(specValue.getSpecValue());
                return skuSpecValue;
            }).toList();
            this.saveBatch(skuSpecValues);
        }
    }

}

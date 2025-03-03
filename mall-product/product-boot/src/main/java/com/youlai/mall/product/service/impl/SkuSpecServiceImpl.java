package com.youlai.mall.product.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.mall.product.mapper.SkuSpecMapper;
import com.youlai.mall.product.model.entity.SkuSpec;
import com.youlai.mall.product.model.form.SpuForm;
import com.youlai.mall.product.service.SkuSpecService;
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
public class SkuSpecServiceImpl extends ServiceImpl<SkuSpecMapper, SkuSpec> implements SkuSpecService {

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
        this.remove(new LambdaQueryWrapper<SkuSpec>().eq(SkuSpec::getSkuId, skuId));

        // 保存 SKU 的规格值
        if (CollectionUtil.isNotEmpty(specValues)) {
            List<SkuSpec> skuSpecs = specValues.stream().map(specValue -> {
                SkuSpec skuSpec = new SkuSpec();
                skuSpec.setSkuId(skuId);
                skuSpec.setSpecId(specValue.getSpecId());
                skuSpec.setSpecValue(specValue.getSpecValue());
                return skuSpec;
            }).toList();
            this.saveBatch(skuSpecs);
        }
    }

}

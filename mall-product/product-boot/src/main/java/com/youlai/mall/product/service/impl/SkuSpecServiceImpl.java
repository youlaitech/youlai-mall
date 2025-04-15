package com.youlai.mall.product.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.mall.product.mapper.SkuSpecMapper;
import com.youlai.mall.product.model.entity.SkuSpec;
import com.youlai.mall.product.model.form.SpuForm;
import com.youlai.mall.product.service.SkuSpecService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * SKU规格值服务实现类
 *
 * @author Ray.Hao
 * @since 2024-04-14
 */
@Service
@Slf4j
public class SkuSpecServiceImpl extends ServiceImpl<SkuSpecMapper, SkuSpec> implements SkuSpecService {

    /**
     * 保存商品规格值
     *
     * @param skuId    SKU ID
     * @param specList 规格列表
     */
    @Override
    @Transactional
    public void saveSkuSpecs(Long skuId, List<SpuForm.SpecValue> specList) {
        if (skuId == null) {
            throw new IllegalArgumentException("SKU ID 不能为空");
        }

        log.info("开始处理 SKU ID={} 的规格值", skuId);

        // 1. 直接删除该SKU的所有旧规格
        this.remove(new LambdaQueryWrapper<SkuSpec>().eq(SkuSpec::getSkuId, skuId));
        log.info("已删除 SKU ID={} 的所有旧规格", skuId);

        // 2. 如果传入规格列表为空，则直接返回
        if (CollectionUtil.isEmpty(specList)) {
            log.info("SKU ID={} 无新规格值需要保存", skuId);
            return;
        }

        // 3. 批量新增规格（包括原ID存在的新规格）
        List<SkuSpec> specsToSave = specList.stream()
                .map(spec -> {
                    SkuSpec skuSpec = new SkuSpec();
                    skuSpec.setSkuId(skuId);
                    skuSpec.setSpecName(spec.getName());
                    skuSpec.setSpecValue(spec.getValue());
                    skuSpec.setImgUrl(spec.getImgUrl());
                    return skuSpec;
                })
                .toList();

        this.saveBatch(specsToSave);
        log.info("已新增 SKU ID={} 的规格值，数量={}", skuId, specsToSave.size());
    }

}

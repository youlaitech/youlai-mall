package com.youlai.mall.product.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.mall.product.converter.SkuAttributeValueConverter;
import com.youlai.mall.product.mapper.SkuAttributeValueMapper;
import com.youlai.mall.product.model.entity.SkuAttributeValue;
import com.youlai.mall.product.model.form.SpuForm;
import com.youlai.mall.product.service.SkuAttributeValueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * SKU规格值服务实现类
 *
 * @author Ray Hao
 * @since 2024-04-14
 */
@Service
@RequiredArgsConstructor
public class SkuAttributeValueServiceImpl extends ServiceImpl<SkuAttributeValueMapper, SkuAttributeValue> implements SkuAttributeValueService {

    private final SkuAttributeValueConverter skuAttributeValueConverter;


    /**
     * 保存销售属性值
     *
     * @param skuId    SKU ID
     * @param saleAttributeValues 销售属性值列表
     */
    @Override
    public void saveSaleAttributeValues(Long skuId, List<SpuForm.AttributeValue> saleAttributeValues) {

        // 检索数据库中与sku关联的规格值
        List<SkuAttributeValue> existingInDb = this.list(new LambdaQueryWrapper<SkuAttributeValue>().eq(SkuAttributeValue::getSkuId, skuId));

        // 从提交的表单中提取所有非空的SKU ID
        List<Long> submittedIds = saleAttributeValues.stream()
                .map(SpuForm.AttributeValue::getId)
                .filter(Objects::nonNull)
                .toList();

        // 确定需要删除的SKU：如果它们在提交的SKU列表中不存在
        List<SkuAttributeValue> specValuesToDelete = existingInDb.stream()
                .filter(item -> !submittedIds.contains(item.getId()))
                .toList();

        // 如果有SKU需要删除，则进行删除操作
        if (CollectionUtil.isNotEmpty(specValuesToDelete)) {
            List<Long> specValueIdsToDelete = specValuesToDelete.stream()
                    .map(SkuAttributeValue::getId)
                    .toList();

            // 删除SKU关联的规格值
            this.removeByIds(specValueIdsToDelete);
        }

        // 循环处理提交的每个规格值
        for (SpuForm.AttributeValue spec : saleAttributeValues) {
            SkuAttributeValue entity = skuAttributeValueConverter.convertToEntity(spec);
            entity.setSkuId(skuId);
            this.saveOrUpdate(entity);
        }
    }
}

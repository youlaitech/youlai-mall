package com.youlai.mall.pms.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.mall.pms.converter.SkuSpecValueConverter;
import com.youlai.mall.pms.mapper.SkuSpecValueMapper;
import com.youlai.mall.pms.model.entity.Sku;
import com.youlai.mall.pms.model.entity.SkuSpecValue;
import com.youlai.mall.pms.model.form.SpuForm;
import com.youlai.mall.pms.service.SkuSpecValueService;
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
public class SkuSpecValueServiceImpl extends ServiceImpl<SkuSpecValueMapper, SkuSpecValue> implements SkuSpecValueService {

    private final SkuSpecValueConverter skuSpecValueConverter;


    /**
     * 保存SKU规格值
     *
     * @param skuId    SKU ID
     * @param specList 规格值列表
     */
    @Override
    public void saveSkuSpecValues(Long skuId, List<SpuForm.Attribute> specList) {

        // 检索数据库中与sku关联的规格值
        List<SkuSpecValue> existingInDb = this.list(new LambdaQueryWrapper<SkuSpecValue>().eq(SkuSpecValue::getSkuId, skuId));

        // 从提交的表单中提取所有非空的SKU ID
        List<Long> submittedIds = specList.stream()
                .map(SpuForm.Attribute::getId)
                .filter(Objects::nonNull)
                .toList();

        // 确定需要删除的SKU：如果它们在提交的SKU列表中不存在
        List<SkuSpecValue> specValuesToDelete = existingInDb.stream()
                .filter(item -> !submittedIds.contains(item.getId()))
                .toList();

        // 如果有SKU需要删除，则进行删除操作
        if (CollectionUtil.isNotEmpty(specValuesToDelete)) {
            List<Long> specValueIdsToDelete = specValuesToDelete.stream()
                    .map(SkuSpecValue::getId)
                    .toList();

            // 删除SKU关联的规格值
            this.removeByIds(specValueIdsToDelete);
        }
        // 循环处理提交的每个规格值
        for (SpuForm.Attribute spec : specList) {
            SkuSpecValue entity = skuSpecValueConverter.convertToEntity(spec);
            entity.setSkuId(skuId);
            this.saveOrUpdate(entity);
        }
    }
}

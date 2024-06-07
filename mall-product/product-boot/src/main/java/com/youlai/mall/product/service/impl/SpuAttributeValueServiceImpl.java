package com.youlai.mall.product.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.mall.product.converter.SpuAttributeValueConverter;
import com.youlai.mall.product.mapper.SpuAttributeValueMapper;
import com.youlai.mall.product.model.entity.SpuAttributeValue;
import com.youlai.mall.product.model.form.SpuForm;
import com.youlai.mall.product.service.SpuAttributeValueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 商品属性 服务实现类
 *
 * @author Ray Hao
 * @since 2024/04/24
 */
@Service
@RequiredArgsConstructor
public class SpuAttributeValueServiceImpl extends ServiceImpl<SpuAttributeValueMapper, SpuAttributeValue> implements SpuAttributeValueService {

    private final SpuAttributeValueConverter spuAttributeValueConverter;

    /**
     * 保存商品属性
     *
     * @param spuId            SPU ID
     * @param attributeGroupList 属性组列表
     */
    @Override
    @Transactional
    public void saveSpuAttributes(Long spuId, List<SpuForm.AttributeGroup> attributeGroupList) {
        // 如果属性列表为空，则删除所有旧属性
        if (CollectionUtil.isEmpty(attributeGroupList)) {
            this.remove(new LambdaQueryWrapper<SpuAttributeValue>().eq(SpuAttributeValue::getSpuId, spuId));
            return;
        }

        // 获取当前数据库中的属性
        Map<Long, SpuAttributeValue> existingAttributes = this.list(new LambdaQueryWrapper<SpuAttributeValue>()
                        .eq(SpuAttributeValue::getSpuId, spuId))
                .stream().collect(Collectors.toMap(SpuAttributeValue::getId, Function.identity()));

        List<SpuAttributeValue> attributesToSave = new ArrayList<>();

        // 遍历属性组并处理属性值
        for (SpuForm.AttributeGroup attributeGroup : attributeGroupList) {
            for (SpuForm.AttributeValue formAttribute : attributeGroup.getAttributeValues()) {
                SpuAttributeValue newAttr = spuAttributeValueConverter.convertToEntity(formAttribute);
                newAttr.setSpuId(spuId);

                // 如果存在旧属性则移除，这样existingAttributes中剩下的即为需要删除的属性
                if (newAttr.getId() != null) {
                    existingAttributes.remove(newAttr.getId());
                }

                attributesToSave.add(newAttr);
            }
        }

        // 删除不再存在的属性
        if (!existingAttributes.isEmpty()) {
            this.removeByIds(existingAttributes.keySet());
        }

        // 保存或更新所有属性
        if (!attributesToSave.isEmpty()) {
            this.saveOrUpdateBatch(attributesToSave);
        }
    }

    /**
     * 根据SPU ID获取商品属性列表
     *
     * @param spuId SPU ID
     * @return 商品属性列表
     */
    @Override
    public List<SpuForm.AttributeGroup> listAttributeGroupsBySpuId(Long spuId) {
        return this.baseMapper.listAttributeGroupsBySpuId(spuId);
    }
}

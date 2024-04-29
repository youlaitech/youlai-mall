package com.youlai.mall.pms.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.mall.pms.converter.SpuAttributeConverter;
import com.youlai.mall.pms.mapper.SpuAttributeMapper;
import com.youlai.mall.pms.model.entity.SpuAttributeValue;
import com.youlai.mall.pms.model.form.SpuForm;
import com.youlai.mall.pms.service.SpuAttributeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
public class SpuAttributeServiceImpl extends ServiceImpl<SpuAttributeMapper, SpuAttributeValue> implements SpuAttributeService {

    private final SpuAttributeConverter spuAttributeConverter;


    /**
     * 保存商品属性
     *
     * @param spuId         SPU ID
     * @param formAttributes 属性列表
     */
    @Override
    public void saveSpuAttributes(Long spuId, List<SpuForm.Attribute> formAttributes) {
        // 如果属性列表为空，则删除所有旧属性
        if (CollectionUtil.isEmpty(formAttributes)) {
            this.remove(new LambdaQueryWrapper<SpuAttributeValue>().eq(SpuAttributeValue::getSpuId, spuId));
        } else {
            // 获取当前数据库中的属性
            Map<Long, SpuAttributeValue> existingAttributes = this.list(new LambdaQueryWrapper<SpuAttributeValue>().eq(SpuAttributeValue::getSpuId, spuId))
                    .stream().collect(Collectors.toMap(SpuAttributeValue::getId, Function.identity()));

            List<SpuAttributeValue> attributesToSave = new ArrayList<>();
            for (int i = 0; i < formAttributes.size(); i++) {
                SpuAttributeValue newAttr = spuAttributeConverter.formAttribute2Entity(formAttributes.get(i));
                newAttr.setSort(i + 1);
                newAttr.setSpuId(spuId);

                // 如果存在旧属性则移除，这样existingAttributes中剩下的即为需要删除的属性
                if (newAttr.getId() != null) {
                    existingAttributes.remove(newAttr.getId());
                }

                attributesToSave.add(newAttr);
            }

            // 删除不再存在的属性
            if (!existingAttributes.isEmpty()) {
                this.removeByIds(existingAttributes.keySet());
            }

            // 保存所有属性
            if (!attributesToSave.isEmpty()) {
                this.saveOrUpdateBatch(attributesToSave);
            }
        }
    }
}

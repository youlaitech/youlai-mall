package com.youlai.mall.product.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.mall.product.mapper.SpuAttrValueMapper;
import com.youlai.mall.product.model.entity.SpuAttrValue;
import com.youlai.mall.product.model.form.SpuForm;
import com.youlai.mall.product.service.SpuAttrValueService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 商品属性 服务实现类
 *
 * @author Ray.Hao
 * @since 2024/04/24
 */
@Service
public class SpuAttrValueServiceImpl extends ServiceImpl<SpuAttrValueMapper, SpuAttrValue> implements SpuAttrValueService {
    /**
     * 保存商品属性
     *
     * @param spuId      SPU ID
     * @param attrValues 属性值列表
     */
    @Override
    @Transactional
    public void saveAttributeValues(Long spuId, List<SpuForm.AttrValue> attrValues) {
        // 删除原有属性
        this.remove(new LambdaQueryWrapper<SpuAttrValue>().eq(SpuAttrValue::getSpuId, spuId));

        // 保存新属性
        if (CollectionUtil.isNotEmpty(attrValues)) {
            List<SpuAttrValue> spuAttrValues = attrValues.stream().map(attrValue -> {
                SpuAttrValue spuAttrValue = new SpuAttrValue();
                spuAttrValue.setSpuId(spuId);
                spuAttrValue.setAttrId(attrValue.getAttrId());
                spuAttrValue.setAttrValue(attrValue.getAttrValue());
                return spuAttrValue;
            }).collect(Collectors.toList());
            this.saveBatch(spuAttrValues);
        }
    }

    /**
     * 判断属性是否有商品引用
     *
     * @param attrId 属性ID
     */
    @Override
    public boolean isAttrReferenced(Long attrId) {
        long count = this.count(new LambdaQueryWrapper<SpuAttrValue>()
                .eq(SpuAttrValue::getAttrId, attrId));
        return count > 0;
    }


}

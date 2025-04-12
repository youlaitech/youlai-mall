package com.youlai.mall.product.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.mall.product.mapper.SpuAttrMapper;
import com.youlai.mall.product.model.bo.SpuAttrBO;
import com.youlai.mall.product.model.entity.SpuAttr;
import com.youlai.mall.product.model.form.SpuForm;
import com.youlai.mall.product.service.SpuAttrService;
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
public class SpuAttrServiceImpl extends ServiceImpl<SpuAttrMapper, SpuAttr> implements SpuAttrService {
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
        this.remove(new LambdaQueryWrapper<SpuAttr>().eq(SpuAttr::getSpuId, spuId));

        // 保存新属性
        if (CollectionUtil.isNotEmpty(attrValues)) {
            List<SpuAttr> spuAttrs = attrValues.stream().map(attrValue -> {
                SpuAttr spuAttr = new SpuAttr();
                spuAttr.setSpuId(spuId);
                spuAttr.setAttrId(attrValue.getAttrId());
                spuAttr.setAttrValue(attrValue.getAttrValue());
                return spuAttr;
            }).collect(Collectors.toList());
            this.saveBatch(spuAttrs);
        }
    }

    /**
     * 判断属性是否有商品引用
     *
     * @param attrId 属性ID
     */
    @Override
    public boolean isAttrReferenced(Long attrId) {
        long count = this.count(new LambdaQueryWrapper<SpuAttr>()
                .eq(SpuAttr::getAttrId, attrId));
        return count > 0;
    }

    /**
     * 根据SPU ID查询商品属性列表
     *
     * @param spuId SPU ID
     * @return 商品属性列表
     */
    @Override
    public List<SpuAttrBO> listAttrsBySpuId(Long spuId) {
        return this.baseMapper.listAttrsBySpuId(spuId);
    }
}

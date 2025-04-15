package com.youlai.mall.product.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.mall.product.mapper.SpuAttrMapper;
import com.youlai.mall.product.model.bo.SpuAttrBO;
import com.youlai.mall.product.model.entity.SpuAttr;
import com.youlai.mall.product.model.form.SpuForm;
import com.youlai.mall.product.service.SpuAttrService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 商品属性 服务实现类
 *
 * @author Ray.Hao
 * @since 2024/04/24
 */
@Service
@Slf4j
public class SpuAttrServiceImpl extends ServiceImpl<SpuAttrMapper, SpuAttr> implements SpuAttrService {

    /**
     * 保存商品属性
     *
     * @param spuId  SPU ID
     * @param attrList 属性列表
     */
    @Override
    @Transactional
    public void saveSpuAttrs(Long spuId, List<SpuForm.AttrValue> attrList) {
        // 参数校验
        if (spuId == null) {
            throw new IllegalArgumentException("SPU ID 不能为空");
        }

        log.info("开始保存 SPU ID={} 的属性（全删全插模式）", spuId);

        // 1. 先删除该SPU的所有现有属性
        this.remove(new LambdaQueryWrapper<SpuAttr>().eq(SpuAttr::getSpuId, spuId));

        // 2. 如果传入的属性列表不为空，则批量插入新属性
        if (CollectionUtil.isNotEmpty(attrList)) {
            List<SpuAttr> newAttrs = attrList.stream()
                    .map(attr -> {
                        SpuAttr spuAttr = new SpuAttr();
                        spuAttr.setSpuId(spuId);
                        spuAttr.setAttrName(attr.getName());
                        spuAttr.setAttrValue(attr.getValue());
                        return spuAttr;
                    })
                    .collect(Collectors.toList());

            log.info("插入 SPU ID={} 的新属性，数量={}", spuId, newAttrs.size());
            this.saveBatch(newAttrs);
        }

        log.info("完成保存 SPU ID={} 的属性", spuId);
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

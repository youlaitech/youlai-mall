package com.youlai.mall.pms.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.mall.pms.mapper.PmsAttributeMapper;
import com.youlai.mall.pms.pojo.dto.admin.AttributeFormDTO;
import com.youlai.mall.pms.pojo.entity.PmsAttribute;
import com.youlai.mall.pms.service.IPmsAttributeService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:xianrui0365@163.com">haoxr</a>
 */
@Service
public class PmsAttributeServiceImpl extends ServiceImpl<PmsAttributeMapper, PmsAttribute> implements IPmsAttributeService {

    @Override
    public boolean saveBatch(AttributeFormDTO attributeForm) {
        Long categoryId = attributeForm.getCategoryId();
        Integer attributeType = attributeForm.getType();

        List<Long> formIds = attributeForm.getAttributes().stream()
                .filter(item -> item.getId() != null)
                .map(item -> item.getId())
                .collect(Collectors.toList());

        List<Long> dbIds = this.list(new LambdaQueryWrapper<PmsAttribute>()
                .eq(PmsAttribute::getCategoryId, categoryId)
                .eq(PmsAttribute::getType, attributeType)
                .select(PmsAttribute::getId)).stream()
                .map(item -> item.getId())
                .collect(Collectors.toList());

        // 删除此次表单没有的属性ID
        if (CollectionUtil.isNotEmpty(dbIds)) {
            List<Long> rmIds = dbIds.stream()
                    .filter(id -> CollectionUtil.isEmpty(formIds) || !formIds.contains(id))
                    .collect(Collectors.toList());
            if (CollectionUtil.isNotEmpty(rmIds)) {
                this.removeByIds(rmIds);
            }
        }

        // 新增/修改表单提交的属性
        List<AttributeFormDTO.Attribute> formAttributes = attributeForm.getAttributes();

        List<PmsAttribute> attributeList = new ArrayList<>();

        formAttributes.forEach(item -> {
            PmsAttribute attribute = PmsAttribute.builder().id(item.getId()).categoryId(categoryId).type(attributeType).name(item.getName()).build();
            attributeList.add(attribute);
        });
        boolean result = this.saveOrUpdateBatch(attributeList);
        return result;
    }
}

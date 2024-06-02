package com.youlai.mall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.youlai.common.web.model.Option;
import com.youlai.mall.product.model.entity.Attribute;
import com.youlai.mall.product.model.entity.AttributeGroup;
import com.youlai.mall.product.mapper.AttributeGroupMapper;
import com.youlai.mall.product.service.AttributeGroupService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.mall.product.service.AttributeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.youlai.common.util.DateUtils;
import com.youlai.mall.product.model.form.AttributeGroupForm;
import com.youlai.mall.product.model.query.AttributeGroupPageQuery;
import com.youlai.mall.product.model.bo.AttributeGroupBO;
import com.youlai.mall.product.model.vo.AttributeGroupPageVO;
import com.youlai.mall.product.converter.AttributeGroupConverter;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.Arrays;
import java.util.List;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;

/**
 * 属性组服务实现类
 *
 * @author Ray Hao
 * @since 2024-04-14
 */
@Service
@RequiredArgsConstructor
public class AttributeGroupServiceImpl extends ServiceImpl<AttributeGroupMapper, AttributeGroup> implements AttributeGroupService {

    private final AttributeGroupConverter attributeGroupConverter;

    private final AttributeService attributeService;

    /**
     * 获取属性组分页列表
     *
     * @param queryParams 查询参数
     * @return {@link IPage<AttributeGroupPageVO>} 属性组分页列表
     */
    @Override
    public IPage<AttributeGroupPageVO> listPagedAttributeGroups(AttributeGroupPageQuery queryParams) {

        // 参数构建
        int pageNum = queryParams.getPageNum();
        int pageSize = queryParams.getPageSize();
        Page<AttributeGroupBO> page = new Page<>(pageNum, pageSize);

        // 格式化为数据库日期格式，避免日期比较使用格式化函数导致索引失效
        DateUtils.toDatabaseFormat(queryParams, "startTime", "endTime");

        // 查询数据
        Page<AttributeGroupBO> boPage = this.baseMapper.listPagedAttributeGroups(page, queryParams);

        // 实体转换
        return attributeGroupConverter.bo2PageVo(boPage);
    }

    /**
     * 获取属性组表单数据
     *
     * @param id 属性组ID
     * @return
     */
    @Override
    public AttributeGroupForm getAttributeGroupFormData(Long id) {
        AttributeGroup entity = this.getById(id);
        return attributeGroupConverter.entity2Form(entity);
    }

    /**
     * 新增属性组
     *
     * @param formData 属性组表单对象
     * @return
     */
    @Override
    public boolean saveAttributeGroup(AttributeGroupForm formData) {
        // 实体转换 form->entity
        AttributeGroup entity = attributeGroupConverter.form2Entity(formData);
        return this.save(entity);
    }

    /**
     * 更新属性组
     *
     * @param id       属性组ID
     * @param formData 属性组表单对象
     * @return
     */
    @Override
    public boolean updateAttributeGroup(Long id, AttributeGroupForm formData) {
        AttributeGroup entity = attributeGroupConverter.form2Entity(formData);
        return this.updateById(entity);
    }

    /**
     * 删除属性组
     *
     * @param ids 属性组ID，多个以英文逗号(,)分割
     */
    @Override
    public void deleteAttributeGroups(String ids) {
        Assert.isTrue(StrUtil.isNotBlank(ids), "删除的属性组数据为空");
        // 逻辑删除
        List<Long> idList = Arrays.stream(ids.split(","))
                .map(Long::parseLong)
                .toList();

        for (Long groupId : idList) {
            boolean result = this.removeById(groupId);
            if (result) {
                // 删除属性组下的属性
                attributeService.remove(
                        new LambdaQueryWrapper<Attribute>().eq(Attribute::getAttributeGroupId, groupId)
                );
            }
        }
    }

    /**
     * 获取属性组选项列表
     *
     * @param categoryId 分类ID
     * @return
     */
    @Override
    public List<Option> listAttributeOptions(Long categoryId) {
        return this.list(new LambdaQueryWrapper<AttributeGroup>()
                        .eq(categoryId != null, AttributeGroup::getCategoryId, categoryId)
                        .orderByAsc(AttributeGroup::getSort)
                )
                .stream()
                .map(item -> new Option(item.getId(), item.getName()))
                .toList();
    }

}

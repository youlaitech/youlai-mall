package com.youlai.mall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.youlai.common.web.model.Option;
import com.youlai.mall.product.converter.AttrGroupConverter;
import com.youlai.mall.product.model.entity.Attr;
import com.youlai.mall.product.model.entity.AttrGroup;
import com.youlai.mall.product.mapper.AttrGroupMapper;
import com.youlai.mall.product.model.form.AttrGroupForm;
import com.youlai.mall.product.service.AttrGroupService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.mall.product.service.AttrService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.youlai.common.util.DateUtils;
import com.youlai.mall.product.model.query.AttributeGroupPageQuery;
import com.youlai.mall.product.model.bo.AttrGroupBO;
import com.youlai.mall.product.model.vo.AttributeGroupPageVO;

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
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupMapper, AttrGroup> implements AttrGroupService {

    private final AttrGroupConverter attrGroupConverter;

    private final AttrService attrService;

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
        Page<AttrGroupBO> page = new Page<>(pageNum, pageSize);

        // 格式化为数据库日期格式，避免日期比较使用格式化函数导致索引失效
        DateUtils.toDatabaseFormat(queryParams, "startTime", "endTime");

        // 查询数据
        Page<AttrGroupBO> boPage = this.baseMapper.listPagedAttributeGroups(page, queryParams);

        // 实体转换
        return attrGroupConverter.toPageVo(boPage);
    }

    /**
     * 获取属性组表单数据
     *
     * @param id 属性组ID
     * @return
     */
    @Override
    public AttrGroupForm getAttributeGroupFormData(Long id) {
        AttrGroup entity = this.getById(id);
        return attrGroupConverter.toForm(entity);
    }

    /**
     * 新增属性组
     *
     * @param formData 属性组表单对象
     * @return
     */
    @Override
    public boolean saveAttributeGroup(AttrGroupForm formData) {
        // 实体转换 form->entity
        AttrGroup entity = attrGroupConverter.toEntity(formData);
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
    public boolean updateAttributeGroup(Long id, AttrGroupForm formData) {
        AttrGroup entity = attrGroupConverter.toEntity(formData);
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
                attrService.remove(
                        new LambdaQueryWrapper<Attr>().eq(Attr::getAttributeGroupId, groupId)
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
        return this.list(new LambdaQueryWrapper<AttrGroup>()
                        .eq(categoryId != null, AttrGroup::getCategoryId, categoryId)
                        .orderByAsc(AttrGroup::getSort)
                )
                .stream()
                .map(item -> new Option(item.getId(), item.getName()))
                .toList();
    }

}

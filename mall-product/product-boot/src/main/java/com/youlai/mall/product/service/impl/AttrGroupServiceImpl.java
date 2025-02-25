package com.youlai.mall.product.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.mall.product.converter.AttrConverter;
import com.youlai.mall.product.converter.AttrGroupConverter;
import com.youlai.mall.product.mapper.AttrGroupMapper;
import com.youlai.mall.product.model.entity.Attr;
import com.youlai.mall.product.model.entity.AttrGroup;
import com.youlai.mall.product.model.form.AttrGroupForm;
import com.youlai.mall.product.model.query.AttrGroupPageQuery;
import com.youlai.mall.product.model.vo.AttrGroupPageVO;
import com.youlai.mall.product.service.AttrGroupService;
import com.youlai.mall.product.service.AttrService;
import com.youlai.mall.product.service.SpuAttrValueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 属性组服务实现类
 *
 * @author Ray.Hao
 * @since 2024-04-14
 */
@Service
@RequiredArgsConstructor
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupMapper, AttrGroup> implements AttrGroupService {

    private final AttrService attrService;

    private final AttrGroupConverter attrGroupConverter;

    private final AttrConverter attrConverter;

    private final SpuAttrValueService spuAttrValueService;

    /**
     * 获取属性组分页列表
     *
     * @param queryParams 查询参数
     * @return {@link IPage<AttrGroupPageVO>} 属性组分页列表
     */
    @Override
    public IPage<AttrGroupPageVO> listPagedAttrGroups(AttrGroupPageQuery queryParams) {
        return this.baseMapper.listPagedAttrGroups(
                new Page<>(queryParams.getPageNum(), queryParams.getPageSize()),
                queryParams
        );
    }

    /**
     * 新增属性组
     *
     * @param formData 属性组表单对象
     * @return
     */
    @Override
    public boolean saveAttrGroup(AttrGroupForm formData) {
        AttrGroup entity = attrGroupConverter.toEntity(formData);
        boolean result = this.save(entity);
        if (result) {
            // 保存属性列表
            List<AttrGroupForm.Attr> attrs = formData.getAttrs();
            List<Attr> attrList = attrConverter.convertToEntity(attrs);
            attrService.saveBatch(attrList);
        }
        return result;
    }

    /**
     * 获取属性组表单数据
     *
     * @param groupId 属性组ID
     * @return
     */
    @Override
    public AttrGroupForm getAttrGroupForm(Long groupId) {
        AttrGroup attrGroup = this.getById(groupId);
        Assert.isTrue(attrGroup != null, "属性组不存在");

        AttrGroupForm attrGroupForm = attrGroupConverter.toForm(attrGroup);

        // 属性列表
        List<Attr> attrEntities = attrService.list(new LambdaQueryWrapper<Attr>()
                .eq(Attr::getAttrGroupId, groupId)
        );
        List<AttrGroupForm.Attr> attrList = attrConverter.toForm(attrEntities);
        attrGroupForm.setAttrs(attrList);

        return attrGroupForm;
    }

    /**
     * 更新属性组
     *
     * @param groupId  属性组ID
     * @param formData 属性组表单对象
     * @return
     */
    @Override
    public boolean updateAttrGroup(Long groupId, AttrGroupForm formData) {
        AttrGroup entity = attrGroupConverter.toEntity(formData);
        boolean result = this.updateById(entity);

        if (result) {
            List<AttrGroupForm.Attr> attrList = formData.getAttrs();
            List<Attr> attrEntities = attrConverter.convertToEntity(attrList);

            // 获取当前组的所有属性
            List<Attr> currentAttrEntities = attrService.list(new LambdaQueryWrapper<Attr>()
                    .eq(Attr::getAttrGroupId, groupId)
            );

            // 获取当前数据库中存在的属性ID集合
            Set<Long> currentAttrIds = currentAttrEntities.stream()
                    .map(Attr::getId)
                    .collect(Collectors.toSet());

            // 获取新提交的属性ID集合
            Set<Long> newAttrIds = attrEntities.stream()
                    .map(Attr::getId)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());

            // 需要删除的属性ID集合（存在于数据库但不在新提交的属性中）
            Set<Long> idsToDelete = new HashSet<>(currentAttrIds);
            idsToDelete.removeAll(newAttrIds);

            // 删除不在新提交属性中的旧属性
            if (!idsToDelete.isEmpty()) {
                attrService.removeByIds(idsToDelete);
            }

            // 更新或新增属性
            for (Attr attr : attrEntities) {
                if (attr.getId() != null && currentAttrIds.contains(attr.getId())) {
                    // 更新现有属性
                    attrService.updateById(attr);
                } else {
                    // 新增属性
                    attr.setAttrGroupId(groupId);
                    attrService.save(attr);
                }
            }
        }
        return result;
    }

    /**
     * 删除属性组
     *
     * @param ids 属性组ID，多个以英文逗号(,)分割
     */
    @Override
    @Transactional
    public void deleteAttrGroups(String ids) {
        Assert.isTrue(StrUtil.isNotBlank(ids), "请选择需要删除的属性组");
        List<Long> idList = Arrays.stream(ids.split(","))
                .map(Long::parseLong)
                .toList();

        for (Long groupId : idList) {
            boolean result = this.removeById(groupId);
            if (result) {
                // 删除属性组下的属性
                attrService.removeByGroupId(groupId);
            }
        }
    }


}

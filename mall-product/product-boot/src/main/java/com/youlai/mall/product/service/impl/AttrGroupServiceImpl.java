package com.youlai.mall.product.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.mall.product.converter.AttrGroupConverter;
import com.youlai.mall.product.mapper.AttrGroupMapper;
import com.youlai.mall.product.model.entity.Attr;
import com.youlai.mall.product.model.entity.AttrGroup;
import com.youlai.mall.product.model.form.AttrGroupForm;
import com.youlai.mall.product.model.query.AttrGroupPageQuery;
import com.youlai.mall.product.model.vo.AttrGroupPageVO;
import com.youlai.mall.product.model.vo.AttributeGroupPageVO;
import com.youlai.mall.product.service.AttrGroupService;
import com.youlai.mall.product.service.AttrService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

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
                        new LambdaQueryWrapper<Attr>().eq(Attr::getAttrGroupId, groupId)
                );
            }
        }
    }


}

package com.youlai.mall.product.service.impl;

import com.youlai.common.web.model.Option;
import com.youlai.mall.product.model.entity.Attribute;
import com.youlai.mall.product.mapper.AttributeMapper;
import com.youlai.mall.product.service.AttributeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.youlai.mall.product.model.form.AttributeForm;
import com.youlai.mall.product.model.query.AttributePageQuery;
import com.youlai.mall.product.model.bo.AttributeBO;
import com.youlai.mall.product.model.vo.AttributePageVO;
import com.youlai.mall.product.converter.AttributeConverter;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.Arrays;
import java.util.List;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;

/**
 * 属性服务实现类
 *
 * @author Ray Hao
 * @since 2024/4/19
 */
@Service
@RequiredArgsConstructor
public class AttributeServiceImpl extends ServiceImpl<AttributeMapper, Attribute> implements AttributeService {

    private final AttributeConverter attributeConverter;

    /**
     * 获取属性分页列表
     *
     * @param queryParams 查询参数
     * @return {@link IPage<AttributePageVO>} 属性分页列表
     */
    @Override
    public IPage<AttributePageVO> listPagedAttributes(AttributePageQuery queryParams) {
        Page<AttributeBO> page = this.baseMapper.listPagedAttributes(
                new Page<>(queryParams.getPageNum(), queryParams.getPageSize()),
                queryParams
        );
        return attributeConverter.convertToPageVo(page);
    }

    /**
     * 获取属性表单数据
     *
     * @param id 属性ID
     * @return
     */
    @Override
    public AttributeForm getAttributeFormData(Long id) {
        Attribute entity = this.getById(id);
        return attributeConverter.convertToForm(entity);
    }

    /**
     * 新增属性
     *
     * @param formData 属性表单对象
     * @return
     */
    @Override
    public boolean saveAttribute(AttributeForm formData) {
        Attribute entity = attributeConverter.convertToEntity(formData);
        return this.save(entity);
    }

    /**
     * 更新属性
     *
     * @param id       属性ID
     * @param formData 属性表单对象
     * @return
     */
    @Override
    public boolean updateAttribute(Long id, AttributeForm formData) {
        Attribute entity = attributeConverter.convertToEntity(formData);
        return this.updateById(entity);
    }

    /**
     * 删除属性
     *
     * @param ids 属性ID，多个以英文逗号(,)分割
     * @return true|false
     */
    @Override
    public boolean deleteAttributes(String ids) {
        Assert.isTrue(StrUtil.isNotBlank(ids), "删除的属性数据为空");
        // 逻辑删除
        List<Long> idList = Arrays.stream(ids.split(","))
                .map(Long::parseLong)
                .toList();
        return this.removeByIds(idList);
    }

    /**
     * 根据分类ID获取属性组&列表
     *
     * @param categoryId 分类ID
     * @return
     */
    @Override
    public List<Option> listAttributesWithGroupByCategoryId(Long categoryId) {
        return this.baseMapper.listAttributesWithGroupByCategoryId(categoryId);
    }


}

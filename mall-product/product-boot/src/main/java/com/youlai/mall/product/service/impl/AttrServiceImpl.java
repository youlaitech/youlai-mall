package com.youlai.mall.product.service.impl;

import com.youlai.mall.product.converter.AttrConverter;
import com.youlai.mall.product.model.bo.AttrBO;
import com.youlai.mall.product.model.entity.Attr;
import com.youlai.mall.product.mapper.AttrMapper;
import com.youlai.mall.product.model.form.AttrForm;
import com.youlai.mall.product.model.vo.AttrPageVO;
import com.youlai.mall.product.model.vo.AttributeGroupVO;
import com.youlai.mall.product.model.vo.AttributeVO;
import com.youlai.mall.product.service.AttrService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.youlai.mall.product.model.query.AttributePageQuery;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.Arrays;
import java.util.List;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;

/**
 * 属性服务实现类
 *
 * @author Ray
 * @since 2024/4/19
 */
@Service
@RequiredArgsConstructor
public class AttrServiceImpl extends ServiceImpl<AttrMapper, Attr> implements AttrService {

    private final AttrConverter attributeConverter;

    /**
     * 获取属性分页列表
     *
     * @param queryParams 查询参数
     * @return {@link IPage< AttrPageVO >} 属性分页列表
     */
    @Override
    public IPage<AttrPageVO> listPagedAttributes(AttributePageQuery queryParams) {
        Page<AttrBO> page = this.baseMapper.listPagedAttributes(
                new Page<>(queryParams.getPageNum(), queryParams.getPageSize()),
                queryParams
        );
        return attributeConverter.toPageVo(page);
    }

    /**
     * 获取属性表单数据
     *
     * @param id 属性ID
     */
    @Override
    public AttrForm getAttributeFormData(Long id) {
        Attr entity = this.getById(id);
        return attributeConverter.convertToForm(entity);
    }

    /**
     * 新增属性
     *
     * @param formData 属性表单对象
     */
    @Override
    public boolean saveAttribute(AttrForm formData) {
        Attr entity = attributeConverter.toEntity(formData);
        return this.save(entity);
    }

    /**
     * 更新属性
     *
     * @param id       属性ID
     * @param formData 属性表单对象
     */
    @Override
    public boolean updateAttribute(Long id, AttrForm formData) {
        Attr entity = attributeConverter.toEntity(formData);
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
     * 获取基础属性列表
     *
     * @param categoryId 商品分类ID
     */
    @Override
    public List<AttributeGroupVO> listAttributesByCategoryId(Long categoryId) {
        return this.baseMapper.listAttributesByCategoryId(categoryId);
    }

    /**
     * 获取销售属性列表
     *
     * @param categoryId 商品分类ID
     */
    @Override
    public List<AttributeVO> listSaleAttributes(Long categoryId) {
        return this.baseMapper.listSaleAttributes(categoryId);
    }

}

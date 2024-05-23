package com.youlai.mall.product.mapper;

import com.youlai.common.web.model.Option;
import com.youlai.mall.product.model.entity.Attribute;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.mall.product.model.bo.AttributeBO;
import com.youlai.mall.product.model.query.AttributePageQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 属性 Mapper 接口
 *
 * @author Ray Hao
 * @since 2024/4/19
 */

@Mapper
public interface AttributeMapper extends BaseMapper<Attribute> {

    /**
     * 获取属性分页列表
     *
     * @param page
     * @param queryParams 查询参数
     * @return
     */
    Page<AttributeBO> listPagedAttributes(Page<AttributeBO> page, AttributePageQuery queryParams);

    /**
     * 根据分类ID获取属性组&列表
     *
     * @param categoryId 分类ID
     * @return
     */
    List<Option> listAttributesWithGroupByCategoryId(Long categoryId);
}

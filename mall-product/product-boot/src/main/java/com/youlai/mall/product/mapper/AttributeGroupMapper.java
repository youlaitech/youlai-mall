package com.youlai.mall.product.mapper;

import com.youlai.mall.product.model.entity.AttributeGroup;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.mall.product.model.bo.AttributeGroupBO;
import com.youlai.mall.product.model.query.AttributeGroupPageQuery;
import org.apache.ibatis.annotations.Mapper;

/**
 * 属性组 Mapper 接口
 *
 * @author Ray Hao
 * @since 2024-04-19
 */

@Mapper
public interface AttributeGroupMapper extends BaseMapper<AttributeGroup> {

    /**
     * 获取属性组分页列表
     *
     * @param page
     * @param queryParams 查询参数
     * @return
     */
    Page<AttributeGroupBO> listPagedAttributeGroups(Page<AttributeGroupBO> page, AttributeGroupPageQuery queryParams);

}

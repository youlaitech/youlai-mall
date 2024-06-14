package com.youlai.mall.product.mapper;

import com.youlai.mall.product.model.bo.AttrBO;
import com.youlai.mall.product.model.entity.Attr;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.mall.product.model.query.AttrGroupPageQuery;
import com.youlai.mall.product.model.vo.AttributeGroupVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 属性 Mapper 接口
 *
 * @author Ray Hao
 * @since 2024/4/19
 */

@Mapper
public interface AttrMapper extends BaseMapper<Attr> {

    /**
     * 获取属性分页列表
     *
     * @param page        分页参数
     * @param queryParams 查询参数
     */
    Page<AttrBO> listPagedAttrGroups(Page<AttrBO> page, AttrGroupPageQuery queryParams);


    /**
     * 根据分类ID获取基础属性列表
     *
     * @param categoryId 分类ID
     */
    List<AttributeGroupVO> listAttributesByCategoryId(Long categoryId);
}

package com.youlai.mall.product.mapper;

import com.youlai.mall.product.model.bo.AttrBO;
import com.youlai.mall.product.model.entity.CategoryAttrEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 商品属性持久层接口
 *
 * @author Ray.Hao
 * @since 2024/4/19
 */

@Mapper
public interface CategoryAttrMapper extends BaseMapper<CategoryAttrEntity> {

    /**
     * 获取分类下的属性列表
     *
     * @param categoryId 分类ID
     */
    List<AttrBO> getCategoryAttrs(Long categoryId);
}

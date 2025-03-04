package com.youlai.mall.product.mapper;

import com.youlai.mall.product.model.bo.AttrBO;
import com.youlai.mall.product.model.entity.Attr;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youlai.mall.product.model.vo.AttrVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 商品属性持久层接口
 *
 * @author Ray.Hao
 * @since 2024/4/19
 */

@Mapper
public interface AttrMapper extends BaseMapper<Attr> {

    /**
     * 获取分类下的属性列表
     *
     * @param categoryId 分类ID
     */
    List<AttrBO> getCategoryAttrs(Long categoryId);
}

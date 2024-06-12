package com.youlai.mall.product.mapper;

import com.youlai.mall.product.model.entity.Attr;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.mall.product.model.bo.AttributeBO;
import com.youlai.mall.product.model.query.AttributePageQuery;
import com.youlai.mall.product.model.vo.AttributeGroupVO;
import com.youlai.mall.product.model.vo.AttributeVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 属性 Mapper 接口
 *
 * @author Ray Hao
 * @since 2024/4/19
 */

@Mapper
public interface AttributeMapper extends BaseMapper<Attr> {

    /**
     * 获取属性分页列表
     *
     * @param page        分页参数
     * @param queryParams 查询参数
     */
    Page<AttributeBO> listPagedAttributes(Page<AttributeBO> page, AttributePageQuery queryParams);


    /**
     * 根据分类ID获取基础属性列表
     *
     * @param categoryId 分类ID
     */
    List<AttributeGroupVO> listBaseAttributes(Long categoryId);

    /**
     * 根据分类ID获取销售属性列表
     *
     * @param categoryId 分类ID
     */
    List<AttributeVO> listSaleAttributes(Long categoryId);
}

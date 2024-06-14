package com.youlai.mall.product.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.youlai.mall.product.model.entity.AttrGroup;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.mall.product.model.query.AttrGroupPageQuery;
import com.youlai.mall.product.model.vo.AttrGroupPageVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 属性组 Mapper 接口
 *
 * @author Ray Hao
 * @since 2024-04-19
 */

@Mapper
public interface AttrGroupMapper extends BaseMapper<AttrGroup> {

    /**
     * 获取属性组分页列表
     *
     * @param page
     * @param queryParams 查询参数
     * @return
     */
    IPage<AttrGroupPageVO> listPagedAttrGroups(Page<AttrGroupPageVO> page, AttrGroupPageQuery queryParams);
}

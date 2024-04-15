package com.youlai.mall.pms.mapper;

import com.youlai.mall.pms.model.entity.SkuSpecValue;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.mall.pms.model.bo.SkuSpecValueBO;
import com.youlai.mall.pms.model.query.SkuSpecValuePageQuery;
import org.apache.ibatis.annotations.Mapper;

/**
 * SKU规格值 Mapper 接口
 *
 * @author Ray Hao
 * @since 2024-04-14
 */

@Mapper
public interface SkuSpecValueMapper extends BaseMapper<SkuSpecValue> {

    /**
     * 获取用户分页列表
     *
     * @param page
     * @param queryParams 查询参数
     * @return
     */
    Page<SkuSpecValueBO> listPagedSkuSpecValues(Page<SkuSpecValueBO> page, SkuSpecValuePageQuery queryParams);

}

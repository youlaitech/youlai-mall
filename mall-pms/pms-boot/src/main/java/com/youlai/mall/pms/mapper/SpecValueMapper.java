package com.youlai.mall.pms.mapper;

import com.youlai.mall.pms.model.entity.SpecValue;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.mall.pms.model.bo.SpecValueBO;
import com.youlai.mall.pms.model.query.SpecValuePageQuery;
import org.apache.ibatis.annotations.Mapper;

/**
 *  Mapper 接口
 *
 * @author Ray Hao
 * @since 2024-04-14
 */

@Mapper
public interface SpecValueMapper extends BaseMapper<SpecValue> {

    /**
     * 获取用户分页列表
     *
     * @param page
     * @param queryParams 查询参数
     * @return
     */
    Page<SpecValueBO> listPagedSpecValues(Page<SpecValueBO> page, SpecValuePageQuery queryParams);

}

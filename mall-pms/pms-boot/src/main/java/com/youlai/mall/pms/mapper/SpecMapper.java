package com.youlai.mall.pms.mapper;

import com.youlai.mall.pms.model.entity.Spec;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.mall.pms.model.bo.SpecBO;
import com.youlai.mall.pms.model.query.SpecPageQuery;
import org.apache.ibatis.annotations.Mapper;

/**
 *  Mapper 接口
 *
 * @author Ray Hao
 * @since 2024-04-14
 */

@Mapper
public interface SpecMapper extends BaseMapper<Spec> {

    /**
     * 获取用户分页列表
     *
     * @param page
     * @param queryParams 查询参数
     * @return
     */
    Page<SpecBO> listPagedSpecs(Page<SpecBO> page, SpecPageQuery queryParams);

}

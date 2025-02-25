package com.youlai.mall.product.mapper;

import com.youlai.mall.product.model.entity.Spec;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.mall.product.model.bo.SpecBO;
import com.youlai.mall.product.model.query.SpecPageQuery;
import com.youlai.mall.product.model.vo.SpecPageVO;
import com.youlai.mall.product.model.vo.SpecVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 *  Mapper 接口
 *
 * @author Ray.Hao
 * @since 2024-06-13
 */

@Mapper
public interface SpecMapper extends BaseMapper<Spec> {

    /**
     * 分页列表
     *
     * @param page 分页对象
     * @param queryParams 查询参数
     * @return
     */
    Page<SpecBO> listPagedSpecs(Page<SpecBO> page, SpecPageQuery queryParams);

    /**
     * 根据分类ID查询规格列表
     *
     * @param categoryId
     * @return
     */
    List<SpecVO> listSpecsByCategoryId(Long categoryId);
}

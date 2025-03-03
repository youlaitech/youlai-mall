package com.youlai.mall.product.mapper;

import com.youlai.mall.product.model.entity.Spec;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.mall.product.model.bo.SpecBO;
import com.youlai.mall.product.model.query.SpecPageQuery;
import com.youlai.mall.product.model.vo.SpecVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 商品规格持久层接口
 *
 * @author Ray.Hao
 * @since 2024-06-13
 */

@Mapper
public interface SpecMapper extends BaseMapper<Spec> {

    /**
     * 规格分页列表
     *
     * @param page        分页对象
     * @param queryParams 查询参数
     * @return 规格分页列表
     */
    Page<SpecBO> getSpecPage(Page<SpecBO> page, SpecPageQuery queryParams);

    /**
     * 根据分类ID查询规格列表
     *
     * @param categoryId 分类ID
     * @return 规格列表
     */
    List<SpecVO> listSpecsByCategoryId(Long categoryId);
}

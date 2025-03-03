package com.youlai.mall.product.mapper;

import com.youlai.common.core.model.Option;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 品牌分类关联 Mapper 接口
 *
 * @author Ray.Hao
 * @since 2024-05-06
 */

@Mapper
public interface BrandCategoryMapper extends BaseMapper<BrandCategory> {

    /**
     * 获取品牌的分类列表
     */
    List<Option> getCategoryOptions(Long brandId);
}

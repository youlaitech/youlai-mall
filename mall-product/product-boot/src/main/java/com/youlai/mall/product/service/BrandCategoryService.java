package com.youlai.mall.product.service;

import com.youlai.mall.product.model.entity.BrandCategory;
import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.mall.product.model.query.BrandCategoryQuery;
import com.youlai.mall.product.model.vo.BrandCategoryVO;

import java.util.List;

/**
 * 品牌分类关联 服务类
 *
 * @author Ray Hao
 * @since 2024-05-06
 */
public interface BrandCategoryService extends IService<BrandCategory> {

    /**
     * 品牌分类关联列表
     * <p>
     * {@link List<BrandCategoryVO>} 品牌分类关联分页列表
     */
    List<BrandCategoryVO> listBrandCategories(BrandCategoryQuery queryParams);


    /**
     * 新增品牌分类关联
     *
     * @param brandId     品牌ID
     * @param categoryIds 分类ID列表
     */
    void saveBrandCategories(Long brandId, List<Long> categoryIds);


}

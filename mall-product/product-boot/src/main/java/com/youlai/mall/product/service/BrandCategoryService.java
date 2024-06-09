package com.youlai.mall.product.service;

import com.youlai.common.web.model.Option;
import com.youlai.mall.product.model.entity.BrandCategory;
import com.baomidou.mybatisplus.extension.service.IService;

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
     */
    List<Option> listCategoriesByBrandId(Long brandId);


    /**
     * 新增品牌分类关联
     *
     * @param brandId     品牌ID
     * @param categoryIds 分类ID列表
     */
    void saveBrandCategories(Long brandId, List<Long> categoryIds);


}

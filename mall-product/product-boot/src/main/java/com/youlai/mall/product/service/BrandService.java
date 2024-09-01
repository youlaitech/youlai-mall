package com.youlai.mall.product.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.common.core.model.Option;
import com.youlai.mall.product.model.entity.Brand;
import com.youlai.mall.product.model.query.BrandPageQuery;
import com.youlai.mall.product.model.vo.BrandPageVO;

import java.util.List;

/**
 * 品牌接口
 *
 * @author Ray Hao
 * @since 2024/7/2
 */
public interface BrandService extends IService<Brand> {

    /**
     * 品牌分页列表
     *
     * @param queryParams 查询参数
     * @return 品牌分页列表
     */
    Page<BrandPageVO> listPagedBrands(BrandPageQuery queryParams);


    /**
     * 品牌下拉选项
     *
     * @return 品牌下拉选项
     */
    List<Option<Long>> listBrandOptions(Long categoryId);


}

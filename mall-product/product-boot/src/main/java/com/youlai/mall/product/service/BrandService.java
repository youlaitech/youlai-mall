package com.youlai.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.common.web.model.Option;
import com.youlai.mall.product.model.entity.Brand;

import java.util.List;

/**
 * 品牌接口
 *
 * @author Ray Hao
 * @since 2024/7/2
 */
public interface BrandService extends IService<Brand> {

    /**
     * 查询品牌下拉选项
     *
     * @return 品牌下拉选项
     */
    List<Option<Long>> listBrandOptions();
}

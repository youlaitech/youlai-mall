package com.youlai.mall.product.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.common.web.model.Option;
import com.youlai.mall.product.converter.BrandCategoryConverter;
import com.youlai.mall.product.converter.BrandConverter;
import com.youlai.mall.product.mapper.BrandMapper;
import com.youlai.mall.product.model.entity.Brand;
import com.youlai.mall.product.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 品牌接服务现类
 *
 * @author Ray Hao
 * @since 2024/5/4
 */
@Service
@RequiredArgsConstructor
public class BrandServiceImpl extends ServiceImpl<BrandMapper, Brand> implements BrandService {

    private final BrandConverter brandConverter;

    /**
     * 查询品牌下拉选项
     *
     * @return 品牌下拉选项
     */

    @Override
    public List<Option<Long>> listBrandOptions() {

        List<Brand> list = this.list();


        return List.of();
    }
}

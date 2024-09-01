package com.youlai.mall.product.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.common.core.model.Option;
import com.youlai.mall.product.converter.BrandConverter;
import com.youlai.mall.product.mapper.BrandMapper;
import com.youlai.mall.product.model.entity.Brand;
import com.youlai.mall.product.model.query.BrandPageQuery;
import com.youlai.mall.product.model.vo.BrandPageVO;
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
     * 品牌分页列表
     *
     * @param queryParams 查询参数
     * @return 品牌分页列表
     */
    @Override
    public Page<BrandPageVO> listPagedBrands(BrandPageQuery queryParams) {
        String keywords = queryParams.getKeywords();
        int pageNum = queryParams.getPageNum();
        int pageSize = queryParams.getPageSize();
        Page<Brand> page = this.page(new Page<>(pageNum, pageSize),
                new LambdaQueryWrapper<Brand>().like(StrUtil.isNotBlank(keywords), Brand::getName, keywords)
                        .orderByAsc(Brand::getSort)
        );
        return brandConverter.toPageVo(page);
    }

    /**
     * 品牌下拉选项
     *
     * @param categoryId 分类ID
     */
    @Override
    public List<Option<Long>> listBrandOptions(Long categoryId){
        List<Brand> list = this.baseMapper.listBrandOptions(categoryId);
        return brandConverter.convertToOption(list);
    }
}

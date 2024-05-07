package com.youlai.mall.product.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.youlai.common.security.util.SecurityUtils;
import com.youlai.mall.product.model.entity.Brand;
import com.youlai.mall.product.model.entity.BrandCategory;
import com.youlai.mall.product.mapper.BrandCategoryMapper;
import com.youlai.mall.product.model.vo.BrandCategoryVO;
import com.youlai.mall.product.service.BrandCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.youlai.mall.product.model.form.BrandCategoryForm;
import com.youlai.mall.product.model.query.BrandCategoryQuery;
import com.youlai.mall.product.converter.BrandCategoryConverter;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;

/**
 * 品牌分类关联服务实现类
 *
 * @author Ray Hao
 * @since 2024-05-06
 */
@Service
@RequiredArgsConstructor
public class BrandCategoryServiceImpl extends ServiceImpl<BrandCategoryMapper, BrandCategory> implements BrandCategoryService {

    private final BrandCategoryConverter brandCategoryConverter;

    /**
     * 获取品牌分类关联分页列表
     *
     * @param queryParams 查询参数
     * @return {@link List<BrandCategoryVO>} 品牌分类关联分页列表
     */
    @Override
    public List<BrandCategoryVO> listBrandCategories(BrandCategoryQuery queryParams) {
        List<BrandCategory> list = this.list(new LambdaQueryWrapper<BrandCategory>()
                .eq(queryParams.getBrandId() != null, BrandCategory::getBrandId, queryParams.getBrandId())
        );

        return brandCategoryConverter.entity2Vo(list);
    }


    /**
     * 新增品牌分类关联
     *
     * @param brandId     品牌ID
     * @param categoryIds 分类ID列表
     */
    @Override
    public void saveBrandCategories(Long brandId, List<Long> categoryIds) {

        // 获取已存在的品牌分类关联
        List<Long> existCategoryIds = this.list(
                        new LambdaQueryWrapper<>(BrandCategory.class).eq(BrandCategory::getBrandId, brandId))
                .stream()
                .map(BrandCategory::getCategoryId)
                .toList();

        // 删除已存在的品牌分类关联
        List<Long> removeCategoryIds = existCategoryIds.stream()
                .filter(item -> !categoryIds.contains(item))
                .toList();

        if (CollectionUtil.isNotEmpty(removeCategoryIds)) {
            this.remove(
                    new LambdaQueryWrapper<>(BrandCategory.class)
                            .eq(BrandCategory::getBrandId, brandId)
                            .in(BrandCategory::getId, removeCategoryIds)
            );
        }

        // 新增品牌分类关联
        List<Long> newCategoryIds = categoryIds.stream()
                .filter(item -> !existCategoryIds.contains(item))
                .toList();

        if (CollectionUtil.isNotEmpty(newCategoryIds)) {
            Long userId = SecurityUtils.getUserId();
            LocalDateTime now = LocalDateTime.now();

            List<BrandCategory> newBrandCategories = newCategoryIds.stream()
                    .map(categoryId -> {
                        BrandCategory brandCategory = new BrandCategory();
                        brandCategory.setBrandId(brandId);
                        brandCategory.setCategoryId(categoryId);
                        brandCategory.setCreateBy(userId);
                        brandCategory.setCreateTime(now);
                        return brandCategory;
                    })
                    .toList();

            this.saveBatch(newBrandCategories);
        }

    }

}

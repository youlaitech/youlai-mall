package com.youlai.mall.product.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.youlai.common.core.model.Option;
import com.youlai.mall.product.model.entity.BrandCategory;
import com.youlai.mall.product.mapper.BrandCategoryMapper;
import com.youlai.mall.product.service.BrandCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * 品牌分类关联服务实现类
 *
 * @author Ray Hao
 * @since 2024-05-06
 */
@Service
public class BrandCategoryServiceImpl extends ServiceImpl<BrandCategoryMapper, BrandCategory> implements BrandCategoryService {

    /**
     * 获取品牌的分类ID列表
     */
    @Override
    public List<Option> getCategoryOptions(Long brandId) {
        if (brandId == null) {
            return Collections.emptyList();
        }
        return this.baseMapper.getCategoryOptions(brandId);
    }


    /**
     * 新增品牌分类关联
     *
     * @param brandId     品牌ID
     * @param categoryIds 分类ID列表
     */
    @Override
    @Transactional
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
                            .in(BrandCategory::getCategoryId, removeCategoryIds)
            );
        }

        // 新增品牌分类关联
        List<Long> newCategoryIds = categoryIds.stream()
                .filter(item -> !existCategoryIds.contains(item))
                .toList();

        if (CollectionUtil.isNotEmpty(newCategoryIds)) {

            List<BrandCategory> newBrandCategories = newCategoryIds.stream()
                    .map(categoryId -> {
                        BrandCategory brandCategory = new BrandCategory();
                        brandCategory.setBrandId(brandId);
                        brandCategory.setCategoryId(categoryId);
                        return brandCategory;
                    })
                    .toList();

            this.saveBatch(newBrandCategories);
        }

    }

}

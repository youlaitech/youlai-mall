package com.youlai.mall.sale.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.common.base.IBaseEnum;
import com.youlai.mall.sale.converter.CouponConverter;
import com.youlai.mall.sale.enums.CouponUseScopeEnum;
import com.youlai.mall.sale.mapper.CouponMapper;
import com.youlai.mall.sale.model.entity.Coupon;
import com.youlai.mall.sale.model.entity.CouponCategory;
import com.youlai.mall.sale.model.entity.CouponSpu;
import com.youlai.mall.sale.model.form.CouponForm;
import com.youlai.mall.sale.model.query.CouponPageQuery;
import com.youlai.mall.sale.model.vo.CouponPageVO;
import com.youlai.mall.sale.service.CouponCategoryService;
import com.youlai.mall.sale.service.CouponService;
import com.youlai.mall.sale.service.CouponSpuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 优惠券业务实现类
 *
 * @author Ray.Hao
 * @since 2022/5/29
 */
@Service
@RequiredArgsConstructor
public class CouponServiceImpl extends ServiceImpl<CouponMapper, Coupon> implements CouponService {

    private final CouponConverter couponConverter;

    private final CouponCategoryService couponCategoryService;

    private final CouponSpuService couponSpuService;

    /**
     * 优惠券分页列表
     *
     * @param queryParams 优惠券分页查询参数
     * @return
     */
    @Override
    public Page<CouponPageVO> getCouponPage(CouponPageQuery queryParams) {
        Page<CouponPageVO> page = new Page<>(queryParams.getPageNum(), queryParams.getPageSize());
        // 查询数据
        List<Coupon> couponList = this.baseMapper.getCouponPage(page, queryParams);
        // 实体转换
        List<CouponPageVO> records = couponConverter.toPageVo(couponList);
        page.setRecords(records);
        return page;
    }

    /**
     * 优惠券表单数据
     *
     * @param couponId 优惠券ID
     * @return
     */
    @Override
    public CouponForm getCouponFormData(Long couponId) {
        Coupon entity = this.getById(couponId);
        // 实体转换entity->form
        CouponForm couponForm = couponConverter.toForm(entity);

        Integer useScope = couponForm.getUseScope();

        // 使用位运算判断使用范围
        if (CouponUseScopeEnum.hasScope(useScope, CouponUseScopeEnum.CATEGORY)) {
            // 包含指定分类
            List<CouponCategory> couponCategoryList = couponCategoryService.list(new LambdaQueryWrapper<CouponCategory>()
                    .eq(CouponCategory::getCouponId, couponId)
                    .select(CouponCategory::getCategoryId)
            );
            List<Long> categoryIds = couponCategoryList.stream().map(CouponCategory::getCategoryId).collect(Collectors.toList());
            couponForm.setCategoryIds(categoryIds);
        }

        if (CouponUseScopeEnum.hasScope(useScope, CouponUseScopeEnum.SPU)) {
            // 包含指定商品
            List<CouponSpu> couponSpuList = couponSpuService.list(new LambdaQueryWrapper<CouponSpu>()
                    .eq(CouponSpu::getCouponId, couponId)
                    .select(CouponSpu::getSpuId)
            );
            List<Long> spuIds = couponSpuList.stream().map(CouponSpu::getSpuId).collect(Collectors.toList());
            couponForm.setSpuIds(spuIds);
        }

        return couponForm;
    }

    /**
     * 新增优惠券
     *
     * @param couponForm 优惠券表单
     * @return
     */
    @Override
    @Transactional
    public boolean saveCoupon(CouponForm couponForm) {
        Coupon entity = couponConverter.toEntity(couponForm);
        boolean result = this.save(entity);

        if (result) {
            // 根据使用范围保存关联关系
            Long couponId = entity.getId();
            Integer useScope = couponForm.getUseScope();

            // 处理分类关联
            if (CouponUseScopeEnum.hasScope(useScope, CouponUseScopeEnum.CATEGORY) || 
                useScope.equals(CouponUseScopeEnum.CATEGORY_AND_SPU.getValue())) {
                
                List<Long> categoryIds = couponForm.getCategoryIds();
                if (CollectionUtil.isNotEmpty(categoryIds)) {
                    List<CouponCategory> couponCategories = categoryIds.stream()
                            .map(categoryId -> new CouponCategory().setCouponId(couponId).setCategoryId(categoryId))
                            .collect(Collectors.toList());
                    couponCategoryService.saveBatch(couponCategories);
                }
            }

            // 处理商品关联
            if (CouponUseScopeEnum.hasScope(useScope, CouponUseScopeEnum.SPU) || 
                useScope.equals(CouponUseScopeEnum.CATEGORY_AND_SPU.getValue())) {
                
                List<Long> spuIds = couponForm.getSpuIds();
                if (CollectionUtil.isNotEmpty(spuIds)) {
                    List<CouponSpu> couponSpuList = spuIds.stream()
                            .map(spuId -> new CouponSpu().setCouponId(couponId).setSpuId(spuId))
                            .collect(Collectors.toList());
                    couponSpuService.saveBatch(couponSpuList);
                }
            }
        }
        return result;
    }

    /**
     * 修改优惠券
     *
     * @param couponId   优惠券ID
     * @param couponForm 优惠券表单
     * @return
     */
    @Override
    @Transactional
    public boolean updateCoupon(Long couponId, CouponForm couponForm) {
        Coupon entity = couponConverter.toEntity(couponForm);
        boolean result = this.updateById(entity);

        if (result) {
            // 根据使用范围保存关联关系
            Integer useScope = couponForm.getUseScope();

            // 处理分类关联
            if (!CouponUseScopeEnum.hasScope(useScope, CouponUseScopeEnum.CATEGORY) && 
                !useScope.equals(CouponUseScopeEnum.CATEGORY_AND_SPU.getValue())) {
                
                // 如果不包含分类范围，删除所有分类关联
                couponCategoryService.remove(new LambdaQueryWrapper<CouponCategory>()
                        .eq(CouponCategory::getCouponId, couponId)
                );
            } else {
                // 包含分类范围，更新分类关联
                List<Long> categoryIds = couponForm.getCategoryIds();
                if (CollectionUtil.isNotEmpty(categoryIds)) {
                    couponCategoryService.remove(new LambdaQueryWrapper<CouponCategory>()
                            .eq(CouponCategory::getCouponId, couponId)
                    );
                    List<CouponCategory> couponCategories = categoryIds.stream()
                            .map(categoryId -> new CouponCategory().setCouponId(couponId).setCategoryId(categoryId))
                            .collect(Collectors.toList());
                    couponCategoryService.saveBatch(couponCategories);
                }
            }

            // 处理商品关联
            if (!CouponUseScopeEnum.hasScope(useScope, CouponUseScopeEnum.SPU) && 
                !useScope.equals(CouponUseScopeEnum.CATEGORY_AND_SPU.getValue())) {
                
                // 如果不包含商品范围，删除所有商品关联
                couponSpuService.remove(new LambdaQueryWrapper<CouponSpu>()
                        .eq(CouponSpu::getCouponId, couponId)
                );
            } else {
                // 包含商品范围，更新商品关联
                List<Long> spuIds = couponForm.getSpuIds();
                if (CollectionUtil.isNotEmpty(spuIds)) {
                    couponSpuService.remove(new LambdaQueryWrapper<CouponSpu>()
                            .eq(CouponSpu::getCouponId, couponId)
                    );
                    List<CouponSpu> couponSpuList = spuIds.stream()
                            .map(spuId -> new CouponSpu().setCouponId(couponId).setSpuId(spuId))
                            .collect(Collectors.toList());
                    couponSpuService.saveBatch(couponSpuList);
                }
            }
        }

        return result;
    }

    /**
     * 删除优惠券
     *
     * @param idsStr 优惠券ID，多个以英文逗号(,)分割
     * @return
     */
    @Override
    @Transactional
    public boolean deleteCoupons(String idsStr) {
        Assert.isTrue(StrUtil.isNotBlank(idsStr), "删除的优惠券数据为空");
        // 逻辑删除
        List<Long> ids = Arrays.stream(idsStr.split(","))
                .map(Long::parseLong)
                .collect(Collectors.toList());
        return this.removeByIds(ids);
    }
}





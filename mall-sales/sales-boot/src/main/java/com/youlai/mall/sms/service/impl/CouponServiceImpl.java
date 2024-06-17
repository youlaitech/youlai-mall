package com.youlai.mall.sms.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.common.base.IBaseEnum;
import com.youlai.mall.sms.enums.CouponApplicationScopeEnum;
import com.youlai.mall.sms.converter.CouponConverter;
import com.youlai.mall.sms.mapper.CouponMapper;
import com.youlai.mall.sms.model.entity.Coupon;
import com.youlai.mall.sms.model.entity.CouponSpu;
import com.youlai.mall.sms.model.entity.CouponSpuCategory;
import com.youlai.mall.sms.model.form.CouponForm;
import com.youlai.mall.sms.model.query.CouponPageQuery;
import com.youlai.mall.sms.model.vo.CouponPageVO;
import com.youlai.mall.sms.service.CouponService;
import com.youlai.mall.sms.service.CouponSpuCategoryService;
import com.youlai.mall.sms.service.CouponSpuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 优惠券业务实现类
 *
 * @author Ray
 * @since 2022/5/29
 */
@Service
@RequiredArgsConstructor
public class CouponServiceImpl extends ServiceImpl<CouponMapper, Coupon> implements CouponService {

    private final CouponConverter couponConverter;

    private final CouponSpuCategoryService couponSpuCategoryService;

    private final CouponSpuService couponSpuService;

    /**
     * 优惠券分页列表
     *
     * @param queryParams
     * @return
     */
    @Override
    public Page<CouponPageVO> getCouponPage(CouponPageQuery queryParams) {
        Page<CouponPageVO> page = new Page<>(queryParams.getPageNum(), queryParams.getPageSize());
        // 查询数据
        List<Coupon> couponList = this.baseMapper.getCouponPage(page, queryParams);
        // 实体转换
        List<CouponPageVO> records = couponConverter.entity2PageVO(couponList);
        page.setRecords(records);
        return page;
    }

    /**
     * 优惠券表单数据
     *
     * @param couponId
     * @return
     */
    @Override
    public CouponForm getCouponFormData(Long couponId) {
        Coupon entity = this.getById(couponId);
        // 实体转换entity->form
        CouponForm couponForm = couponConverter.toForm(entity);

        Integer applicationScope = couponForm.getApplicationScope();

        CouponApplicationScopeEnum applicationScopeEnum = IBaseEnum.getEnumByValue(applicationScope, CouponApplicationScopeEnum.class);
        if (applicationScopeEnum != null) {
            switch (applicationScopeEnum) {
                case SPU_CATEGORY:
                    List<CouponSpuCategory> couponSpuCategoryList = couponSpuCategoryService.list(new LambdaQueryWrapper<CouponSpuCategory>()
                            .eq(CouponSpuCategory::getCouponId, couponId)
                            .select(CouponSpuCategory::getCategoryId)
                    );
                    List<Long> categoryIds = couponSpuCategoryList.stream().map(item -> item.getCategoryId()).collect(Collectors.toList());
                    couponForm.setSpuCategoryIds(categoryIds);
                    break;
                case SPU:
                    List<CouponSpu> couponSpuList = couponSpuService.list(new LambdaQueryWrapper<CouponSpu>()
                            .eq(CouponSpu::getCouponId, couponId)
                            .select(CouponSpu::getSpuId)
                    );
                    List<Long> spuIds = couponSpuList.stream().map(item -> item.getSpuId()).collect(Collectors.toList());
                    couponForm.setSpuIds(spuIds);
                    break;
            }
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
    public boolean saveCoupon(CouponForm couponForm) {
        Coupon entity = couponConverter.toEntity(couponForm);
        boolean result = this.save(entity);

        if (result) {
            // 根据优惠券适用商品范围保存对应的关联关系
            // 指定商品分类： 优惠券 <-> 商品分类
            // 指定商品： 优惠券 <-> 商品
            Long couponId = entity.getId();
            Integer applicationScope = couponForm.getApplicationScope();
            CouponApplicationScopeEnum applicationScopeEnum = IBaseEnum.getEnumByValue(applicationScope, CouponApplicationScopeEnum.class);

            Assert.isTrue(applicationScopeEnum != null, "请指定优惠券适用范围");
            switch (applicationScopeEnum) {
                case SPU_CATEGORY:
                    List<Long> spuCategoryIds = couponForm.getSpuCategoryIds();
                    if (CollectionUtil.isNotEmpty(spuCategoryIds)) {
                        List<CouponSpuCategory> smsCouponSpuCategories = spuCategoryIds.stream()
                                .map(spuCategoryId -> new CouponSpuCategory().setCouponId(couponId).setCategoryId(spuCategoryId))
                                .collect(Collectors.toList());
                        couponSpuCategoryService.saveBatch(smsCouponSpuCategories);
                    }
                    break;

                case SPU:
                    List<Long> spuIds = couponForm.getSpuIds();
                    if (CollectionUtil.isNotEmpty(spuIds)) {
                        List<CouponSpu> couponSpuList = spuIds.stream()
                                .map(spuId -> new CouponSpu().setCouponId(couponId).setSpuId(spuId))
                                .collect(Collectors.toList());
                        couponSpuService.saveBatch(couponSpuList);
                    }
                    break;
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
    public boolean updateCoupon(Long couponId, CouponForm couponForm) {
        Coupon entity = couponConverter.toEntity(couponForm);
        boolean result = this.updateById(entity);

        if (result) {
            // 根据优惠券适用商品范围保存对应的关联关系
            // 全场通用： 删除所有关联
            // 指定商品分类： 优惠券 <-> 商品分类
            // 指定商品： 优惠券 <-> 商品
            Integer applicationScope = couponForm.getApplicationScope();
            CouponApplicationScopeEnum applicationScopeEnum = IBaseEnum.getEnumByValue(applicationScope, CouponApplicationScopeEnum.class);

            Assert.isTrue(applicationScopeEnum != null, "请指定优惠券适用范围");
            switch (applicationScopeEnum) {
                case ALL:
                    couponSpuCategoryService.remove(new LambdaQueryWrapper<CouponSpuCategory>()
                            .eq(CouponSpuCategory::getCouponId, couponId)
                    );
                    couponSpuService.remove(new LambdaQueryWrapper<CouponSpu>()
                            .eq(CouponSpu::getCouponId, couponId)
                    );

                    break;
                case SPU_CATEGORY:
                    List<Long> spuCategoryIds = couponForm.getSpuCategoryIds();
                    if (CollectionUtil.isNotEmpty(spuCategoryIds)) {
                        couponSpuCategoryService.remove(new LambdaQueryWrapper<CouponSpuCategory>()
                                .eq(CouponSpuCategory::getCouponId, couponId)
                        );
                        List<CouponSpuCategory> smsCouponSpuCategories = spuCategoryIds.stream()
                                .map(spuCategoryId -> new CouponSpuCategory().setCouponId(couponId)
                                        .setCategoryId(spuCategoryId))
                                .collect(Collectors.toList());
                        couponSpuCategoryService.saveBatch(smsCouponSpuCategories);
                    }
                    break;
                case SPU:
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
                    break;
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





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
import com.youlai.mall.sms.mapper.SmsCouponMapper;
import com.youlai.mall.sms.pojo.entity.SmsCoupon;
import com.youlai.mall.sms.pojo.entity.SmsCouponSpu;
import com.youlai.mall.sms.pojo.entity.SmsCouponSpuCategory;
import com.youlai.mall.sms.pojo.form.CouponForm;
import com.youlai.mall.sms.pojo.query.CouponPageQuery;
import com.youlai.mall.sms.pojo.vo.CouponPageVO;
import com.youlai.mall.sms.service.SmsCouponService;
import com.youlai.mall.sms.service.SmsCouponSpuCategoryService;
import com.youlai.mall.sms.service.SmsCouponSpuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 优惠券业务实现类
 *
 * @author haoxr
 * @date 2022/5/29
 */
@Service
@RequiredArgsConstructor
public class SmsCouponServiceImpl extends ServiceImpl<SmsCouponMapper, SmsCoupon> implements SmsCouponService {

    private final CouponConverter couponConverter;

    private final SmsCouponSpuCategoryService smsCouponSpuCategoryService;

    private final SmsCouponSpuService smsCouponSpuService;

    /**
     * 优惠券分页列表
     *
     * @param queryParams
     * @return
     */
    @Override
    public Page<CouponPageVO> listCouponPages(CouponPageQuery queryParams) {
        Page<CouponPageVO> page = new Page<>(queryParams.getPageNum(), queryParams.getPageSize());
        // 查询数据
        List<SmsCoupon> couponList = this.baseMapper.listCouponPages(page, queryParams);
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
        SmsCoupon entity = this.getById(couponId);
        // 实体转换entity->form
        CouponForm couponForm = couponConverter.entity2Form(entity);

        Integer applicationScope = couponForm.getApplicationScope();

        CouponApplicationScopeEnum applicationScopeEnum = IBaseEnum.getEnumByValue(applicationScope, CouponApplicationScopeEnum.class);
        if (applicationScopeEnum != null) {
            switch (applicationScopeEnum) {
                case SPU_CATEGORY:
                    List<SmsCouponSpuCategory> couponSpuCategoryList = smsCouponSpuCategoryService.list(new LambdaQueryWrapper<SmsCouponSpuCategory>()
                            .eq(SmsCouponSpuCategory::getCouponId, couponId)
                            .select(SmsCouponSpuCategory::getCategoryId)
                    );
                    List<Long> categoryIds = couponSpuCategoryList.stream().map(item -> item.getCategoryId()).collect(Collectors.toList());
                    couponForm.setSpuCategoryIds(categoryIds);
                    break;
                case SPU:
                    List<SmsCouponSpu> couponSpuList = smsCouponSpuService.list(new LambdaQueryWrapper<SmsCouponSpu>()
                            .eq(SmsCouponSpu::getCouponId, couponId)
                            .select(SmsCouponSpu::getSpuId)
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
        SmsCoupon entity = couponConverter.form2Entity(couponForm);
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
                        List<SmsCouponSpuCategory> smsCouponSpuCategories = spuCategoryIds.stream()
                                .map(spuCategoryId -> new SmsCouponSpuCategory().setCouponId(couponId).setCategoryId(spuCategoryId))
                                .collect(Collectors.toList());
                        smsCouponSpuCategoryService.saveBatch(smsCouponSpuCategories);
                    }
                    break;

                case SPU:
                    List<Long> spuIds = couponForm.getSpuIds();
                    if (CollectionUtil.isNotEmpty(spuIds)) {
                        List<SmsCouponSpu> smsCouponSpuList = spuIds.stream()
                                .map(spuId -> new SmsCouponSpu().setCouponId(couponId).setSpuId(spuId))
                                .collect(Collectors.toList());
                        smsCouponSpuService.saveBatch(smsCouponSpuList);
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
        SmsCoupon entity = couponConverter.form2Entity(couponForm);
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
                    smsCouponSpuCategoryService.remove(new LambdaQueryWrapper<SmsCouponSpuCategory>()
                            .eq(SmsCouponSpuCategory::getCouponId, couponId)
                    );
                    smsCouponSpuService.remove(new LambdaQueryWrapper<SmsCouponSpu>()
                            .eq(SmsCouponSpu::getCouponId, couponId)
                    );

                    break;
                case SPU_CATEGORY:
                    List<Long> spuCategoryIds = couponForm.getSpuCategoryIds();
                    if (CollectionUtil.isNotEmpty(spuCategoryIds)) {
                        smsCouponSpuCategoryService.remove(new LambdaQueryWrapper<SmsCouponSpuCategory>()
                                .eq(SmsCouponSpuCategory::getCouponId, couponId)
                        );
                        List<SmsCouponSpuCategory> smsCouponSpuCategories = spuCategoryIds.stream()
                                .map(spuCategoryId -> new SmsCouponSpuCategory().setCouponId(couponId)
                                        .setCategoryId(spuCategoryId))
                                .collect(Collectors.toList());
                        smsCouponSpuCategoryService.saveBatch(smsCouponSpuCategories);
                    }
                    break;
                case SPU:
                    List<Long> spuIds = couponForm.getSpuIds();
                    if (CollectionUtil.isNotEmpty(spuIds)) {
                        smsCouponSpuService.remove(new LambdaQueryWrapper<SmsCouponSpu>()
                                .eq(SmsCouponSpu::getCouponId, couponId)
                        );
                        List<SmsCouponSpu> smsCouponSpuList = spuIds.stream()
                                .map(spuId -> new SmsCouponSpu().setCouponId(couponId).setSpuId(spuId))
                                .collect(Collectors.toList());
                        smsCouponSpuService.saveBatch(smsCouponSpuList);
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
    public boolean deleteCoupons(String idsStr) {
        Assert.isTrue(StrUtil.isNotBlank(idsStr), "删除的优惠券数据为空");
        // 逻辑删除
        List<Long> ids = Arrays.asList(idsStr.split(",")).stream()
                .map(idStr -> Long.parseLong(idStr))
                .collect(Collectors.toList());
        boolean result = this.removeByIds(ids);
        return result;
    }


}





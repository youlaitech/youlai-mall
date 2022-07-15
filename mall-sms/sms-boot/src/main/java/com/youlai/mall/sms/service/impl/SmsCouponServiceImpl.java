package com.youlai.mall.sms.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.mall.sms.converter.SmsCouponConverter;
import com.youlai.mall.sms.mapper.SmsCouponMapper;
import com.youlai.mall.sms.pojo.entity.SmsCoupon;
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

    private final SmsCouponConverter smsCouponConverter;

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
        List<CouponPageVO> records = smsCouponConverter.entity2PageVO(couponList);
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
        CouponForm couponForm = smsCouponConverter.entity2Form(entity);
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
        SmsCoupon smsCoupon = smsCouponConverter.form2Entity(couponForm);
        boolean result = this.save(smsCoupon);

        if (result) {

            Long couponId = smsCoupon.getId();
            List<Long> spuCategoryIds = couponForm.getSpuCategoryIds();

            Integer applicationScope = couponForm.getApplicationScope();


            if (CollectionUtil.isNotEmpty(spuCategoryIds)) {
                List<SmsCouponSpuCategory> smsCouponSpuCategories = spuCategoryIds.stream().map(spuCategoryId -> {
                    SmsCouponSpuCategory smsCouponSpuCategory = new SmsCouponSpuCategory();
                    smsCouponSpuCategory.setCouponId(couponId);
                    smsCouponSpuCategory.setCouponId(spuCategoryId);
                    return smsCouponSpuCategory;
                }).collect(Collectors.toList());

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
        SmsCoupon entity = smsCouponConverter.form2Entity(couponForm);
        boolean result = this.updateById(entity);

        if (result) {
            List<SmsCouponSpuCategory> list = smsCouponSpuCategoryService.list(new LambdaQueryWrapper<SmsCouponSpuCategory>().eq(SmsCouponSpuCategory::getCouponId, couponId));
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
        List<Long> ids = Arrays.asList(idsStr.split(",")).stream().map(idStr -> Long.parseLong(idStr)).collect(Collectors.toList());
        boolean result = this.removeByIds(ids);
        return result;
    }


}





package com.youlai.mall.sale.converter;

import com.youlai.mall.sale.model.entity.Coupon;
import com.youlai.mall.sale.model.form.CouponForm;
import com.youlai.mall.sale.model.vo.CouponPageVO;
import com.youlai.mall.sale.util.CouponUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

/**
 * 优惠券对象转换器
 *
 * @author Ray.Hao
 * @since 2022/5/29
 */
@Mapper(componentModel = "spring")
public interface CouponConverter {

    @Mappings({
            @Mapping(target = "statusLabel", expression = "java(com.youlai.mall.sale.util.CouponUtils.getStatusLabel(entity.getStatus()))"),
            @Mapping(target = "typeLabel", expression = "java(com.youlai.mall.sale.util.CouponUtils.getTypeLabel(entity.getType()))"),
            @Mapping(target = "useScopeLabel", expression = "java(com.youlai.mall.sale.util.CouponUtils.getUseScopeLabel(entity.getUseScope()))"),
            @Mapping(target = "discountValueLabel", expression = "java(com.youlai.mall.sale.util.CouponUtils.getDiscountValueLabel(entity.getType(), entity.getDiscountAmount(), entity.getDiscountLimit()))"),
            @Mapping(target = "validityPeriodLabel", expression = "java(com.youlai.mall.sale.util.CouponUtils.getValidityPeriodLabel(entity.getValidType(), entity.getValidDays(), entity.getValidStartTime(), entity.getValidEndTime()))"),
            @Mapping(target = "minAmountLabel", expression = "java(com.youlai.mall.sale.util.CouponUtils.getMinAmountLabel(entity.getMinAmount()))"),
            @Mapping(target = "canSuperimposeLabel", expression = "java(com.youlai.mall.sale.util.CouponUtils.getSuperimposeLabel(entity.getCanSuperimpose()))"),
            @Mapping(target = "firstOrderOnlyLabel", expression = "java(com.youlai.mall.sale.util.CouponUtils.getFirstOrderOnlyLabel(entity.getFirstOrderOnly()))")
    })
    CouponPageVO toPageVo(Coupon entity);

    List<CouponPageVO> toPageVo(List<Coupon> entities);

    Coupon toEntity(CouponForm form);

    CouponForm toForm(Coupon entity);
}
package com.youlai.mall.sale.converter;


import com.youlai.mall.sale.model.entity.Coupon;
import com.youlai.mall.sale.model.form.CouponForm;
import com.youlai.mall.sale.model.vo.CouponPageVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

/**
 * 优惠券对象转换器
 *
 * @author Ray
 * @since 2022/5/29
 */
@Mapper(componentModel = "spring")
public interface CouponConverter {

    @Mappings({
            @Mapping(target = "platformLabel", expression = "java(com.youlai.common.base.IBaseEnum.getLabelByValue(entity.getPlatform(), com.youlai.mall.sale.enums.PlatformEnum.class))"),
            @Mapping(target = "typeLabel", expression = "java(com.youlai.common.base.IBaseEnum.getLabelByValue(entity.getType(), com.youlai.mall.sale.enums.CouponTypeEnum.class))"),
            @Mapping(target = "faceValueLabel", expression = "java(com.youlai.mall.sale.util.CouponUtils.getFaceValue(entity.getType(),entity.getFaceValue(),entity.getDiscount()))"),
            @Mapping(
                    target = "validityPeriodLabel",
                    expression = "java(com.youlai.mall.sale.util.CouponUtils.getValidityPeriod(entity.getValidityPeriodType(),entity.getValidityDays(),entity.getValidityStartTime(),entity.getValidityEndTime()))"
            ),
            @Mapping(target = "minPointLabel", expression = "java(cn.hutool.core.util.NumberUtil.toStr(cn.hutool.core.util.NumberUtil.div(entity.getMinPoint(),new java.math.BigDecimal(100)).setScale(2)))"),
    })
    CouponPageVO entity2PageVO(Coupon entity);


    List<CouponPageVO> entity2PageVO(List<Coupon> entities);


    @Mappings({
            @Mapping(target = "discount",expression = "java(cn.hutool.core.util.NumberUtil.div(form.getDiscount(),10L))"),
    })
    Coupon toEntity(CouponForm form);


    @Mappings({
            @Mapping(target = "discount",expression = "java(cn.hutool.core.util.NumberUtil.mul(entity.getDiscount(),10L))"),
    })
    CouponForm convertToForm(Coupon entity);
}
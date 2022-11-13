package com.youlai.mall.sms.converter;


import com.youlai.mall.sms.pojo.entity.SmsCoupon;
import com.youlai.mall.sms.pojo.form.CouponForm;
import com.youlai.mall.sms.pojo.vo.CouponPageVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

/**
 * 优惠券对象转换器
 *
 * @author haoxr
 * @date 2022/5/29
 */
@Mapper(componentModel = "spring")
public interface CouponConverter {

    @Mappings({
            @Mapping(target = "platformLabel", expression = "java(com.youlai.common.base.IBaseEnum.getLabelByValue(entity.getPlatform(), com.youlai.mall.sms.enums.PlatformEnum.class))"),
            @Mapping(target = "typeLabel", expression = "java(com.youlai.common.base.IBaseEnum.getLabelByValue(entity.getType(), com.youlai.mall.sms.enums.CouponTypeEnum.class))"),
            @Mapping(target = "faceValueLabel", expression = "java(com.youlai.mall.sms.util.CouponUtils.getFaceValue(entity.getType(),entity.getFaceValue(),entity.getDiscount()))"),
            @Mapping(
                    target = "validityPeriodLabel",
                    expression = "java(com.youlai.mall.sms.util.CouponUtils.getValidityPeriod(entity.getValidityPeriodType(),entity.getValidityDays(),entity.getValidityBeginTime(),entity.getValidityBeginTime()))"
            ),
            @Mapping(target = "minPointLabel", expression = "java(cn.hutool.core.util.NumberUtil.toStr(cn.hutool.core.util.NumberUtil.div(entity.getMinPoint(),new java.math.BigDecimal(100)).setScale(2)))"),
    })
    CouponPageVO entity2PageVO(SmsCoupon entity);


    List<CouponPageVO> entity2PageVO(List<SmsCoupon> entities);


    @Mappings({
            @Mapping(target = "discount",expression = "java(cn.hutool.core.util.NumberUtil.div(form.getDiscount(),10L))"),
    })
    SmsCoupon form2Entity(CouponForm form);


    @Mappings({
            @Mapping(target = "discount",expression = "java(cn.hutool.core.util.NumberUtil.mul(entity.getDiscount(),10L))"),
    })
    CouponForm entity2Form(SmsCoupon entity);
}
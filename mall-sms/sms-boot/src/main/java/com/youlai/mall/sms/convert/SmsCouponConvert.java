package com.youlai.mall.sms.convert;


import com.youlai.mall.sms.pojo.entity.SmsCoupon;
import com.youlai.mall.sms.pojo.vo.CouponPageVO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 优惠券实体MapStruct转换器
 */
@Mapper(componentModel = "spring")
public interface SmsCouponConvert {

    List<CouponPageVO> entity2PageVO(List<SmsCoupon> couponList);

}
package com.youlai.mall.sms.convert;


import com.youlai.mall.sms.pojo.entity.SmsCoupon;
import com.youlai.mall.sms.pojo.form.CouponForm;
import com.youlai.mall.sms.pojo.vo.CouponPageVO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 优惠券实体转换器
 *
 * @author haoxr
 * @date 2022/5/29
 */
@Mapper(componentModel = "spring")
public interface SmsCouponConvert {

    List<CouponPageVO> entity2PageVO(List<SmsCoupon> entities);

    SmsCoupon form2Entity(CouponForm form);

}
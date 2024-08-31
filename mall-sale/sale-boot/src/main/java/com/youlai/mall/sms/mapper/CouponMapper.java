package com.youlai.mall.sms.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.mall.sms.model.entity.Coupon;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youlai.mall.sms.model.query.CouponPageQuery;
import com.youlai.mall.sms.model.vo.CouponPageVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface CouponMapper extends BaseMapper<Coupon> {

    List<Coupon> getCouponPage(Page<CouponPageVO> page, CouponPageQuery queryParams);
}





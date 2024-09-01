package com.youlai.mall.sale.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.mall.sale.model.entity.Coupon;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youlai.mall.sale.model.query.CouponPageQuery;
import com.youlai.mall.sale.model.vo.CouponPageVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface CouponMapper extends BaseMapper<Coupon> {

    List<Coupon> getCouponPage(Page<CouponPageVO> page, CouponPageQuery queryParams);
}





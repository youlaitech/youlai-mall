package com.youlai.mall.sms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youlai.mall.sms.pojo.domain.SmsCoupon;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SmsCouponMapper extends BaseMapper<SmsCoupon> {
    int deleteByPrimaryKey(Long id);

    int insertSelective(SmsCoupon record);

    SmsCoupon selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SmsCoupon record);

    int updateByPrimaryKey(SmsCoupon record);

    int updateTakeStock(@Param("id") String couponId);
}
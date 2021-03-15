package com.youlai.mall.sms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.mall.sms.pojo.domain.SmsCouponRecord;

/**
 * @author huawei
 * @desc 优惠券领券记录业务接口
 * @email huawei_code@163.com
 * @date 2021/3/15
 */
public interface ICouponRecordService extends IService<SmsCouponRecord> {

    /**
     * 用户领券
     * @param couponId
     */
    void add(String couponId);
}

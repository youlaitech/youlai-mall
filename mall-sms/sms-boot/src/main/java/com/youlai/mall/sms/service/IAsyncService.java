package com.youlai.mall.sms.service;

import com.youlai.mall.sms.pojo.domain.SmsCouponTemplate;

/**
 * @author xinyi
 * @desc：异步服务接口
 * @date 2021/6/27
 */
public interface IAsyncService {

    /**
     * 通过优惠券模板异步的创建优惠券码
     * @param template {@link SmsCouponTemplate} 优惠券模板实体
     */
    void asyncConstructCouponByTemplate(SmsCouponTemplate template);
}

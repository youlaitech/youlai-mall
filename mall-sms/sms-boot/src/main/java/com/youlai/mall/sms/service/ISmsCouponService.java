package com.youlai.mall.sms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.mall.sms.pojo.domain.SmsCoupon;
import com.youlai.mall.sms.pojo.form.CouponForm;
import com.youlai.mall.sms.pojo.query.CouponPageQuery;
import com.youlai.mall.sms.pojo.vo.CouponTemplateVO;
import com.youlai.mall.sms.pojo.vo.SmsCouponVO;

import java.util.List;

/**
 * @author huawei
 * @desc
 * @email huawei_code@163.com
 * @date 2021/3/14
 */
public interface ISmsCouponService extends IService<SmsCoupon> {

    /**
     * 查询用户可用优惠券列表
     * @param userId 用户ID
     * @return {@link CouponTemplateVO} 优惠券模板列表
     */
    List<CouponTemplateVO> findAvailableTemplate(Long userId);


    /**
     * 领取优惠券
     *
     * @param userId 用户ID
     * @param templateId 优惠券模板ID
     */
    void receive(Long userId, String templateId);

    /**
     * 根据用户ID和优惠券状态查询优惠券列表
     *
     * @param userId 用户ID
     * @param state  优惠券状态
     * @return {@link SmsCouponVO} 优惠券列表
     */
    List<SmsCoupon> findCouponsByState(Long userId, Integer state);

    /**
     * 获取优惠券详情
     *
     * @param couponId 优惠券ID
     */
    SmsCouponVO detail(String couponId);

    /**
     * 新增优惠券
     *
     * @param form 新增提交表单
     */
    void add(CouponForm form);

    /**
     * 修改优惠券
     *
     * @param form 修改优惠券提交表单
     */
    void modify(CouponForm form);

    IPage<SmsCoupon> pageQuery(CouponPageQuery query);

    /**
     * 根据已领券数量
     *
     * @param couponId
     * @return
     */
    int updateTakeStock(String couponId);
}

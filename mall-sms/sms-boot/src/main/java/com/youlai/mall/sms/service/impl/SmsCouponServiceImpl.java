package com.youlai.mall.sms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.common.base.BasePageQuery;
import com.youlai.common.web.util.BeanMapperUtils;
import com.youlai.mall.sms.mapper.SmsCouponDao;
import com.youlai.mall.sms.pojo.domain.SmsCoupon;
import com.youlai.mall.sms.pojo.form.CouponForm;
import com.youlai.mall.sms.pojo.vo.SmsCouponVO;
import com.youlai.mall.sms.service.ISmsCouponService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author huawei
 * @desc 优惠券服务业务实现类
 * @email huawei_code@163.com
 * @date 2021/3/14
 */
@Service
@Slf4j
public class SmsCouponServiceImpl extends ServiceImpl<SmsCouponDao, SmsCoupon> implements ISmsCouponService {
    @Override
    public SmsCouponVO detail(String couponId) {
        log.info("根据优惠券ID获取优惠券详情，couponId={}", couponId);
        SmsCoupon coupon = this.getById(couponId);
        return BeanMapperUtils.map(coupon, SmsCouponVO.class);
    }

    @Override
    public void add(CouponForm form) {
        log.info("新增优惠券，form={}", form);
        SmsCoupon coupon = BeanMapperUtils.map(form, SmsCoupon.class);
        coupon.setId(null);
        this.save(coupon);
    }

    @Override
    public void modify(CouponForm form) {
        log.info("新增优惠券，form={}", form);
        SmsCoupon coupon = BeanMapperUtils.map(form, SmsCoupon.class);
        // TODO 如果该优惠券已经有领取记录，则优惠券价格、限领账户、过期时间等相关参数不能修改
        // 这里没有考虑太复杂，如有需要自行处理
        this.updateById(coupon);
    }

    @Override
    public IPage<SmsCoupon> pageQuery(Page<SmsCoupon> page, BasePageQuery query) {
        QueryWrapper<SmsCoupon> queryWrapper = new QueryWrapper<>();
        IPage<SmsCoupon> iPage = this.page(page,queryWrapper);
        return iPage;
    }

    @Override
    public int updateTakeStock(String couponId) {
        return this.updateTakeStock(couponId);
    }
}

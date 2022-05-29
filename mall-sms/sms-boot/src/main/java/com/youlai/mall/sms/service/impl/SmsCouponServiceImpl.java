package com.youlai.mall.sms.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.mall.sms.convert.SmsCouponConvert;
import com.youlai.mall.sms.mapper.SmsCouponMapper;
import com.youlai.mall.sms.pojo.entity.SmsCoupon;
import com.youlai.mall.sms.pojo.form.CouponForm;
import com.youlai.mall.sms.pojo.query.CouponPageQuery;
import com.youlai.mall.sms.pojo.vo.CouponPageVO;
import com.youlai.mall.sms.service.SmsCouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 优惠券业务实现类
 *
 * @author haoxr
 * @date 2022/5/29
 */
@Service
@RequiredArgsConstructor
public class SmsCouponServiceImpl extends ServiceImpl<SmsCouponMapper, SmsCoupon>
        implements SmsCouponService {

    private final SmsCouponConvert smsCouponConvert;

    /**
     * 优惠券分页列表
     *
     * @param queryParams
     * @return
     */
    @Override
    public Page<CouponPageVO> listCouponsPage(CouponPageQuery queryParams) {
        Page<CouponPageVO> page = new Page<>(queryParams.getPageNum(), queryParams.getPageSize());
        // 查询数据
        List<SmsCoupon> couponList = this.baseMapper.listCouponsPage(page, queryParams);
        // 实体转换
        List<CouponPageVO> records = smsCouponConvert.entity2PageVO(couponList);
        page.setRecords(records);
        return page;
    }

    /**
     * 新增优惠券
     *
     * @param couponForm
     * @return
     */
    @Override
    public boolean saveCoupon(CouponForm couponForm) {
        SmsCoupon smsCoupon = smsCouponConvert.form2Entity(couponForm);
        boolean result = this.save(smsCoupon);
        return result;
    }
}





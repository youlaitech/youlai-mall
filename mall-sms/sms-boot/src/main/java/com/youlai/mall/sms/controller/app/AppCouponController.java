package com.youlai.mall.sms.controller.app;

import com.youlai.common.result.Result;
import com.youlai.common.web.util.MemberUtils;
import com.youlai.mall.sms.util.BeanMapperUtils;
import com.youlai.common.web.util.JwtUtils;
import com.youlai.mall.sms.pojo.domain.SmsCoupon;
import com.youlai.mall.sms.pojo.vo.CouponTemplateVO;
import com.youlai.mall.sms.pojo.vo.SmsCouponVO;
import com.youlai.mall.sms.service.ISmsCouponService;
import com.youlai.mall.sms.service.ISmsCouponTemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author xinyi
 * @desc: 用户优惠券
 * @date 2021/7/7
 */
@Api(tags = "「移动端」优惠券")
@RestController
@RequestMapping("/api-app/v1/coupon/")
@Slf4j
public class AppCouponController {

    @Autowired
    private ISmsCouponService couponService;

    @Autowired
    private ISmsCouponTemplateService couponTemplateService;

    @ApiOperation("查看可领取优惠券模板列表")
    @GetMapping("/template")
    public Result<List<CouponTemplateVO>> findAvailableTemplate() {
        List<CouponTemplateVO> availableTemplate = couponService.findAvailableTemplate(MemberUtils.getMemberId());
        return Result.success(availableTemplate);
    }

    @ApiOperation("用户领取优惠券")
    @GetMapping("/receive")
    public Result receive(@ApiParam(value = "优惠券模板ID")
                          @RequestParam("templateId") String templateId) {
        couponService.receive(MemberUtils.getMemberId(), templateId);
        return Result.success();
    }

    @ApiOperation("查询用户已领取优惠券列表")
    @GetMapping("/list")
    public Result<List<SmsCouponVO>> list(@ApiParam(value = "优惠券模板ID", defaultValue = "1")
                                          @RequestParam(value = "state", required = false) Integer state) {
        List<SmsCoupon> coupons = couponService.findCouponsByState(MemberUtils.getMemberId(), state);
        return Result.success(BeanMapperUtils.mapList(coupons, SmsCouponVO.class));
    }

}

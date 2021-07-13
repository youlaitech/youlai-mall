package com.youlai.mall.sms.controller.app;

import com.youlai.common.result.Result;
import com.youlai.common.web.util.BeanMapperUtils;
import com.youlai.common.web.util.JwtUtils;
import com.youlai.mall.sms.pojo.domain.SmsCoupon;
import com.youlai.mall.sms.pojo.enums.CouponStateEnum;
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
@Api(tags = "优惠券(移动端接口)")
@RestController
@RequestMapping("/api-app/v1/coupon/")
@Slf4j
public class CouponController {

    @Autowired
    private ISmsCouponService couponService;

    @Autowired
    private ISmsCouponTemplateService couponTemplateService;

    @ApiOperation("查看可领取优惠券模板列表")
    @GetMapping("/template")
    public Result<List<CouponTemplateVO>> findAvailableTemplate() {
        couponService.findAvailableTemplate(JwtUtils.getUserId());
        return Result.success();
    }

    @ApiOperation("用户领取优惠券")
    @GetMapping("receive")
    public Result receive(@ApiParam(value = "优惠券模板ID")
                          @RequestParam("templateId") String templateId) {
        couponService.receive(JwtUtils.getUserId(), templateId);
        return Result.success();
    }

    @ApiOperation("查询用户已领取优惠券列表")
    @GetMapping("/list")
    public Result<List<SmsCouponVO>> list() {
        List<SmsCoupon> coupons = couponService.findCouponsByState(JwtUtils.getUserId(), CouponStateEnum.USABLE.getCode());
        return Result.success(BeanMapperUtils.mapList(coupons, SmsCouponVO.class));
    }

}

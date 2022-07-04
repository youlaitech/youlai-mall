package com.youlai.mall.sms.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.common.result.PageResult;
import com.youlai.common.result.Result;
import com.youlai.mall.sms.pojo.form.CouponForm;
import com.youlai.mall.sms.pojo.query.CouponPageQuery;
import com.youlai.mall.sms.pojo.vo.CouponPageVO;
import com.youlai.mall.sms.service.SmsCouponService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "「管理端」优惠券管理")
@RestController
@RequestMapping("/api/v1/coupons")
@RequiredArgsConstructor
public class SmsCouponController {

    private final SmsCouponService couponService;

    @ApiOperation(value = "优惠券分页列表")
    @GetMapping("/pages")
    public PageResult listCouponPages(CouponPageQuery queryParams) {
        Page<CouponPageVO> result = couponService.listCouponPages(queryParams);
        return PageResult.success(result);
    }

    @ApiOperation(value = "优惠券表单数据")
    @GetMapping("/{couponId}/form_data")
    public Result<CouponForm> getCouponFormData(@ApiParam(value = "优惠券ID") @PathVariable Long couponId) {
        CouponForm couponForm = couponService.getCouponFormData(couponId);
        return Result.success(couponForm);
    }

    @ApiOperation("新增优惠券")
    @PostMapping
    public Result saveCoupon(@RequestBody @Valid CouponForm couponForm) {
        boolean result = couponService.saveCoupon(couponForm);
        return Result.judge(result);
    }

    @ApiOperation("修改优惠券")
    @PutMapping("/{couponId}")
    public Result updateCoupon(
            @PathVariable Long couponId,
            @RequestBody @Valid CouponForm couponForm
    ) {
        boolean result = couponService.updateCoupon(couponId,couponForm);
        return Result.judge(result);
    }

    @ApiOperation(value = "删除优惠券")
    @DeleteMapping("/{ids}")
    public Result deleteCoupons(@ApiParam("用户ID，多个以英文逗号(,)分割") @PathVariable String ids) {
        boolean result = couponService.deleteCoupons(ids);
        return Result.judge(result);
    }
}

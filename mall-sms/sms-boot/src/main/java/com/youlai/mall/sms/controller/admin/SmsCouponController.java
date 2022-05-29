package com.youlai.mall.sms.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.common.result.PageResult;
import com.youlai.mall.sms.pojo.query.CouponPageQuery;
import com.youlai.mall.sms.pojo.vo.CouponPageVO;
import com.youlai.mall.sms.service.SmsCouponService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "「系统端」优惠券管理")
@RestController
@RequestMapping("/api/v1/coupons")
@RequiredArgsConstructor
public class SmsCouponController {

    private final SmsCouponService smsCouponService;

    @ApiOperation(value = "优惠券分页列表")
    @GetMapping("/pagelist")
    public PageResult listAdvertsPage(CouponPageQuery queryParams) {

        Page<CouponPageVO> result = smsCouponService.listCouponsPage(queryParams);
        return PageResult.success(result);
    }

}

package com.fly4j.yshop.sms.controller.app;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.fly4j.yshop.sms.pojo.dto.app.AppCouponDTO;
import com.fly4j.yshop.sms.pojo.entity.SmsCoupon;
import com.fly4j.yshop.sms.service.ISmsCouponService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author haoxianrui
 * @since 2020-04-21
 **/

@RestController
@RequestMapping("/api.app/v1/coupons")
@Api(tags = "APP-优惠券API")
public class AppCouponController {

    @Autowired
    private ISmsCouponService iSmsCouponService;

    @Autowired
    private DozerBeanMapper dozerBeanMapper;


    @ApiOperation(value = "优惠券列表", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "limit", value = "返回结果条数", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "user_id", value = "用户ID", paramType = "query", dataType = "Long")
    })
    @GetMapping()
    public R list(
            @RequestParam(required = false) Integer limit,
            @RequestParam(required = false) Integer user_id
    ) {
        List<SmsCoupon> list = iSmsCouponService.list(new LambdaQueryWrapper<SmsCoupon>()
                .eq(user_id != null, SmsCoupon::getUser_id, user_id)
                .orderByDesc(SmsCoupon::getCreate_time)
                .last(limit != null, "LIMIT " + limit)
        );
        List<AppCouponDTO> resultList = list.stream().map(item -> dozerBeanMapper.map(item, AppCouponDTO.class))
                .collect(Collectors.toList());
        return R.ok(resultList);
    }
}

package com.youlai.mall.sms.controller.admin;

import com.youlai.common.result.Result;
import com.youlai.mall.sms.pojo.form.CouponForm;
import com.youlai.mall.sms.service.ISmsCouponService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author huawei
 * @desc 优惠券管理服务
 * @email huawei_code@163.com
 * @date 2021/3/14
 */
@Api(tags = "「系统端」优惠券管理")
@RestController
@RequestMapping("/api/v1/coupon")
public class CouponController {

    @Autowired
    private ISmsCouponService couponService;

//    @ApiOperation("优惠券分页列表查询")
//    @GetMapping("/page")
//    public Result page(@ApiParam("条件分页请求入参") BasePageQuery query) {
//        Page<SmsCoupon> page = new Page<>(query.getPageNum(), query.getPageSize());
//        return Result.success(couponService.pageQuery(page, query));
//    }

    @ApiOperation("查询优惠券详情")
    @RequestMapping("/{couponId}/detail")
    public Result detail(@ApiParam("删除商品分页列表查询") @PathVariable("couponId") String couponId) {
        return Result.success(couponService.detail(couponId));
    }

    /**
     * 新增优惠券
     *
     * @return
     */
    @ApiOperation("新增优惠券")
    @PostMapping()
    public Result add(@ApiParam("新增优惠券提交表单") @Validated @RequestBody CouponForm form) {
        couponService.add(form);
        return Result.success();
    }

    /**
     * 修改优惠券
     *
     * @return
     */
    @ApiOperation("修改优惠券")
    @PutMapping()
    public Result modify(@ApiParam("修改优惠券提交表单") @Validated @RequestBody CouponForm form) {
        if (form.getId() == null) {
            return Result.failed("优惠券ID不能为空");
        }
        couponService.modify(form);
        return Result.success();
    }

    /**
     * 删除优惠券
     *
     * @return
     */
    @ApiOperation("删除优惠券")
    @DeleteMapping()
    public Result del(@ApiParam("优惠券ID") @RequestParam("couponId") String couponId) {
        couponService.removeById(couponId);
        return Result.success();
    }
}

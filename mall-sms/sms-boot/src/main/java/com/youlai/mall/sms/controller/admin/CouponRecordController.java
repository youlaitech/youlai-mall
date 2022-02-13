package com.youlai.mall.sms.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.common.base.BasePageQuery;
import com.youlai.common.result.Result;
import com.youlai.common.web.util.MemberUtils;
import com.youlai.mall.sms.pojo.domain.SmsCouponRecord;
import com.youlai.mall.sms.service.ICouponRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author huawei
 * @desc 优惠券领券记录服务
 * @email huawei_code@163.com
 * @date 2021/3/15
 */
@Api(tags = "「系统端」优惠券领券记录")
@RestController
@RequestMapping("/api/v1/coupon_record")
@RequiredArgsConstructor
public class CouponRecordController {

    private final ICouponRecordService couponRecordService;

    @ApiOperation(value = "分页获取会员领券记录")
    @GetMapping("/page")
    public Result page(BasePageQuery pageQuery) {
        Page<SmsCouponRecord> page = new Page<>(pageQuery.getPageNum(), pageQuery.getPageSize());
        IPage<SmsCouponRecord> result = couponRecordService.page(page, new LambdaQueryWrapper<SmsCouponRecord>()
                .eq(SmsCouponRecord::getUserId, MemberUtils.getMemberId())
                .orderByDesc(SmsCouponRecord::getGmtCreate));
        return Result.success(result);
    }

    @ApiOperation(value = "获取优惠券记录详情")
    @GetMapping("/{id}/detail")
    public Result detail(@ApiParam(value = "优惠券记录ID") @PathVariable("id") String id) {
        Long userId = MemberUtils.getMemberId();
        QueryWrapper<SmsCouponRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId).eq("id", id);
        SmsCouponRecord result = couponRecordService.getOne(queryWrapper);
        if (result == null) {
            return Result.failed("优惠券记录不存在");
        }
        return Result.success(result);
    }

    /**
     * 用户领券功能
     * 1、查询优惠券是否真实存在
     * 2、校验优惠券信息（是否有效、是否超额领取、是否过期）
     * 3、
     *
     * @param couponId
     * @return
     */
    @ApiOperation(value = "用户领券功能")
    @PostMapping()
    public Result add(@ApiParam(name = "couponId", value = "优惠券ID", required = true)
                      @RequestParam("couponId") String couponId) {
        couponRecordService.add(couponId);
        return Result.success();

    }
}

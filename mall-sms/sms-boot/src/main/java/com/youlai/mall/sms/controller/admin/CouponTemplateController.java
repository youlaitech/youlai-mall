package com.youlai.mall.sms.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.youlai.common.result.PageResult;
import com.youlai.common.result.Result;
import com.youlai.mall.sms.pojo.domain.SmsCoupon;
import com.youlai.mall.sms.pojo.form.CouponTemplateForm;
import com.youlai.mall.sms.pojo.query.CouponPageQuery;
import com.youlai.mall.sms.pojo.vo.SmsCouponTemplateInfoVO;
import com.youlai.mall.sms.pojo.vo.SmsCouponTemplateVO;
import com.youlai.mall.sms.service.ISmsCouponService;
import com.youlai.mall.sms.service.ISmsCouponTemplateService;
import com.youlai.mall.sms.service.ITemplateBaseService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author xinyi
 * @desc：优惠券模板(管理台API接口)
 * @date 2021/6/26
 */
@Api(tags = "「系统端」优惠券模板")
@RestController
@RequestMapping("/api/v1/coupon_template")
@Slf4j
public class CouponTemplateController {

    @Autowired
    private ITemplateBaseService templateBaseService;

    @Autowired
    private ISmsCouponTemplateService couponTemplateService;

    @Autowired
    private ISmsCouponService couponService;

    @ApiOperation(value = "列表分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "limit", value = "每页数量", paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "name", value = "广告名称", paramType = "query", dataType = "String")
    })
    @GetMapping
    public PageResult page(Integer page, Integer limit, String name) {
        IPage<SmsCouponTemplateVO> result = couponTemplateService.pageQuery(page, limit, name);
        return PageResult.success(result);
    }

    /**
     * 创建优惠券模板
     * 1、优惠券模板基础数据校验
     * 2、生成优惠券模板实体数据
     * 3、初始由运营人员创建的优惠券模板为待审核状态
     * 3.1、待审核状态优惠券可以修改，删除
     *
     * @param form 提交表单
     * @return result
     */
    @ApiOperation(value = "创建优惠券模板")
    @PostMapping()
    public Result<Object> createTemplate(@ApiParam(value = "优惠券模板构建表单")
                                         @Validated
                                         @RequestBody CouponTemplateForm form) {

        log.info("Create Coupon Template , Form:{}", form);
        couponTemplateService.createTemplate(form);
        return Result.success();
    }

    /**
     * 修改优惠券模板
     * 待审核状态优惠券模板，允许修改
     * 1、优惠券模板基础数据校验
     * 2、修改优惠券模板实体数据
     *
     * @param form 提交表单
     * @return result
     */
    @ApiOperation(value = "修改优惠券模板")
    @PutMapping("/template")
    public Result<Object> updateTemplate(@ApiParam(value = "优惠券模板构建表单")
                                         @Validated @RequestBody CouponTemplateForm form) {
        log.info("Update Coupon Template，form:{}", form);
        couponTemplateService.updateTemplate(form);
        return Result.success();
    }

    /**
     * 优惠券模板审核功能
     * 优惠券创建完成后，更高级别人员审核通过，在系统中批量生成优惠券码
     * 审核完成后的优惠券无法修改，删除
     *
     * @param id 优惠券模板id
     * @return
     */
    @ApiOperation(value = "优惠券模板审核")
    @GetMapping("/template/confirm")
    public Result<Object> templateConfirm(@ApiParam(value = "优惠券模板ID") @RequestParam("id") String id) {
        couponTemplateService.confirmTemplate(id);
        return Result.success();
    }

    @ApiOperation(value = "获取优惠券模板详情")
    @GetMapping("/template/info")
    public Result<SmsCouponTemplateInfoVO> info(@ApiParam(value = "优惠券模板ID", defaultValue = "1")
                                         @RequestParam("id") String id) {
        SmsCouponTemplateInfoVO templateVO = couponTemplateService.info(id);
        return Result.success(templateVO);
    }

    @ApiOperation(value = "删除优惠券模板详情")
    @DeleteMapping
    public Result deleteTemplate(@ApiParam(value = "优惠券模板ID", defaultValue = "1")
                                                @RequestParam("id") String id) {
        couponTemplateService.deleteTemplate(id);
        return Result.success();
    }

    @ApiOperation(value = "优惠券领取使用条件分页查询")
    @GetMapping("/template/coupon/page")
    public PageResult couponPage(@ApiParam(value = "优惠券领取使用条件分页查询") CouponPageQuery query) {
        IPage<SmsCoupon> result = couponService.pageQuery(query);
        return PageResult.success(result);
    }

    @ApiOperation(value = "优惠券模板下优惠券领取详情")
    @GetMapping("/template/coupon/info")
    public Result couponInfo(@ApiParam(value = "优惠券码CODE", defaultValue = "0000000")
                             @RequestParam("code") String code) {
        return Result.success();
    }


}

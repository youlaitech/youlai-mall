package com.youlai.mall.sms.controller.admin;

import com.youlai.common.result.Result;
import com.youlai.mall.sms.pojo.form.CouponTemplateForm;
import com.youlai.mall.sms.pojo.vo.CouponTemplateVO;
import com.youlai.mall.sms.service.ISmsCouponTemplateService;
import com.youlai.mall.sms.service.ITemplateBaseService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author xinyi
 * @desc：优惠券模板API接口
 * @date 2021/6/26
 */
@Slf4j
@Api(tags = "优惠券模板API接口")
@RestController
@RequestMapping("/api/v1/coupon_template")
public class CouponTemplateController {

    @Autowired
    private ITemplateBaseService templateBaseService;

    @Autowired
    private ISmsCouponTemplateService couponTemplateService;

    /**
     * 创建优惠券模板
     *
     * @param form 提交表单
     * @return result
     */
    @PostMapping("/template")
    public Result<Object> createTemplate(@RequestBody CouponTemplateForm form) {
        log.info("Create Coupon Template , form:{}", form);
        couponTemplateService.createTemplate(form);
        return Result.success();
    }

    /**
     * 修改优惠券模板
     * @param form 提交表单
     * @return result
     */
    @PutMapping("/template")
    public Result<Object> updateTemplate(@RequestBody CouponTemplateForm form){
        log.info("Update Coupon Template，form:{}",form);
        couponTemplateService.updateTemplate(form);
        return Result.success();
    }

    /**
     * 查询优惠券模板详情
     * @param id 优惠券模板ID
     * @return result
     */
    @GetMapping("/template/info")
    public Result<CouponTemplateVO> getTemplateInfo(@RequestParam("id") Long id){
        log.info("Query Coupon Template Info , id:{}",id);
        CouponTemplateVO templateVO = templateBaseService.queryTemplateInfo(id);
        return Result.success(templateVO);
    }

}

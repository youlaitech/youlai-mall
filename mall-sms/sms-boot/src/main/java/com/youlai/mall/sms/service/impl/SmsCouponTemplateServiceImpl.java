package com.youlai.mall.sms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.common.web.exception.BizException;
import com.youlai.mall.sms.mapper.SmsCouponTemplateMapper;
import com.youlai.mall.sms.pojo.domain.SmsCouponTemplate;
import com.youlai.mall.sms.pojo.enums.CouponCategoryEnum;
import com.youlai.mall.sms.pojo.enums.DistributeTargetEnum;
import com.youlai.mall.sms.pojo.form.CouponTemplateForm;
import com.youlai.mall.sms.service.IAsyncService;
import com.youlai.mall.sms.service.ISmsCouponTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xinyi
 * @desc: 优惠券模板业务实现类
 * @date 2021/6/26
 */
@Slf4j
@Service
public class SmsCouponTemplateServiceImpl extends ServiceImpl<SmsCouponTemplateMapper, SmsCouponTemplate>
        implements ISmsCouponTemplateService {


    @Autowired
    private IAsyncService asyncService;


    @Override
    public SmsCouponTemplate createTemplate(CouponTemplateForm form) {
        // 1、form 表单参数校验
        // 2、不允许出现同名的模板
        if (null == findByCouponTemplateName(form.getName())) {
            throw new BizException("Coupon Template Name Exist");
        }

        // 构造 CouponTemplate 并保存到数据库
        SmsCouponTemplate template = formToTemplate(form);
        this.save(template);

        // 根据优惠券模板异步生成优惠券码
        asyncService.asyncConstructCouponByTemplate(template);
        return template;
    }

    @Override
    public SmsCouponTemplate updateTemplate(CouponTemplateForm form) {
        // TODO 开发中。。。
        return null;
    }

    @Override
    public List<SmsCouponTemplate> findAllUsableTemplate(boolean available, boolean expired) {
        QueryWrapper<SmsCouponTemplate> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("available", available)
                .eq("expired", expired);
        return this.list(queryWrapper);
    }

    @Override
    public List<SmsCouponTemplate> findAllNotExpiredTemplate(Integer expired) {
        QueryWrapper<SmsCouponTemplate> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("expired", expired);
        return this.list(queryWrapper);
    }

    /**
     * 根据模板名称查询实体类
     *
     * @param name 模板名称
     * @return 模板实体类
     */
    private SmsCouponTemplate findByCouponTemplateName(String name) {
        QueryWrapper<SmsCouponTemplate> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", name);
        return getOne(queryWrapper);
    }

    private SmsCouponTemplate formToTemplate(CouponTemplateForm form) {
        SmsCouponTemplate template = new SmsCouponTemplate();
        template.setName(form.getName());
        template.setLogo(form.getLogo());
        template.setCategory(CouponCategoryEnum.of(form.getCategory()));
        template.setTotal(form.getTotal());
        template.setTarget(DistributeTargetEnum.of(form.getTarget()));
        template.setRule(form.getRule());
        template.setCode("");
        return template;
    }
}

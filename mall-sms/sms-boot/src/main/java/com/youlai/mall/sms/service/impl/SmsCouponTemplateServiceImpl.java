package com.youlai.mall.sms.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.common.web.exception.BizException;
import com.youlai.common.web.util.BeanMapperUtils;
import com.youlai.mall.sms.mapper.SmsCouponTemplateMapper;
import com.youlai.mall.sms.pojo.domain.SmsCouponTemplate;
import com.youlai.mall.sms.pojo.enums.CouponCategoryEnum;
import com.youlai.mall.sms.pojo.enums.CouponTemplateStateEnum;
import com.youlai.mall.sms.pojo.enums.DistributeTargetEnum;
import com.youlai.mall.sms.pojo.form.CouponTemplateForm;
import com.youlai.mall.sms.pojo.query.CouponTemplatePageQuery;
import com.youlai.mall.sms.pojo.vo.CouponTemplateVO;
import com.youlai.mall.sms.service.IAsyncService;
import com.youlai.mall.sms.service.ISmsCouponTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
    public IPage<SmsCouponTemplate> pageQuery(CouponTemplatePageQuery query) {
        Page<SmsCouponTemplate> page = new Page<>(query.getPageNum(), query.getPageSize());
        QueryWrapper<SmsCouponTemplate> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StrUtil.isNotBlank(query.getName()), "name", query.getName());
        queryWrapper.eq(query.getCategory() != null, "category", query.getCategory());
        queryWrapper.eq(query.getState() != null, "state", query.getState());
        queryWrapper.eq(query.getTarget() != null, "target", query.getTarget());
        queryWrapper.gt(query.getEndTime() != null, "gmt_create", query.getEndTime());
        queryWrapper.gt(query.getStartTime() != null, "gmt_create", query.getStartTime());
        return this.page(page, queryWrapper);
    }

    @Override
    public SmsCouponTemplate createTemplate(CouponTemplateForm form) {
        log.info("Create Coupon Template , form={}", form);
        // 1、form 表单参数校验
        if (null != findByCouponTemplateName(form.getName())) {
            throw new BizException("Coupon Template Name Exist");
        }

        // 构造 CouponTemplate 并保存到数据库
        SmsCouponTemplate template = formToTemplate(form);
        this.save(template);

        return template;
    }

    @Override
    public SmsCouponTemplate updateTemplate(CouponTemplateForm form) {
        log.info("Update Coupon Template, ID={}, form={}", form.getId(), form);
        // 1、判断优惠券的名称有没有变化
        SmsCouponTemplate template = findByTemplateId(form.getId());
        if (CouponTemplateStateEnum.INIT != template.getState()) {
            throw new BizException("Coupon Template State Not Init");
        }
        if (!StringUtils.equals(template.getName(), form.getName())) {
            // 如果form提交的名称不一致，需要判断名称是否重复
            if (null != findByCouponTemplateName(form.getName())) {
                throw new BizException("Coupon Template Name Exist");
            }
        }

        // 2、将修改后的数据，覆盖原数据
        BeanMapperUtils.copy(form, template);
        this.updateById(template);

        return template;
    }

    @Override
    public void confirmTemplate(String id) {
        // 1、校验优惠券状态
        SmsCouponTemplate template = findByTemplateId(id);
        if (CouponTemplateStateEnum.INIT != template.getState()) {
            log.info("Finish Confirm Coupon Template State Not Init, TemplateID={}.", id);
            return;
        }

        // 2、修改优惠券模板状态，生成优惠券模板编码
        template.setState(CouponTemplateStateEnum.USED);
        template.setCode(template.getCategory() +
                DateUtil.today().replace("-", "") +
                template.getId());
        this.save(template);

        // 3、根据优惠券模板异步生成优惠券码，放入Redis 缓存中，等待用户领取
        asyncService.asyncConstructCouponByTemplate(template);
        log.info("Finish Confirm Coupon Template, TemplateID={}.", id);
    }


    @Override
    public List<SmsCouponTemplate> findAllUsableTemplate(Integer available, Integer expired) {
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

    @Override
    public CouponTemplateVO info(String id) {
        SmsCouponTemplate template = findByTemplateId(id);
        return BeanMapperUtils.map(template,CouponTemplateVO.class);
    }

    /**
     * 根据ID查找优惠券模板
     *
     * @param id 优惠券模板ID
     * @return 优惠券模板
     */
    private SmsCouponTemplate findByTemplateId(String id) {
        SmsCouponTemplate template = this.getById(id);
        if (template == null) {
            throw new BizException("Template Not Exist, ID=" + id);
        }
        return template;
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
        template.setState(CouponTemplateStateEnum.INIT);
        return template;
    }
}

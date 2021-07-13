package com.youlai.mall.sms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.mall.sms.pojo.domain.SmsCouponTemplate;
import com.youlai.mall.sms.pojo.form.CouponTemplateForm;
import com.youlai.mall.sms.pojo.query.CouponTemplatePageQuery;
import com.youlai.mall.sms.pojo.vo.CouponTemplateVO;

import java.util.List;

/**
 * @author xinyi
 * @desc: 优惠券模板业务接口
 * @date 2021/6/26
 */
public interface ISmsCouponTemplateService extends IService<SmsCouponTemplate> {


    IPage<SmsCouponTemplate> pageQuery(CouponTemplatePageQuery query);

    /**
     * 创建优惠券模板
     *
     * @param form {@link CouponTemplateForm} 模板信息请求对象
     * @return {@link SmsCouponTemplate} 优惠券模板实体类
     */
    SmsCouponTemplate createTemplate(CouponTemplateForm form);


    /**
     * 修改优惠券模板
     * @param form {@link CouponTemplateForm} 模板信息请求对象
     * @return {@link SmsCouponTemplate} 优惠券模板实体类
     */
    SmsCouponTemplate updateTemplate(CouponTemplateForm form);


    /**
     * 优惠券模板审核
     * @param id
     */
    void confirmTemplate(String id);

    /**
     * 查询所有可用优惠券模板列表
     *
     * @param available 是否可用
     * @param expired   是否过期
     * @return 优惠券模板
     */
    List<SmsCouponTemplate> findAllUsableTemplate(Integer available, Integer expired);

    /**
     * 查询未过期的优惠券模板列表
     *
     * @param expired 是否过期
     * @return 优惠券模板列表
     */
    List<SmsCouponTemplate> findAllNotExpiredTemplate(Integer expired);

    /**
     * 查询优惠券模板详情
     * @param id 优惠券模板ID
     * @return 优惠券模板详情
     */
    CouponTemplateVO info(String id);
}

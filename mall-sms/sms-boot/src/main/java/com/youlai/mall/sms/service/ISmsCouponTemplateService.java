package com.youlai.mall.sms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.mall.sms.pojo.domain.SmsCouponTemplate;
import com.youlai.mall.sms.pojo.form.CouponTemplateForm;

import java.util.List;

/**
 * @author xinyi
 * @desc: 优惠券模板业务接口
 * @date 2021/6/26
 */
public interface ISmsCouponTemplateService extends IService<SmsCouponTemplate> {

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
     * 查询所有可用优惠券模板列表
     *
     * @param available 是否可用
     * @param expired   是否过期
     * @return 优惠券模板
     */
    List<SmsCouponTemplate> findAllUsableTemplate(boolean available, boolean expired);

    /**
     * 查询未过期的优惠券模板列表
     *
     * @param expired 是否过期
     * @return 优惠券模板列表
     */
    List<SmsCouponTemplate> findAllNotExpiredTemplate(Integer expired);

}

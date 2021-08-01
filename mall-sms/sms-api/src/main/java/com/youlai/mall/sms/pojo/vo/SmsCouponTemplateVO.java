package com.youlai.mall.sms.pojo.vo;

import com.youlai.mall.sms.pojo.enums.CouponCategoryEnum;
import com.youlai.mall.sms.pojo.enums.CouponOfferStateEnum;
import com.youlai.mall.sms.pojo.enums.CouponVerifyStateEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xinyi
 * @desc: 优惠券模板实体类：基础属性 + 规则属性
 * @date 2021/6/26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SmsCouponTemplateVO {

    /**
     * 主键自增ID
     */
    private Long id;

    /**
     * 优惠券名称
     */
    private String name;

    /**
     * 优惠券logo
     */
    private String logo;

    /**
     * 优惠券描述
     */
    private String description;

    /**
     * 优惠券分类
     */
    private CouponCategoryEnum category;

    /**
     * 优惠券审核状态
     * 0: 待审核
     * 1: 已审核
     */
    private CouponVerifyStateEnum verifyState;

    /**
     * 优惠券发放状态
     * (已审核且在允许发放时间之内，才允许发放)
     * 0: 未开始
     * 1: 进行中
     * 2: 已结束
     */
    private CouponOfferStateEnum offerState;

    /**
     * 优惠券发放最早时间
     */
    private Long offerStartTime;

    /**
     * 优惠券发放最晚时间
     */
    private Long offerEndTime;

    /**
     * 优惠券使用状态
     * 0：未生效：（未审核，未到最早使用时间）
     * 1：生效中（已审核，正在使用时间中）
     * 2：已结束（超过最晚使用时间）
     */
    private CouponOfferStateEnum usedState;

    /**
     * 优惠券最早使用时间
     */
    private Long usedStartTime;

    /**
     * 优惠券最晚使用时间
     */
    private Long usedEndTime;

    /**
     * 总数
     */
    private Integer total;

    /**
     * 优惠券模板编码
     */
    private String code;

    /**
     * 优惠券规则
     */
    private CouponTemplateRuleVO rule;

}

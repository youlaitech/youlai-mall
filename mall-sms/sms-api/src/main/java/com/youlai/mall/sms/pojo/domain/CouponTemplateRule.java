package com.youlai.mall.sms.pojo.domain;

import com.youlai.mall.sms.pojo.enums.UserTypeEnum;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author xinyi
 * @desc：优惠券使用规则
 * @date 2021/7/19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CouponTemplateRule {

    private UserLimit userLimit;

    /**
     * 优惠券过期规则
     */
    private Expiration expiration;

    /**
     * 优惠券折扣规则
     */
    private Discount discount;

    private List<String> goodsCategories;

    /**
     * 权重（可以和哪些优惠券叠加使用，同一类的优惠券一定不能叠加）
     */
    private List<String> weight;

    private String targetUrl;


    /**
     * 有效期规则定义
     */
    @ApiModel("有效期规则定义模型")
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Expiration {

        /**
         * 有效期规则，对应 PeriodType 的 code 字段
         */
        private Integer period;

        private Long startTime;

        private Long endTime;

        /**
         * 有效间隔：只对变动性有效期有效
         */
        private Integer gap;

    }

    /**
     * 折扣的规则
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Discount {

        /**
         * 额度：满减（20），折扣（85），立减（10）
         */
        private Long quota;

        /**
         * 基准：需要满多少才可用
         */
        private Long base;

        /**
         * 最大优惠金额
         */
        private Long maxDiscount;

    }

    /**
     * 用户领取限制
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserLimit {

        private UserTypeEnum userType;

        private Integer limit;
    }
}

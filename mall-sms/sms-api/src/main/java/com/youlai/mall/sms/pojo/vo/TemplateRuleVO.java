package com.youlai.mall.sms.pojo.vo;

import com.youlai.mall.sms.pojo.enums.PeriodTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * @author xinyi
 * @desc: 优惠券规则对象定义
 * @date 2021/6/26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TemplateRuleVO {

    /**
     * 优惠券过期规则
     */
    private Expiration expiration;

    /**
     * 折扣
     */
    private Discount discount;

    /**
     * 限领张数限制
     */
    private Integer limitation;

    /**
     * 使用范围：地域 + 商品类型
     */
    private Usage usage;

    /**
     * 权重（可以和哪些优惠券叠加使用，同一类的优惠券一定不能叠加）
     */
    private String weight;

    /**
     * 有效期规则定义
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Expiration {

        /**
         * 有效期规则，对应 PeriodType 的 code 字段
         */
        private Integer period;

        /**
         * 有效间隔：只对变动性有效期有效
         */
        private Integer gap;

        /**
         * 优惠券模板的失效日期，两类规则都有效
         */
        private Long deadline;

        boolean validate() {
            // 最简化校验
            return null != PeriodTypeEnum.of(period) && gap > 0;
        }
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
        private Integer quota;

        /**
         * 基准：需要满多少才可用
         */
        private Integer base;

        boolean validate() {
            // 最简化校验
            return quota > 0 && base > 0;
        }
    }

    /**
     * 优惠券使用范围
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Usage {

        /**
         * 省份
         */
        private String province;

        /**
         * 城市
         */
        private String city;

        /**
         * 商品类型，list[文娱，生鲜，家居，全品类]
         */
        private String goodsType;

        boolean validate() {
            return StringUtils.isNotEmpty(province)
                    && StringUtils.isNotEmpty(province)
                    && StringUtils.isNotEmpty(province);
        }
    }
}

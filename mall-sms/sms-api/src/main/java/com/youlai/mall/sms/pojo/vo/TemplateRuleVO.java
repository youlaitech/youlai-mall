package com.youlai.mall.sms.pojo.vo;

import com.youlai.mall.sms.pojo.enums.PeriodTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author xinyi
 * @desc: 优惠券规则对象定义
 * @date 2021/6/26
 */
@ApiModel("优惠券领取使用规则模型")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TemplateRuleVO {

    /**
     * 优惠券过期规则
     */
    @ApiModelProperty("优惠券过期规则")
    @NotNull(message = "优惠券过期规则不能为空")
    private Expiration expiration;

    /**
     * 优惠券折扣规则
     */
    @ApiModelProperty("优惠券折扣规则")
    @NotNull(message = "优惠券折扣规则不能为空")
    private Discount discount;

    /**
     * 单个限领张数限制
     */
    @ApiModelProperty("单个限领张数限制")
    @Min(value = 1,message = "单个限领张数限制不能小于1")
    private Integer limitation;

    /**
     * 优惠券使用范围规则
     * 使用范围：地域 + 商品类型
     */
    @ApiModelProperty("优惠券使用范围规则")
    private Usage usage;

    /**
     * 权重（可以和哪些优惠券叠加使用，同一类的优惠券一定不能叠加）
     */
    @ApiModelProperty("优惠券使用权重规则")
    private String weight;

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
        @ApiModelProperty("有效期规则(固定日期、以领取之日开始计算)")
        @NotNull(message = "有效期规则不能为空")
        private Integer period;

        /**
         * 有效间隔：只对变动性有效期有效
         */
        @ApiModelProperty("有效间隔(单位小时，只对变动性有效期有效)")
        @Min(value = 0,message = "有效期间隔不能小于0(单位小时)")
        private Integer gap;

        /**
         * 优惠券模板的失效日期，两类规则都有效
         */
        @ApiModelProperty("优惠券模板的失效日期(两类规则都有效)")
        @Min(value = 0,message = "失效日期不能小于0(时间毫秒值)")
        private Long deadline;

        boolean validate() {
            // 最简化校验
            return null != PeriodTypeEnum.of(period) && gap > 0;
        }
    }

    /**
     * 折扣的规则
     */
    @ApiModel("折扣规则定义模型")
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Discount {

        /**
         * 额度：满减（20），折扣（85），立减（10）
         */
        @ApiModelProperty("额度：满减（20），折扣（85），立减（10）")
        @Min(value = 0,message = "折扣额度最小值为0")
        private Long quota;

        /**
         * 基准：需要满多少才可用
         */
        @ApiModelProperty("折扣基准(需要满多少才可用,为0表示无基准)")
        @Min(value = 0,message = "折扣基准最小值为0")
        private Long base;

    }

    /**
     * 优惠券使用范围
     */
    @ApiModel("优惠券使用范围")
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Usage {

        /**
         * 商品类型，list[文娱，生鲜，家居，全品类]
         * 可根据业务扩展，增加商品现在，地域限制，下单手机号限制。。。
         */
        @ApiModelProperty("允许使用优惠券的商品类型(为空则表示不限制)")
        private List<String> goodsType;

    }
}

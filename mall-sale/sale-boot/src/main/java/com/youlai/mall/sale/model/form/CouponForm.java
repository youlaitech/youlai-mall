package com.youlai.mall.sale.model.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 优惠券表单对象
 *
 * @author Ray.Hao
 * @since 2022/6/23
 */
@Schema(description = "优惠券表单对象")
@Data
public class CouponForm {

    @Schema(description="ID")
    private Long id;

    @Schema(description="优惠券名称")
    private String name;

    @Schema(description="优惠券类型(1:满减券;2:直减券;3:折扣券)")
    private Integer type;

    @Schema(description="优惠券面值类型((1:金额;2:折扣)")
    private Integer faceValueType;

    @Schema(description="优惠券面值金额(单位:分)")
    private Long faceValue;

    @Schema(description="优惠券折扣")
    private BigDecimal discount;

    @Schema(description="优惠券码")
    private String code;

    @Schema(description="使用平台(0-全平台;1-移动端;2-PC;)")
    private Integer platform;

    @Schema(description="发行量(-1:无限制)")
    private Integer circulation;

    @Schema(description="使用门槛(0:无门槛)")
    private Long minPoint;

    @Schema(description="每人限领张数(-1:不限制)")
    private Integer perLimit;

    @Schema(description="有效期类型(1:日期范围;2:固定天数)")
    private Integer validityPeriodType;

    @Schema(description="自领取之日起有效天数")
    private Integer validityDays;

    @Schema(description="有效期开始时间")
    private Date validityBeginTime;

    @Schema(description="有效期截止时间")
    private Date validityEndTime;

    @Schema(description="应用范围(0:全场通用;1:指定商品分类;2:指定商品)")
    private Integer applicationScope;

    @Schema(description="备注")
    private String remark;

    @Schema(description="优惠券适用商品分类ID集合")
    private List<Long> spuCategoryIds;

    @Schema(description="优惠券适用商品列表")
    private List<Long> spuIds;
}
package com.youlai.mall.sale.model.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

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

    @Schema(description="主键ID")
    private Long id;

    @Schema(description="优惠券名称")
    private String name;

    @Schema(description="优惠券兑换码")
    private String code;

    @Schema(description="优惠券类型(1-满减券;2-直减券;3-折扣券;4-包邮券)")
    private Integer type;

    @Schema(description="优惠金额/折扣值")
    private BigDecimal discountAmount;

    @Schema(description="最高折扣金额")
    private BigDecimal discountLimit;

    @Schema(description="使用门槛金额")
    private BigDecimal minAmount;

    @Schema(description="优惠券状态(0-草稿;1-未开始;2-进行中;3-已结束;4-已失效)")
    private Integer status;

    @Schema(description="发布时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date publishTime;

    @Schema(description="发行数量(-1表示不限制)")
    private Integer totalCount;

    @Schema(description="每人限领数量(-1表示不限制)")
    private Integer perLimit;

    @Schema(description="有效期类型(1-固定日期范围;2-领取后天数)")
    private Integer validType;

    @Schema(description="领取后有效天数")
    private Integer validDays;

    @Schema(description="有效期开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date validStartTime;

    @Schema(description="有效期结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date validEndTime;

    @Schema(description="使用范围(位运算: 1-全场通用(0001);2-指定分类(0010);4-指定商品(0100))")
    private Integer useScope;

    @Schema(description="是否可叠加使用(0-否;1-是)")
    private Integer canSuperimpose;

    @Schema(description="是否仅首单可用(0-否;1-是)")
    private Integer firstOrderOnly;

    @Schema(description="备注说明")
    private String remark;

    @Schema(description="优惠券适用商品分类ID集合")
    private List<Long> categoryIds;

    @Schema(description="优惠券适用商品ID集合")
    private List<Long> spuIds;
}
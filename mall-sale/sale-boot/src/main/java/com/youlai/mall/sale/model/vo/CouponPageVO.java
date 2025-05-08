package com.youlai.mall.sale.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

/**
 * 优惠券分页视图对象
 *
 * @author Ray.Hao
 * @since 2022/5/31
 */
@Schema(description = "优惠券分页视图对象")
@Data
public class CouponPageVO {

    @Schema(description="优惠券ID")
    private Long id;

    @Schema(description="优惠券名称")
    private String name;

    @Schema(description="优惠券兑换码")
    private String code;

    @Schema(description="优惠券类型")
    private Integer type;

    @Schema(description="优惠券类型描述")
    private String typeLabel;

    @Schema(description="优惠券状态")
    private Integer status;

    @Schema(description="优惠券状态描述")
    private String statusLabel;

    @Schema(description="发布时间")
    private Date publishTime;

    @Schema(description="优惠券面值/折扣描述")
    private String discountValueLabel;

    @Schema(description="使用门槛描述")
    private String minAmountLabel;
    
    @Schema(description="有效期描述")
    private String validityPeriodLabel;

    @Schema(description="使用范围")
    private Integer useScope;

    @Schema(description="使用范围描述")
    private String useScopeLabel;

    @Schema(description="发行量")
    private Integer totalCount;

    @Schema(description="已领取数量")
    private Integer receiveCount;

    @Schema(description="已使用数量")
    private Integer usedCount;

    @Schema(description="是否可叠加使用")
    private Integer canSuperimpose;

    @Schema(description="是否可叠加使用描述")
    private String canSuperimposeLabel;

    @Schema(description="是否仅首单可用")
    private Integer firstOrderOnly;

    @Schema(description="是否仅首单可用描述")
    private String firstOrderOnlyLabel;

    @Schema(description="备注说明")
    private String remark;

    @Schema(description="创建时间")
    private Date createTime;

    @Schema(description="更新时间")
    private Date updateTime;
}

package com.youlai.mall.order.model.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.time.LocalDateTime;
/**
 *  DTO
 *
 * @author Ray.Hao
 * @since 2024-06-07
 */
@Getter
@Setter
@Schema( description = "传输层对象")
public class OrderRefundDTO implements Serializable {

    private static final long serialVersionUID = 1L;
        @Schema(description = "主键")
    private Long id;
        @Schema(description = "订单ID")
    private Long orderId;
        @Schema(description = "退款原因")
    private String reason;
        @Schema(description = "状态（0：等待审批，1：已批准，-1：已拒绝）")
    private Byte status;
        @Schema(description = "订单总金额（单位：分）")
    private Long totalFee;
        @Schema(description = "退款金额（单位：分）")
    private Long refundFee;
        @Schema(description = "审批人ID")
    private Long approverId;
        @Schema(description = "审批时间")
    private LocalDateTime approvalTime;
        @Schema(description = "创建人ID")
    private Long createBy;
        @Schema(description = "更新人ID")
    private Long updateBy;
        @Schema(description = "创建时间")
    private LocalDateTime createTime;
        @Schema(description = "更新时间")
    private LocalDateTime updateTime;
        @Schema(description = "逻辑删除标识（1：已删除，0：未删除）")
    private Byte isDeleted;
}

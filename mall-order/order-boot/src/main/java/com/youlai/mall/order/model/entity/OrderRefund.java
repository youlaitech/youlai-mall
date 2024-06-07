package com.youlai.mall.order.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * 订单退款实体
 *
 * @author Ray Hao
 * @since 2024-06-07
 */
@Getter
@Setter
@TableName
public class OrderRefund implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 订单ID
     */
    private Long orderId;

    /**
     * 退款原因
     */
    private String reason;

    /**
     * 状态（0：等待审批，1：已批准，-1：已拒绝）
     */
    private Integer status;

    /**
     * 订单总金额（单位：分）
     */
    private Long totalFee;

    /**
     * 退款金额（单位：分）
     */
    private Long refundFee;

    /**
     * 审批人ID
     */
    private Long approverId;

    /**
     * 审批时间
     */
    private LocalDateTime approvalTime;

    /**
     * 创建人ID
     */
    private Long createBy;

    /**
     * 更新人ID
     */
    private Long updateBy;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 逻辑删除标识（1：已删除，0：未删除）
     */
    private Byte isDeleted;
}

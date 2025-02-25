package com.youlai.mall.order.model.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * 订单退款传输对象
 *
 * @author Ray.Hao
 * @since 2024/6/7
 */
@Schema(description = "订单退款传输对象")
@Getter
@Setter
public class OrderRefundForm {

    @Schema(description = "订单ID",example = "1")
    @NotNull(message = "订单ID不能为空")
    private Long orderId;

    @Schema(description = "退款原因",example = "商品质量问题")
    @NotBlank(message = "退款原因不能为空")
    private String reason;

    @Schema(description = "退款金额(单位：分)",example = "1000")
    @NotNull(message = "退款金额不能为空")
    private Long refundFee;
}

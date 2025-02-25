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
public class OrderRefundApprovalForm {

    @Schema(description = "退款ID",example = "1")
    @NotNull(message = "退款ID不能为空")
    private Long id;

    @Schema(description = "审批意见(1:通过，-1:拒绝)",example = "1")
    @NotBlank(message = "审批意见不能为空")
    private Integer  status;

}

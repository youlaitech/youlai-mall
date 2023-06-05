package com.youlai.mall.oms.model.form;

import com.youlai.mall.oms.common.enums.OrderSourceTypeEnum;
import com.youlai.mall.oms.model.dto.OrderItemDTO;
import com.youlai.mall.ums.dto.MemberAddressDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import jakarta.validation.constraints.Size;
import java.util.List;

/**
 * 订单提交表单对象
 *
 * @author huawei
 * @email huawei_code@163.com
 * @since 2021/1/16
 */
@Data
@ToString
public class OrderSubmitForm {


    /**
     * 订单来源
     *
     * @see OrderSourceTypeEnum
     */
    @Schema(description="订单来源")
    private Integer sourceType;

    @Schema(description="提交订单确认页面签发的令牌(防止订单重复提交，订单提交成功转为订单编号)")
    private String orderToken;

   @Schema(description="订单总金额-用于验价(单位：分)")
    private Long totalAmount;

    @Schema(description="支付金额(单位：分)")
    private Long payAmount;

    @Schema(description="订单的商品明细")
    private List<OrderItemDTO> orderItems;

    @Schema(description="订单备注")
    @Size(max = 500, message = "订单备注长度不能超过500")
    private String remark;

    @Schema(description="优惠券ID")
    private String couponId;

    @Schema(description="收获地址")
    private MemberAddressDTO deliveryAddress;

}

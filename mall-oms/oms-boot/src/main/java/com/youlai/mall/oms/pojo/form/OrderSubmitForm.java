package com.youlai.mall.oms.pojo.form;

import com.youlai.mall.oms.common.enums.OrderSourceTypeEnum;
import com.youlai.mall.oms.pojo.dto.OrderItemDTO;
import com.youlai.mall.ums.dto.MemberAddressDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Size;
import java.util.List;

/**
 * 订单提交表单对象
 *
 * @author huawei
 * @email huawei_code@163.com
 * @date 2021/1/16
 */
@Data
@ToString
public class OrderSubmitForm {


    /**
     * 订单来源
     *
     * @see OrderSourceTypeEnum
     */
    @ApiModelProperty("订单来源")
    private Integer sourceType;

    @ApiModelProperty("提交订单确认页面签发的令牌(防止订单重复提交，订单提交成功转为订单编号)")
    private String orderToken;

   @ApiModelProperty("订单总金额-用于验价(单位：分)")
    private Long totalAmount;

    @ApiModelProperty("支付金额(单位：分)")
    private Long payAmount;

    @ApiModelProperty("订单的商品明细")
    private List<OrderItemDTO> orderItems;

    @ApiModelProperty("订单备注")
    @Size(max = 500, message = "订单备注长度不能超过500")
    private String remark;

    @ApiModelProperty("优惠券ID")
    private String couponId;

    @ApiModelProperty("收获地址")
    private MemberAddressDTO deliveryAddress;

}

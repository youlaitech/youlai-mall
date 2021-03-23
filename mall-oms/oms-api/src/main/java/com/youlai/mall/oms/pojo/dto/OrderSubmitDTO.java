package com.youlai.mall.oms.pojo.dto;

import com.youlai.mall.ums.pojo.domain.UmsAddress;
import lombok.Data;

import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author huawei
 * @desc 订单提交
 * @email huawei_code@163.com
 * @date 2021/1/16
 */
@Data
public class OrderSubmitDTO {

    // 提交订单确认页面签发的令牌
    private String orderToken;

    private List<OrderItemDTO> orderItems;

    // 验价前台传值
    private Long totalPrice;

    // 收货地址
    private UmsAddress deliveryAddress;

    @Size(max = 500, message = "订单备注长度不能超过500")
    private String remark;

    private String couponId;

    private Long payAmount;

}

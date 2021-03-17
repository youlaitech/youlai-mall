package com.youlai.mall.oms.pojo.dto;

import com.youlai.mall.oms.pojo.vo.OrderItemVO;
import lombok.Data;

import javax.validation.constraints.NotBlank;
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

    private List<OrderItemVO> orderItems;

    private Long payAmount;

    private String couponId;

    @NotBlank(message = "请选择收货地址")
    private String addressId;

    @Size(max = 500, message = "订单备注长度不能超过500")
    private String remark;

}

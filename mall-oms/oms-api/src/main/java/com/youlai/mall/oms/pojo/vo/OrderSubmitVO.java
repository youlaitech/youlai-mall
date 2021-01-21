package com.youlai.mall.oms.pojo.vo;

import com.youlai.common.core.base.BaseVO;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author huawei
 * @desc 订单提交实体类
 * @email huawei_code@163.com
 * @date 2021/1/16
 */
@Data
public class OrderSubmitVO extends BaseVO {

    /**
     * 用户选择地址id
     */
    @NotBlank(message = "请选择收货地址")
    private String addressId;

    /**
     * 如果携带skuId则表示该订单通过直接下单方式生成
     * 否则从购物车中生成 -- 清空购物车
     */
    private String skuId;

    /**
     * 直接下单时商品数量
     */
    private Integer skuNumber;

    /**
     * 优惠券id
     */
    private String couponId;

    @Size(max = 500, message = "订单备注长度不能超过500")
    private String remark;


    private Long payAmount;

}

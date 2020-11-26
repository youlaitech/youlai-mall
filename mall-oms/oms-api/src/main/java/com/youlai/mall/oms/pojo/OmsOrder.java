package com.youlai.mall.oms.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.youlai.common.core.base.BaseEntity;
import lombok.Data;

import java.util.Date;

@Data
public class OmsOrder extends BaseEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String orderSn;

    private Long memberId;

    private Integer status;

    private Integer source;

    private String consignee;

    private String mobile;

    private String postcode;

    private String address;

    private Long couponId;

    private Long skuPrice;

    private Long freightPrice;

    private Long couponPrice;

    private Long orderPrice;

    private Long integrationPrice;

    private Long payPrice;

    private String payId;

    private Integer payChannel;

    private Date payTime;

    private String logisticsChannel;

    private String logisticsNo;

    private Date deliveryTime;

}

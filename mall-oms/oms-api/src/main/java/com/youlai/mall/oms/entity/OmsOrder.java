package com.youlai.mall.oms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.youlai.common.core.base.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;


@Data
public class OmsOrder extends BaseEntity {

    @TableId(type= IdType.AUTO)
    private Long id;

    private String orderSn;

    private Long memberId;

    private Integer status;

    private Integer sourceType;

    private String consignee;

    private String mobile;

    private String postcode;

    private String address;

    private Long couponId;

    private BigDecimal skuPrice;

    private BigDecimal freightPrice;

    private BigDecimal couponPrice;

    private BigDecimal orderPrice;

    private BigDecimal integrationPrice;

    private BigDecimal payPrice;

    private String payId;

    private Integer payType;

    private Date payTime;

    private String shipSn;






}

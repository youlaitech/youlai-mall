package com.fly4j.shop.order.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fly.common.core.domain.BaseEntity;
import lombok.Data;

@Data
@TableName("order_company_address")
public class OrderCompanyAddress   {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String addressName;

    private Integer isDefaultSend;

    private Integer isDefaultReceive;

    private String name;

    private String phone;

    private String province;

    private String city;

    private String region;

    private String detailAddress;

}

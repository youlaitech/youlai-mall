package com.fly4j.yshop.oms.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fly4j.common.core.bean.BaseEntity;
import lombok.Data;

@Data
public class OmsOrder extends BaseEntity {

    @TableId(type = IdType.ID_WORKER)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;
    private String order_sn;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long member_id;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long coupon_id;
    private Long status;
    private Long source_type;
    private String receiver_name;
    private String receiver_mobile;
    private String receiver_address;
    private String receiver_zip;
    private String buyer_message;
    private Double freight_amount;
    private Double coupon_amount;
    private Double total_amount;
    private Double pay_amount;
    private String logistics_number;
    private String logistics_company;
    private Data pay_type;
    private Data pay_time;
    private Data delivery_time;
    private Data confirm_time;

}

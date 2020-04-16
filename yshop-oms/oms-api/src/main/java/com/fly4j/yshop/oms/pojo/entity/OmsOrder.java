package com.fly4j.yshop.oms.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fly4j.common.core.bean.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel
public class OmsOrder extends BaseEntity {
    @TableId(type = IdType.ID_WORKER)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty(hidden = true)
    private Long id;
    @ApiModelProperty(hidden = true)
    private String order_sn;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty(name = "会员ID")
    private Long member_id;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty(name="使用优惠券")
    private Long coupon_id;
    private Integer status;
    private Integer type;
    private Integer source_type;
    private String receiver_name;
    private String receiver_mobile;
    private String receiver_province;
    private String receiver_city;
    private String receiver_area;
    private String receiver_detail_address;
    private String receiver_zip_code;
    private String buyer_message;
    private BigDecimal freight_amount;
    private BigDecimal coupon_amount;
    private BigDecimal total_amount;
    private BigDecimal pay_amount;
    private String logistics_number;
    private String logistics_company;
    private Integer pay_type;
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date pay_time;
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date deliver_time;
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date receive_time;
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss" )
    private Date comment_time;

}

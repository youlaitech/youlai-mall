package com.fly4j.shop.order.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("order_return_apply")
public class OrderReturnApply  {

    @TableId
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long orderId;

    private Integer companyAddressId;

    private Long goodsId;

    private String orderNo;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT-8")
    private Date createTime;

    private String memberUsername;

    private BigDecimal returnAmount;

    private String returnPerson;

    private String returnPhone;

    private Integer status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT-8")
    private Date handleTime;

    private String goodsPic;

    private String goodsName;

    private String goodsBrand;

    private String goodsAttr;

    private Integer goodsCount;

    private BigDecimal goodsPrice;

    private BigDecimal goodsRealPrice;

    private String reason;

    private String description;

    private String proofPics;

    private String handleRemark;

    private String handler;

    private String receiver;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT-8")
    private Date receiveTime;

    private String receiveRemark;

}

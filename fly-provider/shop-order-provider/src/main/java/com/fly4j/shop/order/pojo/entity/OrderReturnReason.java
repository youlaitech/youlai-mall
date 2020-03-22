package com.fly4j.shop.order.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
@TableName("order_return_reason")
public class OrderReturnReason {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String name;

    private Integer sort;

    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT-8")
    private Date createTime;
}

package com.fly4j.shop.order.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fly.common.core.domain.BaseEntity;
import lombok.Data;

@Data
@TableName("order_return_reason")
public class OrderReturnReason extends BaseEntity {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String name;

    private Integer sort;

    private Integer status;
}

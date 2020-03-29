package com.fly4j.shop.order.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fly4j.common.core.domain.BaseEntity;
import lombok.Data;

@Data
@TableName("order_operate_history")
public class OrderOperateHistory extends BaseEntity {
    @TableId(type = IdType.AUTO)
    private Long id;

    private Long orderId;

    private String operateMan;

    private Integer orderStatus;

    private String note;

}
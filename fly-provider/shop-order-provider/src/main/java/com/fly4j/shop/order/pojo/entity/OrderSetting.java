package com.fly4j.shop.order.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("order_setting")
public class OrderSetting {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer flashOrderOvertime;

    private Integer normalOrderOvertime;

    private Integer confirmOvertime;

    private Integer finishOvertime;

    private Integer commentOvertime;

}

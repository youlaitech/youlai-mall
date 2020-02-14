package com.fly.cloud.seckill.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fly.common.base.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("seckill_order")
public class SeckillOrder extends BaseEntity {

    @TableId
    private long seckillId; //秒杀到的商品ID
    private BigDecimal money; //支付金额

    private long userPhone; //秒杀用户的手机号

    private boolean status; //订单状态， -1:无效 0:成功 1:已付款

    private Seckill seckill;
    }

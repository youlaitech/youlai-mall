package com.fly.seckill.pojo.dto;

import com.fly.seckill.enums.SeckillStatEnum;
import com.fly.seckill.pojo.entity.SeckillOrder;
import lombok.Data;

@Data
public class SeckillExecution {

    private Long seckillId;

    //秒杀执行结果状态
    private int state;

    //状态表示
    private String stateInfo;

    //秒杀成功的订单对象
    private SeckillOrder seckillOrder;

    public SeckillExecution(Long seckillId, SeckillStatEnum seckillStatEnum, SeckillOrder seckillOrder) {
        this.seckillId = seckillId;
        this.state = seckillStatEnum.getState();
        this.stateInfo = seckillStatEnum.getStateInfo();
        this.seckillOrder = seckillOrder;
    }

    public SeckillExecution(Long seckillId, SeckillStatEnum seckillStatEnum) {
        this.seckillId = seckillId;
        this.state = seckillStatEnum.getState();
        this.stateInfo = seckillStatEnum.getStateInfo();
    }
}
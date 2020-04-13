package com.fly4j.yshop.sms.pojo.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fly4j.common.core.bean.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class SmsSeckillSessionSku extends BaseEntity {

    @TableId(type = IdType.ID_WORKER)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long seckill_id;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long seckill_session_Id;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long sku_id;
    private BigDecimal seckill_price;
    private Long seckill_count;
    private Long seckill_limit;
    private Long seckill_sort;
}

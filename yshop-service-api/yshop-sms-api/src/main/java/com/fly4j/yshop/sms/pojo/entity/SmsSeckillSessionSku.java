package com.fly4j.yshop.sms.pojo.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fly4j.yshop.common.core.entity.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class SmsSeckillSessionSku extends BaseEntity {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private Long promotion_id;
    private Long promotion_session_Id;
    private Long sku_id;
    private BigDecimal seckill_price;
    private Long seckill_count;
    private Long seckill_limit;
    private Long sort;

    private String spu_code;
    private String spu_name;
    private String pic_url;
    private String specs;
}

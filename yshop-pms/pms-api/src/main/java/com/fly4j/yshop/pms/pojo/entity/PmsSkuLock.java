package com.fly4j.yshop.pms.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class PmsSkuLock {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private Long sku_id;
    private Integer stock;
    private Integer stock_locked;

}
package com.fly4j.yshop.pms.pojo.vo;

import lombok.Data;

@Data
public class SkuLockVO {

    private Long id;

    private Long sku_id;

    private Integer quantity; // 锁定数量

    private String order_token; // 那个订单（订单编号）
}
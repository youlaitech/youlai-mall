package com.fly4j.yshop.oms.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fly4j.common.core.bean.BaseEntity;
import lombok.Data;

@Data
public class OmsOrderItem extends BaseEntity {

    @TableId(type = IdType.ID_WORKER)
    private Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long order_id;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long goods_id;
    private String goods_name;
    private String goods_sn;
    private Long sku_id;
    private Integer quantity;
    private String specifications;
    private String pic_url;
}

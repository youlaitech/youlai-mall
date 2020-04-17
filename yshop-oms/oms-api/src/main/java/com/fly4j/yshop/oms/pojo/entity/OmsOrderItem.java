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
    private String order_sn;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long spu_id;
    private String spu_name;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String spu_brand;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long spu_category_id;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long sku_id;
    private Integer quantity;
    private String specs;
    private String pic_url;
}

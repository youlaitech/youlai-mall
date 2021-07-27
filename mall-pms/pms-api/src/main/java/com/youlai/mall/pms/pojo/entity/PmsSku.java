package com.youlai.mall.pms.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.youlai.common.base.BaseEntity;
import lombok.Data;

@Data
public class PmsSku extends BaseEntity {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long spuId;
    private String name;
    private String sn;
    private String picUrl;
    private String specs;
    private Long originPrice;
    private Long price;
    private Integer stock;
    private Integer lockedStock;
}

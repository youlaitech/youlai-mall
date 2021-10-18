package com.youlai.mall.pms.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.youlai.common.base.BaseEntity;
import lombok.Data;

@Data
public class PmsSku extends BaseEntity {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String sn;
    private String name;
    private Long spuId;
    private String specIds;
    private Long price;
    private Integer stock;
    private Integer lockedStock;
    private String picUrl;
}

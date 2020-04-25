package com.fly4j.yshop.pms.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fly4j.yshop.common.core.entity.BaseEntity;
import lombok.Data;

@Data
public class PmsAttribute extends BaseEntity {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private Long spu_id;
    private String name;
    private String value;
}

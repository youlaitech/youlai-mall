package com.fly4j.yshop.pms.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fly4j.common.core.entity.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PmsCategory extends BaseEntity {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private String name;
    private String description;
    private String icon_url;
    private Long parent_id;
    private Integer level;
    private String pic_url;
    private Integer sort;
    private Integer is_show;
}

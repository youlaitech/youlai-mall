package com.youlai.mall.pms.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.youlai.common.base.BaseEntity;
import lombok.Data;

@Data
@TableName("pms_category")
public class Category extends BaseEntity {

    @TableId(type= IdType.AUTO)
    private Long id;

    private String name;

    private Long parentId;

    private String iconUrl;

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private Integer level;

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private Integer sort;

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private Integer visible;
}

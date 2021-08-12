package com.youlai.mall.pms.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.youlai.common.base.BaseEntity;
import lombok.Data;

@Data
public class PmsCategory extends BaseEntity {

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

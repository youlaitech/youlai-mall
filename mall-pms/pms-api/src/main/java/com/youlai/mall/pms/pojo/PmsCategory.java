package com.youlai.mall.pms.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.youlai.common.core.base.BaseEntity;
import lombok.Data;

@Data
public class PmsCategory extends BaseEntity {

    @TableId(type= IdType.AUTO)
    private Long id;

    private String name;

    private Long parentId;

    private Integer level;

    private String icon;

    private Integer sort;

    private Integer status;
}

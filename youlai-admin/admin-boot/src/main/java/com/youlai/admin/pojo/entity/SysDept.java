package com.youlai.admin.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.youlai.common.base.BaseEntity;
import lombok.Data;

@Data
public class SysDept extends BaseEntity {

    @TableId(type= IdType.AUTO)
    private Long id;

    private String name;

    private Long parentId;

    private String treePath;

    private Integer sort;

    private Integer status;

    private Integer deleted;

}

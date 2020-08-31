package com.youlai.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.youlai.common.core.entity.BaseEntity;
import lombok.Data;

@Data
public class SysMenu  extends BaseEntity {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String name;

    private Integer parentId;

    private String icon;

    private Integer sort;

    private Integer visible;

    private Integer status;

    private String path;

    private String perms;

}

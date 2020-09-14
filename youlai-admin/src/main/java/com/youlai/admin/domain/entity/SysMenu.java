package com.youlai.admin.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.youlai.common.core.entity.BaseEntity;
import lombok.Data;

import java.util.List;

@Data
public class SysMenu  extends BaseEntity {

    @TableId
    private Integer id;

    private String name;

    private Integer parentId;

    private String icon;

    private Integer sort;

    private Integer visible;

    private Integer status;

    private String path;

    private String component;

    private String perms;

    @TableField(exist = false)
    private List<Integer> roles;

}

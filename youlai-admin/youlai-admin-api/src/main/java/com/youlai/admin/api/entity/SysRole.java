package com.youlai.admin.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.youlai.common.core.base.BaseEntity;
import lombok.Data;

import java.util.List;

@Data
public class SysRole extends BaseEntity {

    @TableId(type= IdType.AUTO)
    private Integer id;

    private String name;

    private String perms;

    private Integer sort;

    private Integer status;

    private Integer deleted;

    private String remark;

    @TableField(exist = false)
    private List<Integer> menuIds;

    @TableField(exist = false)
    private List<Integer> resourceIds;

}

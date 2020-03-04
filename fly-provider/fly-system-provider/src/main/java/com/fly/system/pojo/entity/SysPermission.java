package com.fly.system.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@TableName("sys_permission")
@Accessors(chain = true)
public class SysPermission {

    @TableId
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long permissionId;
    private String permissionName;
    private String perms;
}

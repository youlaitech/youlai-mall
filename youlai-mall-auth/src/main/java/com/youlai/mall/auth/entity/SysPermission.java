package com.youlai.mall.auth.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@TableName("sys_permission")
@Accessors(chain = true)
public class SysPermission {

    @TableId
    private Long permissionId;
    private String permissionName;
    private String perms;
}

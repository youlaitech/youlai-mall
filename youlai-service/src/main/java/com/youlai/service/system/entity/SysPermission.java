package com.youlai.service.system.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SysPermission {

    @TableId
    private Long permissionId;
    private String permissionName;
    private String perms;
}

package com.youlai.admin.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SysPermission {

    @TableId
    private Integer id;
    private String name;
    private String perms;
    private Integer type;
    private Integer status;
    private String icon;
    private Integer level;
    private Integer sort;
    private String url;
}

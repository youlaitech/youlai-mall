package com.youlai.admin.api.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.youlai.common.core.base.BaseEntity;
import lombok.Data;

@Data
public class SysDict  extends BaseEntity {

    @TableId
    private Integer id;

    private String name;

    private String value;

    private String typeCode;

    private String sort;

    private Integer status;

    private Integer defaulted;

    private  String remark;

}

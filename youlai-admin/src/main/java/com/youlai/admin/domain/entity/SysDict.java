package com.youlai.admin.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.youlai.common.core.entity.BaseEntity;
import lombok.Data;

@Data
public class SysDict  extends BaseEntity {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String label;

    private String value;

    private String typeCode;

    private String sort;

    private Integer status;

    private Integer isDefault;

    private  String remark;

}

package com.youlai.admin.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.youlai.common.core.entity.BaseEntity;
import lombok.Data;

@Data
public class SysDict  extends BaseEntity {

    @TableId
    private Integer id;

    private String value;

    private String text;

    private String typeCode;

    private String sort;

    private Integer status;

    private Integer defaulted;

    private  String remark;

}
